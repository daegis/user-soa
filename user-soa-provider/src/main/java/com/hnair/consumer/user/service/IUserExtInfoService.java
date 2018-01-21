package com.hnair.consumer.user.service;


import java.util.List;

import com.hnair.consumer.user.enums.UserBehaviorTypeEnum;
import com.hnair.consumer.user.model.UserExtInfo;

/**
 *
 */
public interface IUserExtInfoService {
	/**
	 * 关注
	 * @param userId			　　　发起者
	 * @param targetUserId　　　　　被关注者
	 * @param userBehaviorType	　　关注或者取消关注
     */
	 void updateUserExtInfo(Long userId, Long targetUserId, UserBehaviorTypeEnum userBehaviorType);

	/**
	 * 用户发帖
	 * @param userId
     */
	 void updateUserExtInfo(Long userId);

	 /**
	  * 批量获取用户扩展信息
	  * @param userIds
	  * @return
	  */
	 List<UserExtInfo> getUserExtInfoList(List<Long> userIds);

	/**
	 * 
	 * @author 许文轩
	 * @comment 清空用户泡泡数量
	 * @date 2017年3月29日 上午9:06:01
	 */
	public void cleanUserPostCount(Long userId);
	
	/**
	 * 记录用户最后一次登录app版本号和渠道号
	 * @param userId
	 * @param lastAppVersion
	 * @param lastChannel
	 */
	public void updateLastAppVersion(Long userId, String lastAppVersion, String lastChannel);

}
