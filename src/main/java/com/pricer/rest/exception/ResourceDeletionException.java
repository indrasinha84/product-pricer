package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
public class ResourceDeletionException extends RuntimeException {

	/**
	* 
	*/
	private static final long serialVersionUID = -865877778578623805L;

	private String resourceName;

	public ResourceDeletionException(String resourceName) {
		super(String.format("%s modification failed.", resourceName));
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}
}
