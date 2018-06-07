package com.pricer.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.MarketPrice;
import com.pricer.entity.PriceDetails;

@JsonPropertyOrder({ "product", "name", "description", "basePrice", "averagePrice", "lowestPrice", "highestPrice",
		"idealPrice", "count" })
public class SchedulerResponse implements Serializable, IJSONResponse<PriceDetails> {

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

	@Override
	public void buildResponse(PriceDetails entity) {
	}


}
