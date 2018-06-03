package com.pricer.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "STORE")
public class Store implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3978844178062782422L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_ID_GENERATOR")
	@SequenceGenerator(name = "STORE_ID_GENERATOR", sequenceName = "SEQ_STORE")
	@Column(name = "STORE_ID")
	private Integer id;

	@Column(name = "STORE_NAME", length = 100, nullable = false)
	private String name;

	@Column(name = "STORE_DESCRIPTION", length = 1000)
	private String descrption;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@OneToMany(mappedBy="store")
    private Set<StorePrice> storePrice;

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

	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
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
}