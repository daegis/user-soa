package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * Customers Entity.
 */
public class Customers implements Serializable{
	
	//列信息
	private Integer cid;
	
	private String address;
	
	private String cnote;
	
	private java.util.Date firstAdd;
	
	private String gender;
	
	private String idNumber;
	
	private String inUse;
	
	private String mobile;
	
	private String name;
	
	private String nickname;
	
	private String special;
	

		
	public void setCid(Integer value) {
		this.cid = value;
	}
	
	public Integer getCid() {
		return this.cid;
	}
		
		
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
		
		
	public void setCnote(String value) {
		this.cnote = value;
	}
	
	public String getCnote() {
		return this.cnote;
	}
		
		
	public void setFirstAdd(java.util.Date value) {
		this.firstAdd = value;
	}
	
	public java.util.Date getFirstAdd() {
		return this.firstAdd;
	}
		
		
	public void setGender(String value) {
		this.gender = value;
	}
	
	public String getGender() {
		return this.gender;
	}
		
		
	public void setIdNumber(String value) {
		this.idNumber = value;
	}
	
	public String getIdNumber() {
		return this.idNumber;
	}
		
		
	public void setInUse(String value) {
		this.inUse = value;
	}
	
	public String getInUse() {
		return this.inUse;
	}
		
		
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setNickname(String value) {
		this.nickname = value;
	}
	
	public String getNickname() {
		return this.nickname;
	}
		
		
	public void setSpecial(String value) {
		this.special = value;
	}
	
	public String getSpecial() {
		return this.special;
	}
		
}

