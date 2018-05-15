package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v3.6")
public class ProductQueryV6Controller {
	
	@Autowired
	private IProductQueryService productQueryService;
	
	/**
	 * @title 产品列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @param orderBy
	 * @return
     * @since v3.6
	 */
//    @RequestMapping("/app/product/queryV2ProductList")
//    @AppController
//    public Object queryProductList(String userId,String uuid, String page,String orderBy) {
//
//        return productQueryService.queryProductListV6(userId,uuid,page,orderBy);
//    }

    /**
     * @title 产品详情
     * @param userId
     * @param uuid
     * @param productId 产品编号
     * @return
     * @since v3.6
     */
    @RequestMapping("app/product/queryV2ProductDetail.security")
    @AppController
    public Object queryProductDetailV6(String userId,String uuid,String productId) {

        return productQueryService.queryProductDetailV6(userId,uuid,productId);
    }
    
    @RequestMapping("app/product/queryV3ProductDetail.security")
    @AppController
    public Object queryProductDetailV7(String userId,String uuid,String productId) {
    	
    	return productQueryService.queryProductDetailV7(userId,uuid,productId);
    }
    
    @RequestMapping("product/bidAnnouncement")
	public String bidAnnouncement(String productId,Model model) {
		ValidatorUtils.validateNull(productId, "productId");
		model.addAttribute("bidAnnouncementContent", productQueryService.queryProductBidAnnouncementDetail(productId));
		return "/rqjf/productBidAnnouncement";
	}

}
