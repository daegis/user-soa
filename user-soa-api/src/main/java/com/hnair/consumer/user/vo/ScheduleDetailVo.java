package com.hnair.consumer.user.vo;

import java.io.Serializable;


/**
 * ScheduleDetail Entity.
 */
public class ScheduleDetailVo implements Serializable{
	
	//列信息
	
	
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
	
	private String cabinDesc;
	
	private Integer flightTime;
	
	private Short goBack;
	
	

		
	public Short getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Short delFlag) {
		this.delFlag = delFlag;
	}

		
		
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	public String getOrderNo() {
		return this.orderNo;
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

	public String getCabinDesc() {
		return cabinDesc;
	}

	public void setCabinDesc(String cabinDesc) {
		this.cabinDesc = cabinDesc;
	}
		
		
}

