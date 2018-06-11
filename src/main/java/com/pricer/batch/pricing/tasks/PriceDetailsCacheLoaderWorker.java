package com.pricer.batch.pricing.tasks;

import org.springframework.stereotype.Component;

import com.pricer.service.PriceDetailsCacheLoaderService;

@Component
public class PriceDetailsCacheLoaderWorker implements Runnable {

	PriceDetailsCacheLoaderService priceDetailsCacheLoaderService;

	public PriceDetailsCacheLoaderWorker(PriceDetailsCacheLoaderService priceDetailsCacheLoaderService) {
		super();
		this.priceDetailsCacheLoaderService = priceDetailsCacheLoaderService;
	}

	@Override
	public void run() {
		priceDetailsCacheLoaderService.createCache();
	}
}
