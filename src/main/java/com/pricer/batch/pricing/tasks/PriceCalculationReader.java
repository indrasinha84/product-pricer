package com.pricer.batch.pricing.tasks;

import java.util.List;
import java.util.Queue;

import com.pricer.batch.core.BatchReaderService;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;

public class PriceCalculationReader implements Runnable {

	private PriceCalculatorEventLog eventLog;
	private Integer chunkStartPosition;
	private Integer chunkEndPosition;
	private Queue<Product> productQueue;
	BatchReaderService<PriceCalculatorEventLog, Product> priceCalculationReaderService; 

	public PriceCalculationReader(BatchReaderService<PriceCalculatorEventLog, Product> priceCalculationReaderService,
			PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition) {
		super();
		this.priceCalculationReaderService = priceCalculationReaderService;
		this.eventLog = eventLog;
		this.chunkStartPosition = chunkStartPosition;
		this.chunkEndPosition = chunkEndPosition;
	}

	@Override
	public void run() {
		List<Product> products = priceCalculationReaderService.read(eventLog, chunkStartPosition, chunkEndPosition);
		priceCalculationReaderService.processAndWrite(eventLog, products, productQueue, chunkStartPosition,
				chunkEndPosition);
	}
}