package com.pricer.batch.core;

public class JobManagerWorker implements Runnable {
//TODO Merge this thread to reader thread
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
