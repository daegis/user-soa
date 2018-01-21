package com.hnair.consumer.user.vo;

import java.io.Serializable;

/**
 * 金鹏积分返回值基类
 * @author TJJ
 *
 */
public class BaseJinPengRespVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 错误编码：0-成功   非0-具体错误代码
	 */
	private String errorCode;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
