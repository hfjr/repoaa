package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.util.List;
import java.util.Map;

/**
 * 工资先享-借款申请初始化
 * Created by wangzf on 16/5/20.
 */
public class ScanRepaymentPlanDTO {

    private List<RepaymentPlanTitleInfoDTO> borrowModes;
    private RepaymentPlanInfoByTypeDTO principalAndInterestEqualType;
    private RepaymentPlanInfoByTypeDTO monthlyInterestType;

    public List<RepaymentPlanTitleInfoDTO> getBorrowModes() {
        return borrowModes;
    }

    public void setBorrowModes(List<RepaymentPlanTitleInfoDTO> borrowModes) {
        this.borrowModes = borrowModes;
    }

    public RepaymentPlanInfoByTypeDTO getPrincipalAndInterestEqualType() {
        return principalAndInterestEqualType;
    }

    public void setPrincipalAndInterestEqualType(RepaymentPlanInfoByTypeDTO principalAndInterestEqualType) {
        this.principalAndInterestEqualType = principalAndInterestEqualType;
    }

    public RepaymentPlanInfoByTypeDTO getMonthlyInterestType() {
        return monthlyInterestType;
    }

    public void setMonthlyInterestType(RepaymentPlanInfoByTypeDTO monthlyInterestType) {
        this.monthlyInterestType = monthlyInterestType;
    }
}
