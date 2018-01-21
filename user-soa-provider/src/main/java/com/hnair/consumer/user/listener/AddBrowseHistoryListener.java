package com.hnair.consumer.user.listener;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.model.UserBrowseHistory;

/**
 * 
 * @author 许文轩
 * @comment 增加浏览历史消息
 * @date 2017年9月4日 下午7:48:07
 *
 */
public class AddBrowseHistoryListener {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;

	private final static Logger logger = LoggerFactory.getLogger(AddBrowseHistoryListener.class);

	public void handleMessage(Object browseHistoryMsg) {
		try {
			UserBrowseHistory userBrowseHistory = (UserBrowseHistory) browseHistoryMsg;
			if (userBrowseHistory.getBrowseType() != null && StringUtils.isNotBlank(userBrowseHistory.getTargetType())
					&& userBrowseHistory.getTargetId() != null && userBrowseHistory.getUserId() != null) {
				// 判断如果已经存在，更新最后更新时间
				List<UserBrowseHistory> browseHistoryList = ucenterService.getList(UserBrowseHistory.class,
						"browseType", userBrowseHistory.getBrowseType(), "targetType",
						userBrowseHistory.getTargetType(), "targetId", userBrowseHistory.getTargetId(), "userId",
						userBrowseHistory.getUserId(), "idDel", 0);
				if (CollectionUtils.isNotEmpty(browseHistoryList)) {
					UserBrowseHistory dbUserBrowseHistory = browseHistoryList.get(0);
					dbUserBrowseHistory.setLastModifyDate(new Date());
					dbUserBrowseHistory.setIsDel(1);
					ucenterService.update(dbUserBrowseHistory);
				}
				userBrowseHistory.setCreateDate(new Date());
				userBrowseHistory.setLastModifyDate(new Date());
				userBrowseHistory.setIsDel(0);
				ucenterService.save(userBrowseHistory);
			}

		} catch (Exception e) {
			logger.info("处理增加浏览历史消息异常", e);
		}

	}
}
