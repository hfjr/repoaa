package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.client.dto.ClientBasicInfoDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CheckApplyStatusReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CheckApplyStatusResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.QueryOrderBasicInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.webservice.IApplyHousingFundLoanDubboService;
import com.zhuanyi.vjwealth.loan.sd.webservice.ISDDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.DateCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.IPTools;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IHouseFundLoanThirdMarketService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanOrderStatusConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.MarketHouseFundApplyInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.SDHouseFundLoanApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RegisterParamDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;

@Service
public class HouseFundLoanThirdMarketServiceImpl implements IHouseFundLoanThirdMarketService {
    @Remote
    private IMBUserInviteService mbUserInviteService;

    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Autowired
    private IApplyHousingFundLoanDubboService applyHousingFundLoanDubboService;

    @Autowired
    //@Reference(version = "1.0.0")
    private ISDDubboService sdDubboService;

    @Override
    public Object sendSMSNotice(String phone) {
        ParamValidUtil.validatorPhone(phone);
        if (userQueryMapper.existUserByPhone(phone)) {
            throw new AppException(601, "您已是融桥宝用户|请直接登录");
        }

        return mbUserInviteService.sendSMSNoticeForHouseFundLoanIntention(phone);
    }

    @Override
    public Object applyCredit(MarketHouseFundApplyInfoDTO dto) {
        ParamValidUtil.validatorPhone(dto.getPhone());
        ParamValidUtil.validatorNotEmpty(dto.getCode());
        ParamValidUtil.validatorNotEmpty(dto.getCityCode());
        ParamValidUtil.validatorNotEmpty(dto.getLoginType());
        ParamValidUtil.validatorNotEmpty(dto.getHouseFundAccount());

        if (userQueryMapper.existUserByPhone(dto.getPhone())) {
            throw new AppException(601, "您已是融桥宝用户|请直接登录申请");
        }

        RegisterParamDTO registerParamDTO = new RegisterParamDTO();
        registerParamDTO.setChannelNo(dto.getChannelId());
        Map userInfo = mbUserInviteService.registerByHouseFundLoanIntention(dto.getPhone(), dto.getCode(), registerParamDTO, dto.getName());

        //公积金贷二期 申请授信额度 接口入口
        ClientBasicInfoDTO clientBasicInfoDTO = new ClientBasicInfoDTO();
        clientBasicInfoDTO.setChannelId(dto.getChannelId());
        clientBasicInfoDTO.setPhone(dto.getPhone());
        clientBasicInfoDTO.setId(userInfo.get("userId") + "");
//        clientBasicInfoDTO.setChannelClientName(dto.getName());

        CheckApplyStatusReqDTO checkApplyStatusReqDTO = new CheckApplyStatusReqDTO(clientBasicInfoDTO);

        BaseLogger.info("调用信贷系统：创建预授信订单 开始reqDTO = " + JSON.toJSONString(checkApplyStatusReqDTO));
        CheckApplyStatusResDTO resDTO = applyHousingFundLoanDubboService.checkApplyStatus(checkApplyStatusReqDTO);
        BaseLogger.info("调用信贷系统：创建预授信订单 结束reqDTO = " + JSON.toJSONString(resDTO));
        if (!resDTO.getCode().equals("200")) {
            throw new AppException(resDTO.getMsg());
        }

        HousingInfoSaveReqDTO housingInfoSaveReqDTO = new HousingInfoSaveReqDTO();
        housingInfoSaveReqDTO.setUserId(userInfo.get("userId") + "");
        housingInfoSaveReqDTO.setBorrowCode(resDTO.getBorrowCode());
        housingInfoSaveReqDTO.setCityCode(dto.getCityCode());
        housingInfoSaveReqDTO.setFundAccount(dto.getHouseFundAccount());
        housingInfoSaveReqDTO.setFundAccountPassword(dto.getHouseFundPassword());
        housingInfoSaveReqDTO.setLoginType(dto.getLoginType());
        housingInfoSaveReqDTO.setIdCard(dto.getIdCard());
        housingInfoSaveReqDTO.setOtherParam(dto.getOtherParam());
        housingInfoSaveReqDTO.setEquipmentIp("0");
        housingInfoSaveReqDTO.setEquipmentNumber("0");
        housingInfoSaveReqDTO.setOsType("0");
        housingInfoSaveReqDTO.setOsVersion("0");
        housingInfoSaveReqDTO.setRealName(dto.getRealName());

        BaseLogger.info("调用信贷系统：公积金信息保存及爬取任务创建 开始reqDTO = " + JSON.toJSONString(housingInfoSaveReqDTO));
        HousingInfoSaveResDTO housingInfoSaveResDTO = applyHousingFundLoanDubboService.housingInfoSave(housingInfoSaveReqDTO);
        BaseLogger.info("调用信贷系统：公积金信息保存及爬取任务创建 结束resDTO = " + JSON.toJSONString(housingInfoSaveResDTO));
        if (!housingInfoSaveResDTO.getCode().equals("200")) {
            throw new AppException(housingInfoSaveResDTO.getMsg());
        }
        return housingInfoSaveResDTO;
    }

