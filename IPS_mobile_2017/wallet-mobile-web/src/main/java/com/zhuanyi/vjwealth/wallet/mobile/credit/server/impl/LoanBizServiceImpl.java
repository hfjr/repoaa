package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.order.vo.PersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.webservice.IApplyForCreditDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaApplyForCreditDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ICreditRouteDubboService;
import com.zhuanyi.vjwealth.loan.product.vo.ProductCityInfoVo;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductCityInfoDubboService;
import com.zhuanyi.vjwealth.loan.util.ValidateUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.IPTools;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IApplyForCreditService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanBizService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundLoanShareStatisticsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.WjTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.ApplyForCreditMapper;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:信贷业务层实现类
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
@Service
public class LoanBizServiceImpl implements ILoanBizService{
    @Autowired
    private ICreditRouteDubboService creditRouteDubboService;
    @Autowired
    private IApplyForCreditDubboService applyForCreditDubboService;
    @Autowired
    private ICanaApplyForCreditDubboService canaApplyForCreditDubboService;
    @Autowired
    private IProductCityInfoDubboService productCityInfoDubboService;
    @Autowired
    private IApplyForCreditService applyForCreditService;
//    @Autowired
//    private IUserInfoQueryService userInfoQueryService;
    @Autowired
    private ApplyForCreditMapper applyForCreditMapper;

    /**
     * 页面路由
     * @param userId
     * @param productTypeCode
     * @return
     */
    public Object loanApplicationInit(String userId,String productTypeCode) {
        return creditRouteDubboService.loanApplicationInit(userId,productTypeCode);
    }
    /**
     *申请额度初始化
     * @param userId
     * @param productTypeCode
     * @return
     */
    public Object creditApplicationInit(String userId,String productTypeCode){
        Map<String,Object> resultMap=null;
        if(BizCodeType.LOAN_CANA_PRODUCT_ID.getCode().equals(productTypeCode)){
            resultMap=canaApplyForCreditDubboService.creditApplicationInit(userId,productTypeCode);
        }else{
            resultMap=applyForCreditDubboService.creditApplicationInit(userId,productTypeCode);
        }
        Integer isTieBankCard  = applyForCreditService.queryIsTieBankCardById(userId);
        if (isTieBankCard>0) {
            resultMap.put("isTieBankCard", "true");
            resultMap.put("tieBankCardMessage","");
        } else {
            resultMap.put("isTieBankCard", "false");
            resultMap.put("tieBankCardMessage", "您未绑定银行卡，请先绑卡|返回|绑卡");
        }
        return resultMap;
    }

    /**
     *  10.申请额度-完善个人信息初始化
     * @param userId
     * @param productTypeCode
     * @return
     */
    @Override
    public Object improvePersonalInformationIniti(String userId, String productTypeCode) {
//        ImprovePersonalInformationVo resultDTo = applyForCreditDubboService.improvePersonalInformationInit(userId,productTypeCode);
//        BasicPersonalInformationVo basicInfo = resultDTo.getBasicPersonalInformation();
//        if (basicInfo != null) {
//            //同步用户信息
//            if (StringUtils.isEmpty(basicInfo.getName())) {
//                UserInfoDTO userInfoDTO = null;//userInfoQueryService.queryUserInfoById(userId);
//                basicInfo.setName(StringUtil.replaceClientNameString(userInfoDTO.getRealName()));
//                basicInfo.setIdentity(StringUtil.replaceIndentityNoString(userInfoDTO.getIndentityNo()));
//                ClientUserInfoDTO userInfo = new ClientUserInfoDTO();
//                try {
//                    BeanUtils.copyProperties(userInfo, userInfoDTO);
//                    userInfo.setOrderId(resultDTo.getBorrowCode());
//                    if("M".equals(userInfoDTO.getSex())){
//                        basicInfo.setGender("男");
//                        userInfo.setSex("1");
//                    }else{
//                        basicInfo.setGender("女");
//                        userInfo.setSex("0");
//                    }
//                    applyForCreditDubboService.synOrderUserInfo(userInfo);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//            //获取银行卡列表  // FIXME: 2016/10/10  获取银行卡列表 锦囊与公积金贷
//            List<BankCardsVo> bankCardsVos;
//            DetailPersonalInformationVo detailPersonalInformationVo=resultDTo.getDetailPersonalInformation();
//            if(BizCodeType.LOAN_PRODUCT_ID.getCode().equals(productTypeCode)){
//                 bankCardsVos = applyForCreditService.queryBindCardList(userId);
//            } else {
//                bankCardsVos = applyForCreditService.queryBindCardListV2(userId);
//            }
//            if(detailPersonalInformationVo!=null&&detailPersonalInformationVo.getBankCards().isEmpty()){
//                for(BankCardsVo bankCardsVo:bankCardsVos) {
//                    bankCardsVo.setBankName(MessageFormat.format("{0}(尾号{1})",bankCardsVo.getBankName(),
//                            StringUtil.getCardNoFourAfterFour(bankCardsVo.getBankCardNumber())));
//                    detailPersonalInformationVo.getBankCards().add(bankCardsVo);
//                }
//            }else{
//                for(BankCardsVo bankCardsVo:bankCardsVos) {
//                    for(BankCardsVo loanBankCardsVo:detailPersonalInformationVo.getBankCards()){
//                        if(bankCardsVo.getBankCardNumber().equals(loanBankCardsVo.getBankCardNumber())){
//                            loanBankCardsVo.setBankCode(bankCardsVo.getBankCode());
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//        return resultDTo;
        
        return null;
    }

