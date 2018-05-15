package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;


import com.alibaba.fastjson.JSONObject;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.ProductCityInfoDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.QueryOrderBasicInfoReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessCountResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ProductCityInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.QueryOrderBasicInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.webservice.IApplyHousingFundLoanDubboService;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductInfoDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.LoanProductsDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.CreditExistResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ProductsInfoResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.DateCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.MoneyCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanProductService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanOrderStatusConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanProductIdConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CityBaseDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.CityDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundLoanCityListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.PledgeInvestInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IPledgeInvestMapper;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款产品筛选
 * Created by wangzf on 16/10/14.
 */
@Service
public class LoanProductServiceImpl implements ILoanProductService {

    @Autowired
    private IApplyWageAdvanceDubboService applyWageAdvanceDubboService;

    @Autowired
    private IProductInfoDubboService productInfoDubboService;

    @Autowired
    private IPledgeInvestMapper pledgeInvestMapper;

    @Autowired
    private FinancialLoanMapper financialLoanMapper;

    @Autowired
    private IApplyHousingFundLoanDubboService applyHousingFundLoanDubboService;

    @Override
    public CreditInvestigationWayDTO queryMatchedProductList(String userId,String loanMinAmount,String loanMaxAmount) {

        if(org.apache.commons.lang.StringUtils.isBlank(userId) ){
            throw new AppException("用户不合法");
        }
        if( org.apache.commons.lang.StringUtils.isBlank(loanMinAmount)  || !NumberUtils.isNumber(loanMinAmount)){
            throw new AppException("金额不合法");
        }
        if( org.apache.commons.lang.StringUtils.isBlank(loanMaxAmount)  || !NumberUtils.isNumber(loanMaxAmount)){
            throw new AppException("金额不合法");
        }

        //新建返回实体
        CreditInvestigationWayDTO creditInvestigationWayDTO = new CreditInvestigationWayDTO();

        //返回产品列表
        List<BaseCreditWayDTO> creditWays = new ArrayList<>();

        //调用信贷征信列表接口
        try{

            BaseLogger.info("调用信贷获取产品列表");
            ProductsInfoResDTO productsInfoResDTO =  productInfoDubboService.creditInvestigationWay();
            BaseLogger.info("调用信贷获取产品列表结果"+ JSONObject.toJSONString(productsInfoResDTO));

            if(org.apache.commons.lang.StringUtils.equals("200",productsInfoResDTO.getCode())){
                //获取信贷产品列表
                List<LoanProductsDTO>  loanProductsDTOs =  productsInfoResDTO.getProductsInfoList();

                String iconUrl = financialLoanMapper.getParamsValueByKeyAndGroup("loan_common_pic_url","loan_comm");//图片地址

                for(LoanProductsDTO loanPreoductsDTO : loanProductsDTOs){

                    BaseCreditWayDTO baseCreditWayDTO = null;

                    // 获取PFI,工资先享征信方式产品
                    if(BizCodeType.LOAN_WA_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
                        baseCreditWayDTO = builderPFICreditWayDTO(userId,loanMinAmount,loanMaxAmount,loanPreoductsDTO,iconUrl);
                    }
                    // 获取公积金（白领专享）征信方式产品
                    else if(BizCodeType.LOAN_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId()) ){
                        baseCreditWayDTO  =  builderFundCreditWayDTO(loanMinAmount,loanMaxAmount,loanPreoductsDTO,iconUrl,userId);
                    }
                    // 获取理财征信征信方式产品
                    else if(BizCodeType.LOAN_LTB_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
                        baseCreditWayDTO = builderPledgeInvestCreditWayDTO(userId,loanMinAmount,loanMaxAmount,loanPreoductsDTO,iconUrl);

                    }
                    // 获取工资易贷（房抵贷）产品
                    else if(BizCodeType.LOAN_HOUSE_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
                        baseCreditWayDTO = builderHouseCreditWayDTO(loanPreoductsDTO,loanMinAmount,loanMaxAmount,iconUrl,loanPreoductsDTO.getProductId());
                    }

                    // 获取工资易贷（精英房抵贷）产品
                    else if(BizCodeType.LOAN_JY_HOUSE_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
                        baseCreditWayDTO = builderHouseCreditWayDTO(loanPreoductsDTO,loanMinAmount,loanMaxAmount,iconUrl,loanPreoductsDTO.getProductId());

                    }

                    // 获取工资易贷（赎楼贷）产品
                    else if(BizCodeType.LOAN_S_HOUSE_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
                        baseCreditWayDTO = builderHouseCreditWayDTO(loanPreoductsDTO,loanMinAmount,loanMaxAmount,iconUrl,loanPreoductsDTO.getProductId());

                    }

                    //如果符合筛选条件，则将实体放入列表
                    if(baseCreditWayDTO!=null){
                        creditWays.add(baseCreditWayDTO);
                    }

                }
            }else {
                BaseLogger.warn("获取信贷系统增信方式信息,失败");
                throw new AppException("增信方式获取失败");
            }

        }catch (Exception e){
            BaseLogger.error("获取信贷系统增信方式信息,失败",e);
            throw new AppException("增信方式获取失败");
        }


