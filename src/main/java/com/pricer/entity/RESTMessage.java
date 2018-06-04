package com.pricer.entity;

public enum RESTMessage {
	
	OK("success"), FAILED("failed");

	public String getMessage() {
		return message;
	}

	private final String message;
	
	RESTMessage(String message) {
		this.message = message;
	}


}
