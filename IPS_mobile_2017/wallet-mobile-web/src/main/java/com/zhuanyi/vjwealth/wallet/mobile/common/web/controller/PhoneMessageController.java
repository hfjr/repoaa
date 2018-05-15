package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IPhoneMessageTransferService;

/**
 * 手机短信发送 Created by CE
 */
@Controller
public class PhoneMessageController {

	@Autowired
	private IPhoneMessageTransferService phoneMessageTransferService;
	
	private static final String IN_LOG_INFO = "wallet-mobile to send phone message start.";

	// 1.发送手机短信
	@RequestMapping("/api/V3.2/app/message/sendPhoneMessage")
	@AppController
	public Object sendPhoneMessage(String jsonData) {
		BaseLogger.audit(IN_LOG_INFO);
		return phoneMessageTransferService.sendPhoneMessage(jsonData);
	}
	
}
