package com.hnair.consumer.user.api;

import com.hnair.consumer.user.enums.MsgCodeTypeEnum;
import com.hnair.consumer.user.enums.UserBehaviorTypeEnum;
import com.hnair.consumer.user.model.*;
import com.hnair.consumer.user.vo.UserBaseInfoVo;
import com.hnair.consumer.user.vo.UserMessageVo;

import java.util.List;
import java.util.Map;

/**
 * Description: 用户接口
 * All Rights Reserved.
 * @version 1.0  2016年11月1日 下午4:27:47  by 李超（li-ch3@hnair.com）创建
 */
public interface IUserApi {
	/**
	 * Description: 登录
	 * @Version1.0 2016年11月4日 上午11:21:10 by 李超（li-ch3@hnair.com）创建
	 * @param userBaseInfo
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> login(UserLoginRecord userLoginRecord);
	
	/**
	 * Description: 注销
	 * @Version1.0 2016年11月4日 下午3:05:42 by 李超（li-ch3@hnair.com）创建
	 * @param userBaseInfo
	 * @param userLoginRecord
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> loginOut(UserLoginRecord userLoginRecord);
	
	/**
	 * Description: 根据token查询用户信息
	 * @Version1.0 2016年11月4日 下午3:58:59 by 李超（li-ch3@hnair.com）创建
	 * @param token
	 * @return
	 */
	public UserBaseInfo getUserBaseInfoByToken(String token);
	
	/**
	 * Description: 查询手机号是否被注册
	 * @Version1.0 2016年11月2日 下午7:00:57 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNumber
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> queryPhoneNumber(String region,String phoneNumber);
	/**
	 * Description: 注册接口
	 * @Version1.0 2016年11月1日 下午5:58:21 by 李超（li-ch3@hnair.com）创建
	 * @param userBaseInfo
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> register(UserLoginRecord userLoginRecord);
	/**
	 * Description: 发送短信验证码
	 * @Version1.0 2016年11月3日 上午10:22:15 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNumber
	 * @return
	 */
	public UserMessageVo<MessageCode> sendMessage(String region,String phoneNumber,String ipAddr,String appType);	
	/**
	 * Description: 发送短信验证码
	 * @Version1.0 2016年11月3日 上午10:22:15 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNumber
	 * @return
	 */
	public UserMessageVo<MessageCode> sendMessage(String region,String phoneNumber,String ipAddr);
	
	/**
	 * Description: 查询账号是否被绑定
	 * @Version1.0 2016年11月5日 下午4:54:07 by 李超（li-ch3@hnair.com）创建
	 * @param openId
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> queryBindPhone(UserLoginRecord userLoginRecord);
	/**
	 * Description: 第三方登录绑定手机号
	 * @Version1.0 2016年11月5日 下午4:54:24 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNumber
	 * @param openId
	 * @param type
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> bindPhone(UserLoginRecord userLoginRecord);
	/**
	 * Description: 第三方绑定手机号V2
	 * @Version1.0 2017年07月11日 下午14:54:24 by shiw.zhao@hnair.com创建
	 * @param userLoginRecord
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> bindPhoneV2(UserLoginRecord userLoginRecord);

	/**
	 * Description: 第三方登录
	 * @Version1.0 2017年07月11日 上午09:54:24 by shiw.zhao@hnair.com 创建
	 * @param userLoginRecord
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> thirdPartyRegister(UserLoginRecord userLoginRecord);

	/**
	 * Description: 批量查询用户id
	 * @Version1.0 2016年11月7日 下午5:11:31 by 李超（li-ch3@hnair.com）创建
	 * @param userIds
	 * @return
	 */
	public Map<Long,UserBaseInfo> queryUserBaseInfoByIds(List<Long> userIds);
	/**
	 * Description: 查询用户基本信息
	 * @Version1.0 2016年11月8日 下午8:59:07 by 李超（li-ch3@hnair.com）创建
	 * @param userId
	 * @return
	 */
	public UserBaseInfo queryUserBaseInfoById(Long userId);
	/**
	 * 获取用户的会员信息
	 * @param userBaseInfo
	 */
	public void loadMemberInfo(UserBaseInfo userBaseInfo);
	/**
	 * Description: h5小工具接口
	 * @Version1.0 2016年11月15日 下午7:49:03 by 李超（li-ch3@hnair.com）创建
	 * @param userLoginRecord
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> h5Login(UserLoginRecord userLoginRecord);
	/**
	 * Description: 修改个人信息
	 * @Version1.0 2016年11月17日 下午6:04:51 by 李超（li-ch3@hnair.com）创建
	 * @param userBaseInfo
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> modifyUserInfo(UserBaseInfo userBaseInfo);
	/**
	 * Description: 用户反馈
	 * @Version1.0 2016年11月17日 下午8:01:27 by 李超（li-ch3@hnair.com）创建
	 * @param userFeedback
	 * @return
	 */
	public UserMessageVo<UserFeedback> userFeedback(UserFeedback userFeedback);



