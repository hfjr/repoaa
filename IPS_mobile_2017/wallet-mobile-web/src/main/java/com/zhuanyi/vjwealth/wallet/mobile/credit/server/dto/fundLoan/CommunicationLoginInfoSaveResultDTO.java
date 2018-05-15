package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/11/2.
 */
public class CommunicationLoginInfoSaveResultDTO{

    private String code;
    private String msg;

    private String isHaveNextStep;

    public CommunicationLoginInfoSaveResultDTO() {
    }

    public CommunicationLoginInfoSaveResultDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIsHaveNextStep() {
        return isHaveNextStep;
    }

    public void setIsHaveNextStep(String isHaveNextStep) {
        this.isHaveNextStep = isHaveNextStep;
    }
}
