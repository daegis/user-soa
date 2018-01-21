package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.user.vo.UserPrivilegeVo;

/**
 * 用户会员权益接口
 * @author zhangxianbin
 *
 */
public interface IUserPrivilegeApi {
	
	/**
	 * 查询会员权益
	 * @param userId
	 * @return
	 */
	public UserPrivilegeVo queryUserPrivilege(Long userId,String memberLevelCode,String selfPage) throws Exception;
	
	/**
	 * 查询所有会员权益
	 * @return
	 * @throws Exception
	 */
	public List<UserPrivilegeVo> queryAllPrivilege(Long userId) throws Exception;
	
}
