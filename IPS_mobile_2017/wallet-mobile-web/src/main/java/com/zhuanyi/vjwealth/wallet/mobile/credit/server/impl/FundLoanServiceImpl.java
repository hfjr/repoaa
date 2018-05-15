package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;


import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.template.ITemplateService;
import com.zhuanyi.vjwealth.loan.client.dto.ClientBasicInfoDTO;
import com.zhuanyi.vjwealth.loan.functionInterface.Housingfund.vo.HousingfundResponseVO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.FundLoanBankInfo;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.HousingFundLoanInfoDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.HousingFundLoginModeDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.BasicInfoInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.BasicInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.BindCardInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CheckApplyStatusReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationFirstLoginReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationSecondInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationSecondLoginReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ConfirmLoanApplyReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ContactInfoInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ContactInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ContractReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ImproveApplyInfoReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanApplyInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanApplySaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanProcessCountReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanProcessDetailReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanProcessListReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.PhoneCommunicationQueryReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.QueryOrderBasicInfoReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.QueryOrderStatusIsChangedReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.RepaymentTrialReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.UpdateChangedOrderStatusReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztBindCardReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztBindCardSendSmsReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztDynCodeReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztPicCodeReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.BasicInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.BasicInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.BindCardInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CheckApplyStatusResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationFirstLoginResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationSecondInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationSecondLoginResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ConfirmLoanApplyResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ContactInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ContactInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ContractResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ImproveApplyInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanApplyInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanApplySaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessCountResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessDetailResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessListResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.PhoneCommunicationQueryResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.QueryOrderBasicInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.QueryOrderStatusIsChangedResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.RepaymentTrialResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.UpdateChangedOrderStatusResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztBindCardResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztBindCardSendSmsResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztDynCodeResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztPicCodeResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.webservice.IApplyHousingFundLoanDubboService;
import com.zhuanyi.vjwealth.loan.order.vo.IntentionPersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.LoanInfoVo;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentDetailVo;
import com.zhuanyi.vjwealth.loan.order.vo.UploadIdentitysVO;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.util.ValidateUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.DateCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.IPTools;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.UserInfoCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFundLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanProductService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanOrderStatusConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanProductIdConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.CommonCommitResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.BasicInfoInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.BasicInfoSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.BingdingBankCardInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CheckBingdingBankcardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CommunicationDetailInfoInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CommunicationDetailInfoSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CommunicationLoginInfoInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CommunicationLoginInfoSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CommunicationLoginInfoSaveResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.ContactInfoInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.ContactInfoSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundAccountInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundAccountInitiTypeSelectDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundAccountInitiTypeSelectDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundInfoSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanApplyInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanApplyRepayTrialDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanApplySaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanOrderInfo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanRepaymentDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.ImproveApplyInfoCommitResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.ImproveApplyInfoInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.ImproveApplyInfoInitInnerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanAmountSelectionDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanAmountSelectionInnerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanApplyInfoShowDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanProcessCountDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanProcessDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanProcessDetailInnerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanProcessListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.LoanProductInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditIntroduceDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditInvestigationWayDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IFundLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadFileInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 公积金贷款(白领专享)
 * Created by wangzf on 16/10/14.
 */
@Service
public class FundLoanServiceImpl implements IFundLoanService {

    @Autowired
    IFundLoanMapper fundLoanMapper;

    @Remote
    IMBUserService mbUserService;

    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Autowired
    private IApplyHousingFundLoanDubboService applyHousingFundLoanDubboService;

    @Autowired
    private FinancialLoanMapper financialLoanMapper;

    @Autowired
    private ILoanProductService loanProductService;

    @Autowired
    private IOrderDateHelperService orderDateHelperService;

    @Autowired
    private ITemplateService template;
    @Remote
    private IMBUserInviteService mbUserInviteService;


    @Remote
    private ICommonAttachmentOperate commonAttachmentOperate;


    @Override
    public Object queryCityList() {
        return loanProductService.queryLoanProductCityList(LoanProductIdConstant.BLZX);
    }





    private Map<String,String> buildKeyAndValueMap(String key,String value){
        Map<String,String> map = new HashMap<>();
        map.put("key",key);
        map.put("value",value);
        return map;
    }


    @Override
    public Object grantLoanFormIniti(String userId, String borrowCode) {

       return null;
    }




    /************************************************************************分割线**********************************************************/


    @Override
    public LoanProductInitDTO loanProductInit(String userId) {
        //返回结果实体
        LoanProductInitDTO loanProductInitDTO = new LoanProductInitDTO();

        //1.设置金额选择项信息
        LoanAmountSelectionDTO amountSelectionDTO = new LoanAmountSelectionDTO();
        amountSelectionDTO.setDefaultLoanMinAmount("500");
        amountSelectionDTO.setDefaultLoanMaxAmount("50000");
        amountSelectionDTO.setLoanMinAmount("500");
        amountSelectionDTO.setLoanMaxAmount("20000000");

        List<LoanAmountSelectionInnerDTO> selections = new ArrayList<>();
        selections.add(new LoanAmountSelectionInnerDTO("50000","5W","500","500"));
        selections.add(new LoanAmountSelectionInnerDTO("100000","10W","50000","5W"));
        selections.add(new LoanAmountSelectionInnerDTO("20000000","2000W","100000","10W"));
        amountSelectionDTO.setAmountSelection(selections);

        loanProductInitDTO.setLoanAmountSelection(amountSelectionDTO);

        //2.设置筛选产品
        CreditInvestigationWayDTO creditWay = queryMatchedProductList(userId,amountSelectionDTO.getDefaultLoanMinAmount(),amountSelectionDTO.getDefaultLoanMaxAmount());
        loanProductInitDTO.setCreditWays(creditWay);

        //3.查询借款进度

        LoanProcessCountDTO loanProcessCountDTO = queryLoanProcessCount(userId);
        loanProductInitDTO.setLoanProcessInfo(loanProcessCountDTO);

        return loanProductInitDTO;
    }

    @Override
    public FundLoanOrderInfo checkApplyStatus(String userId,boolean queryBasicInfo) {
        FundLoanOrderInfo result = new FundLoanOrderInfo();

        ClientBasicInfoDTO basicInfoDTO = null;
        //查询用户基本信息
        if(queryBasicInfo){
            basicInfoDTO = fundLoanMapper.queryUserBasicInfo(userId);
        }else{
            basicInfoDTO = new  ClientBasicInfoDTO();
            basicInfoDTO.setId(userId);
        }
        CheckApplyStatusReqDTO reqDTO = new CheckApplyStatusReqDTO(basicInfoDTO);
        BaseLogger.info("白领专享查询借款状态入参:"+JSONObject.toJSONString(reqDTO));
        CheckApplyStatusResDTO resDTO = applyHousingFundLoanDubboService.checkApplyStatus(reqDTO);
        BaseLogger.info("白领专享查询借借款状态结果"+JSONObject.toJSONString(resDTO));

        result.setLoanStatus(resDTO.getOrderStatus());
        result.setBorrowCode(resDTO.getBorrowCode());

        return result;
    }

    @Override
    public Object creditIntroduce() {

        CreditIntroduceDTO creditIntroduceDTO = new CreditIntroduceDTO();
        //获取介绍图片
        creditIntroduceDTO.setButtonTextMessage("去贷款");
        String picUrl =  financialLoanMapper.getParamsValueByKeyAndGroup("loan_common_pic_url","loan_comm")+"/4.0/loan_introduce";
        creditIntroduceDTO.setIntroducePictureURL(picUrl+".png");
        creditIntroduceDTO.setIntroducePictureWebpURL(picUrl+".webp");
        //注册及征信授权协议url
        String contractUrl = financialLoanMapper.getParamsValueByKeyAndGroup("credit_registration_and_license_agreement","wallet-mobile");
        creditIntroduceDTO.setProtocolUrl(contractUrl);
        creditIntroduceDTO.setProtocolAgreenmentTip("请仔细阅读以下协议，点击申请额度表示同意遵守");
        creditIntroduceDTO.setProtocolDesc("《注册及征信授权协议》");
        creditIntroduceDTO.setProtocolUrlTitle("注册及征信授权协议");
        return creditIntroduceDTO;
    }

    //筛选产品
    @Override
    public CreditInvestigationWayDTO queryMatchedProductList(String userId,String loanMinAmount,String loanMaxAmount) {
        return loanProductService.queryMatchedProductList(userId,loanMinAmount,loanMaxAmount);
    }

