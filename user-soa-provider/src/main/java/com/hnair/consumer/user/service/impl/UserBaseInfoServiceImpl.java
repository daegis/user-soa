package com.hnair.consumer.user.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.hnair.consumer.utils.RegexpUtils;
import org.apache.tools.ant.util.regexp.RegexpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hnair.consumer.content.model.SocialContactInfo;
import com.hnair.consumer.content.vo.SocialContactInfoMsg;
import com.hnair.consumer.dao.spi.ICommonDao;
import com.hnair.consumer.system.api.ISystemApi;
import com.hnair.consumer.system.constant.Constans;
import com.hnair.consumer.user.api.IUserBlackListApi;
import com.hnair.consumer.user.enums.LoginTypeEnum;
import com.hnair.consumer.user.enums.MessageCodeStatusEnum;
import com.hnair.consumer.user.enums.MessageResultEnum;
import com.hnair.consumer.user.enums.MsgCodeTypeEnum;
import com.hnair.consumer.user.enums.ThirdTypeEnum;
import com.hnair.consumer.user.enums.UserBehaviorTypeEnum;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.Attention;
import com.hnair.consumer.user.model.BindPhonenumber;
import com.hnair.consumer.user.model.MessageCode;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserExtInfo;
import com.hnair.consumer.user.model.UserFeedback;
import com.hnair.consumer.user.model.UserIdGen;
import com.hnair.consumer.user.model.UserLoginHistory;
import com.hnair.consumer.user.model.UserLoginRecord;
import com.hnair.consumer.user.service.IUserBaseInfoService;
import com.hnair.consumer.user.service.IUserExtInfoService;
import com.hnair.consumer.user.utils.MessageCodeGen;
import com.hnair.consumer.user.utils.MessageIdentifyingCode;
import com.hnair.consumer.user.utils.TokenGen;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.utils.DateUtil;
import com.hnair.consumer.utils.ImageUtils;
import com.hnair.consumer.utils.LogUtil;
import com.hnair.consumer.utils.system.ConfigPropertieUtils;

/**
 * Description: All Rights Reserved.
 * 
 * @version 1.0 2016年11月2日 下午7:26:17 by 李超（li-ch3@hnair.com）创建
 */