	/**
	 * @param userId
	 * @param targetUserId
	 * @param userBehaviorType	行为类型
	 */
	public void updateUserExtInfo(Long userId, Long targetUserId, UserBehaviorTypeEnum userBehaviorType);

	/**
	 * 用户发帖
	 * @param userId
	 */
	public void   updateUserExtInfo(Long userId);

	/**
	 * 新增用户
	 * @param userBaseInfo
     */
	public  void addUser(UserBaseInfo userBaseInfo,UserExtInfo userExtInfo);
	
	/**
	 * 批量获取用户扩展信息
	 * @param userIds
	 * @return
	 */
	public List<UserExtInfo> getUserExtInfoList(List<Long> userIds);
	
	/**
	 * Description: 无验证码的绑定手机
	 * @Version1.0 2016年12月19日 下午1:14:37 by 李超（li-ch3@hnair.com）创建
	 * @param userLoginRecord
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> bindPhoneNoIdentifyCode(UserLoginRecord userLoginRecord);
	/**
	 * Description: 检查验证码是否正确
	 * @Version1.0 2016年12月19日 下午4:03:33 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNum
	 * @param code
	 * @return
	 */
	public UserMessageVo<MessageCode> checkMessageCode(String region,String phoneNum,String code);
	/**
	 * Description: 发送订单状态
	 * @Version1.0 2017年2月24日 上午9:36:40 by 李超（li-ch3@hnair.com）创建
	 * @param region
	 * @param phoneNumber
	 * @param content
	 * @return
	 */
	public UserMessageVo<MessageCode> sendOrderStatusMessage(String region,String phoneNumber,String content);
	
	/**
	 * 
	 * @author 许文轩
	 * @comment 清空用户泡泡数量
	 * @date 2017年3月29日 上午9:06:01
	 */
	public void cleanUserPostCount(Long userId);
	
	public void updateUserNewFlag(Long userId,String token);
	
	/**
	 * 查询用户集合
	 * @param req
	 * @return
	 */
	public List<UserBaseInfo> queryUserBaseInfo(UserBaseInfo req);

	/**
	 * 
	 * @author 许文轩
	 * @comment 发送验证码新接口
	 * @date 2017年11月27日 下午4:43:36
	 */
	public UserMessageVo<MessageCode> sendMessageNew(String region, String phoneNumber, String ipAddr, String appType,
			MsgCodeTypeEnum msgCodeType);

	/**
	 * 
	 * @author 许文轩
	 * @comment 验证验证码新接口
	 * @date 2017年11月27日 下午5:04:42
	 */
	public UserMessageVo<MessageCode> checkMessageCodeNew(String region, String phoneNum, String code,
			MsgCodeTypeEnum msgCodeType);

	/**
	 * 登录次数
	 * @param userId
	 * @return
	 */
	public Integer getLoginCountByUserId(Long userId);

	/**
	 * 随机获取匿名用户信息
	 * @return 返回匿名用户信息
	 */
	public UserBaseInfo queryRandomUserBaseInfo();
}
