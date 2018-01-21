package com.hnair.consumer.user.api.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.ICreditApi;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.CreditBillDetail;
import com.hnair.consumer.user.model.CreditBillModify;
import com.hnair.consumer.user.model.CreditConversionDetail;
import com.hnair.consumer.user.model.CreditPartner;
import com.hnair.consumer.user.model.CreditPartnerMap;
import com.hnair.consumer.user.model.CreditPayDetail;
import com.hnair.consumer.user.model.CreditPriceRule;
import com.hnair.consumer.user.model.CreditRecommendProduct;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserIdentityConfirm;
import com.hnair.consumer.user.service.ICreditService;
import com.hnair.consumer.user.utils.CreditUtils;
import com.hnair.consumer.user.vo.CheckAvailableCreditRespVo;
import com.hnair.consumer.user.vo.CreditBillDetailQueryVo;
import com.hnair.consumer.user.vo.CreditExchangeReqVo;
import com.hnair.consumer.user.vo.CreditExchangeRespVo;
import com.hnair.consumer.user.vo.CreditExpendVo;
import com.hnair.consumer.user.vo.RefundCreditAndMoneyRespVo;
import com.hnair.consumer.user.vo.SynJinPengCreditReqVo;
import com.hnair.consumer.user.vo.SynJinPengCreditRespVo;
import com.hnair.consumer.user.vo.UserBindingReqVo;
import com.hnair.consumer.user.vo.UserBindingRespVo;
import com.hnair.consumer.user.vo.UserCountAndCreditRespVo;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.UserNameAndCreditVo;
import com.hnair.consumer.utils.CollectionUtils;
import com.hnair.consumer.utils.DateUtil;
import com.hnair.consumer.utils.HttpClientUtils;
import com.hnair.consumer.utils.bean.BeanUtils;
import com.hnair.consumer.utils.system.ConfigPropertieUtils;

@Component("creditApi")
public class CreditApiImpl implements ICreditApi {
	private static final Logger logger = LoggerFactory.getLogger(CreditApiImpl.class);

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	@Resource
	private ICreditService creditService;
	@SuppressWarnings("rawtypes")
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate masterRedisTemplate;
	@SuppressWarnings("rawtypes")
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate slaveRedisTemplate;

	/***
	 * 金鹏接口密钥
	 */
	private static final String jinPeng_signKey = ConfigPropertieUtils.getString("jinPeng_signKey");
	/***
	 * 获取金鹏余额接口路径
	 */
	private static final String query_JinPeng_balance_url = ConfigPropertieUtils.getString("query_JinPeng_balance_url");
	/***
	 * 金鹏登录验证接口路径
	 */
	private static final String query_JinPeng_login_url = ConfigPropertieUtils.getString("query_JinPeng_login_url");

	/**
	 * HiApp与金鹏积分互换接口路径
	 */
	private static final String query_JinPeng_credit_exchange_url = ConfigPropertieUtils
			.getString("query_JinPeng_credit_exchange_url");

	@Override
	public CreditAccount getCreditAccountByUserId(Long userId) {
		List<CreditAccount> accounts = ucenterService.getList(CreditAccount.class, "userId", userId, "status", 1);
		if (accounts!=null && accounts.size()>0) {
			return accounts.get(0);
		}
		return null;
	}

	@Override
	public UserMessageVo<CreditAccount> createCreditAccount(String mobile, Long userId) {
		return creditService.createCreditAccount(mobile, userId);
	}

	@Override
	public UserMessageVo<CreditBillDetail> creditIncome(Long userId, Integer taskType, String businessNo,
			Double payAmount) {
		return creditService.creditIncome(userId, taskType, businessNo, payAmount);
	}

	@Override
	public List<CreditBillDetail> QueryCreditBillDetails(CreditBillDetailQueryVo query) {

		return ucenterService.getList(CreditBillDetail.class, query);
	}

	@Override
	public UserMessageVo<CreditExpendVo> creditExpend(Long userId, Long payCount, String orderNo, Integer businessType,
			String productDesc) {
		return creditService.creditExpend(userId, payCount, orderNo, businessType, productDesc);
	}

