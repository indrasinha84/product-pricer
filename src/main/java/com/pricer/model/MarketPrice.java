package com.pricer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "STORE_PRICE")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({ "store", "product", "price", "notes", "created" })
public class MarketPrice implements Serializable {

	public MarketPrice() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226347194998137862L;

	private Integer id;
	private Store store;
	private Product product;
	private String notes;
	private Double storePrice;
	private EffectiveStatus effectiveStatus;
	private Date createdDate;
	
	@Id
	@Column(name = "STORE_PRICE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_PRICE_ID_GENERATOR")
	@GenericGenerator(name = "STORE_PRICE_ID_GENERATOR", strategy ="com.pricer.model.id.generator.MarketPriceIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_STORE_PRICE") })
	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "STORE_ID", nullable = false)
	@JsonIgnore
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@JsonProperty("store")
	@NotNull
	@Transient
	public Integer getStoreId() {
		return this.getStore().getId();
	}

	public void setStoreId(Integer storeId) {
		if (this.getStore() == null)
			this.setStore(new Store());
		this.store.setId(storeId);
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

	@JsonProperty("product")
	@NotNull
	@Transient
	public Integer getProductId() {
		return this.getProduct().getId();
	}

	public void setProductId(Integer productId) {
		if (this.getProduct() == null)
			this.setProduct(new Product());
		this.product.setId(productId);
	}

	@Column(name = "PRODUCT_NOTES", length = 1000)
	@JsonProperty(value = "notes", access = Access.WRITE_ONLY)
	@Size(max=1000)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "STORE_PRICE")
	@JsonProperty("price")
	@NotNull
	public Double getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Double storePrice) {
		this.storePrice = storePrice;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	@CreatedDate
	@JsonProperty(value = "created", access = Access.READ_ONLY)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "MarketPrice [id=" + id + ", store=" + store + ", product=" + product + ", notes=" + notes
				+ ", storePrice=" + storePrice + ", effectiveStatus=" + effectiveStatus + ", createdDate=" + createdDate
				+ "]";
	}

}
