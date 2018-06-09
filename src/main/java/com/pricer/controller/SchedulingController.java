package com.pricer.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.model.JSONResponse;
import com.pricer.model.SchedulerResponse;

@RestController
@RequestMapping("/jobs")
public class SchedulingController {

	
	@PostMapping(path="/pricecalculator", consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<SchedulerResponse> priceCalculator(
			@RequestParam String command) {
		return null;
		
	}
	
}
