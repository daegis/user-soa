package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * CreditRecommendProduct Entity.
 */
public class CreditRecommendProduct implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private String name;
	
	private String pictureUrl;
	
	private String detailUrl;
	
	private Double marketPrice;
	
	private Double price;
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setPictureUrl(String value) {
		this.pictureUrl = value;
	}
	
	public String getPictureUrl() {
		return this.pictureUrl;
	}
		
		
	public void setDetailUrl(String value) {
		this.detailUrl = value;
	}
	
	public String getDetailUrl() {
		return this.detailUrl;
	}
		
		
	public void setMarketPrice(Double value) {
		this.marketPrice = value;
	}
	
	public Double getMarketPrice() {
		return this.marketPrice;
	}
		
		
	public void setPrice(Double value) {
		this.price = value;
	}
	
	public Double getPrice() {
		return this.price;
	}
		
}

