package com.hnair.consumer.user.api.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IThirdUserApi;
import com.hnair.consumer.user.model.ThirdPartyUserInfo;
import com.hnair.consumer.user.service.IThirdUserService;

@Component("thirdUserApi")
public class ThirdUserApiImpl implements IThirdUserApi {
	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;

	@Autowired
	private IThirdUserService thirdUserService;

	@Override
	public ThirdPartyUserInfo getThirdUserInfoByOpenId(String openId) {
		return thirdUserService.getThirdUserInfoByOpenId(openId);
	}

	@Override
	public ThirdPartyUserInfo getThirdUserInfoBySessionId(String sessionId) {
		return thirdUserService.getThirdUserInfoBySessionId(sessionId);
	}

	@Override
	public void updateThirdUserInfo(ThirdPartyUserInfo partyUserInfo) {
		thirdUserService.updateThirdUserInfo(partyUserInfo);
	}

	@Override
	public void saveThirdUserInfo(ThirdPartyUserInfo partyUserInfo) {
		thirdUserService.saveThirdUserInfo(partyUserInfo);
	}
}
