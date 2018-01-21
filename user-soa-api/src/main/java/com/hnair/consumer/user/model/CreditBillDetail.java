package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditBillDetail Entity.
 */
public class CreditBillDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private Integer taskType;
	
	private String businessNo;
	
	private Double payAmount;
	
	private Long creditCount;
	
	private Long balanceCount;
	
	private Integer creditType;
	
	private String description;
	
	private Integer status;
	
	private java.util.Date createTime;
	
	private java.util.Date expireTime;
	
	private String createTimeText;
	
	private String icon;

		
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
		
		
	public void setTaskType(Integer value) {
		this.taskType = value;
	}
	
	public Integer getTaskType() {
		return this.taskType;
	}
		
		
	public void setBusinessNo(String value) {
		this.businessNo = value;
	}
	
	public String getBusinessNo() {
		return this.businessNo;
	}
		
		
	public void setPayAmount(Double value) {
		this.payAmount = value;
	}
	
	public Double getPayAmount() {
		return this.payAmount;
	}
		
		
	public void setCreditCount(Long value) {
		this.creditCount = value;
	}
	
	public Long getCreditCount() {
		return this.creditCount;
	}
		
		
	public void setBalanceCount(Long value) {
		this.balanceCount = value;
	}
	
	public Long getBalanceCount() {
		return this.balanceCount;
	}
		
		
	public void setCreditType(Integer value) {
		this.creditType = value;
	}
	
	public Integer getCreditType() {
		return this.creditType;
	}
		
		
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
		
	public void setExpireTime(java.util.Date value) {
		this.expireTime = value;
	}
	
	public java.util.Date getExpireTime() {
		return this.expireTime;
	}

	public String getCreateTimeText() {
		return createTimeText;
	}

	public void setCreateTimeText(String createTimeText) {
		this.createTimeText = createTimeText;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
		
	
}

