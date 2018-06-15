package com.pricer.batch.pricing.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pricer.batch.core.BatchProcessorService;
import com.pricer.batch.core.ChunkManagerService;
import com.pricer.batch.core.JobManagerService;
import com.pricer.batch.pricing.tasks.PriceCalculationProcessor;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceDetailsCacheService;
import com.pricer.service.impl.PriceDetailsService;
import com.pricer.service.impl.ProductService;

@Component("priceCalculationReaderService")
public class PriceCalculationChunkManagerServiceImpl implements ChunkManagerService<PriceCalculatorEventLog, Product> {

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationChunkManagerServiceImpl.class);

	@Autowired
	@Qualifier("defaultPricingJobManager")
	JobManagerService jobManager;

	@Autowired
	@Qualifier("priceCalculationProcessorService")
	BatchProcessorService<Product> priceCalculationProcessorService;

	@Autowired
	PricingCalculator pricingCalculator;

	@Autowired
	PriceDetailsService priceDetailsService;

	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;

	@Value("${com.pricer.properties.pricing.batch.processor.threads.pool.size}")
	private Integer poolSize;

	@Autowired
	ProductService productService;

	public List<Product> read(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition) {
		jobManager.markStarted(eventLog);
		return productService.getProductsForPriceCalculation(chunkStartPosition, chunkEndPosition);
	}

	public void processAndWrite(PriceCalculatorEventLog eventLog, List<Product> products, Queue<Product> productQueue,
			Integer chunkStartPosition, Integer chunkEndPosition) {

		if (products != null & !products.isEmpty()) {
			productQueue = new ConcurrentLinkedQueue<>(products);
			ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
			CountDownLatch cdl = new CountDownLatch(poolSize);
			List<Future<Boolean>> futuresList = new ArrayList<>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				futuresList.add(threadPool
						.submit(new PriceCalculationProcessor(priceCalculationProcessorService, productQueue, cdl)));
			}
			threadPool.shutdown();
			try {
				cdl.await();
			} catch (InterruptedException e) {
				LOGGER.error("Thread Interrupted.", e);
			}
			boolean batchSuccess = true;
			for (Future<Boolean> future : futuresList) {
				try {
					batchSuccess = batchSuccess & future.get();
				} catch (InterruptedException e) {
					LOGGER.error("Fatal error", e);
					jobManager.markFailure(eventLog);
				} catch (ExecutionException e) {
					LOGGER.error("Fatal error", e.getCause());
					batchSuccess = false;
					break;

				}
			}
			if (batchSuccess) {
				jobManager.markComplete(eventLog, chunkStartPosition, chunkEndPosition);
			} else {
				LOGGER.error("Execution Failed.");
				jobManager.markFailure(eventLog);

			}
		} else {
			LOGGER.error("No products to process.");

			jobManager.markComplete(eventLog, chunkStartPosition, chunkEndPosition);
		}
	}
}
