package com.pricer.batch.pricing.tasks;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import com.pricer.batch.core.ChunkManagerService;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;

public class PriceCalculationChunkManager implements Callable<Void> {

	private PriceCalculatorEventLog eventLog;
	private Integer chunkStartPosition;
	private Integer chunkEndPosition;
	private Queue<Product> productQueue;
	ChunkManagerService<PriceCalculatorEventLog, Product> priceCalculationReaderService;
	Semaphore processorSemaphore;

	public PriceCalculationChunkManager(
			ChunkManagerService<PriceCalculatorEventLog, Product> priceCalculationReaderService,
			PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition, 
			Semaphore processorSemaphore) {
		super();
		this.priceCalculationReaderService = priceCalculationReaderService;
		this.eventLog = eventLog;
		this.chunkStartPosition = chunkStartPosition;
		this.chunkEndPosition = chunkEndPosition;
		this.processorSemaphore = processorSemaphore;
	}

	@Override
	public Void call() {
		try {
			List<Product> products = priceCalculationReaderService.read(eventLog, chunkStartPosition, chunkEndPosition);
			processorSemaphore.acquire();
			Thread.sleep(5000);
			System.out.println("Chunkkkkkkkkkkkkkkkkkk" + chunkStartPosition +";;;;" + processorSemaphore.availablePermits());
			priceCalculationReaderService.processAndWrite(eventLog, products, productQueue, chunkStartPosition,
					chunkEndPosition);
			processorSemaphore.release();
		} catch (InterruptedException e) {
			e.getCause().printStackTrace();
		}

		return null;
	}
}
