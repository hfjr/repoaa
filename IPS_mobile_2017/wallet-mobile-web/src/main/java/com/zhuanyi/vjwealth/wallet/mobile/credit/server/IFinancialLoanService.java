package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundAndSocialSecurityAccountTaskDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.CommonResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanCreditDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.FinancialLoanInvestmentNewFlows;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.InvestInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.LoanInformationConfirmationInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.TaskCommitResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.TaskDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductDetailQueryDTO;

/**
 * Created by hexy on 16/6/7.
 */
public interface IFinancialLoanService {

    /**
     * 1.V信贷产品类型列表初始化
     * @param userId
     * @param page (默认为1)
     * @since 3.3
     * @return
     */
    Map<String,Object> productTypeList(String userId, String page);

    /**
     * 3.理财贷初始化（任务未激活状态，任务激活状态，备用金使用状态）
     * @param userId
     * @param page
     * @param productTypeCode (标识是理财贷)
     * @since 3.3
     * @return
     */
    FinancialLoanInitiDTO financialLoanIniti(String userId, String page,String productTypeCode);

    /**
     *  4.1 产品详情（理财贷）接口［同V理财产品详情］
     * @param userId
     * @param productId
     * @since 3.3
     * @return
     */
    ProductDetailQueryDTO queryProductDetail(String userId, String productId) ;


    /**
     * 4.2 查询产品投资记录
     * @param userId
     * @param productId
     * @param page
     * @since 3.3
     * @return
     */
    Map<String,Object> queryProductInvestmentRecordList(String userId,String productId,String page) ;



    /**
     * 4.3 立即投资初始化
     * @param userId
     * @param productId
     * @param investmentAmount 投资金额（默认为0）
     * @since 3.3
     * @return
     */
    InvestInitiDTO investIniti(String userId, String productId, String investmentAmount) ;



    /**
     * 4.4 立即投资保存
     * @param userId
     * @param productId
     * @param investmentAmount
     * @param token
     * @since 3.3
     * @return
     */
    CommonResultDTO investSave(String userId,String productId,String investmentAmount,String token) ;



    /**
     * 4.5 立即投资-任务额度判断
     * @param userId
     * @since 3.3
     * @return
     */
    Boolean taskQuotaJudgment(String userId) ;


    /**
     * 4.6 借款信息确认初始化
     * @param userId
     * @param productId
     * @param investmentAmount
     * @since 3.3
     * @return
     */
    LoanInformationConfirmationInitiDTO loanInformationConfirmationIniti(String userId, String productId, String investmentAmount);


    /**
     * 4.7 借款信息确认保存
     * @param userId
     * @param productId
     * @param investmentAmount
     * @param token
     * @since 3.3
     * @return
     */
    Object loanInformationConfirmationSave(String userId,String productId, String investmentAmount, String token) ;


    /**
     * 4.8 《合同及相关协议》 列表
     * @param userId
     * @param productId
     * @param borrowCode (为空包含其他合同和借款合同,不为空只为借款合同)
     * @since 3.3
     * @return
     */
    Map<String,Object> contractList(String userId, String productId,String borrowCode);


    /**
     * 4.9 《合同及相关协议》（模版&详情）H5
     * @param userId
     * @param borrowCode (borrowCode为空,为模版,不为空为详情)
     * @param contractCode
     * @since 3.3
     * @return
     */
    Object contractDetail(String userId,String borrowCode,String contractCode);

    /**
     * 5.1 任务列表（更高额度）
     * @param userId
     * @param page
     * @since 3.3
     * @return
     */
    Map<String,Object> taskList(String userId,String page) ;

    /**
     * 5.1 任务列表（更高额度）
     * @param userId
     * @param page
     * @since 3.3
     * @return
     */
    Map<String,Object> newTaskList(String userId,String page) ;

    /**
     * 5.2 (个人社保公积金信息)任务详情
     * @param userId
     * @param taskCode
     * @since 3.3
     * @return
     */
    TaskDetailDTO taskDetail(String userId,String taskCode,String productTypeCode,String cityCode,String cityName) ;


    /**
     * 5.3 (个人社保公积金信息)任务保存
     * @param fundAndSocialSecurityAccountTaskDTO
     * @since 3.3
     * @return
     */
    TaskCommitResultDTO fundAndSocialSecurityAccountTaskSave(FundAndSocialSecurityAccountTaskDTO fundAndSocialSecurityAccountTaskDTO) ;


    /**
     * 6.1 我的理财贷明细列表
     * @param userId
     * @param investmentStatus
     * @param page
     * @since 3.3
     * @return
     */
    FinancialLoanInvestmentInitDTO borrowRecordList(String userId,String investmentStatus,String page) ;



    /**
     * 6.2 我的理财贷明细列表刷新&分页
     * @param userId
     * @param investmentStatus
     * @param page
     * @since 3.3
     * @return
     */
    Map<String,Object> borrowRecordListForRefresh(String userId,String investmentStatus,String page) ;


    /**
     * 6.3 投资详情接口
     * @param userId
     * @param orderId
     * @since 3.3
     * @return
     */
    FinancialLoanInvestmentDetailDTO investmentDetail(String userId,String orderId);


    /**
     * 6.4 投资详情－投资动态
     * @param userId
     * @param orderId
     * @since 3.3
     * @return
     */
    FinancialLoanInvestmentNewFlows investmentNewsFlow(String userId,String orderId);


    /**
     * 6.5 投资详情－回款计划
     * @param userId
     * @param orderId
     * @since 3.3
     * @return
     */
    Object repaymentPlan(String userId,String orderId);


    /**
     * 6.6 借款列表-借款详情
     * @param userId
     * @param borrowCode
     * @since 3.3
     * @return
     */
    FinancialLoanCreditDetailDTO borrowRecordDetail(String userId,String borrowCode) ;

    /**
     * 获取城市列表
     * @return
     */
    Object queryCityList(String loanProductId);

    FinancialLoanInitiDTO newFinancialLoanInit(String userId, String page,String productTypeCode);

}
