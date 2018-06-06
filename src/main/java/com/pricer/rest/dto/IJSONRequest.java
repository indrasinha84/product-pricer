package com.pricer.rest.dto;

import org.springframework.data.domain.Example;

public interface IJSONRequest<E, K> {
	
	public E toEntity(K key);
	
	public default Example<E> buildExampleUsingNaturalKey() {return null;};
	
}
