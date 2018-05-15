package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.dto;

import java.io.Serializable;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:借款申请入参实体
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public class PlanDetailInfoDTO implements Serializable {

    private String planDate;

    private String planAmount;

    private String planStatusDesc;

    private String isShowIcon;

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(String planAmount) {
        this.planAmount = planAmount;
    }

    public String getPlanStatusDesc() {
        return planStatusDesc;
    }

    public void setPlanStatusDesc(String planStatusDesc) {
        this.planStatusDesc = planStatusDesc;
    }

    public String getIsShowIcon() {
        return isShowIcon;
    }

    public void setIsShowIcon(String isShowIcon) {
        this.isShowIcon = isShowIcon;
    }
}
