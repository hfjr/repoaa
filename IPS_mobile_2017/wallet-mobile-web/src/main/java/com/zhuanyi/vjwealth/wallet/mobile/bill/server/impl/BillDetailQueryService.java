package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillDetailQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.BillTemplateQueryFactory;

@Service
public class BillDetailQueryService implements IBillDetailQueryService {
	
	@Autowired
	private BillTemplateQueryFactory billQueryFactory;
	

	@Override
	public Object getBillDetailByOrderIdAndOrderType(String orderId, String billType) {
		//1.校验参数
		validatorParams(orderId,billType);
		//2.通过实例工厂获取实现查询账单详细的具体实例
		IBillTemplateService instance = billQueryFactory.getBillDetailServiceInstance(billType);
		//3.返回账单详细信息
		return instance.getBillDetail(orderId, billType);
	}
	
	
	


	private void validatorParams(String orderId, String billType) {
		if(StringUtils.isBlank(orderId)){
			throw new AppException("查询账单详情，账单编号不能为空");
		}
		if(StringUtils.isBlank(billType)){
			throw new AppException("查询账单详情，账单类型不能为空");
		}
	}
	
}
