package com.pricer.rest.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.Store;

@JsonPropertyOrder({ "name", "description" })
public class StoreRequestDTO implements Serializable, IJSONRequest<Store, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7087961662536220273L;
	
	private String name;
	private String description;

	@JsonProperty("name")
	@NotBlank
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

	@Override
	public Store toEntity(Integer id) {
		Store store = new Store();
		store.setName(this.getName());
		store.setDescription(this.getDescription());
		store.setId(id);
		return store;
	}
}
