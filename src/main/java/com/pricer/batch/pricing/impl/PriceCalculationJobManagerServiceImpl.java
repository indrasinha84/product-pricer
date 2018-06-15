package com.pricer.batch.pricing.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pricer.batch.core.ChunkManagerService;
import com.pricer.batch.core.JobManagerService;
import com.pricer.batch.pricing.tasks.PriceCalculationChunkManager;
import com.pricer.model.JobStatus;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;
import com.pricer.service.impl.PriceCalculatorEventLogService;
import com.pricer.service.impl.ProductService;

@Service("defaultPricingJobManager")
public class PriceCalculationJobManagerServiceImpl implements JobManagerService {

	private BlockingQueue<PriceCalculationChunkManager> readerQueue;

	public BlockingQueue<PriceCalculationChunkManager> getReaderQueue() {
		return readerQueue;
	}

	@Autowired
	PriceCalculatorEventLogService eventLogService;

	@Autowired
	ProductService productService;
	
	@Autowired
	@Qualifier("priceCalculationReaderService")
	ChunkManagerService<PriceCalculatorEventLog, Product> priceCalculationReaderService;

	final ReentrantLock lock = new ReentrantLock();
	
	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationJobManagerServiceImpl.class);

	@Value("${com.pricer.properties.pricing.batch.reader.chunk.size}")
	int chunkSize;

	@Value("${com.pricer.properties.pricing.batch.reader.threads.pool}")
	private Integer readerPoolSize;
	
	private Semaphore processorSemaphore;

	public void loadPoolOnStartup() {
		List<PriceCalculatorEventLog> log = eventLogService.getPendingTheadsInOrder();
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
		int totalChunks = (totalItems / chunkSize) + ((totalItems % chunkSize) > 0 ? 1 : 0);
		int chunkStart = logStart;
		int chunkEnd = (chunkStart + chunkSize - 1) >= logEnd ? logEnd : chunkStart + chunkSize - 1;
		for (int i = 0; i < totalChunks; i++) {
			readerQueue.put(new PriceCalculationChunkManager(priceCalculationReaderService, log, chunkStart, chunkEnd,
					processorSemaphore));
			chunkStart = chunkEnd + 1;
			chunkEnd = (chunkStart + chunkSize - 1) >= logEnd ? logEnd : chunkStart + chunkSize - 1;
		}

	}
	
	public Integer getReaderPoolSize() {
		return readerPoolSize;
	}

	public void publishEvent(PriceCalculatorEventLog log) throws InterruptedException {
		createChunkedEvents(log);
	}

	public void markComplete(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition) {
		try {
			lock.lock();
			eventLogService.updateEventLogOnCompletion(eventLog, chunkStartPosition, chunkEndPosition);
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
		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		readerQueue = new LinkedBlockingQueue<>();
		processorSemaphore = new Semaphore(1);
		loadPoolOnStartup();

		
	}

}
