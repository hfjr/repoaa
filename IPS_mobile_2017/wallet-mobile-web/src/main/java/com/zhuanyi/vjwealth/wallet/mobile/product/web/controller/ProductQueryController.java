package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;

@Controller
@RequestMapping("/api/v3.0")
public class ProductQueryController {
	
	@Autowired
	private IProductQueryService productQueryService;
	
	/**
	 * @title 产品列表
	 * @param page
	 * @return
	 */
    @RequestMapping("/app/product/queryProductList.security")
    @AppController
    public Object queryProductList(String page) {

        return productQueryService.queryProductList(page);
    }
    
    /**
     * @title 产品详情
     * @param productId 产品编号
     * @return
     */
    @RequestMapping("/app/product/queryProductDetail.security")
    @AppController
    public Object queryProductDetail(String userId,String productId) {

        return productQueryService.queryProductDetail(userId,productId);
    }
    
    /**
     * @title 查询产品的投资记录
     * @param productId 产品编号
     * @return
     */
    @RequestMapping("/app/product/queryProductInvestmentRecordList.security")
    @AppController
    public Object queryProductInvestmentRecordList(String productId) {

        return productQueryService.queryProductInvestmentRecordList(productId);
    }
    
    
}
