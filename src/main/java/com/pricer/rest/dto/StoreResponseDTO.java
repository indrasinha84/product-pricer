package com.pricer.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.Store;

@JsonPropertyOrder({"name", "description", "created", "identifier"})
public class StoreResponseDTO implements Serializable, IJSONResponse<Store> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3655485079166074185L;
	
	private Integer id;
	private String name;
	private String description;
	private Date createdDate;
	
	@JsonProperty("identifier")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("created")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public void buildResponse(Store store) {
		this.setId(store.getId());
		this.setName(store.getName());
		this.setDescription(store.getDescription());
		this.setCreatedDate(store.getCreatedDate());
	}
}
