package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v3.4")
public class ProductQueryV4Controller {
	
	@Autowired
	private IProductQueryService productQueryService;
	
	/**
	 * @title 产品列表
	 * @param page
	 * @return
     * @since v3.3
	 */
    @RequestMapping("/app/product/queryProductList")
    @AppController
    public Object queryProductList(String page) {

        return productQueryService.queryProductListV4(page);
    }


}
