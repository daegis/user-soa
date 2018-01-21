package com.hnair.consumer.user.vo;

import java.io.Serializable;

/**
 * 
 * @author 许文轩
 * @comment 客服信息Vo
 * @date 2017年11月6日 上午9:13:11
 *
 */
public class CustomServiceVo implements Serializable {

	private static final long serialVersionUID = 5449063601202812952L;

	private Long customServiceId;

	private String thirdId;

	private String nickname;

	private String hometown;

	private String photo;

	private String headPic;

	private String hobby;

	private Integer serviceType;

	public void setCustomServiceId(Long value) {
		this.customServiceId = value;
	}

	public Long getCustomServiceId() {
		return this.customServiceId;
	}

	public void setThirdId(String value) {
		this.thirdId = value;
	}

	public String getThirdId() {
		return this.thirdId;
	}

	public void setNickname(String value) {
		this.nickname = value;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setHometown(String value) {
		this.hometown = value;
	}

	public String getHometown() {
		return this.hometown;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

}
