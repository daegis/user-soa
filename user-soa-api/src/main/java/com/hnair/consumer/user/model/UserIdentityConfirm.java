package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * UserIdentityConfirm Entity.
 */
public class UserIdentityConfirm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1694055305078559032L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private String name;
	
	private String identityCard;
	
	private String phone;
	
	private Short ifConfirm;
	
	private String photoUrl;
	
	private String creator;
	
	private java.util.Date creationDate;
	
	private String modifier;
	
	private java.util.Date lastModifiedDate;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setIdentityCard(String value) {
		this.identityCard = value;
	}
	
	public String getIdentityCard() {
		return this.identityCard;
	}
		
		
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
		
		
	public void setIfConfirm(Short value) {
		this.ifConfirm = value;
	}
	
	public Short getIfConfirm() {
		return this.ifConfirm;
	}
		
		
	public void setPhotoUrl(String value) {
		this.photoUrl = value;
	}
	
	public String getPhotoUrl() {
		return this.photoUrl;
	}
		
		
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
		
		
	public void setCreationDate(java.util.Date value) {
		this.creationDate = value;
	}
	
	public java.util.Date getCreationDate() {
		return this.creationDate;
	}
		
		
	public void setModifier(String value) {
		this.modifier = value;
	}
	
	public String getModifier() {
		return this.modifier;
	}
		
		
	public void setLastModifiedDate(java.util.Date value) {
		this.lastModifiedDate = value;
	}
	
	public java.util.Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}
		
}

