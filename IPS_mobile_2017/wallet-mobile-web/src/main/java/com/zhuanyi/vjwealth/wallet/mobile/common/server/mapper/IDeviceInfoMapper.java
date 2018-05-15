package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;


import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.DeviceInfoDTO;

@Mapper
public interface IDeviceInfoMapper {

	Integer queryDeviceInfoCountByUserId(@Param("userId")String userId);
	
	void saveDeviceInfo(DeviceInfoDTO dto);
	
	void updateDeviceInfoByUserId(DeviceInfoDTO dto);
	
}
