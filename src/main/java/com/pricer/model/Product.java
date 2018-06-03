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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "PRODUCT")
@JsonIgnoreProperties(value = {"storePrice", "priceDetails", "latestDetails"}, 
allowGetters = false)
@JsonPropertyOrder({"name", "description", "basePrice", "created", "identifier"})
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3248457103645185693L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_ID_GENERATOR")
	@SequenceGenerator(name = "PRODUCT_ID_GENERATOR", sequenceName = "SEQ_PRODUCT", allocationSize = 1)
	@Column(name = "PRODUCT_ID")
	@JsonProperty("identifier")
	private Integer id;

	@JsonProperty("name")
	@Column(name = "PRODUCT_NAME", length = 100, nullable = false)
	private String name;

	@JsonProperty("description")
	@Column(name = "PRODUCT_DESCRIPTION", length = 1000)
	private String description;

	@JsonProperty("basePrice")
	@Column(name = "BASE_PRICE")
	private Double basePrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	@CreatedDate
	@JsonProperty("created")
	private Date createdDate;

	@OneToMany(mappedBy = "product")
	private Set<StorePrice> storePrice;
	
	@OneToMany(mappedBy = "product")
	private Set<ProductPriceDetails> priceDetails;
	
	@ManyToOne
    @JoinColumn(name="LATEST_DETAILS_ID", nullable=true, unique=true)
	private ProductPriceDetails latestDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Set<StorePrice> getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Set<StorePrice> storePrice) {
		this.storePrice = storePrice;
	}

	public Set<ProductPriceDetails> getPriceDetails() {
		return priceDetails;
	}

	public void setPriceDetails(Set<ProductPriceDetails> priceDetails) {
		this.priceDetails = priceDetails;
	}

	public ProductPriceDetails getLatestDetails() {
		return latestDetails;
	}

	public void setLatestDetails(ProductPriceDetails latestDetails) {
		this.latestDetails = latestDetails;
	}

//	@Column(name = "LATEST_PRICE_ID")
//	private Integer latestPriceId;

}
