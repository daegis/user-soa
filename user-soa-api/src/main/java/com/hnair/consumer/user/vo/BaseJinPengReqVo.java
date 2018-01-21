package com.hnair.consumer.user.vo;

import java.io.Serializable;

/**
 * 金鹏积分请求基类
 * @author TJJ
 *
 */
public class BaseJinPengReqVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String version="1.0";
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 字符集
	 */
	private String charset="utf-8";
	
	/**
	 * 签名方式
	 */
	private String signType="HMAC-SHA256";
	
	/**
	 * 签名
	 */
	private String sign;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
