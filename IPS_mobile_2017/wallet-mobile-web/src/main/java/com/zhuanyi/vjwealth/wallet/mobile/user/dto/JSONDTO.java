package com.zhuanyi.vjwealth.wallet.mobile.user.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by yi on 16/1/29.
 */
public class JSONDTO {

    private   String isMore ;
    private List<Map<String,String>> records;

    public String getIsMore() {
        return isMore;
    }

    public void setIsMore(String isMore) {
        this.isMore = isMore;
    }

    public List<Map<String, String>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, String>> records) {
        this.records = records;
    }
}
