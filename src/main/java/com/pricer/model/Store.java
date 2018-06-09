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
@Table(name = "STORE")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({ "name", "description", "created", "identifier"})
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
	@GenericGenerator(name = "STORE_ID_GENERATOR", strategy = "com.pricer.model.id.generator.StoreIdGenerator",
			parameters = {@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_STORE")})
	@JsonProperty(value = "identifier", access = Access.READ_ONLY)
	@Column(name = "STORE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonProperty("name")
	@NotBlank
	@Size(max=100)
	@Column(name = "STORE_NAME", length = 100, nullable = false) 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	@Size(max=1000)
	@Column(name = "STORE_DESCRIPTION", length = 1000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToMany(mappedBy="store")
	@JsonIgnore
	public Set<MarketPrice> getStorePrice() {
		return storePrice;
	}

	public void setStorePrice(Set<MarketPrice> storePrice) {
		this.storePrice = storePrice;
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", description=" + description + ", createdDate=" + createdDate
				+ "]";
	}

	
	
}