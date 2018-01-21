package com.hnair.consumer.user.vo;

public class UserBindingReqVo extends BaseJinPengReqVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 会员标识符
	 */
	private String primaryKey;
	/**
	 * 身份信息字符
	 */
	private String loginId;
	private String pwd;
	private String lastNameEn;
	private String firtNameEn;
	private String idCode;
	private String mobileNumber;
	private String lastNameZh;
	private String firstNameZh;
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLastNameEn() {
		return lastNameEn;
	}
	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}
	public String getFirtNameEn() {
		return firtNameEn;
	}
	public void setFirtNameEn(String firtNameEn) {
		this.firtNameEn = firtNameEn;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getLastNameZh() {
		return lastNameZh;
	}
	public void setLastNameZh(String lastNameZh) {
		this.lastNameZh = lastNameZh;
	}
	public String getFirstNameZh() {
		return firstNameZh;
	}
	public void setFirstNameZh(String firstNameZh) {
		this.firstNameZh = firstNameZh;
	}
}
