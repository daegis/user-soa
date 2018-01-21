package com.hnair.consumer.user.enums;

/**
 * 
 * @author 许文轩
 * @comment 浏览类型
 * @date 2017年9月4日 下午7:41:04
 *
 */
public enum BrowseTypeEnum {

	DETAIL(1), // 详情页
	LIST(2), // 列表页
	SEARCH(3);// 搜索
	private Integer type;

	public Integer getType() {
		return type;
	}

	private BrowseTypeEnum(Integer type) {
		this.type = type;
	}
}
