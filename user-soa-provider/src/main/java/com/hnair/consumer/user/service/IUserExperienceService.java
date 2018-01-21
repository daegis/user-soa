package com.hnair.consumer.user.service;

import java.util.List;
import java.util.Set;

/**
 * 用户经验相关service
 * @author zhangxianbin
 *
 */
public interface IUserExperienceService {

	/**
	 * 根据订单号给用户增加经验值
	 * @param orderNo
	 * @return 操作成功的订单号集合
	 */
	public Set<String> addExperienceByOrderNo(List<String> orderNos);
	
	/**
	 * 根据用户id批量计算并调整用户等级
	 * @param userIds
	 */
	public void calculateUserGrade(List<Long> userIds);
	
}
