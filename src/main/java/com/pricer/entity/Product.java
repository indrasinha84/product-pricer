package com.pricer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product extends AbstractProduct {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1339069450307772136L;

	public Product() {
	}
	
	private Integer id;

	@Id
	@Column(name = "PRODUCT_ID")	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
