package com.hnair.consumer.user.api;


import java.math.BigDecimal;
import java.util.List;

import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.CreditBillDetail;
import com.hnair.consumer.user.model.CreditBillModify;
import com.hnair.consumer.user.model.CreditConversionDetail;
import com.hnair.consumer.user.model.CreditPartner;
import com.hnair.consumer.user.model.CreditPartnerMap;
import com.hnair.consumer.user.model.CreditPayDetail;
import com.hnair.consumer.user.model.CreditPriceRule;
import com.hnair.consumer.user.model.CreditRecommendProduct;
import com.hnair.consumer.user.model.UserIdentityConfirm;
import com.hnair.consumer.user.vo.CheckAvailableCreditRespVo;
import com.hnair.consumer.user.vo.CreditBillDetailQueryVo;
import com.hnair.consumer.user.vo.CreditExchangeReqVo;
import com.hnair.consumer.user.vo.CreditExchangeRespVo;
import com.hnair.consumer.user.vo.CreditExpendVo;
import com.hnair.consumer.user.vo.RefundCreditAndMoneyRespVo;
import com.hnair.consumer.user.vo.UserBindingRespVo;
import com.hnair.consumer.user.vo.UserCountAndCreditRespVo;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * 积分接口
 * @author TJJ
 *
 */
public interface ICreditApi {
	/**
	 * 查询指定用户的积分账户
	 * @param userId
	 * @return
	 */
	public CreditAccount getCreditAccountByUserId(Long userId);
	
	/**
	 * 创建积分账户
	 * @param mobile
	 * @param userId
	 */
	public UserMessageVo<CreditAccount> createCreditAccount(String mobile,Long userId);
	
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
	 * 查询积分交易明细
	 * @param query
	 * @return
	 */
	public List<CreditBillDetail> QueryCreditBillDetails(CreditBillDetailQueryVo query);
	
	/**
	 * 积分消费
	 * @param userId
	 * @param payCount
	 * @param orderNo
	 * @param businessType 1000-积分商城； 酒店-1001;旅游产品-1002;机票-1004
	 * @param productDesc
	 * @return
	 */
	public UserMessageVo<CreditExpendVo> creditExpend(Long userId,Long payCount,String orderNo,Integer businessType,String productDesc);
	
	/**
	 * 获取积分商城推荐产品集合
	 * @return
	 */
	public List<CreditRecommendProduct> getRecommendProducts();
	
	/**
	 * 积分冲正
	 * @param req
	 */
	public void saveCreditModify(CreditBillModify req);
	
	/**
	 * 同步金鹏积分
	 */
	public void synJinPenCredit(Long userId);
	
	/**
	 * 获取指定用户指定关联账户
	 * @param userId
	 * @return
	 */
	public CreditPartnerMap getCreditPartnerMapByUserId(Long userId,Integer partnerId);
	
	/**
	 * 获取用户金鹏信息
	 * @param partnerId 用户金鹏ID
	 * @return
	 */
	public CreditPartner getCreditPartner(Integer partnerId);
	
	/**
	 * 同步指定用户的指定第三方积分余额
	 * @param userId 用户id
	 * @param partnerId 第三方id
	 * @param balanceCount 积分余额
	 */
	public void synPartnerCredit(Long userId,Long partnerId,Long balanceCount);
	
	/**
	 * 金鹏用户认证接口
	 * @param userId
	 * @param loginId
	 * @param pwd
	 * @return
	 */
	public UserBindingRespVo userBinding(String userId,String loginId,String pwd);

	/**
	 * 查询积分价值规则
	 * @param req
	 * @return
	 */
	public List<CreditPriceRule> queryCreditPriceRules(CreditPriceRule req);
	
	/**
	 * 积分兑换接口
	 * @param creditExchangeReqVo
	 * @return
	 */
	public CreditExchangeRespVo creditExchange(CreditExchangeReqVo creditExchangeReqVo);
	
	/**
	 * 验证是否可以积分支付
	 * @param userId 
	 * @param businessType 酒店-1001;旅游产品-1002;机票-1004
	 * @param payAmount 支付金额
	 * @param paycredit 支付积分
	 * @return
	 */
	public UserMessageVo<CheckAvailableCreditRespVo> CheckAvailableCredit(Long userId,Integer businessType,BigDecimal payAmount,Long payCredit);
	
	/**
	 * 积分退回
	 * @param userId 
	 * @param businessType 业务类型：1000-积分商城； 酒店-1001;旅游产品-1002;机票-1004
	 * @param orderNo 订单编号
	 * @param refundCount 退还积分数量
	 * @param productDesc  备注
	 * @return
	 */
	public UserMessageVo<CreditBillDetail> CreditRefund(Long userId,Integer businessType,String orderNo,Long refundCount,String productDesc);
	
	/**
	 * 积分兑换后，更新数据库信息
	 * @param creditExchangeReqVo 兑换参数
	 * @param ca 积分账户
	 * @param cbd 积分账户明细
	 * @param cpm 关联积分账户
	 * @param ccd 积分兑换明细
	 */
	public void updateUserAndJinPengCredit(CreditExchangeReqVo creditExchangeReqVo,CreditAccount ca,CreditBillDetail cbd,CreditPartnerMap cpm,CreditConversionDetail ccd) throws Exception;
	
	/**
	 * 校验时间是否合理
	 * @return
	 */
	public boolean checkTime();
	
	/**
	 * 获取返回的金额和积分
	 * @param orderNo 业务单号
	 * @param RefundBalance 返回金额
	 * @return
	 */
	public UserMessageVo<RefundCreditAndMoneyRespVo> getRefundCreditAndMoney(String orderNo,Double refundMoney);
	
	/**
	 * 获取积分捐赠的人数和积分和当前用户剩余积分
	 * @param taskType 任务类型
	 * @param userId 用户id
	 * @return
	 */
	public UserMessageVo<UserCountAndCreditRespVo> getDonatePeopleAndCredit(String taskType,Long userId);
	/**
	 * 获取积分捐赠的人数和积分
	 * @param taskType 任务类型
	 * @return
	 */
	public UserMessageVo<UserCountAndCreditRespVo> getDonatePeopleAndCreditCount(String taskType);
	
	/**
	 * 保存积分兑换信息，更新积分
	 * @param ca 积分账户
	 * @param cbd 积分账户明细
	 * @param ccd 积分兑换明细
	 */
	public void synCreditFromFenXiagn(CreditAccount ca,CreditBillDetail cbd,CreditConversionDetail ccd);
	
	/**
	 * 获取积分支付明细
	 * @param businessNo 订单号
	 * @return
	 */
	public CreditPayDetail getCreditPayDetail(String businessNo);
	

	/**
	 * 签到赠送积分
	 * @param userId 签到用户Id
	 * @param creditCount 赠送积分数量
	 * @return 
	 * @author 陶嘉骏
	 * @date 2018年1月10日
	 */
	public UserMessageVo<CreditBillDetail> signInPresentedCredit(Long userId,Long creditCount);
}
