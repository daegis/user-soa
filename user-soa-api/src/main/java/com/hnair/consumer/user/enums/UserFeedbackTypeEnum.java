package com.hnair.consumer.user.enums;

/**
 * 用户反馈类型枚举类
 * @author zhangxianbin
 *
 */
public enum UserFeedbackTypeEnum {
	/**
	 * 意见反馈
	 */
	DEFAULT("1","意见反馈"),
	/**
	 * 版权举报
	 */
	COPYRIGHT_REPORT("2","版权举报"),
	/**
	 * 排版错误
	 */
	TYPOGRAPHICAL_ERROR("3","排版错误"),
	/**
	 * 评论举报
	 */
	COMMENT_TIPOFF("4","评论举报");
	private String no;
	private String name;

	private UserFeedbackTypeEnum(String no,String name){
		this.no = no;
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public String getName() {
		return name;
	}

	public static UserFeedbackTypeEnum getUserFeedbackType(String no){
		for(UserFeedbackTypeEnum userFeedbackType : UserFeedbackTypeEnum.values()){
			if(userFeedbackType.getNo().equals(no)){
				return userFeedbackType;
			}
		}
		return null;
	}
}