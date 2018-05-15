package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/8.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductDetailDTO implements Serializable {

    private String productId;//产品ID
    private String productName;//产品名称
    private String isPreExpire;
    private String wjAdvanceDueDate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIsPreExpire() {
        return isPreExpire;
    }

    public void setIsPreExpire(String isPreExpire) {
        this.isPreExpire = isPreExpire;
    }

    public String getWjAdvanceDueDate() {
        return wjAdvanceDueDate;
    }

    public void setWjAdvanceDueDate(String wjAdvanceDueDate) {
        this.wjAdvanceDueDate = wjAdvanceDueDate;
    }
}
