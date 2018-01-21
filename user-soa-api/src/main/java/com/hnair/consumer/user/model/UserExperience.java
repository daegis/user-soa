package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * UserExperience Entity.
 */
public class UserExperience implements Serializable{
	
	//列信息
	private Long id;
	
	private Long userId;
	
	private Integer changeNum;
	
	private Integer changeType;
	
	private String changeFrom;
	
	private String changeFromNo;
	
	private String changeRelationNo;
	
	private String changeDesc;
	
	private Integer deviceType;
	
	private java.util.Date createTime;
	
	private java.util.Date lastModifyTime;
	

		
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
		
		
	public void setChangeNum(Integer value) {
		this.changeNum = value;
	}
	
	public Integer getChangeNum() {
		return this.changeNum;
	}
		
		
	public void setChangeType(Integer value) {
		this.changeType = value;
	}
	
	public Integer getChangeType() {
		return this.changeType;
	}
		
		
	public void setChangeFrom(String value) {
		this.changeFrom = value;
	}
	
	public String getChangeFrom() {
		return this.changeFrom;
	}
		
		
	public void setChangeFromNo(String value) {
		this.changeFromNo = value;
	}
	
	public String getChangeFromNo() {
		return this.changeFromNo;
	}
		
		
	public void setChangeRelationNo(String value) {
		this.changeRelationNo = value;
	}
	
	public String getChangeRelationNo() {
		return this.changeRelationNo;
	}
		
		
	public void setChangeDesc(String value) {
		this.changeDesc = value;
	}
	
	public String getChangeDesc() {
		return this.changeDesc;
	}
		
		
	public void setDeviceType(Integer value) {
		this.deviceType = value;
	}
	
	public Integer getDeviceType() {
		return this.deviceType;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
		
	public void setLastModifyTime(java.util.Date value) {
		this.lastModifyTime = value;
	}
	
	public java.util.Date getLastModifyTime() {
		return this.lastModifyTime;
	}
		
}

