package com.pricer.rest.dto;

import java.io.Serializable;

import com.pricer.entity.MarketPrice;
import com.pricer.entity.PriceDetails;
import com.pricer.entity.Product;
import com.pricer.entity.Store;

public class PriceDetailsRequestDTO implements Serializable, IJSONRequest<PriceDetails, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8169920771521044720L;

	private Integer product;

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	@Override
	public PriceDetails toEntity(Integer key) {
		return null;
	}
	
	@Override
	public PriceDetails buildEntityUsingNaturalKey() {
		Product product = new Product();
		product.setId(this.getProduct());
		PriceDetails entity = new PriceDetails();
		entity.setProduct(product);
		return entity;
	}

}
