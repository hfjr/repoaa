package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ConfirmLoanApplyReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.order.vo.BankCardsVo;
import com.zhuanyi.vjwealth.loan.order.vo.IntentionPersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.PersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.UploadIdentitysVO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeBindBankCardInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.RechargeInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.WjTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditInvestigationWayDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公积金贷款(白领专享)
 * Created by wangzf on 16/10/14.
 */
public interface IFundLoanService {

    /**
     * 4.白领专享，申请额度个人信息保存
     * @param query
     * @param request
     * @return
     * @since 4.0
     */
    Object applyCreditPersonalInfoCommit(FundInfoSaveDTO query, HttpServletRequest request);

    /**
     * 获取白领专享公积金支持的城市列表
     * @return
     */
    Object queryCityList();

    /**
     * 公积金填写初始化
     * @param borrowCode
     * @return
     */
    Object fundAccountIniti(String userId,String borrowCode,String cityCode,String isInit);

    /**
     *
     * @param userId
     * @param borrowCode
     * @return
     */
    Object grantLoanFormIniti(String userId, String borrowCode);

    /**
     * 借款-公积金贷-初始化路由接口
     * @param userId
     * @param
     * @return
     */
    Object checkApplyStatus(String userId,boolean queryBasicInfo);

    /**
     *
     * @return
     */
    Object creditIntroduce();

    /**
     * @param userId
     * @return
     */
    CreditInvestigationWayDTO queryMatchedProductList(String userId,String loanMinAmount,String loanMaxAmount);

    /**
     *
     * @param userId
     * @return
     */
    Object queryLoanProcessCount(String userId);

    /**
     *
     * @param userId
     * @return
     */
    Object queryLoanRecordList(String userId);

    /**
     *
     * @param userId
     * @param borrowCode
     * @return
     */
    Object queryLoanRecordDetail(String userId, String borrowCode,String isChanged);

    /**
     *
     * @param userId
     * @return
     */
    Object loanApplyInit(String userId,String loanAmount);

    /**
     *
     * @param
     * @return
     */
    Object loanApplySave(FundLoanApplySaveDTO applySaveDTO);

    /**
     *
     * @param userId
     * @param borrowCode
     * @return
     */
    Object improveApplyInfo(String userId, String borrowCode);

    /**
     *
     * @param userId
     * @param borrowCode
     * @return
     */
    Object bingdingBankCardInit(String userId, String borrowCode);

    /**
     *
     * @return
     */
    Object bingdingBankcardSendSMS(CheckBingdingBankcardDTO reqDto);

    /**
     * 绑定银行卡
     * @return
     */
    Object checkBingdingBankcard(CheckBingdingBankcardDTO reqDto);

    /**
     *
     * @param userId
     * @param loanAmount
     * @return
     */
    Object repaymentTrial(String userId,String borrowCode, String loanAmount,String loanPeriod);

    /**
     *17. 完善资料-实名认证-初始化接口
     * @param userId
     * @param borrowCode
     * @return
     */
    Object certificationInit(String userId, String borrowCode);

    /**
     *18. 完善资料-实名认证-图片上传接口
     * @param userId
     * @param picFile
     * @return
     */
    Object certificationUploadPic(String userId, String picFile);

    /**
     *19.完善资料-实名认证-保存接口
     * @param query
     * @return
     */
    Object certificationSave(Object query);

    /**
     *19.1 完善资料-实名认证-更新接口
     * @param uploadIdentitysVO
     * @return
     */
    Object certificationUpdate(UploadIdentitysVO uploadIdentitysVO);

    /**
     *20.完善资料-基本资料-初始化接口
     * @param userId
     * @param borrowCode
     * @return
     */
    Object basicInfoInit(String userId, String borrowCode);

    /**
     *21.完善资料-基本资料-保存接口
     * @param query
     * @return
     */
    Object basicInfoSave(BasicInfoSaveDTO query);

    /**
     *22.完善资料-联系人-初始化接口
     * @param userId
     * @param borrowCode
     * @return
     */
    Object contactInfoInit(String userId, String borrowCode);

    /**
     *23.完善资料-联系人-保存接口
     * @param query
     * @return
     */
    Object contactInfoSave(ContactInfoSaveDTO query);

    /**
     *24.完善资料-运营商-初始化登录【step1】接口
     * @param userId
     * @param borrowCode
     * @return
     */
    Object communicationLoginInfoInit(String userId, String borrowCode);

