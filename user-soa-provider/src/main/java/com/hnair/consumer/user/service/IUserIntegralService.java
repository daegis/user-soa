package com.hnair.consumer.user.service;

import com.hnair.consumer.user.model.UserIntegral;
import com.hnair.consumer.user.vo.OpenUserIntegralVo;
import com.hnair.consumer.user.vo.UserMessageVo;

public interface IUserIntegralService {

	/**
	 * 根据手机号消费用户积分
	 * @param mobile
	 * @param integral
	 * @return
	 */
	public UserMessageVo<OpenUserIntegralVo> consumeUerIntegralByMobile(UserIntegral userIntegral);
	
	/**
	 * 返还用户积分
	 * @param userIntegral
	 * @return
	 */
	public UserMessageVo<OpenUserIntegralVo> refundUerIntegral(UserIntegral userIntegral);
	
}
