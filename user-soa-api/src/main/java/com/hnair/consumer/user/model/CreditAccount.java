package com.hnair.consumer.user.model;

import java.io.Serializable;
import java.util.Date;


/**
 * CreditAccount Entity.
 */
public class CreditAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private Long creditsAvailable;
	
	private String mobile;
	
	private Long creditsIncome;
	
	private Long creditsExpend;
	
	private java.util.Date createTime;
	
	private java.util.Date updateTime;
	
	private Integer status;
	
	private Date expireTime;
	
	private String userName;
	

		
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
		
		
	public void setCreditsAvailable(Long value) {
		this.creditsAvailable = value;
	}
	
	public Long getCreditsAvailable() {
		return this.creditsAvailable;
	}
		
		
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
		
		
	public void setCreditsIncome(Long value) {
		this.creditsIncome = value;
	}
	
	public Long getCreditsIncome() {
		return this.creditsIncome;
	}
		
		
	public void setCreditsExpend(Long value) {
		this.creditsExpend = value;
	}
	
	public Long getCreditsExpend() {
		return this.creditsExpend;
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
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
		
}

