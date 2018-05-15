package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.BillListQueryTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.OrderTypeEnum;

@Component
public class BillTemplateQueryFactory {
	
	@Resource(name="rechargeMaBillQueryService")
	private  IBillTemplateService recharge;
	
	@Resource(name="allBillQueryService")
	private  IBillTemplateService all;
	
	@Resource(name="eaBillQueryService")
	private  IBillTemplateService ea;
	
	@Resource(name="incomeBillQueryService")
	private  IBillTemplateService income;
	
	@Resource(name="regularMoneyBillQueryService")
	private  IBillTemplateService regularMoney;
	
	@Resource(name="wageBillQueryService")
	private  IBillTemplateService wage;
	
	@Resource(name="withdrawBillQueryService")
	private  IBillTemplateService  withdraw;
	
	@Resource(name="frozenAllBillQueryService")
	private  IBillTemplateService  frozenAll;
	
	@Resource(name="loanBillQueryService")
	private  IBillTemplateService  loan;
	
	@Resource(name="repayBillQueryService")
	private  IBillTemplateService  repay;

	@Resource(name="taBillQueryService")
	private  IBillTemplateService ta;

	@Resource(name="cashBackBillQueryService")
	private  IBillTemplateService rechargeCashback;

	@Resource(name="rpBillQueryService")
	private  IBillTemplateService rechargeRp;
	
	@Resource(name="bankcardWithholdBillQueryService")
	private  IBillTemplateService bankcardWithhold;
	/**
	 * 获取账单列表的实例
	 * @param bizType
	 * @return
	 */
	public  IBillTemplateService getBillListServiceInstance(String bizType){
		
		//1.全部账单
		if(bizType.equals(BillListQueryTypeEnum.ALL.getValue())){
			return all;
		}
		//2.e账户账单：
		if(bizType.equals(BillListQueryTypeEnum.EA.getValue())){
			//return ea;
			return ta;//e账户替换为ta账户
		}
		//3.收益账单
		if(bizType.equals(BillListQueryTypeEnum.INCOME.getValue())){
			return income;
		}
		//4.充值账单
		if(bizType.equals(BillListQueryTypeEnum.RECHARGE.getValue())){
			return recharge;
		}
		//5.定期理财账单
		if(bizType.equals(BillListQueryTypeEnum.REGULARMONEY.getValue())){
			return regularMoney;
		}
		//6.工资账单
		if(bizType.equals(BillListQueryTypeEnum.WAGE.getValue())){
			return wage;
		}
		//7.提现
		if(bizType.equals(BillListQueryTypeEnum.WITHDRAW.getValue())){
			return withdraw;
		}
		//8.冻结金额（全部）
		if(bizType.equals(BillListQueryTypeEnum.FROZENALL.getValue())){
			return frozenAll;
		}
		//9.借款
		if(bizType.equals(BillListQueryTypeEnum.LOAN.getValue())){
			return loan;
		}
		//10.还款
		if(bizType.equals(BillListQueryTypeEnum.REPAY.getValue())){
			return repay;
		}
		//11.ta账户账单：
		if(bizType.equals(BillListQueryTypeEnum.TA.getValue())){
			return ta;
		}
		//12.推荐返现：
		if(bizType.equals(BillListQueryTypeEnum.CASHBACK.getValue())){
			return rechargeCashback;
		}
		//13.红包：
		if(bizType.equals(BillListQueryTypeEnum.RP.getValue())){
			return rechargeRp;
		}
		//14.工资定存代扣：
		if(bizType.equals(BillListQueryTypeEnum.BANKCARDWITHHOLD.getValue())){
			return bankcardWithhold;
		}
		throw new AppException("查询账单列表，账单类型不正确");
	
	}
	
	
	
	/**
	 * 获取账单详情 的实例
	 * @param billType
	 * @return
	 */
	public  IBillTemplateService getBillDetailServiceInstance(String billType){
		
		//1.充值
		if(billType.equals(OrderTypeEnum.RECHARGE_MA.getValue())){
			return recharge;
		}
		//2.余额提现,e账户提现,余额申购e账户,e账户转余额
		if(billType.equals(OrderTypeEnum.WITHDRAW_EA.getValue()) ||
				billType.equals(OrderTypeEnum.APPLY_MA_TO_EA.getValue()) ||
				billType.equals(OrderTypeEnum.TRANSFER_EA_TO_MA.getValue()) ||
				billType.equals(OrderTypeEnum.BATCH_APPLY.getValue())){
			//return ea;
			return ta;//e账户替换为ta账户
		}
        // add by zhangyingxuan on 20160714 begin
        //8.余额提现,ta账户提现,余额申购ta账户,ta账户转余额
        if(billType.equals(OrderTypeEnum.WITHDRAW_TA.getValue()) ||
                billType.equals(OrderTypeEnum.APPLY_MA_TO_TA.getValue()) ||
                billType.equals(OrderTypeEnum.TRANSFER_TA_TO_MA.getValue()) ){
            return ta;
        }
        // add by zhangyingxuan on 20160714 end

		//3.余额提现,e账户提现,余额申购e账户,e账户转余额
		if(billType.equals(OrderTypeEnum.WITHDRAW_MA.getValue())){
			return withdraw;
		}
		//4.ma转账到v+，v+转到ma
		if(billType.equals(OrderTypeEnum.APPLY_MA_TO_V1.getValue()) ||
				billType.equals(OrderTypeEnum.TRANSFER_V1_TO_MA.getValue())){
			return all;
		}
		//5.ma转账到rf,流通宝，锦囊还款中，增加工资先享账单类型
		if(billType.equals(OrderTypeEnum.APPLY_MA_TO_RF.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_LTB_LOAN.getValue())
				||billType.equals(OrderTypeEnum.EARLY_REPAY_LTB_LOAN.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_CANA_LOAN.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN.getValue())
				||billType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN.getValue())
				||billType.equals(OrderTypeEnum.DUE_REPAY_WAGE_LOAN_WITHHOLD.getValue())
				||billType.equals(OrderTypeEnum.EARLY_REPAY_WAGE_LOAN_WITHHOLD.getValue())){
			return frozenAll;
		}
		//6.借款
		if(billType.equals(OrderTypeEnum.TRANSFER_LF_TO_LA.getValue())){
			return loan;
		}
		//7.还款
		if(billType.equals(OrderTypeEnum.APPLY_LOAN.getValue())||
				billType.equals(OrderTypeEnum.REPAY_LOAN.getValue())){
			return repay;
		}
		//8.推荐返现
		if(billType.equals(OrderTypeEnum.RECHARGE_CASHBACK.getValue())){
			return rechargeCashback;
		}
		//9.红包
		if(billType.equals(OrderTypeEnum.RECHARGE_RP.getValue())){
			return rechargeRp;
		}
		throw new AppException("查询账单明细，账单类型不正确");
	
	}
	
	
	
	
	

}
