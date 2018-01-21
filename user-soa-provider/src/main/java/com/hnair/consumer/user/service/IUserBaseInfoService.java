package com.hnair.consumer.user.service;

import java.util.Map;

import com.hnair.consumer.user.enums.MsgCodeTypeEnum;
import com.hnair.consumer.user.model.*;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * 
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 下午7:26:17  by 李超（li-ch3@hnair.com）创建
 */
public interface IUserBaseInfoService {
	/**
	 * Description:登录
	 *
	 * @param userBaesInfo
	 * @return
	 * @Version1.0 2016年11月4日 上午11:59:49 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserBaseInfo> login(UserLoginRecord userLoginRecord);

	/**
	 * Description: 注销
	 *
	 * @param userBaesInfo
	 * @param userLoginRecord
	 * @return
	 * @Version1.0 2016年11月4日 下午3:07:24 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserBaseInfo> loginOut(UserLoginRecord userLoginRecord);

	/**
	 * Description: 注册
	 *
	 * @param userBaseInfo
	 * @return
	 * @Version1.0 2016年11月2日 下午7:27:43 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserBaseInfo> register(UserLoginRecord userLoginRecord);

	/**
	 * Description: 发送验证码
	 *
	 * @param phoneNum
	 * @return
	 * @Version1.0 2016年11月3日 上午10:30:29 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<MessageCode> sendMessage(String region,String phoneNum,String appType);

	/**
	 * Description: 检查验证码
	 *
	 * @param phoneNum
	 * @param code
	 * @return
	 * @Version1.0 2016年11月3日 下午2:58:52 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<MessageCode> checkMessageCode(String region,String phoneNum, String code);

	/**
	 * Description: 查询账号是否绑定
	 *
	 * @param openId
	 * @return
	 * @Version1.0 2016年11月5日 下午4:57:04 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserBaseInfo> queryBindPhone(UserLoginRecord userLoginRecord);

	/**
	 * Description: 绑定手机号
	 *
	 * @param userLoginRecord
	 * @return
	 * @Version1.0 2016年11月5日 下午5:42:04 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserBaseInfo> bindPhone(UserLoginRecord userLoginRecord);

	/**
	 * Description: 第三方绑定手机号V2
	 *
	 * @param userLoginRecord
	 * @return
	 * @Version1.0 2017年07月11日 下午15:42:04 by shiw.zhao@hnair.com创建
	 */
	public UserMessageVo<UserBaseInfo> bindPhoneV2(UserLoginRecord userLoginRecord);


	/**
	 * Description: 第三方注册登录
	 *
	 * @param userLoginRecord
	 * @return
	 * @Version1.0 2017年07月11日 上午09:42:04 by shiw.zhao@hnair.com创建
	 */
	public UserMessageVo<UserBaseInfo> thirdPartyRegister(UserLoginRecord userLoginRecord);

	/**
	 * Description: 关注
	 *
	 * @param attention
	 * @return
	 * @Version1.0 2016年11月17日 下午2:56:15 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<Attention> attention(Attention attention);

	/**
	 * Description: 取消关注
	 *
	 * @param attention
	 * @return
	 * @Version1.0 2016年11月17日 下午2:56:35 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<Attention> cancelAttention(Attention attention);

	/**
	 * Description: 关注信息
	 *
	 * @param userId
	 * @param targetUserId
	 * @return
	 * @Version1.0 2016年11月17日 下午2:56:56 by 李超（li-ch3@hnair.com）创建
	 */
	public Map<String, Object> queryAttentionInfo(Long userId, Long targetUserId);

	/**
	 * Description: 修改个人信息
	 *
	 * @param userBaseInfo
	 * @return
	 * @Version1.0 2016年11月17日 下午6:29:28 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserBaseInfo> modifyUserInfo(UserBaseInfo userBaseInfo);

	/**
	 * Description: 用户反馈
	 *
	 * @param userFeedback
	 * @return
	 * @Version1.0 2016年11月17日 下午8:02:25 by 李超（li-ch3@hnair.com）创建
	 */
	public UserMessageVo<UserFeedback> userFeedback(UserFeedback userFeedback);

	public void addUser(UserBaseInfo userBaseInfo,UserExtInfo userExtInfo);
	/**
	 * Description: 无验证码的绑定手机
	 * @Version1.0 2016年12月19日 下午1:14:37 by 李超（li-ch3@hnair.com）创建
	 * @param userLoginRecord
	 * @return
	 */
	public UserMessageVo<UserBaseInfo> bindPhoneNoIdentifyCode(UserLoginRecord userLoginRecord);

	/**
	 * Description: 发送订单状态
	 * @Version1.0 2017年2月24日 上午10:01:35 by 李超（li-ch3@hnair.com）创建
	 * @param region
	 * @param phoneNumber
	 * @param content
	 * @return
	 */
	public  UserMessageVo<MessageCode> sendOrderStatusMessage(String region,String phoneNumber, String content) ;

	/**
	 * 
	 * @author 许文轩
	 * @comment 发送验证码新接口（增加验证码类型）
	 * @date 2017年11月27日 下午4:04:05
	 */
	public UserMessageVo<MessageCode> sendMessageNew(String region, String phoneNum, String appType,
			MsgCodeTypeEnum msgCodeType);

	/**
	 * 
	 * @author 许文轩
	 * @comment 验证验证码新接口（增加验证码类型）
	 * @date 2017年11月27日 下午4:07:29
	 */
	public UserMessageVo<MessageCode> checkMessageCodeNew(String region, String phoneNum, String code,
			MsgCodeTypeEnum msgCodeType);

	/**
	 * 随机获取匿名用户信息
	 *
	 * @return 返回匿名用户信息
	 */
	public UserBaseInfo queryRandomUserBaseInfo();
}
