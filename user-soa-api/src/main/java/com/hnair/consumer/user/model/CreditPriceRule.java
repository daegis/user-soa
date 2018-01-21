package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditPriceRule Entity.
 */
public class CreditPriceRule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Integer businessType;
	
	private String businessName;
	
	private Double multiplier;
	
	private Double divisor;
	
	private Integer status;
	
	private java.util.Date startTime;
	
	private java.util.Date endTime;
	
	private java.util.Date createTime;
	
	private String description;
	
	private String startTimeStr;
	
	private String endTimeStr;
	
	private Double deductionPercentage;
	
	public Double getDeductionPercentage() {
		return deductionPercentage;
	}

	public void setDeductionPercentage(Double deductionPercentage) {
		this.deductionPercentage = deductionPercentage;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setBusinessType(Integer value) {
		this.businessType = value;
	}
	
	public Integer getBusinessType() {
		return this.businessType;
	}
		
		
	public void setBusinessName(String value) {
		this.businessName = value;
	}
	
	public String getBusinessName() {
		return this.businessName;
	}
		
		
	public void setMultiplier(Double value) {
		this.multiplier = value;
	}
	
	public Double getMultiplier() {
		return this.multiplier;
	}
		
		
	public void setDivisor(Double value) {
		this.divisor = value;
	}
	
	public Double getDivisor() {
		return this.divisor;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
		
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
		
		
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}

