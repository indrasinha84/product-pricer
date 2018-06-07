package com.pricer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PRODUCT")
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

	public Product() {
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = -3248457103645185693L;

	private Integer id;
	private String name;
	private String description;
	private Double basePrice;
	private Date createdDate;
	private Set<MarketPrice> storePrice;
	private Set<PriceDetails> priceDetails;
//	private PriceDetails latestDetails;

	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
	@GenericGenerator(name = "PRODUCT_ID_GENERATOR", strategy = "com.pricer.entity.id.generator.ProductIdGenerator", 
	parameters = {@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_PRODUCT")})
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PRODUCT_NAME", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PRODUCT_DESCRIPTION", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "BASE_PRICE")
	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@OneToMany(mappedBy = "product")
	public Set<MarketPrice> getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Set<MarketPrice> storePrice) {
		this.storePrice = storePrice;
	}

	@OneToMany(mappedBy = "product")
	public Set<PriceDetails> getPriceDetails() {
		return priceDetails;
	}

	public void setPriceDetails(Set<PriceDetails> priceDetails) {
		this.priceDetails = priceDetails;
	}

//	@ManyToOne
//	@JoinColumn(name = "LATEST_DETAILS_ID", nullable = true, unique = true)
//	public PriceDetails getLatestDetails() {
//		return latestDetails;
//	}
//
//	public void setLatestDetails(PriceDetails latestDetails) {
//		this.latestDetails = latestDetails;
//	}
}
