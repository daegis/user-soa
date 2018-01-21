package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CommonContacts Entity.
 */
public class CommonContacts implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7031151925503748119L;

		//列信息
		private Long id;
		
		private String contactsName;
		
		private Integer sortIdentifier;
		
		private String phone;
		
		private Integer gender;
		
		private String familyName;
		
		private String firstName;
		
		private Integer isChildren;
		
		private java.util.Date dateOfBirth;
		
		private Integer isOneself;
		
		private String nationality;
		
		private String email;
		
		private Long userId;
		
		private java.util.Date createTime;
		
		private java.util.Date lastModifyTime;
		
		private Integer isDelete;
		
		private String phonePrefix;
		
		private String phoneCountryName;
		
	    private  String emailCopy;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setContactsName(String value) {
		this.contactsName = value;
	}
	
	public String getContactsName() {
		return this.contactsName;
	}
		
	
	
		
	public Integer getSortIdentifier() {
		return sortIdentifier;
	}

	public void setSortIdentifier(Integer sortIdentifier) {
		this.sortIdentifier = sortIdentifier;
	}

	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
		
		
	public void setGender(Integer value) {
		this.gender = value;
	}
	
	public Integer getGender() {
		return this.gender;
	}
		
		
	public void setFamilyName(String value) {
		this.familyName = value;
	}
	
	public String getFamilyName() {
		return this.familyName;
	}
		
		
	public void setFirstName(String value) {
		this.firstName = value;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
		
		
	public void setIsChildren(Integer value) {
		this.isChildren = value;
	}
	
	public Integer getIsChildren() {
		return this.isChildren;
	}
		
		
	public void setDateOfBirth(java.util.Date value) {
		this.dateOfBirth = value;
	}
	
	public java.util.Date getDateOfBirth() {
		return this.dateOfBirth;
	}
		
		
	public void setIsOneself(Integer value) {
		this.isOneself = value;
	}
	
	public Integer getIsOneself() {
		return this.isOneself;
	}
		
		
	public void setNationality(String value) {
		this.nationality = value;
	}
	
	public String getNationality() {
		return this.nationality;
	}
		
		
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return this.email;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
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
		
		
	public void setIsDelete(Integer value) {
		this.isDelete = value;
	}
	
	public Integer getIsDelete() {
		return this.isDelete;
	}

	public String getPhonePrefix() {
		return phonePrefix;
	}

	public void setPhonePrefix(String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}

	public String getPhoneCountryName() {
		return phoneCountryName;
	}

	public void setPhoneCountryName(String phoneCountryName) {
		this.phoneCountryName = phoneCountryName;
	}

	public String getEmailCopy() {
		return emailCopy;
	}

	public void setEmailCopy(String emailCopy) {
		this.emailCopy = emailCopy;
	}
	
	
		
}

