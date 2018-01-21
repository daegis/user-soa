package com.hnair.consumer.user.api.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.ISignInApi;
import com.hnair.consumer.user.model.PrizeRecord;
import com.hnair.consumer.user.model.SignInRecord;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.service.ISignInService;
import com.hnair.consumer.user.vo.SignInVo;
/**
 * 积分签到
 * @author YangDong
 *
 */
@Component("signInApi")
public class SignInApiImpl implements ISignInApi{

	@Resource(name = "signInService")
	private ISignInService signInService;
	
	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;

	@Override
	public SignInVo getSignInHomePage(Long userId) {
		return signInService.getSignInHomePage(userId);
	}

	@Override
	public SignInVo signIn(UserBaseInfo user) {
		return signInService.signIn(user);
	}

	@Override
	public PrizeRecord getPrizeRecord(Map<String, Object> queryMap) {
		return signInService.getPrizeRecord(queryMap);
	}

	
	@Override
	public void updatePrizeRecord(PrizeRecord prizeRecord) {
		ucenterService.update(prizeRecord);		
	}

	@Override
	public PrizeRecord getPrizeRecordBySignInId(Long signInRecordId) {
		PrizeRecord prizeRecord = new PrizeRecord();
		prizeRecord.setSignInRecordId(signInRecordId);
		return ucenterService.get(prizeRecord);
	}

	@Override
	public void updatePrizeRecordByRecordId(Long recordId,int status) {
		PrizeRecord prizeRecord = this.getPrizeRecordBySignInId(recordId);
		// 修改中奖记录中的领奖状态
		prizeRecord.setDrawStatus(status);
		this.updatePrizeRecord(prizeRecord);
	}

	@Override
	public List<PrizeRecord> getPrizeRecordByUserId(Long userId) {
		return ucenterService.getList(PrizeRecord.class,"userId",userId);
	}

	@Override
	public SignInRecord getSignInRecordListById(String signInRecordId) {
		return ucenterService.get(signInRecordId, SignInRecord.class);
	}

}
