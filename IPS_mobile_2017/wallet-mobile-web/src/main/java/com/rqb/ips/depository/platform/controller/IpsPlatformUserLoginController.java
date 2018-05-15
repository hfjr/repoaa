package com.rqb.ips.depository.platform.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fab.web.controller.annotation.AppController;
import com.rqb.ips.depository.platform.faced.IpsPlatformService;

@Controller
public class IpsPlatformUserLoginController {

	@Autowired
	private IpsPlatformService ipsPlatformService;

	@RequestMapping(value="/app/ips/userlogin",method=RequestMethod.GET)
	//@AppController
	
    public String ipsUserLogin(String userId,Model model) {

		Map<String, Object> ipsUserLogin = (Map<String, Object>) ipsPlatformService.ipsUserLogin(userId);
		String userName = (String) ipsUserLogin.get("userName");
		String merchantId = (String) ipsUserLogin.get("merchantId");
		String url = (String) ipsUserLogin.get("ipsLoginUrl");
		model.addAttribute("userName",userName);
		model.addAttribute("merchantId",merchantId);
		model.addAttribute("url",url);
		
		
		return "/app/model/ipsLogin";
	}
	
	

}
