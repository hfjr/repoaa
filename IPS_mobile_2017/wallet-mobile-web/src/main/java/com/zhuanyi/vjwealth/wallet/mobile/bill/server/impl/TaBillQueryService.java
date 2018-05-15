package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.*;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.OrderTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillDetailQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author  zhangyingxuan
 * @date 20160714
 *
 */
@Service("taBillQueryService")
public class TaBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{
	
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
		return billListQueryMapper.getTaBillListByUserIdAndType(userId,pageIndex);
	}
	
	public Object getBillDetail(String orderId, String billType) {
		
		//1.ta账户提现
		if(billType.equals(OrderTypeEnum.WITHDRAW_TA.getValue())){
			return getWithdrawTaBillDetail(orderId);
		}
		//2.工资
		if(billType.equals(OrderTypeEnum.BATCH_APPLY.getValue())){
			return getBatchApplyBillDetail(orderId);
		}
		//3.余额申购ta账户
		if(billType.equals(OrderTypeEnum.APPLY_MA_TO_TA.getValue())){
			return getMaToTaBillDetail(orderId);
		}
		//4.ta账户转余额
		if(billType.equals(OrderTypeEnum.TRANSFER_TA_TO_MA.getValue())){
			return getTaToMaBillDetail(orderId);
		}
		
		return null;
	}
	
	

	//ta账户转余额
	private TaToMaDetailDTO getTaToMaBillDetail(String orderId) {
		TaToMaDetailDTO edd = billDetailQueryMapper.getTaToMaBillDetail(orderId);
		if(edd == null){
			throw new AppException("账单信息不存在");
		}
		return edd;
	}

	//余额申购ta账户
	private BatchApplyDetailDTO getMaToTaBillDetail(String orderId) {
		//1.获取账单基本信息
		BatchApplyDetailDTO wdd = billDetailQueryMapper.getMaToTaBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取提现的完成时间
		String confirmDate = orderDateHelperService.getTaConfirmDate(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
						wdd.getOrderStatus().equals("apply_ma_to_ta_failconfirm")?"申购失败":"申购成功"
						,confirmDate, wdd.getOrderStatus().equals("apply_ma_to_ta_confirm")?"yes":"no");
		wdd.setOperateStateInfos(processInfoList);
		if(wdd.getOrderStatus().equals("apply_ma_to_ta_noconfirm")){
			String nextDay = orderDateHelperService.getNextDay(confirmDate);
			wdd.setTip("预计"+confirmDate+"确认份额,"+nextDay+"收益到账");
		}else{
			wdd.setTip("");
		}
		//4.返回结果
		return wdd;
	}

    //工资
	private BatchApplyDetailDTO getBatchApplyBillDetail(String orderId) {
		//1.获取账单基本信息
		BatchApplyDetailDTO wdd = billDetailQueryMapper.getBatchApplyBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取提现的完成时间
		String confirmDate = orderDateHelperService.getTaConfirmDate(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
						wdd.getOrderStatus().equals("batch_apply_failconfirm")?"申购失败":"申购成功"                       
						,confirmDate, wdd.getOrderStatus().equals("batch_apply_confirm")?"yes":"no");
		wdd.setOperateStateInfos(processInfoList);
		
		//4.返回结果
		return wdd;
	}

    //ta账户提现
	private WithdrawDetailDTO getWithdrawTaBillDetail(String orderId) {
		//1.获取账单基本信息
		WithdrawDetailDTO wdd = billDetailQueryMapper.getWithdrawTaBillDetail(orderId);
		if(wdd == null){
			throw new AppException("账单信息不存在");
		}
		//2.获取提现的完成时间
		String confirmDate = orderDateHelperService.getCurrentDay(wdd.getCreateDate());
		//3.获取操作流程信息
		List<Map<String,String>> processInfoList = getOperateProcessInfo(wdd.getCreateDate(),
				                       "提现成功",confirmDate, wdd.getOrderStatus().equals("withdraw_ta_success")?"yes":"no");
		wdd.setOperateStateInfos(processInfoList);
		//4.返回结果
		return wdd;
	}

}
