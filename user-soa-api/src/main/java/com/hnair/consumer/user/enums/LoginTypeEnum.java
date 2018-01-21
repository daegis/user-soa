package com.hnair.consumer.user.enums;
/**
 * Description: 注销类型
 * All Rights Reserved.
 * @version 1.0  2016年11月4日 下午2:12:10  by 李超（li-ch3@hnair.com）创建
 */
public enum LoginTypeEnum {
	
	LOGIN_IN(1),//登录
	LOGIN_OUT(0);//注销
	private Integer type;
		
	public Integer getType() {
		return type;
	}
	private LoginTypeEnum(Integer type){
		this.type = type;
	}
}
