package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.user.model.HimiChatRecord;


/**
 * Description: HIMI聊天信息处理API接口
 * 
 * All Rights Reserved.
 * @version 1.0  2017年10月17日 下午2:16:52  by 张慧东（huid.zhang@haihangyun.com）创建
 */
public interface IHimiChatRecordApi {
	
	/**
	 * Description: HIMI保存消息记录
	 * 
	 * @version 1.0  2017年10月17日 下午2:16:52  by 张慧东（huid.zhang@haihangyun.com）创建
	 * @param himiChatRecord
	 * @return
	 */
	public HimiChatRecord saveHimiChatRecord(HimiChatRecord himiChatRecord);
	
	/**
	 * Description:取得历史用户消息记录
	 * 
	 * @version 1.0  2017年10月17日 下午2:16:52  by 张慧东（huid.zhang@haihangyun.com）创建
	 * @param userId
	 * @param currentId
	 * @param pageSize
	 * @return
	 */
	public List<HimiChatRecord> queryHimiChatRecordList(Long userId, Integer currentId, Integer pageSize);

	/**
	 * 根据用户ID和UID查询用聊天记录，避免重复记录用户发送消息
	 * 
	 * @param userId
	 * @param Uid
	 * @return
	 */
	public List<HimiChatRecord> getHimiChatRecordBycondition(Long userId, String uid);
}
