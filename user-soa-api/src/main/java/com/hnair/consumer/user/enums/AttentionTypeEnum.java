package com.hnair.consumer.user.enums;
/**
 * Description: 关注类型
 * All Rights Reserved.
 * @version 1.0  2016年11月21日 下午6:17:10  by 李超（li-ch3@hnair.com）创建
 */
public enum AttentionTypeEnum {
	
	ATTENTION(0),//关注
	TARGET_ATTENTION(1),//被关注
	MUTUAL_ATTENTION(2),//相互关注
	NO_RELATION(3);//没有关系
	private Integer type;
		
	public Integer getType() {
		return type;
	}
	private AttentionTypeEnum(Integer type){
		this.type = type;
	}
}
