package com.hnair.consumer.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * Description: 用户反馈
 * All Rights Reserved.
 * @version 1.0  2016年11月17日 下午7:58:54  by 李超（li-ch3@hnair.com）创建
 */
@Getter
@Setter
public class UserFeedback implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private String content;
	
	private String contact;


	private String userName;


	private Date createTime;

	//建议类型
	private String type;

	private String phoneBrand;

	private String phoneModel;

	private String systemOs;

	private String appVersion;

	private String deviceType;

	private Integer status;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
		
		
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
}

