package com.zhuanyi.vjwealth.wallet.mobile.personalCenter.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.personalCenter.server.IPersonalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:个人中心模块API
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Controller
@RequestMapping("/api/v3.6")
public class PersonalCenterController {
	
	@Autowired
	private IPersonalCenterService personalCenterService;
    /**
     * 1.个人中心-我的
     * @param userId
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/personalCenter/queryPersonalCenter.security")
    @AppController
    public Object queryPersonalCenter(String userId) {
        return personalCenterService.queryPersonalCenter(userId);
    }

    /**
     * 查询我的资产
     * @param userId
     * @return
     */
    @RequestMapping("/app/personalCenter/queryMyAssets.security")
    @AppController
    public Object queryMyAssets(String userId) {
        return personalCenterService.queryPersonalCenter(userId);
    }
}
