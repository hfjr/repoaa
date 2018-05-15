package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yi on 16/3/5.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class PayRollDetailDTO {

    private List<KeyValueDTO> payRollDetail = new ArrayList<KeyValueDTO>();

    public List<KeyValueDTO> getPayRollDetail() {
        return payRollDetail;
    }

    public void setPayRollDetail(List<KeyValueDTO> payRollDetail) {
        this.payRollDetail = payRollDetail;
    }
}