	@Override
	public List<CreditRecommendProduct> getRecommendProducts() {
		return ucenterService.getList(CreditRecommendProduct.class);
	}

	@Override
	public void saveCreditModify(CreditBillModify req) {
		creditService.saveCreditModify(req);
	}

	@Override
	public void synJinPenCredit(Long userId) {

		SynJinPengCreditReqVo req = new SynJinPengCreditReqVo();
		req.setTimestamp(String.valueOf(System.currentTimeMillis()));
		req.setPrimaryKey(userId.toString());

		try {
			// 签名
			req.setSign(CreditUtils.toSign(req, jinPeng_signKey));
			Map<String, Object> map = BeanUtils.convertBeanToMap(req);
			Map<String, String> params = new HashMap<>();
			for (String m : map.keySet()) {
				params.put(m, map.get(m).toString());
			}
			logger.info("金鹏积分同步参数：" + JSON.toJSONString(req));
			String resultMsg = HttpClientUtils.sendPost(query_JinPeng_balance_url, params, "utf-8");
			logger.info("金鹏积分同步结果：" + userId + " - " + resultMsg);
			SynJinPengCreditRespVo resp = JSON.parseObject(resultMsg, SynJinPengCreditRespVo.class);
			if (resp.getErrorCode().equals("0")) {
				// 同步到数据库
				creditService.synPartnerCredit(userId, 10001l, Double.valueOf(resp.getAccountBalance()).longValue() );
			} else {
				logger.error("用户" + userId + "金鹏积分同步失败，同步结果：" + resultMsg);
			}
		} catch (Exception e) {
			logger.error("用户" + userId + "金鹏积分同步错误,错误信息：" + e.getMessage());
		}
	}

	@Override
	public CreditPartnerMap getCreditPartnerMapByUserId(Long userId,Integer partnerId) {
		CreditPartnerMap creditPartnerMap = ucenterService.get(CreditPartnerMap.class, "userId", userId,"partnerId",partnerId);
		return creditPartnerMap;
	}

	@Override
	public CreditPartner getCreditPartner(Integer partnerId) {
		CreditPartner creditPartner = ucenterService.get(CreditPartner.class, "partnerId", partnerId,"status",1,"currentTime",DateUtil.getCurrentDateTimeToStr2());
		return creditPartner;
	}

