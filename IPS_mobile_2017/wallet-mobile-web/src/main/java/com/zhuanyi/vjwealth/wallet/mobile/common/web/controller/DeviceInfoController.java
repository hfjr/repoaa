package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.DeviceInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IDeviceInfoService;

@Controller
public class DeviceInfoController {

	@Autowired
	private IDeviceInfoService deviceInfoService;
	
	
	@RequestMapping("/app/common/device/saveDeviceInfo")
	@AppController
	public Object saveDeviceInfo(DeviceInfoDTO dto) {
		return deviceInfoService.saveDeviceInfo(dto);
	}
}
