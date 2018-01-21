package com.hnair.consumer.user.vo;

import java.io.Serializable;
/**
 * Description: 用户信息
 * All Rights Reserved.
 * @version 1.0  2016年11月5日 下午1:12:36  by 李超（li-ch3@hnair.com）创建
 */
public class ReplyUserBaseInfoVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	
}
