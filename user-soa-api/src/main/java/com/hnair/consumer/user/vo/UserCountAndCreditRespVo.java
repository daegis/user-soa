package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserCountAndCreditRespVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3879045129605689757L;
	
	private Integer userCount;
	private Long creditCount;
	private Long creditBalance;
	private List<UserNameAndCreditVo> userNameAndCredits = new ArrayList<>();
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	public Long getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(Long creditCount) {
		this.creditCount = creditCount;
	}
	public List<UserNameAndCreditVo> getUserNameAndCredits() {
		return userNameAndCredits;
	}
	public void setUserNameAndCredits(List<UserNameAndCreditVo> userNameAndCredits) {
		this.userNameAndCredits = userNameAndCredits;
	}
	public Long getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(Long creditBalance) {
		this.creditBalance = creditBalance;
	}
	
}
