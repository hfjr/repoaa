package com.zhuanyi.vjwealth.wallet.mobile.index.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.IIndexService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping("/api/v3.7")
public class IndexV37Controller {

    @Autowired
    private IIndexService indexService;

    /**
     * 1.首页初始化界面
     *
     * @return
     * @since 3.7
     */
    @RequestMapping("/api/v3.7/app/index/queryIndex")
    @AppController
    public Object queryIndex(String userChannelType) {
        return indexService.querySdIndex(userChannelType);
    }

    /**
     * 查询用户是否可以查询公积金、工资、社保
     *
     * @param userId
     * @return
     */
    @RequestMapping("/api/v3.7/app/index/queryHumanService.security")
    @AppController
    public Object queryHumanService(String userId) {
        return indexService.queryHumanService(userId);
    }

    /**
     * 查询我的资产
     *
     * @param userId
     * @return
     */
    @RequestMapping("/api/v3.7/app/personalCenter/queryMyAssets.security")
    @AppController
    public Object queryMyAssets(String userId) {
        return indexService.queryMyAssets(userId);
    }

    @Autowired
    private IProductQueryService productQueryService;

    /**
     * @param userId
     * @param uuid
     * @param page
     * @return
     * @title 产品列表
     */
    @RequestMapping("/api/v3.7/app/product/queryV2ProductList")
    @AppController
    public Object queryProductList(String userId, String uuid, String page, String userChannelType) {
        return indexService.queryProductListV37(userId, uuid, page, userChannelType);
    }

    @RequestMapping("/api/v3.7/weixin/weixinHomeInit")
    @AppController
    public Object weixinHomeInit() {
        return indexService.queryWeiXinHome();
    }

    @RequestMapping("/api/v3.7/app/common/advertisement/queryV2InvestmentAdvertisementList")
    @AppController
    public Object queryV2InvestmentAdvertisementList() {
        return indexService.queryV2AdvertisementBannerInfo();
    }

    @RequestMapping("/api/v3.7/app/aboutSBCVC")
    public Object aboutSBCVC() {
        return "/app/advertisement/about-sbcvc";
    }

    @RequestMapping("/api/v3.8/app/index/queryIndex")
    @AppController
    public Object queryV38Index(String userChannelType) {
        return indexService.queryV38Index(userChannelType);
    }
}
