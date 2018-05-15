package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;

@Service("regularMoneyBillQueryService")
public class RegularMoneyBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
	@Autowired
	private IBillListQueryMapper billQueryMapper;
	
	@Override
	public Map<String, Object> getBillList(String userId, String page) {
		
		return super.getBillPageList(userId, page);
	}
	
	public List<BillListQueryDTO> getListRows(String userId, int pageIndex) {
		return billQueryMapper.getRegularMoneyBillListByUserIdAndType(userId, pageIndex);
	}
	
	public Object getBillDetail(String orderId, String orderType) {
		return null;
	}
	
	
	
	
}