	@Override
	public void synPartnerCredit(Long userId, Long partnerId, Long balanceCount) {
		creditService.synPartnerCredit(userId, partnerId, balanceCount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserBindingRespVo userBinding(String userId, String loginId, String pwd) {
		UserBindingRespVo ubrv = new UserBindingRespVo();
		
		String cacheKey = "bind_jinPeng_userId" + userId;
		Object objectCount = slaveRedisTemplate.opsForValue().get(cacheKey);
		if (objectCount!=null) {
			if ((int)objectCount==0) {
				//同一用户，金鹏账号密码剩余验证错误次为0，则直接返回
				ubrv.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12018.getErrorCode().toString());
				ubrv.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12018.getErrorMessage());
				return ubrv;
			}
		}
		
		
		UserBindingReqVo userBinding = new UserBindingReqVo();
		UserIdentityConfirm userIdentityConfirm = new UserIdentityConfirm();
		userIdentityConfirm.setUserId(Long.valueOf(userId));
		UserIdentityConfirm uic = ucenterService.get(userIdentityConfirm);
		userBinding.setTimestamp(String.valueOf(System.currentTimeMillis()));
		userBinding.setPrimaryKey(userId);
		userBinding.setLoginId(loginId);
		userBinding.setPwd(pwd);
		userBinding.setLastNameEn("");
		userBinding.setFirtNameEn("");
		userBinding.setIdCode(uic.getIdentityCard());
		userBinding.setMobileNumber(uic.getPhone());
		userBinding.setLastNameZh(uic.getName().substring(0, 1));
		userBinding.setFirstNameZh(uic.getName().substring(1));

		
		try {
			userBinding.setSign(CreditUtils.toSign(userBinding, jinPeng_signKey));
			logger.info("url:"+query_JinPeng_login_url+"  signKey:"+jinPeng_signKey);
			logger.info("金鹏登录接口认证参数：" + JSON.toJSONString(userBinding));
			Map<String, Object> map = BeanUtils.convertBeanToMap(userBinding);
			Map<String, String> params = new HashMap<>();
			for (String m : map.keySet()) {
				params.put(m, map.get(m).toString());
			}
			String resultMsg = HttpClientUtils.sendPost(query_JinPeng_login_url, params, "utf-8");
			logger.info("金鹏登录接口认证结果：" + userId + " - " + resultMsg);
			ubrv = JSON.parseObject(resultMsg, UserBindingRespVo.class);
			if (ubrv.getErrorCode().equals("0")) {
				CreditPartnerMap cpm = ucenterService.get(CreditPartnerMap.class, "userId", userId);
				if(cpm == null){
					CreditPartnerMap creditPartnerMap = new CreditPartnerMap();
					creditPartnerMap.setUserId(Long.valueOf(userId));
					creditPartnerMap.setPartnerId(10001);
					creditPartnerMap.setBalanceCount(Double.valueOf(ubrv.getAccountBalance()).longValue());
					creditPartnerMap.setCreateTime(DateUtil.getCurrentDateTime());
					//保存到数据库
					ucenterService.save(creditPartnerMap);
				}else{
					logger.error("用户" + userId + "金鹏用户认证失败，认证重复");
				}
			} else {
				//如果是账号密码验证错误，则加入或修改缓存的金鹏账号密码验证错误次数
				if (ubrv.getErrorCode().contains("PasswordVerify")) {
					if (objectCount==null) {
						masterRedisTemplate.delete(cacheKey);
						masterRedisTemplate.opsForValue().set(cacheKey, 10);
						masterRedisTemplate.expire(cacheKey, 24, TimeUnit.HOURS);
					}else {
						masterRedisTemplate.opsForValue().set(cacheKey, (int)objectCount-1);
					}
				}
					
				
				ubrv.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12045.getErrorCode().toString());
				logger.error("用户" + userId + "金鹏用户认证失败，认证结果：" + resultMsg);
			}
		} catch (Exception e) {
			logger.error("用户" + userId + "金鹏用户认证错误,错误信息：" + e.getMessage());
		}
		return ubrv;
	}

	@Override
	public List<CreditPriceRule> queryCreditPriceRules(CreditPriceRule req) {
		return ucenterService.getList(CreditPriceRule.class, req);
	}

	@Override
	public CreditExchangeRespVo creditExchange(CreditExchangeReqVo creditExchangeReqVo) {
		logger.info("进入兑换方法");
		CreditExchangeRespVo cerv = new CreditExchangeRespVo();
		try {
			creditExchangeReqVo.setTimestamp(String.valueOf(System.currentTimeMillis()));
			creditExchangeReqVo.setSign(CreditUtils.toSign(creditExchangeReqVo, jinPeng_signKey));
			Map<String, Object> map = BeanUtils.convertBeanToMap(creditExchangeReqVo);
			Map<String, String> params = new HashMap<>();
			for (String m : map.keySet()) {
				params.put(m, map.get(m).toString());
			}
			logger.info("金鹏积分兑换参数：" + JSON.toJSONString(creditExchangeReqVo));
			logger.info("url:"+query_JinPeng_credit_exchange_url+"  key:"+jinPeng_signKey);
			String resultMsg = HttpClientUtils.sendPost(query_JinPeng_credit_exchange_url, params, "utf-8");
			logger.info("金鹏积分兑换结果：" + creditExchangeReqVo.getPrimaryKey() + " - " + resultMsg);
			cerv = JSON.parseObject(resultMsg, CreditExchangeRespVo.class);
			if (cerv.getErrorCode().equals("0")) {
				return cerv;
			} else {
				logger.error("用户" + creditExchangeReqVo.getPrimaryKey() + "金鹏积分兑换失败，认证结果：" + resultMsg);
			}
		} catch (Exception e) {
			logger.error("用户" + creditExchangeReqVo.getPrimaryKey() + "金鹏积分兑换接口调用错误,错误信息：" + e.getMessage());
			cerv.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12999.getErrorCode().toString());
			cerv.setErrorMessage(e.getMessage());
		}
		return cerv;
	}

	@Override
	public void updateUserAndJinPengCredit(CreditExchangeReqVo creditExchangeReqVo,CreditAccount ca, CreditBillDetail cbd, CreditPartnerMap cpm,
			CreditConversionDetail ccd) throws Exception {
		//调用兑换方法
		CreditExchangeRespVo result = creditExchange(creditExchangeReqVo);
		if (result.getErrorCode().equals("0")) {
			//若兑换成功，则同步数据库
			creditService.synUserAndJinPengCredit(ca, cbd, cpm, ccd);
		}else {
			throw new Exception(result.getErrorMessage());
		}
	}
	
	@Override
	public UserMessageVo<CheckAvailableCreditRespVo> CheckAvailableCredit(Long userId, Integer businessType,
			BigDecimal payAmount,Long payCredit) {
		UserMessageVo<CheckAvailableCreditRespVo> result = new UserMessageVo<>();
		result.setResult(true);
		if (userId == null || businessType == null || payAmount == null || payCredit == null) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}

		// 获取核心消费积分价值规则
		List<CreditPriceRule> creditPriceRules = ucenterService.getList(CreditPriceRule.class, "businessType",
				businessType, "status", 1, "currentTime", DateUtil.getCurrentDateTimeToStr2());
		if (CollectionUtils.isEmpty(creditPriceRules)) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12042.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12042.getErrorMessage());
			return result;
		}
		// 目前每种业务类型积分价值固定，后期如果每种业务有多个积分价值，修改此处
		CreditPriceRule creditPriceRule = creditPriceRules.get(0);

		// 获取积分账户信息
		CreditAccount account = ucenterService.get(CreditAccount.class, "userId", userId, "status", 1);
		if (account == null) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12039.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12039.getErrorMessage());
			return result;
		}
		// 积分余额
		Long available = account.getCreditsAvailable();

		/* 计算所需积分数量（向上取整）,钱兑换积分：X*divisor/multiplier
		long creditCount = (long) Math
				.ceil((payAmount.multiply(BigDecimal.valueOf(creditPriceRule.getDivisor()))
						.divide(BigDecimal.valueOf(creditPriceRule.getMultiplier()))).doubleValue());*/
		//计算最大能支付的积分
		Long maxPayCredit = 0L;
		if (available >= 1000) {
			creditPriceRule.getDeductionPercentage();
			if(payAmount.doubleValue()!=0){
				maxPayCredit = (long)Math.ceil(payAmount.multiply(BigDecimal.valueOf(creditPriceRule.getDeductionPercentage())).multiply(BigDecimal.valueOf(creditPriceRule.getDivisor())).divide(BigDecimal.valueOf(creditPriceRule.getMultiplier())).doubleValue());
			}
			if(maxPayCredit<1000){
				maxPayCredit = 0L;
			}else{
				if(maxPayCredit>available){
					maxPayCredit = (available /1000)*1000;
				}else{
					maxPayCredit = (maxPayCredit/1000)*1000;
				}
			}
		}
		Double creditPrice = 0d;
		if(payCredit!=0){
			creditPrice = BigDecimal.valueOf(payCredit).multiply(BigDecimal.valueOf(creditPriceRule.getMultiplier())).divide(BigDecimal.valueOf(creditPriceRule.getDivisor())).doubleValue();
		}
		//积分价值
		//积分使用信息
		CheckAvailableCreditRespVo vo=new CheckAvailableCreditRespVo();
		vo.setAvailable(available);
		vo.setCreditCount(payCredit);
		vo.setMaxPayCredit(maxPayCredit);
		vo.setDeductionPercentage(creditPriceRule.getDeductionPercentage());
		vo.setMultiplier(creditPriceRule.getMultiplier());
		vo.setDivisor(creditPriceRule.getDivisor());
		vo.setCreditPrice(creditPrice);
		vo.setUseInfo(creditPriceRule.getDescription());
		result.setT(vo);
		
		if (payCredit>maxPayCredit || (payCredit%1000) !=0 ) {
			result.setResult(false);
		}
		
		return result;
	}

	@Override
	public UserMessageVo<CreditBillDetail> CreditRefund(Long userId, Integer businessType, String orderNo,
			Long refundCount, String productDesc) {
		return creditService.CreditRefund(userId, businessType, orderNo, refundCount, productDesc);
	}

	@Override
	public boolean checkTime() {
		List<CreditPartner> creditPartner = ucenterService.getList(CreditPartner.class, "currentTime",DateUtil.getCurrentDateTimeToStr2());
		if(creditPartner == null){
			return false;
		}
		return true;
	}

	@Override
	public UserMessageVo<RefundCreditAndMoneyRespVo> getRefundCreditAndMoney(String orderNo, Double refundMoney) {
		UserMessageVo<RefundCreditAndMoneyRespVo> result = new UserMessageVo<>();
		result.setResult(true);
		if(StringUtils.isBlank(orderNo) || refundMoney==null){
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		//获取积分账户明细
		List<CreditBillDetail> creditBillDetails = ucenterService.getList(CreditBillDetail.class, "businessNo", orderNo);
		if(CollectionUtils.isEmpty(creditBillDetails)){
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12043.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12043.getErrorMessage());
			return result;
		}
		//剩余积分
		Long balanceCredit = 0l;
		for (CreditBillDetail cbd : creditBillDetails) {
			balanceCredit += cbd.getCreditCount();
		}
		// 获取核心消费积分价值规则
		List<CreditPriceRule> creditPriceRules = ucenterService.getList(CreditPriceRule.class, "businessType",
				creditBillDetails.get(0).getTaskType(), "status", 1, "currentTime", DateUtil.getCurrentDateTimeToStr2());
		if (CollectionUtils.isEmpty(creditPriceRules)) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12042.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12042.getErrorMessage());
			return result;
		}
		// 目前每种业务类型积分价值固定，后期如果每种业务有多个积分价值，修改此处
		CreditPriceRule creditPriceRule = creditPriceRules.get(0);
		//剩余积分的钱数
		double creditExchangeMoney = BigDecimal.valueOf(Math.abs(balanceCredit)).multiply(BigDecimal.valueOf(creditPriceRule.getMultiplier())).divide(BigDecimal.valueOf(creditPriceRule.getDivisor())).doubleValue();
		RefundCreditAndMoneyRespVo vo = new RefundCreditAndMoneyRespVo();
		vo.setDivisor(creditPriceRule.getDivisor());
		vo.setMultiplier(creditPriceRule.getMultiplier());
		if(refundMoney > creditExchangeMoney){
			vo.setCredit(Math.abs(balanceCredit));
			vo.setMoney(BigDecimal.valueOf(refundMoney).subtract(BigDecimal.valueOf(creditExchangeMoney)).doubleValue());
		}else{
			vo.setCredit((long)Math.ceil(BigDecimal.valueOf(refundMoney).multiply(BigDecimal.valueOf(creditPriceRule.getDivisor())).divide(BigDecimal.valueOf(creditPriceRule.getMultiplier())).doubleValue()));
			vo.setMoney(0.00);
		}
		result.setT(vo);
		return result;
	}

	@Override
	public UserMessageVo<UserCountAndCreditRespVo> getDonatePeopleAndCredit(String taskType,Long userId) {
		UserMessageVo<UserCountAndCreditRespVo> result = new UserMessageVo<>();
		result.setResult(true);
		if(StringUtils.isBlank(taskType) || userId == null){
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		
		UserCountAndCreditRespVo userCountAndCredit = new UserCountAndCreditRespVo();
		CreditAccount creditAccount = getCreditAccountByUserId(userId);
		if(creditAccount == null){
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12039.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12039.getErrorMessage());
			return result;
		}
		List<CreditBillDetail> creditBillDetails = ucenterService.getListBySqlId(CreditBillDetail.class, "getDonateUser", "taskType",taskType,"creditType",2);
		if(CollectionUtils.isEmpty(creditBillDetails)){
			userCountAndCredit.setCreditCount(0L);
			userCountAndCredit.setUserCount(0);
			result.setT(userCountAndCredit);
			return result;
		}
		
		userCountAndCredit = ucenterService.getBySqlId(CreditBillDetail.class, "selectUserCountAndCredit", "taskType",taskType,"creditType",2);
		userCountAndCredit.setCreditBalance(creditAccount.getCreditsAvailable());
		for (CreditBillDetail creditBillDetail : creditBillDetails) {
			UserNameAndCreditVo unac = new UserNameAndCreditVo();
			UserBaseInfo userBaseInfo = ucenterService.get(UserBaseInfo.class, "userId",creditBillDetail.getUserId());
			unac.setUserName(new StringBuffer(userBaseInfo.getUserName().substring(0, 1)).append("****").toString());
			unac.setDonateCredit(Math.abs(creditBillDetail.getCreditCount()));
			userCountAndCredit.getUserNameAndCredits().add(unac);
		}
		userCountAndCredit.setCreditCount(Math.abs(userCountAndCredit.getCreditCount()));
		result.setT(userCountAndCredit);
		
		return result;
	}

	@Override
	public UserMessageVo<UserCountAndCreditRespVo> getDonatePeopleAndCreditCount(String taskType) {
		UserMessageVo<UserCountAndCreditRespVo> result = new UserMessageVo<>();
		result.setResult(true);
		if(StringUtils.isBlank(taskType)){
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		
		UserCountAndCreditRespVo userCountAndCredit = new UserCountAndCreditRespVo();
		
		List<CreditBillDetail> creditBillDetails = ucenterService.getListBySqlId(CreditBillDetail.class, "getDonateUser", "taskType",taskType,"creditType",2);
		if(CollectionUtils.isEmpty(creditBillDetails)){
			userCountAndCredit.setCreditCount(0L);
			userCountAndCredit.setUserCount(0);
			result.setT(userCountAndCredit);
			return result;
		}
		
		userCountAndCredit = ucenterService.getBySqlId(CreditBillDetail.class, "selectUserCountAndCredit", "taskType",taskType,"creditType",2);
		for (CreditBillDetail creditBillDetail : creditBillDetails) {
			UserNameAndCreditVo unac = new UserNameAndCreditVo();
			UserBaseInfo userBaseInfo = ucenterService.get(UserBaseInfo.class, "userId",creditBillDetail.getUserId());
			unac.setUserName(new StringBuffer(userBaseInfo.getUserName().substring(0, 1)).append("****").toString());
			unac.setDonateCredit(Math.abs(creditBillDetail.getCreditCount()));
			userCountAndCredit.getUserNameAndCredits().add(unac);
		}
		userCountAndCredit.setCreditCount(Math.abs(userCountAndCredit.getCreditCount()));
		result.setT(userCountAndCredit);
		return result;
	}

	@Override
	public void synCreditFromFenXiagn(CreditAccount ca, CreditBillDetail cbd, CreditConversionDetail ccd) {
		creditService.synCreditFromFenXiagn(ca, cbd, ccd);
	}

	@Override
	public CreditPayDetail getCreditPayDetail(String businessNo) {
		List<CreditPayDetail> list = new ArrayList<>();
		try {
			list = ucenterService.getList(CreditPayDetail.class, "businessNo",businessNo);
		} catch (Exception e) {
			logger.error("查询数据库失败："+e.getMessage());
		}
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public UserMessageVo<CreditBillDetail> signInPresentedCredit(Long userId, Long creditCount) {
		return creditService.signInPresentedCredit(userId, creditCount);
	}
	
}
