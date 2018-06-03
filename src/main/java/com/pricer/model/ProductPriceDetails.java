package com.pricer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "PRICE_DETAILS_LOG")
public class ProductPriceDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8927107845256199307L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETAILS_ID_GENERATOR")
	@SequenceGenerator(name = "DETAILS_ID_GENERATOR", sequenceName = "SEQ_PRICE_DETAILS_LOG", allocationSize = 1)
	@Column(name = "DETAILS_ID")
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name="PRODUCT_ID", nullable=false)
	private Product product;
	
	@Column(name = "AVERAGE_STORE_PRICE")
	private Double averageStorePrice;
	
	@ManyToOne
    @JoinColumn(name="LOWEST_PRICE_ID", nullable=false)
	private StorePrice lowestPrice;
	
	@ManyToOne
    @JoinColumn(name="HIGHEST_PRICE_ID", nullable=false)
	private StorePrice highestPrice;
	
	@Column(name = "IDEAL_STORE_PRICE")
	private Double idealPrice;
	
	@Column(name = "COUNT_OF_PRICES")
	private Integer countOfPrices;
	
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getAverageStorePrice() {
		return averageStorePrice;
	}

	public void setAverageStorePrice(Double averageStorePrice) {
		this.averageStorePrice = averageStorePrice;
	}

	public StorePrice getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(StorePrice lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public StorePrice getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(StorePrice highestPrice) {
		this.highestPrice = highestPrice;
	}

	public Double getIdealPrice() {
		return idealPrice;
	}

	public void setIdealPrice(Double idealPrice) {
		this.idealPrice = idealPrice;
	}

	public Integer getCountOfPrices() {
		return countOfPrices;
	}

	public void setCountOfPrices(Integer countOfPrices) {
		this.countOfPrices = countOfPrices;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}	
	
}
