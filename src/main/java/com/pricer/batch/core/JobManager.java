package com.pricer.batch.core;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceCalculatorEventLogService;
import com.pricer.service.impl.PriceDetailsCacheService;
import com.pricer.service.impl.PriceDetailsService;
import com.pricer.service.impl.ProductService;

@Component
public class JobManager {

	@Autowired
	PricingCalculator pricingCalculator;

	@Autowired
	PriceDetailsService priceDetailsService;

	private BlockingQueue<PriceCalculationReader> readerQueue;

	private volatile boolean batchRunning = false;

	@Value("${com.pricer.properties.pricing.batch.processor.threads.pool.size}")
	private Integer poolSize;

	@Autowired
	PriceCalculatorEventLogService eventLogService;
	
	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;

	@Autowired
	ProductService productService;

	final ReentrantLock lock = new ReentrantLock();
	Condition batchRuningWaitCondition = lock.newCondition();

	private static Logger LOGGER = LoggerFactory.getLogger(JobManager.class);

	@PostConstruct
	private void init() {
		loadQueueOnStartup();
	}

	// TODO Add chunk processing.
	private void loadQueueOnStartup() {
		List<PriceCalculatorEventLog> log = eventLogService.getPendingTheadsInOrder();
		readerQueue = new LinkedBlockingQueue<>();
		log.stream().forEach(p -> {
			try {
				readerQueue.put(createPriceCalculationReader(p, p.getStartPosition(), p.getEndPosition()));
			} catch (InterruptedException e) {
				LOGGER.error("Failed on batch startup.", e);
			}
		});
	}

	private PriceCalculationReader createPriceCalculationReader(PriceCalculatorEventLog eventLog,
			Integer chunkStartPosition, Integer chunkEndPosition) {
		return new PriceCalculationReader(productService, this, eventLog, chunkStartPosition, chunkEndPosition,
				poolSize, pricingCalculator, priceDetailsService, priceDetailsCacheService);
	}

	public void startReader() {
		try {
			lock.lock();
			PriceCalculationReader reader = readerQueue.take();
			while (batchRunning) {
				batchRuningWaitCondition.await();
			}
			new Thread(reader).start();
			batchRunning = true;
		} catch (InterruptedException e) {
			LOGGER.error("Failed on statring reader.", e);
		} finally {
			lock.unlock();
		}
	}

	public void publishEventToQueue(PriceCalculatorEventLog p) {
		try {
			readerQueue.put(createPriceCalculationReader(p, p.getStartPosition(), p.getEndPosition()));
		} catch (InterruptedException e) {
			LOGGER.error("Failed on event logging into queue.", e);
		}

	}

	public void markComplete(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition) {
		try {
			lock.lock();
			eventLogService.updateEventLogOnCompletion(eventLog, chunkStartPosition, chunkEndPosition);
			batchRunning = false;
			batchRuningWaitCondition.signal();
		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		} finally {
			lock.unlock();
		}
	}

	public void markFailure(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition) {
		try {
			lock.lock();
			eventLogService.updateEventLogOnFailure(eventLog, chunkStartPosition, chunkEndPosition);
			batchRunning = false;
			batchRuningWaitCondition.signal();
		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		} finally {
			lock.unlock();
		}
	}
}
