package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditConversionDetail Entity.
 */
public class CreditConversionDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private Integer partnerId;
	
	private Long billId;
	
	private Integer type;
	
	private Long hiappCount;
	
	private Long partnerCount;
	
	private java.util.Date createTime;

	private String tradeNo;

	private String partnerDesc;
	
	public String getPartnerDesc() {
		return partnerDesc;
	}

	public void setPartnerDesc(String partnerDesc) {
		this.partnerDesc = partnerDesc;
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
		
		
	public void setBillId(Long value) {
		this.billId = value;
	}
	
	public Long getBillId() {
		return this.billId;
	}
		
		
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
		
		
	public void setHiappCount(Long value) {
		this.hiappCount = value;
	}
	
	public Long getHiappCount() {
		return this.hiappCount;
	}
		
		
	public void setPartnerCount(Long value) {
		this.partnerCount = value;
	}
	
	public Long getPartnerCount() {
		return this.partnerCount;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
		
}

