package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditRuleRecord Entity.
 */
public class CreditRuleRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Integer ruleType;
	
	private Integer ownerType;
	
	private Long updateBy;
	
	private String updateName;
	
	private java.util.Date updateTime;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setRuleType(Integer value) {
		this.ruleType = value;
	}
	
	public Integer getRuleType() {
		return this.ruleType;
	}
		
		
	public void setOwnerType(Integer value) {
		this.ownerType = value;
	}
	
	public Integer getOwnerType() {
		return this.ownerType;
	}
		
		
	public void setUpdateBy(Long value) {
		this.updateBy = value;
	}
	
	public Long getUpdateBy() {
		return this.updateBy;
	}
		
		
	public void setUpdateName(String value) {
		this.updateName = value;
	}
	
	public String getUpdateName() {
		return this.updateName;
	}
		
		
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
		
}

