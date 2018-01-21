package com.hnair.consumer.user.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.hnair.consumer.dao.spi.ICommonDao;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.CreditBillDetail;
import com.hnair.consumer.user.model.CreditBillModify;
import com.hnair.consumer.user.model.CreditConversionDetail;
import com.hnair.consumer.user.model.CreditPartnerMap;
import com.hnair.consumer.user.model.CreditPayDetail;
import com.hnair.consumer.user.model.CreditPriceRule;
import com.hnair.consumer.user.model.CreditPromotionRule;
import com.hnair.consumer.user.model.CreditRule;
import com.hnair.consumer.user.service.ICreditService;
import com.hnair.consumer.user.vo.CreditExchangeRespVo;
import com.hnair.consumer.user.vo.CreditExpendVo;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.utils.DateUtil;

@Service("creditService")
public class CreditServiceImpl implements ICreditService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);

	@Resource(name = "ucenterCommonDao")
	private ICommonDao ucenterCommonDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<CreditAccount> createCreditAccount(String mobile, Long userId) {
		UserMessageVo<CreditAccount> result = new UserMessageVo<>();

		// 验证是否已经开户
		List<CreditAccount> accounts;
		accounts = ucenterCommonDao.getListFromMaster(CreditAccount.class, "userId", userId);
		if (CollectionUtils.isNotEmpty(accounts)) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12013.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12013.getErrorMessage());
			result.setResult(false);
			result.setT(accounts.get(0));
			return result;
		}
		accounts = ucenterCommonDao.getListFromMaster(CreditAccount.class, "mobile", mobile);
		if (CollectionUtils.isNotEmpty(accounts)) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12013.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12013.getErrorMessage());
			result.setResult(false);
			result.setT(accounts.get(0));
			return result;
		}

		// 添加积分账户
		CreditAccount account = new CreditAccount();
		Date date = new Date();
		account.setCreateTime(date);
		account.setCreditsAvailable(0l);
		account.setCreditsExpend(0l);
		account.setCreditsIncome(0l);
		account.setMobile(mobile);
		account.setStatus(1);
		account.setUpdateTime(date);
		account.setUserId(userId);
		try {
			ucenterCommonDao.save(account);
			UserMessageVo<CreditBillDetail> creditIncome = creditIncome(userId, 5, "", 0d);
			if (!creditIncome.isResult()) {
				LOGGER.error("开户送积分失败：" + creditIncome.getErrorMessage() + "userId=" + userId);
			}
		} catch (Exception e) {
			LOGGER.error("创建积分账户错误：" + e.getMessage() + "userId=" + userId);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12037.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12037.getErrorMessage());
			result.setResult(false);
		}

		result.setResult(true);
		result.setT(account);

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<CreditBillDetail> creditIncome(Long userId, Integer taskType, String businessNo,
			Double payAmount) {
		UserMessageVo<CreditBillDetail> result = new UserMessageVo<CreditBillDetail>();
		// 积分账户
		CreditAccount account = ucenterCommonDao.getFromMaster(CreditAccount.class, "userId", userId);
		if (account == null) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12039.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12039.getErrorMessage());
			result.setResult(false);
			return result;
		}

		Date date = new Date();
		// 对应任务规则
		CreditRule rule = ucenterCommonDao.get(CreditRule.class, "taskType", taskType, "status", 1, "currentTime",
				date);
		if (rule == null) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12038.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12038.getErrorMessage());
			result.setResult(false);
			return result;
		}
		// 积分增加值
		long creditCount = 0l;

		// 根据积分规则计算积分增加值
		if (rule.getCalculateType() == 1) {
			creditCount = (long) rule.getCalculateRule().doubleValue();
		} else {
			creditCount = (long) Math.floor(payAmount * rule.getCalculateRule());
		}

		// 在积分规则基础上，根据积分促销规则计算最终积分增加值

		CreditPromotionRule promotionRule = ucenterCommonDao.get(CreditPromotionRule.class, "taskType", taskType,
				"startDate", date, "endDate", date, "status", 1);
		if (promotionRule != null) {
			if (promotionRule.getCalType() == 1) {
				creditCount += (long) promotionRule.getCalRule().doubleValue();
			} else {
				creditCount = (long) Math.floor(creditCount * (promotionRule.getCalRule().doubleValue()));
			}
		}
		
		//保存入库
		result=creditIncome(creditCount, account, businessNo, taskType, payAmount, rule.getTaskName());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<CreditExpendVo> creditExpend(Long userId, Long payCount, String orderNo, Integer businessType,
			String productDesc) {
		UserMessageVo<CreditExpendVo> result = new UserMessageVo<>();
		//积分捐赠不判断订单号重复
		if(businessType != 5000){
			List<CreditPayDetail> creditPayDetail = ucenterCommonDao.getList(CreditPayDetail.class, "businessType", businessType,
					 "businessNo", orderNo);
			if (creditPayDetail != null && creditPayDetail.size()>0) {
				result.setResult(false);
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12048.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12048.getErrorMessage());
				return result;
			}
		}
		CreditExpendVo vo = new CreditExpendVo();
		vo.setOrderNo(orderNo);
		vo.setUserId(userId);
		vo.setPayStatus(1);
		result.setT(vo);
		// 积分账户
		CreditAccount account = ucenterCommonDao.get(CreditAccount.class, "userId", userId, "status", 1);
		if (account != null) {
			if (payCount > account.getCreditsAvailable()) {
				result.getT().setPayStatus(3);
				result.setResult(false);
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12041.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12041.getErrorMessage());
				return result;
			}

			Date date = new Date();
			// 增加明细记录
			CreditBillDetail billDetail = new CreditBillDetail();
			billDetail.setBalanceCount(account.getCreditsAvailable() - payCount);
			billDetail.setBusinessNo(orderNo);
			billDetail.setCreateTime(date);
			billDetail.setCreditCount(0 - payCount);
			billDetail.setCreditType(2);
			billDetail.setDescription(productDesc);
			billDetail.setStatus(1);
			billDetail.setUserId(userId);
			billDetail.setTaskType(businessType);

			// 增加积分消费明细记录
			CreditPayDetail payDetail = new CreditPayDetail();
			payDetail.setBusinessNo(orderNo);
			payDetail.setBusinessType(businessType);
			payDetail.setPayCount(payCount);
			payDetail.setPayStatus(1);
			payDetail.setPayTime(date);
			payDetail.setProductDesc(productDesc);
			String tradeNo = UUID.randomUUID().toString().replace("-", "");
			payDetail.setTradeNo(tradeNo);
			payDetail.setUserId(userId);

			// 修改可用积分
			account.setCreditsAvailable(account.getCreditsAvailable() - payCount);
			account.setCreditsExpend(account.getCreditsExpend() + payCount);
			account.setUpdateTime(date);

			try {
				ucenterCommonDao.save(billDetail);
				ucenterCommonDao.save(payDetail);
				ucenterCommonDao.update(account);
				result.getT().setTradeNo(tradeNo);
				result.setResult(true);
			} catch (Exception e) {
				result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12032.getErrorCode().toString());
				result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12032.getErrorMessage());
				result.setResult(false);
				result.getT().setPayStatus(2);
				return result;
			}
		} else {
			result.getT().setPayStatus(2);
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12039.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12039.getErrorMessage());
			return result;
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveCreditModify(CreditBillModify req) {

		// 明细记录
		CreditBillDetail bill = ucenterCommonDao.get(req.getCreditBillId(), CreditBillDetail.class);
		// 积分账户
		CreditAccount account = ucenterCommonDao.get(CreditAccount.class, "userId", bill.getUserId());

		// 修改
		account.setCreditsAvailable(account.getCreditsAvailable() - bill.getCreditCount() + req.getNewCreditCount());
		account.setUpdateTime(DateUtil.getCurrentDateTime());

		if (bill.getCreditType() == 1) {
			// 原记录为收入
			account.setCreditsIncome(account.getCreditsIncome() - bill.getCreditCount());
			if (req.getNewCreditCount() < 0) {
				// 修改为支出
				account.setCreditsExpend(account.getCreditsExpend() + req.getNewCreditCount());
				bill.setCreditType(2);
			} else {
				account.setCreditsIncome(account.getCreditsIncome() + req.getNewCreditCount());
			}
		} else if (bill.getCreditType() == 1) {
			// 原记录为支出
			account.setCreditsExpend(account.getCreditsExpend() - bill.getCreditCount()); // 减负数
			if (req.getNewCreditCount() > 0) {
				// 修改为收入
				account.setCreditsIncome(account.getCreditsIncome() + req.getNewCreditCount());
				bill.setCreditType(1);
			} else {
				account.setCreditsExpend(account.getCreditsExpend() + req.getNewCreditCount());
			}
		}

		bill.setBalanceCount(bill.getBalanceCount() - bill.getCreditCount() + req.getNewCreditCount());
		bill.setCreditCount(req.getNewCreditCount());

		ucenterCommonDao.update(account);
		ucenterCommonDao.update(bill);

		// 新增修改记录
		req.setCreateTime(DateUtil.getCurrentDateTime());
		req.setUserId(bill.getUserId());
		ucenterCommonDao.save(req);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void synPartnerCredit(Long userId, Long partnerId, Long balanceCount) {
		CreditPartnerMap map = ucenterCommonDao.get(CreditPartnerMap.class, "userId", userId, "partnerId", partnerId);
		CreditPartnerMap map2 = new CreditPartnerMap();
		map2.setId(map.getId());
		map2.setBalanceCount(balanceCount);
		map2.setUpdateTime(DateUtil.getCurrentDateTime());
		ucenterCommonDao.update(map2);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<CreditBillDetail> CreditRefund(Long userId, Integer businessType, String orderNo,
			Long refundCount, String productDesc) {
		UserMessageVo<CreditBillDetail> result = new UserMessageVo<>();
		result.setResult(true);
		if (userId == null || businessType == null || StringUtils.isBlank(orderNo)) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}
		// 非积分商城退单，所退积分必传
		if (businessType != 1000 && (refundCount == null || refundCount.longValue() <= 0)) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			return result;
		}

		// 获取支付明细
		CreditPayDetail payDetail = ucenterCommonDao.get(CreditPayDetail.class, "businessType", businessType, "userId",
				userId, "businessNo", orderNo);
		if (payDetail == null) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12043.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12043.getErrorMessage());
			return result;
		}

		if (payDetail.getPayStatus() == 2) {
			// 积分已全部退换
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12046.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12046.getErrorMessage());
			return result;
		}

		// 积分商城退单，退全部积分
		if (businessType == 1000) {
			refundCount = payDetail.getPayCount();
		}

		// 积分明细集合（退一次加一条）
		List<CreditBillDetail> billDetails = ucenterCommonDao.getList(CreditBillDetail.class, "businessNo", orderNo,
				"taskType", businessType);
		// 该订单剩余可退积分
		Long balancePayCount = 0 - billDetails.stream().mapToLong(a -> a.getCreditCount()).sum();
		if (balancePayCount <= 0 || balancePayCount.longValue() < refundCount.longValue()) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12047.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12047.getErrorMessage());
			return result;
		}

		// 获取积分账户信息
		CreditAccount account = ucenterCommonDao.get(CreditAccount.class, "userId", userId, "status", 1);
		if (account == null) {
			result.setResult(false);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12039.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12039.getErrorMessage());
			return result;
		}

		Date date = new Date();
		// 修改支付明细状态
		if (balancePayCount.longValue() > refundCount.longValue()) {
			// 支付的积分未全部退还，部分退
			payDetail.setPayStatus(3);
		} else if (balancePayCount.equals(refundCount)) {
			// 支付的积分全部退还，已退
			payDetail.setPayStatus(2);
		}

		// 增加积分明细(退一次，加一条)
		CreditBillDetail billDetail = new CreditBillDetail();
		billDetail.setBalanceCount(account.getCreditsAvailable() + refundCount);
		billDetail.setBusinessNo(orderNo);
		billDetail.setCreateTime(date);
		billDetail.setCreditCount(refundCount);
		billDetail.setCreditType(1);
		billDetail.setDescription(productDesc);
		billDetail.setStatus(1);
		billDetail.setUserId(userId);
		billDetail.setTaskType(businessType);
		// 修改可用积分
		account.setCreditsAvailable(account.getCreditsAvailable() + refundCount);
		account.setCreditsIncome(account.getCreditsIncome() + refundCount);
		account.setUpdateTime(date);

		try {
			ucenterCommonDao.save(billDetail);
			ucenterCommonDao.update(payDetail);
			ucenterCommonDao.update(account);
		} catch (Exception e) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12044.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12044.getErrorMessage());
			result.setResult(false);
			return result;
		}
		// 计算当前退还积分价值金额
		List<CreditPriceRule> creditPriceRules = ucenterCommonDao.getList(CreditPriceRule.class, "businessType",
				businessType, "status", 1, "currentTime", DateUtil.getCurrentDateTimeToStr2());
		if (CollectionUtils.isEmpty(creditPriceRules)) {
			billDetail.setPayAmount(0d);
		} else {
			// 目前每种业务类型积分价值固定，后期如果每种业务有多个积分价值，修改此处
			CreditPriceRule creditPriceRule = creditPriceRules.get(0);
			billDetail.setPayAmount(
					BigDecimal.valueOf(refundCount).multiply(BigDecimal.valueOf(creditPriceRule.getMultiplier()))
							.divide(BigDecimal.valueOf(creditPriceRule.getDivisor())).doubleValue());
		}
		result.setT(billDetail);
		return result;
	}

	@Override
	@Transactional
	public void synUserAndJinPengCredit(CreditAccount ca, CreditBillDetail cbd, CreditPartnerMap cpm,
			CreditConversionDetail ccd) {
		LOGGER.info("兑换成功后保存积分变动信息Start");
		ucenterCommonDao.update(ca);
		ucenterCommonDao.update(cpm);
		ucenterCommonDao.save(cbd);
		ccd.setBillId(cbd.getId());
		ucenterCommonDao.save(ccd);
		LOGGER.info("兑换成功后保存积分变动信息End");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void synCreditFromFenXiagn(CreditAccount ca, CreditBillDetail cbd, CreditConversionDetail ccd) {
		ucenterCommonDao.update(ca);
		ucenterCommonDao.save(cbd);
		ccd.setBillId(cbd.getId());
		ucenterCommonDao.save(ccd);
	}

	@Override
	@Transactional
	public UserMessageVo<CreditBillDetail> signInPresentedCredit(Long userId, Long creditCount) {
		UserMessageVo<CreditBillDetail> result = new UserMessageVo<>();

		// 积分账户
		CreditAccount account = ucenterCommonDao.getFromMaster(CreditAccount.class, "userId", userId);
		if (account == null) {
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12039.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12039.getErrorMessage());
			result.setResult(false);
			return result;
		}

		String now = DateUtil.getCurrentSimpleDate();

		List<CreditBillDetail> billDetails = ucenterCommonDao.getList(CreditBillDetail.class, "taskType", 13, "userId",
				userId, "startTime", now + " 00:00:00", "endTime", now + "23:59:59", "status", 1);
		if (billDetails != null && billDetails.size() > 0) {
			LOGGER.info("用户{0}，在{1}已签到！", userId, now);
			result.setResult(false);
			result.setT(billDetails.get(0));
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12049.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12049.getErrorMessage());
			return result;
		}
		
		//保存入库
		result=creditIncome(creditCount, account, null, 13, null, "签到");

		return result;
	}

	/**
	 * 积分累积保存入库
	 * @param creditCount 积分增加数量
	 * @param account 变动的积分账户
	 * @param businessNo 业务单号
	 * @param taskType 任务类型
	 * @param payAmount 交易金额
	 * @param description 备注
	 * @return
	 * @author 陶嘉骏
	 * @date 2018年1月10日
	 */
	private UserMessageVo<CreditBillDetail> creditIncome(Long creditCount, CreditAccount account,String businessNo,
			Integer taskType,Double payAmount,String description) {
		UserMessageVo<CreditBillDetail> result = new UserMessageVo<>();
		if (account==null || creditCount==null || taskType==null) {
			LOGGER.error("积分累积保存入库参数错误:account={0},creditCount={1},taskType={2}", account, creditCount, taskType);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			result.setResult(false);
		}
		
		Date date=new Date();
		
		// 增加明细记录
		CreditBillDetail billDetail = new CreditBillDetail();
		billDetail.setBalanceCount(creditCount + account.getCreditsAvailable());
		billDetail.setBusinessNo(businessNo);
		billDetail.setCreateTime(date);
		billDetail.setCreditCount(creditCount);
		billDetail.setCreditType(1);
		billDetail.setDescription(description);
		billDetail.setPayAmount(payAmount);
		billDetail.setStatus(1);
		billDetail.setTaskType(taskType);
		billDetail.setUserId(account.getUserId());

		// 修改可用积分,及有效期
		account.setCreditsAvailable(creditCount + account.getCreditsAvailable());
		account.setCreditsIncome(creditCount + account.getCreditsIncome());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 2); // 两年过期
		billDetail.setExpireTime(calendar.getTime());
		account.setExpireTime(calendar.getTime());
		account.setUpdateTime(date);

		result.setResult(true);
		result.setT(billDetail);

		try {
			ucenterCommonDao.save(billDetail);
			ucenterCommonDao.update(account);

		} catch (Exception e) {
			LOGGER.error("积分累积错误:{0},userId={1},taskType={2},businessNo={3}", e.getMessage(), account.getUserId(), taskType,
					businessNo);
			result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12040.getErrorCode().toString());
			result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12040.getErrorMessage());
			result.setResult(false);
		}

		return result;
	}

}
