package com.hnair.consumer.user.vo;

/**
 * 同步金鹏积分请求参数类
 * @author TJJ
 *
 */
public class SynJinPengCreditReqVo extends BaseJinPengReqVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * HiApp会员唯一标识
	 */
	private String primaryKey;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}
