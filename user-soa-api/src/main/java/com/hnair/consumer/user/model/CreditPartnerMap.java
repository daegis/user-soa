package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditPartnerMap Entity.
 */
public class CreditPartnerMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private Integer partnerId;
	
	private Long balanceCount;
	
	private java.util.Date createTime;
	
	private java.util.Date updateTime;

	private String partnerName;


	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setPartnerId(Integer value) {
		this.partnerId = value;
	}
	
	public Integer getPartnerId() {
		return this.partnerId;
	}
		
		
	public void setBalanceCount(Long value) {
		this.balanceCount = value;
	}
	
	public Long getBalanceCount() {
		return this.balanceCount;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
		
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
		
}

