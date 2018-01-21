package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * Description: 关注
 * All Rights Reserved.
 * @version 1.0  2016年11月17日 下午2:15:10  by 李超（li-ch3@hnair.com）创建
 */
public class Attention implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long attentionId;
	
	private Long userId;
	
	private Long attentionUserId;
	
	private java.util.Date createTime;
	
	private Integer isMutualAttention;
	
	
		
	public Integer getIsMutualAttention() {
		return isMutualAttention;
	}

	public void setIsMutualAttention(Integer isMutualAttention) {
		this.isMutualAttention = isMutualAttention;
	}

	public void setAttentionId(Long value) {
		this.attentionId = value;
	}
	
	public Long getAttentionId() {
		return this.attentionId;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setAttentionUserId(Long value) {
		this.attentionUserId = value;
	}
	
	public Long getAttentionUserId() {
		return this.attentionUserId;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
}

