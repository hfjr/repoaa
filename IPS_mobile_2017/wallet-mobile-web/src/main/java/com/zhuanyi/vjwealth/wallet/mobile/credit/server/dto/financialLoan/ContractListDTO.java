package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/12.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ContractListDTO implements Serializable {

    private String  contractCode; //合同编号
    private String contractName; //合同名称
    private String contractDetailURLTitle ; //合同详情URL标题
    private String  contractDetailURL ; //合同详情URL

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractDetailURLTitle() {
        return contractDetailURLTitle;
    }

    public void setContractDetailURLTitle(String contractDetailURLTitle) {
        this.contractDetailURLTitle = contractDetailURLTitle;
    }

    public String getContractDetailURL() {
        return contractDetailURL;
    }

    public void setContractDetailURL(String contractDetailURL) {
        this.contractDetailURL = contractDetailURL;
    }
}
