package com.hnair.consumer.user.api.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hnair.consumer.user.api.ISignInActiveApi;
import com.hnair.consumer.user.service.ISignInActiveService;
import com.hnair.consumer.user.vo.SignInActiveVo;

@Component("signInActiveApi")
public class SignInActiveApiImpl implements ISignInActiveApi {

	@Resource
	private ISignInActiveService signInActiveService;

	@Override
	public SignInActiveVo getSignInActiveByStatus(int status, Date date) {
		return signInActiveService.getSignInActiveByStatus(status, date);
	}

	@Override
	public SignInActiveVo getSignInActiveById(Long id) {
		return signInActiveService.getSignInActiveById(id);
	}
}