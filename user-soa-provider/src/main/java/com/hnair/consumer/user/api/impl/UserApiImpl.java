package com.hnair.consumer.user.api.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.hnair.consumer.utils.RegexpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.hnair.consumer.content.constant.Constant;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.system.api.ISystemApi;
import com.hnair.consumer.system.api.loader.IColumnBannerLoaderApi;
import com.hnair.consumer.system.model.Block;
import com.hnair.consumer.user.api.ICreditApi;
import com.hnair.consumer.user.api.IUserApi;
import com.hnair.consumer.user.enums.MsgCodeTypeEnum;
import com.hnair.consumer.user.enums.UserBehaviorTypeEnum;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.MessageCode;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserExtInfo;
import com.hnair.consumer.user.model.UserFeedback;
import com.hnair.consumer.user.model.UserLoginRecord;
import com.hnair.consumer.user.service.IUserBaseInfoService;
import com.hnair.consumer.user.service.IUserExtInfoService;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.utils.ImageUtils;
import com.hnair.consumer.utils.system.ConfigPropertieUtils;

@Component("userApi")
public class UserApiImpl implements IUserApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserApiImpl.class);
	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	@Autowired
	private IUserBaseInfoService userBaseInfoService;
	@Resource
	private IUserExtInfoService userExtInfoService;
	@SuppressWarnings("rawtypes")
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate masterRedisTemplate;
	@SuppressWarnings("rawtypes")
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate slaveRedisTemplate;

	@Resource(name = "creditApi")
	private ICreditApi creditApi;
	
	@Resource
	private ISystemApi systemApi;
	

	@Autowired
	private IColumnBannerLoaderApi columnBannerLoaderApi;

	private final String IP_MESSAGE_CODE_30_MINUTES = "ip_message_code_30_minutes_";

	private final String IP_MESSAGE_CODE_2_HOUR = "ip_message_code_2_hour_";

	private final String IP_MESSAGE_CODE_1_DAY = "ip_message_code_1_day_";

	// 手机号白名单,供苹果appStore使用
	private String[] WHITE_PHONE = ConfigPropertieUtils.getString("white_phone", "13800000000,13900000000,18846707477").split(",");

	@Override
	public UserMessageVo<UserBaseInfo> register(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> userMessageVo = new UserMessageVo<UserBaseInfo>();
		// 检查验证码
		if (!Arrays.asList(WHITE_PHONE).contains(String.valueOf(userLoginRecord.getMobile()))) {
			UserMessageVo<MessageCode> messageVo = userBaseInfoService.checkMessageCodeNew(userLoginRecord.getRegion(),
					userLoginRecord.getMobile(), userLoginRecord.getIdentifyCode(), MsgCodeTypeEnum.LOGIN);
			if (!messageVo.isResult()) {
				userMessageVo.setErrorCode(messageVo.getErrorCode());
				userMessageVo.setErrorMessage(messageVo.getErrorMessage());
				userMessageVo.setResult(false);
				return userMessageVo;
			}
		}
		userMessageVo = userBaseInfoService.register(userLoginRecord);
		if (userMessageVo != null && userMessageVo.getT() != null) {
			try {
				creditApi.createCreditAccount(userMessageVo.getT().getMobile(), userMessageVo.getT().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userMessageVo;
	}

	@Override
	public UserMessageVo<UserBaseInfo> queryPhoneNumber(String region, String phoneNumber) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 查询是否存在手机号
		List<UserBaseInfo> userBaseInfo = ucenterService.getListFromMaster(UserBaseInfo.class, "mobile", phoneNumber, "region",
				region);
		// 存在返回true,不存在返回false
		if (CollectionUtils.isNotEmpty(userBaseInfo)) {
			messageVo.setResult(true);
			messageVo.setT(userBaseInfo.get(0));
			return messageVo;
		}
		messageVo.setResult(false);
		return messageVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserMessageVo<MessageCode> sendMessageNew(String region, String phoneNumber, String ipAddr, String appType,
			MsgCodeTypeEnum msgCodeType) {
		try {
			UserMessageVo<MessageCode> messageVo = new UserMessageVo<MessageCode>();

			Long sendTimes30Minutes = masterRedisTemplate.opsForValue().increment(IP_MESSAGE_CODE_30_MINUTES + ipAddr,
					1l);
			Long sendTimes2Hour = masterRedisTemplate.opsForValue().increment(IP_MESSAGE_CODE_2_HOUR + ipAddr, 1l);
			Long sendTimes1Day = masterRedisTemplate.opsForValue().increment(IP_MESSAGE_CODE_1_DAY + ipAddr, 1l);
			if (sendTimes30Minutes == 1l) {
				masterRedisTemplate.expire(IP_MESSAGE_CODE_30_MINUTES + ipAddr, 30, TimeUnit.MINUTES);
			}
			if (sendTimes2Hour == 1l) {
				masterRedisTemplate.expire(IP_MESSAGE_CODE_2_HOUR + ipAddr, 2, TimeUnit.HOURS);
			}

			if (sendTimes1Day == 1l) {
				masterRedisTemplate.expire(IP_MESSAGE_CODE_1_DAY + ipAddr, 1, TimeUnit.DAYS);
			}
			LOGGER.info("当前IP:" + ipAddr + ",30分钟内发送验证码次数:" + sendTimes30Minutes + ",两小时内发送验证码次数:" + sendTimes2Hour
					+ ",一天内发送验证码次数:" + sendTimes1Day);

			String valid = systemApi.getProperty("send_message_code_times_valide", "0");
			
			if (StringUtils.isNotEmpty(valid) && valid.equals("1")) {
				if (sendTimes30Minutes != null && sendTimes30Minutes >= 5l) {
					messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12018.getErrorCode().toString());
					messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12018.getErrorMessage());
					messageVo.setResult(false);
					LOGGER.warn("当前IP:" + ipAddr + ",30分钟内发送验证码次数:" + sendTimes30Minutes + ",访问过于频繁，返回失败");
					return messageVo;
				}
				if (sendTimes2Hour != null && sendTimes2Hour >= 10l) {
					messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12018.getErrorCode().toString());
					messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12018.getErrorMessage());
					messageVo.setResult(false);
					LOGGER.warn("当前IP:" + ipAddr + ",两小时内发送验证码次数:" + sendTimes2Hour + ",访问过于频繁，返回失败");
					return messageVo;
				}
				if (sendTimes1Day != null && sendTimes1Day >= 20l) {
					messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12018.getErrorCode().toString());
					messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12018.getErrorMessage());
					messageVo.setResult(false);
					LOGGER.warn("当前IP:" + ipAddr + ",一天内发送验证码次数:" + sendTimes1Day + ",访问过于频繁，返回失败");
					if (sendTimes1Day == 50l) {
						masterRedisTemplate.expire(IP_MESSAGE_CODE_1_DAY + ipAddr, 1, TimeUnit.DAYS);
						this.sendOrderStatusMessage(null, "13904050692",
								"当前IP:" + ipAddr + ",30分钟内发送验证码次数:" + sendTimes30Minutes + ",两小时内发送验证码次数:"
										+ sendTimes2Hour + ",一天内发送验证码次数:" + sendTimes1Day);
					}
					if (sendTimes1Day > 100l) {
						masterRedisTemplate.expire(IP_MESSAGE_CODE_1_DAY + ipAddr, 1, TimeUnit.DAYS);
					}
					return messageVo;
				}
			}

		} catch (Exception e) {
			LOGGER.error("限制请求次数发生异常", e);
		}

		return userBaseInfoService.sendMessageNew(region, phoneNumber, appType, msgCodeType);
	}

	@Override
	public UserMessageVo<MessageCode> sendMessage(String region, String phoneNumber, String ipAddr, String appType) {
		return userBaseInfoService.sendMessageNew(region, phoneNumber, appType, MsgCodeTypeEnum.LOGIN);
	}

	@Override
	public UserMessageVo<MessageCode> sendMessage(String region, String phoneNumber, String ipAddr) {
		return this.sendMessage(region, phoneNumber, ipAddr, "1");
	}

	@Override
	public UserMessageVo<UserBaseInfo> login(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> userMessageVo = new UserMessageVo<UserBaseInfo>();
		// 检查验证码是否正确,白名单不验证
		if (!Arrays.asList(WHITE_PHONE).contains(String.valueOf(userLoginRecord.getMobile()))) {
			UserMessageVo<MessageCode> vo = userBaseInfoService.checkMessageCodeNew(userLoginRecord.getRegion(),
					userLoginRecord.getMobile().trim(), userLoginRecord.getIdentifyCode().trim(),
					MsgCodeTypeEnum.LOGIN);
			if (!vo.isResult()) {
				userMessageVo.setErrorCode(vo.getErrorCode());
				userMessageVo.setErrorMessage(vo.getErrorMessage());
				return userMessageVo;
			}
		}
		// 查询手机号是否注册,存在则登录,不存在则注册
		userMessageVo = queryPhoneNumber(userLoginRecord.getRegion(), userLoginRecord.getMobile());
		if (userMessageVo.isResult()) {
			userMessageVo = userBaseInfoService.login(userLoginRecord);
		} else {
			userMessageVo = userBaseInfoService.register(userLoginRecord);
		}
		//判断本用户是否存在积分账户，若不存在则调用积分开户接口
		if (userMessageVo != null && userMessageVo.getT() != null) {
			try {
				 CreditAccount creditAccount = ucenterService.getFromMaster(CreditAccount.class, "userId",userMessageVo.getT().getUserId());
				if (creditAccount==null) {
					 UserMessageVo<CreditAccount> createCreditAccount = creditApi.createCreditAccount(userMessageVo.getT().getMobile(), userMessageVo.getT().getUserId());
					 if (!createCreditAccount.isResult()) {
						 LOGGER.info("创建积分账户失败状态码："+createCreditAccount.getErrorCode());
					}
				}
			} catch (Exception e) {
				LOGGER.error("创建积分账户错误："+e.getMessage());
			}
		}
		this.loadMemberInfo(userMessageVo.getT());
		return userMessageVo;
	}

	@Override
	public UserMessageVo<UserBaseInfo> loginOut(UserLoginRecord userLoginRecord) {

		return userBaseInfoService.loginOut(userLoginRecord);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserBaseInfo getUserBaseInfoByToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		String cacheKey = "user_base_info_by_token_" + token;
		UserBaseInfo userBaseInfo = (UserBaseInfo) slaveRedisTemplate.opsForValue().get(cacheKey);
		if (userBaseInfo == null) {
			// 查询登录记录
			List<UserLoginRecord> userLoginRecordList = ucenterService.getList(UserLoginRecord.class, "lastLoginToken",
					token);
			if (CollectionUtils.isNotEmpty(userLoginRecordList)) {
				Long userId = userLoginRecordList.get(0).getUserId();
				userBaseInfo = ucenterService.get(UserBaseInfo.class, "userId", userId);
				if (userBaseInfo != null && StringUtils.isNotEmpty(userBaseInfo.getImgUrl())) {
					userBaseInfo.setImgUrl(ImageUtils.splitJointImageUrl(userBaseInfo.getImgUrl()));
				}
				this.loadMemberInfo(userBaseInfo);
				masterRedisTemplate.delete(cacheKey);
				masterRedisTemplate.opsForValue().set(cacheKey, userBaseInfo);
				masterRedisTemplate.expire(cacheKey, 24, TimeUnit.HOURS);
			}
		}
		return userBaseInfo;
	}

	@Override
	public UserMessageVo<UserBaseInfo> queryBindPhone(UserLoginRecord userLoginRecord) {
		return userBaseInfoService.queryBindPhone(userLoginRecord);
	}

	@Override
	public UserMessageVo<UserBaseInfo> bindPhone(UserLoginRecord userLoginRecord) {

		return userBaseInfoService.bindPhone(userLoginRecord);
	}

	@Override
	public UserMessageVo<UserBaseInfo> bindPhoneV2(UserLoginRecord userLoginRecord) {

		return userBaseInfoService.bindPhoneV2(userLoginRecord);
	}

	@Override
	public UserMessageVo<UserBaseInfo> thirdPartyRegister(UserLoginRecord userLoginRecord) {

		UserMessageVo<UserBaseInfo> userMessageVo = userBaseInfoService.thirdPartyRegister(userLoginRecord);

		if (userMessageVo != null && userMessageVo.getT() != null) {
			try {
				creditApi.createCreditAccount(userMessageVo.getT().getMobile(), userMessageVo.getT().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userMessageVo;
	}

	@Override
	public Map<Long, UserBaseInfo> queryUserBaseInfoByIds(List<Long> userIds) {
		// 查询用户信息
		if (userIds == null) {
			return null;
		}
		// 查询用户列表
		List<UserBaseInfo> userBaseInfoList = ucenterService.getList(UserBaseInfo.class, "userIds", userIds);
		if (CollectionUtils.isEmpty(userBaseInfoList)) {
			return null;
		}
		// 封装list到map
		Map<Long, UserBaseInfo> userMap = new HashMap<Long, UserBaseInfo>();
		for (UserBaseInfo userBaseInfo : userBaseInfoList) {
			userBaseInfo.setMobile(RegexpUtils.replacePhoneNumber(userBaseInfo.getMobile()));
			if (StringUtils.isNotEmpty(userBaseInfo.getImgUrl())) {
				userBaseInfo.setImgUrl(ImageUtils
						.splitJointImageUrl(ImageUtils.replaceImageUrl(userBaseInfo.getImgUrl(), Constant.PIC_SMALL)));
			}
			userMap.put(userBaseInfo.getUserId(), userBaseInfo);
		}
		return userMap;
	}

	@Override
	public UserBaseInfo queryUserBaseInfoById(Long userId) {
		Date startTime = new Date();
		UserBaseInfo userBaseInfo = ucenterService.get(UserBaseInfo.class, "userId", userId);
		Date endTime = new Date();
		System.out.println("queryUserBaseInfoById时间--soa---" + (endTime.getTime() - startTime.getTime()));
		if (userBaseInfo != null && StringUtils.isNotEmpty(userBaseInfo.getImgUrl())) {
			userBaseInfo.setImgUrl(ImageUtils.splitJointImageUrl(userBaseInfo.getImgUrl()));
		}
		this.loadMemberInfo(userBaseInfo);
		return userBaseInfo;
	}

	@Override
	public void loadMemberInfo(UserBaseInfo userBaseInfo) {
		if (userBaseInfo == null) {
			return;
		}
		String memberLevelCode = userBaseInfo.getGrade() != null ? userBaseInfo.getGrade().toString() : "0";
		userBaseInfo.setMemberLevelCode(memberLevelCode);
		userBaseInfo.setIsMember(!"0".equals(memberLevelCode) ? 1 : 0);
		Block block = columnBannerLoaderApi.getBlock("member_name_" + memberLevelCode);
		userBaseInfo.setMemberLevel(block != null ? block.getContent() : null);
	}

	@Override
	public UserMessageVo<UserBaseInfo> h5Login(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 查询手机号是否注册,存在则登录,不存在则注册
		messageVo = queryPhoneNumber(userLoginRecord.getRegion(), userLoginRecord.getMobile());
		if (messageVo.isResult()) {
			messageVo = userBaseInfoService.login(userLoginRecord);
		} else {
			messageVo = userBaseInfoService.register(userLoginRecord);
			if (messageVo != null && messageVo.getT() != null) {
				try {
					creditApi.createCreditAccount(messageVo.getT().getMobile(), messageVo.getT().getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return messageVo;
	}

	@Override
	public UserMessageVo<UserBaseInfo> modifyUserInfo(UserBaseInfo userBaseInfo) {
		return userBaseInfoService.modifyUserInfo(userBaseInfo);
	}

	@Override
	public UserMessageVo<UserFeedback> userFeedback(UserFeedback userFeedback) {

		return userBaseInfoService.userFeedback(userFeedback);
	}

	@Override
	public void updateUserExtInfo(Long userId, Long targetUserId, UserBehaviorTypeEnum userBehaviorType) {
		userExtInfoService.updateUserExtInfo(userId, targetUserId, userBehaviorType);
	}

	@Override
	public void updateUserExtInfo(Long userId) {
		userExtInfoService.updateUserExtInfo(userId);
	}

	@Override
	public void addUser(UserBaseInfo userBaseInfo, UserExtInfo userExtInfo) {
		userBaseInfoService.addUser(userBaseInfo, userExtInfo);
		if (userBaseInfo != null) {
			try {
				creditApi.createCreditAccount(userBaseInfo.getMobile(), userBaseInfo.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public UserMessageVo<UserBaseInfo> bindPhoneNoIdentifyCode(UserLoginRecord userLoginRecord) {

		return userBaseInfoService.bindPhoneNoIdentifyCode(userLoginRecord);
	}

	@Override
	public UserMessageVo<MessageCode> checkMessageCode(String region, String phoneNum, String code) {
		return this.checkMessageCodeNew(region, phoneNum, code, MsgCodeTypeEnum.LOGIN);
	}

	@Override
	public UserMessageVo<MessageCode> checkMessageCodeNew(String region, String phoneNum, String code,
			MsgCodeTypeEnum msgCodeType) {
		UserMessageVo<MessageCode> messageVo = new UserMessageVo<MessageCode>();
		if (Arrays.asList(WHITE_PHONE).contains(String.valueOf(phoneNum))) {
			messageVo.setResult(true);
			return messageVo;
		}
		return userBaseInfoService.checkMessageCodeNew(region, phoneNum, code, msgCodeType);
	}

	@Override
	public UserMessageVo<MessageCode> sendOrderStatusMessage(String region, String phoneNumber, String content) {
		UserMessageVo<MessageCode> messageVo = new UserMessageVo<MessageCode>();
		userBaseInfoService.sendOrderStatusMessage(region, phoneNumber, content);
		return messageVo;
	}

	@Override
	public List<UserExtInfo> getUserExtInfoList(List<Long> userIds) {

		return userExtInfoService.getUserExtInfoList(userIds);
	}

	@Override
	public void cleanUserPostCount(Long userId) {
		userExtInfoService.cleanUserPostCount(userId);
	}

	@Override
	public void updateUserNewFlag(Long userId, String token) {
		if (userId == null) {
			return;
		}
		UserBaseInfo user = new UserBaseInfo();
		user.setUserId(userId);
		user.setNewFlag(0);
		ucenterService.updateBySqlId("updateBaseInfo", user);
		String cacheKey = "user_base_info_by_token_" + token;
		masterRedisTemplate.delete(cacheKey);
	}

	@Override
	public List<UserBaseInfo> queryUserBaseInfo(UserBaseInfo req) {
		return ucenterService.getList(req);
	}

	@Override
	public Integer getLoginCountByUserId(Long userId){
		return ucenterService.getBySqlId(UserLoginRecord.class,"pageCount", "userId", userId);
	}

	/**
	 * 随机获取匿名用户信息
	 *
	 * @return 返回匿名用户信息
	 */
	@Override
	public UserBaseInfo queryRandomUserBaseInfo() {
		return userBaseInfoService.queryRandomUserBaseInfo();
	}
}