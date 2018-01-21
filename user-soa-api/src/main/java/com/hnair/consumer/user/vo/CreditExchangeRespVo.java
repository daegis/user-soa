package com.hnair.consumer.user.vo;

public class CreditExchangeRespVo extends BaseJinPengRespVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ffptid;
	private String subStatus;
	private String orderNumber;
	public String getFfptid() {
		return ffptid;
	}
	public void setFfptid(String ffptid) {
		this.ffptid = ffptid;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

}
