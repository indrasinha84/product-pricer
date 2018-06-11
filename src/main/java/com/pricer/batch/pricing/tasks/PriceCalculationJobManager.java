package com.pricer.batch.pricing.tasks;

import com.pricer.batch.core.JobManagerService;

public class PriceCalculationJobManager implements Runnable {
	
	public PriceCalculationJobManager(JobManagerService jobManager) {
		super();
		this.jobManager = jobManager;
	}

	JobManagerService  jobManager;

	@Override
	public void run() {
		jobManager.startReader();
	}
}
