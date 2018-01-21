package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditPartner Entity.
 */
public class CreditPartner implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Integer partnerId;
	
	private String partnerName;
	
	private String icon;
	
	private Double inMultiplier;
	
	private Double inDivisor;
	
	private Double outMultiplier;
	
	private Double outDivisor;
	
	private java.util.Date startDate;
	
	private java.util.Date endDate;


	private String startDateStr;

	private String endDateStr;

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	private Integer status;

	private String description;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setPartnerId(Integer value) {
		this.partnerId = value;
	}
	
	public Integer getPartnerId() {
		return this.partnerId;
	}
		
		
	public void setPartnerName(String value) {
		this.partnerName = value;
	}
	
	public String getPartnerName() {
		return this.partnerName;
	}
		
		
	public void setIcon(String value) {
		this.icon = value;
	}
	
	public String getIcon() {
		return this.icon;
	}
		
		
	public void setInMultiplier(Double value) {
		this.inMultiplier = value;
	}
	
	public Double getInMultiplier() {
		return this.inMultiplier;
	}
		
		
	public void setInDivisor(Double value) {
		this.inDivisor = value;
	}
	
	public Double getInDivisor() {
		return this.inDivisor;
	}
		
		
	public void setOutMultiplier(Double value) {
		this.outMultiplier = value;
	}
	
	public Double getOutMultiplier() {
		return this.outMultiplier;
	}
		
		
	public void setOutDivisor(Double value) {
		this.outDivisor = value;
	}
	
	public Double getOutDivisor() {
		return this.outDivisor;
	}
		
		
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
	public java.util.Date getStartDate() {
		return this.startDate;
	}
		
		
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
//
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}

