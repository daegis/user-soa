package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditPayDetail Entity.
 */
public class CreditPayDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private String businessNo;
	
	private Integer businessType;
	
	private Long payCount;
	
	private java.util.Date payTime;
	
	/**
	 * 支付状态：1-已支付；2-已退单；3-部分退
	 */
	private Integer payStatus;
	
	private String productDesc;
	
	private String tradeNo;
	

		
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
		
		
	public void setBusinessNo(String value) {
		this.businessNo = value;
	}
	
	public String getBusinessNo() {
		return this.businessNo;
	}
		
		
	public void setBusinessType(Integer value) {
		this.businessType = value;
	}
	
	public Integer getBusinessType() {
		return this.businessType;
	}
		
		
	public void setPayCount(Long value) {
		this.payCount = value;
	}
	
	public Long getPayCount() {
		return this.payCount;
	}
		
		
	public void setPayTime(java.util.Date value) {
		this.payTime = value;
	}
	
	public java.util.Date getPayTime() {
		return this.payTime;
	}
		
		
	public void setPayStatus(Integer value) {
		this.payStatus = value;
	}
	
	public Integer getPayStatus() {
		return this.payStatus;
	}
		
		
	public void setProductDesc(String value) {
		this.productDesc = value;
	}
	
	public String getProductDesc() {
		return this.productDesc;
	}
		
		
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
		
}

