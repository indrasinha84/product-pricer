package com.pricer.batch.core;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.InitializingBean;

import com.pricer.batch.pricing.tasks.PriceCalculationChunkManager;
import com.pricer.model.PriceCalculatorEventLog;

public interface JobManagerService extends InitializingBean {

	public void publishEvent(PriceCalculatorEventLog p) throws Exception;

	public void markComplete(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition);
	
	public void markStarted(PriceCalculatorEventLog eventLog);

	public void markFailure(PriceCalculatorEventLog eventLog);

	public Integer getReaderPoolSize();
		
	public BlockingQueue<PriceCalculationChunkManager> getReaderQueue();
	
}
