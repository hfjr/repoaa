package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;


import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MyBankCardInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserBankCardListInfoDTO;

import java.util.List;

/**
 * Created by wangzhangfei on 16/7/12.
 */
public class WageAdvanceEarlyRepaymentConfirmInitiDTO {

	private String repaymentTitle;// 
	private String repaymentTypeLabel;//
	private String repaymentType;// 
	private String repaymentTypeDesc;// 
	private String repaymentWayLabel;// 
	private List<UserBankCardListInfoDTO> repaymentWays;//
	private UserBankCardListInfoDTO repaymentWayDefault;

	private String needRepaymentMoneyLabel;//
	private String needRepaymentMoney;//
	private String bizType;//

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getRepaymentTitle() {
		return repaymentTitle;
	}

	public void setRepaymentTitle(String repaymentTitle) {
		this.repaymentTitle = repaymentTitle;
	}

	public String getRepaymentTypeLabel() {
		return repaymentTypeLabel;
	}

	public void setRepaymentTypeLabel(String repaymentTypeLabel) {
		this.repaymentTypeLabel = repaymentTypeLabel;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getRepaymentTypeDesc() {
		return repaymentTypeDesc;
	}

	public void setRepaymentTypeDesc(String repaymentTypeDesc) {
		this.repaymentTypeDesc = repaymentTypeDesc;
	}

	public String getRepaymentWayLabel() {
		return repaymentWayLabel;
	}

	public void setRepaymentWayLabel(String repaymentWayLabel) {
		this.repaymentWayLabel = repaymentWayLabel;
	}

	public List<UserBankCardListInfoDTO> getRepaymentWays() {
		return repaymentWays;
	}

	public void setRepaymentWays(List<UserBankCardListInfoDTO> repaymentWays) {
		this.repaymentWays = repaymentWays;
	}

	public UserBankCardListInfoDTO getRepaymentWayDefault() {
		return repaymentWayDefault;
	}

	public void setRepaymentWayDefault(UserBankCardListInfoDTO repaymentWayDefault) {
		this.repaymentWayDefault = repaymentWayDefault;
	}

	public String getNeedRepaymentMoneyLabel() {
		return needRepaymentMoneyLabel;
	}

	public void setNeedRepaymentMoneyLabel(String needRepaymentMoneyLabel) {
		this.needRepaymentMoneyLabel = needRepaymentMoneyLabel;
	}

	public String getNeedRepaymentMoney() {
		return needRepaymentMoney;
	}

	public void setNeedRepaymentMoney(String needRepaymentMoney) {
		this.needRepaymentMoney = needRepaymentMoney;
	}
}
