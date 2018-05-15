package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/9/8.
 */
public class MyAllLoanInitDTO {

    private List<Map<String,String>> borrowRecordSummary;
    private List<Map<String,String>> productSearch;
    private String isMore;
    private List<MyAllLoanListDTO> records;

    public List<Map<String, String>> getBorrowRecordSummary() {
        return borrowRecordSummary;
    }

    public void setBorrowRecordSummary(List<Map<String, String>> borrowRecordSummary) {
        this.borrowRecordSummary = borrowRecordSummary;
    }

    public List<Map<String, String>> getProductSearch() {
        return productSearch;
    }

    public void setProductSearch(List<Map<String, String>> productSearch) {
        this.productSearch = productSearch;
    }

    public String getIsMore() {
        return isMore;
    }

    public void setIsMore(String isMore) {
        this.isMore = isMore;
    }

    public List<MyAllLoanListDTO> getRecords() {
        return records;
    }

    public void setRecords(List<MyAllLoanListDTO> records) {
        this.records = records;
    }
}
