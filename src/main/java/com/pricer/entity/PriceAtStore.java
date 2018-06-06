package com.pricer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "STORE_PRICE")
@EntityListeners(AuditingEntityListener.class)
public class PriceAtStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226347194998137862L;

	private Integer id;

	private Store store;
	private Product product;
	private Set<ProductPriceDetails> lowestPrices;
	private Set<ProductPriceDetails> highestPrices;
	private String notes;
	private Double storePrice;
	private String effectivestatus;
	private Date createdDate;

	@Id
	@Column(name = "STORE_PRICE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_PRICE_ID_GENERATOR")
	@GenericGenerator(name = "STORE_PRICE_ID_GENERATOR", strategy = "com.pricer.entity.id.generator.PriceAtStoreIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_STORE_PRICE") })
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "STORE_ID", nullable = false)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToMany(mappedBy = "lowestPrice")
	public Set<ProductPriceDetails> getLowestPrices() {
		return lowestPrices;
	}

	public void setLowestPrices(Set<ProductPriceDetails> lowestPrices) {
		this.lowestPrices = lowestPrices;
	}

	@OneToMany(mappedBy = "highestPrice")
	public Set<ProductPriceDetails> getHighestPrices() {
		return highestPrices;
	}

	public void setHighestPrices(Set<ProductPriceDetails> highestPrices) {
		this.highestPrices = highestPrices;
	}

	@Column(name = "PRODUCT_NOTES", length = 1000)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "STORE_PRICE")
	public Double getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Double storePrice) {
		this.storePrice = storePrice;
	}

	@Column(name = "EFF_STS")
	public String getEffectivestatus() {
		return effectivestatus;
	}

	public void setEffectivestatus(String effectivestatus) {
		this.effectivestatus = effectivestatus;
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
