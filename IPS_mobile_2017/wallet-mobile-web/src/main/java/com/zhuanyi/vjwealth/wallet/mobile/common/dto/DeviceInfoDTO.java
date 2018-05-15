package com.zhuanyi.vjwealth.wallet.mobile.common.dto;

import com.fab.core.entity.dto.BaseDTO;

public class DeviceInfoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	//用户id
	private String userId;
	
	//设备类型(小米,华为,苹果等)
	private String deviceType;
	
	//系统版本
	private String deviceSystemVersion;
	
	//运行商
	private String operator;
	
	//网络类型
	private String networkType;
	
	//经纬度
	private String longitudeLatitude;
	
	//手机类型(安卓,IOS)
	private String type;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceSystemVersion() {
		return deviceSystemVersion;
	}

	public void setDeviceSystemVersion(String deviceSystemVersion) {
		this.deviceSystemVersion = deviceSystemVersion;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getLongitudeLatitude() {
		return longitudeLatitude;
	}

	public void setLongitudeLatitude(String longitudeLatitude) {
		this.longitudeLatitude = longitudeLatitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
