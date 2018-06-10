package com.pricer.model;

public enum Command {
	START("start"), FULL("fullstart");

	private String value;

	public String value() {
		return value;
	}

	Command(String value) {
		this.value = value;
	}

}
