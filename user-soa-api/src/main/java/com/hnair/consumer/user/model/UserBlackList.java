package com.hnair.consumer.user.model;

import java.io.Serializable;

public class UserBlackList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 342042165522199371L;

	// 列信息
	private Long blackListId;

	private Long userId;

	private Long banUserId;

	private Integer isDel;

	private java.util.Date createTime;

	private java.util.Date lastModifyTime;

	/**
	 * vo字段
	 */
	private String banUserName;

	/**
	 * vo字段
	 */
	private String banUserImgUrl;

	public void setBlackListId(Long value) {
		this.blackListId = value;
	}

	public Long getBlackListId() {
		return this.blackListId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setBanUserId(Long value) {
		this.banUserId = value;
	}

	public Long getBanUserId() {
		return this.banUserId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
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

	public String getBanUserName() {
		return banUserName;
	}

	public void setBanUserName(String banUserName) {
		this.banUserName = banUserName;
	}

	public String getBanUserImgUrl() {
		return banUserImgUrl;
	}

	public void setBanUserImgUrl(String banUserImgUrl) {
		this.banUserImgUrl = banUserImgUrl;
	}

	@Override
	public String toString() {
		return "UserBlackList [blackListId=" + blackListId + ", userId=" + userId + ", banUserId=" + banUserId
				+ ", isDel=" + isDel + ", createTime=" + createTime + ", lastModifyTime=" + lastModifyTime + "]";
	}

}
