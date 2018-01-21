package com.hnair.consumer.user.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IUserBlackListApi;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.UserBlackList;
import com.hnair.consumer.user.vo.UserMessageVo;

@Component("userBlackListApi")
public class UserBlackListApiImpl implements IUserBlackListApi {

	private static final Logger logger = LoggerFactory.getLogger(UserBlackListApiImpl.class);

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterCommonService;

	@Override
	public UserMessageVo<UserBlackList> addUserBlackList(UserBlackList userBlackList) {
		UserMessageVo<UserBlackList> result = new UserMessageVo<UserBlackList>();
		result.setResult(false);
		try {
			if (userBlackList == null || userBlackList.getUserId() == null || userBlackList.getBanUserId() == null) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12022.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12022.getErrorMessage());
				return result;
			}
			List<UserBlackList> userBlackListList = ucenterCommonService.getList(UserBlackList.class, "isDel", 0,
					"userId", userBlackList.getUserId(), "banUserId", userBlackList.getBanUserId());
			if (CollectionUtils.isNotEmpty(userBlackListList)) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12023.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12023.getErrorMessage());
				return result;
			}
			userBlackList.setCreateTime(new Date());
			userBlackList.setIsDel(0);
			userBlackList.setLastModifyTime(new Date());
			ucenterCommonService.save(userBlackList);
			result.setResult(true);
			result.setT(userBlackList);
		} catch (Exception e) {
			logger.info("添加黑名单用户异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12022.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12022.getErrorMessage());
			return result;
		}
		return result;
	}

	@Override
	public UserMessageVo<UserBlackList> deleteUserBlackList(Long userId, Long userBlackListId) {
		UserMessageVo<UserBlackList> result = new UserMessageVo<UserBlackList>();
		result.setResult(false);
		UserBlackList userBlackList = null;
		try {
//			UserBlackList userBlackList = ucenterCommonService.get(userBlackListId, UserBlackList.class);
			List<UserBlackList> userBlackListList = ucenterCommonService.getList(UserBlackList.class,"userId",userId,"banUserId",userBlackListId,"isDel",0);
			for(UserBlackList list: userBlackListList){
				if(list.getIsDel().equals(0)){
					userBlackList = list;
				}
			}

			if (userBlackList == null || userBlackList.getIsDel() == 1) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12024.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12024.getErrorMessage());
				return result;
			}
			if (!userBlackList.getUserId().equals(userId)) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12027.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12027.getErrorMessage());
				return result;
			}
			logger.info(userBlackList.getBlackListId()+"**"+userBlackList.getUserId()+"**"+userBlackList.getBanUserId());

			userBlackList.setIsDel(1);
			userBlackList.setLastModifyTime(new Date());
			ucenterCommonService.update(userBlackList);
			result.setResult(true);
		} catch (Exception e) {
			logger.info("删除黑名单用户异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12025.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12025.getErrorMessage());
			return result;
		}

		return result;
	}

	@Override
	public UserMessageVo<UserBlackList> deleteUserBlackListByUserId(Long userId, Long banUserId) {
		UserMessageVo<UserBlackList> result = new UserMessageVo<UserBlackList>();
		result.setResult(false);
		try {
			UserBlackList userBlackList = ucenterCommonService.get(UserBlackList.class, "userId", userId, "banUserId",
					banUserId,"isDel", 0);
			if (userBlackList == null || userBlackList.getIsDel() == 1) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12024.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12024.getErrorMessage());
				return result;
			}

			userBlackList.setIsDel(1);
			userBlackList.setLastModifyTime(new Date());
			ucenterCommonService.update(userBlackList);
			result.setResult(true);
		} catch (Exception e) {
			logger.info("删除黑名单用户异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12025.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12025.getErrorMessage());
			return result;
		}

		return result;
	}

	@Override
	public UserMessageVo<List<UserBlackList>> getUserBlackList(Long userId, Long lastUserBlackListId,
			Integer pageSize) {
		UserMessageVo<List<UserBlackList>> result = new UserMessageVo<List<UserBlackList>>();
		result.setResult(false);
		try {

			List<UserBlackList> userBlackListList = ucenterCommonService.getList(UserBlackList.class, "userId", userId,
					"idLess", lastUserBlackListId, "pageSize", pageSize + 1, "isDel", 0, "orderById", 1);
			int isHasNext = 0;
			if (CollectionUtils.isNotEmpty(userBlackListList) && userBlackListList.size() > pageSize) {
				isHasNext = 1;
				userBlackListList.remove(userBlackListList.size() - 1);
			}
			result.setResult(true);
			result.setIsHasNext(isHasNext);
			result.setT(userBlackListList);
		} catch (Exception e) {
			logger.info("查询黑名单用户异常", e);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12026.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12026.getErrorMessage());
			return result;
		}
		return result;
	}

	@Override
	public List<Long> getBlackListuserIds(Long userId) {
		List<UserBlackList> userBlackListList = ucenterCommonService.getList(UserBlackList.class, "userId", userId,
				"isDel", 0);
		List<Long> userIds = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(userBlackListList)) {
			for (UserBlackList black : userBlackListList) {
				userIds.add(black.getBanUserId());
			}
		}
		return userIds;
	}

	@Override
	public List<Long> getbanedUserIds(Long userId) {
		List<UserBlackList> userBlackListList = ucenterCommonService.getList(UserBlackList.class, "userId", userId,
				"isDel", 0);
		List<Long> userIds = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(userBlackListList)) {
			for (UserBlackList black : userBlackListList) {
				userIds.add(Long.valueOf(black.getBanUserId()));
			}
		}
		return userIds;
	}

	@Override
	public boolean validBlackList(Long userId, Long banUserId) {
		if (userId == null || banUserId == null) {
			return false;
		}
		List<UserBlackList> userBlackListList = ucenterCommonService.getList(UserBlackList.class, "userId", userId,
				"banUserId", banUserId, "isDel", 0);
		if (CollectionUtils.isNotEmpty(userBlackListList)) {
			return true;
		}
		return false;
	}

}