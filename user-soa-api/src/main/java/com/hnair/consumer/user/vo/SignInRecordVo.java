package com.hnair.consumer.user.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 签到记录Vo
 */
@Getter
@Setter
public class SignInRecordVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long userId;
	
	private String userName;
	
	private Integer basisIntegral;
	
	private Integer awardIntegral;
	
	private Integer integral; // 每天可获得积分，页面展示用 = 基础积分+额外奖励积分
	
	private Integer signCount;
	
	private Long signInRecordId; // 签到记录ID
	
	private java.util.Date signInTime;// 时间
	
	private boolean isSign; // 是否签到

	private Integer awardType;// 礼包类型 0:没有礼包,1:抽奖活动,2:优惠券
	
	private Long luckDrawId; // 抽奖活动ID
	
	private Long couponSchemeId; // 优惠券批次ID(同上)
	
	private String userMobile; // 用户手机号
	
	private String weekday; // 星期几
	
	private Integer days; // 距离下一次领奖天数
	
	private Integer prizeStatus; // 奖品状态 0：不可领取，1：未领取，2：已领取，3：待发放，4：已发放
	
	private Integer page;
	
	private Integer pageSize;
	
	private Integer prizeRecordId;
	
	private String productName;
	
	private String orderNo;
	
	private Long signInActiveId; // 签到记录ID
	
	private Integer productType;
	
}

