package com.hnair.consumer.user.api.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hnair.consumer.dao.service.ICommonService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import com.hnair.consumer.user.api.IUserBrowseHistoryApi;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.UserBrowseHistory;
import com.hnair.consumer.user.vo.UserMessageVo;

@Component("userBrowseHistoryApi")
public class UserBrowseHistoryApiImpl implements IUserBrowseHistoryApi {

	private static final Logger logger = LoggerFactory.getLogger(UserBrowseHistoryApiImpl.class);

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterCommonService;

	@Override
	public UserMessageVo<UserBrowseHistory> deleteUserBrowseHistory(Long userId, Long browseId) {
		UserMessageVo<UserBrowseHistory> result = new UserMessageVo<UserBrowseHistory>();
		result.setResult(false);
		try {
			UserBrowseHistory userBrowseHistory = ucenterCommonService.get(browseId, UserBrowseHistory.class);
			if (userBrowseHistory == null || userBrowseHistory.getIsDel() == 1) {
				result.setResult(true);
				return result;
			}
			if (!userBrowseHistory.getUserId().equals(userId)) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12029.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12029.getErrorMessage());
				return result;
			}

			userBrowseHistory.setIsDel(1);
			userBrowseHistory.setLastModifyDate(new Date());
			ucenterCommonService.update(userBrowseHistory);
			result.setResult(true);
		} catch (Exception e) {
			logger.info("删除浏览历史异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12030.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12030.getErrorMessage());
			return result;
		}

		return result;
	}

	@Override
	public UserMessageVo<UserBrowseHistory> cleanUserBrowseHistory(Long userId) {

		UserMessageVo<UserBrowseHistory> result = new UserMessageVo<UserBrowseHistory>();
		result.setResult(false);
		try {
			ucenterCommonService.updateBySqlId(UserBrowseHistory.class, "updateByUserId", "userId", userId, "isDel",
					"1", "lastModifyDate", new Date());
			result.setResult(true);
		} catch (Exception e) {
			logger.info("删除浏览历史异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12030.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12030.getErrorMessage());
			return result;
		}

		return null;
	}

	@Override
	public UserMessageVo<List<UserBrowseHistory>> getUserBrowseHistoryList(Long userId, Long lastBrowseId,
			Integer pageSize) {
		UserMessageVo<List<UserBrowseHistory>> result = new UserMessageVo<List<UserBrowseHistory>>();
		result.setResult(false);
		try {

			List<UserBrowseHistory> userBrowseHistoryList = ucenterCommonService.getList(UserBrowseHistory.class,
					"userId", userId, "idLess", lastBrowseId, "pageSize", pageSize + 1, "isDel", 0, "orderById", 1);
			int isHasNext = 0;
			if (CollectionUtils.isNotEmpty(userBrowseHistoryList) && userBrowseHistoryList.size() > pageSize) {
				isHasNext = 1;
				userBrowseHistoryList.remove(userBrowseHistoryList.size() - 1);
			}
			result.setResult(true);
			result.setIsHasNext(isHasNext);
			result.setT(userBrowseHistoryList);
		} catch (Exception e) {
			logger.info("查询浏览历史异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12026.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12026.getErrorMessage());
			return result;
		}
		return result;
	}

}