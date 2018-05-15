package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IGreenService;

@Controller
public class GreenController {
	@Autowired
	private IGreenService greenService;
	@Autowired
	private IProductQueryService productQueryService;
	
	@RequestMapping("/api/green/redPackage/init/v1.0")
	@AppController
	public Object redPackageInit(String userId) {
		
		return greenService.redPackageInit(userId);
	}
	
	@RequestMapping("/api/green/redPackage/doapply/v1.0.security")
	@AppController
	public Object redPackageDoApply(String userId) {
		
		return greenService.redPackageDoApply(userId);
	}
	
	@RequestMapping("/api/green/coupon/doapply/v1.0.security")
	@AppController
	public Object couponDoApply(String userId) {
		
		return greenService.couponDoApply(userId);
	}
	
	@RequestMapping("/api/green/lottery/init/v1.0.security")
	@AppController
	public Object lotteryInit(String userId) {
		
		return greenService.lotteryInit(userId);
	}
	
	@RequestMapping("/api/green/lottery/doapply/v1.0.security")
	@AppController
	public Object lotteryDoApply(String userId) {
		
		return greenService.lotteryDoApply(userId);
	}
	
	
    @RequestMapping("/api/finance/product/queryProductDetail/v1.0.security")
    @AppController
    public Object queryProductDetailV7(String userId,String uuid,String productId) {
    	
    	return productQueryService.queryProductDetailV7(userId,uuid,productId);
    }

}
