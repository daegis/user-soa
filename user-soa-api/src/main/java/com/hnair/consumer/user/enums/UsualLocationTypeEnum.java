package com.hnair.consumer.user.enums;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author 许文轩
 * @comment 常用定位类型
 * @date 2018年1月2日 下午8:34:36
 *
 */
public enum UsualLocationTypeEnum {
	CITY(1, "城市定位"), 
	DESTINATION(2, "目的地定位");

	@Getter
	private final Integer type;
	@Getter
	private final String title;
	private final static Map<Integer, UsualLocationTypeEnum> TYPE_MAP = new LinkedHashMap<>();

	private UsualLocationTypeEnum(Integer type, String title) {
		this.type = type;
		this.title = title;
	}

	static {
		for (UsualLocationTypeEnum type : UsualLocationTypeEnum.values()) {
			TYPE_MAP.put(type.getType(), type);
		}
	}

	public static Map<Integer, UsualLocationTypeEnum> getAllType() {
		return TYPE_MAP;
	}

	public static UsualLocationTypeEnum of(Short type) {
		if (type == null) {
			return null;
		}
		return TYPE_MAP.get(String.valueOf(type));
	}
}
