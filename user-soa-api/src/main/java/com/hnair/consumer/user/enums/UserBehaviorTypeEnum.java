package com.hnair.consumer.user.enums;

/**
 * 用户行为类型
 *
 *
 */
public enum UserBehaviorTypeEnum {

	POST("发帖"),
	DELETE_POST("删帖"),
	ATTENTION("关注"),
	CANCEL_ATTENTION("取消关注"),
	PRAISE("点赞"),
	CANCEL_PRAISE("取消点赞"),
	COLLECT("收藏"),
	CANCEL_COLLECT("取消收藏");

	private String desc;

	public String getType() {
		return desc;
	}
	private UserBehaviorTypeEnum( String desc){
		this.desc = desc;
	}
}
