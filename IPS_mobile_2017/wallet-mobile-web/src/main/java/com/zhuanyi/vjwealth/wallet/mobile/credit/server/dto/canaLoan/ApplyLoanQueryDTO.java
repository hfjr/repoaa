package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan;

import java.io.Serializable;
/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:借款申请入参实体
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public class ApplyLoanQueryDTO  implements Serializable {

    private String userId;

    private String productTypeCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }
}
