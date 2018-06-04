package com.pricer.rest.dto;

public interface IRESTResponse<T> {
	
	public void buildResponse(T t);

}
