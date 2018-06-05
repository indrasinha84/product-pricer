package com.pricer.entity.sequence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pricer.entity.AbstractProduct;

@Entity
@Table(name = "PRODUCT")
public class ProductWithSequence extends AbstractProduct {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1339069450307772136L;

	public ProductWithSequence() {
	}
	
	private Integer id;

	@Id
	@Column(name = "PRODUCT_ID")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
	@SequenceGenerator(name = "PRODUCT_ID_GENERATOR", sequenceName = "SEQ_PRODUCT", allocationSize = 1)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
