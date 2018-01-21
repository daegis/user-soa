package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.List;

import com.hnair.consumer.user.model.SignInAwardRule;

import lombok.Getter;
import lombok.Setter;

/**
 * 签到活动
 */
@Getter
@Setter
public class SignInActiveVo implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 活动id
	 */
	private Long id;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 连续签到总周期(单位:天)
	 */
	private Integer countPeriod;
	/**
	 * 连续签到奖励规则Id
	 */
	private List<SignInAwardRule> signInAwardRuleList;
	/**
	 * 活动开始时间
	 */
	private java.util.Date startTime;
	/**
	 * 活动结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 连续签到达到周期最大时间时是否清零,0:不清零,1:清零
	 */
	private Integer reset;
	/**
	 * 签到基础积分
	 */
	private Integer basisIntegral;
	
	/**
	 * 活动状态,0:未开始,1:进行中,2:已结束
	 */
	private Integer status;
	/**
	 * 签到活动规则（描述）
	 */
	private String signInActiveRule;
	
	/**
	 * 活动创建时间
	 */
	private String createTime;
	
	private Integer totalNum;// 总次数
	
	private Integer totalPerson; // 总人数
	
	private Long toatlIntegral; // 总积分
	
	/**
	 * 跳转页面
	 */
	private Integer page;
	/**
	 * 每页展示的数据数量
	 */
	private Integer pageSize;
	/**
	 * 页面跳转页
	 */
	private Integer pageNo;
	/**
	 * 开始记录索引
	 */
	private Integer offset;
	
}
