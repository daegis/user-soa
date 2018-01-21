package com.hnair.consumer.user.service;

import java.util.Map;

import com.hnair.consumer.user.model.PrizeRecord;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.vo.SignInVo;

/**
 * 签到接口
 */
public interface ISignInService{
	
	/**
	 * 签到
	 * @param signInVo
	 * @return
	 */
	public SignInVo signIn(UserBaseInfo user);	
	/**
	 * 签到主页
	 * @param userId
	 * @return
	 */
	public SignInVo getSignInHomePage(Long userId);
	
	/**
	 * 查询中奖纪录
	 * @param queryMap
	 * @return
	 */
	public PrizeRecord getPrizeRecord(Map<String, Object> queryMap);


	
}
