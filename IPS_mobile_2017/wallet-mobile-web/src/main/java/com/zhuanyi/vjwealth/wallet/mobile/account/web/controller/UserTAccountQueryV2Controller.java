package com.zhuanyi.vjwealth.wallet.mobile.account.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v3.6")
public class UserTAccountQueryV2Controller {

	@Autowired
	private IInvestmentOrderService investmentOrderService;
	@Autowired
	private IProductContractService productContractService;


	/**
	 * 存钱罐页面初始化
	 * @param userId
	 * @param userChannelType 渠道类型(微信:weixin;安卓:Android;苹果:IOS)
	 * @since 3.6
	 * @return
	 */
	@RequestMapping("/app/account/queryMAccountInfo.security")
	@AppController
	public Object queryMAccountInfo(String userId,String userChannelType) {
		if(StringUtils.isEmpty(userChannelType)){
			throw new AppException("渠道类型不能为空");
		}
		if(!"weixin".equals(userChannelType)&&!"Android".equals(userChannelType)&&!"IOS".equals(userChannelType)){
			throw new AppException("渠道类型参数有误");
		}
		return investmentOrderService.queryMAccountInfo(userId,userChannelType);
	}
	/**
	 * 2.风险提示和保障
	 * @param userId
	 * @since 3.6
	 * @return
	 */
	@RequestMapping("/app/account/queryWarningAndProtection.security")
	@AppController
	public Object queryWarningAndProtection(String userId) {
		return productContractService.queryWarningAndProtectionContracts(userId);
	}
}
