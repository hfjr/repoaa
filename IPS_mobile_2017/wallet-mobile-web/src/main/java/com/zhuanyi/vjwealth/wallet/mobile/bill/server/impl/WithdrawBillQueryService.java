package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.WithdrawDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillDetailQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;

@Service("withdrawBillQueryService")
public class WithdrawBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
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
		return billListQueryMapper.getWithdrawBillListByUserIdAndType(userId,pageIndex);
	}
	
	public Object getBillDetail(String orderId, String billType) {
		
		return getWithdrawMaBillDetail(orderId);
	}
	
	
    //余额提现
	private WithdrawDetailDTO getWithdrawMaBillDetail(String orderId) {
		//1.获取账单基本信息
		WithdrawDetailDTO wdd = billDetailQueryMapper.getWithdrawMaBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取提现的完成时间
		String confirmDate = orderDateHelperService.getNextDay(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
				                        getWithdrawMaDetailCompleteTitle(wdd.getOrderStatus()),confirmDate, getWithdrawMaDetailCompleteFlag(wdd.getOrderStatus()));
		wdd.setOperateStateInfos(processInfoList);
		//4.返回结果
		return wdd;
	}

	
	
	/*******************************************************************工具方法*******************************************************************************/
	
	//余额提现，根据账单状态，获取操作流程“完成”节点的中文描述
	private String getWithdrawMaDetailCompleteTitle(String orderStatus){
		if(orderStatus.equals("withdraw_ma_noconfirm"))
			return "提现成功";
		if(orderStatus.equals("withdraw_ma_confirm"))
			return "提现成功";
		if(orderStatus.equals("withdraw_ma_reject"))
			return "提现拒绝";
		if(orderStatus.equals("withdraw_ma_fail"))
			return "提现失败";
		if(orderStatus.equals("withdraw_ma_success"))
			return "提现成功";
		return "";
	}
	
	//余额提现，根据账单状态，获取操作流程“完成”节点是否高亮显示
	private String getWithdrawMaDetailCompleteFlag(String orderStatus){
		if(orderStatus.equals("withdraw_ma_noconfirm"))
			return "no";
		if(orderStatus.equals("withdraw_ma_confirm"))
			return "no";
		if(orderStatus.equals("withdraw_ma_reject"))
			return "yes";
		if(orderStatus.equals("withdraw_ma_fail"))
			return "yes";
		if(orderStatus.equals("withdraw_ma_success"))
			return "yes";
		return "";
	}
	
	
	
	
}
