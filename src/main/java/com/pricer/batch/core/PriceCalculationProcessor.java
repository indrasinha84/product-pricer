package com.pricer.batch.core;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pricer.model.Product;

public class PriceCalculationProcessor implements Callable<Boolean> {

	private Queue<Product> productQueue;
	CountDownLatch cdl;
	
	BatchProcessorService<Product> batchProcessorService;

	public PriceCalculationProcessor(BatchProcessorService<Product> batchProcessorService,
			Queue<Product> productQueue, CountDownLatch cdl) {
		this.batchProcessorService = batchProcessorService;
		this.productQueue = productQueue;
		this.cdl = cdl;
	}

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationProcessor.class);

	@Override
	public Boolean call() throws Exception {
		try {
			return batchProcessorService.process(productQueue);
		} catch (Exception e) {
			LOGGER.error("Failed in Processor. ", e);
			throw e;
		} finally {
			cdl.countDown();
		}
	} 
}
