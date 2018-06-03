package com.pricer.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "STORE_PRICE")
public class StorePrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4041352438225918708L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_PRICE_ID_GENERATOR")
	@SequenceGenerator(name = "STORE_PRICE_ID_GENERATOR", sequenceName = "SEQ_STORE_PRICE", allocationSize = 1)
	@Column(name = "STORE_PRICE_ID")
	private Integer id;

	
	@ManyToOne
    @JoinColumn(name="STORE_ID", nullable=false)
	private Store store;

	@ManyToOne
    @JoinColumn(name="PRODUCT_ID", nullable=false)
	private Product product;
	
	@OneToMany(mappedBy = "lowestPrice")
	private Set<ProductPriceDetails> lowestPrices;
	
	@OneToMany(mappedBy = "highestPrice")
	private Set<ProductPriceDetails> highestPrices;
	

	@Column(name = "PRODUCT_NOTES", length = 1000)
	private String productNotes;

	@Column(name = "STORE_PRICE")
	private Double storePrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	@CreatedDate
	private Date createdDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<ProductPriceDetails> getLowestPrices() {
		return lowestPrices;
	}

	public void setLowestPrices(Set<ProductPriceDetails> lowestPrices) {
		this.lowestPrices = lowestPrices;
	}

	public Set<ProductPriceDetails> getHighestPrices() {
		return highestPrices;
	}

	public void setHighestPrices(Set<ProductPriceDetails> highestPrices) {
		this.highestPrices = highestPrices;
	}

	public String getProductNotes() {
		return productNotes;
	}

	public void setProductNotes(String productNotes) {
		this.productNotes = productNotes;
	}

	public Double getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Double storePrice) {
		this.storePrice = storePrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
