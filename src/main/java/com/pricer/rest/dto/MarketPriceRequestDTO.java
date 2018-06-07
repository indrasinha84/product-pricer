package com.pricer.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pricer.entity.MarketPrice;
import com.pricer.entity.Product;
import com.pricer.entity.Store;

@JsonPropertyOrder({ "store", "product", "price", "notes" })
public class MarketPriceRequestDTO implements Serializable, IJSONRequest<MarketPrice, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7470106534009773465L;

	private Integer store;
	private Integer product;
	private Double storePrice;
	private String notes;

	@JsonProperty("store")
	@NotNull
	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@JsonProperty("product")
	@NotNull
	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	@JsonProperty("price")
	@NotNull
	public Double getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Double storePrice) {
		this.storePrice = storePrice;
	}

	@JsonProperty("notes")

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public MarketPrice toEntity(Integer id) {
		Store store = new Store();
		store.setId(this.getStore());
		Product product = new Product();
		product.setId(this.getProduct());
		Set<MarketPrice> storePrices = new HashSet<>();
		MarketPrice entity = new MarketPrice();
		storePrices.add(entity);
		product.setStorePrice(storePrices);
		entity.setId(id);
		entity.setStore(store);
		entity.setProduct(product);
		entity.setStorePrice(this.getStorePrice());
		entity.setNotes(this.getNotes());
		return entity;
	}

	@Override
	public MarketPrice buildEntityUsingNaturalKey() {
		Store store = new Store();
		store.setId(this.getStore());
		Product product = new Product();
		product.setId(this.getProduct());
		MarketPrice entity = new MarketPrice();
		entity.setStore(store);
		entity.setProduct(product);
		return entity;
	}

}