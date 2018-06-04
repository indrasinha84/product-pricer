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
@Table(name = "STORE")
@JsonIgnoreProperties(value = {"storePrice"})
@JsonPropertyOrder({"name", "description", "created", "identifier"})
@EntityListeners(AuditingEntityListener.class)
public class Store implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3978844178062782422L;

	private Integer id; 
	private String name;
	private String descrption;
	private Date createdDate;
    private Set<StorePrice> storePrice;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_ID_GENERATOR")
	@SequenceGenerator(name = "STORE_ID_GENERATOR", sequenceName = "SEQ_STORE", allocationSize = 1)
	@JsonProperty("identifier")
	@Column(name = "STORE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonProperty("name")
	@Column(name = "STORE_NAME", length = 100, nullable = false) 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("descrption")
	@Column(name = "STORE_DESCRIPTION", length = 1000)
	public String getDescrption() {
		return descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonProperty("created")
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@OneToMany(mappedBy="store")
	public Set<StorePrice> getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Set<StorePrice> storePrice) {
		this.storePrice = storePrice;
	}
}