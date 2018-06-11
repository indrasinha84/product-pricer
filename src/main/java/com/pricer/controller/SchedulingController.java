package com.pricer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.model.Command;
import com.pricer.model.EventType;
import com.pricer.model.JSONResponse;
import com.pricer.model.SchedulerResponse;
import com.pricer.rest.exception.SchedulerLoggingException;
import com.pricer.service.impl.PriceCalculatorEventLogService;

@RestController
@RequestMapping("/jobs")
public class SchedulingController {

	@Autowired
	PriceCalculatorEventLogService priceCalculatorEventLogService;

	@PostMapping(path = "/pricecalculator")
	public @ResponseBody JSONResponse<SchedulerResponse> priceCalculator(@RequestParam("command") String command) {
		try {
			if (Command.START.value().equals(command))
				return priceCalculatorEventLogService.createEvent(EventType.ADHOC);
			else if (Command.FULL.value().equals(command)) {
				return priceCalculatorEventLogService.createEvent(EventType.FULL);
			} else {
				throw new SchedulerLoggingException();
			}
		} catch (Exception e) {
			throw new SchedulerLoggingException();
		}
	}
}
