package com.hnair.consumer.user.model;

import java.io.Serializable;
import java.util.Date;


/**
 * CommonIdtype Entity.
 */
public class CommonIdtype implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2162283043483441589L;

	//列信息
	private Long id;
	
	private Long contactsId;
	
	private Long userId;
	
	private String type;
	
	private String idNo;
	
	private String typeName;
	
	private  Date termOfValidity;
	
	private java.util.Date createTime;
	
	private java.util.Date lastModifyTime;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setContactsId(Long value) {
		this.contactsId = value;
	}
	
	public Long getContactsId() {
		return this.contactsId;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setType(String value) {
		this.type = value;
	}
	
	public String getType() {
		return this.type;
	}
		
		
	public void setIdNo(String value) {
		this.idNo = value;
	}
	
	public String getIdNo() {
		return this.idNo;
	}
		
		
	public void setTypeName(String value) {
		this.typeName = value;
	}
	
	public String getTypeName() {
		return this.typeName;
	}

	public Date getTermOfValidity() {
		return termOfValidity;
	}

	public void setTermOfValidity(Date termOfValidity) {
		this.termOfValidity = termOfValidity;
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

