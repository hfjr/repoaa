package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.cana.dto.ApplyLoanReq;
import com.zhuanyi.vjwealth.loan.cana.dto.cana.SendVerificationReq;
import com.zhuanyi.vjwealth.loan.cana.webservice.ICanaLoanInterfaceService;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.constant.BizConstant;
import com.zhuanyi.vjwealth.loan.order.cana.vo.BorrowApplyIntiVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.InformationConfirmationSMSVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.InformationConfirmationVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.LoanApplicationInitVo;
import com.zhuanyi.vjwealth.loan.order.vo.LoanInfoVo;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaLoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.util.NumberUtil;
import com.zhuanyi.vjwealth.loan.util.PDFUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICanaApplyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IPdfFileOperationService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan.ApplyLoanQueryDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class CanaApplyLoanServiceImpl implements ICanaApplyLoanService {
    @Autowired
    private ICommConfigsQueryService commConfigsQueryService;
    @Autowired
    private ICanaLoanApplicationDubboService canaLoanApplicationDubboService;
    @Autowired
    private ICanaLoanInterfaceService canaLoanInterfaceService;
    @Remote
    private IPhoneMessageService phoneMessageService;
    @Autowired
    private IPdfFileOperationService pdfFileOperationService;
    /**
     * 12.借款申请 ［未借款，借款处理中，借款失败，借款成功（还款中）］ 初始化
     * @param queryDto
     * @return
     */
    public Object loanApplicationInit(ApplyLoanQueryDTO queryDto){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("borrowTitle","锦囊");
        resultMap.put("canBorrowAmountTitle","可借额度");
        LoanApplicationInitVo loanApplicationInitVo=canaLoanApplicationDubboService.loanApplicationInit(queryDto.getUserId(),queryDto.getProductTypeCode());
        resultMap.put("canBorrowAmount",NumberUtil.convertFenAmount(loanApplicationInitVo.getCanBorrowAmount(),2));
        resultMap.put("loanAmountTitle",MessageFormat.format("信用额度￥{0}", NumberUtil.convertFenAmount(loanApplicationInitVo.getLoanAmount(),2)));
        resultMap.put("canBorrowAmountRatio",String.valueOf(loanApplicationInitVo.getCanBorrowAmountRatio()));
        resultMap.put("isCanEditInformation",(loanApplicationInitVo.getCreditAuditRecordNum()>0)?"false":"true");
        resultMap.put("isShowCreditInformation",(loanApplicationInitVo.getCreditFailRecordNum()>0
                ||loanApplicationInitVo.getCreditAuditRecordNum()>0)?"true":"false");
        if(loanApplicationInitVo.getCreditFailRecordNum()>0){
            resultMap.put("creditInformation", new AuditInformation("资料申请失败"
                    , "用户资料不完善", String.valueOf(loanApplicationInitVo.getCreditFailRecordNum())));
        }else if(loanApplicationInitVo.getCreditAuditRecordNum()>0){
            resultMap.put("creditInformation", new AuditInformation("资料申请审核中，请耐心等待"
                    , "审核结果将于一个工作日内公布，敬请留意", String.valueOf(loanApplicationInitVo.getCreditAuditRecordNum())));
        }
        resultMap.put("isShowAuditInformation",loanApplicationInitVo.getAuditRecordNum()>0?"true":"false");
        resultMap.put("auditInformation",new AuditInformation("借款申请审核中，请耐心等待"
                ,"审核结果将于一个工作日内公布，敬请留意",String.valueOf(loanApplicationInitVo.getAuditRecordNum())));

        resultMap.put("isShowFailInformation",loanApplicationInitVo.getFailRecordNum()>0?"true":"false");
        resultMap.put("failInformation",new AuditInformation("借款失败","",String.valueOf(loanApplicationInitVo.getFailRecordNum())));

        resultMap.put("isShowRepaymentInformation",loanApplicationInitVo.getAllBorrowRecord()>0?"true":"false");
        Map<String,Object> repaymentInformation=new HashMap<String,Object>();
        repaymentInformation.put("allBorrowRecordTitle",MessageFormat.format("查看全部借款({0})",String.valueOf(loanApplicationInitVo.getAllBorrowRecord())));
        if(loanApplicationInitVo.getRepaymentRecord()>0){
            repaymentInformation.put("repaymentDate",MessageFormat.format("{0}到期应还",loanApplicationInitVo.getRepaymentDate()));
            repaymentInformation.put("repaymentAmount",NumberUtil.convertFenAmount(loanApplicationInitVo.getRepaymentAmount(),2));
            repaymentInformation.put("isShowRepayment","true");
        }else{
            repaymentInformation.put("repaymentDate","");
            repaymentInformation.put("repaymentAmount","");
            repaymentInformation.put("isShowRepayment","false");
        }
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.ICON_GROUP_CANA);
        repaymentInformation.put("statusMarkURL",loanApplicationInitVo.getIsOverDue()>0?commConfigMap.get(BizConstant.REPAYMENT_STATUS_OVERDUE_URL):"");
        repaymentInformation.put("repaymentPeriodCode",loanApplicationInitVo.getPlanId()==null?"":loanApplicationInitVo.getPlanId());
        repaymentInformation.put("borrowCode",loanApplicationInitVo.getOrderId()==null?"":loanApplicationInitVo.getOrderId());
        repaymentInformation.put("debitType","账户余额自动扣除");
        resultMap.put("repaymentInformation",repaymentInformation);
        //设置产品描述信息
        buildCommonProductDescription(resultMap);

        resultMap.put("loanNotes", "《借款须知》");
        resultMap.put("loanNotesDetail", "借款须知|确认");
        List<LoanInfoVo> loanInfoContentVos = new ArrayList<LoanInfoVo>();
        loanInfoContentVos.add(new LoanInfoVo("还款方式", "等额本金，每月从账户余额扣款。"));
        loanInfoContentVos.add(new LoanInfoVo("利息计算方式", "按实际借款天数计息，日利率低至0.055%，已还本金不计利息。"));
        resultMap.put("loanNotesDetailContent",loanInfoContentVos);

        List<ButtonInformation> buttonInformations=new ArrayList<ButtonInformation>();
        if(BigDecimal.ZERO.compareTo(loanApplicationInitVo.getLoanAmount())==0||
                loanApplicationInitVo.getFailRecordNum()>0||loanApplicationInitVo.getCreditFailRecordNum()>0){
            buttonInformations.add(new ButtonInformation("203500","修改资料","true"));
        }else{
            buttonInformations.add(new ButtonInformation("203501", "申请借款",
                    (BigDecimal.ZERO.compareTo(loanApplicationInitVo.getCanBorrowAmount())==0
                    ||loanApplicationInitVo.getCreditAuditRecordNum()>0)?"false":"true"));
        }
        if(loanApplicationInitVo.getAllBorrowRecord()>0){
            buttonInformations.add(new ButtonInformation("203502","立即还款","true"));
        }
        resultMap.put("buttonInformation",buttonInformations);
        return resultMap;
    }

    /**
     * 13.借款申请［额度，还款期数填写］初始化
     * @param userId
     * @param productTypeCode
     * @return
     */
    @Override
    public Object amountAndRepaymentInstallmentFormInit(String userId, String productTypeCode) {
        BorrowApplyIntiVo borrowApplyIntiVo=canaLoanApplicationDubboService.amountAndRepaymentInstallmentFormInit(userId,productTypeCode);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("borrowCode",borrowApplyIntiVo.getBorrowCode());
        resultMap.put("applyAmountTitle","申请借款");
        resultMap.put("canBorrowAmount",NumberUtil.convertFenAmount(borrowApplyIntiVo.getCanBorrowAmount(),2));
        resultMap.put("canBorrowAmountLabel","借款金额");
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CANA_LOAN_SETTING);
        resultMap.put("minCanBorrowAmount",commConfigMap.get(BizCommonConstant.MIN_BORROW_AMOUNT_CANA));
        resultMap.put("maxCanBorrowAmountTips","借款金额不能大于最大可借金额");
        resultMap.put("minCanBorrowAmountTips",MessageFormat.format("最低借款额度{0}元",commConfigMap.get(BizCommonConstant.MIN_BORROW_AMOUNT_CANA)));
        resultMap.put("canBorrowAmountTips","申请额度未达总金额");
        resultMap.put("repaymentDateLabel","还款日期");
        resultMap.put("repaymentDate",MessageFormat.format("{0}前",borrowApplyIntiVo.getRepaymentDate()));
        //设置产品描述信息
        buildCommonProductDescription(resultMap);
        resultMap.put("buttonTextMessage","申请借款");
        return resultMap;
    }
    /**
     * 14.借款申请［额度填写］保存接口
     * @param param
     * @return
     * @since 3.4
     */
    @Override
    public Object amountAndRepaymentInstallmentFormSave(RepaymentPlanQueryVo param) {
        return canaLoanApplicationDubboService.amountAndRepaymentInstallmentFormSave(param);
    }
    /**
     * 15.借款申请［信息确认］初始化
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    @Override
    public Object informationConfirmationIniti(String userId, String borrowCode) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("personalInformationTitle", "信息确认");
        InformationConfirmationVo informationVo=canaLoanApplicationDubboService.informationConfirmationIniti(userId, borrowCode);
        List<LoanInfoVo> personalInformation = new ArrayList<LoanInfoVo>();
        personalInformation.add(new LoanInfoVo("姓名",informationVo.getClientName()));
        personalInformation.add(new LoanInfoVo("身份证号码",informationVo.getIndentityNo()));
        resultMap.put("personalInformation", personalInformation);

        List<LoanInfoVo> loadDetailInformation = new ArrayList<LoanInfoVo>();
        loadDetailInformation.add(new LoanInfoVo("借款金额", MessageFormat.format("￥{0}",informationVo.getAmount())));
        loadDetailInformation.add(new LoanInfoVo("收款银行",informationVo.getCardBankName()));
        loadDetailInformation.add(new LoanInfoVo("借款账户", informationVo.getCardNo()));
        loadDetailInformation.add(new LoanInfoVo("起止时间",MessageFormat.format("{0}-{1}",informationVo.getLoanApplyTime(),informationVo.getEndTime())));
        loadDetailInformation.add(new LoanInfoVo("计划还款日",informationVo.getEndTime()));
        loadDetailInformation.add(new LoanInfoVo("借款期限",MessageFormat.format("{0}天",informationVo.getPeriods())));
        loadDetailInformation.add(new LoanInfoVo("借款方式", informationVo.getInterestType()));
        loadDetailInformation.add(new LoanInfoVo("应还利息",MessageFormat.format("￥{0}",informationVo.getInterest())));
        resultMap.put("loadDetailInformation", loadDetailInformation);

        resultMap.put("protocolSpecification", "请仔细阅读本协议，点击确认提交表示您同意遵守");
        resultMap.put("protocolName", "《合同及相关协议》");
        resultMap.put("contractTitle", "借款合同");
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CANA_LOAN_SETTING);
        resultMap.put("contractURL",commConfigMap.get(BizCommonConstant.CANA_CONTRACT_URL));
        resultMap.put("buttonTextMessage", "确认");
        return resultMap;
    }
    /**
     * 16.借款申请［信息确认］《合同及相关协议》-借款合同 H5接口
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    @Override
    public Map<String, String> investmentContractTemplate(String userId, String borrowCode,String productTypeCode) {
        Map<String, String> returnMap=canaLoanApplicationDubboService.investmentContractTemplate(userId,borrowCode,productTypeCode);
        if(BizCodeType.CONTRACT_SAVE.getCode().equals(returnMap.get("type"))){
            String fileId=null;
            try {
                BaseLogger.info("pdf文件上传开始,borrowCode:"+borrowCode);
                fileId=pdfFileOperationService.uploadFile(borrowCode+".pdf",PDFUtil.base64StringToPDF(returnMap.get("content")));
                BaseLogger.info("pdf文件上传结束,fileId:"+fileId);
            } catch (IOException e) {
                BaseLogger.error("pdf文件上传异常:",e);
            }
            if(fileId!=null){
                canaLoanApplicationDubboService.saveContractFileId(returnMap.get("agreementsId"),fileId);
            }else {
                returnMap.put("flag", BizCodeType.IS_NO.getCode());
            }
            returnMap.put("fileId",fileId);
        }
        return returnMap;
    }
    /**
     * 16.1.借款申请 合同下载
     *
     * @param fileId
     * @return
     * @since 3.4
     */
    @Override
    public byte[] downContract(String fileId) {
        return pdfFileOperationService.loadFile(fileId);
    }

    /**
     * 17.借款申请［信息确认-SMS］初始化
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    @Override
    public Object informationConfirmationSMSVerificationIniti(String userId, String borrowCode) {
        InformationConfirmationSMSVo informationConfirmationSMSVo=canaLoanApplicationDubboService.informationConfirmationSMSVerificationIniti(userId,borrowCode);
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("personalInformationTitle", "信息确认");
        resultMap.put("personalInformationLabel", "信息确认无误后填写验证码");
        resultMap.put("phoneDisplayLabel", "绑定手机号");
        resultMap.put("phoneDisplay",informationConfirmationSMSVo.getPhoneDisplay());
        resultMap.put("phone", informationConfirmationSMSVo.getPhone());
        resultMap.put("codeLabel", "验证码");
        resultMap.put("buttonTextMessage", "确认提交");
        return resultMap;

    }
    /**
     * 18 借款申请［信息确认-SMS］ (18 获取文字验证码)
     *
     * @param userId
     *  @param borrowCode
     * @return
     * @since 3.4
     */
    @Override
    public Object informationConfirmationSMSVerificationSendSMSNotice(String userId, String borrowCode) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        InformationConfirmationSMSVo informationConfirmationSMSVo = canaLoanApplicationDubboService.informationConfirmationSMSVerificationIniti(userId, borrowCode);
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CANA_LOAN_SETTING);
        String smsChannel=commConfigMap.get("cana_send_sms_channel");
        if("vj".equals(smsChannel)) {
            MessageDTO md = phoneMessageService.sendTextMessage(informationConfirmationSMSVo.getPhone(), "cana_apply_loan_sms");
            if (!md.getSendFlag()) {
                throw new AppException(md.getSendFlagMessage());
            }
            resultMap.put("remainTime",md.getVaildeTime());
        }else {
            SendVerificationReq sendVerificationReq = new SendVerificationReq();
            sendVerificationReq.setOrderId(borrowCode);
            sendVerificationReq.setPhone(informationConfirmationSMSVo.getPhone());
            canaLoanInterfaceService.sendVerification(sendVerificationReq);
            resultMap.put("remainTime","60");
        }
        resultMap.put("message", "验证码已发送至手机");
        return resultMap;
    }
    /**
     * 19.借款申请［信息确认-SMS］保存
     * @param userId
     * @param borrowCode
     * @param code
     * @return
     * @since 3.4
     */
    @Override
    public Object informationConfirmationSMSVerificationSave(String userId, String borrowCode, String code) {
        Map<String, String> smsCommConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CANA_LOAN_SETTING);
        String smsChannel=smsCommConfigMap.get("cana_send_sms_channel");
        if("vj".equals(smsChannel)) {
            InformationConfirmationSMSVo informationConfirmationSMSVo = canaLoanApplicationDubboService.informationConfirmationSMSVerificationIniti(userId, borrowCode);
            MessageDTO message = phoneMessageService.checkValidationCode(informationConfirmationSMSVo.getPhone(), code, "cana_apply_loan_sms");
            if (!message.getSendFlag()) {
                throw new AppException(message.getSendFlagMessage());
            }
        }
        final Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.ICON_GROUP_CANA);
        Map<String, Object> resultMap = new HashMap<String, Object>(){{
            put("informationTitle","借款成功");
            put("feedbackInformation","将于一个工作日内完成审核");
            put("iconURL",commConfigMap.get(BizCommonConstant.ICON_CONFIRMATIONSMSVERIFICATION));
        }};
        //Map<String, String> commLenderConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CANA_LOAN_SETTING);
        ApplyLoanReq applyLoanReq=new ApplyLoanReq();
        applyLoanReq.setOrderId(borrowCode);
        applyLoanReq.setCheckCode(code);
        Map<String, Object> applyResultMap=canaLoanApplicationDubboService.informationConfirmationSMSVerificationSave(userId,applyLoanReq);
        resultMap.putAll(applyResultMap);
        return resultMap;
    }

    /**
     * 产品描述信息设置
     * @param resultMap
     */
    private void buildCommonProductDescription(Map<String,Object> resultMap){
        resultMap.put("canBorrowAmountInterestDescription","按日计息|日利率低至|0.055|%");
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.ICON_GROUP_CANA);
        List<IconRecord> iconRecords=new ArrayList<IconRecord>();
        iconRecords.add(new IconRecord(commConfigMap.get(BizCommonConstant.ICON_QUICKINFORMATION),"20日内","随借随还"));
        iconRecords.add(new IconRecord(commConfigMap.get(BizCommonConstant.ICON_REALTIMEAMOUNT),"成功还款","额度实时恢复"));
        resultMap.put("iconRecord",iconRecords);
    }

    private class AuditInformation implements Serializable{
        private String title;
        private String message;
        private String countNum;

        public AuditInformation(String title, String message, String countNum) {
            this.title = title;
            this.message = message;
            this.countNum = countNum;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getCountNum() {
            return countNum;
        }
        public void setCountNum(String countNum) {
            this.countNum = countNum;
        }
    }

    private class IconRecord implements Serializable {
        private String iconUrl;
        private String smallWordsDesc;
        private String wordsDesc;

        public IconRecord(String iconUrl, String smallWordsDesc, String wordsDesc) {
            this.iconUrl = iconUrl;
            this.smallWordsDesc = smallWordsDesc;
            this.wordsDesc = wordsDesc;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getSmallWordsDesc() {
            return smallWordsDesc;
        }

        public void setSmallWordsDesc(String smallWordsDesc) {
            this.smallWordsDesc = smallWordsDesc;
        }

        public String getWordsDesc() {
            return wordsDesc;
        }

        public void setWordsDesc(String wordsDesc) {
            this.wordsDesc = wordsDesc;
        }
    }

    private class ButtonInformation {
        private String code;
        private String isEnable;
        private String buttonTextMessage;

        public ButtonInformation(String code, String buttonTextMessage,String isEnable) {
            this.code = code;
            this.buttonTextMessage = buttonTextMessage;
            this.isEnable = isEnable;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getButtonTextMessage() {
            return buttonTextMessage;
        }

        public void setButtonTextMessage(String buttonTextMessage) {
            this.buttonTextMessage = buttonTextMessage;
        }

        public String getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(String isEnable) {
            this.isEnable = isEnable;
        }
    }
}
