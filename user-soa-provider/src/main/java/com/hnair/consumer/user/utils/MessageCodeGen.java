package com.hnair.consumer.user.utils;
/**
 * Description: 生成验证码
 * All Rights Reserved.
 * @version 1.0  2016年11月3日 上午10:44:34  by 李超（li-ch3@hnair.com）创建
 */
public class MessageCodeGen {
	/**
	 * Description: 生成验证码
	 * @Version1.0 2016年11月3日 上午10:46:11 by 李超（li-ch3@hnair.com）创建
	 * @return
	 */
	public static String genCode(){
		int code=(int)(Math.random()*900000+100000);
		return String.valueOf(code);
	}

}
