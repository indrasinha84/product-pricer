package com.pricer.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.Product;

@JsonPropertyOrder({"name", "description", "basePrice", "created", "identifier"})
public class ProductResponseDTO implements Serializable, IRESTResponse<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5000127492293742278L;
	
	private Integer id;
	private String name;
	private String description;
	private Double basePrice;
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

	@JsonProperty("basePrice")
	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	@JsonProperty("created")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public void buildResponse(Product product) {
		this.setId(product.getId());
		this.setName(product.getName());
		this.setDescription(product.getDescription());
		this.setBasePrice(product.getBasePrice());
		this.setCreatedDate(product.getCreatedDate());
	}
}
