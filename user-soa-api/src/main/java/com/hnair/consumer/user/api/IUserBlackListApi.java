package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.user.model.UserBlackList;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * 
 * @author 许文轩
 * @comment 用户黑名单接口
 * @date 2017年8月18日 下午2:19:23	
 *
 */
public interface IUserBlackListApi {

	/**
	 * 
	 * @author 许文轩
	 * @comment 添加用户黑名单
	 * @date 2017年8月18日 下午3:12:05
	 */
	public UserMessageVo<UserBlackList> addUserBlackList(UserBlackList userBlackList);

	/**
	 * 
	 * @author 许文轩
	 * @comment 删除用户黑名单
	 * @date 2017年8月18日 下午3:21:20
	 */
	public UserMessageVo<UserBlackList> deleteUserBlackList(Long userId,Long userBlackListId);

	/**
	 * 
	 * @author 许文轩
	 * @comment 查询黑名单用户列表
	 * @date 2017年8月18日 下午3:37:54
	 */
	public UserMessageVo<List<UserBlackList>> getUserBlackList(Long userId, Long lastUserBlackListId, Integer pageSize);

	/**
	 * 
	 * @author 许文轩
	 * @comment 获取该用户屏蔽的所有用户id
	 * @date 2017年8月18日 下午5:37:35
	 */
	public List<Long> getBlackListuserIds(Long userId);

	/**
	 * 
	 * @author 许文轩
	 * @comment 获取屏蔽该用户的所有用户id
	 * @date 2017年8月18日 下午6:26:51
	 */
	public List<Long> getbanedUserIds(Long userId);

	/**
	 * 
	 * @author 许文轩
	 * @comment 根据用户id删除黑名单
	 * @date 2017年8月21日 上午9:13:40
	 */
	public UserMessageVo<UserBlackList> deleteUserBlackListByUserId(Long userId, Long banUserId);

	/**
	 * 
	 * @author 许文轩
	 * @comment 验证用户是否被拉黑
	 * @param userId 拉黑用户
	 * @param 被拉黑用户
	 * @date 2017年8月21日 上午11:46:57
	 */
	public boolean validBlackList(Long userId, Long banUserId);
	
	
	
	

}
