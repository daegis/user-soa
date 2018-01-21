package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * 
 * @author 许文轩
 * @comment 用户常用定位表
 * @date 2018年1月2日 下午5:46:52
 *
 */
public class UserLocationUsual implements Serializable {

	private static final long serialVersionUID = 7866621475552409306L;

	private Long usualLocationId;

	private Long userId;

	private Integer usualLocationType;

	private Long cityId;

	private Long destinationId;

	private java.util.Date createTime;

	private java.util.Date lastModifyTime;

	public Long getUsualLocationId() {
		return usualLocationId;
	}

	public void setUsualLocationId(Long usualLocationId) {
		this.usualLocationId = usualLocationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getUsualLocationType() {
		return usualLocationType;
	}

	public void setUsualLocationType(Integer usualLocationType) {
		this.usualLocationType = usualLocationType;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(java.util.Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
