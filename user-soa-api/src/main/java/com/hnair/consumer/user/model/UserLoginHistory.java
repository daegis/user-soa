package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * Description: 登录历史
 * All Rights Reserved.
 * @version 1.0  2016年11月4日 下午12:14:10  by 李超（li-ch3@hnair.com）创建
 */
public class UserLoginHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long loginLogId;
	
	private Long userId;
	
	private String userName;
	
	private String region;
	
	private String mobile;
	
	private String deviceType;
	
	private String deviceId;
	
	
	private Integer loginType;
	
	private java.util.Date createTime;
	
	private String ip;
	
	private String mac;
	

		
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setLoginLogId(Long value) {
		this.loginLogId = value;
	}
	
	public Long getLoginLogId() {
		return this.loginLogId;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
		
		
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
		
		
	public void setDeviceType(String value) {
		this.deviceType = value;
	}
	
	public String getDeviceType() {
		return this.deviceType;
	}
		
		
	public void setDeviceId(String value) {
		this.deviceId = value;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
		
	
	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
		
		
	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}
		
}

