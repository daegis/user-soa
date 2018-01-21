package com.hnair.consumer.user.enums;

/**
 * Description: 设备类型枚举类
 * All Rights Reserved.
 * @version 1.0  2015年1月10日 下午1:40:41  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public enum DeviceTypeEnum {
	ANDROID("1","android"),
	IPHONE("2","iphone"),
	IPAD("3","ipad"),
	PC("4","PC"),
	H5("5","H5"),
	SUPPLIER("6","supplier");
	private String deviceTypeNo;
	private String deviceTypeName;

	private DeviceTypeEnum(String deviceTypeNo,String deviceTypeName){
		this.deviceTypeNo = deviceTypeNo;
		this.deviceTypeName = deviceTypeName;
	}

	public String getDeviceTypeNo() {
		return deviceTypeNo;
	}

	public void setDeviceTypeNo(String deviceTypeNo) {
		this.deviceTypeNo = deviceTypeNo;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public static DeviceTypeEnum getDeviceType(String deviceTypeNo){
		for(DeviceTypeEnum deviceType : DeviceTypeEnum.values()){
			if(deviceType.getDeviceTypeNo().equals(deviceTypeNo)){
				return deviceType;
			}
		}
		return null;
	}
}