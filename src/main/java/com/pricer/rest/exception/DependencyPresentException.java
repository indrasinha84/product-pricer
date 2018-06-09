package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)

public class DependencyPresentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4423740691144212466L;
	
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public DependencyPresentException() {
		
	}
	public DependencyPresentException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("Dependendecy of %s present for %s : '%s'", resourceName, fieldName, fieldValue));
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
