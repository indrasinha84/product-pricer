package com.pricer.rest.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.PriceAtStore;
import com.pricer.entity.Product;

@JsonPropertyOrder({ "store", "product", "price", "created" })
public class PriceAtStoreResponseDTO implements Serializable, IJSONResponse<PriceAtStore> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5000127492293742278L;

	private Integer store;
	private Integer product;
	private Double storePrice;
	private Date createdDate;

	@JsonProperty("store")
	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@JsonProperty("product")
	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	@JsonProperty("price")
	public Double getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Double storePrice) {
		this.storePrice = storePrice;
	}

	@JsonProperty("created")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public void buildResponse(PriceAtStore entity) {
		this.setStore(entity.getStore().getId());
		this.setProduct(entity.getProduct().getId());
		this.setStorePrice(entity.getStorePrice());
		this.setCreatedDate(entity.getCreatedDate());
	}
}
