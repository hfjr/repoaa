package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFinancialLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundAndSocialSecurityAccountTaskDTO;

import java.util.Map;

/**
 * Created by hexy on 16/6/7.
 */
@Controller
@RequestMapping("/api/v3.3")
public class FinancialLoanController {

    @Autowired
    IFinancialLoanService financialLoanService;

    @Autowired
    private ILoanApplicationDubboService loanApplicationDubboService;

    @Autowired
    private IMyBorrowDubboService myBorrowDubboService;

    /**
     * 0.时光钱包介绍 H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/timeWallet")
    public String timeWallet() {

        return "/app/credit/financialLoan/timeWallet";
    }

    /**
     * 1.V信贷产品类型列表初始化
     *
     * @param userId
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/productTypeList.security")
    @AppController
    public Object productTypeList(String userId, String page) {
        return financialLoanService.productTypeList(userId, page);
    }

    /**
     * 2.理财贷介绍（什么是小金鱼） H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/financialLoanIntroduction")
    public String financialLoanIntroduction() {

        return "/app/credit/financialLoan/financialLoanIntroduction";
    }


    /**
     * 2.1 白领专享介绍（什么是白领专享） H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/aboutSalaryXX")
    public String aboutSalaryXX() {

        return "/app/credit/aboutSalaryXX";
    }

    /**
     * 2.2 工资易贷介绍（什么是工资易贷） H5
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/aboutSalaryYD")
    public String aboutSalaryYD() {

        return "/app/credit/aboutSalaryYD";
    }


    /**
     * 3.理财贷初始化（任务未激活状态，任务激活状态，备用金使用状态）
     *
     * @param userId
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/financialLoanIniti.security")
    @AppController
    public Object financialLoanIniti(String userId, String page, String productTypeCode) {
        return financialLoanService.financialLoanIniti(userId, page, productTypeCode);
    }

    /**
     * 3.理财贷初始化（任务未激活状态，任务激活状态，备用金使用状态）
     *
     * @param userId
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/NewFinancialLoanInit.security")
    @AppController
    public Object financialLoanInitV331(String userId, String page, String productTypeCode) {
        return financialLoanService.newFinancialLoanInit(userId, page, productTypeCode);
    }


    /**
     * 4.1 产品详情（理财贷）接口［同V理财产品详情］
     *
     * @param userId
     * @param productId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/queryProductDetail.security")
    @AppController
    public Object queryProductDetail(String userId, String productId) {
        return financialLoanService.queryProductDetail(userId, productId);
    }


    /**
     * 4.2 查询产品投资记录
     *
     * @param userId
     * @param productId
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/queryProductInvestmentRecordList.security")
    @AppController
    public Object queryProductInvestmentRecordList(String userId, String productId, String page) {
        return financialLoanService.queryProductInvestmentRecordList(userId, productId, page);
    }


    /**
     * 4.3 立即投资初始化
     *
     * @param userId
     * @param productId
     * @param investmentAmount 投资金额（默认为0）
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/investIniti.security")
    @AppController
    public Object investIniti(String userId, String productId, String investmentAmount) {
        return financialLoanService.investIniti(userId, productId, investmentAmount);
    }


    /**
     * 4.4 立即投资保存
     *
     * @param userId
     * @param productId
     * @param investmentAmount
     * @param token
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/investSave.security")
    @AppController
    public Object investSave(String userId, String productId, String investmentAmount, String token) {

        return financialLoanService.investSave(userId, productId, investmentAmount, token);
    }


    /**
     * 4.6 借款信息确认初始化
     *
     * @param userId
     * @param productId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/loanInformationConfirmationIniti.security")
    @AppController
    public Object loanInformationConfirmationIniti(String userId, String productId, String investmentAmount) {

        return financialLoanService.loanInformationConfirmationIniti(userId, productId, investmentAmount);
    }


    /**
     * 4.7 借款信息确认保存
     *
     * @param userId
     * @param productId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/loanInformationConfirmationSave.security")
    @AppController
    public Object loanInformationConfirmationSave(String userId, String productId, String investmentAmount, String token) {

        return financialLoanService.loanInformationConfirmationSave(userId, productId, investmentAmount, token);
    }


    /**
     * 4.8 《合同及相关协议》 列表
     *
     * @param userId
     * @param productId
     * @param borrowCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/contractList.security")
    @AppController
    public Object contractList(String userId, String productId, String borrowCode) {

        return financialLoanService.contractList(userId, productId, borrowCode);

    }


    /**
     * 4.9 《合同及相关协议》（模版&详情）H5
     *
     * @param userId
     * @param contractCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/contractTemplate.security")
    public Object contractTemplate(String userId, String contractCode, Model model) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(contractCode)) {
            return "app/credit/financialLoan/404Error";
        }

        Map<String, String> returnMap = loanApplicationDubboService.investmentContractTemplate(userId, null, contractCode);
        model.addAttribute("content", returnMap.get("content"));

        return "/app/credit/financialLoan/contractDetail";
    }


    /**
     * 4.10 《合同及相关协议》（内容&详情）H5
     *
     * @param userId
     * @param contractCode
     * @param borrowCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/contractDetail.security")
    public Object contractDetail(String userId, String contractCode, String borrowCode, Model model) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(borrowCode) || StringUtils.isEmpty(contractCode)) {
            return "app/credit/financialLoan/404Error";
        }

        Map<String, String> returnMap = loanApplicationDubboService.investmentContractTemplate(userId, borrowCode, contractCode);
        model.addAttribute("content", returnMap.get("content"));

        return "/app/credit/financialLoan/contractDetail";
    }


    /**
     * 5.1 任务列表（更高额度）
     *
     * @param userId
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/taskList.security")
    @AppController
    public Object taskList(String userId, String page) {

        return financialLoanService.taskList(userId, page);

    }

    /**
     * 5.1 任务列表（更高额度）
     *
     * @param userId
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/NewTaskList.security")
    @AppController
    public Object NewTaskList(String userId, String page) {
        return financialLoanService.newTaskList(userId, page);
    }


    /**
     * 5.2 (个人社保公积金信息)任务详情
     *
     * @param userId
     * @param taskCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/taskDetail.security")
    @AppController
    public Object taskDetail(String userId, String taskCode, String productTypeCode, String cityCode, String cityName) {

        // TODO 参数验证
        return financialLoanService.taskDetail(userId, taskCode, productTypeCode, cityCode, cityName);
    }


    /**
     * 5.3 (个人社保公积金信息)任务保存
     *
     * @param fundAndSocialSecurityAccountTaskDTO
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/fundAndSocialSecurityAccountTaskSave.security")
    @AppController
    public Object fundAndSocialSecurityAccountTaskSave(FundAndSocialSecurityAccountTaskDTO fundAndSocialSecurityAccountTaskDTO) {

        //参数验证
        return financialLoanService.fundAndSocialSecurityAccountTaskSave(fundAndSocialSecurityAccountTaskDTO);
    }


    /**
     * 5.4 获取城市列表接口
     *
     * @param productTypeCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/queryCityList.security")
    @AppController
    public Object queryCityList(String productTypeCode) {

        //参数验证
        return financialLoanService.queryCityList(productTypeCode);
    }


    /**
     * 6.1 我的理财贷明细列表
     *
     * @param userId
     * @param investmentStatus
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/borrowRecordList.security")
    @AppController
    public Object borrowRecordList(String userId, String investmentStatus, String page) {

        return financialLoanService.borrowRecordList(userId, investmentStatus, page);
    }


    /**
     * 6.2 我的理财贷明细列表刷新&分页
     *
     * @param userId
     * @param investmentStatus
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/borrowRecordListForRefresh.security")
    @AppController
    public Object borrowRecordListForRefresh(String userId, String investmentStatus, String page) {

        return financialLoanService.borrowRecordListForRefresh(userId, investmentStatus, page);
    }


    /**
     * 6.3 投资详情接口
     *
     * @param userId
     * @param orderId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/investmentDetail.security")
    @AppController
    public Object investmentDetail(String userId, String orderId) {

        return financialLoanService.investmentDetail(userId, orderId);
    }


    /**
     * 6.4 投资详情－投资动态
     *
     * @param userId
     * @param orderId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/investmentNewsFlow.security")
    @AppController
    public Object investmentNewsFlow(String userId, String orderId) {

        return financialLoanService.investmentNewsFlow(userId, orderId);
    }


    /**
     * 6.5 投资详情－回款计划
     *
     * @param userId
     * @param orderId
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/repaymentPlan.security")
    @AppController
    public Object repaymentPlan(String userId, String orderId) {

        return financialLoanService.repaymentPlan(userId, orderId);
    }


    /**
     * 6.6 借款列表-借款详情
     *
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/financialLoan/borrowRecordDetail.security")
    @AppController
    public Object borrowRecordDetail(String userId, String borrowCode) {

        return financialLoanService.borrowRecordDetail(userId, borrowCode);
    }

}
