package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;

/**
 * Created by yi on 16/4/5.
 */
@Controller
public class PaymentPasswordUsageController {


	@Autowired
	private IInvestmentOrderService investmentOrderService;

	/**
	 * 下订单[购买产品]
	 * @param userId
	 * @param uuid
	 * @param productId
	 * @param investmentAmount
	 * @param token
	 * @param paymentPassword
	 * @return
	 * @since 3.1.2
	 */
	@RequestMapping("/api/v3.1/app/order/placeOrder.payment")
	@AppController
	public Object placeOrderForPaymentPassword(String userId,String productId,String investmentAmount,String token) {

		return investmentOrderService.placeOrder(userId, productId, investmentAmount, token);
	}

    /**
     * @author zhangyingxuan
     * @date 20160721
     * 增加采用红包购买理财产品
     * @param userId
     * @param productId
     * @param investmentAmount
     * @param rpId
     * @param token
     * @return
     */
	@RequestMapping("/api/v3.2/app/order/placeOrder.payment")
	@AppController
	public Object placeOrderForPaymentPasswordV2(String userId,String productId,String investmentAmount,String rpId, String clientType, String token) {
        if (!org.apache.commons.lang.math.NumberUtils.isNumber(investmentAmount)) {
            throw new AppException("投资金额不合法");
        }

		return investmentOrderService.placeOrder(userId, productId, investmentAmount, rpId, clientType, token);
	}
	/**
	 * @author xwt
	 * @date 20170808
	 * 增加推荐人
	 * @param userId
	 * @param productId
	 * @param investmentAmount
	 * @param rpId
	 * @param token
	 * @param phone
	 * @return
	 */
//	@RequestMapping("/api/v3.3/app/order/placeOrder.payment")
//	@AppController
//	public Object placeOrderForPaymentPasswordV2(String userId,String productId,String investmentAmount,String rpId, String clientType, String token,String recommendPhone) {
//		if (!org.apache.commons.lang.math.NumberUtils.isNumber(investmentAmount)) {
//			throw new AppException("投资金额不合法");
//		}
//		
//		return investmentOrderService.placeOrder(userId, productId, investmentAmount, rpId, clientType, token, recommendPhone);
//	}

	}
