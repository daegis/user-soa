package com.hnair.consumer.user.vo.recommendation.flight.international;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 国际机票
 * All Rights Reserved.
 * @version 1.0  2017年2月16日 下午2:18:34  by 李超（li-ch3@hnair.com）创建
 */
@Getter
@Setter
public class InternationalFlightSupplierPriceInfoVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//供应商名称
	private String supplierName;
	//供应商描述
	private String supplierDesc;
	//总票价
	private String totalFare;
	//是否有报销凭证(0:无,1:有)
	private String hasExpenseAccount;
	//报销凭证邮寄人
	private String sendPerson;
	//报销凭证邮寄电话
	private String sendPhone;
	//报销凭证邮寄地址
	private String sendAddress;
	//出票速度(分钟)
	private String ticketTimeLimit;
	//行李规则
	private String baggage;
	//退改详情
	private Object backAndChange;
	//航班信息
	private List<InternationalFlightSupplierSegmentInfoVo> supplierSegmentInfos;
	// 退改签key
	private String refundRuleKey;
	
}
