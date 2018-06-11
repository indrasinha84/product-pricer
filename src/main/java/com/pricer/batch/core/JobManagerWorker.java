package com.pricer.batch.core;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobManagerWorker implements Runnable {

	public JobManagerWorker(JobManager jobManager) {
		super();
		this.jobManager = jobManager;
	}

	@Autowired
	JobManager jobManager;

	@Override
	public void run() {
		jobManager.startReader();
	}

	public JobManagerWorker getInstance() {
		return new JobManagerWorker(jobManager) ;
	}

}