    @Override
    public LoanProcessCountDTO queryLoanProcessCount(String userId) {

        LoanProcessCountReqDTO reqDTO = new LoanProcessCountReqDTO(userId);

        BaseLogger.info("白领专享查询借款进度入参:"+JSONObject.toJSONString(reqDTO));
        LoanProcessCountResDTO resDTO = applyHousingFundLoanDubboService.countLoanProcess(reqDTO);
        BaseLogger.info("白领专享查询借款进度结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        LoanProcessCountDTO resultDto = new LoanProcessCountDTO();
        resultDto.setLoanProcessLabel("借款进度");
        resultDto.setLoanProcessCount(resDTO.getLoanProcessCount());

        return resultDto;
    }

    @Override
    public Object queryLoanRecordList(String userId) {

        LoanProcessListReqDTO reqDTO = new LoanProcessListReqDTO(userId);
        BaseLogger.info("白领专享查询借款进度列表入参:"+JSONObject.toJSONString(reqDTO));
        LoanProcessListResDTO resDTO = applyHousingFundLoanDubboService.loanProcessList(reqDTO);
        BaseLogger.info("白领专享查询借款进度列表结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //返回结果
        List<LoanProcessListDTO> resultList = new ArrayList<>();

        //获取贷款进度列表
        List<HousingFundLoanInfoDTO> processList = resDTO.getLoanProcessList();
        if(CollectionUtils.isNotEmpty(processList)){
            for(HousingFundLoanInfoDTO dto:processList){

                LoanProcessListDTO processDto = new LoanProcessListDTO();
                String status = dto.getOrderStatus();

                //公积金资料审核阶段
                if(LoanOrderStatusConstant.ORDER_STATUS_C.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_C_R.equals(status) ||
                       LoanOrderStatusConstant.ORDER_STATUS_C_A.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_C_F.equals(status)
                        || LoanOrderStatusConstant.ORDER_STATUS_C_S.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_Z.equals(status)){
                    processDto.setLoanApplyTip("申请额度");
                    processDto.setLoanAmountDesc("2000~50000");
                }

                //借款审核阶段
                else if(LoanOrderStatusConstant.ORDER_STATUS_G.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_G_F.equals(status)
                        || LoanOrderStatusConstant.ORDER_STATUS_Z_P.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_Z_F.equals(status)){
                    processDto.setLoanApplyTip("申请借款");
                    processDto.setLoanAmountDesc(dto.getLoanAmount());
                }

                //放款阶段
                else if(LoanOrderStatusConstant.ORDER_STATUS_R.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_C_O.equals(status)
                        || LoanOrderStatusConstant.ORDER_STATUS_F.equals(status)){
                    processDto.setLoanApplyTip("放款金额");
                    processDto.setLoanAmountDesc(dto.getLoanAmount());
                }

                processDto.setBorrowCode(dto.getBorrowCode());
                processDto.setLoanApplyDateDesc("申请时间 "+dto.getApplyTime());
                processDto.setLoanProductName("公积金贷款");
                processDto.setLoanStatus(dto.getOrderStatus());
                processDto.setLoanStatusDesc(LoanOrderStatusConstant.getStatusStr(dto.getOrderStatus()));
                processDto.setIsChanged(dto.isChanged()?"Y":"N");

                resultList.add(processDto);
            }
        }

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("loanRecords",resultList);

        return resultMap;
    }

    @Override
    public Object queryLoanRecordDetail(String userId, String borrowCode,String isChanged) {

        //返回结果失败
        LoanProcessDetailDTO detailDTO = new LoanProcessDetailDTO();

        //获取贷款订单信息
        LoanProcessDetailReqDTO reqDTO = new LoanProcessDetailReqDTO(userId,borrowCode);
        BaseLogger.info("白领专享查询借款进度列表入参:"+JSONObject.toJSONString(reqDTO));
        LoanProcessDetailResDTO resDTO = applyHousingFundLoanDubboService.loanProcessDetail(reqDTO);
        BaseLogger.info("白领专享查询借款进度列表结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //设置基本信息
        String status = resDTO.getOrderStatus();
        detailDTO.setLoanStatus(status);
        detailDTO.setLoanApplyDateDesc("申请时间 "+resDTO.getApplyTime());

        //设置状态流转流程
        List<LoanProcessDetailInnerDTO> innerList = new ArrayList<>();

        //公积金资料审核阶段
        if(LoanOrderStatusConstant.ORDER_STATUS_C.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_C_R.equals(status)){
            innerList = buildGJJProcessInfo(status,resDTO.getApplyTime(),resDTO.isOld());
            detailDTO.setLoanApplyTip("申请额度");
            detailDTO.setLoanAmountDesc("2000~50000");
        }

        //额度审批阶段
        else if(LoanOrderStatusConstant.ORDER_STATUS_C_A.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_C_F.equals(status)
                || LoanOrderStatusConstant.ORDER_STATUS_C_S.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_Z.equals(status)){
            //设置资料审核
            innerList = buildEDProcessInfo(status,resDTO.getUpdateTime(),resDTO.isOld());
            detailDTO.setLoanApplyTip("申请额度");
            detailDTO.setLoanAmountDesc("2000~50000");
        }

        //借款审核阶段
        else if(LoanOrderStatusConstant.ORDER_STATUS_G.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_G_F.equals(status)
                || LoanOrderStatusConstant.ORDER_STATUS_Z_P.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_Z_F.equals(status)){
            //设置资料审核
            innerList = buildJKProcessInfo(status,resDTO.getUpdateTime(),resDTO.isOld());
            detailDTO.setLoanApplyTip("申请借款");
            detailDTO.setLoanAmountDesc(resDTO.getLoanAmount());
        }

        //放款阶段
        else if(LoanOrderStatusConstant.ORDER_STATUS_R.equals(status) || LoanOrderStatusConstant.ORDER_STATUS_C_O.equals(status)
                || LoanOrderStatusConstant.ORDER_STATUS_F.equals(status)){
            //设置资料审核
            innerList = buildFKProcessInfo(status);
            detailDTO.setLoanApplyTip("放款金额");
            detailDTO.setLoanAmountDesc(resDTO.getLoanAmount());
        }

        detailDTO.setLoanProcessInfo(innerList);

        //更新订单状态
        if(!StringUtils.isBlank(isChanged) && isChanged.equals("Y")){
            changeOrderStatus(userId,borrowCode);
        }

        return detailDTO;
    }

    private List<LoanProcessDetailInnerDTO> buildGJJProcessInfo(String status,String applyTime,boolean isOld){
        List<LoanProcessDetailInnerDTO> innerList = new ArrayList<>();
        //设置资料审核
        LoanProcessDetailInnerDTO currentDto = new LoanProcessDetailInnerDTO("资料审核","Y","Y","N");
        if(LoanOrderStatusConstant.ORDER_STATUS_C.equals(status)){
            currentDto.setCurrentStepStatusDesc("公积金信用资料审核中");
            currentDto.setCurrentStepTip(orderDateHelperService.getNextDay(applyTime)+" 晚24点前资料审核完成");
        }else{
            currentDto.setCurrentStepStatusDesc("公积金信用资料审核失败");
            currentDto.setCurrentStepTip("额度申请失败，请修改资料后重新申请");
            if(!isOld){
                currentDto.setIsClick("Y");
            }
        }
        innerList.add(currentDto);
        //设置额度审批
        innerList.add(new LoanProcessDetailInnerDTO("额度审批","N","N","N"));
        //设置借款审核
        innerList.add(new LoanProcessDetailInnerDTO("借款审核","N","N","N"));
        //设置极速放款
        innerList.add(new LoanProcessDetailInnerDTO("极速放款","N","N","N"));

        return innerList;
    }

    private List<LoanProcessDetailInnerDTO> buildEDProcessInfo(String status,Date updateTime,boolean isOld){
        List<LoanProcessDetailInnerDTO> innerList = new ArrayList<>();
        //设置资料审核
        innerList.add(new LoanProcessDetailInnerDTO("资料审核","Y","N","N"));
        //设置额度审批
        LoanProcessDetailInnerDTO currentDto = new LoanProcessDetailInnerDTO("额度审批","Y","Y","N");
        if(LoanOrderStatusConstant.ORDER_STATUS_C_A.equals(status)){
            currentDto.setCurrentStepStatusDesc("额度审批中,请耐心等待");
            currentDto.setCurrentStepTip("");
        }else if(LoanOrderStatusConstant.ORDER_STATUS_C_F.equals(status)){
            currentDto.setCurrentStepStatusDesc("额度申请失败，请申请其他借款产品");
            currentDto.setCurrentStepTip("");
            currentDto.setIsClick("N");
            if(DateCommonUtils.addDay(updateTime,90).before(new Date())){
                if(!isOld){
                    currentDto.setIsClick("Y");
                }
                currentDto.setCurrentStepStatusDesc("额度申请失败，请修改资料重新审核");
            }
        }else if(LoanOrderStatusConstant.ORDER_STATUS_C_S.equals(status) ||  LoanOrderStatusConstant.ORDER_STATUS_Z.equals(status)){
            currentDto.setCurrentStepStatusDesc("额度申请成功");
            currentDto.setCurrentStepTip("");
            if(!isOld){
                currentDto.setCurrentStepStatusDesc("额度申请成功，点击借款");
                currentDto.setIsClick("Y");
            }
        }
        innerList.add(currentDto);
        //设置借款审核
        innerList.add(new LoanProcessDetailInnerDTO("借款审核","N","N","N"));
        //设置极速放款
        innerList.add(new LoanProcessDetailInnerDTO("极速放款","N","N","N"));

        return innerList;
    }


    private List<LoanProcessDetailInnerDTO> buildJKProcessInfo(String status,Date updateTime,boolean isOld){
        List<LoanProcessDetailInnerDTO> innerList = new ArrayList<>();
        //设置资料审核
        innerList.add(new LoanProcessDetailInnerDTO("资料审核","Y","N","N"));
        //设置额度审批
        innerList.add(new LoanProcessDetailInnerDTO("额度审批","Y","N","N"));
        //设置借款审核
        LoanProcessDetailInnerDTO currentDto = new LoanProcessDetailInnerDTO("借款审核","Y","Y","N");
        if(LoanOrderStatusConstant.ORDER_STATUS_G.equals(status)){
            currentDto.setCurrentStepStatusDesc("借款审核中");
            currentDto.setCurrentStepTip("最快将于一个工作日内完成审核");
        }else if(LoanOrderStatusConstant.ORDER_STATUS_Z_P.equals(status)){
            currentDto.setCurrentStepStatusDesc("实名认证中");
            currentDto.setCurrentStepTip("");
        }else if(LoanOrderStatusConstant.ORDER_STATUS_Z_F.equals(status)){
            currentDto.setCurrentStepStatusDesc("实名认证失败，请重新上传身份证图片");
            currentDto.setCurrentStepTip("");
            currentDto.setIsClick("Y");
        }else if(LoanOrderStatusConstant.ORDER_STATUS_G_F.equals(status)){
            currentDto.setCurrentStepStatusDesc("借款失败，请申请其他借款产品");
            currentDto.setCurrentStepTip("");
            currentDto.setIsClick("N");
            if(DateCommonUtils.addDay(updateTime,90).before(new Date())){
                currentDto.setCurrentStepStatusDesc("借款失败，请修改资料后重新申请");
                if(!isOld){
                    currentDto.setIsClick("Y");
                }
            }
        }
        innerList.add(currentDto);

        //设置极速放款
        innerList.add(new LoanProcessDetailInnerDTO("极速放款","N","N","N"));

        return innerList;
    }

    private List<LoanProcessDetailInnerDTO> buildFKProcessInfo(String status){
        List<LoanProcessDetailInnerDTO> innerList = new ArrayList<>();
        //设置资料审核
        innerList.add(new LoanProcessDetailInnerDTO("资料审核","Y","N","N"));
        //设置额度审批
        innerList.add(new LoanProcessDetailInnerDTO("额度审批","Y","N","N"));
        //设置借款审核
        innerList.add(new LoanProcessDetailInnerDTO("借款审核","Y","N","N"));
        //设置极速放款
        innerList.add(new LoanProcessDetailInnerDTO("极速放款","Y","Y","N"));

        return innerList;
    }



    //公积金初始化
    @Override
    public Object fundAccountIniti(String userId,String borrowCode,String cityCode,String isInit) {

        FundAccountInitiDTO fundAccountInitiDTO = new FundAccountInitiDTO();

        HousingInfoInitReqDTO reqDTO = new HousingInfoInitReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setCityCode(cityCode);
        reqDTO.setFirst(isInit.equals("Y")?true:false);
        BaseLogger.info("白领专享查询公积金录入初始化入参"+JSONObject.toJSONString(reqDTO));
        HousingInfoInitResDTO resDTO = applyHousingFundLoanDubboService.housingFundInfoInit(reqDTO);
        BaseLogger.info("白领专享查询公积金录入初始化返回结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //设置公积金城市信息
        fundAccountInitiDTO.setDefaultCity(buildKeyAndValueMap(resDTO.getCityCode(),resDTO.getCityName()));

        //设置常量
        fundAccountInitiDTO.setFundAccountOpenHelpLabel("忘记公积金账号密码");
        fundAccountInitiDTO.setFundAccountOpenHelpMessage("温馨提示 |如您遗忘社保、公积金账户及密码，请至当地社保局或社保查询网站进行相关咨询。|确认");
        fundAccountInitiDTO.setCityLabel("城市");
        fundAccountInitiDTO.setFundAccountQueryTitle("个人公积金查询信息");
        fundAccountInitiDTO.setLoginTypeLabel("账号类型");

        //获取公积金账号类型列表
        List<HousingFundLoginModeDTO> list = resDTO.getHousingFundLoginModeList();

        //公积金账号类型详细列表
        List<FundAccountInitiTypeSelectDTO> selectDTOs = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(list)){
            for(HousingFundLoginModeDTO modelDto:list){
                //设置公积金类型的详细信息
                FundAccountInitiTypeSelectDTO typeSelectDTO = new FundAccountInitiTypeSelectDTO();
                FundAccountInitiTypeSelectDetailDTO detailDTO = new FundAccountInitiTypeSelectDetailDTO();

                BeanUtils.copyProperties(modelDto,detailDTO);

                typeSelectDTO.setKey(modelDto.getLoginType());
                typeSelectDTO.setValue(modelDto.getLoginTypeLabel());
//                detailDTO.setRealNameLabel("");//默认不输入
                typeSelectDTO.setUiDesc(detailDTO);

                selectDTOs.add(typeSelectDTO);
            }
        }


        //设置默认logintype
        FundAccountInitiTypeSelectDTO defaultTypeSelectDTO = new FundAccountInitiTypeSelectDTO();
        FundAccountInitiTypeSelectDetailDTO defaultDetailDTO = new FundAccountInitiTypeSelectDetailDTO();

        HousingFundLoginModeDTO defaultModel = resDTO.getDefaultHousingFundLoginMode();

        BeanUtils.copyProperties(defaultModel,defaultDetailDTO);


        defaultTypeSelectDTO.setKey(defaultModel.getLoginType());
        defaultTypeSelectDTO.setValue(defaultModel.getLoginTypeLabel());
//        defaultDetailDTO.setRealNameLabel("");//默认不输入
        defaultTypeSelectDTO.setUiDesc(defaultDetailDTO);
        fundAccountInitiDTO.setDefaultLoginType(defaultTypeSelectDTO);

        fundAccountInitiDTO.setLoginTypeSelections(selectDTOs);

        return fundAccountInitiDTO;
    }



    @Override
    public Object applyCreditPersonalInfoCommit(FundInfoSaveDTO fundInfoSaveDTO, HttpServletRequest request) {

        //校验
        checkFundInfo(fundInfoSaveDTO);


        //入参
        HousingInfoSaveReqDTO reqDTO = new HousingInfoSaveReqDTO();

        String ipAddress = "";
        try{
            ipAddress = IPTools.getIpAddress(request);
        }catch (Exception e){
            BaseLogger.error("获取IP地址异常",e);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            reqDTO.setEquipmentIp(ipAddress);
        }

        //获取贷款订单信息
        BeanUtils.copyProperties(fundInfoSaveDTO,reqDTO);

        BaseLogger.info("白领专享公积金信息保存入参:"+JSONObject.toJSONString(reqDTO));
        HousingInfoSaveResDTO resDTO = applyHousingFundLoanDubboService.housingInfoSave(reqDTO);
        BaseLogger.info("白领专享公积金信息保存结果"+JSONObject.toJSONString(resDTO));

        if(!"200".equals(resDTO.getCode())){
            throw new AppException(resDTO.getMsg());
        }
        return new CommonCommitResultDTO("","资料提交成功","将于一个工作日内完成审核","");
    }

    private void checkFundInfo(FundInfoSaveDTO query){
        if (query == null || StringUtils.isEmpty(query.getUserId())) {
            throw new AppException("用户编号不能为空");
        }
        if (query == null || StringUtils.isEmpty(query.getCityCode())) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(query.getFundAccount())) {
            throw new AppException("公积金账户不能为空");
        }
        if (!ValidateUtil.isFundAccount(query.getFundAccount())) {
            throw new AppException("公积金账户格式无效:请至少输入两位字符");
        }
//        if (StringUtils.isEmpty(query.getFundAccountPassword())) {
//            throw new AppException("公积金账户密码不能为空");
//        }

        if (StringUtils.isEmpty(query.getOsType())) {
            throw new AppException("系统类型不能为空");
        }

        if (StringUtils.isEmpty(query.getOsVersion())) {
            throw new AppException("系统版本不能为空");
        }

        if (StringUtils.isEmpty(query.getEquipmentNumber())) {
            throw new AppException("设备号不能为空");
        }


        //判断正则表达式
        if(!StringUtils.isBlank(query.getFundAccountValidator()) && !Pattern.matches(query.getFundAccountValidator(),query.getFundAccount())){
            throw new AppException("请正确填写公积金账户格式");
        }

        if(!StringUtils.isBlank(query.getFundAccountPasswordValidator()) && !Pattern.matches(query.getFundAccountPasswordValidator(),query.getFundAccountPassword())){
            throw new AppException("请正确填写公积金密码格式");
        }

        if(!StringUtils.isBlank(query.getIdCardValidator()) && !Pattern.matches(query.getIdCardValidator(),query.getIdCard())){
            throw new AppException("请正确填写身份证号码格式");
        }

        if(!StringUtils.isBlank(query.getOtherParamValidator()) && !Pattern.matches(query.getOtherParamValidator(),query.getOtherParam())){
            throw new AppException("请正确填写数据格式");
        }

    }



    @Override
    public Object loanApplyInit(String userId, String borrowCode) {

        //返回结果实体
        FundLoanApplyInitDTO result = new FundLoanApplyInitDTO();

        //查询订单信息
        LoanApplyInitReqDTO reqDTO = new LoanApplyInitReqDTO(userId,borrowCode);
        BaseLogger.info("借款申请-金额录入初始化入参:"+JSONObject.toJSONString(reqDTO));
        LoanApplyInitResDTO resDTO = applyHousingFundLoanDubboService.loanApplyInit(reqDTO);
        BaseLogger.info("借款申请-金额录入初始化结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //设置基本信息
        BeanUtils.copyProperties(resDTO,result);
        result.setLoanProductDesc("按日计息，日利率低至 "+"<font color='#fac744'>"+resDTO.getLoanMinDayRate()+"%</font>");
        result.setQuotaDescription("审批金额仅供单次申请，如仍需后续资金，须还清所有待还金额，方可再次进行贷款申请");
        result.setRepaymentPeriodLabel("还款期数");
        result.setDefaultRepaymentPeriod(resDTO.getDefaultRepaymentPeriod()); //默认借款期数
        result.setRepaymentPeriodSelection(resDTO.getRepaymentPeriodSelection());//借款期数

        List<Map<String,String>> methodSelection = new ArrayList<>();
        methodSelection.add(resDTO.getDefaultRepaymentMethod());
        result.setRepaymentMethodSelection(methodSelection);

        result.setRepaymentMethodLabel("还款方式");
        result.setRepaymentMethod("等额本息");
        result.setFirstRepaymentLabel("首次还款");
        result.setRepaymentDateLabel("还款日");
        result.setRepaymentDetailLabel("还款详情");
        result.setRepaymentDetail("查看详情");
        result.setIsClickRepaymentDetail("true");
        result.setMonthlyPrincipalAndInterestLabel("每月应还本息");

        //设置借款须知
        List<LoanInfoVo> loanInfoContentVos = new ArrayList<>();
        loanInfoContentVos.add(new LoanInfoVo("还款方式", "等额本息，每月从借款银行卡或账户余额扣款。"));
        loanInfoContentVos.add(new LoanInfoVo("利息计算方式", "按实际借款天数计息，日利率低至0.035%，已还本金不计利息。"));
        result.setLoanNotesDetail("借款须知|确认");
        result.setLoanNotesDetailContent(loanInfoContentVos);
        result.setLoanNotes("《借款须知》");

        //调用还款计划试算
        FundLoanApplyRepayTrialDTO repayTrialDTO = repaymentTrial(userId,borrowCode,result.getBorrowAmount(),result.getDefaultRepaymentPeriod().get("key"));
        BeanUtils.copyProperties(repayTrialDTO,result);

        return result;
    }


    @Override
    public FundLoanApplyRepayTrialDTO repaymentTrial(String userId,String borrowCode, String loanAmount,String loanPeriod) {

        FundLoanApplyRepayTrialDTO trialDTO = new FundLoanApplyRepayTrialDTO();

        //查询订单信息
        RepaymentTrialReqDTO reqDTO = new RepaymentTrialReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setLoanAmount(loanAmount);
        reqDTO.setRepaymentPeriod(loanPeriod);
        BaseLogger.info("借款申请-还款计划试算入参:"+JSONObject.toJSONString(reqDTO));
        RepaymentTrialResDTO resDTO = applyHousingFundLoanDubboService.repaymentTrial(reqDTO);
        BaseLogger.info("借款申请-还款计划试算结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //设置还款汇总信息
        trialDTO.setRepaymentDate("每月"+resDTO.getRepaymentDate()+"日");
        trialDTO.setFirstRepayment("¥"+resDTO.getFirstRepaymentAmount()+"（"+resDTO.getFirstRepaymentTime()+"）");
        trialDTO.setMonthlyPrincipalAndInterest("借满"+resDTO.getRepaymentPeriod()+"个月总利息￥"+resDTO.getInterestTotal());
        trialDTO.setMonthlyPrincipalAndInterestLabel("每月应还本息");
        List<String> titleList = new ArrayList<>();
        titleList.add("还款时间");
        titleList.add("应还本金");
        titleList.add("应还利息及费用");
        trialDTO.setGeneratedRepaymentDetailLabels(titleList);


        //获取还款计划
        List<FundLoanRepaymentDTO> returnRepayList = new ArrayList<>();
        List<RepaymentDetailVo> repayList = resDTO.getRepaymentPlanList();
        if(CollectionUtils.isNotEmpty(repayList)){
            for(RepaymentDetailVo vo:repayList){
                FundLoanRepaymentDTO returnRepay = new FundLoanRepaymentDTO();
                returnRepay.setShouldPrincipal("￥"+vo.getShouldPrincipal());
                returnRepay.setShouldInterest("￥"+vo.getShouldInterest());
                returnRepay.setRepaymentDate(vo.getRepaymentDate());
                returnRepayList.add(returnRepay);
            }
        }

        //设置还款计划
        trialDTO.setGeneratedRepaymentDetailList(returnRepayList);

        return trialDTO;
    }

    @Override
    public Object loanApplySave(FundLoanApplySaveDTO applySaveDTO) {

        LoanApplySaveReqDTO reqDTO = new LoanApplySaveReqDTO();
        BeanUtils.copyProperties(applySaveDTO,reqDTO);
        reqDTO.setLoanAmount(applySaveDTO.getApplyAmount());
        BaseLogger.info("借款申请-保存入参:"+JSONObject.toJSONString(reqDTO));
        LoanApplySaveResDTO resDTO = applyHousingFundLoanDubboService.loanApplySave(reqDTO);
        BaseLogger.info("借款申请-保存结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        if("200".equals(resDTO.getCode())){
            return new CommonCommitResultDTO("202100","资料提交成功","将于一个工作日内完成审核","");
        }
        return new CommonCommitResultDTO("202101",resDTO.getMsg(),"","");

    }

    @Override
    public Object improveApplyInfo(String userId, String borrowCode) {

        //返回结果实体
        ImproveApplyInfoInitDTO infoInitDTO = new ImproveApplyInfoInitDTO();

        ImproveApplyInfoReqDTO reqDTO = new ImproveApplyInfoReqDTO(userId,borrowCode);
        BaseLogger.info("借款申请-完善资料初始化入参:"+JSONObject.toJSONString(reqDTO));
        ImproveApplyInfoResDTO resDTO = applyHousingFundLoanDubboService.improveApplyInfo(reqDTO);
        BaseLogger.info("借款申请-完善资料初始化结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        infoInitDTO.setImproveInfoTip("请您仔细填写资料，乱填或误填将会影响贷款申请!");
        infoInitDTO.setTipInformation("请仔细阅读借款协议，点击确认提交表示您同意遵守");
        infoInitDTO.setProtocolName("《合同及相关协议》");

        String commUrl =  financialLoanMapper.getParamsValueByKeyAndGroup("loan_common_url","loan_comm");

        List<Map<String,String>> urls = new ArrayList<>();
        urls.add(buildLabelAndValueMap("借款协议",commUrl+"/api/v4.0/app/credit/fund/contract"+"?borrowCode="+borrowCode));
        urls.add(buildLabelAndValueMap("咨询服务协议",commUrl+"/api/v4.0/app/credit/fund/consult"+"?borrowCode="+borrowCode));
        urls.add(buildLabelAndValueMap("个人征信系统查询授权委托书",commUrl+"/api/v4.0/app/credit/fund/reference"+"?borrowCode="+borrowCode));

        infoInitDTO.setProtocolURLs(urls);//合同及相关协议地址


        //设置完善信息列表
        List<ImproveApplyInfoInitInnerDTO> innerList = new ArrayList<>();

        String bankCardNo = resDTO.getBankCardNo();
        String desc = "已完成";
        if(!StringUtils.isBlank(bankCardNo)){
            desc = resDTO.getBankName()+"(尾号"+bankCardNo.substring(bankCardNo.length()-4,bankCardNo.length())+")";
        }

        //认证信息
        ImproveApplyInfoInitInnerDTO certificationTO = new ImproveApplyInfoInitInnerDTO("实名认证",resDTO.isCertificationStatus()?"Y":"N",resDTO.isCertificationStatus()?"已完成":"未完成","certification");
        innerList.add(certificationTO);

        //银行卡
        ImproveApplyInfoInitInnerDTO bankInfoDTO = new ImproveApplyInfoInitInnerDTO("放款银行卡号",resDTO.isBankCardStatus()?"Y":"N",resDTO.isBankCardStatus()?desc:"未完成","bankInfo");
        innerList.add(bankInfoDTO);

        //基本信息
        ImproveApplyInfoInitInnerDTO basicInfoTO = new ImproveApplyInfoInitInnerDTO("基本资料",resDTO.isBasicInfoStatus()?"Y":"N",resDTO.isBasicInfoStatus()?"已完成":"未完成","basicInfo");
        innerList.add(basicInfoTO);

        //联系人信息
        ImproveApplyInfoInitInnerDTO contactInfoTO = new ImproveApplyInfoInitInnerDTO("联系人",resDTO.isContactInfoStatus()?"Y":"N",resDTO.isContactInfoStatus()?"已完成":"未完成","contactInfo");
        innerList.add(contactInfoTO);

        if(sendPhoneCommunicationQueryRequest(userId,borrowCode)){
            //运营商信息
            ImproveApplyInfoInitInnerDTO comunicationTO = new ImproveApplyInfoInitInnerDTO("运营商信息",resDTO.isCommunicationLoginInfoStatus()?"Y":"N",resDTO.isCommunicationLoginInfoStatus()?"已完成":"未完成","comunication");
            innerList.add(comunicationTO);
        }

        infoInitDTO.setPersonalInfo(innerList);

        return infoInitDTO;
    }


    //判断是否是支持的
    private boolean sendPhoneCommunicationQueryRequest(final String userId,final String borrowCode){

        //查询手机号
        String phone = fundLoanMapper.queryUserPhoneByUserId(userId);
        PhoneCommunicationQueryReqDTO reqDTO = new PhoneCommunicationQueryReqDTO(userId,phone,borrowCode);
        BaseLogger.info("借款申请-发起查询通话详情入参:"+JSONObject.toJSONString(reqDTO));
        PhoneCommunicationQueryResDTO resDTO = applyHousingFundLoanDubboService.sendPhoneCommunicationQuery(reqDTO);
        BaseLogger.info("借款申请-发起查询通话详情结果"+JSONObject.toJSONString(resDTO));

        if(resDTO == null){
            throw new AppException("接口异常");
        }

        return resDTO.isSupport();

    }

    @Override
    public Object bingdingBankCardInit(String userId, String borrowCode) {

        BingdingBankCardInitDTO cardInitDTO = new BingdingBankCardInitDTO();

        //查询用户手机号
        ClientBasicInfoDTO basicInfo = fundLoanMapper.queryUserBasicInfo(userId);
        cardInitDTO.setBankReservationPhone(basicInfo.getPhone());

        BaseLogger.info("借款申请-获取银行卡列表入参:");
        FundLoanBankInfo resDTO = applyHousingFundLoanDubboService.queryFundLoanBankInfo();
        BaseLogger.info("借款申请-获取银行卡列表结果"+JSONObject.toJSONString(resDTO));

        cardInitDTO.setBankSelection(resDTO.getBankInfoList());
        cardInitDTO.setCardBindMobilePhoneNoHelpURL(financialLoanMapper.getParamsValueByKeyAndGroup("cardBindMobilePhoneNoHelpURL","wallet_vcredit_yingzt"));//预留手机号说明


        BindCardInitReqDTO initReqDTO = new BindCardInitReqDTO(userId,borrowCode);
        BaseLogger.info("借款申请-判断绑卡是否需要发送验证码入参"+JSONObject.toJSONString(initReqDTO));
        BindCardInitResDTO initResDTO = applyHousingFundLoanDubboService.bindCardInit(initReqDTO);
        BaseLogger.info("借款申请-判断绑卡是否需要发送验证码结果"+JSONObject.toJSONString(initResDTO));

        if(!initResDTO.isSuccess()){
            throw new AppException(initResDTO.getMsg());
        }


        cardInitDTO.setIsSendSMS("Y");
        if(!initResDTO.isNeedSms()){
            cardInitDTO.setIsSendSMS("N");
        }

        //设置银行卡信息
        cardInitDTO.setBankCardNo(initResDTO.getBankCardNo());
        cardInitDTO.setBankCode(initResDTO.getBankCode());

        //设置label信息
        cardInitDTO.setBankCardNoLabel("银行卡号");
        cardInitDTO.setOwnerIdentityLabel("身份证号");
        cardInitDTO.setOwnerIdentity(basicInfo.getIndentityNo());
        cardInitDTO.setOwnerNameLabel("持卡人");
        cardInitDTO.setOwnerName(basicInfo.getClientName());
        cardInitDTO.setBankNameLabel("所属银行");
        cardInitDTO.setBankDistrictLabel("所属银行地区");
        cardInitDTO.setBankReservationPhoneLabel("银行预留手机号");
        cardInitDTO.setMessageCodeLabel("验证码");

        cardInitDTO.setManageRuleLabel("");
        cardInitDTO.setManageRuleUrl("");
        cardInitDTO.setManageRuleUrlTitle("");


        return cardInitDTO;
    }

    @Override
    public Object bingdingBankcardSendSMS(CheckBingdingBankcardDTO saveDto) {
        //校验入参
        checkCheckBingdingBankcardDTO(saveDto,false);

        bingdingCardsendSms(saveDto,false);

        return new HashMap<String,String>(){{
            put("remainTime","60");
        }};
    }

    private void checkCheckBingdingBankcardDTO(CheckBingdingBankcardDTO saveDto,boolean ifCheckCode){
        if(StringUtils.isBlank(saveDto.getUserId())){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isBlank(saveDto.getBankCardNo())){
            throw new AppException("银行卡号不能为空");
        }
        if(StringUtils.isBlank(saveDto.getBankCity())){
            throw new AppException("银行所属市不能为空");
        }
        if(StringUtils.isBlank(saveDto.getBankProvince())){
            throw new AppException("银行所属省不能为空");
        }
        if(StringUtils.isBlank(saveDto.getBankReservationPhone())){
            throw new AppException("预留手机号不能为空");
        }
        if(StringUtils.isBlank(saveDto.getOwnerName())){
            throw new AppException("持卡人姓名不能为空");
        }
        if(StringUtils.isBlank(saveDto.getOwnerIdentity())){
            throw new AppException("持卡人身份证号不能为空");
        }
        if(StringUtils.isBlank(saveDto.getBorrowCode())){
            throw new AppException("借款编号不能为空");
        }
        if(ifCheckCode && StringUtils.isBlank(saveDto.getMessageCode())){
            throw new AppException("验证码不能为空");
        }

    }

    @Override
    public Object bingdingBankcardSendSMSAgain(CheckBingdingBankcardDTO saveDto) {
        //校验入参
        checkCheckBingdingBankcardDTO(saveDto,false);

        bingdingCardsendSms(saveDto,true);

        return new HashMap<String,String>(){{
            put("remainTime","60");
        }};
    }


    private void bingdingCardsendSms(CheckBingdingBankcardDTO saveDto,boolean isSend){

        YztBindCardSendSmsReqDTO reqDTO = new YztBindCardSendSmsReqDTO();
        reqDTO.setUserId(saveDto.getUserId());
        reqDTO.setBorrowCode(saveDto.getBorrowCode());
        reqDTO.setBankCardCity(saveDto.getBankCity());
        reqDTO.setBankCardNo(saveDto.getBankCardNo());
        reqDTO.setBankCardProvince(saveDto.getBankProvince());
        reqDTO.setBankCode(saveDto.getBankCode());
        reqDTO.setClientName(saveDto.getOwnerName());
        reqDTO.setIdentityNo(saveDto.getOwnerIdentity());
        reqDTO.setAsideBankPhone(saveDto.getBankReservationPhone());
        reqDTO.setSend(isSend);

        BaseLogger.info("借款申请-绑卡重发送验证码入参:"+JSONObject.toJSONString(reqDTO));
        YztBindCardSendSmsResDTO resDTO = applyHousingFundLoanDubboService.bindCardSendSms(reqDTO);
        BaseLogger.info("借款申请-绑卡重发送验证码结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }
    }

    @Override
    public Object checkBingdingBankcard(CheckBingdingBankcardDTO saveDto) {

        boolean flag = saveDto.getIsSendSMS().equals("Y")?true:false;
        //校验入参
         checkCheckBingdingBankcardDTO(saveDto,flag);

        YztBindCardReqDTO reqDTO = new YztBindCardReqDTO();

        reqDTO.setUserId(saveDto.getUserId());
        reqDTO.setBorrowCode(saveDto.getBorrowCode());
        reqDTO.setSmsCode(saveDto.getMessageCode());
        reqDTO.setBankCardCity(saveDto.getBankCity());
        reqDTO.setBankCardNo(saveDto.getBankCardNo());
        reqDTO.setBankCardProvince(saveDto.getBankProvince());
        reqDTO.setBankCode(saveDto.getBankCode());
        reqDTO.setPhone(fundLoanMapper.queryUserPhoneByUserId(saveDto.getUserId()));
        reqDTO.setClientName(saveDto.getOwnerName());
        reqDTO.setIdentityNo(saveDto.getOwnerIdentity());
        reqDTO.setNeedSms(flag);

        BaseLogger.info("借款申请-绑卡入参:"+JSONObject.toJSONString(reqDTO));
        YztBindCardResDTO resDTO = applyHousingFundLoanDubboService.bindCard(reqDTO);
        BaseLogger.info("借款申请-绑卡结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //绑卡成功后，保存绑卡记录信息
        fundLoanMapper.insertBankCardBingdingRecord(saveDto);

        //返回结果
        return new CommonCommitResultDTO("202100","恭喜您绑定成功","",financialLoanMapper.getParamsValueByKeyAndGroup("loan_common_pic_url","loan_comm")+"/4.0/bingdingCardSuccess.png");//图片地址);
    }

    @Override
    public Object certificationInit(String userId, String borrowCode) {
        return null;
    }

    @Override
    public Object certificationUploadPic(String userId, String picFile) {
        return null;
    }

    @Override
    public Object certificationSave(Object query) {
        return null;
    }

    @Override
    public Object certificationUpdate(UploadIdentitysVO uploadIdentitysVO) {

        BaseLogger.audit("公积金贷实名认证更新 图片信息开始: " + uploadIdentitysVO);
        // 验证参数
        uploadIdentitysVO.validate();
        //验证文件ID是否存在
        List<String> fileIds = new ArrayList<>();
        fileIds.add(uploadIdentitysVO.getRightSideFileId());
        fileIds.add(uploadIdentitysVO.getReverseSideFileId());
        fileIds.add(uploadIdentitysVO.getHandheldIdentityFileId());
        Boolean isExit = commonAttachmentOperate.isExitAttachementByFileIds(fileIds);
        if (!isExit) {
            BaseLogger.audit("公积金贷实名认证更新－调用文件系统－图片信息不存在");
            throw new AppException("图片信息不存在");
        }
        List<UploadFileInfoDTO> fileList = new ArrayList<UploadFileInfoDTO>();
        fileList.add(new UploadFileInfoDTO(uploadIdentitysVO.getRightSideFileId(), "200410"));
        fileList.add(new UploadFileInfoDTO(uploadIdentitysVO.getReverseSideFileId(), "200411"));
        fileList.add(new UploadFileInfoDTO(uploadIdentitysVO.getHandheldIdentityFileId(), "200412"));
        UploadIdentityPhotosDTO uploadIdentityPhotosDTO = new UploadIdentityPhotosDTO();
        uploadIdentityPhotosDTO.setFileList(fileList);
        Map<String, Object> saveMap;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("borrowCode", uploadIdentitysVO.getBorrowCode());
            map.put("fileList", fileList);
            BaseLogger.audit("___证件照上传信息_____:" + JSONObject.toJSONString(map));
            //调用更新的方法
            saveMap = applyHousingFundLoanDubboService.uploadIdentityFormUpdate(JSONObject.toJSONString(map));

        } catch (Exception e) {
            BaseLogger.error("公积金贷实名认证更新 -调用信贷系统－保存上传身份证信息异常", e);
            throw new AppException("图片上传失败");
        }
        if (null == saveMap || "0".equals(saveMap.get("isUploadIdentityFormSave"))) {
            uploadIdentityPhotosDTO.setCode("202201");
            uploadIdentityPhotosDTO.setMessage("上传失败");
            BaseLogger.audit("公积金贷实名认证更新 -调用信贷系统－服务器保存文件失败!");
        } else {
            uploadIdentityPhotosDTO.setCode("202200");
            uploadIdentityPhotosDTO.setMessage("上传成功");
            BaseLogger.audit("公积金贷实名认证更新-调用信贷系统－保存文件成功!");
        }
        return uploadIdentityPhotosDTO;
    }

    @Override
    public Object basicInfoInit(String userId, String borrowCode) {
        BasicInfoInitDTO basicInfoInitDTO = new BasicInfoInitDTO();

        BasicInfoInitReqDTO reqDTO = new BasicInfoInitReqDTO(userId,borrowCode);
        BaseLogger.info("借款申请-完善基本资料初始化入参:"+JSONObject.toJSONString(reqDTO));
        BasicInfoInitResDTO resDTO = applyHousingFundLoanDubboService.basicInfoInit(reqDTO);
        BaseLogger.info("借款申请-完善基本资料初始化结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        basicInfoInitDTO.setEducationLabel("学历");
        basicInfoInitDTO.setDefaultEducation(resDTO.getDefaultEducation());
        basicInfoInitDTO.setEducationSelection(resDTO.getEducationSelection());

        basicInfoInitDTO.setCollegeLabel("学校");
        basicInfoInitDTO.setCollege(resDTO.getSchool());
        basicInfoInitDTO.setAddressDetailLabel("详细地址");
        basicInfoInitDTO.setAddressDetail(resDTO.getAddressDetail());

        basicInfoInitDTO.setMarriageLabel("婚姻状况");
        basicInfoInitDTO.setDefaultMarriage(resDTO.getDefaultMarriage());
        basicInfoInitDTO.setMarriageSelection(resDTO.getMarriageSelection());

        basicInfoInitDTO.setAddressProvinceAndCityLabel("现住址所属省市");
        basicInfoInitDTO.setAddressProvinceAndCity(StringUtils.isBlank(resDTO.getAddressProvince())?"":resDTO.getAddressProvince()+"|"+resDTO.getAddressCity());

        basicInfoInitDTO.setAddressDistrictLabel("现住址所属区");
        basicInfoInitDTO.setAddressDistrict(resDTO.getAddressDistrict());

        return basicInfoInitDTO;
    }

    @Override
    public Object basicInfoSave(BasicInfoSaveDTO query) {

        BasicInfoSaveReqDTO reqDTO = new BasicInfoSaveReqDTO();
        BeanUtils.copyProperties(query,reqDTO);
        reqDTO.setSchool(query.getCollege());
        BaseLogger.info("借款申请-完善基本资料保存入参:"+JSONObject.toJSONString(reqDTO));
        BasicInfoSaveResDTO resDTO = applyHousingFundLoanDubboService.basicInfoSave(reqDTO);
        BaseLogger.info("借款申请-完善基本资料保存结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        return null;
    }

    @Override
    public Object contactInfoInit(String userId, String borrowCode) {

        ContactInfoInitDTO infoInitDTO = new ContactInfoInitDTO();

        ContactInfoInitReqDTO reqDTO = new ContactInfoInitReqDTO(userId,borrowCode);
        BaseLogger.info("借款申请-完善联系人信息初始化入参:"+JSONObject.toJSONString(reqDTO));
        ContactInfoInitResDTO resDTO = applyHousingFundLoanDubboService.contactInfoInit(reqDTO);
        BaseLogger.info("借款申请-完善联系人信息初始化结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //设置直接联系人信息
        infoInitDTO.setDirectContactNameLabel("直系联系人姓名");
        infoInitDTO.setDirectContactName(resDTO.getDirectContactName());
        infoInitDTO.setDirectContactPhoneLabel("直系联系人手机");
        infoInitDTO.setDirectContactPhone(resDTO.getDirectContactPhone());
        infoInitDTO.setDirectContactRelationLabel("直系联系人关系");
        infoInitDTO.setDefaultDirectContactRelation(resDTO.getDefaultDirectContactRelation());
        infoInitDTO.setDirectContactRelationSelection(resDTO.getDirectContactRelationSelection());

        //设置其他联系人信息
        infoInitDTO.setOtherContactNameLabel("其他联系人姓名");
        infoInitDTO.setOtherContactName(resDTO.getOtherContactName());
        infoInitDTO.setOtherContactPhoneLabel("其他联系人手机");
        infoInitDTO.setOtherContactPhone(resDTO.getOtherContactPhone());
        infoInitDTO.setOtherContactRelationLabel("其他联系人关系");
        infoInitDTO.setDefaultOtherContactRelation(resDTO.getDefaultOtherContactRelation());
        infoInitDTO.setOtherContactRelationSelection(resDTO.getOtherContactRelationSelection());

        return infoInitDTO;
    }

    @Override
    public Object contactInfoSave(ContactInfoSaveDTO query) {

        if(StringUtils.isBlank(query.getDirectContactName()) || StringUtils.isBlank(query.getOtherContactName())){
            throw new AppException("姓名不能为空");
        }
        if(query.getDirectContactName().length()>10 || query.getOtherContactName().length()>10){
            throw new AppException("联系人姓名过长");
        }

        ContactInfoSaveReqDTO reqDTO = new ContactInfoSaveReqDTO();
        BeanUtils.copyProperties(query,reqDTO);
        BaseLogger.info("借款申请-完善联系人信息保存入参:"+JSONObject.toJSONString(reqDTO));
        ContactInfoSaveResDTO resDTO = applyHousingFundLoanDubboService.contactInfoSave(reqDTO);
        BaseLogger.info("借款申请-完善联系人信息保存结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        return null;
    }

    @Override
    public Object communicationLoginInfoInit(String userId, String borrowCode) {
        CommunicationLoginInfoInitDTO infoInitDTO = new CommunicationLoginInfoInitDTO();
        String phone = fundLoanMapper.queryUserPhoneByUserId(userId);
        infoInitDTO.setPhone(phone);

        CommunicationInitReqDTO reqDTO = new CommunicationInitReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setPhone(phone);
        BaseLogger.info("借款申请-运营商登录初始化入参:"+JSONObject.toJSONString(reqDTO));
        CommunicationInitResDTO resDTO = applyHousingFundLoanDubboService.communicationLoginInit(reqDTO);
        BaseLogger.info("借款申请-运营商登录初始化结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //设置值
        infoInitDTO.setPhoneLabel("手机号");
        infoInitDTO.setServicePasswordLabel("服务密码");

        if(resDTO.isSms()){
            infoInitDTO.setMessageCodeLabel("验证码");
        }
        if(resDTO.isPic()){
            infoInitDTO.setPictureCodeLabel("图片验证码");
            infoInitDTO.setPictureCodeUrl(resDTO.getPicUrl());
        }

        infoInitDTO.setProtocolLabel("");
        infoInitDTO.setProtocolUrl("");
        infoInitDTO.setProtocolUrlTitle("");

        infoInitDTO.setWarmTip("温馨提示：");
        infoInitDTO.setTipContent("1.请授权本人实名认证的手机号。（使用满三个月）|2.登录成功后将收到运营商通知短信，无需回复。|3.运营商信息审核后，符合条件的用户最高可借50000元，不符合条件的用户最高可借20000元。");
        return infoInitDTO;
    }

    @Override
    public String communicationLoginInfoInitSendSMS(String userId, String borrowCode) {

        YztDynCodeReqDTO reqDTO = new YztDynCodeReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        String phone = fundLoanMapper.queryUserPhoneByUserId(userId);
        reqDTO.setPhone(phone);
        reqDTO.setFirst(Boolean.TRUE);
        BaseLogger.info("借款申请-运营商登录发送验证码入参:"+JSONObject.toJSONString(reqDTO));
        YztDynCodeResDTO resDTO = applyHousingFundLoanDubboService.sendYztDynCode(reqDTO);
        BaseLogger.info("借款申请-运营商登录发送验证码结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        return "60";
    }

    @Override
    public Object communicationProtocol(String userId, String borrowCode, String contractCode) {
        return null;
    }

    @Override
    public Object communicationLoginInfoSave(CommunicationLoginInfoSaveDTO query) {
        if(StringUtils.isBlank(query.getServicePassword())){
            throw new AppException("服务密码不能为空");
        }
        if(query.getServicePassword().length()> 20 ){
            throw new AppException("服务密码格式不合法");
        }

        CommunicationFirstLoginReqDTO reqDTO = new CommunicationFirstLoginReqDTO();
        BeanUtils.copyProperties(query,reqDTO);
        BaseLogger.info("借款申请-运营商登录入参:"+JSONObject.toJSONString(reqDTO));
        CommunicationFirstLoginResDTO resDTO = applyHousingFundLoanDubboService.communicationFirstLogin(reqDTO);
        BaseLogger.info("借款申请-运营商登录结果"+JSONObject.toJSONString(resDTO));

        String resultCode = resDTO.getCode();
        //判断返回结果
        if(resultCode.equals("-203")){  //需要图片验证码
            String picUrl = getPicUrl(query.getUserId(),query.getBorrowCode(),"1");//获取图片验证码url
            return new CommunicationLoginInfoSaveResultDTO("202702",picUrl);
        }else if(resultCode.equals("202701")){//需要短信验证码
            return new CommunicationLoginInfoSaveResultDTO("202701","");
        }else if(resultCode.equals("200")){//成功
            CommunicationLoginInfoSaveResultDTO result = new CommunicationLoginInfoSaveResultDTO("202700","");
            //是否有下一步
            if(resDTO.isNeedNext()){
                result.setIsHaveNextStep("Y");
            }else{
                result.setIsHaveNextStep("N");
            }
            return result;
        }
        else{//其他异常信息
            throw new AppException(resDTO.getMsg());
        }
    }

    @Override
    public Object communicationDetailInfoInit(String userId, String borrowCode) {
        CommunicationDetailInfoInitDTO detailInfoInitDTO = new CommunicationDetailInfoInitDTO();

        CommunicationSecondInitReqDTO reqDTO = new CommunicationSecondInitReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        String phone = fundLoanMapper.queryUserPhoneByUserId(userId);
        reqDTO.setPhone(phone);
        BaseLogger.info("借款申请-运营商详情初始化入参:"+JSONObject.toJSONString(reqDTO));
        CommunicationSecondInitResDTO resDTO = applyHousingFundLoanDubboService.communicationSecondLoginInit(reqDTO);
        BaseLogger.info("借款申请-运营商详情初始化结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        if(resDTO.isSms()){
            detailInfoInitDTO.setMessageCodeLabel("短信验证码");
            detailInfoInitDTO.setWaitTime("60");
        }

        if(resDTO.isPic()){
            detailInfoInitDTO.setPictureCodeLabel("图片验证码");
            detailInfoInitDTO.setPictureCodeUrl(resDTO.getPicUrl());
        }

        detailInfoInitDTO.setPhoneLabel("手机号");
        detailInfoInitDTO.setPhone(phone);

        detailInfoInitDTO.setWarmTip("温馨提示");
        detailInfoInitDTO.setTipContent("请重新获取一次短信验证码用于此步骤验证");


        return detailInfoInitDTO;
    }

    @Override
    public String communicationDetailInfoInitSendSMS(String userId, String borrowCode) {

        YztDynCodeReqDTO reqDTO = new YztDynCodeReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        String phone = fundLoanMapper.queryUserPhoneByUserId(userId);
        reqDTO.setPhone(phone);
        reqDTO.setFirst(Boolean.FALSE);
        BaseLogger.info("借款申请-运营商详情发送验证码入参:"+JSONObject.toJSONString(reqDTO));
        YztDynCodeResDTO resDTO = applyHousingFundLoanDubboService.sendYztDynCode(reqDTO);
        BaseLogger.info("借款申请-运营商详情发送验证码结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        return "60";
    }

    @Override
    public Object communicationDetailInfoSave(CommunicationDetailInfoSaveDTO obj) {

        CommunicationSecondLoginReqDTO reqDTO = new CommunicationSecondLoginReqDTO();
        BeanUtils.copyProperties(obj,reqDTO);
        BaseLogger.info("借款申请-运营商详情入参:"+JSONObject.toJSONString(reqDTO));
        CommunicationSecondLoginResDTO resDTO = applyHousingFundLoanDubboService.communicationSecondLogin(reqDTO);
        BaseLogger.info("借款申请-运营商详情结果"+JSONObject.toJSONString(resDTO));

        String resultCode = resDTO.getCode();
        //判断返回结果
        if(resultCode.equals("-203")){  //需要图片验证码
            String picUrl = getPicUrl(obj.getUserId(),obj.getBorrowCode(),"2");//获取图片验证码url
            return new CommunicationLoginInfoSaveResultDTO("202702",picUrl);
        }else if(resultCode.equals("202701")){//需要短信验证码
            return new CommunicationLoginInfoSaveResultDTO("202701","");
        }else if(resultCode.equals("200")){//成功
            CommunicationLoginInfoSaveResultDTO result = new CommunicationLoginInfoSaveResultDTO("202700","");
            return result;
        }
        else{//其他异常信息
            throw new AppException(resDTO.getMsg());
        }
    }

    @Override
    public Object loanApplyContract(String userId) {
        return null;
    }

    @Override
    public Object loanApplyInfoShow(String userId, String borrowCode) {
        LoanApplyInfoShowDTO showDTO = new LoanApplyInfoShowDTO();



        return showDTO;
    }

    @Override
    public Object queryDistrictList() {

        //暂时省市都配在ftl文件中
        Object jsonStr = JSONObject.parse(readJSONTemplate("/template/V4.0/provinceAndCity.ftl"));
        return jsonStr;
    }

    private String readJSONTemplate(String templateFullPath) {
        return template.process(templateFullPath, new HashMap<String, String>());
    }


    @Override
    public Object queryCreditResult(String userId, String borrowCode) {

        QueryOrderBasicInfoReqDTO reqDTO = new QueryOrderBasicInfoReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        BaseLogger.info("借款申请-查询授信结果状态入参:"+JSONObject.toJSONString(reqDTO));
        QueryOrderBasicInfoResDTO resDTO = applyHousingFundLoanDubboService.queryOrderBasicInfo(reqDTO);
        BaseLogger.info("借款申请-查询授信结果状态结果"+JSONObject.toJSONString(resDTO));

        String orderStatus = resDTO.getOrderStatus();
        if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C) || orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_A)){
            return new CommonCommitResultDTO("202002","授信处理中","处理中提示信息","");
        }
        if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_F) || orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_R)){
            return new CommonCommitResultDTO("202001","授信失败","错误原因","");
        }
        if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_S)){
            return new CommonCommitResultDTO("202000","授信成功","","");
        }
        return null;
    }

    private Map<String,String> buildLabelAndValueMap(String label, String value){
        Map<String,String> map = new HashMap<>();
        map.put("label",label);
        map.put("value",value);
        return map;
    }

    @Override
    public Object orderStatusChanged(String userId) {

        //调用查询账单状态是否有更新的接口
        QueryOrderStatusIsChangedReqDTO reqDTO = new QueryOrderStatusIsChangedReqDTO();
        reqDTO.setUserId(userId);
        BaseLogger.info("借款页-判断是否有状态更新入参userId:"+userId);
        QueryOrderStatusIsChangedResDTO resDTO = applyHousingFundLoanDubboService.queryOrderStatusIsChanged(reqDTO);
        BaseLogger.info("借款页-判断是否有状态更新结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.isSuccess()){
            throw new AppException(resDTO.getMsg());
        }

        final String isChanged = resDTO.isChanged()?"Y":"N";
        final String borrowCode = resDTO.getBorrowCode();
        return new HashMap<String,String>(){{
            put("isChanged",isChanged);
            put("borrowCode",borrowCode);
        }};
    }


    @Override
    public Object changeOrderStatus(String userId,String borrowCode) {

        UpdateChangedOrderStatusReqDTO reqDTO = new UpdateChangedOrderStatusReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setBorrowCode(borrowCode);
        BaseLogger.info("借款页-更新账单状态入参"+JSONObject.toJSONString(reqDTO));
        UpdateChangedOrderStatusResDTO resDTO = applyHousingFundLoanDubboService.updateChangedOrderStatus(reqDTO);
        BaseLogger.info("借款页-更新账单状态结果"+JSONObject.toJSONString(resDTO));

        return null;
    }


    @Override
    public Object improveApplyInfoCommit(ConfirmLoanApplyReqDTO reqDTO,HttpServletRequest request) {

        ImproveApplyInfoCommitResultDTO resultDTO = new ImproveApplyInfoCommitResultDTO();

        //校验
        checkUserIdAndBorrowCode(reqDTO.getUserId(),reqDTO.getBorrowCode());

        String ipAddress = "";
        try{
            ipAddress = IPTools.getIpAddress(request);
        }catch (Exception e){
            BaseLogger.error("获取IP地址异常",e);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            reqDTO.setEquipmentIp(ipAddress);
        }

        //完善资料提交接口
        BaseLogger.info("借款申请-完善资料后的提交申请放款入参"+JSONObject.toJSONString(reqDTO));
        ConfirmLoanApplyResDTO resDTO = applyHousingFundLoanDubboService.confirmLoanApply(reqDTO);
        BaseLogger.info("借款申请-完善资料后的提交申请放款结果"+JSONObject.toJSONString(resDTO));

        if(!(resDTO.isSuccess() || resDTO.isProcess())){
            throw new AppException(resDTO.getMsg());
        }

        //提交成功后，组装返回结果
        resultDTO.setLoanPicUrl("");
        resultDTO.setLoanBigTip("借款申请已提交");
        resultDTO.setLoanSmallTip("最快将于一个工作日内完成审核");

        List<Map<String,String>> applyInfo = new ArrayList<>();

        //处理字段

        applyInfo.add(buildLabelAndValueMap("姓名", UserInfoCommonUtils.operateName(resDTO.getClientName())));//姓名
        applyInfo.add(buildLabelAndValueMap("身份证号",UserInfoCommonUtils.operateIdNo(resDTO.getIdentityNo())));//身份证号
        applyInfo.add(buildLabelAndValueMap("借款金额","￥"+ resDTO.getLoanAmount()));//借款金额
        applyInfo.add(buildLabelAndValueMap("收款银行",resDTO.getBankName()));//收款银行
        applyInfo.add(buildLabelAndValueMap("借款账户",UserInfoCommonUtils.operateBankcardNo(resDTO.getBankCardNo())));//借款账户
        applyInfo.add(buildLabelAndValueMap("起止时间",resDTO.getStartEndTime()));//起止时间
        applyInfo.add(buildLabelAndValueMap("首次还款日",resDTO.getFirstRepayTime()));//首次还款日
        applyInfo.add(buildLabelAndValueMap("借款期限",resDTO.getRepaymentPeriod()+"个月(期)"));//借款期限
        applyInfo.add(buildLabelAndValueMap("借款方式","等额本息"));//借款方式


        //还款信息
        resultDTO.setApplyDetailInfo(applyInfo);
        resultDTO.setRepayDetailLabel("还款详情");
        resultDTO.setRepayDetail("点击查看");

        //设置还款汇总信息
        resultDTO.setRepaymentDate("每月"+resDTO.getFirstRepayTime()+"日");
        resultDTO.setFirstRepayment("¥"+"金额"+"（"+resDTO.getFirstRepayTime()+"）");
        resultDTO.setMonthlyPrincipalAndInterestLabel("每月应还本息");
        resultDTO.setMonthlyPrincipalAndInterest("借满"+resDTO.getRepaymentPeriod()+"个月总利息￥"+resDTO.getInterestTotal());

        List<String> titleList = new ArrayList<>();
        titleList.add("还款时间");
        titleList.add("应还本金");
        titleList.add("应还利息及费用");
        resultDTO.setGeneratedRepaymentDetailLabels(titleList);

        //获取还款计划
        List<FundLoanRepaymentDTO> returnRepayList = new ArrayList<>();
        List<RepaymentDetailVo> repayList = resDTO.getRepaymentPlanList();
        if(CollectionUtils.isNotEmpty(repayList)){
            for(RepaymentDetailVo vo:repayList){
                FundLoanRepaymentDTO returnRepay = new FundLoanRepaymentDTO();
                returnRepay.setShouldPrincipal("￥"+vo.getShouldPrincipal());
                returnRepay.setShouldInterest("￥"+vo.getShouldInterest());
                returnRepay.setRepaymentDate(vo.getRepaymentDate());
                returnRepayList.add(returnRepay);
            }
        }

        //设置还款计划
        resultDTO.setGeneratedRepaymentDetailList(returnRepayList);

        return resultDTO;
    }


    @Override
    public Object loanContractList(String userId, String borrowCode) {
        return null;
    }

    @Override
    public Object productIntroduce(String productId) {

        return null;
    }

    private void checkUserIdAndBorrowCode(String userId, String borrowCode){
        if(StringUtils.isBlank(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isBlank(borrowCode)){
            throw new AppException("借款编号不能为空");
        }
    }

    public static void main(String[] args) {
        System.out.println("start....");
        new Thread(){
            public void run() {
                long currentTime = System.currentTimeMillis();
                while(System.currentTimeMillis()-currentTime<6000){
                    try{
                        this.sleep(1000);
                    }catch (Exception e){
                    }
                    System.out.println("ssssssssssss");
                }
            }
        }.start();
        System.out.println("end....");
    }
    @Override
    public Object applyHouseFundLoanIntention(IntentionPersonalInformationVo vo, String code) {
        ParamValidUtil.validatorNotEmpty(vo.getName());
        ParamValidUtil.validatorNotEmpty(vo.getPhone());
        ParamValidUtil.validatorNotEmpty(vo.getCity());
        ParamValidUtil.validatorNotEmpty(vo.getCityCode());
        ParamValidUtil.validatorNotEmpty(vo.getPaymentSituation());
        ParamValidUtil.validatorNotEmpty(code);
//        ParamValidUtil.validatorNotEmpty(vo.getChannelId());
//        ParamValidUtil.validatorNotEmpty(vo.getChannelSubId());
        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getChannelId()) && vo.getChannelId().length() > 100) {
            throw new AppException("参数异常");
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getChannelSubId()) && vo.getChannelSubId().length() > 100) {
            throw new AppException("参数异常");
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getOpenParam()) && vo.getOpenParam().length() > 100) {
            throw new AppException("参数异常");
        }

        try {
            vo.setChannelId(URLDecoder.decode(vo.getChannelId(), "utf-8"));
            vo.setChannelSubId(URLDecoder.decode(vo.getChannelSubId(), "utf-8"));
            vo.setOpenParam(URLDecoder.decode(vo.getOpenParam(), "utf-8"));
        } catch (Exception e) {
            BaseLogger.error(e.getMessage());
        }

        if (applyHousingFundLoanDubboService.existUserHouseFundIntentionInfo(vo.getPhone())) {//用户已申请过
            throw new AppException("您的借款申请已经提交，请勿重复提交！");
        }

        RegisterParamDTO registerParamDTO = new RegisterParamDTO();
        registerParamDTO.setChannelNo(vo.getChannelId());
        mbUserInviteService.registerByHouseFundLoanIntention(vo.getPhone(), code, registerParamDTO, vo.getName());

        return applyHousingFundLoanDubboService.applyHouseFundLoanIntention(vo);
    }

    @Override
    public Object updateHouseFundLoanIntention(IntentionPersonalInformationVo vo) {
        ParamValidUtil.validatorPhone(vo.getPhone());
        ParamValidUtil.validatorNotEmpty(vo.getName());
        ParamValidUtil.validatorNotEmpty(vo.getCity());
        ParamValidUtil.validatorNotEmpty(vo.getCityCode());
        ParamValidUtil.validatorNotEmpty(vo.getPaymentSituation());

        //选填参数均为空时，不执行更新操作
        if (StringUtils.isEmpty(vo.getIdCard()) && StringUtils.isEmpty(vo.getHouseFundAccount()) && StringUtils.isEmpty(vo.getHouseFundPassword())) {
            return null;
        }
        return applyHousingFundLoanDubboService.updateHouseFundLoanIntentionInfo(vo);
    }

    @Override
    public Object updateHouseFundLoanIntentionForSSD(IntentionPersonalInformationVo vo, String code) {
        ParamValidUtil.validatorPhone(vo.getPhone());
        ParamValidUtil.validatorNotEmpty(vo.getName());
        ParamValidUtil.validatorNotEmpty(vo.getCity());
        ParamValidUtil.validatorIdentityNo(vo.getIdCard());
        ParamValidUtil.validatorNotEmpty(vo.getHouseFundAccount());
        ParamValidUtil.validatorNotEmpty(vo.getHouseFundPassword());
        ParamValidUtil.validatorNotEmpty(code);

        try {
            vo.setName(URLDecoder.decode(vo.getName(), "utf-8"));
            vo.setPhone(URLDecoder.decode(vo.getPhone(), "utf-8"));
            vo.setCity(URLDecoder.decode(vo.getCity(), "utf-8"));
            vo.setIdCard(URLDecoder.decode(vo.getIdCard(), "utf-8"));
            vo.setHouseFundAccount(URLDecoder.decode(vo.getHouseFundAccount(), "utf-8"));
            vo.setHouseFundPassword(URLDecoder.decode(vo.getHouseFundPassword(), "utf-8"));
        } catch (Exception e) {
            BaseLogger.error(e.getMessage());
        }
        RegisterParamDTO registerParamDTO = new RegisterParamDTO();
        registerParamDTO.setChannelNo(vo.getChannelId());
        mbUserInviteService.registerByHouseFundLoanIntention(vo.getPhone(), code, registerParamDTO, vo.getName());

        boolean exitsApply = applyHousingFundLoanDubboService.existUserHouseFundIntentionInfo(vo.getPhone());
        if (exitsApply) {//存在，更新
            applyHousingFundLoanDubboService.updateHouseFundLoanIntentionInfoForSSD(vo);
        } else {//不存在，新增
            applyHousingFundLoanDubboService.applyHouseFundLoanIntention(vo);
        }
        return null;
    }

    @Override
    public String getPicUrl(String userId, String borrowCode, String type) {

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(borrowCode) || StringUtils.isBlank(type)){
            throw new AppException("参数不能为空");
        }

        YztPicCodeReqDTO reqDTO = new YztPicCodeReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setPhone(fundLoanMapper.queryUserPhoneByUserId(userId));
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setFirst(type.equals("1")?true:false);
        BaseLogger.info("运营商-获取图片验证码入参：userId,borrowCode,type"+userId+";"+borrowCode+";"+type);
        YztPicCodeResDTO resDTO = applyHousingFundLoanDubboService.sendYztPicCode(reqDTO);
        BaseLogger.info("运营商-获取图片验证码结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.isSuccess()){
            throw new AppException(resDTO.getMsg());
        }

        return resDTO.getPicUrl();
    }

    @Override
    public String getFundLoanContract(String borrowCode) {
        ContractReqDTO reqDTO = new ContractReqDTO();
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setContractType("loan");

        BaseLogger.info("借款合同查询入参："+JSONObject.toJSONString(reqDTO));
        ContractResDTO resDTO = applyHousingFundLoanDubboService.queryYztContract(reqDTO);
        BaseLogger.info("借款合同查询出参："+JSONObject.toJSONString(resDTO));

        if(!resDTO.isSuccess()){
            throw new AppException(resDTO.getMsg());
        }
        return resDTO.getContents();
    }

    @Override
    public String getFundLoanConsult(String borrowCode) {
        ContractReqDTO reqDTO = new ContractReqDTO();
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setContractType("consult");

        BaseLogger.info("咨询协议入参："+JSONObject.toJSONString(reqDTO));
        ContractResDTO resDTO = applyHousingFundLoanDubboService.queryYztContract(reqDTO);
        BaseLogger.info("咨询协议出参："+JSONObject.toJSONString(resDTO));

        if(!resDTO.isSuccess()){
            throw new AppException(resDTO.getMsg());
        }
        return resDTO.getContents();
    }

    @Override
    public String getFundLoanReference(String borrowCode) {
        ContractReqDTO reqDTO = new ContractReqDTO();
        reqDTO.setBorrowCode(borrowCode);
        reqDTO.setContractType("credit_reference");

        BaseLogger.info("征信授权协议入参："+JSONObject.toJSONString(reqDTO));
        ContractResDTO resDTO = applyHousingFundLoanDubboService.queryYztContract(reqDTO);
        BaseLogger.info("征信授权协议出参："+JSONObject.toJSONString(resDTO));

        if(!resDTO.isSuccess()){
            throw new AppException(resDTO.getMsg());
        }
        return resDTO.getContents();
    }

    @Override
    public Object applyHouseFundLoanIntention(IntentionPersonalInformationVo vo, String code, String inviteCode, String activityCode) {
        ParamValidUtil.validatorNotEmpty(vo.getName());
        ParamValidUtil.validatorPhone(vo.getPhone());
//        ParamValidUtil.validatorNotEmpty(vo.getCity());
//        ParamValidUtil.validatorNotEmpty(vo.getCityCode());
        ParamValidUtil.validatorNotEmpty(code);
        ParamValidUtil.validatorIdentityNo(vo.getIdCard());

        ParamValidUtil.validatorInviteCode(inviteCode);
        ParamValidUtil.validatorActivityCode(activityCode);

        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getChannelId()) && vo.getChannelId().length() > 100) {
            throw new AppException("参数异常");
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getChannelSubId()) && vo.getChannelSubId().length() > 100) {
            throw new AppException("参数异常");
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(vo.getOpenParam()) && vo.getOpenParam().length() > 100) {
            throw new AppException("参数异常");
        }

        if (userQueryMapper.existUserByPhone(vo.getPhone())) {
            throw new AppException(601, "您已是融桥宝用户|请直接登录申请");
        }

