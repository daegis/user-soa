package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * Description: 第三方登录对应关系
 * All Rights Reserved.
 * @version 1.0  2016年11月5日 下午4:52:37  by 李超（li-ch3@hnair.com）创建
 */
@lombok.Setter
@lombok.Getter
public class BindPhonenumber implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	private Long userId;
	private String region;
	private String mobile;
	
	private String openId;
	
	private Integer bindType;
	
	private java.util.Date createTime;
	
	private java.util.Date lastModifyTime;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
		
		
	public void setOpenId(String value) {
		this.openId = value;
	}
	
	public String getOpenId() {
		return this.openId;
	}
		
		

		
	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
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

