package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.dto;

import java.io.Serializable;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:借款申请入参实体
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public class MySalaryPlanQueryDTO implements Serializable {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 几号存
     */
    private String depositDate;
    /**
     * 存多少
     */
    private String depositAmount;
    /**
     * 工资卡编号
     */
    private String cardId;
    /**
     * 计划编号
     */
    private String planCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }
}
