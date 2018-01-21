package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * SignInAwardRule Entity.
 */
public class SignInAwardRule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long signInActiveId; // 签到活动Id
	
	private Integer signInDays; // 连续签到天数
	
	private Integer awardIntegral; // 额外奖励积分
	
	private Integer specialAwardType; // 特殊奖励类型  0:无特殊奖励,1:抽奖活动,2:优惠券
	
	private Long luckyDrawId; // 抽奖活动ID
	
	private Long couponSchemeId; // 优惠券批次
	

		
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
		
		
	public void setSignInActiveId(Long value) {
		this.signInActiveId = value;
	}
	
	public Long getSignInActiveId() {
		return this.signInActiveId;
	}
		
		
	public void setSignInDays(Integer value) {
		this.signInDays = value;
	}
	
	public Integer getSignInDays() {
		return this.signInDays;
	}
		
		
	public void setAwardIntegral(Integer value) {
		this.awardIntegral = value;
	}
	
	public Integer getAwardIntegral() {
		return this.awardIntegral;
	}
		
		
	public void setSpecialAwardType(Integer value) {
		this.specialAwardType = value;
	}
	
	public Integer getSpecialAwardType() {
		return this.specialAwardType;
	}
		
		
	public void setLuckyDrawId(Long value) {
		this.luckyDrawId = value;
	}
	
	public Long getLuckyDrawId() {
		return this.luckyDrawId;
	}
		
		
	public void setCouponSchemeId(Long value) {
		this.couponSchemeId = value;
	}
	
	public Long getCouponSchemeId() {
		return this.couponSchemeId;
	}
		
}