        // 排序
        Collections.sort(creditWays, new Comparator<BaseCreditWayDTO>(){
            /*
             * 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(BaseCreditWayDTO o1, BaseCreditWayDTO o2) {

                //按照征信方式的权重值进行升序排列
                if(o1.getOrderingWeightValue() > o2.getOrderingWeightValue()){
                    return 1;
                }
                if(o1.getOrderingWeightValue() == o2.getOrderingWeightValue()){
                    return 0;
                }
                return -1;
            }
        });

        //设置返回结果
        creditInvestigationWayDTO.setCreditWayIntroduceDesc("请选择增信方式，不同的方式借款利率有所差别");
        creditInvestigationWayDTO.setCreditWays(creditWays);
        creditInvestigationWayDTO.setNoMatchProductTip("没有对应的贷款产品推荐|温馨提示：试试降低或提高贷款产品金额");
        return creditInvestigationWayDTO;
    }





    /**
     * 筛选工资易贷
     * @param loanPreoductsDTO
     * @return
     */
    private HouseCreditWayDTO builderHouseCreditWayDTO(LoanProductsDTO loanPreoductsDTO,String loanMinAmount,String loanMaxAmount,String iconUrl,String productId) {
        HouseCreditWayDTO houseCreditWayDTO = null;

        //工资易贷
        String minAmount = loanPreoductsDTO.getMinPrincipal();
        String maxAmount = loanPreoductsDTO.getMaxPrincipal();
        //2.判断需要借的钱是否在可借钱的范围内
        if(null != loanPreoductsDTO && (compareAmountBetweenStr(loanMinAmount,minAmount,maxAmount) || compareAmountBetweenStr(loanMaxAmount,minAmount,maxAmount))){

            houseCreditWayDTO = new HouseCreditWayDTO();
            houseCreditWayDTO.setName("房抵贷");
            houseCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(true));
            houseCreditWayDTO.setIconUrl(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_HOUSE.getValue()+".png");
            houseCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());

            String minAmountDesc = new BigDecimal(minAmount).compareTo(new BigDecimal(10000))>=0?MoneyCommonUtils.divideW(minAmount)+"W":minAmount;
            String maxAmountDesc = new BigDecimal(maxAmount).compareTo(new BigDecimal(10000))>=0?MoneyCommonUtils.divideW(maxAmount)+"W":maxAmount;
            houseCreditWayDTO.setLoanAmountDesc(minAmountDesc +"~"+maxAmountDesc);

            houseCreditWayDTO.setLoanRate(loanPreoductsDTO.getRate());
            houseCreditWayDTO.setLoanRateDesc("月费率");
            BaseLogger.audit("构建白领专享方式 结束 ,fundCreditWayDTO ： " + JSONObject.toJSONString(houseCreditWayDTO));

            if(BizCodeType.LOAN_HOUSE_PRODUCT_ID.getCode().equals(productId)){
                houseCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_HOUSE.getValue());
                houseCreditWayDTO.setConditionDesc("需要房产抵押");
                houseCreditWayDTO.setOtherTip("最高房产价值7成");
                houseCreditWayDTO.setTipIcon(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_HOUSE.getValue()+"_tip.png");
            }
            else if(BizCodeType.LOAN_JY_HOUSE_PRODUCT_ID.getCode().equals(productId)){
                houseCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_JY_HOUSE.getValue());
                houseCreditWayDTO.setConditionDesc("需要房产抵押，公积金信用");
                houseCreditWayDTO.setOtherTip("最高房产价值5成");
                houseCreditWayDTO.setTipIcon(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_JY_HOUSE.getValue()+"_tip1.png");
            }
            else if(BizCodeType.LOAN_S_HOUSE_PRODUCT_ID.getCode().equals(productId)){
                houseCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_S_HOUSE.getValue());
                houseCreditWayDTO.setConditionDesc("需要房产买卖信息");
                houseCreditWayDTO.setOtherTip("满足房屋买卖过程中的银行按揭垫资需求");
                houseCreditWayDTO.setTipIcon(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_S_HOUSE.getValue()+"_tip.png");
            }

        }

        return houseCreditWayDTO;


    }


    /**
     * 根据贷款产品构建理财征信方式
     * @param loanPreoductsDTO
     * @return
     */
    private PledgeInvestCreditWayDTO builderPledgeInvestCreditWayDTO(String userId,String loanMinAmount,String loanMaxAmount,LoanProductsDTO loanPreoductsDTO,String iconUrl) {
        PledgeInvestCreditWayDTO pledgeInvestCreditWayDTO = null;

        //查询用户的可借金额和投资总额
        PledgeInvestInitiDTO pii = pledgeInvestMapper.queryPledgeInvestInitiV2(userId,loanMinAmount);

        if(pii == null){
            return pledgeInvestCreditWayDTO;
        }
        BigDecimal canBorrowAmount = new BigDecimal(MoneyCommonUtils.handlerMoneyStr(pii.getCanBorrowAmount()));
        //投资金额须大于500元
        if(null != pii && new BigDecimal(loanMinAmount).compareTo(new BigDecimal("500")) >= 0 && new BigDecimal(loanMinAmount).compareTo(canBorrowAmount) <= 0) {

            pledgeInvestCreditWayDTO = new PledgeInvestCreditWayDTO();
            pledgeInvestCreditWayDTO.setCanBorrowAmount(pii.getCanBorrowAmount());

            //查询订单
            List<InverstOrder> inverstOrders =  builderInverstOrder(userId,loanMinAmount);
            pledgeInvestCreditWayDTO.setInverstOrders(builderInverstOrder(userId,loanMinAmount));

            pledgeInvestCreditWayDTO.setName("流通宝");
            pledgeInvestCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(true));
            pledgeInvestCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_PLEDGE_INVEST.getValue());
            pledgeInvestCreditWayDTO.setIconUrl(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_PLEDGE_INVEST.getValue()+".png");
            pledgeInvestCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());
            pledgeInvestCreditWayDTO.setConditionDesc("需要理财资产");
            pledgeInvestCreditWayDTO.setLoanAmountDesc("500");
            pledgeInvestCreditWayDTO.setLoanRate(loanPreoductsDTO.getRate());
            pledgeInvestCreditWayDTO.setLoanRateDesc("费率");
            pledgeInvestCreditWayDTO.setInverstOrderIntroduceDesc("请选择一个在投理财产品");
            pledgeInvestCreditWayDTO.setInverstOrderTotalDesc("共("+inverstOrders.size()+")笔可用作担保");
            pledgeInvestCreditWayDTO.setInverstOrders(inverstOrders);

            BaseLogger.audit("构建理财征信方式 结束 ,pledgeInvestCreditWayDTO ： " + JSONObject.toJSONString(pledgeInvestCreditWayDTO));
        }



        return pledgeInvestCreditWayDTO;
    }

    /**
     * 查询满足条件的理财订单
     * @param userId
     * @return
     */
    private List<InverstOrder> builderInverstOrder(String userId,String borrowAmount) {

        List<InverstOrder> list = new ArrayList<InverstOrder>();
        if(pledgeInvestMapper.queryFinacialRewardSendInfo(userId)){
            //查询用户可借金额对应的投资记录信息
            list = pledgeInvestMapper.queryPledgeInvestInitiInnerOrderListLimitV2(userId,borrowAmount);
        }else{
            list = pledgeInvestMapper.queryPledgeInvestInitiInnerOrderListV2(userId,borrowAmount);
        }
        return list;
    }


    /**
     * 筛选白领专享
     * @return
     */
    private FundCreditWayDTO builderFundCreditWayDTO(String loanMinAmount,String loanMaxAmount,LoanProductsDTO loanPreoductsDTO,String iconUrl,String userId) {

        FundCreditWayDTO fundCreditWayDTO = null;

        //白领专享的校验

        String minAmount = loanPreoductsDTO.getMinPrincipal();
        String maxAmount = loanPreoductsDTO.getMaxPrincipal();
        //2.判断需要借的钱是否在工资先享的可借钱的范围内
        if(null != loanPreoductsDTO && (compareAmountBetweenStr(loanMinAmount,minAmount,maxAmount) || compareAmountBetweenStr(loanMaxAmount,minAmount,maxAmount) )){

            fundCreditWayDTO = new FundCreditWayDTO();
            fundCreditWayDTO.setName("公积金贷款");
            fundCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(true));
            fundCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_FUND.getValue());
            fundCreditWayDTO.setIconUrl(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_FUND.getValue()+".png");
            fundCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());
            fundCreditWayDTO.setConditionDesc("需要公积金信用");

            String minAmountDesc = new BigDecimal(minAmount).compareTo(new BigDecimal(10000))>=0?MoneyCommonUtils.divideW(minAmount)+"W":minAmount;
            String maxAmountDesc = new BigDecimal(maxAmount).compareTo(new BigDecimal(10000))>=0?MoneyCommonUtils.divideW(maxAmount)+"W":maxAmount;
            fundCreditWayDTO.setLoanAmountDesc(minAmountDesc +"~"+maxAmountDesc);

            fundCreditWayDTO.setLoanRate(loanPreoductsDTO.getRate());
            fundCreditWayDTO.setLoanRateDesc("最低费率");

            //查询当前贷款状态及时间
            QueryOrderBasicInfoReqDTO reqDTO = new QueryOrderBasicInfoReqDTO();
            reqDTO.setUserId(userId);
            BaseLogger.info("白领专享查询状态及更新时间入参userId:"+userId);
            QueryOrderBasicInfoResDTO resDTO = applyHousingFundLoanDubboService.queryOrderBasicInfo(reqDTO);
            BaseLogger.info("白领专享查询状态及更新时间结果"+JSONObject.toJSONString(resDTO));

            if(!resDTO.isSuccess()){
                throw new AppException(resDTO.getMsg());
            }

            String orderStatus = resDTO.getOrderStatus();
            //根据不同状态，展示不同描述
            if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_R)){  //不可点
                fundCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(false));
                fundCreditWayDTO.setConditionDesc("还款中，详情查看我的借款");
                fundCreditWayDTO.setLoanAmountDesc(resDTO.getLoanAmount());
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C)){  //进度
                fundCreditWayDTO.setConditionDesc("公积金信用资料审核中");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_R)){ //进度 -- 公积金录入
                fundCreditWayDTO.setConditionDesc("公积金信用资料审核失败");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_A)){ //进度
                fundCreditWayDTO.setConditionDesc("公积金信用资料审核中");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_F)){
                if(DateCommonUtils.addDay(resDTO.getUpdateTime(),90).after(new Date())){//不可点
                    fundCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(false));
                    fundCreditWayDTO.setConditionDesc("暂时不符合公积金贷条件");
                }else{                                                                        //公积金录入
                    fundCreditWayDTO.setConditionDesc("需要公积金信用");
                }
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_C_S)){  //借钱
                fundCreditWayDTO.setConditionDesc("额度申请成功");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_Z)){ //借钱
                fundCreditWayDTO.setConditionDesc("资料完善中");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_Z_P)){ //进度
                fundCreditWayDTO.setConditionDesc("实名认证中");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_Z_F)){ //上传图片页
                fundCreditWayDTO.setConditionDesc("实名认证失败");
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_G)){ //进度
                fundCreditWayDTO.setConditionDesc("放款审核中");
                fundCreditWayDTO.setLoanAmountDesc(resDTO.getLoanAmount());
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_G_F)){
                if(DateCommonUtils.addDay(resDTO.getUpdateTime(),90).after(new Date())){   //不可点
                    fundCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(false));
                    fundCreditWayDTO.setConditionDesc("暂时不符合公积金贷条件");
                    fundCreditWayDTO.setLoanAmountDesc(resDTO.getLoanAmount());
                }else{                                                                        //公积金录入
                    fundCreditWayDTO.setConditionDesc("借款失败，请修改资料后重新申请");
                    fundCreditWayDTO.setLoanAmountDesc(resDTO.getLoanAmount());
                }
            }else if(orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_A) || orderStatus.equals(LoanOrderStatusConstant.ORDER_STATUS_F)){

            }else{
                fundCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(false));
                fundCreditWayDTO.setConditionDesc("还款中，详情查看我的借款");
                fundCreditWayDTO.setLoanAmountDesc(resDTO.getLoanAmount());
            }

            BaseLogger.audit("构建白领专享方式 结束 ,fundCreditWayDTO ： " + JSONObject.toJSONString(fundCreditWayDTO));
        }

        return fundCreditWayDTO;

    }


    /**
     * 筛选工资先享产品
     * @param loanPreoductsDTO
     * @return
     */
    private PFICreditWayDTO builderPFICreditWayDTO(String userId,String loanMinAmount,String loanMaxAmount,LoanProductsDTO loanPreoductsDTO,String iconUrl ) {
        PFICreditWayDTO pfiCreditWayDTO = null;

        //1.判断用户是否有工资先享额度
        if(!isValidatePFIUser(userId,loanMinAmount,loanMaxAmount)){
            return pfiCreditWayDTO;
        }

        String minAmount = loanPreoductsDTO.getMinPrincipal();
        String maxAmount = loanPreoductsDTO.getMaxPrincipal();
        //2.判断需要借的钱是否在工资先享的可借钱的范围内
        if(null != loanPreoductsDTO && (compareAmountBetweenStr(loanMinAmount,minAmount,maxAmount) || compareAmountBetweenStr(loanMaxAmount,minAmount,maxAmount))){

            pfiCreditWayDTO = new PFICreditWayDTO();
            pfiCreditWayDTO.setName("工资先享");
            pfiCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(true));
            pfiCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_PFI.getValue());
            pfiCreditWayDTO.setIconUrl(iconUrl+"/4.0/"+CreditWayCodeEnum.CREDIT_PFI.getValue()+".png");
            pfiCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());
            pfiCreditWayDTO.setConditionDesc("需要PFI信用");
            String minAmountDesc = new BigDecimal(minAmount).compareTo(new BigDecimal(10000))>=0?MoneyCommonUtils.divideW(minAmount)+"W":minAmount;
            String maxAmountDesc = new BigDecimal(maxAmount).compareTo(new BigDecimal(10000))>=0?MoneyCommonUtils.divideW(maxAmount)+"W":maxAmount;
            pfiCreditWayDTO.setLoanAmountDesc(minAmountDesc +"~"+maxAmountDesc);
            pfiCreditWayDTO.setLoanRate(loanPreoductsDTO.getRate());
            pfiCreditWayDTO.setLoanRateDesc("费率");

            BaseLogger.audit("构建PFI征信方式 结束 ,pfiCreditWayDTO ： " + pfiCreditWayDTO.toString());
        }

        return pfiCreditWayDTO;
    }

    /**
     * 判断用户是否满足PFI产品需求
     * @param userId
     * @return
     */
    private Boolean isValidatePFIUser(String userId,String loanMinAmount,String maxMinAmount) {
        Boolean isValidateUser = Boolean.TRUE;
        //1、此产品仅针对B端用户（B端用户为包括代发工资用户，代缴公积金社保用户，即信贷表）

//        BaseLogger.info("调用信贷系统，查询用户是否有工资先享信用额度入参userId:"+userId);
//        CreditExistResDTO resDTO = applyWageAdvanceDubboService.isClientCreditExist(userId);
//        BaseLogger.info("调用信贷系统，查询用户是否有工资先享信用额度回结果"+ JSONObject.toJSONString(resDTO));
//        if(!resDTO.isExist()){
//            isValidateUser = Boolean.FALSE;
//        }

//         TODO 2、必须通过后台风控模型的用户才能进行借款（非黑名单且评分合格）

        // 3、用户没有公积金贷款（白领专享及锦囊）的未结清款项
//        try{
//
//            OtherLoanOrderLeftResDTO otherLoanOrderLeftResDTO = applyWageAdvanceDubboService.isOtherLoanOrderLeft(userId);
//            isValidateUser  = otherLoanOrderLeftResDTO.isLeft();
//        }catch (Exception e){
//           BaseLogger.error("掉用信贷系统 － 查询（白领专享及锦囊）的未结清款项失败",e);
//        }

        return Boolean.FALSE;
    }


    private boolean compareAmountBetweenStr(String amount,String minAmount,String maxAmount){
        if(compareAmountStr(amount,minAmount) && compareAmountStr(maxAmount,amount)){
            return true;
        }
        return false;
    }

    private boolean compareAmountStr(String amount1,String amount2){
        try{
            return (new BigDecimal(amount1)).compareTo(new BigDecimal(amount2))>=0;
        }catch (Exception e){
            throw new AppException("筛选产品时金额不合法");
        }
    }


    @Override
    public FundLoanCityListDTO queryLoanProductCityList(String productId) {

        FundLoanCityListDTO fundLoanCityListDTO = new FundLoanCityListDTO();
        BaseLogger.info("白领专享查询公积金支持城市列表入参prodcutId:"+ productId);
        ProductCityInfoResDTO resDTO = applyHousingFundLoanDubboService.getProductCityInfo(productId);
        BaseLogger.info("白领专享查询公积金支持城市列表返回结果"+JSONObject.toJSONString(resDTO));

        if(!resDTO.getCode().equals("200")){
            throw new AppException(resDTO.getMsg());
        }

        //获取支持的城市
        List<ProductCityInfoDTO> cityList = resDTO.getProductCityInfoList();

        //热门城市
        List<CityBaseDTO> hotCityList = new ArrayList<>();
        //所有城市
        List<CityDTO> allCityList = new ArrayList<>();

        //暂时保存所有城市，key:首字母，value:对应城市list
        Map<String,List<CityBaseDTO>> tempMap = new HashMap<String,List<CityBaseDTO>>();

        if(!cityList.isEmpty()){
            for(ProductCityInfoDTO city:cityList){
                CityBaseDTO cityDto = new CityBaseDTO(city.getCityCode(),city.getCityName());

                //设置热门城市
                if(city.isHot()){
                    hotCityList.add(cityDto);
                }

                //城市首字母
                String firstEnName = city.getFirstCode();
                //首字母对应的城市列表
                List<CityBaseDTO> tempCityList = tempMap.get(firstEnName);
                if(tempCityList == null){
                    //如果为空，则新建一个list
                    tempCityList = new ArrayList<>();
                }
                tempCityList.add(cityDto);
                tempMap.put(firstEnName,tempCityList);
            }

            //设置所有城市
            for(Map.Entry<String,List<CityBaseDTO>> map:tempMap.entrySet()){
                CityDTO city = new CityDTO();
                city.setCode(map.getKey());
                city.setCitys(map.getValue());
                allCityList.add(city);
            }

            //根据城市首字母重新排序
            Collections.sort(allCityList,new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    CityDTO obj1 = (CityDTO)o1;
                    CityDTO obj2 = (CityDTO)o2;
                    return obj1.getCode().compareTo(obj2.getCode());
                }
            });

            fundLoanCityListDTO.setSelectDesc("请选择所在的城市");
            fundLoanCityListDTO.setHotCitys(hotCityList);
            fundLoanCityListDTO.setAllCitys(allCityList);

        }

        return fundLoanCityListDTO;
    }
}
