package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * ScheduleDetail Entity.
 */
public class ScheduleDetail implements Serializable{
	
	//列信息
	private Long scheduleDetailId;
	
	private String scheduleNo;
	
	private String orderNo;
	
	private Short delFlag;
	
	private String orderSubNo;
	
	private String carrier;
	
	private String carrierName;
	
	private String dptAirport;
	
	private String dptAirportName;
	
	private String arrAirport;
	
	private String arrAirportName;
	
	private String depTerminal;
	
	private String arrTerminal;
	
	private String code;
	
	private String terminal;
	
	private String arrTime;
	
	private String dptTime;
	
	private String dptDate;
	
	private String arrDate;
	
	private String arrCity;
	
	private String arrCityName;
	
	private String departCity;
	
	private String departCityName;
	
	private Short goBack;
	
	private Integer flightTime;
	
	private String cabinDesc;
	
	private Integer flightStatus;
	
	private String checkinCounter;
	
	private String boardingGate;
	
	private java.util.Date beginDate;
	
	private java.util.Date endDate;
	
	private Integer checkinStatus;
	
	private String seatId;
	

		
	public void setScheduleDetailId(Long value) {
		this.scheduleDetailId = value;
	}
	
	public Long getScheduleDetailId() {
		return this.scheduleDetailId;
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
		
		
	public void setDelFlag(Short value) {
		this.delFlag = value;
	}
	
	public Short getDelFlag() {
		return this.delFlag;
	}
		
		
	public void setOrderSubNo(String value) {
		this.orderSubNo = value;
	}
	
	public String getOrderSubNo() {
		return this.orderSubNo;
	}
		
		
	public void setCarrier(String value) {
		this.carrier = value;
	}
	
	public String getCarrier() {
		return this.carrier;
	}
		
		
	public void setCarrierName(String value) {
		this.carrierName = value;
	}
	
	public String getCarrierName() {
		return this.carrierName;
	}
		
		
	public void setDptAirport(String value) {
		this.dptAirport = value;
	}
	
	public String getDptAirport() {
		return this.dptAirport;
	}
		
		
	public void setDptAirportName(String value) {
		this.dptAirportName = value;
	}
	
	public String getDptAirportName() {
		return this.dptAirportName;
	}
		
		
	public void setArrAirport(String value) {
		this.arrAirport = value;
	}
	
	public String getArrAirport() {
		return this.arrAirport;
	}
		
		
	public void setArrAirportName(String value) {
		this.arrAirportName = value;
	}
	
	public String getArrAirportName() {
		return this.arrAirportName;
	}
		
		
	public void setDepTerminal(String value) {
		this.depTerminal = value;
	}
	
	public String getDepTerminal() {
		return this.depTerminal;
	}
		
		
	public void setArrTerminal(String value) {
		this.arrTerminal = value;
	}
	
	public String getArrTerminal() {
		return this.arrTerminal;
	}
		
		
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
		
		
	public void setTerminal(String value) {
		this.terminal = value;
	}
	
	public String getTerminal() {
		return this.terminal;
	}
		
		
	public void setArrTime(String value) {
		this.arrTime = value;
	}
	
	public String getArrTime() {
		return this.arrTime;
	}
		
		
	public void setDptTime(String value) {
		this.dptTime = value;
	}
	
	public String getDptTime() {
		return this.dptTime;
	}
		
		
	public void setDptDate(String value) {
		this.dptDate = value;
	}
	
	public String getDptDate() {
		return this.dptDate;
	}
		
		
	public void setArrDate(String value) {
		this.arrDate = value;
	}
	
	public String getArrDate() {
		return this.arrDate;
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
		
		
	public void setGoBack(Short value) {
		this.goBack = value;
	}
	
	public Short getGoBack() {
		return this.goBack;
	}
		
		
	public void setFlightTime(Integer value) {
		this.flightTime = value;
	}
	
	public Integer getFlightTime() {
		return this.flightTime;
	}
		
		
	public void setCabinDesc(String value) {
		this.cabinDesc = value;
	}
	
	public String getCabinDesc() {
		return this.cabinDesc;
	}
		
		
	public void setFlightStatus(Integer value) {
		this.flightStatus = value;
	}
	
	public Integer getFlightStatus() {
		return this.flightStatus;
	}
		
		
	public void setCheckinCounter(String value) {
		this.checkinCounter = value;
	}
	
	public String getCheckinCounter() {
		return this.checkinCounter;
	}
		
		
	public void setBoardingGate(String value) {
		this.boardingGate = value;
	}
	
	public String getBoardingGate() {
		return this.boardingGate;
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
		
		
	public void setCheckinStatus(Integer value) {
		this.checkinStatus = value;
	}
	
	public Integer getCheckinStatus() {
		return this.checkinStatus;
	}
		
		
	public void setSeatId(String value) {
		this.seatId = value;
	}
	
	public String getSeatId() {
		return this.seatId;
	}
		
}

