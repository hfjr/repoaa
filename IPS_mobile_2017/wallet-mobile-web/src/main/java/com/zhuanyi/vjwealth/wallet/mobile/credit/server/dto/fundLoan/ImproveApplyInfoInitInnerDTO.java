package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/10/31.
 */
public class ImproveApplyInfoInitInnerDTO {

    private String label;
    private String isCompelete;//Y/N
    private String desc;//
    private String type;

    public ImproveApplyInfoInitInnerDTO() {
    }

    public ImproveApplyInfoInitInnerDTO(String label, String isCompelete, String desc, String type) {
        this.label = label;
        this.isCompelete = isCompelete;
        this.desc = desc;
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIsCompelete() {
        return isCompelete;
    }

    public void setIsCompelete(String isCompelete) {
        this.isCompelete = isCompelete;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
