package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ResourceListingException extends RuntimeException {

	/**
	* 
	*/
	private static final long serialVersionUID = -865877778578623805L;

	private String resourceName;

	public ResourceListingException(String resourceName) {
		super(String.format("%s listing failed.", resourceName));
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}
}
