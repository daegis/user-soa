package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * HimiChatRecord Entity. Description: HIMI聊天记录 All Rights Reserved.
 * 
 * @version 1.0 2017年10月17日 下午2:10:57 by 张慧东（huid.zhang@haiyangyun.com）创建
 */
public class HimiChatRecord implements Serializable {

	private static final long serialVersionUID = 8363168565026517573L;
	// 列信息
	private Long id;

	private String uid;

	private Long userId;

	private Integer userFlag;

	private String content;

	private java.util.Date createTime;

	private Integer isDelete;

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setUid(String value) {
		this.uid = value;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserFlag(Integer value) {
		this.userFlag = value;
	}

	public Integer getUserFlag() {
		return this.userFlag;
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

	public void setIsDelete(Integer value) {
		this.isDelete = value;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}
}
