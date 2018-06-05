package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class IdNotAllowedException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6323093524969782359L;
	
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public IdNotAllowedException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s did not allow %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
