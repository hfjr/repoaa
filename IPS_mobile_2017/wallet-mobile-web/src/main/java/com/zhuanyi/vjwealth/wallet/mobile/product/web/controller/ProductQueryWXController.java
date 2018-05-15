package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v3.0.1")
public class ProductQueryWXController {
	
	@Autowired
	private IProductQueryWXService productQueryService;
	
	/**
	 * @title 产品列表
	 * @param page
	 * @return
	 */
    @RequestMapping("/weixin/product/queryProductList")
    @AppController
    public Object queryProductList(String page) {

        return productQueryService.queryProductList(page);
    }
    
}
