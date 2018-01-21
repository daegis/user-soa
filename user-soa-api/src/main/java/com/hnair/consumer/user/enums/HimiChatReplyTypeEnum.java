package com.hnair.consumer.user.enums;

/**
 * 
 * @author 许文轩
 * @comment 消息类型
 * @date 2017年11月3日 下午3:01:01
 *
 */
public enum HimiChatReplyTypeEnum {

	// 国内机票
	AIR_TICKET("air_ticket"),
	// 国际机票
	INTER_AIR_TICKET("inter_air_ticket"),
	// 酒店查询
	HOTEL_QUERY("hotel_query"),
	// 度假查询
	TRIP_QUERY("trip_query"),

	//餐饮查询
	CATERING_QUERY("food_query"),
	//天气查询
	WEATHER_QUERY( "weather"),
	//地图导航
	MAP_NAVIGATION( "navigation"),
	//汇率换算
	FOREX_QUERY("currency_exchange"),
	//旅行闹钟
	ALARM_CLOCK("alarm_clock"),
	//签证查询
	VISA_QUERY("visa_query"),
	//时差换算
	TIME_CONVERSION("time_converter"),

	// 文字消息
	MESSAGE("text"),
	// 没有获取到意图
	OTHER("other"),
	// 转人工服务
	HUMAN("human"),
	// 错误
	ERROR("error"),
	//进客户端时删除信息
	BREAKTIPS("breaktips");

	private String type;

	public String getType() {
		return type;
	}

	private HimiChatReplyTypeEnum(String type) {
		this.type = type;
	}
}
