package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * ScheduleMtsConfig Entity.
 */
public class ScheduleMtsConfig implements Serializable{
	
	//列信息
	private Integer id;
	
	private Integer rid;
	
	private String carrier;
	
	private String carrierName;
	
	private String deptCityName;
	
	private String deptAirportName;
	
	private String deptAirportTerminal;
	
	private String deptCountry;
	
	private String arrCityName;
	
	private String arrAirportName;
	
	private String arrAirportTerminal;
	
	private String arrCountry;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setRid(Integer value) {
		this.rid = value;
	}
	
	public Integer getRid() {
		return this.rid;
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
		
		
	public void setDeptCityName(String value) {
		this.deptCityName = value;
	}
	
	public String getDeptCityName() {
		return this.deptCityName;
	}
		
		
	public void setDeptAirportName(String value) {
		this.deptAirportName = value;
	}
	
	public String getDeptAirportName() {
		return this.deptAirportName;
	}
		
		
	public void setDeptAirportTerminal(String value) {
		this.deptAirportTerminal = value;
	}
	
	public String getDeptAirportTerminal() {
		return this.deptAirportTerminal;
	}
		
		
	public void setDeptCountry(String value) {
		this.deptCountry = value;
	}
	
	public String getDeptCountry() {
		return this.deptCountry;
	}
		
		
	public void setArrCityName(String value) {
		this.arrCityName = value;
	}
	
	public String getArrCityName() {
		return this.arrCityName;
	}
		
		
	public void setArrAirportName(String value) {
		this.arrAirportName = value;
	}
	
	public String getArrAirportName() {
		return this.arrAirportName;
	}
		
		
	public void setArrAirportTerminal(String value) {
		this.arrAirportTerminal = value;
	}
	
	public String getArrAirportTerminal() {
		return this.arrAirportTerminal;
	}
		
		
	public void setArrCountry(String value) {
		this.arrCountry = value;
	}
	
	public String getArrCountry() {
		return this.arrCountry;
	}
		
}

