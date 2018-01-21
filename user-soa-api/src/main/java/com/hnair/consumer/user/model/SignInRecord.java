package com.hnair.consumer.user.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * 签到记录
 */
@Getter
@Setter
public class SignInRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	// 签到时间
	private java.util.Date signInTime;
	
	private Long userId;
	
	private String userName;
	
	private String userMobile; // 用户手机号
	
	// 基础积分
	private Integer basisIntegral;
	
	//额外奖励积分
	private Integer awardIntegral;
	
	// 连续签到天数
	private Integer signCount;
	
	// 中奖纪录Id
	private Long prizeRecordId;
	
	// 签到活动ID
	private Long signInActiveId;
		
}

