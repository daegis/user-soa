package com.hnair.consumer.user.api;

import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.UserUsualLocationVo;


/**
 * 
 * @author 许文轩
 * @comment 用戶定位相關接口
 * @date 2018年1月2日 下午6:00:07	
 *
 */
public interface IUserLocationApi {
	/**
	 * 
	 * @author 许文轩
	 * @comment 获取用户常用定位目的地城市
	 * @date 2018年1月2日 下午6:00:47
	 */
	public UserMessageVo<UserUsualLocationVo> getUserUsualLocationDestination(Long userId);
}
