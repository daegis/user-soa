package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * 
 * @author 许文轩
 * @comment 用户浏览历史
 * @date 2017年9月4日 下午7:39:39
 *
 */
public class UserBrowseHistory implements Serializable {

	private static final long serialVersionUID = -2230766569199754126L;

	private Long browseId;

	private Long userId;
	/**
	 * 浏览类型 BrowseTypeEnum
	 */
	private Integer browseType;

	/**
	 * 目标id
	 */
	private Long targetId;

	/**
	 * 目标类型
	 */
	private String targetType;

	private String title;

	private String remark;

	private String imgPath;

	private Double price;

	private java.util.Date startDate;

	private java.util.Date endDate;

	private String departure;

	private String destination;

	private java.util.Date createDate;

	private java.util.Date lastModifyDate;
	private Integer isDel;

	public Long getBrowseId() {
		return browseId;
	}

	public void setBrowseId(Long browseId) {
		this.browseId = browseId;
	}

	public Integer getBrowseType() {
		return browseType;
	}

	public void setBrowseType(Integer browseType) {
		this.browseType = browseType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(java.util.Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

}
