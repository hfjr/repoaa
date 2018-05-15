package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.vjwealth.event.api.dto.ExcuteServiceRequestDTO;
import com.vjwealth.event.api.service.IExcuteEventService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IProductFinaceMapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;

@Controller
public class TestController {
	@Autowired
	private IExcuteEventService excuteEventService;


	@Autowired
	private IProductFinaceMapper productFinaceMapper;
	
	@Autowired
	private IUserAccountQueryMapper userAccountQueryMapper;
	
	@RequestMapping("/api/test/user/regirstsuccess/v1.0")
	@AppController
	public  Object regirstSuccessEvent(String userId){
			ExcuteServiceRequestDTO excuteServiceRequestDTO = new ExcuteServiceRequestDTO();
			Map<String, String>	paramMap=new HashMap<String,String>();
			paramMap.put("userId", userId);
			paramMap.put("recommendUserId","123456");
			excuteServiceRequestDTO.setParamJsonObject(excuteServiceRequestDTO.parseObject(paramMap));
			excuteEventService.excuteAsyncEvent("EV_0005", excuteServiceRequestDTO);
		return "";
	}
	
	@RequestMapping("/api/test/user/testEvent/v1.0")
	@AppController
	public  Object testEvent(String userId){
		ExcuteServiceRequestDTO excuteServiceRequestDTO = new ExcuteServiceRequestDTO();
		Map<String, String>	paramMap=new HashMap<String,String>();
		//{userId:"US20170600061",tradeNo:"TR2017071500001",productName:"xxx宝1号",productId:"prd0001",tradePrice:"100",tradeType:"loan",recommendUserId:"US20170600061","totalPrice":"255","tradePrice":"255"}
		paramMap.put("userId", userId);
		paramMap.put("productName", "1号");
		paramMap.put("productId", "prd0001");
		paramMap.put("tradeType", "finances");
		paramMap.put("tradePrice", "1000");
		paramMap.put("totalPrice", "1000");
		paramMap.put("recommendUserId","US201707110000002661");
		excuteServiceRequestDTO.setParamJsonObject(excuteServiceRequestDTO.parseObject(paramMap));
		excuteEventService.excuteAsyncEvent("EV_TEST", excuteServiceRequestDTO);
		return "";
	}
	@RequestMapping("/api/test/user/testEvent/v2.0")
	@AppController
	public  Object testEvent2(String userId){
		ExcuteServiceRequestDTO excuteServiceRequestDTO = new ExcuteServiceRequestDTO();
		Map<String, String>	paramMap=new HashMap<String,String>();
		paramMap.put("userId", userId);
		paramMap.put("tradeNo","OR10321");
		paramMap.put("tradePrice", "100");
		paramMap.put("tradeType", "finances");
		paramMap.putAll(productFinaceMapper.queryProductInfoByOrderNo("OR201709270000028191"));
		paramMap.putAll(userAccountQueryMapper.queryEventInfo("OR201709270000028191"));
//		paramMap.put("recommendUserId",userInviteInfoMapper.queryRecommendUserIdByPhone(recommendPhone));
		excuteServiceRequestDTO.setParamJsonObject(excuteServiceRequestDTO.parseObject(paramMap));
//		excuteEventService.excuteAsyncEvent("EV_0004", excuteServiceRequestDTO);
		
		return "";
	}
	
	
}
