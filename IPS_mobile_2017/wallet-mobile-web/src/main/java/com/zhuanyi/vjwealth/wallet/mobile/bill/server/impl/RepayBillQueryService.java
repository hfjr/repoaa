package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFinancialLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.order.dto.WjOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.order.server.service.IWjOrderInfoService;

@Service("repayBillQueryService")
public class RepayBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
	@Autowired
	private IBillListQueryMapper billQueryMapper;
    @Autowired
    private IFinancialLoanService financialLoanService;
    @Autowired
    private IWjOrderInfoService wjOrderInfoService;
	
	@Override
	public Map<String, Object> getBillList(String userId, String page) {
		
		return super.getBillPageList(userId, page);
	}
	
	public List<BillListQueryDTO> getListRows(String userId, int pageIndex) {
		return billQueryMapper.getRepayBillListByUserIdAndType(userId, pageIndex);
	}
	
	public Object getBillDetail(String orderId, String orderType) {
		WjOrderInfoDTO order = wjOrderInfoService.getOrderInfoByOrderNo(orderId);
    	return financialLoanService.borrowRecordDetail(order.getUserId(), order.getBorrowCode());
	}
	
	
	
}
