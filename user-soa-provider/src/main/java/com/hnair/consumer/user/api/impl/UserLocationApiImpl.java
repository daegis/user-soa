package com.hnair.consumer.user.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.poi.api.hi.IDestinationApi;
import com.hnair.consumer.user.api.IUserLocationApi;
import com.hnair.consumer.user.enums.UsualLocationTypeEnum;
import com.hnair.consumer.user.model.UserLocationUsual;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.UserUsualLocationVo;

/**
 * 
 * @author 许文轩
 * @comment 用户定位接口
 * @date 2018年1月2日 下午6:03:13
 *
 */
@Component("userLocationApi")
public class UserLocationApiImpl implements IUserLocationApi {
	private static Logger logger = LoggerFactory.getLogger(UserLocationApiImpl.class);

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;

	@Resource
	IDestinationApi destinationApi;

	@Override
	public UserMessageVo<UserUsualLocationVo> getUserUsualLocationDestination(Long userId) {
		UserMessageVo<UserUsualLocationVo> message = new UserMessageVo<UserUsualLocationVo>();
		try {
			List<UserLocationUsual> locationUsualList = ucenterService.getList(UserLocationUsual.class, "userId",
					userId, "usualLocationType", UsualLocationTypeEnum.DESTINATION.getType());
			if (CollectionUtils.isEmpty(locationUsualList)) {
				message.setResult(false);
				message.setErrorMessage("没有常用定位信息");
				return message;
			}
			UserLocationUsual locationUsual = locationUsualList.get(0);
			if (locationUsual == null || locationUsual.getDestinationId() == null) {
				message.setResult(false);
				message.setErrorMessage("没有常用定位信息");
				return message;
			}

			UserUsualLocationVo locationVo = new UserUsualLocationVo();
			locationVo.setCityId(locationUsual.getCityId());
			locationVo.setDestinationId(locationUsual.getDestinationId());
			locationVo.setUserId(locationUsual.getUserId());
			message.setT(locationVo);
			message.setResult(true);
			return message;

		} catch (Exception e) {
			logger.error("获取常用定位信息异常", e);
			message.setResult(false);
			message.setErrorMessage(e.getMessage());
			return message;
		}

	}

}
