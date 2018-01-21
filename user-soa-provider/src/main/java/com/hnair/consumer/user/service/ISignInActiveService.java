package com.hnair.consumer.user.service;

import java.util.Date;

import com.hnair.consumer.user.vo.SignInActiveVo;

public interface ISignInActiveService {

	/**
	 * 查找当前时间生效的签到活动
	 * @return
	 */
	public SignInActiveVo getSignInActiveByStatus(int status, Date date);
	public SignInActiveVo getSignInActiveById(Long id);
	
}
