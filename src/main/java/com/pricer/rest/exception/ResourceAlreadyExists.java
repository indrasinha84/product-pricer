package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class ResourceAlreadyExists extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5026597817284941768L;
	
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public ResourceAlreadyExists(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s already exists %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceAlreadyExists() {
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
