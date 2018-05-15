package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IWeixinHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
@Controller
public class WeixinHomeController {
@Autowired
private IWeixinHomeService weixinHomeService;

    @RequestMapping("/api/v3.0.1/weixin/weixinHomeInit")
    @ResponseBody
    public Object weixinHomeInit() {
        return weixinHomeService.weixinHomeInit();
    }
    
    @RequestMapping("/api/v3.0.1/weixin/helpCenterInit")
    public String helpCenterInit(Model model) {
    	model.addAttribute("helpCenter", weixinHomeService.helpCenterInit());
    	model.addAttribute("serviceHotline", weixinHomeService.getServiceHotline());
    	return "app/help/helpCenterWithDb";
    }
}
