package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/10/26.
 */
public class LoanProcessDetailInnerDTO {

    private String currentStepDesc;//资料审核,
    private String  isShow;//是否高亮展示
    private String isCurrentStep;//是否
    private String  isClick;//是否可以点击,
    private String  currentStepStatusDesc;//公积金信用资料审核中,
    private String  currentStepTip;//将于一个工作日内完成审核

    public String getCurrentStepDesc() {
        return currentStepDesc;
    }

    public LoanProcessDetailInnerDTO(String currentStepDesc, String isShow, String isCurrentStep,String isClick) {
        this.currentStepDesc = currentStepDesc;
        this.isShow = isShow;
        this.isCurrentStep = isCurrentStep;
        this.isClick = isClick;
    }

    public void setCurrentStepDesc(String currentStepDesc) {
        this.currentStepDesc = currentStepDesc;
    }

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public String getCurrentStepStatusDesc() {
        return currentStepStatusDesc;
    }

    public void setCurrentStepStatusDesc(String currentStepStatusDesc) {
        this.currentStepStatusDesc = currentStepStatusDesc;
    }

    public String getCurrentStepTip() {
        return currentStepTip;
    }

    public void setCurrentStepTip(String currentStepTip) {
        this.currentStepTip = currentStepTip;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getIsCurrentStep() {
        return isCurrentStep;
    }

    public void setIsCurrentStep(String isCurrentStep) {
        this.isCurrentStep = isCurrentStep;
    }
}
