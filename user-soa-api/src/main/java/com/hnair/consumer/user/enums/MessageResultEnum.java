package com.hnair.consumer.user.enums;
/**
 * Description: 短信平台返回值枚举
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 下午4:35:04  by 李超（li-ch3@hnair.com）创建
 */
public enum MessageResultEnum {
	
	RESULT_SUCCESS("0"),//成功
	RESULT_ERROR("-2");//异常
	private String type;
		
	public String getType() {
		return type;
	}
	private MessageResultEnum(String type){
		this.type = type;
	}
}
