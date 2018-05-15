package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillListQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.BillTemplateQueryFactory;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;

@Service
public class BillListQueryService implements IBillListQueryService {
	
	@Autowired
	private IBillListQueryMapper billQueryMapper;
	
	@Autowired
	private BillTemplateQueryFactory billQueryFactory;

	@Override
	public Map<String, Object> getBillListByUserIdAndType(String userId, String type, String page) {
		//1.校验参数
      	validatorParams(userId,type,page);
      	
      	IBillTemplateService instance = billQueryFactory.getBillListServiceInstance(type);
      	return instance.getBillList(userId, page);
      	
	}
	
	
	private void validatorParams(String userId, String type, String page) {
		if(StringUtils.isBlank(userId)){
			throw new AppException("查询账单列表，用户ID不能为空");
		}
		if(StringUtils.isBlank(type)){
			throw new AppException("查询账单列表，账单类型不能为空");
		}
		if(StringUtils.isBlank(page)){
			throw new AppException("查询账单列表，页码不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询账单列表，页码数值不合法，必须为大于0的整数");
		}
	}

}
