package com.hnair.consumer.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hnair.consumer.dao.spi.ICommonDao;
import com.hnair.consumer.promotion.api.IActiveApi;
import com.hnair.consumer.promotion.api.ICouponApi;
import com.hnair.consumer.promotion.api.ILuckyDrawApi;
import com.hnair.consumer.promotion.model.CouponScheme;
import com.hnair.consumer.promotion.model.Prize;
import com.hnair.consumer.promotion.vo.AddluckDrawCountVo;
import com.hnair.consumer.promotion.vo.PersonalCenterCouponVo;
import com.hnair.consumer.promotion.vo.PickupCouponVo;
import com.hnair.consumer.promotion.vo.PromotionMessageVo;
import com.hnair.consumer.user.api.ICreditApi;
import com.hnair.consumer.user.api.ISignInActiveApi;
import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.PrizeRecord;
import com.hnair.consumer.user.model.SignInAwardRule;
import com.hnair.consumer.user.model.SignInRecord;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.service.ISignInService;
import com.hnair.consumer.user.vo.SignInActiveVo;
import com.hnair.consumer.user.vo.SignInPrizeVo;
import com.hnair.consumer.user.vo.SignInRecordVo;
import com.hnair.consumer.user.vo.SignInVo;
import com.hnair.consumer.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 签到接口
 * 
 * @author YangDong
 */
