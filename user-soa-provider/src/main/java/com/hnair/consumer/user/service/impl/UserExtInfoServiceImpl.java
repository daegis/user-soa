package com.hnair.consumer.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hnair.consumer.dao.spi.ICommonDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.hnair.consumer.user.api.impl.UserApiImpl;
import com.hnair.consumer.user.enums.UserBehaviorTypeEnum;
import com.hnair.consumer.user.model.UserExtInfo;
import com.hnair.consumer.user.service.IUserExtInfoService;

/**
 * Created by simple on 2016/11/30 16:23
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
@Service
public class UserExtInfoServiceImpl implements IUserExtInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserApiImpl.class);

    @Resource(name = "ucenterCommonDao")
    private ICommonDao ucenterCommonDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUserExtInfo(Long userId, Long targetUserId, UserBehaviorTypeEnum userBehaviorType) {
		logger.info("updateUserExtInfo|userId:" + userId + ",targetUserId:" + targetUserId + ",userBehaviorType:"
				+ userBehaviorType);
		if (userBehaviorType == null) {
			logger.error("userBehaviorType is null");
			return;
		}

		List<Long> userIdList = new ArrayList<>();
		if (null != userId) {
			userIdList.add(userId);
		}
		if (null != targetUserId) {
			userIdList.add(targetUserId);
		}
		// 数据库是否存在用户
		List<UserExtInfo> userExtInfoList = ucenterCommonDao.getListBySqlId(UserExtInfo.class, "selectByField",
				"_fieldName", "user_id", "list", userIdList);
		Map<Long, UserExtInfo> existsUserIdList = new HashMap<>();
		if (CollectionUtils.isNotEmpty(userExtInfoList)) {
			userExtInfoList.stream().forEach(o -> {
				existsUserIdList.put(o.getUserId(), o);
			});
		}

		UserExtInfo userExtInfo1 = new UserExtInfo();
		String dbField1 = null;
		int count1 = 0;
		UserExtInfo userExtInfo2 = new UserExtInfo();
		String dbField2 = null;
		int count2 = 0;
		switch (userBehaviorType) {
		case POST:
			if (userId == null) {
				logger.info("userId is null");
			}
			userExtInfo1.setUserId(userId);
			userExtInfo1.setCreateDate(new Date());
			userExtInfo1.setModifyDate(new Date());
			userExtInfo1.setPostCount(1);
			count1 = 1;
			dbField1 = "postCount";
			break;
		case DELETE_POST:
			if (userId == null) {
				logger.info("userId is null");
			}
			userExtInfo1.setUserId(userId);
			userExtInfo1.setCreateDate(new Date());
			userExtInfo1.setModifyDate(new Date());
			userExtInfo1.setPostCount(-1);
			count1 = -1;
			dbField1 = "postCount";
			break;
		case ATTENTION:
			if (userId == null || targetUserId == null) {
				logger.info("userId  or targetUserId is null");
			}
			userExtInfo1.setUserId(userId);
			userExtInfo1.setCreateDate(new Date());
			userExtInfo1.setModifyDate(new Date());
			userExtInfo1.setPostCount(1);
			count1 = 1;
			dbField1 = "attentionCount";
			userExtInfo2.setUserId(targetUserId);
			userExtInfo2.setCreateDate(new Date());
			userExtInfo2.setModifyDate(new Date());
			userExtInfo2.setFansCount(1);
			count2 = 1;
			dbField2 = "fansCount";
			break;
		case CANCEL_ATTENTION:
			if (userId == null || targetUserId == null) {
				logger.info("userId  or targetUserId is null");
			}
			userExtInfo1.setUserId(userId);
			userExtInfo1.setCreateDate(new Date());
			userExtInfo1.setModifyDate(new Date());
			userExtInfo1.setPostCount(-1);
			count1 = -1;
			dbField1 = "attentionCount";
			userExtInfo2.setUserId(targetUserId);
			userExtInfo2.setCreateDate(new Date());
			userExtInfo2.setModifyDate(new Date());
			userExtInfo2.setFansCount(-1);
			count2 = -1;
			dbField2 = "fansCount";
			break;
		case PRAISE:
			if (userId == null || targetUserId == null) {
				logger.info("userId  or targetUserId is null");
			}
			userExtInfo2.setUserId(targetUserId);
			userExtInfo2.setCreateDate(new Date());
			userExtInfo2.setModifyDate(new Date());
			userExtInfo2.setReceivePraiseCount(1);
			count2 = 1;
			dbField2 = "receivePraiseCount";
			break;
		case CANCEL_PRAISE:
			if (userId == null || targetUserId == null) {
				logger.info("userId  or targetUserId is null");
			}
			userExtInfo2.setUserId(targetUserId);
			userExtInfo2.setCreateDate(new Date());
			userExtInfo2.setModifyDate(new Date());
			userExtInfo2.setReceivePraiseCount(-1);
			count2 = -1;
			dbField2 = "receivePraiseCount";
			break;
		case COLLECT:
			if (userId == null || targetUserId == null) {
				logger.info("userId  or targetUserId is null");
			}
			userExtInfo2.setUserId(targetUserId);
			userExtInfo2.setCreateDate(new Date());
			userExtInfo2.setModifyDate(new Date());
			userExtInfo2.setReceiveCollectCount(1);
			count2 = 1;
			dbField2 = "receiveCollectCount";
			break;
		case CANCEL_COLLECT:
			if (userId == null || targetUserId == null) {
				logger.info("userId  or targetUserId is null");
			}
			userExtInfo2.setUserId(targetUserId);
			userExtInfo2.setCreateDate(new Date());
			userExtInfo2.setModifyDate(new Date());
			userExtInfo2.setReceiveCollectCount(-1);
			count2 = -1;
			dbField2 = "receiveCollectCount";
			break;
		}
		try {

			if (dbField1 != null) {
				if (null == existsUserIdList.get(userId)) {
					ucenterCommonDao.save(userExtInfo1);
				} else {
					ucenterCommonDao.updateBySqlId(UserExtInfo.class, "addByField", dbField1, count1, "modifyDate",
							new Date(), "id", existsUserIdList.get(userId).getId());
				}
			}
			if (dbField2 != null) {
				if (null == existsUserIdList.get(targetUserId)) {
					ucenterCommonDao.save(userExtInfo2);
				} else {
					ucenterCommonDao.updateBySqlId(UserExtInfo.class, "addByField", dbField2, count2, "modifyDate",
							new Date(), "id", existsUserIdList.get(targetUserId).getId());
				}
			}
		} catch (Exception e) {
			logger.error("update userExtInfo error",e);
		}
	}
	
	@Override
	public void cleanUserPostCount(Long userId) {
		UserExtInfo userExtInfoFromDb = ucenterCommonDao.get(UserExtInfo.class, "userId", userId);
		if (userExtInfoFromDb != null) {
			UserExtInfo userExtInfo = new UserExtInfo();
			userExtInfo.setId(userExtInfoFromDb.getId());
			userExtInfo.setPostCount(0);
			ucenterCommonDao.update(userExtInfo);
		}
	}

    @Override
    public void updateUserExtInfo(Long userId) {
        if(null ==userId ){
            return;
        }
        UserExtInfo userExtInfoFromDb =  ucenterCommonDao.get(UserExtInfo.class,"userId",userId);
        if(null == userExtInfoFromDb){
            UserExtInfo userExtInfo = new UserExtInfo();
            userExtInfo.setUserId( userId );
            userExtInfo.setCreateDate( new Date() );
            userExtInfo.setPostCount(1);
            ucenterCommonDao.save( userExtInfo );
        }else{
            ucenterCommonDao.updateBySqlId(UserExtInfo.class,"addByField","postCount","post_count","modifyDate",new Date(),"id",userExtInfoFromDb.getUserId());
        }
    }

	@Override
	public List<UserExtInfo> getUserExtInfoList(List<Long> userIds) {
		
		List<UserExtInfo> list = ucenterCommonDao.getListBySqlId(UserExtInfo.class, "getByUserIds", "userIds", userIds);
		if(list==null){
			list = new ArrayList<UserExtInfo>();
		}
		return list;
	}

	@Override
	public void updateLastAppVersion(Long userId, String lastAppVersion, String lastChannel) {
		if(userId == null || StringUtils.isBlank(lastAppVersion)){
			return;
		}
		UserExtInfo userExtInfoFromDb =  ucenterCommonDao.get(UserExtInfo.class,"userId",userId);
        if(null == userExtInfoFromDb){
            UserExtInfo userExtInfo = new UserExtInfo();
            userExtInfo.setUserId( userId );
            userExtInfo.setCreateDate( new Date() );
            userExtInfo.setPostCount(0);           
            ucenterCommonDao.save( userExtInfo );
        }
        ucenterCommonDao.updateBySqlId(UserExtInfo.class,"updateLastAppVersion","userId",userId,"lastAppVersion",lastAppVersion,"lastChannel",lastChannel);
	}
}
