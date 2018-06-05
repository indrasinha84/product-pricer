package com.pricer.rest.dto;

public interface IJSONRequest<E, S, K> {
	
	public S toEntityWithSequence();
	
	public E toEntity(K key);


}
