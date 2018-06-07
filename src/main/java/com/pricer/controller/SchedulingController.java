package com.pricer.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.rest.dto.JSONResponse;
import com.pricer.rest.dto.MarketPriceResponseDTO;

@RestController
@RequestMapping("/jobs")
public class SchedulingController {

	
	@PostMapping(path="/pricecalculator", consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<MarketPriceResponseDTO> priceCalculator() {
		return null;
		
	}
	
}
