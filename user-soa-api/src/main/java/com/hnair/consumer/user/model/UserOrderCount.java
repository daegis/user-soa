package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * UserOrderCount Entity.
 */
public class UserOrderCount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4843634270497875474L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private Integer flightOrderCount;
	
	private Integer hotelOrderCount;
	
	private Integer tourismOrderCount;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setFlightOrderCount(Integer value) {
		this.flightOrderCount = value;
	}
	
	public Integer getFlightOrderCount() {
		return this.flightOrderCount;
	}
		
		
	public void setHotelOrderCount(Integer value) {
		this.hotelOrderCount = value;
	}
	
	public Integer getHotelOrderCount() {
		return this.hotelOrderCount;
	}
		
		
	public void setTourismOrderCount(Integer value) {
		this.tourismOrderCount = value;
	}
	
	public Integer getTourismOrderCount() {
		return this.tourismOrderCount;
	}
		
}

