package com.hnair.consumer.user.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IUserIntegralApi;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserIntegral;
import com.hnair.consumer.user.service.IUserIntegralService;
import com.hnair.consumer.user.vo.OpenUserIntegralVo;
import com.hnair.consumer.user.vo.UserMessageVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户积分接口
 * @author zhangxianbin
 *
 */
@Component("userIntegralApi")
@Slf4j
public class UserIntegralApiImpl implements IUserIntegralApi {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	
	@Resource
	private IUserIntegralService userIntegralService;
	
	@Override
	public UserMessageVo<OpenUserIntegralVo> queryUerIntegralByMobile(String mobile) {
		UserMessageVo<OpenUserIntegralVo> result = new UserMessageVo<OpenUserIntegralVo>();
		if(StringUtils.isBlank(mobile)){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		List<UserBaseInfo> users = ucenterService.getListFromMaster(UserBaseInfo.class, "mobile", mobile);
		if(CollectionUtils.isEmpty(users)){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12008.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12008.getErrorMessage());
			return result;
		}
		OpenUserIntegralVo vo = new OpenUserIntegralVo();
		vo.setIntegral(users.get(0).getIntegral() != null ? users.get(0).getIntegral() : 0);
		vo.setMobile(mobile);
		vo.setUserName(users.get(0).getUserName());
		vo.setGender(users.get(0).getGender());
		result.setT(vo);
		result.setResult(true);
		return result;
	}

	@Override
	public UserMessageVo<OpenUserIntegralVo> consumeUerIntegralByMobile(UserIntegral userIntegral) {
		UserMessageVo<OpenUserIntegralVo> result = new UserMessageVo<OpenUserIntegralVo>();
		if(StringUtils.isBlank(userIntegral.getMobile()) || userIntegral.getChangeNum() == null || userIntegral.getChangeNum() < 1 || StringUtils.isBlank(userIntegral.getChangeFrom())){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		try {
			result = userIntegralService.consumeUerIntegralByMobile(userIntegral);
		} catch (Exception e) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12999.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12999.getErrorMessage());
			log.error("", e);	
		} finally{
			UserMessageVo<OpenUserIntegralVo> integral = this.queryUerIntegralByMobile(userIntegral.getMobile());
			if(integral.isResult()){
				result.setT(integral.getT());
			}
		}
		return result;
	}

	@Override
	public UserMessageVo<OpenUserIntegralVo> refundUerIntegral(UserIntegral userIntegral) {
		UserMessageVo<OpenUserIntegralVo> result = new UserMessageVo<OpenUserIntegralVo>();
		if (StringUtils.isBlank(userIntegral.getMobile()) || userIntegral.getChangeNum() == null
				|| userIntegral.getChangeNum() < 1 || StringUtils.isBlank(userIntegral.getChangeFrom())
				|| StringUtils.isBlank(userIntegral.getChangeRelationNo())) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		try {
			result = userIntegralService.refundUerIntegral(userIntegral);
		} catch (Exception e) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12999.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12999.getErrorMessage());
			log.error("", e);			
		} finally{
			UserMessageVo<OpenUserIntegralVo> integral = this.queryUerIntegralByMobile(userIntegral.getMobile());
			if(integral.isResult()){
				result.setT(integral.getT());
			}
		}
		return result;
	}

}
