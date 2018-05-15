package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.DeviceInfoDTO;



public interface IDeviceInfoService {
	
	/**
	 * @title 查询所有的广告板子信息
	 * @return app端,将获得的url与本地缓存中的url进行比较,如果url有变化,则通过url从后台取新的 图片;如果没有变化,则从缓存中获取
	 * 
	 */
	Map<String,String> saveDeviceInfo(DeviceInfoDTO dto);
	
}
