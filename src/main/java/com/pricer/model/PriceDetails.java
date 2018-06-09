package com.pricer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "PRICE_DETAILS_LOG")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({ "product", "name", "description", "basePrice", "averagePrice", "lowestPrice", "highestPrice",
		"idealPrice", "count" })
public class PriceDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8927107845256199307L;

	private Integer id;
	private Product product;
	private Double averageStorePrice;
	private Double lowestPrice;
	private Double highestPrice;
	private Double idealPrice;
	private Integer countOfPrices;
	private Date createdDate;
	private EffectiveStatus effectiveStatus;

	@Id
	@Column(name = "DETAILS_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETAILS_ID_GENERATOR")
	@GenericGenerator(name = "DETAILS_ID_GENERATOR", strategy = "com.pricer.entity.id.generator.PriceDetailsIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_PRICE_DETAILS_LOG") })
	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	@JsonIgnore
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Transient
	@JsonProperty(value = "basePrice", access = Access.READ_ONLY)
	public Double getBasePrice() {
		return product.getBasePrice();
	}
	
	@JsonProperty(value="product", access = Access.READ_ONLY)
	@NotNull
	@Transient
	public Integer getProductId() {
		return this.getProduct().getId();
	}
	
	@JsonProperty(value="name", access = Access.READ_ONLY)
	@NotNull
	@Transient
	@Size(max = 100)
	public String getProductName() {
		return this.getProduct().getName();
	}
	
	@JsonProperty(value="description", access = Access.READ_ONLY)
	@NotNull
	@Transient
	@Size(max = 1000)
	public String getProductDescription() {
		return this.getProduct().getDescription();
	}

	public void setProductId(Integer productId) {
		if (this.getProduct() == null)
			this.setProduct(new Product());
		this.product.setId(productId);
	}

	@Column(name = "AVERAGE_STORE_PRICE")
	@JsonProperty(value = "averagePrice", access = Access.READ_ONLY)
	@NotNull
	public Double getAverageStorePrice() {
		return averageStorePrice;
	}

	public void setAverageStorePrice(Double averageStorePrice) {
		this.averageStorePrice = averageStorePrice;
	}

	@Column(name = "LOWEST_PRICE_ID", nullable = false)
	@JsonProperty(value = "lowestPrice", access = Access.READ_ONLY)
	@NotNull
	public Double getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Double lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	@Column(name = "HIGHEST_PRICE_ID", nullable = false)
	@JsonProperty(value = "highestPrice", access = Access.READ_ONLY)
	@NotNull
	public Double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(Double highestPrice) {
		this.highestPrice = highestPrice;
	}

	@Column(name = "IDEAL_STORE_PRICE")
	@JsonProperty(value = "idealPrice", access = Access.READ_ONLY)
	@NotNull
	public Double getIdealPrice() {
		return idealPrice;
	}

	public void setIdealPrice(Double idealPrice) {
		this.idealPrice = idealPrice;
	}

	@Column(name = "COUNT_OF_PRICES")
	@NotNull
	@JsonProperty(value = "count", access = Access.READ_ONLY)
	public Integer getCountOfPrices() {
		return countOfPrices;
	}

	public void setCountOfPrices(Integer countOfPrices) {
		this.countOfPrices = countOfPrices;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@CreatedDate
	@JsonIgnore
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "EFF_STS")
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	public EffectiveStatus getEffectiveStatus() {
		return effectiveStatus;
	}

	public void setEffectiveStatus(EffectiveStatus effectiveStatus) {
		this.effectiveStatus = effectiveStatus;
	}

}
