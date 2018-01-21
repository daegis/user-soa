package com.hnair.consumer.user.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ScheduleRespVo implements Serializable{
	
	//列信息
	
	
	private String orderNo;	//订单编号
		
	private String orderSubNo;//子订单编号
	
	private String scheduleNo;//行程编号
	
	private String carrier;//当前航段航空公司代码
	
	private String carrierName;//当前航段航司中文名
	
	private String dptAirport;//当前航段出发机场
	
	private String dptAirportName;//当前航段出发机场中文名
	
	private String arrAirport;//当前航段抵达机场中
	
	private String arrAirportName;//当前航段抵达机场中文名
	
	private String code;//当前航段航班号
	
	private String arrTime;//当前航段到达时间
	
	private String dptTime;//当前航段起飞时间
	
	private String dptDate;//当前航段起飞日期
	
	private String arrDate;//当前航段到达日期
	
	private String arrCity;//当前航段到达城市
	
	private String arrCityName;//当前航段到达城市名称
	
	private String departCity;//当前航段起飞城市
	
	private String departCityName;//当前航段起飞城市名称
	
	private Short goBack;//主行程去程还是返程 0去程1返程
	
	private Integer flightTime;//飞行时长
	
	private String departCityMain;//主订出发城市
	
    private String destCity;//主行程到达城市
	
	private String destCountry;//主行程到达国家
		
	private Integer orderStatus;//订单状态 
	
	private String orderStatusStr;//订单状态中文
	 
	private String depTerminal;//起飞航站楼
	 
	private String arrTerminal;//到达航站楼
	
	private Integer checkinStatus;//是否已经值机 0未值机 1已经值机
	
	private String scheduleType;//行程类型1机票 2酒店 3度假 4当地游 5签证 6MTS
		
}

