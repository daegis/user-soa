package com.hnair.consumer.user.model;

import java.io.Serializable;

/**
 * CustomService Entity.
 */
public class CustomService implements Serializable {

	private static final long serialVersionUID = -838405322352596274L;
	// 列信息
	private Long customServiceId;

	private Integer serviceType;

	private String thirdId;

	private String name;

	private String enName;

	private String nickname;

	private String hometown;

	private String photos;

	private String headPic;

	private String hobby;

	private String constellation;

	private String bloodType;

	private String luckyColor;

	private String luckyNumber;

	private String motto;

	private String secret;

	private String cellphone;

	private String email;

	public Long getCustomServiceId() {
		return customServiceId;
	}

	public void setCustomServiceId(Long customServiceId) {
		this.customServiceId = customServiceId;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getLuckyColor() {
		return luckyColor;
	}

	public void setLuckyColor(String luckyColor) {
		this.luckyColor = luckyColor;
	}

	public String getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(String luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
