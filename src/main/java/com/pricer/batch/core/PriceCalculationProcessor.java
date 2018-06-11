package com.pricer.batch.core;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pricer.model.Product;

@Component
public class PriceCalculationProcessor implements Callable<Boolean> {

	private Queue<Product> productQueue;
	CountDownLatch cdl;

	@Autowired
	PriceCalculationProcessorService priceCalculationProcessorService;

	public PriceCalculationProcessor() {

	}

	private PriceCalculationProcessor(PriceCalculationProcessorService priceCalculationProcessorService,
			Queue<Product> productQueue, CountDownLatch cdl) {
		this.priceCalculationProcessorService = priceCalculationProcessorService;
		this.productQueue = productQueue;
		this.cdl = cdl;
	}

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationProcessor.class);

	@Override
	public Boolean call() throws Exception {
		try {
			return priceCalculationProcessorService.calculatePrice(productQueue);
		} catch (Exception e) {
			LOGGER.error("Failed in Processor. ", e);
			throw e;
		} finally {
			cdl.countDown();
		}
	}

	PriceCalculationProcessor getInstance(Queue<Product> productQueue, CountDownLatch cdl) {
		return new PriceCalculationProcessor(priceCalculationProcessorService, productQueue, cdl);
	}

}
