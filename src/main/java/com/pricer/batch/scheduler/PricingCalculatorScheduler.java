package com.pricer.batch.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pricer.batch.core.JobManagerService;
import com.pricer.batch.pricing.tasks.PriceCalculationJobManager;
import com.pricer.batch.pricing.tasks.PriceDetailsCacheLoaderWorker;
import com.pricer.model.EventType;
import com.pricer.model.JSONResponse;
import com.pricer.model.SchedulerResponse;
import com.pricer.service.impl.PriceCalculatorEventLogService;
import com.pricer.service.impl.PriceDetailsCacheService;

@Component
public class PricingCalculatorScheduler implements CommandLineRunner{

	@Autowired
	PriceCalculatorEventLogService priceCalculatorEventLogService;

	
	@Autowired
	PriceDetailsCacheService priceDetailsCacheService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(PricingCalculatorScheduler.class);

	
	@Autowired
	@Qualifier("defaultPricingJobManager")
	JobManagerService jobManager;

	@Override
	public void run(String... args) throws Exception {

		(new Thread(new PriceCalculationJobManager(jobManager))).start();
		(new Thread(new PriceDetailsCacheLoaderWorker(priceDetailsCacheService))).start();
	}
	
	@Scheduled(fixedRateString = "${com.pricer.properties.pricing.batch.scheduler.frequency}")
	public void calculatePricesScheduler() {
		LOGGER.info("Price Calculation Scheduler Timer Triggered");
		try {
			JSONResponse<SchedulerResponse> response = priceCalculatorEventLogService.createEvent(EventType.SCHEDULER);
			if (response != null && response.getStatus() != null && response.getStatus().equals(HttpStatus.OK.value())
					&& response.getPayload() != null && response.getPayload().getJob() != null
					&& response.getPayload().getStarted() != null) {
				LOGGER.info("Price Calculation Scheduler Successfully Logged with Id {} at {}. ",
						response.getPayload().getJob(), response.getPayload().getStarted().toString());
			}
		} catch (Exception e) {
			LOGGER.error("Price Calculation Scheduler Logging Failed.", e);
		}
	}

}
