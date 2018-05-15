package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.V1BillDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.OrderTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillDetailQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;

@Service("allBillQueryService")
public class AllBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
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
		return billListQueryMapper.getAllBillListByUserIdAndType(userId,pageIndex);
	}
	
	public Object getBillDetail(String orderId, String billType) {
		
		//1.ma转账到v+
		if(billType.equals(OrderTypeEnum.APPLY_MA_TO_V1.getValue())){
			return getMaToV1BillDetail(orderId);
		}
		//2.v+转到ma
		if(billType.equals(OrderTypeEnum.TRANSFER_V1_TO_MA.getValue())){
			return getV1ToMaBillDetail(orderId);
		}
		
		return null;
	}
	
	
	
	//v1转到ma
	private V1BillDetailDTO getV1ToMaBillDetail(String orderId) {
		//1.获取账单基本信息
		V1BillDetailDTO wdd = billDetailQueryMapper.getV1ToMaBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取转账确认
		String confirmDate = orderDateHelperService.getNextDay(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
						wdd.getOrderStatus().equals("transfer_v1_to_ma_confirm")?"转账成功":"转账成功"                       
						,confirmDate, wdd.getOrderStatus().equals("transfer_v1_to_ma_confirm")?"yes":"no");
		wdd.setOperateStateInfos(processInfoList);
		//4.返回结果
		return wdd;
	}

    //ma转到v1
	private V1BillDetailDTO getMaToV1BillDetail(String orderId) {
		//1.获取账单基本信息
		V1BillDetailDTO wdd = billDetailQueryMapper.getMaToV1BillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取转账确认
		String confirmDate = orderDateHelperService.getV1ConfirmDate(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
						wdd.getOrderStatus().equals("apply_ma_to_v1_failconfirm")?"申购失败":"申购成功"                       
						,confirmDate, wdd.getOrderStatus().equals("apply_ma_to_v1_confirm")?"yes":"no");
		wdd.setOperateStateInfos(processInfoList);
		if(wdd.getOrderStatus().equals("apply_ma_to_v1_noconfirm")){
			String nextDay = orderDateHelperService.getNextDay(confirmDate);
			wdd.setTip("预计"+confirmDate+"确认份额,"+nextDay+"收益到账");
		}else{
			wdd.setTip("");
		}
		//4.返回结果
		return wdd;
	}
	
}
