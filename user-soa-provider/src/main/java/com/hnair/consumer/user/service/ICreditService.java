package com.hnair.consumer.user.service;

import java.util.List;
import java.util.Map;

import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.CreditBillDetail;
import com.hnair.consumer.user.model.CreditBillModify;
import com.hnair.consumer.user.model.CreditConversionDetail;
import com.hnair.consumer.user.model.CreditPartnerMap;
import com.hnair.consumer.user.vo.CreditExpendVo;
import com.hnair.consumer.user.vo.UserMessageVo;

public interface ICreditService {
	/**
	 * 创建积分账户
	 * @param mobile
	 * @param userId
	 * @return
	 */
	public UserMessageVo<CreditAccount> createCreditAccount(String mobile, Long userId);
	
	/**
	 * 积分收入
	 * @param userId
	 * @param taskType
	 * @param businessNo
	 * @param payAmount
	 * @return
	 */
	public UserMessageVo<CreditBillDetail> creditIncome(Long userId,Integer taskType,String businessNo,Double payAmount);
	
	/**
	 * 积分消费
	 * @param userId
	 * @param payCount
	 * @param orderNo
	 * @param businessType 1000-积分商城；1000+orderType-核心消费订单类型
	 * @param productDesc
	 * @return
	 */
	public UserMessageVo<CreditExpendVo> creditExpend(Long userId,Long payCount,String orderNo,Integer businessType,String productDesc);
	
	/**
	 * 积分冲正
	 * @param req
	 */
	public void saveCreditModify(CreditBillModify req);
	
	/**
	 * 同步指定用户的指定第三方积分余额
	 * @param userId 用户id
	 * @param partnerId 第三方id
	 * @param balanceCount 积分余额
	 */
	public void synPartnerCredit(Long userId,Long partnerId,Long balanceCount);
	
	/**
	 * 积分退回
	 * @param userId
	 * @param businessType
	 * @param orderNo
	 * @param refundCount
	 * @param productDesc
	 * @return
	 */
	public UserMessageVo<CreditBillDetail> CreditRefund(Long userId,Integer businessType,String orderNo,Long refundCount,String productDesc);
	
	/**
	 * 积分兑换后，更新数据库信息
	 * @param ca
	 * @param cbd
	 * @param cpm
	 * @param ccd
	 */
	public void synUserAndJinPengCredit(CreditAccount ca,CreditBillDetail cbd,CreditPartnerMap cpm,CreditConversionDetail ccd);
	
	/**
	 * 记录积分兑换信息，更新积分
	 * @param ca
	 * @param cbd
	 * @param ccd
	 */
	public void synCreditFromFenXiagn(CreditAccount ca,CreditBillDetail cbd,CreditConversionDetail ccd);
	

	/**
	 * 签到赠送积分
	 * @param userId
	 * @param creditCount
	 * @return
	 * @author 陶嘉骏
	 * @date 2018年1月10日
	 */
	public UserMessageVo<CreditBillDetail> signInPresentedCredit(Long userId,Long creditCount);
}
