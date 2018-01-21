package com.hnair.consumer.user.vo;

public class UserBindingRespVo extends BaseJinPengRespVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String remainingLoginFailNum;
	private String hnaRealNameStatus;
	private String accountBalance;
	public String getRemainingLoginFailNum() {
		return remainingLoginFailNum;
	}
	public void setRemainingLoginFailNum(String remainingLoginFailNum) {
		this.remainingLoginFailNum = remainingLoginFailNum;
	}
	public String getHnaRealNameStatus() {
		return hnaRealNameStatus;
	}
	public void setHnaRealNameStatus(String hnaRealNameStatus) {
		this.hnaRealNameStatus = hnaRealNameStatus;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
}
