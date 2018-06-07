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

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "STORE")
@EntityListeners(AuditingEntityListener.class)
public class Store implements Serializable {
	
	
	public Store() {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3978844178062782422L;

	private Integer id; 
	private String name;
	private String description;
	private Date createdDate;
    private Set<MarketPrice> storePrice;

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_ID_GENERATOR")
	@GenericGenerator(name = "STORE_ID_GENERATOR", strategy = "com.pricer.entity.id.generator.StoreIdGenerator",
			parameters = {@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_STORE")})
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

	@JsonProperty("description")
	@Column(name = "STORE_DESCRIPTION", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public Set<MarketPrice> getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Set<MarketPrice> storePrice) {
		this.storePrice = storePrice;
	}
}