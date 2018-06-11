package com.pricer.batch.core;

import org.springframework.beans.factory.InitializingBean;

import com.pricer.model.PriceCalculatorEventLog;

public interface JobManager  extends InitializingBean{

	public void startReader();

	public void publishEventToQueue(PriceCalculatorEventLog p);

	public void markComplete(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition);

	public void markFailure(PriceCalculatorEventLog eventLog, Integer chunkStartPosition, Integer chunkEndPosition);
}
