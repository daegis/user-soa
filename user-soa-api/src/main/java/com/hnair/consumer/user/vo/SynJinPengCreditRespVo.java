package com.hnair.consumer.user.vo;

/**
 * 同步金鹏积分返回值类
 * @author TJJ
 *
 */
public class SynJinPengCreditRespVo extends BaseJinPengRespVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 金鹏积分余额
	 */
	private String accountBalance;

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

}
