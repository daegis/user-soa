package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditRule Entity.
 */
public class CreditRule implements Serializable{
	
	//列信息
	private Long id;
	
	private Integer taskType;
	
	private String taskName;
	
	private Integer calculateType;
	
	private Double calculateRule;
	
	private String ruleDes;
	
	private Integer status;
	
	private java.util.Date endTime;
	
	private java.util.Date startTime;

	private String endTimeStr;

	private String startTimeStr;

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	private Long createUserId;
	
	private java.util.Date createTime;
	
	private Long updateUserId;
	
	private java.util.Date updateTime;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setTaskType(Integer value) {
		this.taskType = value;
	}
	
	public Integer getTaskType() {
		return this.taskType;
	}
		
		
	public void setTaskName(String value) {
		this.taskName = value;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
		
		
	public void setCalculateType(Integer value) {
		this.calculateType = value;
	}
	
	public Integer getCalculateType() {
		return this.calculateType;
	}
		
		
	public void setCalculateRule(Double value) {
		this.calculateRule = value;
	}
	
	public Double getCalculateRule() {
		return this.calculateRule;
	}
		
		
	public void setRuleDes(String value) {
		this.ruleDes = value;
	}
	
	public String getRuleDes() {
		return this.ruleDes;
	}
		
		
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
		
		
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
		
		
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
		
		
	public void setCreateUserId(Long value) {
		this.createUserId = value;
	}
	
	public Long getCreateUserId() {
		return this.createUserId;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
		
	public void setUpdateUserId(Long value) {
		this.updateUserId = value;
	}
	
	public Long getUpdateUserId() {
		return this.updateUserId;
	}
		
		
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
		
}

