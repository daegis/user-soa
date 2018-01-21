package com.hnair.consumer.user.enums;

public enum UserErrorCodeEnum {

	ERROR_CODE_12001(12001, "验证码发送时间不在间隔时间内"),
	ERROR_CODE_12002(12002, "保存验证码失败"),
	ERROR_CODE_12003(12003, "验证码不存在"),
	ERROR_CODE_12004(12004, "验证码已经失效"),
	ERROR_CODE_12005(12005, "验证码错误"),
	ERROR_CODE_12006(12006, "更新验证码失败"),
	ERROR_CODE_12007(12007, "保存用户信息失败"),
	ERROR_CODE_12008(12008, "该用户不存在"),
	ERROR_CODE_12009(12009, "该用户已经在该设备登录"),
	ERROR_CODE_12010(12010, "该用户已经在该设备注销"),
	ERROR_CODE_12011(12011, "该用户未绑定"),
	ERROR_CODE_12012(12012, "该用户已经绑定手机"),
	ERROR_CODE_12013(12013, "用户名已经存在"),
	ERROR_CODE_12014(12014, "已关注该用户"),
	ERROR_CODE_12015(12015, "已经取消关注"),
	ERROR_CODE_12016(12016, "修改信息失败"),
	ERROR_CODE_12017(12017, "用户反馈失败"),
	ERROR_CODE_12018(12018, "频繁请求"),
	ERROR_CODE_12019(12019, "验证码输入错误次数过多,请10分钟后重试"),
	ERROR_CODE_12020(12020, "用户名名字不能包含游鱼,游小鱼等字眼"),
	ERROR_CODE_12021(12021, "该手机号已绑定其他账户"),
	ERROR_CODE_12022(12022, "添加黑名单失败"),
	ERROR_CODE_12023(12023, "该用户已经在您的黑名单"),
	ERROR_CODE_12024(12024, "该用户已经从您的黑名单中删除"),
	ERROR_CODE_12025(12025, "删除黑名单失败"),
	ERROR_CODE_12026(12026, "查询黑名单列表失败"),
	ERROR_CODE_12027(12027, "您无权删除该黑名单用户"),
	ERROR_CODE_12028(12028, "无法关注该用户"),
	ERROR_CODE_12029(12029, "没有权限"),
	ERROR_CODE_12030(12030, "删除浏览历史失败"),
	ERROR_CODE_12031(12031, "参数不完整"),
	ERROR_CODE_12032(12032, "积分交易失败"),
	ERROR_CODE_12033(12033, "参数验证签名失败"),
	ERROR_CODE_12034(12034, "积分交易单号重复"),
	ERROR_CODE_12035(12035, "查询不到有效的积分消费原始单号"),
	ERROR_CODE_12036(12036, "退款积分数量超过限额"),
	ERROR_CODE_12037(12037, "积分账户创建失败"),
	ERROR_CODE_12038(12038, "积分任务不存在"),
	ERROR_CODE_12039(12039, "积分账户不存在"),
	ERROR_CODE_12040(12040, "积分累积失败"),
	ERROR_CODE_12041(12041, "积分余额不足"),
	ERROR_CODE_12042(12042, "积分规则已失效"),
	ERROR_CODE_12043(12043, "订单号不存在"),
	ERROR_CODE_12044(12044, "积分退还失败"),
	ERROR_CODE_12045(12045, "金鹏账户绑定失败"),
	ERROR_CODE_12046(12046, "积分已全部退还"),
	ERROR_CODE_12047(12047, "可退积分不足"),
	ERROR_CODE_12048(12048, "订单号已存在"),
	ERROR_CODE_12049(12049, "用户已签到"),
	ERROR_CODE_12050(12050, "更新行程信息失败"),
	
	ERROR_CODE_12999(12999, "未知错误");
	private Integer errorCode;
	private String errorMessage;

	private UserErrorCodeEnum(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public static UserErrorCodeEnum getByErrorCode(int errorCode){
		for(UserErrorCodeEnum errorCodeEnum : UserErrorCodeEnum.values()){
			if(errorCodeEnum.errorCode.intValue() == errorCode){
				return errorCodeEnum;
			}
		}
		return null;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
