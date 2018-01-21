package com.hnair.consumer.user.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IHimiChatRecordApi;
import com.hnair.consumer.user.model.HimiChatRecord;

/**
 * Description: HIMI聊天信息处理API接口实现
 * 
 * All Rights Reserved.
 * @version 1.0  2017年10月17日 下午2:16:52  by 张慧东（huid.zhang@haihangyun.com）创建
 */
@Component("himiChatRecordApi")
public class HimiChatRecordApiImpl implements IHimiChatRecordApi {
	
    private static Logger logger = LoggerFactory.getLogger(HimiChatRecordApiImpl.class);
    
    @Resource(name = "ucenterCommonService")
    private ICommonService ucenterService;
	
	/**
	 * 根据用户ID和UID查询用聊天记录，避免重复记录用户发送消息
	 * 
	 * @param userId
	 * @param Uid
	 * @return
	 */
	@Override
	public List<HimiChatRecord> getHimiChatRecordBycondition(Long userId, String uid) {
		// 固定数据解释
		// userFlag：用户类型：0 系统；1 用户
		// isDelete: 是否被删除 0：有效,1：删除
		List<HimiChatRecord> result = ucenterService.getList(HimiChatRecord.class, 
				"userId", userId, "uid", uid, "userFlag", 1, "isDelete", 0);
		return result;
	}
	
	/**
	 * Description: HIMI保存消息记录
	 * 
	 * @version 1.0  2017年10月17日 下午2:16:52  by 张慧东（huid.zhang@haihangyun.com）创建
	 * @param himiChatRecord
	 * @return
	 */
	@Override
	public HimiChatRecord saveHimiChatRecord(HimiChatRecord himiChatRecord) {
		logger.info("|----himiChatRecord:" + himiChatRecord.getId());
		ucenterService.save(himiChatRecord);
		logger.info("|----result:" + himiChatRecord.getId());
		
		return himiChatRecord;
	}

	/**
	 * Description:取得历史用户消息记录
	 * 
	 * @version 1.0  2017年10月17日 下午2:16:52  by 张慧东（huid.zhang@haihangyun.com）创建
	 * @param userId
	 * @param currentId
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<HimiChatRecord> queryHimiChatRecordList(Long userId, Integer currentId, Integer pageSize) {
		
		logger.info("》》》queryHimiChatRecordList:");
		logger.info("|----userId:" + userId);
		logger.info("|----currentId:" + currentId);
		logger.info("|----pageSize:" + pageSize);
		
		List<HimiChatRecord> result = ucenterService.getListBySqlId(HimiChatRecord.class, "getHistoryRecord", 
				"isDelete", 0, "userId", userId, "lessId", currentId, "pageSize", pageSize);
		
		logger.info("|----result:" + result);
		logger.info("《《《queryHimiChatRecordList:");
		
		return result;
	}
}
