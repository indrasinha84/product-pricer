package com.pricer.model;

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
import javax.validation.constraints.NotBlank;
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
@Table(name = "PRODUCT")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({ "name", "description", "basePrice", "created", "identifier" })
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

	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
	@GenericGenerator(name = "PRODUCT_ID_GENERATOR", strategy = "com.pricer.entity.id.generator.ProductIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_PRODUCT") })
	@JsonProperty(value = "identifier", access = Access.READ_ONLY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PRODUCT_NAME", length = 100, nullable = false)
	@JsonProperty("name")
	@NotBlank
	@Size(max = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PRODUCT_DESCRIPTION", length = 1000)
	@JsonProperty("description")
	@Size(max = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "BASE_PRICE")
	@JsonProperty("basePrice")
	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@JsonProperty(value = "created", access = Access.READ_ONLY)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	public Set<MarketPrice> getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Set<MarketPrice> storePrice) {
		this.storePrice = storePrice;
	}

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	public Set<PriceDetails> getPriceDetails() {
		return priceDetails;
	}

	public void setPriceDetails(Set<PriceDetails> priceDetails) {
		this.priceDetails = priceDetails;
	}
}
