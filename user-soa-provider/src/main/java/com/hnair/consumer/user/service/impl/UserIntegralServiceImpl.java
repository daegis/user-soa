package com.hnair.consumer.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserIntegral;
import com.hnair.consumer.user.service.IUserIntegralService;
import com.hnair.consumer.user.vo.OpenUserIntegralVo;
import com.hnair.consumer.user.vo.UserMessageVo;

@Service
public class UserIntegralServiceImpl implements IUserIntegralService {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<OpenUserIntegralVo> consumeUerIntegralByMobile(UserIntegral userIntegral) {
		UserMessageVo<OpenUserIntegralVo> result = new UserMessageVo<OpenUserIntegralVo>();	
		List<UserBaseInfo> users = ucenterService.getListFromMaster(UserBaseInfo.class, "mobile", userIntegral.getMobile());
		UserBaseInfo user = users.get(0);
		List<UserIntegral> userIntegrals = ucenterService.getListFromMaster(UserIntegral.class, "changeFromNo", userIntegral.getChangeFromNo(), "changeType", userIntegral.getChangeType(), "changeFrom", userIntegral.getChangeFrom());
		if(!CollectionUtils.isEmpty(userIntegrals)){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12034.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12034.getErrorMessage());
			return result;
		}		
		int updateCols = ucenterService.updateBySqlId(UserBaseInfo.class, "consumeIntegral", "mobile", userIntegral.getMobile(), "integral", userIntegral.getChangeNum(),"updateVersion",user.getUpdateVersion() == null ? 1 : user.getUpdateVersion() + 1);
		if(updateCols < 1){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12032.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12032.getErrorMessage());
			return result;
		}		
		UserIntegral userIntegralForSave = new UserIntegral();
		userIntegralForSave.setChangeFrom(userIntegral.getChangeFrom());
		userIntegralForSave.setChangeFromNo(userIntegral.getChangeFromNo());
		userIntegralForSave.setChangeNum(userIntegral.getChangeNum());		
		userIntegralForSave.setDeviceType(userIntegral.getDeviceType());
		userIntegralForSave.setCreateTime(new Date());
		userIntegralForSave.setLastModifyTime(userIntegralForSave.getCreateTime());
		userIntegralForSave.setUserId(user.getUserId());
		userIntegralForSave.setMobile(user.getMobile());
		userIntegralForSave.setChangeType(2);
		userIntegralForSave.setChangeDesc(userIntegral.getChangeDesc());
		ucenterService.save(userIntegralForSave);
		result.setResult(true);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<OpenUserIntegralVo> refundUerIntegral(UserIntegral userIntegral) {
		UserMessageVo<OpenUserIntegralVo> result = new UserMessageVo<OpenUserIntegralVo>();	
		List<UserBaseInfo> users = ucenterService.getListFromMaster(UserBaseInfo.class, "mobile", userIntegral.getMobile());
		UserBaseInfo user = users.get(0);
		// 查询是否已退款过
		List<UserIntegral> refundUserIntegrals = ucenterService.getListFromMaster(UserIntegral.class, "changeFromNo", userIntegral.getChangeFromNo(), "changeType", 1, "changeFrom", userIntegral.getChangeFrom());
		if(!CollectionUtils.isEmpty(refundUserIntegrals)){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12034.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12034.getErrorMessage());
			return result;
		}
		List<UserIntegral> oldRefundUserIntegrals = ucenterService.getListFromMaster(UserIntegral.class, "changeRelationNo", userIntegral.getChangeRelationNo(), "changeType", 1, "changeFrom", userIntegral.getChangeFrom());
		if(!CollectionUtils.isEmpty(oldRefundUserIntegrals)){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12034.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12034.getErrorMessage());
			return result;
		}
		// 查询对应的消费单是否存在
		List<UserIntegral> consumeUserIntegrals = ucenterService.getListFromMaster(UserIntegral.class, "changeFromNo", userIntegral.getChangeRelationNo(), "changeType", 2, "changeFrom", userIntegral.getChangeFrom());
		if(CollectionUtils.isEmpty(consumeUserIntegrals)){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12035.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12035.getErrorMessage());
			return result;
		}
		// 验证退款金额合法性
		if(consumeUserIntegrals.get(0).getChangeNum().intValue() < userIntegral.getChangeNum().intValue()){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12036.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12036.getErrorMessage());
			return result;
		}		
		int updateCols = ucenterService.updateBySqlId(UserBaseInfo.class, "refundIntegral", "mobile", userIntegral.getMobile(), "integral", userIntegral.getChangeNum(),"updateVersion",user.getUpdateVersion() == null ? 1 : user.getUpdateVersion() + 1);
		if(updateCols < 1){
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12032.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12032.getErrorMessage());
			return result;
		}		
		UserIntegral userIntegralForSave = new UserIntegral();
		userIntegralForSave.setChangeFrom(userIntegral.getChangeFrom());
		userIntegralForSave.setChangeFromNo(userIntegral.getChangeFromNo());
		userIntegralForSave.setChangeNum(userIntegral.getChangeNum());		
		userIntegralForSave.setDeviceType(userIntegral.getDeviceType());
		userIntegralForSave.setCreateTime(new Date());
		userIntegralForSave.setLastModifyTime(userIntegralForSave.getCreateTime());
		userIntegralForSave.setUserId(user.getUserId());
		userIntegralForSave.setMobile(user.getMobile());
		userIntegralForSave.setChangeType(1);
		userIntegralForSave.setChangeRelationNo(userIntegral.getChangeRelationNo());
		userIntegralForSave.setChangeDesc(userIntegral.getChangeDesc());
		ucenterService.save(userIntegralForSave);
		result.setResult(true);
		return result;
	}
	
}
