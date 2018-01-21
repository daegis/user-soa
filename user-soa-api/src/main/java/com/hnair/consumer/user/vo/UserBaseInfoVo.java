package com.hnair.consumer.user.vo;

import java.io.Serializable;

import com.hnair.consumer.user.model.UserIdentityConfirm;
/**
 * Description: 用户信息
 * All Rights Reserved.
 * @version 1.0  2016年11月5日 下午1:12:36  by 李超（li-ch3@hnair.com）创建
 */
public class UserBaseInfoVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String token;
	
	private String imgUrl;
	
	private Long userId;
	
	//是否是新用户(0:否,1:是)
	private Integer isNewUser;
	
	private String gender;
	
	private String birthday;
	
	private String place;
	//是否相互关注,(0:否,1:是;2没有关系)
	private Integer isMutualAttention;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 替换为*号的电话号码
	 */
	private String phoneNumber;
	
	private String region;
	
	/**
	 * 积分余额
	 */
	private Long creditsAvailable;
	
	private Short grade;
	
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
	
	private UserIdentityConfirm userIdentityConfirm;
	
	/**
	 * 登录次数
	 */
	private Integer loginRecordCount=0;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsMutualAttention() {
		return isMutualAttention;
	}

	public void setIsMutualAttention(Integer isMutualAttention) {
		this.isMutualAttention = isMutualAttention;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCreditsAvailable() {
		return creditsAvailable;
	}

	public void setCreditsAvailable(Long creditsAvailable) {
		this.creditsAvailable = creditsAvailable;
	}

	public UserIdentityConfirm getUserIdentityConfirm() {
		return userIdentityConfirm;
	}

	public void setUserIdentityConfirm(UserIdentityConfirm userIdentityConfirm) {
		this.userIdentityConfirm = userIdentityConfirm;
	}

	public Short getGrade() {
		return grade;
	}

	public void setGrade(Short grade) {
		this.grade = grade;
	}

	public String getMemberLevelCode() {
		return memberLevelCode;
	}

	public void setMemberLevelCode(String memberLevelCode) {
		this.memberLevelCode = memberLevelCode;
	}

	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getLoginRecordCount() {
		return loginRecordCount;
	}

	public void setLoginRecordCount(Integer loginRecordCount) {
		this.loginRecordCount = loginRecordCount;
	}

}
