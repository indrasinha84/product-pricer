package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
public class ResourceNotDeletedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6258552473968636760L;
	
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public ResourceNotDeletedException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not deleted with %s : '%s'", resourceName, fieldName, fieldValue));
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
