package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.DeviceInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IDeviceInfoMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IDeviceInfoService;

@Service
public class DeviceInfoService implements IDeviceInfoService {
	@Autowired
	private IDeviceInfoMapper deviceInfoMapper;


	@Override
	public Map<String,String> saveDeviceInfo(DeviceInfoDTO dto) {
		Map<String,String> returnMap = new HashMap<String,String>();
		if(dto!=null){
			String userId=dto.getUserId();
			if(StringUtils.isNotBlank(userId)){
				int count=deviceInfoMapper.queryDeviceInfoCountByUserId(userId);
				//1.如果存在,则更新
				if(count>0){
					deviceInfoMapper.updateDeviceInfoByUserId(dto);
				}else{
				  //2.如果不存在,则新增
					deviceInfoMapper.saveDeviceInfo(dto);
				}
			}else{
				//3.如果userId为空,则新增
				deviceInfoMapper.saveDeviceInfo(dto);
			}
		}
		
		returnMap.put("result", "success");
		return returnMap;
	}
	

}
