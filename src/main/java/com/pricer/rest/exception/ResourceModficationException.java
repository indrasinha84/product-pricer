package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
public class ResourceModficationException extends RuntimeException {

	/**
	* 
	*/
	private static final long serialVersionUID = -865877778578623805L;

	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public ResourceModficationException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s modification failed with %s : '%s'", resourceName, fieldName, fieldValue));
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
