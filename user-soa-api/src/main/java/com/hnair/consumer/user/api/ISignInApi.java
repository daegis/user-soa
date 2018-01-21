package com.hnair.consumer.user.api;

import java.util.List;
import java.util.Map;

import com.hnair.consumer.user.model.PrizeRecord;
import com.hnair.consumer.user.model.SignInRecord;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.vo.SignInVo;

/**
 * 签到接口
 */
public interface ISignInApi {
	/**
	 * 签到
	 * @param signInVo
	 * @return
	 */
	public SignInVo signIn(UserBaseInfo user);
	
	/**
	 * 签到主页
	 * @param userId
	 * @return
	 */
	public SignInVo getSignInHomePage(Long userId);
	
	public PrizeRecord getPrizeRecord(Map<String,Object> queryMap);
	
	public PrizeRecord getPrizeRecordBySignInId(Long signRecordId);
	
	public List<PrizeRecord> getPrizeRecordByUserId(Long userId);
	
	public void updatePrizeRecord(PrizeRecord prizeRecord);

	public void updatePrizeRecordByRecordId(Long recordId, int status);

	public SignInRecord getSignInRecordListById(String signInRecordId);
	
}
