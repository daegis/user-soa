package com.hnair.consumer.user.api;

import java.util.Date;

import com.hnair.consumer.user.vo.SignInActiveVo;

/**
 * 签到活动接口
 * @author yangdong
 *
 */
public interface ISignInActiveApi {

	/**
	 * 查找当前时间生效的签到活动
	 * @return
	 */
	public SignInActiveVo getSignInActiveByStatus(int status, Date date);
	public SignInActiveVo getSignInActiveById(Long id);

}
