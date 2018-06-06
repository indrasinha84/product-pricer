package com.pricer.pricing.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PricingCalculatorScheduler {
	
	@Scheduled(fixedRate=5000)
	public void calculatePricesScheduler() {
		System.out.println("Scheduler Started ********************");
		
	}
	
}
