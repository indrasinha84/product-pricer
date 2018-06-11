package com.pricer.batch.pricing.tasks;

import org.springframework.stereotype.Component;

import com.pricer.service.impl.PriceDetailsCacheService;

@Component
public class PriceDetailsCacheLoaderWorker implements Runnable {

	PriceDetailsCacheService priceDetailsCacheService;

	public PriceDetailsCacheLoaderWorker(PriceDetailsCacheService priceDetailsCacheService) {
		super();
		this.priceDetailsCacheService = priceDetailsCacheService;
	}

	@Override
	public void run() {
		priceDetailsCacheService.createCache();
	}
}
