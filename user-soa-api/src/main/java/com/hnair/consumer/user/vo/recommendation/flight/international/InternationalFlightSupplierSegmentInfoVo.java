package com.hnair.consumer.user.vo.recommendation.flight.international;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Description: 国际机票
 * All Rights Reserved.
 * @version 1.0  2017年2月16日 下午2:18:34  by 李超（li-ch3@hnair.com）创建
 */
@Getter
@Setter
public class InternationalFlightSupplierSegmentInfoVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//舱位等级
	private String cabinClass;
	//舱位
	private String bookingCode;
	//航班代码
	private String airlineCode;
	//舱位等级描述
	private String cabinClassDesc;
	//搜索唯一标识
	private String searchUniqueKey; 
	//sessionID
	private String sessionId;
	//渠道ID
	private String vendorId;
	
}
