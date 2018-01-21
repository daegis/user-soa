package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * 签到信息
 */
@Getter
@Setter
public class SignInVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private boolean sign; // 是否已签到false:未签到,true:已签到
	
	private Long integralBalance; // 积分余额
	
	private Long userId; // 用户编号
	
	private Integer signInCount; // 当前周期连续签到天数
	
	private Integer totalSignInCount; // 总连续签到天数
	
	private List<SignInRecordVo> signInRecordVoList; // 连续签到日期
	
	private Date lastSignDate; // 最后(上次)签到日期
	
	private SignInActiveVo signInActive; // 签到活动
	
	private List<SignInPrizeVo> signInPrizeVoList; // 签到活动关联抽奖奖品列表
	
	private Long luckDrawId; // 抽奖活动ID
	
	private Long couponSchemeId; // 优惠券批次ID
	
	private String couponName; // 优惠券名字
	
	private BigDecimal couponFaceValue; //优惠券面值
	
	private Long signInRecordId; // 签到记录ID
	
	private Integer days; // 几天后领奖
	
	private boolean lucky = false;
	
	private Integer luckyType; //0没有,1:抽奖,2:优惠券
	
	private Integer integral; // 今日签到可获得积分
	
	private boolean activeStatus = true; // 当天活动状态
	
	private boolean lock = false; // 并发锁标志
}
