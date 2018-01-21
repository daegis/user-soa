package com.hnair.consumer.user.vo;

public class CreditExchangeReqVo extends BaseJinPengReqVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String primaryKey;
	private String orderNumber;
	private String exchangeType;
	private String points;
	private String externalPoints;
	private String remark;
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getExternalPoints() {
		return externalPoints;
	}
	public void setExternalPoints(String externalPoints) {
		this.externalPoints = externalPoints;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
