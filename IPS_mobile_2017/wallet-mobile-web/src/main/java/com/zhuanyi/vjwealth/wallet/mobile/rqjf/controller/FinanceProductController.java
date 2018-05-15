package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IFinanceService;

@Controller
public class FinanceProductController {
	
	@Autowired
	private IProductQueryService productQueryService;
	
	@Autowired
	private IFinanceService financeService;
	
	@Autowired
	private IInvestmentOrderService investmentOrderService;
	
	
	/**
	 * @title 产品列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @param orderBy
	 * @return
     * @since v1.0
	 */
    @RequestMapping("/api/finance/product/queryProductList/v1.0")
    @AppController
    public Object queryProductList(String userId,String uuid,String productType, String page,String orderBy) {

        return productQueryService.queryProductListV6(userId,uuid,productType,page,orderBy);
    }
 
    /**
     * @title 红包列表
     * @param userId
     * @param uuid
     * @param page
     * @param orderBy
     * @return
     * @since v1.0
     */
    @RequestMapping("/api/finance/redPackage/queryPackageList/v1.0.security")
    @AppController
    public Object queryPackageList(String userId,String page,String packageType) {
    	ValidatorUtils.validatePage(page);
    	ValidatorUtils.validateNull(packageType, "packageType红包状态");
    	return financeService.queryPackageList(userId, page, packageType);
    }
    
    /**
     * @title 卡券列表
     * @param userId
     * @return
     * @since v1.0
     */
    @RequestMapping("/api/finance/coupon/queryCouponList/v1.0.security")
    @AppController
    public Object queryCouponsList(String userId,String page,String couponType) {
    	ValidatorUtils.validatePage(page);
    	ValidatorUtils.validateNull(couponType, "couponType加息券状态");
    	return financeService.queryCouponsList(userId, page, couponType);
    }
    
    
    @RequestMapping("/api/finance/product/placeOrder/v2.0.payment")
	@AppController
	public Object placeOrderForPaymentPasswordV2(String userId,String productId,String investmentAmount,String packageId,String  couponId,String clientType, String token,String recommendPhone) {
		if (!org.apache.commons.lang.math.NumberUtils.isNumber(investmentAmount)) {
			throw new AppException("投资金额不合法");
		}
		
		return investmentOrderService.placeOrder(userId, productId, investmentAmount, packageId,couponId, clientType, token, recommendPhone);
	}
    
    /**
     * 红包组合下单
     * @param userId
     * @param productId
     * @param investmentAmount
     * @param packageId
     * @param couponId
     * @param clientType
     * @param token
     * @param recommendPhone
     * @return
     */
  /* @RequestMapping("/api/finance/ips/placeOrder/packetFrozen")
   	@AppController
   	public Object placeOrderForPacketForzen(String userId,String productId,String investmentAmount,String packageId,String  couponId,String clientType, String token,String recommendPhone) {
       	//投资金额,优惠券id，客户类型，推荐手机号
   		if (!org.apache.commons.lang.math.NumberUtils.isNumber(investmentAmount)) {
   			throw new AppException("投资金额不合法");
   		}
   		
   		return investmentOrderService.packetByForzen(userId, productId, investmentAmount, packageId,couponId, clientType, token, recommendPhone);
   	}*/
    @RequestMapping("/api/finance/product/placeOrder/v2.0.security")
    @AppController
    public Object placeOrderForSecurityV2(String userId,String productId,String investmentAmount,String packageId,String  couponId,String clientType, String token,String recommendPhone) {
    	if (!org.apache.commons.lang.math.NumberUtils.isNumber(investmentAmount)) {
    		throw new AppException("投资金额不合法");
    	}
    	
    	return investmentOrderService.placeOrder(userId, productId, investmentAmount, packageId,couponId, clientType, token, recommendPhone);
    }
    
}
