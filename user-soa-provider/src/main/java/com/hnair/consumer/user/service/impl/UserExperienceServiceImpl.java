package com.hnair.consumer.user.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.order.api.IOrderBaseApi;
import com.hnair.consumer.system.api.ISystemApi;
import com.hnair.consumer.user.service.IUserExperienceService;

@Service
public class UserExperienceServiceImpl implements IUserExperienceService {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	
	@Resource
	private ISystemApi systemApi;
	
	@Resource
    IOrderBaseApi orderBaseApi;
	
	// 订单完成N天后给用户增加经验
	private static final String USER_ADD_EXPERIENCE_ORDER_END_DAYS = "user_add_experience_order_end_days";
	
	@Override
	public Set<String> addExperienceByOrderNo(List<String> orderNos) {
		String days = systemApi.getProperty(USER_ADD_EXPERIENCE_ORDER_END_DAYS, "7");
		
		return null;
	}

	@Override
	public void calculateUserGrade(List<Long> userIds) {
		

	}

}
