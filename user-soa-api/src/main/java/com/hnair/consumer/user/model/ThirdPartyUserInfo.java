package com.hnair.consumer.user.model;

import java.io.Serializable;
import java.util.Date;

public class ThirdPartyUserInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2455412348584L;

	private Long thirdPartyId;

    private Short thirdPartyType;

    private String openId;

    private String accessToken;

    private Long expiresIn;

    private Date createDate;

    private Date lastUpdateDate;

    private String sessionId;

    private String nickName;

    private String headimgurl;

    private String province;

    private String city;

    private String country;

    private String sex;

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public Short getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(Short thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

	@Override
	public String toString() {
		return "ThirdPartyUserInfo [thirdPartyId=" + thirdPartyId
				+ ", thirdPartyType=" + thirdPartyType + ", openId=" + openId
				+ ", accessToken=" + accessToken + ", expiresIn=" + expiresIn
				+ ", createDate=" + createDate + ", lastUpdateDate="
				+ lastUpdateDate + ", sessionId=" + sessionId + ", nickName="
				+ nickName + ", headimgurl=" + headimgurl + ", province="
				+ province + ", city=" + city + ", country=" + country
				+ ", sex=" + sex + "]";
	}
}