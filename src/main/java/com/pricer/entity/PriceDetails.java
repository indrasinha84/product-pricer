package com.pricer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PRICE_DETAILS_LOG")
@EntityListeners(AuditingEntityListener.class)
public class PriceDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8927107845256199307L;

	private Integer id;
	private Product product;
	private Double averageStorePrice;
	private PriceAtStore lowestPrice;
	private PriceAtStore highestPrice;
	private Double idealPrice;
	private Integer countOfPrices;
	private Date createdDate;

	@Id
	@Column(name = "DETAILS_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETAILS_ID_GENERATOR")
	@GenericGenerator(name = "DETAILS_ID_GENERATOR", strategy = "com.pricer.entity.id.generator.PriceDetailsIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_PRICE_DETAILS_LOG") })
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name = "AVERAGE_STORE_PRICE")
	public Double getAverageStorePrice() {
		return averageStorePrice;
	}

	public void setAverageStorePrice(Double averageStorePrice) {
		this.averageStorePrice = averageStorePrice;
	}

	@ManyToOne
	@JoinColumn(name = "LOWEST_PRICE_ID", nullable = false)
	public PriceAtStore getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(PriceAtStore lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	@ManyToOne
	@JoinColumn(name = "HIGHEST_PRICE_ID", nullable = false)
	public PriceAtStore getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(PriceAtStore highestPrice) {
		this.highestPrice = highestPrice;
	}

	@Column(name = "IDEAL_STORE_PRICE")
	public Double getIdealPrice() {
		return idealPrice;
	}

	public void setIdealPrice(Double idealPrice) {
		this.idealPrice = idealPrice;
	}

	@Column(name = "COUNT_OF_PRICES")
	public Integer getCountOfPrices() {
		return countOfPrices;
	}

	public void setCountOfPrices(Integer countOfPrices) {
		this.countOfPrices = countOfPrices;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	@CreatedDate
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
