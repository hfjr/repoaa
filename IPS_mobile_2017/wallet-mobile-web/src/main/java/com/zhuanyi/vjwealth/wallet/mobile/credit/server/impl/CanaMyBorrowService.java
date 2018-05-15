package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.constant.BizConstant;
import com.zhuanyi.vjwealth.loan.order.cana.vo.*;
import com.zhuanyi.vjwealth.loan.order.vo.LoanInfoVo;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaLoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaMyBorrowDubboService;
import com.zhuanyi.vjwealth.loan.util.DateUtil;
import com.zhuanyi.vjwealth.loan.util.StringUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICanaApplyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICanaMyBorrowService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan.MyBorrowRecordDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan.RecordInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CanaRepaymentResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IMBUserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:信贷业务层
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
@Service
public class CanaMyBorrowService implements ICanaMyBorrowService {
    @Autowired
    private ICommConfigsQueryService commConfigsQueryService;
    @Autowired
    private ICanaMyBorrowDubboService canaMyBorrowDubboService;
    @Autowired
    private ICanaLoanApplicationDubboService canaLoanApplicationDubboService;
    @Remote
    private IMBUserOrderService mBUserOrderService;
    @Autowired
    private ICanaApplyLoanService canaApplyLoanService;
    /**
     * 20.工资随享（右上角更多）我的借款明细-记录列表接口
     *
     * @param userId
     * @param borrowRecordStatus
     * @param page
     * @param productTypeCode
     * @return
     * @since 3.4
     */
    @Override
    public Object borrowRecordList(String userId, String borrowRecordStatus, String page, String productTypeCode) {
        if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
            throw new AppException("查询还款明细，页码数值不合法，必须为大于0的整数");
        }
        BorrowRecordQuery query=new BorrowRecordQuery();
        query.setPage((Integer.parseInt(page)-1)*10);
        query.setProductId(productTypeCode);
        query.setUserId(userId);
        query.setStatus(BizConstant.STATUS_REPAYMENTIN.equals(borrowRecordStatus)?BizConstant.ORDER_STATUS_R:BizConstant.ORDER_STATUS_F);
        BorrowRecordListVo borrowRecords=canaMyBorrowDubboService.borrowRecordList(query);

