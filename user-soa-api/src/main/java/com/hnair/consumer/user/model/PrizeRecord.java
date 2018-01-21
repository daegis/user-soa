package com.hnair.consumer.user.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * PrizeRecord Entity.
 */
@Getter
@Setter
public class PrizeRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private Long signInRecordId;
	
	private Long activeId;
	
	private Long luckyDrawId;
	
	private Integer lotteryType;
	
	private Long lotteryRecord;
	
	private Long prizeId;
	
	private Long userId;
	
	private Integer productType;
	
	private String productName;
	
	private Long productId;
	
	private String prizePic;
	
	private Integer drawStatus; // 0.不可领取1.未领取2.已领取3.待发放4.已发放
	
	private String contactsName;
	
	private String contactsPhone;
	
	private java.util.Date lotteryTime;
	
	private java.util.Date createTime;
	
	private java.util.Date drawTime;

}

