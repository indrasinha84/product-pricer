package com.pricer.batch.pricing.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pricer.batch.core.JobManagerService;

public class PriceCalculationJobManager implements Runnable {

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculationJobManager.class);

	public PriceCalculationJobManager(JobManagerService jobManager) {
		super();
		this.jobManager = jobManager;
	}

	JobManagerService jobManager;

	@Override
	public void run() {

		try {
			ExecutorService chunkPool = Executors.newFixedThreadPool(jobManager.getReaderPoolSize());
			while (true) {
				chunkPool.submit(jobManager.getReaderQueue().take());
			}
		} catch (InterruptedException e) {
			LOGGER.error("Failed on statring reader.", e.getCause());
		}
	}
}
