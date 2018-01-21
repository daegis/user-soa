package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditBillModify Entity.
 */
public class CreditBillModify implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long creditBillId;
	
	private Long userId;
	
	private Long oldCreditCount;
	
	private Long newCreditCount;
	
	private String description;
	
	private Long createBy;
	
	private String createName;
	
	private java.util.Date createTime;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setCreditBillId(Long value) {
		this.creditBillId = value;
	}
	
	public Long getCreditBillId() {
		return this.creditBillId;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setOldCreditCount(Long value) {
		this.oldCreditCount = value;
	}
	
	public Long getOldCreditCount() {
		return this.oldCreditCount;
	}
		
		
	public void setNewCreditCount(Long value) {
		this.newCreditCount = value;
	}
	
	public Long getNewCreditCount() {
		return this.newCreditCount;
	}
		
		
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
		
		
	public void setCreateBy(Long value) {
		this.createBy = value;
	}
	
	public Long getCreateBy() {
		return this.createBy;
	}
		
		
	public void setCreateName(String value) {
		this.createName = value;
	}
	
	public String getCreateName() {
		return this.createName;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
}

