package com.hnair.consumer.user.vo;

import java.io.Serializable;


/**
 * ScheduleDetail Entity.
 */
public class ScheduleReqVo implements Serializable{
	
	//列信息
	
	
	private String orderNo;	
		
	private String orderSubNo;
	
    private Integer checkinStatus;
	
	private String seatId;
	
	
		
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
		
		
		
	public void setOrderSubNo(String value) {
		this.orderSubNo = value;
	}
	
	public String getOrderSubNo() {
		return this.orderSubNo;
	}
		
		
	public void setCarrier(String value) {
	}

	public Integer getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(Integer checkinStatus) {
		this.checkinStatus = checkinStatus;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
		
}

