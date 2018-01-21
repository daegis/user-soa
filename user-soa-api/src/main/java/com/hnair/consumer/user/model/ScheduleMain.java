package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * ScheduleMain Entity.
 */
public class ScheduleMain implements Serializable{
	
	//列信息
	private Long scheduleId;
	
	private String scheduleNo;
	
	private String orderNo;
	
	private String scheduleType;
	
	private Short delFlag;
	
	private Long userId;
	
	private String userName;
	
	private String productName;
	
	private Long productId;
	
	private Short airLineType;
	
	private Short tripType;
	
	private Short goBack;
	
	private String arrCity;
	
	private String arrCityName;
	
	private String departCity;
	
	private String departCityName;
	
	private java.util.Date beginDate;
	
	private java.util.Date endDate;
	
	private String destCountry;
	
	private String destCity;
	
	private String refOrderNo;
	
	private String scheduleSecondType;
	

		
	public void setScheduleId(Long value) {
		this.scheduleId = value;
	}
	
	public Long getScheduleId() {
		return this.scheduleId;
	}
		
		
	public void setScheduleNo(String value) {
		this.scheduleNo = value;
	}
	
	public String getScheduleNo() {
		return this.scheduleNo;
	}
		
		
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
		
		
	public void setScheduleType(String value) {
		this.scheduleType = value;
	}
	
	public String getScheduleType() {
		return this.scheduleType;
	}
		
		
	public void setDelFlag(Short value) {
		this.delFlag = value;
	}
	
	public Short getDelFlag() {
		return this.delFlag;
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
		
		
	public void setProductName(String value) {
		this.productName = value;
	}
	
	public String getProductName() {
		return this.productName;
	}
		
		
	public void setProductId(Long value) {
		this.productId = value;
	}
	
	public Long getProductId() {
		return this.productId;
	}
		
		
	public void setAirLineType(Short value) {
		this.airLineType = value;
	}
	
	public Short getAirLineType() {
		return this.airLineType;
	}
		
		
	public void setTripType(Short value) {
		this.tripType = value;
	}
	
	public Short getTripType() {
		return this.tripType;
	}
		
		
	public void setGoBack(Short value) {
		this.goBack = value;
	}
	
	public Short getGoBack() {
		return this.goBack;
	}
		
		
	public void setArrCity(String value) {
		this.arrCity = value;
	}
	
	public String getArrCity() {
		return this.arrCity;
	}
		
		
	public void setArrCityName(String value) {
		this.arrCityName = value;
	}
	
	public String getArrCityName() {
		return this.arrCityName;
	}
		
		
	public void setDepartCity(String value) {
		this.departCity = value;
	}
	
	public String getDepartCity() {
		return this.departCity;
	}
		
		
	public void setDepartCityName(String value) {
		this.departCityName = value;
	}
	
	public String getDepartCityName() {
		return this.departCityName;
	}
		
		
	public void setBeginDate(java.util.Date value) {
		this.beginDate = value;
	}
	
	public java.util.Date getBeginDate() {
		return this.beginDate;
	}
		
		
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
		
		
	public void setDestCountry(String value) {
		this.destCountry = value;
	}
	
	public String getDestCountry() {
		return this.destCountry;
	}
		
		
	public void setDestCity(String value) {
		this.destCity = value;
	}
	
	public String getDestCity() {
		return this.destCity;
	}
		
		
	public void setRefOrderNo(String value) {
		this.refOrderNo = value;
	}
	
	public String getRefOrderNo() {
		return this.refOrderNo;
	}
		
		
	public void setScheduleSecondType(String value) {
		this.scheduleSecondType = value;
	}
	
	public String getScheduleSecondType() {
		return this.scheduleSecondType;
	}
		
}

