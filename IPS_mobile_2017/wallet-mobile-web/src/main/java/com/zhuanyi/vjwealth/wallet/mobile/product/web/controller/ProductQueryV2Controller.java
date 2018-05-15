package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v3.1")
public class ProductQueryV2Controller {
	
	@Autowired
	private IProductQueryService productQueryService;
	
	/**
	 * @title 产品列表
	 * @param page
	 * @return
     * @since v3.1
	 */
    @RequestMapping("/app/product/queryProductList")
    @AppController
    public Object queryProductList(String page) {

//        return productQueryService.queryProductList(page);
        return productQueryService.queryProductListForOld(page);
    }
    
    /**
     * @title 产品详情
     * @param productId 产品编号
     * @return
     * @since v3.1
     */
    @RequestMapping("/app/product/queryProductDetail")
    @AppController
    public Object queryProductDetail(String productId) {

        return productQueryService.queryProductDetailV2(productId);
    }

    /**
     * @title 查询产品的投资记录
     * @param productId 产品编号
     * @return
     * @since v3.1
     */
    @RequestMapping("/app/product/queryProductInvestmentRecordList")
    @AppController
    public Object queryProductInvestmentRecordList(String productId) {

        return productQueryService.queryProductInvestmentRecordList(productId);
    }

    @Autowired
    private IInvestmentOrderService investmentOrderService;

    /**
     * 5.下订单初始化
     * @param productId
     * @param userId
     * @return
     * @since v3.1
     */
    @RequestMapping("/app/order/queryProductCanBuy.security")
    @AppController
    public Object queryProductCanBuy(String productId,String userId) {

        return investmentOrderService.queryProductCanBuyV2(productId,userId);
    }

}
