package com.hnair.consumer.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * Description: 用户登录记录
 * All Rights Reserved.
 * @version 1.0  2016年11月4日 下午12:14:40  by 李超（li-ch3@hnair.com）创建
 */
@Setter
@Getter
public class UserLoginRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long loginId;
	
	private Long userId;
	
	private String userName;
	
	private String region;
	
	private String mobile;
	
	private String deviceId;
	
	private String deviceType;
	
	private java.util.Date createTime;
	
	private java.util.Date lastLoginTime;
	
	private String lastLoginIp;
	
	private String lastLoginToken;
	
	private String lastLoginMac;
	//验证码
	private String identifyCode;
	//绑定类型
	private Integer bindType;
	
	private String  openId;
	
	private String imgUrl;
	
	private String gender;
	
	private Integer userType;

	private String registChannel;
	
	/**
	 * 身份证号
	 */
	private String identityCard;



		
}

