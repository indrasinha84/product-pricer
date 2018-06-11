package com.pricer.batch.pricing.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pricer.batch.core.BatchProcessorService;
import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceDetailsCacheService;
import com.pricer.service.impl.PriceDetailsService;

@Service("priceCalculationProcessorService")
public class PriceCalculationProcessorServiceImpl implements BatchProcessorService<Product> {	
	
	@Autowired
	private PriceDetailsService priceDetailsService;
	
	@Autowired
	private PricingCalculator pricingCalculator;
	
	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationProcessorServiceImpl.class);

	@Override
	public boolean process(Queue<Product> productQueue) throws Exception {
		try {
			List<PriceDetails> caculatedPrices = new LinkedList<>();
			Product product = productQueue.poll();
			while (product != null) {
				PriceDetails priceDetails = pricingCalculator.getDetailsForAProduct(product);
				caculatedPrices.add(priceDetails);
				product = productQueue.poll();
			}
			if (caculatedPrices.size() != 0) {
				List<PriceDetails> priceList = priceDetailsService.addOrReplacePriceDetails(caculatedPrices);
				putCache(priceList);

			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Failed in calculatePrice. ", e);
			throw e;
		} 
	}

	private void putCache(List<PriceDetails> caculatedPrices) {
		caculatedPrices.stream().forEach(p -> priceDetailsCacheService.putPriceDetails( p.getProductId(), p));
	}

}
