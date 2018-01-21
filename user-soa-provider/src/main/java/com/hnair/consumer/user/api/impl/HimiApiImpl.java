package com.hnair.consumer.user.api.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IHimiApi;
import com.hnair.consumer.user.model.CustomService;
import com.hnair.consumer.user.vo.CustomServiceVo;

@Component("himiApi")
public class HimiApiImpl implements IHimiApi {

	private static Logger logger = LoggerFactory.getLogger(HimiApiImpl.class);

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;

	@SuppressWarnings("rawtypes")
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate masterRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate slaveRedisTemplate;

	/**
	 * 客服信息缓存
	 */
	private final String CUSTOM_SEARVICE_INFO_KEY = "custom_service_info_cache_";

	@Override
	@SuppressWarnings("unchecked")
	public CustomServiceVo getCustomServiceInfo(String customServiceThirdId) {
		if (StringUtils.isBlank(customServiceThirdId)) {
			logger.error("传入的customServiceThirdId为空");
			return null;
		}
		CustomServiceVo customServiceVo = new CustomServiceVo();
		if (slaveRedisTemplate.hasKey(CUSTOM_SEARVICE_INFO_KEY + customServiceThirdId)) {
			customServiceVo = (CustomServiceVo) slaveRedisTemplate.opsForValue()
					.get(CUSTOM_SEARVICE_INFO_KEY + customServiceThirdId);
		} else {
			List<CustomService> customServiceList = ucenterService.getList(CustomService.class, "thirdId",
					customServiceThirdId);
			if (CollectionUtils.isNotEmpty(customServiceList)) {
				CustomService customService = customServiceList.get(0);
				customServiceVo.setCustomServiceId(customService.getCustomServiceId());
				customServiceVo.setHometown(customService.getHometown());
				customServiceVo.setNickname(customService.getNickname());
				customServiceVo.setHeadPic(customService.getHeadPic());
				customServiceVo.setPhoto(customService.getPhotos());
				customServiceVo.setHobby(customService.getHobby());
				customServiceVo.setServiceType(customService.getServiceType());
			}
		}
		masterRedisTemplate.opsForValue().set(CUSTOM_SEARVICE_INFO_KEY + customServiceThirdId, customServiceVo, 1,
				TimeUnit.DAYS);

		// 从缓存里取出后再做随机，防止缓存持续时间都是一张图
		if (StringUtils.isNotBlank(customServiceVo.getPhoto())) {
			String[] photoArray = customServiceVo.getPhoto().split(",");
			Random random = new Random();
			int num = random.nextInt(photoArray.length);
			String randomPhoto = photoArray[num];
			customServiceVo.setPhoto(randomPhoto);
		}

		return customServiceVo;
	}

}
