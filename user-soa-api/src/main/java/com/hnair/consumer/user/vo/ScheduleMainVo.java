package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 
 * @author jiangyu
 *
 * 2018年1月11日
 */
@Setter
@Getter
@ToString
public class ScheduleMainVo implements Serializable{
	
	//列信息
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderNo;
	
	private Short delFlag;
	
	private String scheduleType;	
	
	private Long userId;
	
	private String userName;
	
	private String productName;
	
	private Long productId;
	
	private Short airLineType;
	
	private Short tripType;
	
	private String arrCity;
	
	private String arrCityName;
	
	private String departCity;
	
	private String departCityName;
	
   private java.util.Date beginDateGo;//去程航班起飞时间
	
	private java.util.Date endDateGo;//去程航班到达目的地城市时间
	
	private java.util.Date beginDateBack;//回程航班起飞时间
	
	private java.util.Date endDateBack;//回程航班到达城市时间
	
	
	private String refOrderNo;
	
	private String scheduleSecondType;
	
	/**
	 * 航班详细信息
	 */
	private List<ScheduleDetailVo> ScheduleDetails = new ArrayList<ScheduleDetailVo>();
	
		
}

