package com.pricer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pricer.model.PriceDetails;

@Service
public class PriceDetailsCacheLoaderService {

	private static Logger LOGGER = LoggerFactory.getLogger(PriceDetailsCacheLoaderService.class);

	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;

	@Autowired
	PriceDetailsService priceDetailsService;

	@Value("${com.pricer.properties.pricing.load.cache.on.startup}")
	boolean loadCacheOnStartUp;

	public void createCache() {
		if (loadCacheOnStartUp) {
			LOGGER.info("Price Loading Cache loading started.");
			List<PriceDetails> caculatedPrices = priceDetailsService.listEntities().getPayload();
			caculatedPrices.stream().forEach(p -> priceDetailsCacheService.putPriceDetails(p.getProductId(), p));
			LOGGER.info("Price Loading Cache loading completed.");
		} else {
			LOGGER.info("Price Loading Cache skipped Flag: {}", loadCacheOnStartUp);
		}
	}

}
