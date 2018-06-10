package com.pricer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;

@Service
@CacheConfig(cacheNames = "priceDetailsCache")
public class PriceDetailsCacheService {

	@Autowired
	PriceDetailsService priceDetailsService;

	@Cacheable(key = "#product")
	public JSONResponse<PriceDetails> getPriceDetails(Integer product) {
		return priceDetailsService.getPriceDetails(product);
	}
	
	
	@CachePut(key = "#product")
	public JSONResponse<PriceDetails> putPriceDetails(Integer product, JSONResponse<PriceDetails> priceDetails) {
		return priceDetails;
	}
}
