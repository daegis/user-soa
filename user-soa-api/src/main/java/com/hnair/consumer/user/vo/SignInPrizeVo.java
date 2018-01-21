package com.hnair.consumer.user.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 签到活动奖品vo
 */
@Setter
@Getter
public class SignInPrizeVo implements Serializable {

	private static final long serialVersionUID = 8638206690233461440L;
	
	
	/**
	 * 奖品id
	 */
	private Long prizeId;
	
	
	private Long luckyDrawId;
	
	private String prizeName;
	/**
	 * 奖品类型
	 */
	private Integer productType;
	/**
	 * 优惠券批次id
	 */
	private Long couponSchemeId;
	/**
	 * 产品id
	 */
	private Long productId;
	/**
	 * 奖品主图
	 */
	private String prizePic;
	/**
	 * 奖品说明
	 */
	private String information;

}
