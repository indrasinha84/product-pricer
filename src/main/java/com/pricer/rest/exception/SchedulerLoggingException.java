package com.pricer.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SchedulerLoggingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -833370690313508761L;

	public SchedulerLoggingException() {

		super(String.format("Price Calculation Scheduler Logging Failed."));
	}
}
