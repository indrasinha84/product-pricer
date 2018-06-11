package com.pricer.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.model.RESTMessage;

@Service
@CacheConfig(cacheNames = "priceDetailsCache")
public class PriceDetailsCacheService {

	private static Logger LOGGER = LoggerFactory.getLogger(PriceDetailsCacheService.class);

	@Autowired
	PriceDetailsService priceDetailsService;

	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;

	@Value("${com.pricer.properties.pricing.load.cache.on.startup}")
	boolean loadCacheOnStartUp;
	
	@Cacheable(key = "#product")
	public PriceDetails getPriceDetails(Integer product) {
		return priceDetailsService.getPriceDetails(product).getPayload();
	}

	@CachePut(key = "#product")
	public PriceDetails putPriceDetails(Integer product, PriceDetails priceDetails) {
		return priceDetails;
	}

	public JSONResponse<PriceDetails> getPriceDetailsResponse(Integer product) {
		PriceDetails priceDetails = priceDetailsCacheService.getPriceDetails(product);
		JSONResponse<PriceDetails> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, priceDetails);
		return response;
	}
	
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
