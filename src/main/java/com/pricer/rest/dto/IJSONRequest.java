package com.pricer.rest.dto;

public interface IJSONRequest<E, K> {
	
	public E toEntity(K key);
	
	public default E buildEntityUsingNaturalKey() {return null;};
	
}
