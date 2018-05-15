package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/10/16.
 */
public class FundAccountInitiTypeSelectDTO {
    private String key;
    private String value;
    private FundAccountInitiTypeSelectDetailDTO uiDesc;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FundAccountInitiTypeSelectDetailDTO getUiDesc() {
        return uiDesc;
    }

    public void setUiDesc(FundAccountInitiTypeSelectDetailDTO uiDesc) {
        this.uiDesc = uiDesc;
    }
}
