package com.pricer.batch.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceDetailsCacheService;
import com.pricer.service.impl.PriceDetailsService;

public class PriceCalculationProcessor implements Callable<Boolean> {

	private Queue<Product> productQueue;
	CountDownLatch cdl;
	private PriceDetailsService priceDetailsService;
	private PricingCalculator pricingCalculator;
	PriceDetailsCacheService priceDetailsCacheService;

	public PriceCalculationProcessor(Queue<Product> productQueue, CountDownLatch cdl,
			PricingCalculator pricingCalculator, PriceDetailsService priceDetailsService,
			PriceDetailsCacheService priceDetailsCacheService) {
		this.productQueue = productQueue;
		this.cdl = cdl;
		this.pricingCalculator = pricingCalculator;
		this.priceDetailsService = priceDetailsService;
		this.priceDetailsCacheService = priceDetailsCacheService;
	}

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationProcessor.class);

	@Override
	public Boolean call() throws Exception {
		try {
			List<PriceDetails> caculatedPrices = new LinkedList<>();
			Product product = productQueue.poll();
			while (product != null) {
				PriceDetails priceDetails = pricingCalculator.getDetailsForAProduct(product);
				caculatedPrices.add(priceDetails);
				product = productQueue.poll();
			}
			if (caculatedPrices.size() != 0) {
				List<JSONResponse<PriceDetails>> priceList = priceDetailsService.addOrReplacePriceDetails(caculatedPrices);
				putCache(priceList);

			}

			return true;

		} catch (Exception e) {
			LOGGER.error("Failed in Processor. ", e);
			throw e;
		} finally {
			cdl.countDown();
		}
	}

	private void putCache(List<JSONResponse<PriceDetails>> caculatedPrices) {
		caculatedPrices.stream().forEach(p -> priceDetailsCacheService.putPriceDetails( p.getPayload().getProductId(), p));
	}

}
