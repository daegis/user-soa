package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * NewPayBackInfo Entity.
 */
public class NewPayBackInfo implements Serializable{
	
	//列信息
	private Long id;
	
	private Long identityConfirmId;
	
	private String orderId;
	
	private String submitTime;
	
	private Short payType;
	
	private String realName;
	
	private String idNumber;
	
	private String noticeUrl;
	
	private String partnerId;
	
	private String remark;
	
	private String charset;
	
	private String resultCode;
	
	private String acceptTime;
	
	private String orderNo;
	
	private String photo;
	
	private String feeAmt;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setIdentityConfirmId(Long value) {
		this.identityConfirmId = value;
	}
	
	public Long getIdentityConfirmId() {
		return this.identityConfirmId;
	}
		
		
	public void setOrderId(String value) {
		this.orderId = value;
	}
	
	public String getOrderId() {
		return this.orderId;
	}
		
		
	public void setSubmitTime(String value) {
		this.submitTime = value;
	}
	
	public String getSubmitTime() {
		return this.submitTime;
	}
		
		
	public void setPayType(Short value) {
		this.payType = value;
	}
	
	public Short getPayType() {
		return this.payType;
	}
		
		
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
		
		
	public void setIdNumber(String value) {
		this.idNumber = value;
	}
	
	public String getIdNumber() {
		return this.idNumber;
	}
		
		
	public void setNoticeUrl(String value) {
		this.noticeUrl = value;
	}
	
	public String getNoticeUrl() {
		return this.noticeUrl;
	}
		
		
	public void setPartnerId(String value) {
		this.partnerId = value;
	}
	
	public String getPartnerId() {
		return this.partnerId;
	}
		
		
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
		
		
	public void setCharset(String value) {
		this.charset = value;
	}
	
	public String getCharset() {
		return this.charset;
	}
		
		
	public void setResultCode(String value) {
		this.resultCode = value;
	}
	
	public String getResultCode() {
		return this.resultCode;
	}
		
		
	public void setAcceptTime(String value) {
		this.acceptTime = value;
	}
	
	public String getAcceptTime() {
		return this.acceptTime;
	}
		
		
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
		
		
	public void setPhoto(String value) {
		this.photo = value;
	}
	
	public String getPhoto() {
		return this.photo;
	}
		
		
	public void setFeeAmt(String value) {
		this.feeAmt = value;
	}
	
	public String getFeeAmt() {
		return this.feeAmt;
	}
		
}