//        if (applyHousingFundLoanDubboService.existUserHouseFundIntentionInfo(vo.getPhone())) {//用户已申请过
//            throw new AppException("您的借款申请已经提交，请勿重复提交！");
//        }

        RegisterParamDTO registerParamDTO = new RegisterParamDTO();
        registerParamDTO.setChannelNo(vo.getChannelId());
        registerParamDTO.setInviteCode(inviteCode);
        registerParamDTO.setActivityCode(activityCode);
        Map registerMap = mbUserInviteService.registerByHouseFundLoanIntention(vo.getPhone(), code, registerParamDTO, vo.getName());

        applyHousingFundLoanDubboService.applyHouseFundLoanIntention(vo);


        return registerMap;
    }
    



	@Override
	public Object applyCreditPersonalInfoCommitV2(FundInfoSaveDTO fundInfoSaveDTO,
			HttpServletRequest request) {
		//校验
        checkFundInfo(fundInfoSaveDTO);
        //入参
        HousingInfoSaveReqDTO reqDTO = new HousingInfoSaveReqDTO();

        String ipAddress = "";
        try{
            ipAddress = IPTools.getIpAddress(request);
        }catch (Exception e){
            BaseLogger.error("获取IP地址异常",e);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            reqDTO.setEquipmentIp(ipAddress);
        }

        //获取贷款订单信息
        BeanUtils.copyProperties(fundInfoSaveDTO,reqDTO);

        BaseLogger.info("白领专享公积金信息保存入参:"+JSONObject.toJSONString(reqDTO));
        HousingfundResponseVO housingfundResponseVO = applyHousingFundLoanDubboService.housingInfoSaveV2(reqDTO);
        BaseLogger.info("白领专享公积金信息保存结果"+JSONObject.toJSONString(housingfundResponseVO));
        
        //处理结果信息返回给前端
        CommonCommitResultDTO commonCommitResultDTO = null;
        if(HousingfundResponseVO.CREATE_TASK_SUCCESS.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("200000","资料提交成功","将于一个工作日内完成审核","");
        }else if(HousingfundResponseVO.CREATE_TASK_ERROR.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("600000",housingfundResponseVO.getMessage(),"","");
        }else if(HousingfundResponseVO.CREATE_TASK_WAIT_CODE.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("206001","请输入验证码","","");
        	commonCommitResultDTO.setTaskId(housingfundResponseVO.getTaskId());
        	commonCommitResultDTO.setImgBytes(housingfundResponseVO.getImageBytes());
        }else if(HousingfundResponseVO.CREATE_TASK_ERROR_CODE.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("206002","验证码错误","","");
        	commonCommitResultDTO.setTaskId(housingfundResponseVO.getTaskId());
        	commonCommitResultDTO.setImgBytes(housingfundResponseVO.getImageBytes());
        }else{
        	throw new AppException(housingfundResponseVO.getMessage());
        }

        return commonCommitResultDTO;
	}





	@Override
	public Object refreshValidateCode(FundInfoSaveDTO fundInfoSaveDTO,
			HttpServletRequest request) {
		//校验
        checkFundInfo(fundInfoSaveDTO);
        //入参
        HousingInfoSaveReqDTO reqDTO = new HousingInfoSaveReqDTO();

        String ipAddress = "";
        try{
            ipAddress = IPTools.getIpAddress(request);
        }catch (Exception e){
            BaseLogger.error("获取IP地址异常",e);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            reqDTO.setEquipmentIp(ipAddress);
        }

        //获取贷款订单信息
        BeanUtils.copyProperties(fundInfoSaveDTO,reqDTO);

        BaseLogger.info("白领专享刷新公积金登录验证码入参:"+JSONObject.toJSONString(reqDTO));
        HousingfundResponseVO housingfundResponseVO = applyHousingFundLoanDubboService.refreshValidateCode(reqDTO);
        BaseLogger.info("白领专享刷新公积金登录验证码结果"+JSONObject.toJSONString(housingfundResponseVO));
		
      //处理结果信息返回给前端
        CommonCommitResultDTO commonCommitResultDTO = null;
        if(HousingfundResponseVO.CREATE_TASK_SUCCESS.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("200000","资料提交成功","将于一个工作日内完成审核","");
        }else if(HousingfundResponseVO.CREATE_TASK_WAIT_CODE.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("206011","请输入验证码","","");
        	commonCommitResultDTO.setTaskId(housingfundResponseVO.getTaskId());
        	commonCommitResultDTO.setImgBytes(housingfundResponseVO.getImageBytes());
        }else if(HousingfundResponseVO.CREATE_TASK_ERROR.equals(housingfundResponseVO.getCode())){
        	commonCommitResultDTO = new CommonCommitResultDTO("600000","系统繁忙,请稍后尝试","","");
        }else{
        	throw new AppException("系统繁忙,请稍后尝试!");
        }

        return commonCommitResultDTO;
	}
}