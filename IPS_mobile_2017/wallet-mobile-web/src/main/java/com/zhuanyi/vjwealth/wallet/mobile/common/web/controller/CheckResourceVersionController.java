package com.zhuanyi.vjwealth.wallet.mobile.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IResourceVersionService;

@Controller
public class CheckResourceVersionController {

    @Autowired
    private IResourceVersionService resourceVersionService;

    @AppController
    @RequestMapping("/app/common/{appType}/checkAppVersion")
    public Object checkAppVersion(String appVersion, @PathVariable String appType) {
        return resourceVersionService.checkAppVersion(appType, appVersion);
    }

    @AppController
    @RequestMapping("/app/common/{deviceType}/checkAppVersionForSHQB/{appType}")
    public Object checkAppVersionForSHQB(String appVersion, @PathVariable String deviceType, @PathVariable String appType) {
        return resourceVersionService.checkAppVersionForSHQB(deviceType, appVersion, appType);
    }
}
