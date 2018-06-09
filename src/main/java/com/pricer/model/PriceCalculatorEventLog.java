package com.pricer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PRICE_CALCULATION_REQUEST")
@EntityListeners(AuditingEntityListener.class)
public class PriceCalculatorEventLog implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8720664168323091633L;
	
	
	private Integer id;
	private Integer startPosition;
	private Integer endPosition;
	private Date requestedDate;
	private Date startDate;
	private Date endDate;
	private JobStatus status;
	private Integer restartPosition;

	@Id
	@Column(name = "CALCULATION_REQUEST_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALCULATION_REQUEST_ID_GENERATOR")
	@GenericGenerator(name = "CALCULATION_REQUEST_ID_GENERATOR", 
			strategy = "com.pricer.model.id.generator.PriceCalculatorEventLogIdGenerator", parameters = {
			@Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_PRICE_CALCULATION_REQUEST") })
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "DETAILS_ID_START", nullable = false, updatable = false)
	public Integer getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	
	@Column(name = "DETAILS_ID_END", nullable = false, updatable = false)
	public Integer getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(Integer endPosition) {
		this.endPosition = endPosition;
	}
	
	@Temporal(TemporalType.TIMESTAMP)	
	@Column(name = "REQUESTED_DATE", nullable = false, updatable = false)
	public Date getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "JOB_STS")
	public JobStatus getStatus() {
		return status;
	}
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	
	@Column(name = "DETAILS_ID_RESTART")
	public Integer getRestartPosition() {
		return restartPosition;
	}
	public void setRestartPosition(Integer restartPosition) {
		this.restartPosition = restartPosition;
	}



}
