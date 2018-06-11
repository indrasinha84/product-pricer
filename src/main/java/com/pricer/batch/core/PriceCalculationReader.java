package com.pricer.batch.core;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;

@Component
public class PriceCalculationReader implements Runnable {

	private PriceCalculatorEventLog eventLog;
	private Integer chunkStartPosition;
	private Integer chunkEndPosition;
	private Queue<Product> productQueue;

	@Autowired
	PriceCalculationReaderService priceCalculationReaderService;

	public PriceCalculationReader() {
		
	}
	
	private PriceCalculationReader(PriceCalculationReaderService priceCalculationReaderService, PriceCalculatorEventLog eventLog, Integer chunkStartPosition,
			Integer chunkEndPosition) {
		super();
		this.priceCalculationReaderService = priceCalculationReaderService;
		this.eventLog = eventLog;
		this.chunkStartPosition = chunkStartPosition;
		this.chunkEndPosition = chunkEndPosition;
	}

	@Override
	public void run() {
		List<Product> products = priceCalculationReaderService.read(chunkStartPosition, chunkEndPosition);
		priceCalculationReaderService.processAndWrite(eventLog, products, productQueue, chunkStartPosition,
				chunkEndPosition);
	}

	PriceCalculationReader getInstance(PriceCalculatorEventLog eventLog, Integer chunkStartPosition,
			Integer chunkEndPosition) {
		return new PriceCalculationReader(priceCalculationReaderService, eventLog, chunkStartPosition, chunkEndPosition);
	}
}
