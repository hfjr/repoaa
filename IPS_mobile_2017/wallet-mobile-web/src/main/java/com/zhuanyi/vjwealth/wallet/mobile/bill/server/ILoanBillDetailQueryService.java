package com.zhuanyi.vjwealth.wallet.mobile.bill.server;

import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.LoanApplyAndRepayDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.VFinacialFrozenDetailDTO;

/**
 * @author wangzf
 */
public interface ILoanBillDetailQueryService {


    /**
     * 查询流通宝借款账单详情
     * @param userId 用户编号
     * @param orderId 订单编号
     * @param billType 账单类型
     * @return LoanApplyAndRepayDetailDTO
     * @since 3.5.2
     */
    LoanApplyAndRepayDetailDTO pledgeLoanApplyDetail(String userId, String orderId,String billType);

    /**
     * 查询流通宝还款账单
     * @param userId 用户编号
     * @param orderId   账单编号
     * @param billType 账单类型
     * @return LoanApplyAndRepayDetailDTO
     * @since 3.5.2
     */
    LoanApplyAndRepayDetailDTO pledgeLoanRepayDetail(String userId, String orderId, String billType);

    /**
     * 查询锦囊借款账单详情
     * @param userId 用户编号
     * @param orderId   账单编号
     * @param billType 账单类型
     * @return LoanApplyAndRepayDetailDTO
     * @since 3.5.2
     */
    LoanApplyAndRepayDetailDTO canaLoanApplyDetail(String userId, String orderId, String billType);

    /**
     * 查询锦囊还款账单详情
     * @param userId 用户编号
     * @param orderId   账单编号
     * @param billType 账单类型
     * @return LoanApplyAndRepayDetailDTO
     * @since 3.5.2
     */
    LoanApplyAndRepayDetailDTO canaLoanRepayDetail(String userId, String orderId, String billType);

    /**
     * 定期理财冻结账单详情
     * @param userId 用户编号
     * @param planId （冻结还款计划ID）
     * @param billType 账单类型
     * @return
     */
    VFinacialFrozenDetailDTO vFinacialFrozenDetail(String userId, String planId, String billType);

    /**
     * 工资先享借款详情
     * @param userId 用户编号
     * @param orderId （账单编号）
     * @param billType 账单类型
     * @return
     * @since 3.6
     */
    LoanApplyAndRepayDetailDTO wageAdvanceApplyDetail(String userId, String orderId, String billType);

    /**
     * 查询工资先享还款账单详情
     * @param userId 用户编号
     * @param orderId   账单编号
     * @param billType 账单类型
     * @return LoanApplyAndRepayDetailDTO
     * @since 3.6
     */
    LoanApplyAndRepayDetailDTO wageAdvanceRepayDetail(String userId, String orderId, String billType);
}
