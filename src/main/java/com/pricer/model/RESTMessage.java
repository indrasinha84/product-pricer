package com.pricer.model;

public enum RESTMessage {
	
	OK("success"), FAILED("FAILED");

	public String getMessage() {
		return message;
	}

	private final String message;
	
	RESTMessage(String message) {
		this.message = message;
	}


}
