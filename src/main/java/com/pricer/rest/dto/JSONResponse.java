package com.pricer.rest.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.RESTMessage;

@JsonPropertyOrder({"status", "message", "payload"})
public class JSONResponse<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4827794548581874633L;
	
	public JSONResponse(HttpStatus status, RESTMessage message, T payload) {
		super();
		this.status = status.value();
		this.message = message.getMessage();
		this.payload = payload;
	}
	
	public JSONResponse(HttpStatus status, String message, T payload) {
		super();
		this.status = status.value();
		this.message = message;
		this.payload = payload;
	}
	
	Integer status;
	
	String message;
	
	T payload;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
