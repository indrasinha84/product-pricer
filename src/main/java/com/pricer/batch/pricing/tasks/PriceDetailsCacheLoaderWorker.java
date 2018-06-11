package com.pricer.batch.pricing.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pricer.service.PriceDetailsCacheLoaderService;

@Component
public class PriceDetailsCacheLoaderWorker implements Runnable {

	@Autowired
	PriceDetailsCacheLoaderService priceDetailsCacheLoaderService;
	
	public PriceDetailsCacheLoaderWorker() {
		
	}

	private PriceDetailsCacheLoaderWorker(PriceDetailsCacheLoaderService priceDetailsCacheLoaderService) {
		super();
		this.priceDetailsCacheLoaderService = priceDetailsCacheLoaderService;
	}

	@Override
	public void run() {
		priceDetailsCacheLoaderService.createCache();
	}

	public PriceDetailsCacheLoaderWorker getInstance() {
		return new PriceDetailsCacheLoaderWorker(priceDetailsCacheLoaderService);
	}

}