    /**
     *24.1 完善资料-运营商-初始化登录【step1】接口-发送验证码
     * @param userId
     * @param borrowCode
     * @return
     */
    String communicationLoginInfoInitSendSMS(String userId, String borrowCode);

    /**
     *25.完善资料-运营商-运营商授权协议接口
     * @param userId
     * @param borrowCode
     * @param contractCode
     * @return
     */
    Object communicationProtocol(String userId, String borrowCode, String contractCode);

    /**
     *26.完善资料-运营商-登录保存【step1】接口
     * @param query
     * @return
     */
    Object communicationLoginInfoSave(CommunicationLoginInfoSaveDTO query);

    /**
     *27. 完善资料-运营商-初始化详单获取【step2】接口
     * @param userId
     * @param borrowCode
     * @return
     */
    Object communicationDetailInfoInit(String userId, String borrowCode);

    /**
     *27.1  完善资料-运营商-初始化详单获取【step2】接口--发送验证码
     * @param userId
     * @param phone
     * @return
     */
    String communicationDetailInfoInitSendSMS(String userId, String phone);

    /**
     *28. 完善资料-运营商-初始化详单保存【step2】接口
     * @param obj
     * @return
     */
    Object communicationDetailInfoSave(CommunicationDetailInfoSaveDTO obj);

    /**
     *29. 完善资料-【借款合同协议】接口
     * @param userId
     * @return
     */
    Object loanApplyContract(String userId);

    /**
     *30. 完善资料-【确认借款】接口
     * @param userId
     * @param borrowCode
     * @return
     */
    Object loanApplyInfoShow(String userId, String borrowCode);

    /**
     *31. 银行卡所属地区及联系人地址地区列表
     * @return
     */
    Object queryDistrictList();

    /**
     * 贷款产品初始化页面
     * @param userId
     * @return
     */
    Object loanProductInit(String userId);

    /**
     *
     * @param userId
     * @param borrowCode
     * @return
     */
    Object queryCreditResult(String userId, String borrowCode);

    /**
     * 绑卡重发验证码
     * @param reqDto
     * @return
     */
    Object bingdingBankcardSendSMSAgain(CheckBingdingBankcardDTO reqDto);

    /**
     * 借款订单状态变化提示接口
     * @param userId
     * @return
     */
    Object orderStatusChanged(String userId);

    /**
     *
     * @param userId
     * @return
     */
    Object changeOrderStatus(String userId,String borrowCode);

    /**
     * 完善资料后，提交保存（发给小赢审核放款）
     * @return
     */
    Object improveApplyInfoCommit(ConfirmLoanApplyReqDTO reqDTO,HttpServletRequest request);

    /**
     *
     * @param productId
     * @return
     */
    Object productIntroduce(String productId);
    Object applyHouseFundLoanIntention(IntentionPersonalInformationVo vo, String code);

    Object updateHouseFundLoanIntention(IntentionPersonalInformationVo vo);

    Object updateHouseFundLoanIntentionForSSD(IntentionPersonalInformationVo vo, String code);

    Object loanContractList(String userId, String borrowCode);

    /**
     * 获取图片验证码url
     * @param userId
     * @param borrowCode
     * @param type
     * @return
     */
    String getPicUrl(String userId, String borrowCode, String type);

    /**
     * 借款合同
     * @param borrowCode
     */
    String getFundLoanContract(String borrowCode);

    /**
     * 咨询协议
     * @param borrowCode
     * @return
     */
    String getFundLoanConsult(String borrowCode);

    /**
     * 个人授权协议
     * @param borrowCode
     * @return
     */
    String getFundLoanReference(String borrowCode);

    Object applyHouseFundLoanIntention(IntentionPersonalInformationVo vo, String code, String inviteCode, String activityCode);
    
    
    /**
     * 4.白领专享，申请额度个人信息保存,需判断该城市是否需要用户输入验证码
     * @param query
     * @param request
     * @return
     * @since 4.1
     */
    Object applyCreditPersonalInfoCommitV2(FundInfoSaveDTO query, HttpServletRequest request);
    
    /**
     * 4.申请额度-公积金信息保存接口-公积金登录验证码刷新
     * @param query
     * @param request
     * @return
     * @since 4.1
     */
    Object refreshValidateCode(FundInfoSaveDTO query, HttpServletRequest request);
}
