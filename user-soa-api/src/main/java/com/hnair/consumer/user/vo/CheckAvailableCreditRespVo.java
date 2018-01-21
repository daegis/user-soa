package com.hnair.consumer.user.vo;

import java.io.Serializable;

public class CheckAvailableCreditRespVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 积分余额
	private Long available;
	//所需积分
	private long creditCount;
	//最多可支付积分
	private Long maxPayCredit;
	//抵付比例
	private Double deductionPercentage;
	//积分兑换人民币乘数
	private Double multiplier;
	//积分兑换人民币除数
	private Double divisor;
	//积分价值
	private Double creditPrice;
	//使用说明
	private String useInfo;
	
	public Double getCreditPrice() {
		return creditPrice;
	}

	public void setCreditPrice(Double creditPrice) {
		this.creditPrice = creditPrice;
	}

	public String getUseInfo() {
		return useInfo;
	}

	public void setUseInfo(String useInfo) {
		this.useInfo = useInfo;
	}

	public Double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(Double multiplier) {
		this.multiplier = multiplier;
	}

	public Double getDivisor() {
		return divisor;
	}

	public void setDivisor(Double divisor) {
		this.divisor = divisor;
	}

	public Double getDeductionPercentage() {
		return deductionPercentage;
	}

	public void setDeductionPercentage(Double deductionPercentage) {
		this.deductionPercentage = deductionPercentage;
	}

	public Long getMaxPayCredit() {
		return maxPayCredit;
	}

	public void setMaxPayCredit(Long maxPayCredit) {
		this.maxPayCredit = maxPayCredit;
	}

	public Long getAvailable() {
		return available;
	}

	public void setAvailable(Long available) {
		this.available = available;
	}

	public long getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(long creditCount) {
		this.creditCount = creditCount;
	}
	
}
