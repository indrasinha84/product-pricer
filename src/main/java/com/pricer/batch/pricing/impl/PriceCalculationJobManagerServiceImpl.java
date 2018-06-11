package com.pricer.batch.pricing.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pricer.batch.core.ChunkManagerService;
import com.pricer.batch.core.JobManagerService;
import com.pricer.batch.pricing.tasks.PriceCalculationReader;
import com.pricer.model.JobStatus;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;
import com.pricer.service.impl.PriceCalculatorEventLogService;
import com.pricer.service.impl.ProductService;

@Service("defaultPricingJobManager")
public class PriceCalculationJobManagerServiceImpl implements JobManagerService {

	private BlockingQueue<PriceCalculationReader> readerQueue;

	private volatile boolean batchRunning = false;

	@Autowired
	PriceCalculatorEventLogService eventLogService;

	@Autowired
	ProductService productService;

	@Autowired
	@Qualifier("priceCalculationReaderService")
	ChunkManagerService<PriceCalculatorEventLog, Product> priceCalculationReaderService;

	final ReentrantLock lock = new ReentrantLock();
	Condition batchRuningWaitCondition = lock.newCondition();

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationJobManagerServiceImpl.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		loadQueueOnStartup();
	}

	@Value("${com.pricer.properties.pricing.batch.chunk_size}")
	int chunkSize;

	private void loadQueueOnStartup() {
		List<PriceCalculatorEventLog> log = eventLogService.getPendingTheadsInOrder();
		readerQueue = new LinkedBlockingQueue<>();
		log.stream().forEach(p -> {
			try {
				createChunkedEvents(p);
			} catch (InterruptedException e) {
				LOGGER.error("Failed while event logging on startup.", e);
				e.printStackTrace();
			}
		});
	}

	private void createChunkedEvents(PriceCalculatorEventLog log) throws InterruptedException {

		int logStart = log.getRestartPosition() == null ? log.getStartPosition() : log.getRestartPosition();
		int logEnd = log.getEndPosition();
		int totalItems = logEnd - logStart + 1;
		int totalChunks = (totalItems / chunkSize) + 
				((totalItems % chunkSize) > 0 ? 1 : 0);
		int chunkStart = logStart;
		int chunkEnd = (chunkStart + chunkSize - 1) >= logEnd ? logEnd : chunkStart + chunkSize - 1;
		for (int i = 0; i < totalChunks; i++) {
			try {
				readerQueue.put(new PriceCalculationReader(priceCalculationReaderService, log, chunkStart, chunkEnd));
				chunkStart = chunkEnd + 1;
				chunkEnd = (chunkStart + chunkSize - 1) >= logEnd ? logEnd : chunkStart + chunkSize - 1;
			} catch (InterruptedException e) {
				LOGGER.error("Failed while event logging into queue.", e);
				throw e;
			}
		}

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

	public void publishEventToQueue(PriceCalculatorEventLog log) throws InterruptedException {
		createChunkedEvents(log);
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

	public void markFailure(PriceCalculatorEventLog eventLog) {
		try {
			lock.lock();
			eventLogService.updateEventLogStatus(eventLog, JobStatus.FAILED);
			batchRunning = false;
			batchRuningWaitCondition.signal();
		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void markStarted(PriceCalculatorEventLog eventLog) {
		try {
			eventLogService.updateEventLogStatus(eventLog, JobStatus.STARTED);
			batchRunning = true;
		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		}
	}
}
