package com.pricer.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "job", "started" })
public class SchedulerResponse implements Serializable {

	public SchedulerResponse(String job, Date started) {
		super();
		this.job = job;
		this.started = started;
	}


	public SchedulerResponse() {
		super();
	}


	public void setStarted(Date started) {
		this.started = started;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1850855346464655658L;

	private String job;
	private Date started;
	
	
	
	
	@JsonProperty("job")
	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}

	@JsonProperty("started")
	public Date getStarted() {
		return started;
	}
}