package com.pricer.batch.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pricer.model.EventType;
import com.pricer.model.JSONResponse;
import com.pricer.model.SchedulerResponse;
import com.pricer.service.impl.PriceCalculatorEventLogService;

@Component
public class PricingCalculatorScheduler {

	@Autowired
	PriceCalculatorEventLogService priceCalculatorEventLogService;

	private static Logger LOGGER = LoggerFactory.getLogger(PricingCalculatorScheduler.class);

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
