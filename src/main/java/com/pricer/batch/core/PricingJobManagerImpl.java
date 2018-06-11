package com.pricer.batch.core;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;
import com.pricer.service.impl.PriceCalculatorEventLogService;
import com.pricer.service.impl.ProductService;

@Service("defaultPricingJobManager")
public class PricingJobManagerImpl implements JobManager {

	private BlockingQueue<PriceCalculationReader> readerQueue;

	private volatile boolean batchRunning = false;

	@Autowired
	PriceCalculatorEventLogService eventLogService;

	@Autowired
	ProductService productService;

	@Autowired
	@Qualifier("priceCalculationReaderService")
	BatchReaderService<PriceCalculatorEventLog, Product> priceCalculationReaderService;

	final ReentrantLock lock = new ReentrantLock();
	Condition batchRuningWaitCondition = lock.newCondition();

	private static Logger LOGGER = LoggerFactory.getLogger(PricingJobManagerImpl.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		loadQueueOnStartup();
	}

	// TODO Add chunk processing.
	private void loadQueueOnStartup() {
		List<PriceCalculatorEventLog> log = eventLogService.getPendingTheadsInOrder();
		readerQueue = new LinkedBlockingQueue<>();
		log.stream().forEach(p -> {
			try {
				readerQueue.put(new PriceCalculationReader(priceCalculationReaderService, p, p.getStartPosition(),
						p.getEndPosition()));
			} catch (InterruptedException e) {
				LOGGER.error("Failed on batch startup.", e);
			}
		});
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
			readerQueue.put(new PriceCalculationReader(priceCalculationReaderService, p, p.getStartPosition(),
					p.getEndPosition()));
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
