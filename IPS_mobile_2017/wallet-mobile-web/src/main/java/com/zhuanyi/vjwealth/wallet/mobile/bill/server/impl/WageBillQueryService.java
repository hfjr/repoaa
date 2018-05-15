package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BatchApplyDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillDetailQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;

@Service("wageBillQueryService")
public class WageBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
	@Autowired
	private IBillListQueryMapper billListQueryMapper;
	
	@Autowired
	private IBillDetailQueryMapper billDetailQueryMapper;
	
	@Autowired
	private IOrderDateHelperService orderDateHelperService;
	
	@Override
	public Map<String, Object> getBillList(String userId, String page) {
		
		return super.getBillPageList(userId, page);
	}
	
	public List<BillListQueryDTO> getListRows(String userId, int pageIndex) {
		return billListQueryMapper.getWageBillListByUserIdAndType(userId,pageIndex);
	}
	
	public Object getBillDetail(String orderId, String billType) {
		
		return getBatchApplyBillDetail(orderId);
		
	}
	

    //工资
	private BatchApplyDetailDTO getBatchApplyBillDetail(String orderId) {
		//1.获取账单基本信息
		BatchApplyDetailDTO wdd = billDetailQueryMapper.getBatchApplyBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取提现的完成时间
		String confirmDate = orderDateHelperService.getEaConfirmDate(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
						wdd.getOrderStatus().equals("batch_apply_failconfirm")?"申购失败":"申购成功"                       
						,confirmDate, wdd.getOrderStatus().equals("batch_apply_confirm")?"yes":"no");
		wdd.setOperateStateInfos(processInfoList);
		//4.返回结果
		return wdd;
	}

}
