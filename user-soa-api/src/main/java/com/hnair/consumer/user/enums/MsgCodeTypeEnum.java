package com.hnair.consumer.user.enums;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 
 * @author 许文轩
 * @comment 短信验证码类型
 * @date 2017年11月27日 下午3:38:38
 *
 */
public enum MsgCodeTypeEnum {
	LOGIN(0, "登录注册"), 
	CARD_ACTIVATE(1, "卡片激活");

	@Getter
	private final Integer type;
	@Getter
	private final String title;
	private final static Map<Integer, MsgCodeTypeEnum> TYPE_MAP = new LinkedHashMap<>();

	private MsgCodeTypeEnum(Integer type, String title) {
		this.type = type;
		this.title = title;
	}

	static {
		for (MsgCodeTypeEnum type : MsgCodeTypeEnum.values()) {
			TYPE_MAP.put(type.getType(), type);
		}
	}

	public static Map<Integer, MsgCodeTypeEnum> getAllType() {
		return TYPE_MAP;
	}

	public static MsgCodeTypeEnum of(Integer type) {
		if (type == null) {
			return null;
		}
		return TYPE_MAP.get(type);
	}
}
