package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.util.List;

/**
 * 工资先享-借款申请初始化
 * Created by wangzf on 16/5/20.
 */
public class RepaymentPlanInfoByTypeDTO {

    private String repaymentAmount;  //还款总额
    private String repaymentInterestDesc;//还款总额描述
    private List<RepaymentPlanDTO> repaymentPlans;

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getRepaymentInterestDesc() {
        return repaymentInterestDesc;
    }

    public void setRepaymentInterestDesc(String repaymentInterestDesc) {
        this.repaymentInterestDesc = repaymentInterestDesc;
    }

    public List<RepaymentPlanDTO> getRepaymentPlans() {
        return repaymentPlans;
    }

    public void setRepaymentPlans(List<RepaymentPlanDTO> repaymentPlans) {
        this.repaymentPlans = repaymentPlans;
    }
}
