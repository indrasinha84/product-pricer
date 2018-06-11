package com.pricer.batch.core;

import java.util.List;
import java.util.Queue;

import com.pricer.model.PriceCalculatorEventLog;

public interface BatchReaderService<E, I> {

	public List<I> read(Integer chunkStartPosition, Integer chunkEndPosition);

	public void processAndWrite(PriceCalculatorEventLog eventLog, List<I> item, Queue<I> itemQueue,
			Integer chunkStartPosition, Integer chunkEndPosition);

}
