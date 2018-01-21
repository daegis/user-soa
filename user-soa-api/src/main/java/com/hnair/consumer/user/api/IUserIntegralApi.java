package com.hnair.consumer.user.api;

import com.hnair.consumer.user.model.UserIntegral;
import com.hnair.consumer.user.vo.OpenUserIntegralVo;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * 用户积分接口
 * @author zhangxianbin
 *
 */
public interface IUserIntegralApi {
	
	/**
	 * 根据手机号查询用户积分余额
	 * @param mobile
	 * @return
	 */
	public UserMessageVo<OpenUserIntegralVo> queryUerIntegralByMobile(String mobile);
	
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
