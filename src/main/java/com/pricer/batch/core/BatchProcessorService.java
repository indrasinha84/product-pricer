package com.pricer.batch.core;

import java.util.Queue;

public interface BatchProcessorService<I> {
	
	public boolean process(Queue<I> itemQueue) throws Exception;

}
