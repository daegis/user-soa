package com.hnair.consumer.user.vo;

import java.io.Serializable;

public class RefundCreditAndMoneyRespVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7878645541490809426L;
	/**
	 * 应退积分
	 */
	private Long credit;
	/**
	 * 应退金额
	 */
	private Double money;
	/**
	 * 兑换比例乘数
	 */
	private Double multiplier;
	/**
	 * 兑换比例除数
	 */
	private Double divisor;
	
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
	public Long getCredit() {
		return credit;
	}
	public void setCredit(Long credit) {
		this.credit = credit;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
}
