package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.user.model.UserBrowseHistory;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * 
 * @author 许文轩
 * @comment 用户浏览历史接口
 * @date 2017年9月4日 下午8:29:53
 *
 */
public interface IUserBrowseHistoryApi {

	/**
	 * 
	 * @author 许文轩
	 * @comment 删除用户浏览历史
	 * @date 2017年9月4日 下午8:34:00
	 */
	public UserMessageVo<UserBrowseHistory> deleteUserBrowseHistory(Long userId, Long browseId);

	/**
	 * 
	 * @author 许文轩
	 * @comment 清空用户浏览历史
	 * @date 2017年9月4日 下午8:40:00
	 */
	public UserMessageVo<UserBrowseHistory> cleanUserBrowseHistory(Long userId);

	/**
	 * 
	 * @author 许文轩
	 * @comment 查询用户浏览历史
	 * @date 2017年9月4日 下午8:33:40
	 */
	public UserMessageVo<List<UserBrowseHistory>> getUserBrowseHistoryList(Long userId, Long lastBrowseId,
			Integer pageSize);

}
