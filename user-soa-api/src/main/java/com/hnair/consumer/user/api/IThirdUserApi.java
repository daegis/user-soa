package com.hnair.consumer.user.api;

import com.hnair.consumer.user.model.ThirdPartyUserInfo;

/**
 * Description: 用户接口
 * All Rights Reserved.
 * @version 1.0  2016年11月1日 下午4:27:47  by 李超（li-ch3@hnair.com）创建
 */
public interface IThirdUserApi {
	
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
	 * @Description: 保存
	 * @param partyUserInfo
	 * @return 返回类型 
	 * @throws
	 */
	public void saveThirdUserInfo(ThirdPartyUserInfo partyUserInfo);
}
