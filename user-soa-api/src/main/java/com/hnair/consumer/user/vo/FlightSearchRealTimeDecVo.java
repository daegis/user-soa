package com.hnair.consumer.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FlightSearchRealTimeDecVo extends FlightSearchVo {

	//当前航班状态
	private String flightState;
	//航班状态码 取消=QX 起飞=QF 计划=JH 到达=DT 返航=FH 备降=BJ 延误 =YW
	private String statecode;
	//行李转盘
	private String baggage;
	//登机口
	private String gate;
	//值机柜台
	private String chkdesk;
	//机龄
	private String flightAge;
	//出发城市(汉字)
	private String departCityName;
	/**
	 * 出发地三字码
	 */
	private String flightDepcode ;
	//到达城市(汉字)
	private String arrCityName;
	/**
	 * 目的地三字码
	 */
	private String flightArrcode ;
	/**
	 * 航班计划起飞时间， yyyy-MM-dd HH:mm:ss
	 */
	private String flightDeptimePlan ;
	/**
	 * 航班计划到达时间，yyyy-MM-dd HH:mm:ss
	 */
	private String flightArrtimePlan ;
	/**
	 * 航班预计起飞时间，yyyy-MM-dd HH:mm:ss
	 */
	private String flightDeptimeReady ;
	/**
	 * 航班预计到达时间，yyyy-MM-dd HH:mm:ss
	 */
	private String flightArrtimeReady ;
	/**
	 * 航班实际起飞时间，yyyy-MM-dd HH:mm:ss
	 */
	private String flightDeptime ;
	/**
	 * 航班实际到达时间，yyyy-MM-dd HH:mm:ss
	 */
	private String flightArrtime ;
	/**
	 * 经停城市
	 */
	private String stopCode;
	/**
	 * 经停城市
	 */
	private String stopCity;
	/**
	 * 飞行里程
	 */
	private String distance;
	/**
	 * 天气-城市三字码
	 */
	private String cityCode;
	/**
	 * 天气-城市名称
	 */
	private String cityName;
	/**
	 * 天气-天气状况
	 */
	private String weatherType;
	/**
	 * 天气-最低气温
	 */
	private String temperatureLow;
	/**
	 * 天气-最高气温
	 */
	private String temperatureHigh;
	/**
	 * 天气-风向风力
	 */
	private String wind;
	/**
	 * 机场排队
	 */
	private String airportQueue;
	/**
	 * 机场出口
	 */
	private String exit;
}
