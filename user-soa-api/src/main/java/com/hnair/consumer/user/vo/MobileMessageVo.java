package com.hnair.consumer.user.vo;

import java.io.Serializable;

public class MobileMessageVo implements Serializable {	
	
	private static final long serialVersionUID = -3564209040170999766L;
	//短信的Id 标识  用于去重复
	private String uuid;
	
	//关键字   如 订单号 
	private String keyWord;
	//短信内容  默认 86 
	private String content = "86";
	
	//手机号 区号 + 86  中国
	private String region;
	//手机号
	private String phoneNo;
	
	private String createTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "MobileMessageVo [uuid=" + uuid + ", keyWord=" + keyWord + ", content=" + content + ", region=" + region
				+ ", phoneNo=" + phoneNo + ",createTime =" + createTime + "]";
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	

}
