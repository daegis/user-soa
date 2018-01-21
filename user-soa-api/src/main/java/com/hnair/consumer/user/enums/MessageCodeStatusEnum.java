package com.hnair.consumer.user.enums;
/**
 * Description: 短信平台返回值枚举
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 下午4:35:04  by 李超（li-ch3@hnair.com）创建
 */
public enum MessageCodeStatusEnum {
	
	CODE_VALIDATE(1),//验证
	CODE_NO_VALIDATE(0);//未验证
	private Integer type;
		
	public Integer getType() {
		return type;
	}
	private MessageCodeStatusEnum(Integer type){
		this.type = type;
	}
}
