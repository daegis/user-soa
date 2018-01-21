package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberUpgradeTaskDetailVo implements Serializable{

	private static final long serialVersionUID = 1494290576344029387L;

	/**
	 * 会员等级
	 */
	private String memberLevel;
	
	/**
	 * 会员等级code
	 */
	private String memberLevelCode;
	
	/**
	 * 会员升级任务名称
	 */
	private String upgradeTaskName;
	
	/**
	 * 会员升级任务最小年龄限制
	 */
	private String upgradeTaskMinAgeRestrict;
	
	/**
	 * 会员升级任务最大年龄限制
	 */
	private String upgradeTaskMaxAgeRestrict;
	
	/**
	 * 会员升级任务达成目标
	 */
	private Integer upgradeTaskGoal;
	
	/**
	 * 会员升级任务支持订单类型
	 */
	private Set<String> orderTypes = new HashSet<>();
	
}
