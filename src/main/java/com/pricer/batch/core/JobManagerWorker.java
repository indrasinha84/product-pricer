package com.pricer.batch.core;import org.springframework.stereotype.Component;

public class JobManagerWorker implements Runnable {
	
	public JobManagerWorker(JobManager jobManager) {
		super();
		this.jobManager = jobManager;
	}

	JobManager  jobManager;

	@Override
	public void run() {
		jobManager.startReader();
	}
}
