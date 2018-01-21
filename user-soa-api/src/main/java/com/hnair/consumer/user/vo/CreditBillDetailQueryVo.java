package com.hnair.consumer.user.vo;

import java.io.Serializable;

public class CreditBillDetailQueryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private String startTime;
	
	private String endTime;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
