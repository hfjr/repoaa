package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/10/18.
 */
public class FundLoanStatusListDTO {

    private String name;
    private String status;
    private String desc;
    private String progress;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
