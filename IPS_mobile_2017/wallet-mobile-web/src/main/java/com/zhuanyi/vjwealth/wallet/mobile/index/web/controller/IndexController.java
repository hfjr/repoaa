package com.zhuanyi.vjwealth.wallet.mobile.index.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.IIndexService;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:首页模块API
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Controller
@RequestMapping("/api/v3.6")
public class IndexController {
	
	@Autowired
	private IIndexService indexService;

    /**
     * 1.首页初始化界面
     * @param userId
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/index/queryIndex")
    @AppController
    public Object queryIndex(String userId,String uuid) {
        return indexService.queryIndex(userId,uuid);
    }
    /**
     * 动态 H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/index/aboutCoop")
    public String aboutCoop() {
        return "/app/index/about-coop";
    }
    /**
     * 众安 H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/index/aboutZhongan")
    public String aboutZhongan() {
        return "/app/index/about-zhongan";
    }

    /**
     * 4.1最新活动
     * @param userId
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/index/queryActivityList")
    @AppController
    public Object queryActivityList(String userId,String uuid,String page) {
        if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
            throw new AppException("页码数值不合法，必须为大于0的整数");
        }
        return indexService.queryActivityList(userId,uuid,page);
    }
    /**
     * 4.2动态列表
     * @param userId
     * @since 3.6
     * @return
     */
    @RequestMapping("/app/index/queryDynamicList")
    @AppController
    public Object queryDynamicList(String userId,String uuid,String page) {
        if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
            throw new AppException("页码数值不合法，必须为大于0的整数");
        }
        return indexService.queryDynamicList(userId,uuid,page);
    }

    /**
     * Fesco H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/index/aboutFesco")
    public String aboutFesco() {
        return "/app/index/about-fesco";
    }
    /**
     * Picc H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/index/aboutPicc")
    public String aboutPicc() {
        return "/app/index/about-picc";
    }
    /**
     * zhongan H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/index/aboutZhonganInsurance")
    public String aboutZhonganInsurance() {
        return "/app/index/about-zhongan-insurance";
    }
}
