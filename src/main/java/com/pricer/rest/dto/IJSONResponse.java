package com.pricer.rest.dto;

public interface IJSONResponse<E> {

	void buildResponse(E entity);
	

}
