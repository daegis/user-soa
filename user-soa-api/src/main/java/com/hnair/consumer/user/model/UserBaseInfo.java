package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * UserBaseInfo Entity.
 */
@lombok.Setter
@lombok.Getter
public class UserBaseInfo implements Serializable{
	

	private static final long serialVersionUID = 1L;

	public final static int USER_STATUS_DISABLED= 0;

	public final static int USER_STATUS_ENABLE= 1;
	//列信息
	private Long uid;
	
	private Long userId;
	
	private String userName;
	
	private String imgUrl;
	
	private String region;
	
	private String mobile;
	
	private String gender;
	
	private String birthday;
	
	private String place;
	
	private String token;
	
	private java.util.Date createTime;
	
	private java.util.Date lastModifyTime;

	//用户扩展信息
	private UserExtInfo  userExtInfo;

	//用户状态(1: 正常,0:禁用)
	private Integer userStatus;

	//是否是新用户(0:否,1:是)
	private Integer isNewUser;
	
	private Integer userType;

	private String openId;
	
	private Integer integral;

	private Integer bindType;
	
	private Short grade;
	
	private Integer experience;
	
	private java.util.Date upgradeTime;
	
	private Integer newFlag;
	
	private Integer updateVersion;
	
	/**
	 * 会员级别编号
	 */
	private String memberLevelCode;
	/**
	 * 是否是升级会员 1 是 0 否
	 */
	private Integer isMember;
	/**
	 * 会员级别名称
	 */
	private String memberLevel;

	/**
	 * 注册渠道
	 */
	private String registChannel;
	
	/**
	 * 证件号
	 */
	private String identityCard;
	
	/**
	 * 手机号模糊查询
	 */
	private String mobileLike;

	public Integer getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUid(Long value) {
		this.uid = value;
	}
	
	public Long getUid() {
		return this.uid;
	}
		
		
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
		
		
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
		
		
	public void setImgUrl(String value) {
		this.imgUrl = value;
	}
	
	public String getImgUrl() {
		return this.imgUrl;
	}
		
		
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
		
		
	public void setGender(String value) {
		this.gender = value;
	}
	
	public String getGender() {
		return this.gender;
	}
		
		
	public void setBirthday(String value) {
		this.birthday = value;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
		
		
	public void setPlace(String value) {
		this.place = value;
	}
	
	public String getPlace() {
		return this.place;
	}
		
		
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}


	public void setLastModifyTime(java.util.Date value) {
		this.lastModifyTime = value;
	}

	public java.util.Date getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	public Integer getBindType() {
		return this.bindType;
	}
	
}

