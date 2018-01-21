package com.hnair.consumer.user.utils;

import java.util.UUID;

/**
 * Description: token生成
 * All Rights Reserved.
 * @version 1.0  2016年11月4日 上午11:46:52  by 李超（li-ch3@hnair.com）创建
 */
public class TokenGen {
	/**
	 * Description: 生成唯一token
	 * @Version1.0 2016年11月4日 上午11:57:55 by 李超（li-ch3@hnair.com）创建
	 * @param userId
	 * @return
	 */
	public static String genToken(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
}