@Service("signInService")
@Slf4j
public class SignInServiceImpl implements ISignInService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignInServiceImpl.class);

	@Resource(name = "ucenterCommonDao")
	private ICommonDao signInDao;

	@Resource
	private IActiveApi activeApi;

	@Resource
	private ICreditApi creditApi;

	@Resource
	private ICouponApi couponApi;

	@Resource
	private ILuckyDrawApi luckyDrawApi;

	@Resource
	private ISignInActiveApi signInActiveApi;

	@SuppressWarnings("rawtypes")
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate masterRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate slaveRedisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public SignInVo signIn(UserBaseInfo user) {
		SignInVo signInVo = new SignInVo();
		try {
			// 获取当前日期
			Date date = DateUtil.getOnlyDay(new Date());
			String nowDate = DateUtil.formatDateByFormat(new Date(), DateUtil.DATE_PATTERN3);
			// redis锁限流
			String signLockKey = "signIn_lock_" + user.getUserId();
			if(slaveRedisTemplate.hasKey(signLockKey)){
				log.error("签到请求太频繁!");
				signInVo.setLock(true);
				return signInVo;				
			}
			masterRedisTemplate.opsForValue().set(signLockKey, "1", 6, TimeUnit.SECONDS);
			// 是否签到Key
			String signKey = "signIn_" + user.getUserId() + "_" + nowDate;
			if (slaveRedisTemplate.hasKey(signKey)) {
				signInVo = (SignInVo) slaveRedisTemplate.opsForValue().get(signKey);
			}
			if (signInVo.getSignInActive() == null) {
				signInVo = getSignInHomePage(user.getUserId());
			}
			if ((signInVo != null && signInVo.isSign()) || (signInVo != null && !signInVo.isActiveStatus())) { // 已签到
				return signInVo;
			}
			if (signInVo.getSignInActive() == null) {
				log.error("签到失败signInVo为Null!");
				return signInVo;	
			}
			int count = 0; // 连续签到天数
			SignInActiveVo signInActiveVo = signInVo.getSignInActive();
			SignInRecord signInRecord = new SignInRecord();
			List<SignInAwardRule> signInAwardRuleList = signInActiveVo.getSignInAwardRuleList();
			// 积分规则处理
			if (signInActiveVo.getReset() == 1) { // 清0
				count = signInVo.getSignInCount() % signInActiveVo.getCountPeriod() + 1;
				signInVo.setSignInCount(count);
			}
			if (signInActiveVo.getReset() == 0) { // 不清0
				count = signInVo.getTotalSignInCount() + 1;
				signInVo.setSignInCount(count);
				if (count >= signInActiveVo.getCountPeriod()) { // 积分取最后一天的
					count = signInActiveVo.getCountPeriod();
				}
			}
			for (SignInAwardRule signInAwardRule : signInAwardRuleList) {
				if (signInAwardRule.getSignInDays() == count) { // 今天的奖励
					Integer awardIntegral = signInAwardRule.getAwardIntegral();
					signInRecord.setAwardIntegral(awardIntegral);
					break;
				}
			}
			signInRecord.setBasisIntegral(signInActiveVo.getBasisIntegral());
			// 增加积分
			creditApi.signInPresentedCredit(user.getUserId(), (long)(signInRecord.getBasisIntegral() + signInRecord.getAwardIntegral()));
			if (signInVo.getLuckDrawId() != null && signInVo.getLuckDrawId() != 0) {
				// 增加抽奖机会1次
				AddluckDrawCountVo addLuckDrawCountVo = new AddluckDrawCountVo();
				addLuckDrawCountVo.setUserId(user.getUserId());
				addLuckDrawCountVo.setGiveType(4);
				addLuckDrawCountVo.setLuckyDrawId(signInVo.getLuckDrawId());
				luckyDrawApi.addluckDrawCount(addLuckDrawCountVo);
				// 添加中奖纪录，纪录为待领取状态
				PrizeRecord prizeRecord = new PrizeRecord();
				prizeRecord.setActiveId(signInActiveVo.getId());
				prizeRecord.setContactsName(user.getUserName());
				prizeRecord.setContactsPhone(user.getMobile());
				prizeRecord.setCreateTime(DateUtil.getCurrentDateTime());
				prizeRecord.setDrawStatus(1);// 未领取
				prizeRecord.setLotteryType(1); // 抽奖
				signInDao.save(prizeRecord);
				signInRecord.setPrizeRecordId(prizeRecord.getId());
				signInVo.setLucky(true);
			}
			if (signInVo.getCouponSchemeId() != null && signInVo.getCouponSchemeId() != 0 && signInVo.isLucky()) {
				// 发优惠券
				PickupCouponVo vo = new PickupCouponVo();
				vo.setAllCoupon(true);
				vo.setCouponSchemeId(Integer.valueOf(signInVo.getCouponSchemeId().toString()));
				vo.setUserGrade(user.getGrade());
				vo.setUserId(user.getUserId());
				vo.setRefer("签到赠送");
				vo.setPickupWay(2);
				PromotionMessageVo<List<PersonalCenterCouponVo>> coupons = couponApi.pickupCoupons(vo);
				// 添加中奖纪录，纪录为已领取状态
				PrizeRecord prizeRecord = new PrizeRecord();
				if (coupons.isResult()) {
					PersonalCenterCouponVo personalCenterCouponVo = coupons.getT().get(0);
					prizeRecord.setPrizeId(personalCenterCouponVo.getCouponId()); // 优惠券ID
				}
				prizeRecord.setActiveId(signInActiveVo.getId());
				prizeRecord.setContactsName(user.getUserName());
				prizeRecord.setContactsPhone(user.getMobile());
				prizeRecord.setCreateTime(DateUtil.getCurrentDateTime());
				prizeRecord.setDrawStatus(2);// 已领取
				prizeRecord.setLotteryType(2); // 抽奖
				prizeRecord.setProductType(8); // 优惠券
				signInDao.save(prizeRecord);
				signInRecord.setPrizeRecordId(prizeRecord.getId());
				signInVo.setLucky(false);
			}
			// 添加签到记录
			signInRecord.setSignCount(signInVo.getTotalSignInCount() + 1);
			signInRecord.setSignInActiveId(signInActiveVo.getId());
			signInRecord.setSignInTime(DateUtil.getCurrentDateTime());
			signInRecord.setUserId(user.getUserId());
			signInRecord.setUserName(user.getUserName());
			signInRecord.setUserMobile(user.getMobile());
			// 保存签到记录
			signInDao.save(signInRecord);
			signInVo.setSignInRecordId(signInRecord.getId());
			signInVo.setLastSignDate(date);
			signInVo.setSign(true);
			CreditAccount account = creditApi.getCreditAccountByUserId(user.getUserId());
			if (account != null) {
				signInVo.setIntegralBalance(account.getCreditsAvailable());
			}
			makeSignInPeriod(signInVo, signInRecord);
			masterRedisTemplate.opsForValue().set(signKey, signInVo, 24, TimeUnit.HOURS);
		} catch (Exception e) {
			LOGGER.info("签到发生异常", e);
			e.printStackTrace();
		}
		return signInVo;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public SignInVo getSignInHomePage(Long userId) {
		SignInVo redisSignInVo = null;
		SignInVo signInVo = new SignInVo();
		try {
		// 缓存中查VO
		String nowDate = DateUtil.formatDateByFormat(new Date(), DateUtil.DATE_PATTERN3);
		String signInVoKey = "signIn_" + userId + "_" + nowDate;
		if (slaveRedisTemplate.hasKey(signInVoKey)) {
			redisSignInVo = (SignInVo) slaveRedisTemplate.opsForValue().get(signInVoKey);
			LOGGER.info("缓存中读取SignInVo结果:" + redisSignInVo);
		}
		if (redisSignInVo != null) {
			LOGGER.info("缓存中读取SignInVo,是否已签到:" + redisSignInVo.isSign());
			return redisSignInVo;
		}
		// 缓存中没有VO,查库封装VO
		// 查活动
		SignInActiveVo signInActiveVo = signInActiveApi.getSignInActiveByStatus(1, new Date());// 查询是开启状态，并且在有效时间内的活动。
		boolean checkActive = checkActive(signInActiveVo);
		if (!checkActive) { // 检查活动状态
			signInVo.setActiveStatus(false);
			LOGGER.info("没有签到活动或者签到活动已全部过期/关闭");
			return signInVo;
		}
		signInVo.setUserId(userId);
		signInVo.setSignInActive(signInActiveVo);
		// 查签到记录
		SignInRecord signInRecord = this.getLastSignInRecordByUserId(userId);
		signInVo = makeSignInPeriod(signInVo, signInRecord);
		} catch (Exception e) {
			LOGGER.info("签到主页异常:" + e);
		}
		return signInVo;
	}
	/**
	 * 封装签到Vo
	 * @param signInVo
	 * @param signInRecord
	 * @return
	 */
	private SignInVo makeSignInPeriod(SignInVo signInVo, SignInRecord signInRecord) {
		try {
			signInVo.setSign(false);
			signInVo.setSignInCount(0);
			signInVo.setTotalSignInCount(0);
			SignInActiveVo signInActiveVo = signInVo.getSignInActive();
			int signInCount = 0;
			int countPeriod = signInActiveVo.getCountPeriod();// 周期
			int diffDate = checkSignInRecord(signInRecord, signInActiveVo.getId());// 检查签到记录中连续签到天数并且
			Date firstDate = new Date(); // 页面展示周期第一天
			if (diffDate == 0) { // 连续签到,今天已签到
				signInCount = signInRecord.getSignCount() % countPeriod; // 周期清零的
				signInVo.setSign(true);
				signInVo.setTotalSignInCount(signInRecord.getSignCount());
				firstDate = DateUtil.addDate(new Date(), -(signInCount-1)); // 页面展示周期第一天
			}
			if (diffDate == 1) { // 连续签到,今天未签到
				signInVo.setSign(false);
				signInCount = signInRecord.getSignCount() % countPeriod;
				signInVo.setTotalSignInCount(signInRecord.getSignCount());
			    firstDate = DateUtil.addDate(new Date(), -signInCount); // 页面展示周期第一天
			}
			signInVo.setSignInCount(signInCount);
			// 封装一个签到周期,如果当前活动结束时间距离当前时间天数小于活动周期则不足部分用下个活动的前几天补齐。
			List<SignInRecordVo> signInRecordVoList = new ArrayList<>();
			List<SignInAwardRule> signInAwardRuleList = signInActiveVo.getSignInAwardRuleList();
			SignInActiveVo nextActiveVo = new SignInActiveVo();
			int diffDateDays = DateUtil.diffDateDays(signInActiveVo.getEndTime(), new Date()); // 活动剩余天数
			int remainingPeriod = countPeriod - signInCount; // 当前周期剩余未签到天数
			int days = diffDateDays - remainingPeriod; // 当前活动周期需要下个活动补的天数
			if (days < 0) { // 活动剩余天数小于一个周期剩余未签到天数
				days = -days;
				Date tempDate = DateUtil.addDate(new Date(), days);
				// 需要查下一个活动，和本次活动进拼装
				nextActiveVo = signInActiveApi.getSignInActiveByStatus(1, tempDate);// 查询是开启状态，并且在有效时间内的活动。
			} else {
				days = 0;
			}
			for (int i = 1; i <= countPeriod; i++) { // i 在此处代表一个周期的第i天
				SignInRecordVo signInRecordVo = new SignInRecordVo();
				Date tempDate = DateUtil.addDate(firstDate, i - 1);// 封装日期和和签到状态
				signInRecordVo.setSignInTime(tempDate);
				signInRecordVo.setWeekday(DateUtil.getWeekdayByDate(tempDate, 1));
				if (i <= signInCount) { // 已签到
					signInRecordVo.setSign(true);
				} else {
					signInRecordVo.setSign(false);
				}
				signInRecordVo.setDays(i);
				signInRecordVo.setIntegral(signInActiveVo.getBasisIntegral());
				for (SignInAwardRule signInAwardRule : signInAwardRuleList) { // 默认本次活动剩余天数够本周期剩余未签到天数
					if (i == signInAwardRule.getSignInDays()) {
						signInRecordVo.setAwardIntegral(signInAwardRule.getAwardIntegral());
						signInRecordVo.setAwardType(signInAwardRule.getSpecialAwardType());
						signInRecordVo
								.setIntegral(signInActiveVo.getBasisIntegral() + signInAwardRule.getAwardIntegral());
						signInRecordVo.setCouponSchemeId(signInAwardRule.getCouponSchemeId());
						signInRecordVo.setLuckDrawId(signInAwardRule.getLuckyDrawId());
						if (signInAwardRule.getSpecialAwardType() != null
								&& signInAwardRule.getSpecialAwardType() != 0) {
							String formatDate = DateUtil.formatDateByFormat(tempDate, DateUtil.DATE_PATTERN5);
							Date dayStart = DateUtil.getMinTimeByStringDate(formatDate);
							Date dayEnd = DateUtil.getMaxTimeByStringDate(formatDate);
							Map<String, Object> queryMap = new HashMap<>();
							queryMap.put("userId", signInVo.getUserId());
							queryMap.put("lotteryType", signInAwardRule.getSpecialAwardType());
							queryMap.put("dayStart", dayStart);
							queryMap.put("dayEnd", dayEnd);
							PrizeRecord prizeRecord = this.getPrizeRecord(queryMap); // 封装奖品状态
							if (prizeRecord == null) {
								signInRecordVo.setPrizeStatus(0);
							} else {
								signInRecordVo.setPrizeStatus(prizeRecord.getDrawStatus());
							}
						}
					}
					if (signInVo.isSign()
							&& signInAwardRule.getSignInDays() == signInVo.getSignInCount() % countPeriod) {
						if (signInAwardRule.getSpecialAwardType() == 1) {
							signInVo.setLuckDrawId(signInAwardRule.getLuckyDrawId());
							signInVo.setLucky(true);
							signInVo.setLuckyType(1);
						}
						if (signInAwardRule.getSpecialAwardType() == 2) {
							signInVo.setCouponSchemeId(signInAwardRule.getCouponSchemeId());
							signInVo.setLucky(true);
							signInVo.setLuckyType(2);
						}
					}
				}
				if (days > 0 && nextActiveVo != null && i > countPeriod - days) { // 本次活动即将结束且有下一次活动
					signInRecordVo.setBasisIntegral(nextActiveVo.getBasisIntegral());
					for (SignInAwardRule signInAwardRule : nextActiveVo.getSignInAwardRuleList()) {
						if (i == signInAwardRule.getSignInDays()) {
							signInRecordVo.setAwardIntegral(signInAwardRule.getAwardIntegral());
							signInRecordVo.setAwardType(signInAwardRule.getSpecialAwardType());
							signInRecordVo
									.setIntegral(nextActiveVo.getBasisIntegral() + signInAwardRule.getAwardIntegral());
							signInRecordVo.setCouponSchemeId(signInAwardRule.getCouponSchemeId());
							signInRecordVo.setLuckDrawId(signInAwardRule.getLuckyDrawId());
						}
					}
				}
				signInRecordVoList.add(signInRecordVo);
			}
			signInVo.setSignInRecordVoList(signInRecordVoList); // 封装页面展示签到记录列表
			Long luckyDrawId = 0L;
			Long couponSchemeId = 0L;
			for (SignInRecordVo signInRecordVo : signInRecordVoList) { // 距离下一次开宝箱天数,以及宝箱奖品内容,以及当天获得V分
				if (signInVo.isSign() && signInCount == signInRecordVo.getDays()) {
					signInVo.setIntegral(signInRecordVo.getIntegral());
				}
				if (!signInVo.isSign() && signInCount + 1 == signInRecordVo.getDays()) {
					signInVo.setIntegral(signInRecordVo.getIntegral());
				}
				if (signInRecordVo.getAwardType() != null && signInRecordVo.getAwardType() != 0
						&& signInCount < signInRecordVo.getDays()) {
					signInVo.setDays(signInRecordVo.getDays() - signInCount);
					luckyDrawId = signInRecordVo.getLuckDrawId();
					couponSchemeId = signInRecordVo.getCouponSchemeId();
					break;
				}
			}
			if (luckyDrawId != null && luckyDrawId != 0) {
				List<Prize> prizeList = luckyDrawApi.queryPrizeListByLuckDrawId(luckyDrawId);
				List<SignInPrizeVo> signInPrizeVoList = new ArrayList<>();
				for (Prize prize : prizeList) {
					SignInPrizeVo vo = new SignInPrizeVo();
					vo.setCouponSchemeId(prize.getCouponSchemeId());
					vo.setInformation(prize.getInformation());
					vo.setLuckyDrawId(prize.getLuckyDrawId());
					vo.setPrizeId(prize.getPrizeId());
					vo.setPrizeName(prize.getPrizeName());
					vo.setPrizePic(prize.getPrizePic());
					vo.setProductId(prize.getProductId());
					vo.setProductType(prize.getProductType());
					signInPrizeVoList.add(vo);
				}
				signInVo.setSignInPrizeVoList(signInPrizeVoList);
				LOGGER.info("签到活动对应特殊抽奖活动ID:" + luckyDrawId);
			}
			if (couponSchemeId != null && couponSchemeId != 0) {
				// 查活动对应的优惠券奖品
				CouponScheme couponScheme = couponApi.getCouponSchemeById(Integer.parseInt(couponSchemeId.toString()));
				signInVo.setCouponSchemeId((long) couponScheme.getId());
				signInVo.setCouponFaceValue(couponScheme.getFaceValue());
				LOGGER.info("签到活动对应奖励优惠券SchemeID:" + couponSchemeId);
			}
			// 查积分账户
			CreditAccount account = creditApi.getCreditAccountByUserId(signInVo.getUserId());
			if (account != null) {
				signInVo.setIntegralBalance(account.getCreditsAvailable());
			}
			return signInVo;
		} catch (Exception e) {
			LOGGER.info("封装签到VO异常:" + e);
			return null;
		}
	}

	/**
	 * 查询用户最后一次签到记录
	 * 
	 * @param userId
	 * @return
	 */
	public SignInRecord getLastSignInRecordByUserId(Long userId) {
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("userId", userId);
		queryMap.put("last", true);
		return signInDao.getBySqlId(SignInRecord.class, "querySignInRecordByUserId", queryMap);
	}

	/**
	 * 检查活动状态
	 * 
	 * @param signInActiveVo
	 * @return
	 */
	private boolean checkActive(SignInActiveVo signInActiveVo) {
		if (signInActiveVo == null || signInActiveVo.getStatus() != 1) {
			return false;
		}
		Date nowDate = new Date();
		if (nowDate.after(signInActiveVo.getEndTime()) || nowDate.before(signInActiveVo.getStartTime())) {
			return false;
		}
		return true;
	}

	/**
	 * 检查签到记录
	 * 
	 * @param signInRecord
	 * @param signInActiveId
	 * @return 
	 */
	private int checkSignInRecord(SignInRecord signInRecord, Long signInActiveId) {
		if (signInRecord == null || signInRecord.getSignInActiveId() != signInActiveId) {
			return -1;
		}
		return DateUtil.diffDateDays(new Date(), signInRecord.getSignInTime());
	}
	
	/**
	 * queryMap : user_id,lottery_type,create_time
	 */
	@Override
	public PrizeRecord getPrizeRecord(Map<String, Object> queryMap) {
		return signInDao.getBySqlId(PrizeRecord.class, "getPrizeRecord", queryMap);
	}

}
