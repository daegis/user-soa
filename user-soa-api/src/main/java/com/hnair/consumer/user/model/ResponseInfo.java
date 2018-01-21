package com.hnair.consumer.user.model;

import java.io.Serializable;
/**
 * Description: 海航短信共享平台实体
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 下午4:14:31  by 李超（li-ch3@hnair.com）创建
 */
public class ResponseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Result;
	private String ErrorInfo;
	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getErrorInfo() {
		return ErrorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}
	
}
