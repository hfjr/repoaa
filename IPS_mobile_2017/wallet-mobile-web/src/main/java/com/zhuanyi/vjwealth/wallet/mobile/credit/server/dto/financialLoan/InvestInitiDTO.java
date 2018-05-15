package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/12.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class InvestInitiDTO {

    private String  isCanBuy ; // 是否可购买
    private String  information ; // 提示信息
    private InvestProductInfoDTO product;
   // private String  paymentWay ; // 回款方式(按月付息:monthly_interest, 到期还本付息:repay_maturity,等额本息:principal_and_interest_equal,等额本金:principal_equal,等本等息:principal_equal_and_interest_equal)
    private String  availableBalance ; // 可用余额
    private String  availableBalancePlaceholderTip ; // input可用余额提示
    private String  inputBottomTipContents ; // input框底部提示内容
    private String  incomeAmount ; // 收益金额
    private String  fromInvestmentAmount ; // 起投金额
    private String  incrementAmount ; // 递增金额
    private String  token ; // token机制参数
    private String  investmentTip ; // 投资提示
    private String  buttonTextMessage ; // 投资提示
    private List<Map<String,String>>  detailInformation ; //  投资动态信息 (key[label,value])
    
    private String contractTitle;
    private String contractURL;
    private String contractLabel;

    private String isShowInvestmentIip;//是否提示“需要实名认证”的提示
    
    @JsonIgnore
    private String loanProductId;//贷款产品的产品编号

    private String canBuyAmount;//可以购买的金额

    public String getButtonTextMessage() {
        return buttonTextMessage;
    }

    public void setButtonTextMessage(String buttonTextMessage) {
        this.buttonTextMessage = buttonTextMessage;
    }

    public String getIsCanBuy() {
        return isCanBuy;
    }

    public void setIsCanBuy(String isCanBuy) {
        this.isCanBuy = isCanBuy;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public InvestProductInfoDTO getProduct() {
		return product;
	}

	public void setProduct(InvestProductInfoDTO product) {
		this.product = product;
	}

	public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAvailableBalancePlaceholderTip() {
        return availableBalancePlaceholderTip;
    }

    public void setAvailableBalancePlaceholderTip(String availableBalancePlaceholderTip) {
        this.availableBalancePlaceholderTip = availableBalancePlaceholderTip;
    }

    public String getInputBottomTipContents() {
        return inputBottomTipContents;
    }

    public void setInputBottomTipContents(String inputBottomTipContents) {
        this.inputBottomTipContents = inputBottomTipContents;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getFromInvestmentAmount() {
        return fromInvestmentAmount;
    }

    public void setFromInvestmentAmount(String fromInvestmentAmount) {
        this.fromInvestmentAmount = fromInvestmentAmount;
    }

    public String getIncrementAmount() {
        return incrementAmount;
    }

    public void setIncrementAmount(String incrementAmount) {
        this.incrementAmount = incrementAmount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Map<String, String>> getDetailInformation() {
        return detailInformation;
    }

    public void setDetailInformation(List<Map<String, String>> detailInformation) {
        this.detailInformation = detailInformation;
    }

	public String getInvestmentTip() {
		return investmentTip;
	}

	public void setInvestmentTip(String investmentTip) {
		this.investmentTip = investmentTip;
	}

	public String getLoanProductId() {
		return loanProductId;
	}

	public void setLoanProductId(String loanProductId) {
		this.loanProductId = loanProductId;
	}

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

	public String getContractURL() {
		return contractURL;
	}

	public void setContractURL(String contractURL) {
		this.contractURL = contractURL;
	}

	public String getContractLabel() {
		return contractLabel;
	}

	public void setContractLabel(String contractLabel) {
		this.contractLabel = contractLabel;
	}

    public String getIsShowInvestmentIip() {
        return isShowInvestmentIip;
    }

    public void setIsShowInvestmentIip(String isShowInvestmentIip) {
        this.isShowInvestmentIip = isShowInvestmentIip;
    }

    public String getCanBuyAmount() {
        return canBuyAmount;
    }

    public void setCanBuyAmount(String canBuyAmount) {
        this.canBuyAmount = canBuyAmount;
    }
}
