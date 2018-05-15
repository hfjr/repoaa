package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan.ApplyLoanQueryDTO;

import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:信贷业务层
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public interface ICanaMyBorrowService {
    /**
     * 20.工资随享（右上角更多）我的借款明细-记录列表接口
     *
     * @param userId
     * @param borrowRecordStatus
     * @param page
     * @param productTypeCode
     * @return
     * @since 3.3.1
     */
    Object borrowRecordList(String userId, String borrowRecordStatus, String page, String productTypeCode);
    /**
     * 21.我的借款明细-记录列表-借款详情接口
     *
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    Object borrowRecordDetail(String userId, String borrowCode,String productTypeCode);
    /**
     *  22.全部借款列表接口
     * @param userId
     * @param recordType
     * @param page
     * @param productTypeCode
     * @since 3.4
     * @return
     */
    Object allBorrowRecord(String userId, String recordType, String page, String productTypeCode);
    /**
     *  22.1.全部借款列表接口
     * @param userId
     * @param productTypeCode
     * @since 3.4
     * @return
     */
    Object allRepaymentRecord(String userId, String productTypeCode);
    /**
     *  23.全部借款列表-还款详情接口
     * @param userId
     * @param borrowCode
     * @param repaymentPeriodCode
     * @since 3.4
     * @return
     */
    Object allBorrowRecordDetail(String userId, String borrowCode, String repaymentPeriodCode);
    /**
     *  24.立即还款保存接口
     * @param userId
     * @param borrowCode
     * @param repaymentPeriodCode
     * @since 3.4
     * @return
     */
    Object immediateRepaymentSave(String userId, String borrowCode, String repaymentPeriodCode);


}
