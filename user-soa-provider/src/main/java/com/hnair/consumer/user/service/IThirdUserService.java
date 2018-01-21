package com.hnair.consumer.user.service;

import java.util.Map;

import com.hnair.consumer.user.model.Attention;
import com.hnair.consumer.user.model.MessageCode;
import com.hnair.consumer.user.model.ThirdPartyUserInfo;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserFeedback;
import com.hnair.consumer.user.model.UserLoginRecord;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 下午7:26:17  by 李超（li-ch3@hnair.com）创建
 */
public interface IThirdUserService {
	/**
	 * @Title: getThirdUserInfoByToken 
	 * @Description: 根据openId获取用户第三方用户信息
	 * @param openId
	 * @return
	 * @return 返回类型 
	 * @throws
	 */
	public ThirdPartyUserInfo getThirdUserInfoByOpenId(String openId);
	
	/**
	 * @Title: getThirdUserInfoBySessionId 
	 * @Description: 根据session获取用户信息
	 * @param sessionId
	 * @return
	 * @return 返回类型 
	 * @throws
	 */
	public ThirdPartyUserInfo getThirdUserInfoBySessionId(String sessionId);
	
	/**
	 * @Title: updateThirdUserInfo 
	 * @Description: 更新第三方用户信息
	 * @param partyUserInfo
	 * @return 返回类型 
	 * @throws
	 */
	public void updateThirdUserInfo(ThirdPartyUserInfo partyUserInfo);
	
	/**
	 * @Title: saveThirdUserInfo 
	 * @Description: 保持用户信息
	 * @param partyUserInfo
	 * @return 返回类型 
	 * @throws
	 */
	public void saveThirdUserInfo(ThirdPartyUserInfo partyUserInfo);
}
