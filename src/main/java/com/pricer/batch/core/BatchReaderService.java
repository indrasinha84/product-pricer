package com.pricer.batch.core;

import java.util.List;
import java.util.Queue;

public interface BatchReaderService<E, I> {

	public List<I> read(E eventLog, Integer chunkStartPosition, Integer chunkEndPosition);

	public void processAndWrite(E eventLog, List<I> item, Queue<I> itemQueue,
			Integer chunkStartPosition, Integer chunkEndPosition);

}
