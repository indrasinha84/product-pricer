package com.pricer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	PriceDetailsService priceDetailsService;

	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;

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
}
