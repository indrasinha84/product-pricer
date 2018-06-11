package com.pricer.batch.core;

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

import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceDetailsCacheService;
import com.pricer.service.impl.PriceDetailsService;
import com.pricer.service.impl.ProductService;


//TODO Create Service for this
public class PriceCalculationReader implements Runnable {

	private PriceCalculatorEventLog eventLog;
	private Integer chunkStartPosition;
	private Integer chunkEndPosition;
	private Queue<Product> productQueue;
	PricingCalculator pricingCalculator;
	PriceDetailsService priceDetailsService;

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationReader.class);

	private Integer poolSize;

	ProductService productService;

	JobManager jobManager;

	private PriceDetailsCacheService priceDetailsCacheService;

	public PriceCalculationReader(ProductService productService, JobManager jobManager,
			PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition, Integer poolSize,
			PricingCalculator pricingCalculator, PriceDetailsService priceDetailsService,
			PriceDetailsCacheService priceDetailsCacheService) {
		super();
		this.eventLog = eventLog;
		this.chunkStartPosition = chunkStartPosition;
		this.chunkEndPosition = chunkEndPosition;
		this.productService = productService;
		this.jobManager = jobManager;
		this.poolSize = poolSize;
		this.pricingCalculator = pricingCalculator;
		this.priceDetailsService = priceDetailsService;
		this.priceDetailsCacheService = priceDetailsCacheService;
	}

	@Override
	public void run() {
		List<Product> products = productService.getProductsForPriceCalculation(chunkStartPosition, chunkEndPosition);
		if (products != null & !products.isEmpty()) {
			productQueue = new ConcurrentLinkedQueue<>(products);
			ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
			CountDownLatch cdl = new CountDownLatch(poolSize);
			List<Future<Boolean>> futuresList = new ArrayList<>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				futuresList.add(threadPool.submit(new PriceCalculationProcessor(productQueue, cdl, pricingCalculator,
						priceDetailsService, priceDetailsCacheService)));
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
					jobManager.markFailure(eventLog, chunkStartPosition, chunkEndPosition);
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
				jobManager.markFailure(eventLog, chunkStartPosition, chunkEndPosition);

			}
		} else {
			LOGGER.error("No products to process.");

			jobManager.markComplete(eventLog, chunkStartPosition, chunkEndPosition);
		}
		(new Thread(new JobManagerWorker(jobManager))).start();

	}

}