    @Override
    public Object sdLoanApply(SDHouseFundLoanApplyDTO loanApplyDTO, HttpServletRequest request) {
        ParamValidUtil.validatorPhone(loanApplyDTO.getPhone());
        ParamValidUtil.validatorNotEmpty(loanApplyDTO.getHouseFundAccount(), "公积金账号不能为空");
        ParamValidUtil.validatorNotEmpty(loanApplyDTO.getLoginType(), "登录类型不能为空");
        ParamValidUtil.validatorNotEmpty(loanApplyDTO.getCode(), "短信验证码不能为空");
        ParamValidUtil.validatorNotEmpty(loanApplyDTO.getSdOrderId(), "闪贷订单号不能为空");
        ParamValidUtil.validatorNotEmpty(loanApplyDTO.getCityCode(), "城市编码不能为空");

        //查询用户ID
        String userId = userQueryMapper.queryUserIdByPhone(loanApplyDTO.getPhone());

        if (!StringUtils.isEmpty(userId)) {
//            BaseLogger.info("调用信贷系统：查询订单信息 开始 userId= " + userId);
            QueryOrderBasicInfoResDTO resDTO = applyHousingFundLoanDubboService.queryHouseFundOrderInfoByUserId(userId);
//            BaseLogger.info("调用信贷系统：查询订单信息 结束 resDTO = " + JSON.toJSONString(resDTO));
            if (resDTO != null) {
                if (resDTO.getOrderStatus().equals(LoanOrderStatusConstant.ORDER_STATUS_C_F) || resDTO.getOrderStatus().equals(LoanOrderStatusConstant.ORDER_STATUS_G_F)) {//授信失败或放款失败后90天内不能再申请
                    if (DateCommonUtils.addDay(resDTO.getUpdateTime(), 90).after(new Date())) {
                        throw new AppException(601, "您已提交过申请，请直接登录查看");
                    }
                } else if (!resDTO.getOrderStatus().equals(LoanOrderStatusConstant.ORDER_STATUS_F)) {
                    throw new AppException(601, "您已提交过申请，请直接登录查看");
                }
            }
        }

        RegisterParamDTO registerParamDTO = new RegisterParamDTO();
        registerParamDTO.setChannelNo(StringUtils.isBlank(loanApplyDTO.getChannelId()) ? "shandai_gjjd" : loanApplyDTO.getChannelId());
        Map registerMap = mbUserInviteService.registerByHouseFundLoanIntention(loanApplyDTO.getPhone(), loanApplyDTO.getCode(), registerParamDTO, loanApplyDTO.getName());
        userId = registerMap.get("userId") + "";

        ClientBasicInfoDTO clientBasicInfoDTO = new ClientBasicInfoDTO();
        clientBasicInfoDTO.setChannelId(loanApplyDTO.getChannelId());
        clientBasicInfoDTO.setId(userId);
        clientBasicInfoDTO.setPhone(loanApplyDTO.getPhone());

        CheckApplyStatusReqDTO checkApplyStatusReqDTO = new CheckApplyStatusReqDTO(clientBasicInfoDTO);

        BaseLogger.info("调用信贷系统：创建预授信订单 开始 reqDTO = " + JSON.toJSONString(checkApplyStatusReqDTO));
        CheckApplyStatusResDTO applyStatusResDTO = applyHousingFundLoanDubboService.checkApplyStatus(checkApplyStatusReqDTO);
        BaseLogger.info("调用信贷系统：创建预授信订单 结束 reqDTO = " + JSON.toJSONString(applyStatusResDTO));
        if (!applyStatusResDTO.getCode().equals("200")) {
            throw new AppException(applyStatusResDTO.getMsg());
        }

        HousingInfoSaveReqDTO housingInfoSaveReqDTO = new HousingInfoSaveReqDTO();
        String ipAddress = "";
        try {
            ipAddress = IPTools.getIpAddress(request);
        } catch (Exception e) {
            BaseLogger.error("获取IP地址异常", e);
        }
        if (!StringUtils.isEmpty(ipAddress)) {
            housingInfoSaveReqDTO.setEquipmentIp(ipAddress);
        }
        housingInfoSaveReqDTO.setUserId(userId);
        housingInfoSaveReqDTO.setBorrowCode(applyStatusResDTO.getBorrowCode());
        housingInfoSaveReqDTO.setCityCode(loanApplyDTO.getCityCode());
        housingInfoSaveReqDTO.setFundAccount(loanApplyDTO.getHouseFundAccount());
        housingInfoSaveReqDTO.setFundAccountPassword(loanApplyDTO.getHouseFundPassword());
        housingInfoSaveReqDTO.setLoginType(loanApplyDTO.getLoginType());
        housingInfoSaveReqDTO.setIdCard(loanApplyDTO.getIdCard());
        housingInfoSaveReqDTO.setOtherParam(loanApplyDTO.getOtherParam());
        housingInfoSaveReqDTO.setEquipmentNumber(registerMap.get("uuid") + "");
        housingInfoSaveReqDTO.setOsType(loanApplyDTO.getOsType());
        housingInfoSaveReqDTO.setOsVersion(loanApplyDTO.getOsVersion());
        housingInfoSaveReqDTO.setRealName(loanApplyDTO.getRealName());

        BaseLogger.info("调用信贷系统：公积金信息保存及爬取任务创建 开始 reqDTO = " + JSON.toJSONString(housingInfoSaveReqDTO));
        HousingInfoSaveResDTO housingInfoSaveResDTO = applyHousingFundLoanDubboService.housingInfoSave(housingInfoSaveReqDTO);
        BaseLogger.info("调用信贷系统：公积金信息保存及爬取任务创建 结束 resDTO = " + JSON.toJSONString(housingInfoSaveResDTO));
        if (!housingInfoSaveResDTO.getCode().equals("200")) {
            throw new AppException(housingInfoSaveResDTO.getMsg());
        }

        BaseLogger.info("调用信贷系统：绑定闪贷订单信息 开始");
        Object resultObj = sdDubboService.bindingSDOrderInfo(loanApplyDTO.getSdOrderId(), applyStatusResDTO.getBorrowCode());
        BaseLogger.info("调用信贷系统：绑定闪贷订单信息 结束 resDTO = " + JSON.toJSONString(resultObj));
        return null;
    }

    @Override
    public Object sdSendSMSNotice(String phone) {
        ParamValidUtil.validatorPhone(phone);

        String userId = userQueryMapper.queryUserIdByPhone(phone);
        if (!StringUtils.isEmpty(userId)) {
            QueryOrderBasicInfoResDTO resDTO = applyHousingFundLoanDubboService.queryHouseFundOrderInfoByUserId(userId);
            if (resDTO == null) {
            } else if (resDTO.getOrderStatus().equals(LoanOrderStatusConstant.ORDER_STATUS_C_F) || resDTO.getOrderStatus().equals(LoanOrderStatusConstant.ORDER_STATUS_G_F)) {//授信失败或放款失败后90天内不能再申请
                if (DateCommonUtils.addDay(resDTO.getUpdateTime(), 90).after(new Date())) {
                    throw new AppException(601, "您已提交过申请，请直接登录查看");
                }
            } else if (!resDTO.getOrderStatus().equals(LoanOrderStatusConstant.ORDER_STATUS_F)) {
                throw new AppException(601, "您已提交过申请，请直接登录查看");
            }
        }

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("remainTime", mbUserInviteService.sendSMSNoticeForHouseFundLoanIntention(phone));
        return returnMap;
    }
}
