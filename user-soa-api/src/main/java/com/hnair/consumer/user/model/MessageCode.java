package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * Description: 短信验证码 All Rights Reserved.
 * 
 * @version 1.0 2016年11月2日 下午2:10:57 by 李超（li-ch3@hnair.com）创建
 */
public class MessageCode implements Serializable {

	private static final long serialVersionUID = 8363168565026517573L;

	// 列信息
	private Long id;

	private Long userId;

	private String identifyCode;

	private String region;

	private String mobile;

	private Integer status;

	private java.util.Date createTime;

	private java.util.Date lastModifyTime;

	/**
	 * 验证码类型 MsgCodTypeEnum
	 */
	private Integer msgCodeType;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public void setIdentifyCode(String value) {
		this.identifyCode = value;
	}

	public String getIdentifyCode() {
		return this.identifyCode;
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

	public void setLastModifyTime(java.util.Date value) {
		this.lastModifyTime = value;
	}

	public java.util.Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public Integer getMsgCodeType() {
		return msgCodeType;
	}

	public void setMsgCodeType(Integer msgCodeType) {
		this.msgCodeType = msgCodeType;
	}

}
