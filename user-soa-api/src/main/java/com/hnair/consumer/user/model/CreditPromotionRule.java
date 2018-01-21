package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditPromotionRule Entity.
 */
public class CreditPromotionRule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private java.util.Date startDate;
	
	private java.util.Date endDate;
	
	private Integer taskType;
	
	private Double calRule;
	
	/**
	 * 计算规则类型:1-固定额；2-比例
	 */
	private Integer calType;
	
	private String promotionDes;
	
	private Integer status;
	
	private java.util.Date createTime;
	
	private Long createUserId;
	
	private java.util.Date updateTime;
	
	private Long updateUserId;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
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
		
		
	public void setTaskType(Integer value) {
		this.taskType = value;
	}
	
	public Integer getTaskType() {
		return this.taskType;
	}
		
		
	public void setCalRule(Double value) {
		this.calRule = value;
	}
	
	public Double getCalRule() {
		return this.calRule;
	}
		
	/**
	 * 计算规则类型:1-固定额；2-比例
	 */
	public void setCalType(Integer value) {
		this.calType = value;
	}
	
	/**
	 * 计算规则类型:1-固定额；2-比例
	 */
	public Integer getCalType() {
		return this.calType;
	}
		
		
	public void setPromotionDes(String value) {
		this.promotionDes = value;
	}
	
	public String getPromotionDes() {
		return this.promotionDes;
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
		
		
	public void setCreateUserId(Long value) {
		this.createUserId = value;
	}
	
	public Long getCreateUserId() {
		return this.createUserId;
	}
		
		
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
		
		
	public void setUpdateUserId(Long value) {
		this.updateUserId = value;
	}
	
	public Long getUpdateUserId() {
		return this.updateUserId;
	}
		
}