    /**
     * 10.1 申请额度-完善个人信息初始化-公积金初始化
     * @param userId
     * @param cityCode
     * @param borrowCode
     * @return
     */
    @Override
    public Object fundAccountIniti(String userId, String cityCode, String borrowCode) {
        return applyForCreditDubboService.fundAccountInit(userId, cityCode, borrowCode);
    }

    /**
     * 10.2 申请额度-完善个人信息初始化-社保初始化
     * @param userId
     * @param cityCode
     * @param borrowCode
     * @return
     */
    @Override
    public Object socialSecurityAccountIniti(String userId, String cityCode, String borrowCode) {
        return applyForCreditDubboService.socialSecurityAccountInit(userId, cityCode, borrowCode);
    }

    /**
     * 申请额度-完善个人信息确认（保存）
     *
     * @param request
     * @param query
     * @return
     */
    @Override
    public Object improvePersonalInformationSave(HttpServletRequest request, PersonalInformationVo query) {
        query.setChannelId(BizCodeType.LOAN_CHANNEL_TYPE_WALLET.getCode());
        //查询认证状态

        String certificationStatus = applyForCreditService.queryCertificationStatus(query.getUserId());
        query.setCertificationStatus(certificationStatus==null?"1":certificationStatus);
        //绑卡状态 // FIXME: 2016/10/10 绑卡状态 锦囊与公积金贷
        WjTradeAccountCardDTO wjTradeAccountCardDTO;
        if(BizCodeType.LOAN_PRODUCT_ID.getCode().equals(query.getProductTypeCode())){
             wjTradeAccountCardDTO = applyForCreditService.queryBindCardStatus(query.getUserId(), query.getBankCardNumber());
        } else {
             wjTradeAccountCardDTO = applyForCreditService.queryBindCardStatusV2(query.getUserId(), query.getBankCardNumber());
        }
        //设置银行卡信息
        ProductCityInfoVo productCityInfoVo = productCityInfoDubboService.selectProductCityByCityCode(query.getProductTypeCode(), query.getWorkCity());
        if (StringUtils.isEmpty(query.getWorkProvince())) {
            if (productCityInfoVo != null) {
                query.setCardBankProvince(productCityInfoVo.getProvinceCode());
                query.setWorkProvince(productCityInfoVo.getProvinceCode());
            }
        } else {
            query.setCardBankProvince(query.getWorkProvince());
        }
        query.setCardBankCity(query.getWorkCity());
        if (wjTradeAccountCardDTO != null) {
            query.setBindCardStatus(wjTradeAccountCardDTO.getBindCardStatus());
            query.setAsideBankPhone(wjTradeAccountCardDTO.getAsideBankPhone());
            query.setCardBankCode(wjTradeAccountCardDTO.getLoanBankCode());
            query.setCardBankName(wjTradeAccountCardDTO.getBankName());
        }
        query.setProductId(query.getProductTypeCode());
        String ipAddress=null;
        try {
            ipAddress=IPTools.getIpAddress(request);
        } catch (IOException e) {
            e.printStackTrace();
            BaseLogger.error("获取IP地址异常",e);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            query.setEquipmentIp(ipAddress);
        }
        //query.setAgentIp(WebUtils.getRemoteIP(request));
        if(BizCodeType.LOAN_CANA_PRODUCT_ID.getCode().equals(query.getProductTypeCode())){
            return canaApplyForCreditDubboService.improvePersonalInformationSave(query);
        }
        return applyForCreditDubboService.improvePersonalInformationSave(query);
    }

