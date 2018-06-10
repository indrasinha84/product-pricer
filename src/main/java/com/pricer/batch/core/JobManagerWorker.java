package com.pricer.batch.core;

public class JobManagerWorker implements Runnable {

	public JobManagerWorker(JobManager jobManager) {
		super();
		this.jobManager = jobManager;
	}

	JobManager jobManager;

	@Override
	public void run() {
		jobManager.startReader();
	}

}
