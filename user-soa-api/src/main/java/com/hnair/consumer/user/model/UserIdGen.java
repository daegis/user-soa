package com.hnair.consumer.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 用户主键生成实体
 * All Rights Reserved.
 * @version 1.0  2016年11月3日 上午9:22:08  by 李超（li-ch3@hnair.com）创建
 */
public class UserIdGen implements Serializable{
	private static final long serialVersionUID = 1L;
	//用户id
	private Long userId;
	//创建日期
	private Date createDate;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