    @Override
    public void checkPersonalInformation(HttpServletRequest request,PersonalInformationVo query) throws AppException {
        if (query == null || StringUtils.isEmpty(query.getUserId())) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(query.getBankCardNumber())) {
            throw new AppException("银行卡号不能为空");
        }
        if (StringUtils.isEmpty(query.getWorkCity())) {
            throw new AppException("工作城市不能为空");
        }
        if (StringUtils.isEmpty(query.getFundAccount())) {
            throw new AppException("公积金账户不能为空");
        }
        if (!ValidateUtil.isFundAccount(query.getFundAccount())) {
            throw new AppException("公积金账户格式无效:请至少输入两位字符");
        }
        if (StringUtils.isEmpty(query.getFundAccountPassword())) {
            throw new AppException("公积金账户密码不能为空");
        }
       /* if (!ValidateUtil.isPassword(query.getFundAccountPassword())) {
            throw new AppException("公积金账户密码无效:请输入字母或数字");
        }*/
        if(BizCodeType.LOAN_PRODUCT_ID.getCode().equals(query.getProductTypeCode())){
            if (StringUtils.isEmpty(query.getSocialSecurityAccount())) {
                throw new AppException("社保账户不能为空");
            }
            if (!ValidateUtil.isSocialSecurityAccount(query.getSocialSecurityAccount())) {
                throw new AppException("社保账户格式无效:请至少输入两位字符");
            }
            if (StringUtils.isEmpty(query.getSocialSecurityAccountPassword())) {
                throw new AppException("社保账户密码不能为空");
            }
        }
        /*if (!ValidateUtil.isPassword(query.getSocialSecurityAccountPassword())) {
            throw new AppException("社保账户密码无效:请输入字母或数字");
        }*/
        if (!ValidateUtil.isAmount(query.getMonthlyIncome())) {
            throw new AppException("金额输入有误:超出最大输入位数");
        }
    }

    @Override
    public Object cleanData(String phone) {
        return creditRouteDubboService.cleanData(phone);
    }

    @Override
    public Object fundLoanShareStatistics(FundLoanShareStatisticsDTO dto) throws AppException {
        //重要参数均为空时，不保存数据
        if(StringUtils.isEmpty(dto.getP1())&&StringUtils.isEmpty(dto.getP2())&&StringUtils.isEmpty(dto.getS())){
            return null;
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(dto.getP1()) && dto.getP1().length() > 100) {
            throw new AppException("参数异常");
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(dto.getP2()) && dto.getP2().length() > 100) {
            throw new AppException("参数异常");
        }
//        if (org.apache.commons.lang.StringUtils.isBlank(dto.getInviteCode()) || dto.getInviteCode().length() > 100) {
//            throw new AppException("参数异常");
//        }
//        if (org.apache.commons.lang.StringUtils.isBlank(dto.getActivityCode()) && dto.getActivityCode().length() > 100) {
//            throw new AppException("参数异常");
//        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(dto.getS()) && dto.getS().length() > 100) {
            throw new AppException("参数异常");
        }

        try {
            dto.setP1(URLDecoder.decode(dto.getP1(), "utf-8"));
            dto.setP2(URLDecoder.decode(dto.getP2(), "utf-8"));
            dto.setS(URLDecoder.decode(dto.getS(),"utf-8"));
        } catch (Exception e) {
            BaseLogger.error(e.getMessage());
        }
        applyForCreditMapper.insertFundLoanShareInfo(dto);
        return null;
    }
}