@Service
public class UserBaseInfoServiceImpl implements IUserBaseInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBaseInfoServiceImpl.class);
	// 发送时间间隔默认值1分钟
	private static final String message_code_interval_default = "1";
	// 验证码有效时间默认值3分钟
	private static final String message_code_valid_time_default = "3";
	// 短信验证码内容
	private static final String message_content_default = "验证码：#，请尽快填写完成验证，为保障您账户安全，切勿将此码告诉他人。";
	// 有美短信验证码内容
	private static final String message_content_ym_default = "【有美】验证码：#，3分钟内有效。欢迎进入有美社区，分享生活分享美";
	// 平台类型(1:亿美,2:海航通信)
	private static final String message_platform = "2";
	@Resource(name = "ucenterCommonDao")
	private ICommonDao ucenterCommonDao;
	@Resource
	private ISystemApi systemApi;
	@SuppressWarnings("rawtypes")
	@Resource(name = "masterRedisTemplate")
	private RedisTemplate masterRedisTemplate;
	@SuppressWarnings("rawtypes")
	@Resource(name = "slaveRedisTemplate")
	private RedisTemplate slaveRedisTemplate;
	@Resource
	private IUserExtInfoService userExtInfoService;
	@Resource
	private IUserBlackListApi userBlackListApi;

	@Resource
	private RabbitTemplate rabbitTemplate;
	// 消息类型
	private static String UYU_APP_ID = ConfigPropertieUtils.getString("uyu_app_id");

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> register(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> userMessageVo = new UserMessageVo<UserBaseInfo>();
		// 查看用户是否已经注册
		List<UserBaseInfo> ubi = ucenterCommonDao.getList(UserBaseInfo.class, "mobile", userLoginRecord.getMobile());
		if (CollectionUtils.isNotEmpty(ubi)) {
			userMessageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12013.getErrorCode().toString());
			userMessageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12013.getErrorMessage());
			userMessageVo.setResult(false);
			userMessageVo.setT(ubi.get(0));
			return userMessageVo;
		}
		// 生成用户的唯一userId
		Date date = new Date();
		UserIdGen userIdGen = new UserIdGen();
		userIdGen.setCreateDate(date);
		try {
			ucenterCommonDao.save(userIdGen);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		UserBaseInfo userBaseInfo = new UserBaseInfo();
		if (userIdGen.getUserId() != null) {
			userBaseInfo.setUserId(userIdGen.getUserId());
		}
		// 注册用户
		userBaseInfo.setCreateTime(date);
		userBaseInfo.setRegion(userLoginRecord.getRegion());
		userBaseInfo.setMobile(userLoginRecord.getMobile());
		userBaseInfo.setUserName(StringUtils.isEmpty(userLoginRecord.getUserName())
				? String.valueOf("Hi客" + userBaseInfo.getUserId()) : userLoginRecord.getUserName());
		userBaseInfo.setImgUrl(userLoginRecord.getImgUrl());
		userBaseInfo.setLastModifyTime(date);
		userBaseInfo.setUserType(userLoginRecord.getUserType());
		userBaseInfo.setIsNewUser(1);
		userBaseInfo.setRegistChannel(userLoginRecord.getRegistChannel());
		userBaseInfo.setIdentityCard(userLoginRecord.getIdentityCard());
		try {
			ucenterCommonDao.save(userBaseInfo);
		} catch (Exception e) {
			userMessageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12007.getErrorCode().toString());
			userMessageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12007.getErrorMessage());
			userMessageVo.setResult(false);
			return userMessageVo;
		}
		// 注册完成后直接登录
		// 查看用户是否已经登录
		List<UserLoginRecord> userLoginRecordList = ucenterCommonDao.getList(UserLoginRecord.class, "mobile",
				userLoginRecord.getMobile(), "region", userLoginRecord.getRegion(), "deviceType",
				userLoginRecord.getDeviceType());
		if (CollectionUtils.isNotEmpty(userLoginRecordList)) {
			userMessageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12009.getErrorCode().toString());
			userMessageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12009.getErrorMessage());
			userMessageVo.setResult(false);
			userMessageVo.setT(userBaseInfo);
			return userMessageVo;
		}
		String token = TokenGen.genToken();
		// 登录记录
		userBaseInfo.setToken(token);
		userLoginRecord.setUserId(userBaseInfo.getUserId());
		userLoginRecord.setUserName(userBaseInfo.getUserName());
		userLoginRecord.setCreateTime(date);
		userLoginRecord.setLastLoginTime(date);
		userLoginRecord.setLastLoginToken(token);
		try {
			ucenterCommonDao.save(userLoginRecord);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginRecord出错");
			e.printStackTrace();
			throw e;
		}
		// 登录历史记录
		UserLoginHistory userLoginHistory = new UserLoginHistory();
		userLoginHistory.setCreateTime(date);
		userLoginHistory.setDeviceId(userLoginRecord.getDeviceId());
		userLoginHistory.setDeviceType(userLoginRecord.getDeviceType());
		userLoginHistory.setIp(userLoginRecord.getLastLoginIp());
		userLoginHistory.setLoginType(LoginTypeEnum.LOGIN_IN.getType());
		userLoginHistory.setRegion(userLoginRecord.getRegion());
		userLoginHistory.setMobile(userLoginRecord.getMobile());
		userLoginHistory.setUserId(userLoginRecord.getUserId());
		userLoginHistory.setUserName(userLoginRecord.getUserName());
		try {
			ucenterCommonDao.save(userLoginHistory);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginHistory出错");
			e.printStackTrace();
			throw e;
		}
		userBaseInfo.setImgUrl(ImageUtils.splitJointImageUrl(userBaseInfo.getImgUrl()));
		userMessageVo.setT(userBaseInfo);
		userMessageVo.setResult(true);
		return userMessageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<MessageCode> sendMessage(String region, String phoneNum, String appType) {
		return this.sendMessageNew(region, phoneNum, appType, MsgCodeTypeEnum.LOGIN);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<MessageCode> sendMessageNew(String region, String phoneNum, String appType,MsgCodeTypeEnum msgCodeType) {
		UserMessageVo<MessageCode> messageVo = new UserMessageVo<MessageCode>();
		//验证码类型为空默认是登录注册
		if (msgCodeType == null) {
			msgCodeType = MsgCodeTypeEnum.LOGIN;
		}
		if(!RegexpUtils.isPhoneNumber(phoneNum)){
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12031.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12031.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}

		LOGGER.info("发送短信开始,region:" + region + ",phoneNum:" + phoneNum+",msgCodeType:"+msgCodeType.getType());
		// 当前时间
		Date d = new Date();
		// 验证码
		String code = "";
		// 查询是否存在验证码,如果存在未验证的验证码,查询是否在间隔时间以外
		List<MessageCode> messageCode = null;
		try {
			messageCode = ucenterCommonDao.getListFromMaster(MessageCode.class, "mobile", phoneNum, "status", 0, "region", region,
					"msgCodeType", msgCodeType.getType());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (CollectionUtils.isNotEmpty(messageCode)) {
			// 如果在间隔时间内则返回客户端说明间隔时间少于规定时间
			String messageCodeInterval = "";
			try {
				messageCodeInterval = systemApi.getProperty("message_code_interval");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (StringUtils.isEmpty(messageCodeInterval)) {
				messageCodeInterval = message_code_interval_default;
			}
			Date validDate = DateUtil.addMinute(messageCode.get(0).getCreateTime(),
					Integer.parseInt(messageCodeInterval));
			Long result = DateUtil.diffDateTime(d, validDate);
			// 在间隔时间内返回错误码
			if (result < 0) {
				messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12001.getErrorCode().toString());
				messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12001.getErrorMessage());
				messageVo.setResult(false);
				return messageVo;
			}
			// 不在间隔时间内,则先更新之前的记录状态
			if (result >= 0) {
				ucenterCommonDao.updateBySqlId(MessageCode.class, "updateStatusByPhoneNum", "mobile", phoneNum,
						"msgCodeType", msgCodeType.getType(), "region", region, "status", 1);
			}
		}
		// 生成验证码,并且发送验证码.将新纪录存入数据库
		// 验证码：74741，3分钟内有效。欢迎进入游鱼社区，旅行从此游刃有鱼【游鱼】
		code = MessageCodeGen.genCode();
		String content = "";
		String messagePlatForm = "";
		try {
			if ("2".equals(appType)) {
				content = systemApi.getProperty("message_content_ym");
			} else {
				content = systemApi.getProperty("message_content");
			}
			messagePlatForm = systemApi.getProperty("message_platform");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(content)) {
			if ("2".equals(appType)) {
				content = message_content_ym_default;
			} else {
				content = message_content_default;
			}
		}
		if (StringUtils.isEmpty(messagePlatForm)) {
			messagePlatForm = message_platform;
		}
		content = content.replace("#", code);
		String sendResult = "0";
		try {
			LOGGER.info("开始调用短信接口,messagePlatForm:" + messagePlatForm);
			if ("1".equals(messagePlatForm)) {
				sendResult = MessageIdentifyingCode.sendMessage(phoneNum, content);
			} else {
				sendResult = MessageIdentifyingCode
						.sendMessageHNA(((StringUtils.isEmpty(region) ? "86" : region) + phoneNum), content);
			}
		} catch (Exception e) {
			sendResult = MessageResultEnum.RESULT_ERROR.getType();
			e.printStackTrace();

		}
		// 如果成功则插入新数据
		try {
			if (MessageResultEnum.RESULT_SUCCESS.getType().equals(sendResult)) {
				MessageCode mc = new MessageCode();
				mc.setIdentifyCode(code);
				mc.setRegion((StringUtils.isEmpty(region) ? "86" : region));
				mc.setMobile(phoneNum);
				mc.setStatus(MessageCodeStatusEnum.CODE_NO_VALIDATE.getType());
				mc.setCreateTime(d);
				mc.setLastModifyTime(d);
				mc.setMsgCodeType(msgCodeType.getType());
				ucenterCommonDao.save(mc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		messageVo.setResult(true);
		// //零时代码
		// File aFile=new File(codeTemp);//指定文件名
		// //建立输出流
		// FileOutputStream out;
		// try {
		// out = new FileOutputStream(aFile);
		// byte[] b=new byte[1024];
		// String str=code;
		// b=str.getBytes();//进行String到byte[]的转化
		// out.write(b); //写入文本内容
		// out.flush();
		// out.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		return messageVo;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public UserMessageVo<MessageCode> checkMessageCodeNew(String region, String phoneNum, String code,
			MsgCodeTypeEnum msgCodeType) {
		if(msgCodeType==null){
			msgCodeType=MsgCodeTypeEnum.LOGIN;
		}
		
		UserMessageVo<MessageCode> messageVo = new UserMessageVo<MessageCode>();
		// 当前时间
		Date date = new Date();
		// 查询是否存在验证码,如果存在检查是否在有效时间内
		List<MessageCode> messageCode = null;
		try {
			messageCode = ucenterCommonDao.getListFromMaster(MessageCode.class, "mobile", phoneNum.trim(), "status", 0, "region",
					region,"msgCodeType", msgCodeType.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (CollectionUtils.isEmpty(messageCode)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12003.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12003.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		MessageCode mc = messageCode.get(0);
		// 查询验证码是否过了有效时间
		String messageCodeValidTime = "";
		try {
			messageCodeValidTime = systemApi.getProperty("message_code_valid_time");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(messageCodeValidTime)) {
			messageCodeValidTime = message_code_valid_time_default;
		}
		Date validDate = DateUtil.addMinute(messageCode.get(0).getCreateTime(), Integer.parseInt(messageCodeValidTime));
		Long result = DateUtil.diffDateTime(date, validDate);
		// 验证码失效
		if (result > 0) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12004.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12004.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 验证码输入错误
		if (!code.equalsIgnoreCase(mc.getIdentifyCode())) {
			// 如果一个手机号一分钟之内输错3次,则提示一段时间后重试
			if (slaveRedisTemplate.opsForValue().get("error_identifyCode_" + phoneNum.trim()) == null) {
				masterRedisTemplate.opsForValue().set("error_identifyCode_" + phoneNum, 1);
				masterRedisTemplate.expire("error_identifyCode_" + phoneNum, 1, TimeUnit.MINUTES);
			} else {
				Integer errorTime = (Integer) slaveRedisTemplate.opsForValue()
						.get("error_identifyCode_" + phoneNum.trim());
				if (errorTime > 3) {
					masterRedisTemplate.opsForValue().set("error_identifyCode_" + phoneNum, (errorTime + 1));
					masterRedisTemplate.expire("error_identifyCode_" + phoneNum, 10, TimeUnit.MINUTES);
					messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12019.getErrorCode().toString());
					messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12019.getErrorMessage());
					messageVo.setResult(false);
					return messageVo;
				} else {
					masterRedisTemplate.opsForValue().set("error_identifyCode_" + phoneNum, (errorTime + 1));
				}
			}
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12005.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12005.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		try {
			// 验证码正确则更新状态
			ucenterCommonDao.updateBySqlId(MessageCode.class, "updateStatusByPhoneNum", "mobile", phoneNum, "status", 1,
					"region", region, "msgCodeType", msgCodeType.getType());
		} catch (Exception e) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12006.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12006.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		messageVo.setResult(true);
		return messageVo;
	}

	/**
	 * 随机获取匿名用户信息
	 *
	 * @return 返回匿名用户信息
	 */
	@Override
	public UserBaseInfo queryRandomUserBaseInfo() {
		return ucenterCommonDao.getBySqlId(UserBaseInfo.class,"queryRandomUserBaseInfo");
	}


	@Override
	public UserMessageVo<MessageCode> checkMessageCode(String region, String phoneNum, String code) {
		return this.checkMessageCodeNew(region, phoneNum, code, MsgCodeTypeEnum.LOGIN);
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> login(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 当前日期
		Date date = new Date();
		// 如果验证正确,生成token并且保存登录信息
		List<UserBaseInfo> userBaseInfo = ucenterCommonDao.getList(UserBaseInfo.class, "mobile",
				userLoginRecord.getMobile(), "region", userLoginRecord.getRegion());
		if (CollectionUtils.isEmpty(userBaseInfo)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12008.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12008.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 先注销
		// loginOut(userLoginRecord);
		// 开始登录
		String token = TokenGen.genToken();
		UserBaseInfo ubi = userBaseInfo.get(0);
		ubi.setIsNewUser(0);
		// 登录记录
		ubi.setToken(token);
		if (StringUtils.isNotEmpty(ubi.getImgUrl())) {
			ubi.setImgUrl(ImageUtils.splitJointImageUrl(ubi.getImgUrl()));
		}
		if(null==ubi.getCreateTime()){
			ubi.setCreateTime(date);
		}
		userLoginRecord.setUserId(ubi.getUserId());
		userLoginRecord.setUserName(ubi.getUserName());
		userLoginRecord.setCreateTime(date);
		userLoginRecord.setLastLoginTime(date);
		userLoginRecord.setLastLoginToken(token);
		try {
			ucenterCommonDao.save(userLoginRecord);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存L出错");
			e.printStackTrace();
			throw e;
		}
		// 登录历史记录
		UserLoginHistory userLoginHistory = new UserLoginHistory();
		userLoginHistory.setCreateTime(date);
		userLoginHistory.setDeviceId(userLoginRecord.getDeviceId());
		userLoginHistory.setDeviceType(userLoginRecord.getDeviceType());
		userLoginHistory.setIp(userLoginRecord.getLastLoginIp());
		userLoginHistory.setLoginType(LoginTypeEnum.LOGIN_IN.getType());
		userLoginHistory.setRegion(userLoginRecord.getRegion());
		userLoginHistory.setMobile(userLoginRecord.getMobile());
		userLoginHistory.setUserId(userLoginRecord.getUserId());
		userLoginHistory.setUserName(userLoginRecord.getUserName());
		try {
			ucenterCommonDao.save(userLoginHistory);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginHistory出错");
			e.printStackTrace();
			throw e;
		}
		// 如果用户头像不为空,则替换域名
		ubi.setImgUrl(ImageUtils.splitJointImageUrl(ubi.getImgUrl()));
		messageVo.setT(ubi);
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> loginOut(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 当前日期
		Date date = new Date();
		// 查看用户是否已经注销
		List<UserLoginRecord> userLoginRecordList = ucenterCommonDao.getList(UserLoginRecord.class, "token",
				userLoginRecord.getLastLoginToken(), "deviceType", userLoginRecord.getDeviceType());
		if (CollectionUtils.isEmpty(userLoginRecordList)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12010.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12010.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		UserLoginRecord ubi = userLoginRecordList.get(0);
		try {
			ucenterCommonDao.deleteBySqlId(UserLoginRecord.class, "deleteByToken", "lastLoginToken",
					userLoginRecord.getLastLoginToken());

		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginRecord出错");
			e.printStackTrace();
			throw e;
		}
		// 注销历史记录
		UserLoginHistory userLoginHistory = new UserLoginHistory();
		userLoginHistory.setCreateTime(date);
		userLoginHistory.setDeviceId(userLoginRecord.getDeviceId());
		userLoginHistory.setDeviceType(userLoginRecord.getDeviceType());
		userLoginHistory.setIp(userLoginRecord.getLastLoginIp());
		userLoginHistory.setLoginType(LoginTypeEnum.LOGIN_OUT.getType());
		userLoginHistory.setRegion(userLoginRecord.getRegion());
		userLoginHistory.setRegion(ubi.getRegion());
		userLoginHistory.setMobile(ubi.getMobile());
		userLoginHistory.setUserId(ubi.getUserId());
		userLoginHistory.setMac(userLoginRecord.getLastLoginMac());
		userLoginHistory.setUserName(ubi.getUserName());
		try {
			ucenterCommonDao.save(userLoginHistory);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginHistory出错");
			e.printStackTrace();
			throw e;
		}
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> queryBindPhone(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 查询用户是否绑定
		List<BindPhonenumber> bindPhonenumber = ucenterCommonDao.getList(BindPhonenumber.class, "openId",
				userLoginRecord.getOpenId());
		if (CollectionUtils.isEmpty(bindPhonenumber)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12011.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12011.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		BindPhonenumber bp = bindPhonenumber.get(0);
		// 用户已经绑定直接登录
		List<UserBaseInfo> userBaseInfo = ucenterCommonDao.getList(UserBaseInfo.class, "mobile", bp.getMobile(),
				"region", bp.getRegion());
		if (CollectionUtils.isEmpty(userBaseInfo)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12008.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12008.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 没有登录直接登录
		userLoginRecord.setMobile(bp.getMobile());
		userLoginRecord.setRegion(bp.getRegion());
		messageVo = login(userLoginRecord);
		messageVo.setT(messageVo.getT());
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> bindPhone(UserLoginRecord userLoginRecord) {
		LOGGER.info("绑定昵称:" + userLoginRecord.getUserName() + ",openID:" + userLoginRecord.getOpenId());
		String bindUserName = userLoginRecord.getUserName();
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 当前时间
		Date date = new Date();
		// 并行执行
		Map<String, Object> tempResult = new HashMap<String, Object>();
		// forkJoinPool.submit(() -> {
		Arrays.asList(new String[] { "bindPhonenumber", "checkMessageCode" }).parallelStream().forEach(p -> {
			if ("bindPhonenumber".equals(p)) {
				tempResult.put(p, ucenterCommonDao.getList(BindPhonenumber.class, "openId", userLoginRecord.getOpenId(),
						"mobile", userLoginRecord.getMobile(), "region", userLoginRecord.getRegion()));
			}
			if ("checkMessageCode".equals(p)) {
				tempResult.put(p, this.checkMessageCodeNew(userLoginRecord.getRegion(), userLoginRecord.getMobile(),
						userLoginRecord.getIdentifyCode(), MsgCodeTypeEnum.LOGIN));
			}
		});
		// });

		// 查询用户是否绑定
		List<BindPhonenumber> bindPhonenumber = tempResult.get("bindPhonenumber") != null
				? (List<BindPhonenumber>) tempResult.get("bindPhonenumber") : null;
		if (CollectionUtils.isNotEmpty(bindPhonenumber)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12012.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12012.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 检查验证码
		UserMessageVo<MessageCode> userMessageVo = tempResult.get("checkMessageCode") != null
				? (UserMessageVo<MessageCode>) tempResult.get("checkMessageCode") : null;
		if (!userMessageVo.isResult()) {
			messageVo.setErrorCode(userMessageVo.getErrorCode());
			messageVo.setErrorMessage(userMessageVo.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 注册
		messageVo = register(userLoginRecord);
		System.out.println("register ==== " + JSON.toJSONString(messageVo));
		// 如果用户已经注册则登录
		if (!messageVo.isResult()) {
			messageVo = login(userLoginRecord);
			System.out.println("login ==== " + JSON.toJSONString(messageVo));
		}
		final UserMessageVo<UserBaseInfo> finalMessageVo = messageVo;
		System.out.println("messageVo ==== " + JSON.toJSONString(messageVo));
		// 没有绑定则绑定
		try {
			BindPhonenumber bp = new BindPhonenumber();
			bp.setBindType(userLoginRecord.getBindType());
			bp.setRegion(userLoginRecord.getRegion());
			bp.setMobile(userLoginRecord.getMobile());
			bp.setOpenId(userLoginRecord.getOpenId());
			bp.setCreateTime(date);
			bp.setLastModifyTime(date);
			bp.setUserId(finalMessageVo.getT().getUserId());
			ucenterCommonDao.save(bp);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// 如果用户昵称是系统默认的,则更新用户昵称
		UserBaseInfo ubi = finalMessageVo.getT();
		LOGGER.info("更新用户昵称:" + (ubi == null ? null : ubi.getUserName()) + ",用户昵称是否包含系统名称:"
				+ (ubi == null ? null : ubi.getUserName().trim().contains("用户宝宝")) + "绑定昵称:"
				+ userLoginRecord.getUserName());
		if (ubi != null && StringUtils.isNotEmpty(ubi.getUserName()) && ubi.getUserName().trim().contains("用户宝宝")) {
			UserBaseInfo u = new UserBaseInfo();
			u.setUserName(bindUserName);
			u.setLastModifyTime(date);
			u.setUserId(ubi.getUserId());
			try {
				ucenterCommonDao.updateBySqlId("updateBaseInfo", u);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		messageVo.setT(messageVo.getT());
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> bindPhoneV2(UserLoginRecord userLoginRecord) {
		LOGGER.info("绑定昵称:" + userLoginRecord.getUserName() + ",openID:" + userLoginRecord.getOpenId());
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 当前时间
		Date date = new Date();
		// 并行执行
		Map<String, Object> tempResult = new HashMap<String, Object>();
		Arrays.asList(new String[] { "bindPhonenumber", "checkMessageCode" }).parallelStream().forEach(p -> {
			if ("bindPhonenumber".equals(p)) {
				tempResult.put(p, ucenterCommonDao.getList(BindPhonenumber.class, "openId", userLoginRecord.getOpenId(),
						"mobile", userLoginRecord.getMobile(), "region", userLoginRecord.getRegion()));
			}
			if ("checkMessageCode".equals(p)) {
				tempResult.put(p, this.checkMessageCodeNew(userLoginRecord.getRegion(), userLoginRecord.getMobile(),
						userLoginRecord.getIdentifyCode(), MsgCodeTypeEnum.LOGIN));
			}
		});

		// 查询用户是否绑定
		List<BindPhonenumber> bindPhonenumber = tempResult.get("bindPhonenumber") != null
				? (List<BindPhonenumber>) tempResult.get("bindPhonenumber") : null;
		if (CollectionUtils.isNotEmpty(bindPhonenumber)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12012.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12012.getErrorMessage());
			messageVo.setResult(false);
			messageVo.setT(ucenterCommonDao.get(UserBaseInfo.class, "openId", userLoginRecord.getOpenId()));
			return messageVo;
		}
		// 检查验证码
		UserMessageVo<MessageCode> userMessageVo = tempResult.get("checkMessageCode") != null
				? (UserMessageVo<MessageCode>) tempResult.get("checkMessageCode") : null;
		if (!userMessageVo.isResult()) {
			messageVo.setErrorCode(userMessageVo.getErrorCode());
			messageVo.setErrorMessage(userMessageVo.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 查看手机号是否已经注册
		List<UserBaseInfo> ubi = ucenterCommonDao.getList(UserBaseInfo.class, "mobile", userLoginRecord.getMobile());
		if (CollectionUtils.isNotEmpty(ubi)) {
			UserMessageVo<UserBaseInfo> vo = new UserMessageVo<UserBaseInfo>();
			vo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12021.getErrorCode().toString());
			vo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12021.getErrorMessage());
			vo.setResult(false);
			vo.setT(ubi.get(0));
			return vo;
		}
		UserBaseInfo user = ucenterCommonDao.get(UserBaseInfo.class, "openId", userLoginRecord.getOpenId());
		// 没有绑定则绑定
		try {
			if (user == null) {
				messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12008.getErrorCode().toString());
				messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12008.getErrorMessage());
				messageVo.setResult(false);
				return messageVo;
			}
			user.setMobile(userLoginRecord.getMobile());
			BindPhonenumber bp = new BindPhonenumber();
			bp.setBindType(userLoginRecord.getBindType());
			bp.setRegion(userLoginRecord.getRegion());
			bp.setMobile(userLoginRecord.getMobile());
			bp.setOpenId(userLoginRecord.getOpenId());
			bp.setCreateTime(date);
			bp.setLastModifyTime(date);
			bp.setUserId(user.getUserId());
			ucenterCommonDao.save(bp);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// 回写绑定的手机号至用户信息
		UserLoginRecord record = new UserLoginRecord();
		UserLoginHistory history = new UserLoginHistory();
		record.setUserId(user.getUserId());
		record.setMobile(userLoginRecord.getMobile());
		history.setUserId(user.getUserId());
		history.setMobile(user.getMobile());
		ucenterCommonDao.update(user);
		ucenterCommonDao.update(record);
		ucenterCommonDao.update(history);
		user.setIsNewUser(1);
		messageVo.setT(user);
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> thirdPartyRegister(UserLoginRecord userLoginRecord) {
		LOGGER.info("绑定昵称:" + userLoginRecord.getUserName() + ",openID:" + userLoginRecord.getOpenId() + "imgUrl is "
				+ userLoginRecord.getImgUrl());
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 注册，登录
		messageVo = thirdPartyRegiste(userLoginRecord);
		LOGGER.info("messageVo ==== " + JSON.toJSONString(messageVo));
		return messageVo;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private UserMessageVo<UserBaseInfo> thirdPartyRegiste(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> userMessageVo = new UserMessageVo<UserBaseInfo>();
		// 查看用户是否已经注册
		List<UserBaseInfo> ubi = ucenterCommonDao.getList(UserBaseInfo.class, "openId", userLoginRecord.getOpenId());
		Date date = new Date();
		UserBaseInfo info = null;
		if (CollectionUtils.isNotEmpty(ubi)) {
			LOGGER.info("thirdparty user has registed, userId is " + ubi.get(0).getUserId());
			info = ubi.get(0);
			info.setIsNewUser(0);
		} else {
			// 针对原有数据做特殊处理,去绑定表根据openId去查；因为userBaseInfo表之前没有openId字段
			List<BindPhonenumber> bindPhonenumbers = ucenterCommonDao.getList(BindPhonenumber.class, "openId",
					userLoginRecord.getOpenId());
			if (CollectionUtils.isNotEmpty(bindPhonenumbers)) {
				LOGGER.info("thirdparty user has registed, userId is " + bindPhonenumbers.get(0).getUserId());
				info = ucenterCommonDao.get(UserBaseInfo.class, "userId", bindPhonenumbers.get(0).getUserId());
				info.setIsNewUser(0);
			} else {
				boolean flag = true;
				if (userLoginRecord.getBindType().intValue() == Integer.valueOf(ThirdTypeEnum.QQ.getDeviceTypeNo())) {// QQ不同设备openId不同，
					List<UserBaseInfo> userBaseInfos = ucenterCommonDao.getList(UserBaseInfo.class, "userName",
							userLoginRecord.getUserName(), "bindType",
							Integer.valueOf(ThirdTypeEnum.QQ.getDeviceTypeNo()));
					if (CollectionUtils.isNotEmpty(userBaseInfos)) {
						LOGGER.info("thirdparty user has registed, userId is " + userBaseInfos.get(0).getUserId());
						info = userBaseInfos.get(0);
						info.setIsNewUser(0);
						flag = false;
					}
				}
				if (flag) {
					// 生成用户的唯一userId
					UserIdGen userIdGen = new UserIdGen();
					userIdGen.setCreateDate(date);
					try {
						ucenterCommonDao.save(userIdGen);
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
					UserBaseInfo userBaseInfo = new UserBaseInfo();
					if (userIdGen.getUserId() != null) {
						userBaseInfo.setUserId(userIdGen.getUserId());
					}
					// 注册用户
					userBaseInfo.setCreateTime(date);
					userBaseInfo.setRegion(userLoginRecord.getRegion());
					userBaseInfo.setMobile(userLoginRecord.getMobile());
					userBaseInfo.setUserName(StringUtils.isEmpty(userLoginRecord.getUserName())
							? String.valueOf("用户宝宝" + userBaseInfo.getUserId()) : userLoginRecord.getUserName());
					userBaseInfo.setImgUrl(userLoginRecord.getImgUrl());
					userBaseInfo.setLastModifyTime(date);
					userBaseInfo.setUserType(userLoginRecord.getUserType());
					userBaseInfo.setOpenId(userLoginRecord.getOpenId());
					userBaseInfo.setBindType(userLoginRecord.getBindType());
					userBaseInfo.setIsNewUser(1);
					userBaseInfo.setGender(userLoginRecord.getGender());
					LOGGER.info("imgUrl is " + userBaseInfo.getImgUrl());
					try {
						ucenterCommonDao.save(userBaseInfo);
					} catch (Exception e) {
						userMessageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12007.getErrorCode().toString());
						userMessageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12007.getErrorMessage());
						userMessageVo.setResult(false);
						return userMessageVo;
					}
					info = userBaseInfo;
				}
			}
		}
		// 注册完成后直接登录
		String token = TokenGen.genToken();
		// 登录记录
		info.setToken(token);
		userLoginRecord.setUserId(info.getUserId());
		userLoginRecord.setUserName(info.getUserName());
		userLoginRecord.setCreateTime(date);
		userLoginRecord.setLastLoginTime(date);
		userLoginRecord.setLastLoginToken(token);
		try {
			ucenterCommonDao.save(userLoginRecord);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginRecord出错");
			e.printStackTrace();
			throw e;
		}
		// 登录历史记录
		UserLoginHistory userLoginHistory = new UserLoginHistory();
		userLoginHistory.setCreateTime(date);
		userLoginHistory.setDeviceId(userLoginRecord.getDeviceId());
		userLoginHistory.setDeviceType(userLoginRecord.getDeviceType());
		userLoginHistory.setIp(userLoginRecord.getLastLoginIp());
		userLoginHistory.setLoginType(LoginTypeEnum.LOGIN_IN.getType());
		userLoginHistory.setRegion(userLoginRecord.getRegion());
		userLoginHistory.setMobile(userLoginRecord.getMobile());
		userLoginHistory.setUserId(userLoginRecord.getUserId());
		userLoginHistory.setUserName(userLoginRecord.getUserName());
		try {
			ucenterCommonDao.save(userLoginHistory);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "保存userLoginHistory出错");
			e.printStackTrace();
			throw e;
		}
		info.setImgUrl(ImageUtils.splitJointImageUrl(info.getImgUrl()));

		userMessageVo.setT(info);
		userMessageVo.setResult(true);
		return userMessageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<Attention> attention(Attention attention) {
		UserMessageVo<Attention> messageVo = new UserMessageVo<Attention>();
		// 查看关注用户是否存在
		List<UserBaseInfo> userBaseInfo = ucenterCommonDao.getList(UserBaseInfo.class, "userId",
				attention.getAttentionUserId());
		if (CollectionUtils.isEmpty(userBaseInfo)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12008.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12008.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 查看是否已经关注
		List<Attention> attentionList = ucenterCommonDao.getList(Attention.class, "userId", attention.getUserId(),
				"attentionUserId", attention.getAttentionUserId());
		if (CollectionUtils.isNotEmpty(attentionList)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12014.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12014.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 验证是否是黑名单
		boolean valid = userBlackListApi.validBlackList(attention.getAttentionUserId(), attention.getUserId());
		if (valid) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12028.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12028.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}

		// 查询是否相互关注
		List<Attention> targetAttentionList = ucenterCommonDao.getList(Attention.class, "userId",
				attention.getAttentionUserId(), "attentionUserId", attention.getUserId());
		Integer isMutualAttention = 0;
		if (CollectionUtils.isNotEmpty(targetAttentionList)) {
			isMutualAttention = 1;
			// 更新是否关注字段
			targetAttentionList.get(0).setIsMutualAttention(isMutualAttention);
			ucenterCommonDao.update(targetAttentionList.get(0));
		}
		// 关注
		attention.setCreateTime(new Date());
		attention.setIsMutualAttention(isMutualAttention);
		ucenterCommonDao.save(attention);
		// 发送消息
		try {
			SocialContactInfoMsg scInfoMsg = new SocialContactInfoMsg();
			scInfoMsg.setAppId(Integer.parseInt(UYU_APP_ID));
			SocialContactInfo sci = new SocialContactInfo();
			sci.setAuthorId(attention.getAttentionUserId());
			sci.setCustId(attention.getUserId());
			sci.setType((short) 4);
			sci.setCreateDate(new Date());
			scInfoMsg.setSocialContactInfo(sci);
			rabbitTemplate.convertAndSend(Constans.PUSH_TRANSMIT_MSG_KEY_APP_UYU, scInfoMsg);
		} catch (Exception e) {
			LOGGER.error("发送消息失败", e);
			e.printStackTrace();
		}
		// 记录用户统计信息
		try {
			userExtInfoService.updateUserExtInfo(attention.getUserId(), attention.getAttentionUserId(),
					UserBehaviorTypeEnum.ATTENTION);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		messageVo.setT(attention);
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<Attention> cancelAttention(Attention attention) {
		UserMessageVo<Attention> messageVo = new UserMessageVo<Attention>();
		// 查看是否已经取消关注
		List<Attention> attentionList = ucenterCommonDao.getList(Attention.class, "userId", attention.getUserId(),
				"attentionUserId", attention.getAttentionUserId());
		if (CollectionUtils.isEmpty(attentionList)) {
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12015.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12015.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		// 查询是否相互关注,如果对方已经关注则设置相互关注字段为0
		List<Attention> targetAttentionList = ucenterCommonDao.getList(Attention.class, "userId",
				attention.getAttentionUserId(), "attentionUserId", attention.getUserId());
		if (CollectionUtils.isNotEmpty(targetAttentionList)) {
			targetAttentionList.get(0).setIsMutualAttention(0);
			ucenterCommonDao.update(targetAttentionList.get(0));
		}
		// 取消关注
		ucenterCommonDao.deleteBySqlId(Attention.class, "deleteByMap", "userId", attention.getUserId(),
				"attentionUserId", attention.getAttentionUserId());
		// 记录用户统计信息
		try {
			userExtInfoService.updateUserExtInfo(attention.getUserId(), attention.getAttentionUserId(),
					UserBehaviorTypeEnum.CANCEL_ATTENTION);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	public Map<String, Object> queryAttentionInfo(Long userId, Long targetUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Arrays.asList(new String[] { "isAttention", "myCount", "attentionCount", "targetCount" }).parallelStream()
				.forEach(p -> {
					Object tempResult = null;
					if (userId != null) {
						if ("isAttention".equals(p)) {
							// 查询用户是否已经关注此用户
							List<Attention> attentionList = ucenterCommonDao.getList(Attention.class, "userId", userId,
									"attentionUserId", targetUserId);
							tempResult = CollectionUtils.isEmpty(attentionList) ? "0"
									: ((attentionList.get(0).getIsMutualAttention()) == 0 ? "1" : "2");
						}
						if ("myCount".equals(p)) {
							// 查询用户被关注数量
							tempResult = ucenterCommonDao.getBySqlId(Attention.class, "pageCount", "attentionUserId",
									userId);
						}
						if ("attentionCount".equals(p)) {
							// 查询用户关注数量
							tempResult = ucenterCommonDao.getBySqlId(Attention.class, "pageCount", "userId", userId);
						}

					}
					// 查询目标用户关注数量
					if (targetUserId != null && "targetCount".equals(p)) {
						tempResult = ucenterCommonDao.getBySqlId(Attention.class, "pageCount", "attentionUserId",
								targetUserId);
					}
					map.put(p, tempResult);
				});
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> modifyUserInfo(UserBaseInfo userBaseInfo) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		userBaseInfo.setLastModifyTime(new Date());
		if (StringUtils.isNotEmpty(userBaseInfo.getImgUrl())) {
			userBaseInfo.setImgUrl(ImageUtils.replaceDomainName(userBaseInfo.getImgUrl()));
		}
		try {
			ucenterCommonDao.updateBySqlId("updateBaseInfo", userBaseInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12016.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12016.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserFeedback> userFeedback(UserFeedback userFeedback) {
		UserMessageVo<UserFeedback> messageVo = new UserMessageVo<UserFeedback>();
		userFeedback.setCreateTime(new Date());
		try {
			ucenterCommonDao.save(userFeedback);
		} catch (Exception e) {
			e.printStackTrace();
			messageVo.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12017.getErrorCode().toString());
			messageVo.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12017.getErrorMessage());
			messageVo.setResult(false);
			return messageVo;
		}
		messageVo.setResult(true);
		return messageVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addUser(UserBaseInfo userBaseInfo, UserExtInfo userExtInfo) {
		Date now = new Date();
		UserIdGen userIdGen = new UserIdGen();
		userIdGen.setCreateDate(now);
		ucenterCommonDao.save(userIdGen);
		userBaseInfo.setUserId(userIdGen.getUserId());
		userBaseInfo.setCreateTime(now);
		userBaseInfo.setLastModifyTime(now);
		ucenterCommonDao.save(userBaseInfo);
		userExtInfo.setUserId(userIdGen.getUserId());
		userExtInfo.setCreateDate(now);
		ucenterCommonDao.save(userExtInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public UserMessageVo<UserBaseInfo> bindPhoneNoIdentifyCode(UserLoginRecord userLoginRecord) {
		UserMessageVo<UserBaseInfo> messageVo = new UserMessageVo<UserBaseInfo>();
		// 当前时间
		Date date = new Date();
		// 查询用户是否绑定
		List<BindPhonenumber> bindPhonenumber = ucenterCommonDao.getList(BindPhonenumber.class, "openId",
				userLoginRecord.getOpenId(), "mobile", userLoginRecord.getMobile(), "region",
				userLoginRecord.getRegion());
		if (CollectionUtils.isNotEmpty(bindPhonenumber)) {
			messageVo.setResult(true);
			return messageVo;
		}
		// 没有绑定则绑定
		try {
			BindPhonenumber bp = new BindPhonenumber();
			bp.setBindType(userLoginRecord.getBindType());
			bp.setRegion(userLoginRecord.getRegion());
			bp.setMobile(userLoginRecord.getMobile());
			bp.setOpenId(userLoginRecord.getOpenId());
			bp.setCreateTime(date);
			bp.setLastModifyTime(date);
			bp.setUserId(messageVo.getT().getUserId());
			ucenterCommonDao.save(bp);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		messageVo.setT(messageVo.getT());
		messageVo.setResult(true);
		return messageVo;
	}
	public static void main(String[] args){
		new UserBaseInfoServiceImpl().sendOrderStatusMessage("86", "13718456928", "hello");
	}
	@Override
	public UserMessageVo<MessageCode> sendOrderStatusMessage(String region, String phoneNumber, String content) {
		UserMessageVo<MessageCode> messageVo = new UserMessageVo<MessageCode>();
		//假号码不能发送短信
		if("13900000000".equals(phoneNumber) || "13800000000".equals(phoneNumber)
				|| "13800138000".equals(phoneNumber)){
			return messageVo;
		}
		
		String messagePlatForm = "";
		try {
			messagePlatForm = systemApi.getProperty("message_platform");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(messagePlatForm)) {
			messagePlatForm = message_platform;
		}
		String sendResult = "0";
		try {
			LOGGER.info("开始调用短信接口,messagePlatForm:" + messagePlatForm);
			if ("1".equals(messagePlatForm)) {
				sendResult = MessageIdentifyingCode.sendMessage(phoneNumber, content);
			} else {
				sendResult = MessageIdentifyingCode
						.sendMessageHNA(((StringUtils.isEmpty(region) ? "86" : region) + phoneNumber), content);
			}
		} catch (Exception e) {
			sendResult = MessageResultEnum.RESULT_ERROR.getType();
			e.printStackTrace();

		}
		LOGGER.info("开始调用短信结果:" + sendResult);
		return messageVo;
	}

}
