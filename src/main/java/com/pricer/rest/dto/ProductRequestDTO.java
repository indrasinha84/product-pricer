package com.pricer.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.Product;

@JsonPropertyOrder({ "name", "description", "basePrice" })
public class ProductRequestDTO implements Serializable, IRESTRequest<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7470106534009773465L;

	private String name;
	private String description;
	private Double basePrice;

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

	@JsonProperty("basePrice")
	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Product toEntity() {
		Product product = new Product();
		product.setName(this.getName());
		product.setDescription(this.getDescription());
		product.setBasePrice(this.getBasePrice());
		return product;
	}
}
