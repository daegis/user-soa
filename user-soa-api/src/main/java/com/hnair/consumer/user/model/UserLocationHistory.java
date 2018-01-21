package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * 
 * @author 许文轩
 * @comment 用户定位历史记录表
 * @date 2018年1月2日 下午5:45:57
 *
 */
public class UserLocationHistory implements Serializable {

	private static final long serialVersionUID = 5787763585336793916L;

	private Long locationHistoryId;

	private Long userId;

	private java.util.Date locationDate;

	private Long cityId;

	private Long destinationId;

	private java.util.Date createTime;

	private Double latitude;

	private Double longtitude;

	public Long getLocationHistoryId() {
		return locationHistoryId;
	}

	public void setLocationHistoryId(Long locationHistoryId) {
		this.locationHistoryId = locationHistoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public java.util.Date getLocationDate() {
		return locationDate;
	}

	public void setLocationDate(java.util.Date locationDate) {
		this.locationDate = locationDate;
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

}
