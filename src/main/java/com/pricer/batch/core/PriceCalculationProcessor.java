package com.pricer.batch.core;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceDetailsService;

public class PriceCalculationProcessor implements Callable<Boolean> {

	private Queue<Product> productQueue;
	CountDownLatch cdl;
	private PriceDetailsService priceDetailsService;
	private PricingCalculator pricingCalculator;
	
	


	public PriceCalculationProcessor(Queue<Product> productQueue, CountDownLatch cdl, PricingCalculator pricingCalculator,
			PriceDetailsService priceDetailsService) {
		this.productQueue = productQueue;
		this.cdl = cdl;
		this.pricingCalculator = pricingCalculator;
		this.priceDetailsService = priceDetailsService;
	}

	@Override
	public Boolean call() throws Exception {
		Product product = productQueue.poll();
		while (product != null) {
			PriceDetails priceDetails = pricingCalculator.getDetailsForAProduct(product);
			priceDetailsService.addOrReplacePriceDetails(priceDetails);
			product = productQueue.poll();
		}
		cdl.countDown();
		return true;
	}

}
