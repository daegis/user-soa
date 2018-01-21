package com.hnair.consumer.user.enums;


/**
 * Description: 三方登陆类型
 * All Rights Reserved.
 * @version 1.0  2016年11月5日 下午5:20:39  by 李超（li-ch3@hnair.com）创建
 */
public enum ThirdTypeEnum {
	
	WECHAT("1","wechat"),
	QQ("2","QQ"),
	BLOG("3","blog");
	
	private String typeNo;
	private String typeName;

	private ThirdTypeEnum(String typeNo,String typeName){
		this.typeNo = typeNo;
		this.typeName = typeName;
	}

	public String getDeviceTypeNo() {
		return typeNo;
	}

	public void setDeviceTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getDeviceTypeName() {
		return typeName;
	}

	public void setDeviceTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static ThirdTypeEnum getDeviceType(String typeNo){
		for(ThirdTypeEnum Type : ThirdTypeEnum.values()){
			if(Type.getDeviceTypeNo().equals(typeNo)){
				return Type;
			}
		}
		return null;
	}
}
