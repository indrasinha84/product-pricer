package com.pricer.rest.dto;

public interface IJSONResponse<E, S> {

	void buildResponseUsingSequence(S product);

	void buildResponse(E product);
	

}
