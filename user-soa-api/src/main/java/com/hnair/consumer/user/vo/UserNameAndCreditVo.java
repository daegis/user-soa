package com.hnair.consumer.user.vo;

import java.io.Serializable;

public class UserNameAndCreditVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2093294884654192457L;
	private String userName;
	private Long donateCredit;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getDonateCredit() {
		return donateCredit;
	}
	public void setDonateCredit(Long donateCredit) {
		this.donateCredit = donateCredit;
	}
	
}
