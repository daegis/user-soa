package com.hnair.consumer.user.api.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hnair.consumer.user.api.IUserExperienceApi;
import com.hnair.consumer.user.service.IUserExperienceService;

@Component("userExperienceApi")
public class UserExperienceApiImpl implements IUserExperienceApi {

	@Resource
	private IUserExperienceService userExperienceService;
	
	@Override
	public Set<String> addExperienceByOrderNo(List<String> orderNos) {
		return userExperienceService.addExperienceByOrderNo(orderNos);
	}

	@Override
	public void calculateUserGrade(List<Long> userIds) {
		userExperienceService.calculateUserGrade(userIds);
	}

}