        Map<String, Object> resultMap = new HashMap<String, Object>(){{
            put("borrowRecordTitle","我的借款");
        }};
        List<LoanInfoVo> borrowRecordSummary = new ArrayList<LoanInfoVo>();
        borrowRecordSummary.add(new LoanInfoVo("借款金额(元)",borrowRecords.getBorrowSumAmount()));
        borrowRecordSummary.add(new LoanInfoVo("待还本息(元)",borrowRecords.getPaidSumAmount()));
        resultMap.put("borrowRecordSummary",borrowRecordSummary);
        resultMap.put("isMore",borrowRecords.getIsMore());
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.ICON_GROUP_CANA);
        List<MyBorrowRecordDTO> records=new ArrayList<MyBorrowRecordDTO>();
        for(MyBorrowRecordVo vo:borrowRecords.getBorrowRecords()){
            MyBorrowRecordDTO myBorrowRecordDTO=new MyBorrowRecordDTO();
            myBorrowRecordDTO.setBorrowAmount(StringUtil.formatAmount(vo.getBorrowAmount())+"元");
            myBorrowRecordDTO.setBorrowCode(vo.getBorrowCode());
            myBorrowRecordDTO.setBorrowDate(DateUtil.getDateFormatStr(vo.getBorrowDate(),"yyyy-MM-dd"));
            myBorrowRecordDTO.setPlanRepaymentDate(DateUtil.getDateFormatStr(vo.getPlanRepaymentDate(),"yyyy-MM-dd"));
            myBorrowRecordDTO.setBorrowAmountDescription("借款金额");
            myBorrowRecordDTO.setBorrowDateLabel("借款时间");
            myBorrowRecordDTO.setPlanRepaymentDateLabel("计划还款日期");
            myBorrowRecordDTO.setRepaymentDateLabel("实际还款日期");
            myBorrowRecordDTO.setStatusMarkURL(vo.getIsOverdue()>0?commConfigMap.get(BizConstant.REPAYMENT_STATUS_OVERDUE_URL):"");
            if(BizConstant.STATUS_REPAYMENTIN.equals(borrowRecordStatus)){
                myBorrowRecordDTO.setRepaymentDate("");
                myBorrowRecordDTO.setPaidAmount(StringUtil.formatAmount(vo.getPaidAmount())+"元");
                myBorrowRecordDTO.setPaidAmountLabel("应还本息");
                myBorrowRecordDTO.setRepaymentStateDescription("");
                myBorrowRecordDTO.setRepaymentStatusMarkURL(commConfigMap.get(BizConstant.REPAYMENT_STATUS_PROGRESS_URL));
            }else{
                myBorrowRecordDTO.setRepaymentDate(DateUtil.getDateFormatStr(vo.getRepaymentDate(),"yyyy-MM-dd"));
                myBorrowRecordDTO.setPaidAmount(StringUtil.formatAmount(vo.getActualPaidAmount())+"元");
                myBorrowRecordDTO.setPaidAmountLabel("已还");
                myBorrowRecordDTO.setRepaymentStateDescription("已还款");
                myBorrowRecordDTO.setRepaymentStatusMarkURL(commConfigMap.get(BizConstant.REPAYMENT_STATUS_FINISH_URL));
            }
            records.add(myBorrowRecordDTO);
        }
        resultMap.put("records",records);
        return resultMap;
    }
    /**
     * 21.我的借款明细-记录列表-借款详情接口
     *
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    @Override
    public Object borrowRecordDetail(String userId, String borrowCode,String productTypeCode) {
        PaymentDetailsVo paymentDetailVo=canaMyBorrowDubboService.borrowRecordDetail(userId, borrowCode);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("borrowRecordDetailTitle","借款详情");
        resultMap.put("borrowAmountLabel","金额");
        resultMap.put("borrowAmount",StringUtil.formatAmount(paymentDetailVo.getBorrowAmount()));
        List<LoanInfoVo> dateInfo=new ArrayList<LoanInfoVo>();
        dateInfo.add(new LoanInfoVo("借款时间",DateUtil.getDateFormatStr(paymentDetailVo.getBorrowDate(),"yyyy年MM月dd日")));
        dateInfo.add(new LoanInfoVo("计划还款日期",DateUtil.getDateFormatStr(paymentDetailVo.getPlanPaymentDate(),"yyyy年MM月dd日")));
        if(paymentDetailVo.getPaymentDate()!=null) {
            dateInfo.add(new LoanInfoVo("实际还款日期", DateUtil.getDateFormatStr(paymentDetailVo.getPaymentDate(), "yyyy年MM月dd日")));
        }
        resultMap.put("dateInfo",dateInfo);

        List<LoanInfoVo> interestInfo=new ArrayList<LoanInfoVo>();
        interestInfo.add(new LoanInfoVo("利息",MessageFormat.format("￥{0}",StringUtil.formatAmount(paymentDetailVo.getInterestAmount()))));
        interestInfo.add(new LoanInfoVo("应还本息",MessageFormat.format("￥{0}",StringUtil.formatAmount(paymentDetailVo.getPaidAmount()))));
        resultMap.put("interestInfo",interestInfo);

        resultMap.put("checkPaymentDetailTitle","查看补缴详情");

        List<PaymentDetail> paymentDetails=new ArrayList<PaymentDetail>();
        for(PaymentDetailInfoVo detailInfo:paymentDetailVo.getPaymentDetails()){
            paymentDetails.add(buildPaymentDetails(detailInfo));
        }
        resultMap.put("isShowCheckPaymentDetail",paymentDetails.size()>0?"true":"false");
        resultMap.put("paymentDetailList",paymentDetails);

        Map<String, String> urlConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CANA_LOAN_SETTING);
        resultMap.put("contractLabel","查看合同");
        resultMap.put("contractTitle","借款合同");
        //Map<String,String> returnMap = canaApplyLoanService.investmentContractTemplate(userId,borrowCode,productTypeCode);
       // resultMap.put("contractURL",MessageFormat.format(urlConfigMap.get(BizCommonConstant.CANA_LOAN_CONTRACT_URL),returnMap.get("fileId")));
        resultMap.put("contractURL",urlConfigMap.get(BizCommonConstant.CANA_CONTRACT_URL));
        return resultMap;
    }
    private PaymentDetail buildPaymentDetails(PaymentDetailInfoVo detailInfo){
        PaymentDetail paymentDetail=new PaymentDetail();
        paymentDetail.setCalculationMethodDescription("＊逾期违约金=逾期剩余本金*0.083%*逾期天数");
        List<LoanInfoVo> paymentInfos=new ArrayList<LoanInfoVo>();
        paymentInfos.add(new LoanInfoVo("逾期时间",MessageFormat.format("{0}天",detailInfo.getOverdueDay())));
        paymentInfos.add(new LoanInfoVo("逾期罚息",MessageFormat.format("￥{0}",StringUtil.formatAmount(detailInfo.getOverduePenalty()))));
        paymentDetail.setPaymentInfos(paymentInfos);
        return paymentDetail;
    }
    /**
     *  22.全部借款列表接口
     * @param userId
     * @param recordType
     * @param page
     * @param productTypeCode
     * @since 3.4
     * @return
     */
    @Override
    public Object allBorrowRecord(String userId, String recordType, String page, String productTypeCode) {
        if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
            throw new AppException("页码数值不合法，必须为大于0的整数");
        }
        BorrowRecordQuery query=new BorrowRecordQuery();
        query.setPage((Integer.parseInt(page)-1)*10);
        query.setProductId(productTypeCode);
        query.setUserId(userId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if("audit".equals(recordType)){
            resultMap.put("allBorrowRecordTitle","审核中");
            query.setStatus(BizConstant.ORDER_STATUS_G);
        }else if("fail".equals(recordType)){
            resultMap.put("allBorrowRecordTitle","借款失败");
            query.setStatus(BizConstant.ORDER_STATUS_G_F);
        }else if("toRepayment".equals(recordType)){
            resultMap.put("allBorrowRecordTitle","全部借款");
            query.setStatus(BizConstant.ORDER_STATUS_R);
        }else if("repaymentRecord".equals(recordType)){
            resultMap.put("allBorrowRecordTitle","全部借款");
            query.setStatus(BizConstant.ORDER_STATUS_R);
        }else{
            throw new AppException("记录类型无效参数");
        }
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.ICON_GROUP_CANA);
        List<RecordInfoDTO> recordInfos=new ArrayList<RecordInfoDTO>();
        List<RecordInfoVo> recordInfoVos=canaMyBorrowDubboService.allBorrowRecord(query);
        for(RecordInfoVo vo:recordInfoVos){
            RecordInfoDTO recordInfoDTO=new RecordInfoDTO();
            recordInfoDTO.setRepaymentStatusMarkURL("");
            recordInfoDTO.setAuditResultDesc("");
            recordInfoDTO.setDebitType("");
            recordInfoDTO.setBorrowCode(vo.getOrderId());
            recordInfoDTO.setRepaymentPeriodCode(vo.getRepayId());
            if("audit".equals(recordType)||"fail".equals(recordType)) {
                recordInfoDTO.setRepaymentAmount(StringUtil.formatAmount(vo.getBorrowAmount()));
                recordInfoDTO.setRepaymentDate(DateUtil.getDateFormatStr(vo.getBorrowDate(), "yyyy-MM-dd HH:mm"));
            }else if("toRepayment".equals(recordType)){
                recordInfoDTO.setRepaymentStatusMarkURL(vo.getIsOverDue()>0?commConfigMap.get(BizConstant.REPAYMENT_STATUS_OVERDUE_URL):"");
                recordInfoDTO.setRepaymentAmount(StringUtil.formatAmount(vo.getPlanRepaymentAmount()));
                recordInfoDTO.setRepaymentDate(MessageFormat.format("{0}到期应还",DateUtil.getDateFormatStr(vo.getRepaymentDate(), "yyyy-MM-dd")));
                recordInfoDTO.setDebitType("账户余额自动扣除");
            }else if("repaymentRecord".equals(recordType)){
                recordInfoDTO.setRepaymentAmount(MessageFormat.format("应还￥{0}",StringUtil.formatAmount(vo.getRepaymentAmount())));
                recordInfoDTO.setRepaymentDate(DateUtil.getDateFormatStr(vo.getRepaymentDate(), "yyyy年MM月dd日"));
            }
            recordInfos.add(recordInfoDTO);
        }
        resultMap.put("isMore",recordInfos.size()>=10?"true":"false");
        resultMap.put("recordInfo",recordInfos);
        return resultMap;
    }
    /**
     *  22.1.全部借款列表接口
     * @param userId
     * @param productTypeCode
     * @since 3.4
     * @return
     */
    @Override
    public Object allRepaymentRecord(String userId, String productTypeCode) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("allBorrowRecordTitle","全部借款");
        BorrowRecordQuery query=new BorrowRecordQuery();
        query.setProductId(productTypeCode);
        query.setUserId(userId);
        query.setStatus(BizConstant.ORDER_STATUS_R);
        List<RecordInfoDTO> recordInfos=new ArrayList<RecordInfoDTO>();
        List<RecordInfoVo> recordInfoVos=canaMyBorrowDubboService.allRepaymentRecord(query);
        for(RecordInfoVo vo:recordInfoVos){
            RecordInfoDTO recordInfoDTO = new RecordInfoDTO();
            recordInfoDTO.setRepaymentStatusMarkURL("");
            recordInfoDTO.setAuditResultDesc("");
            recordInfoDTO.setDebitType("");
            recordInfoDTO.setBorrowCode(vo.getOrderId());
            recordInfoDTO.setRepaymentPeriodCode(vo.getRepayId());
            recordInfoDTO.setRepaymentAmount(MessageFormat.format("到期应还￥{0}", StringUtil.formatAmount(vo.getPlanRepaymentAmount())));
            recordInfoDTO.setRepaymentDate(DateUtil.getDateFormatStr(vo.getRepaymentDate(), "yyyy年MM月dd日"));
            recordInfos.add(recordInfoDTO);
        }
        resultMap.put("recordInfo",recordInfos);
        return resultMap;
    }

    /**
     *  23.全部借款列表-还款详情接口
     * @param userId
     * @param borrowCode
     * @param repaymentPeriodCode
     * @since 3.4
     * @return
     */
    @Override
    public Object allBorrowRecordDetail(String userId, String borrowCode, String repaymentPeriodCode) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("allBorrowRecordDetail", "还款详情");
        resultMap.put("paymentAmountLabel", "应还本息");
        PaymentDetailsVo paymentDetailVo=canaMyBorrowDubboService.borrowRecordDetail(userId, borrowCode);
        resultMap.put("paymentAmount", MessageFormat.format("￥{0}",StringUtil.formatAmount(paymentDetailVo.getPaidAmount())));

        InformationConfirmationVo informationVo=canaLoanApplicationDubboService.informationConfirmationIniti(userId, borrowCode);
        List<LoanInfoVo> personalInformation = new ArrayList<LoanInfoVo>();
        personalInformation.add(new LoanInfoVo("姓名",informationVo.getClientName()));
        personalInformation.add(new LoanInfoVo("身份证号码",informationVo.getIndentityNo()));
        resultMap.put("personalInformation", personalInformation);

        List<LoanInfoVo> loadDetailInformation = new ArrayList<LoanInfoVo>();
        loadDetailInformation.add(new LoanInfoVo("借款金额", MessageFormat.format("￥{0}",informationVo.getAmount())));
        loadDetailInformation.add(new LoanInfoVo("收款银行",informationVo.getCardBankName()));
        loadDetailInformation.add(new LoanInfoVo("借款账户", informationVo.getCardNo()));
        loadDetailInformation.add(new LoanInfoVo("起止时间",MessageFormat.format("{0}-{1}",informationVo.getLoanApplyTime(),
                DateUtil.getDateFormatStr(paymentDetailVo.getPlanPaymentDate(),"yyyy/MM/dd"))));
        loadDetailInformation.add(new LoanInfoVo("计划还款日",DateUtil.getDateFormatStr(paymentDetailVo.getPlanPaymentDate(),"yyyy/MM/dd")));
        if(paymentDetailVo.getPaymentDate()!=null) {
            loadDetailInformation.add(new LoanInfoVo("实际还款日期", DateUtil.getDateFormatStr(paymentDetailVo.getPaymentDate(), "yyyy/MM/dd")));
        }
        loadDetailInformation.add(new LoanInfoVo("借款期限",MessageFormat.format("{0}天",informationVo.getPeriods())));
        loadDetailInformation.add(new LoanInfoVo("借款方式", informationVo.getInterestType()));
        loadDetailInformation.add(new LoanInfoVo("应还利息",MessageFormat.format("￥{0}",informationVo.getInterest())));
        resultMap.put("loadDetailInformation", loadDetailInformation);

        resultMap.put("checkPaymentDetailTitle","查看补缴详情");
        List<PaymentDetail> paymentDetails=new ArrayList<PaymentDetail>();
        for(PaymentDetailInfoVo detailInfo:paymentDetailVo.getPaymentDetails()){
            paymentDetails.add(buildPaymentDetails(detailInfo));
        }
        resultMap.put("isShowCheckPaymentDetail",paymentDetails.size()>0?"true":"false");
        resultMap.put("paymentDetailList",paymentDetails);
        resultMap.put("buttonTextMessage", "立即还款");
        return resultMap;
    }
    /**
     *  24.立即还款保存接口
     * @param userId
     * @param borrowCode
     * @param repaymentPeriodCode
     * @since 3.4
     * @return
     */
    @Override
    public Object immediateRepaymentSave(String userId, String borrowCode, String repaymentPeriodCode) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        CanaRepaymentResultDTO canaRepaymentResultDTO=mBUserOrderService.earlyRepaymentConfirm(userId,borrowCode,repaymentPeriodCode);
        resultMap.put("code", canaRepaymentResultDTO.getCode());
        resultMap.put("message",canaRepaymentResultDTO.getMessage());
        if("203600".equals(canaRepaymentResultDTO.getCode())){
            resultMap.put("immediateRepaymentTitle", "信息提交");
            resultMap.put("feedbackInformation", "本次借款额度已实时恢复");
            Map<String, String> urlConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.ICON_GROUP_CANA);
            resultMap.put("iconURL", urlConfigMap.get("icon_immediateRepaymentSave"));
            resultMap.put("paymentInfos",new ArrayList<LoanInfoVo>());
            resultMap.put("buttonTextMessage", "确定");
        }else{
            resultMap.put("immediateRepaymentTitle", "还款失败");
            resultMap.put("feedbackInformation", "");
            resultMap.put("iconURL", "");
            resultMap.put("refillAmount",canaRepaymentResultDTO.getRefillAmount());
            resultMap.put("paymentInfoTitle", "余额不足");
            List<LoanInfoVo> paymentInfos=new ArrayList<LoanInfoVo>();
            paymentInfos.add(new LoanInfoVo("可用金额",canaRepaymentResultDTO.getBalanceAmount()+" 元"));
            paymentInfos.add(new LoanInfoVo("待还金额",canaRepaymentResultDTO.getDebtAmount()+" 元"));
            resultMap.put("paymentInfos",paymentInfos);
            List<LoanInfoVo> refillInfos=new ArrayList<LoanInfoVo>();
            refillInfos.add(new LoanInfoVo("还需充值",canaRepaymentResultDTO.getRefillAmount()+"|元"));
            resultMap.put("refillInfos",refillInfos);
            resultMap.put("buttonTextMessage","取消|充值");
        }
        return resultMap;
    }

    private class PaymentDetail{
        private String calculationMethodDescription;
        private List<LoanInfoVo> paymentInfos;

        public String getCalculationMethodDescription() {
            return calculationMethodDescription;
        }

        public void setCalculationMethodDescription(String calculationMethodDescription) {
            this.calculationMethodDescription = calculationMethodDescription;
        }

        public List<LoanInfoVo> getPaymentInfos() {
            return paymentInfos;
        }

        public void setPaymentInfos(List<LoanInfoVo> paymentInfos) {
            this.paymentInfos = paymentInfos;
        }
    }
}
