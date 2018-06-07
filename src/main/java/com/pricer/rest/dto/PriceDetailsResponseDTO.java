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
public class PriceDetailsResponseDTO implements Serializable, IJSONResponse<PriceDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1850855346464655658L;

	private String name;
	private Integer product;
	private String description;
	private Double basePrice;
	private Double averagePrice;
	private Double lowestPrice;
	private Double highestPrice;
	private Double idealPrice;
	private Integer count;	
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("product")
	public Integer getProduct() {
		return product;
	}


	public void setProduct(Integer product) {
		this.product = product;
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

	@JsonProperty("averagePrice")
	public Double getAveragePrice() {
		return averagePrice;
	}


	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}

	@JsonProperty("lowestPrice")
	public Double getLowestPrice() {
		return lowestPrice;
	}


	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	@JsonProperty("highestPrice")
	public Double getHighestPrice() {
		return highestPrice;
	}


	public void setHighestPrice(Double highestPrice) {
		this.highestPrice = highestPrice;
	}

	@JsonProperty("idealPrice")
	public Double getIdealPrice() {
		return idealPrice;
	}


	public void setIdealPrice(Double idealPrice) {
		this.idealPrice = idealPrice;
	}

	@JsonProperty("count")
	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	@Override
	public void buildResponse(PriceDetails entity) {
		this.setProduct(entity.getProduct().getId());
		this.setName(entity.getProduct().getName());
		this.setDescription(entity.getProduct().getDescription());
		this.setBasePrice(entity.getProduct().getBasePrice());
		this.setAveragePrice(entity.getAverageStorePrice());
		this.setLowestPrice(entity.getLowestPrice().getStorePrice());
		this.setHighestPrice(entity.getHighestPrice().getStorePrice());
		this.setIdealPrice(entity.getIdealPrice());
		this.setCount(entity.getCountOfPrices());
	}


}
