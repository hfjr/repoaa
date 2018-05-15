package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;

import com.fab.core.logger.BaseLogger;

import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductCityInfoDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.*;

import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.*;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.*;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.OrderTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.DateCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.MoneyCommonUtils;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IWageAdvanceService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanBizTypeConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanRepaymentTypeConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditIntroduceDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditInvestigationWayDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IPledgeInvestMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IWageAdvanceMapper;

import com.zhuanyi.vjwealth.wallet.mobile.payment.constants.PaymentPaltformIdConstant;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserQueryService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.ApplyLoanResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.LoanResultCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.WageAdvanceEarlyRepayDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IMBUserOrderService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserBankCardListInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;
import java.util.*;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance.ScanRepaymentPlanDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance.WageAdvanceInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *  工资先享服务实现
 * Created by hexy on 16/8/25.
 */
@Service
public class WageAdvanceServiceImpl implements IWageAdvanceService {

    @Autowired
    private FinancialLoanMapper financialLoanMapper;

    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Autowired
    private IApplyWageAdvanceDubboService applyWageAdvanceDubboService;

    @Autowired
    private IProductCityInfoDubboService productCityInfoDubboService;

    @Autowired
    private IPledgeInvestMapper pledgeInvestMapper;

    @Remote
    private IPhoneMessageService phoneMessageService;

    @Remote
    private IMBUserOrderService mBUserOrderService;

    @Autowired
    private IWageAdvanceMapper wageAdvanceMapper;

  /*  @Autowired
    private IUserQueryService userQueryService;*/

    @Remote
    private IWithholdServiceFacade withholdServiceFacade;


    @Override
    public CreditIntroduceDTO creditIntroduce() {
        CreditIntroduceDTO creditIntroduceDTO = new CreditIntroduceDTO();
         //获取介绍图片
        creditIntroduceDTO.setButtonTextMessage("立即借款");
        String url = financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_credit_introduce","wage_advance");
        creditIntroduceDTO.setIntroducePictureURL(url+".png");
        creditIntroduceDTO.setIntroducePictureWebpURL(url+".webp");
        return creditIntroduceDTO;
    }

    @Override
    public CreditInitiDTO creditIniti() {
        CreditInitiDTO creditInitiDTO = new CreditInitiDTO();
        creditInitiDTO = wageAdvanceMapper.queryUserCreditIniti();
        return creditInitiDTO;
    }



    @Override
    public CreditInvestigationWayDTO creditInvestigationWay(String userId,String borrowAmount) {

//        if(StringUtils.isBlank(userId) ){
//            throw new AppException("用户不合法");
//        }
//
//        if( StringUtils.isBlank(borrowAmount)  || !NumberUtils.isNumber(borrowAmount)){
//            throw new AppException("金额不合法");
//        }
//
//        CreditInvestigationWayDTO creditInvestigationWayDTO = new CreditInvestigationWayDTO();
//        List<BaseCreditWayDTO> creditWays = new ArrayList<BaseCreditWayDTO>();
//
//        Boolean isShowPFI = isValidatePFIUser(userId,borrowAmount);
//        //PFI征信方式
//        PFICreditWayDTO pfiCreditWayDTO = null;
//        //公积金征信方式
//        FundCreditWayDTO fundCreditWayDTO = null ;
//        //理财征信方式
//        PledgeInvestCreditWayDTO pledgeInvestCreditWayDTO = null;
//        //房产征信方式
//        HouseCreditWayDTO houseCreditWayDTO = null;
//
//
//        //调用信贷征信列表接口
//        try{
//            ProductsInfoResDTO  productsInfoResDTO =  applyWageAdvanceDubboService.creditInvestigationWay();
//            BaseLogger.audit("调用信贷增信列表接口  , productsInfoResDTO : "+ productsInfoResDTO.toString());
//            if(StringUtils.equals("200",productsInfoResDTO.getCode())){
//                List<LoanProductsDTO>  loanProductsDTOs =  productsInfoResDTO.getProductsInfoList();
//                LoanProductsDTO loanPreoductsDTOForYingzt = null, loanPreoductsDTOForCana = null;
//                for(LoanProductsDTO loanPreoductsDTO : loanProductsDTOs){
//                    // 获取PFI,工资先享征信方式产品
//                    if(isShowPFI && BizCodeType.LOAN_WA_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
//                        pfiCreditWayDTO = builderPFICreditWayDTO(borrowAmount,loanPreoductsDTO);
//                        continue;
//                    }
//                    // 获取公积金征信方式产品
//                    if(BizCodeType.LOAN_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId()) ){
//                        loanPreoductsDTOForYingzt  =  loanPreoductsDTO;
//                        continue;
//                    }
//                    if( BizCodeType.LOAN_CANA_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
//                        loanPreoductsDTOForCana  =  loanPreoductsDTO;
//                        continue;
//                    }
//                    // 获取理财征信征信方式产品
//                    if(BizCodeType.LOAN_LTB_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
//                        pledgeInvestCreditWayDTO = builderPledgeInvestCreditWayDTO(userId,borrowAmount,loanPreoductsDTO);
//                        continue;
//                    }
//                    // 获取房产征信方式产品
//                    if(BizCodeType.LOAN_HOUSE_PRODUCT_ID.getCode().equals(loanPreoductsDTO.getProductId())){
//                        houseCreditWayDTO = builderHouseCreditWayDTO(loanPreoductsDTO,borrowAmount);
//                    }
//                }
//                fundCreditWayDTO =  builderFundCreditWayDTO(loanPreoductsDTOForYingzt,loanPreoductsDTOForCana);
//            }else {
//                BaseLogger.warn("获取信贷系统增信方式信息,失败");
//                throw new AppException("增信方式获取失败");
//            }
//
//        }catch (Exception e){
//            BaseLogger.error("获取信贷系统增信方式信息,失败",e);
//            throw new AppException("增信方式获取失败");
//        }
//
//
//        if(isShowPFI && null != pfiCreditWayDTO){
//            creditWays.add(pfiCreditWayDTO);
//        }
//
//        if( null != fundCreditWayDTO){
//            creditWays.add(fundCreditWayDTO);
//        }
//
//        if( null != pledgeInvestCreditWayDTO){
//            creditWays.add(pledgeInvestCreditWayDTO);
//        }
//        if( null != houseCreditWayDTO){
//            creditWays.add(houseCreditWayDTO);
//        }
//
//        // 排序
//        Collections.sort(creditWays, new Comparator<BaseCreditWayDTO>(){
//            /*
//             * 返回一个基本类型的整型，
//             * 返回负数表示：o1 小于o2，
//             * 返回0 表示：o1和o2相等，
//             * 返回正数表示：o1大于o2。
//             */
//            public int compare(BaseCreditWayDTO o1, BaseCreditWayDTO o2) {
//
//                //按照征信方式的权重值进行升序排列
//                if(o1.getOrderingWeightValue() > o2.getOrderingWeightValue()){
//                    return 1;
//                }
//                if(o1.getOrderingWeightValue() == o2.getOrderingWeightValue()){
//                    return 0;
//                }
//                return -1;
//            }
//        });
//
//        creditInvestigationWayDTO.setCreditWayIntroduceDesc("请选择增信方式，不同的方式借款利率有所差别");
//        creditInvestigationWayDTO.setCreditWays(creditWays);
//        return creditInvestigationWayDTO;
        return null;
    }


//    /**
//     * 判断用户是否满足PFI产品需求
//     * @param userId
//     * @return
//     */
//    private Boolean isValidatePFIUser(String userId,String amount) {
////       Boolean isValidateUser = Boolean.FALSE;
//       Boolean isValidateUser = Boolean.TRUE;
//
//      //1、此产品仅针对B端用户（B端用户为包括代发工资用户，代缴公积金社保用户，即信贷表）
//
//        BaseLogger.info("调用信贷系统，查询用户是否有工资先享信用额度入参userId:"+userId);
//        CreditExistResDTO resDTO = applyWageAdvanceDubboService.isClientCreditExist(userId);
//        BaseLogger.info("调用信贷系统，查询用户是否有工资先享信用额度回结果"+ JSONObject.toJSONString(resDTO));
//        if(!resDTO.isExist()){
//            isValidateUser = Boolean.FALSE;
//        }
//
////         TODO 2、必须通过后台风控模型的用户才能进行借款（非黑名单且评分合格）
//
//        // 3、用户没有公积金贷款（白领专享及锦囊）的未结清款项
////        try{
////
////            OtherLoanOrderLeftResDTO otherLoanOrderLeftResDTO = applyWageAdvanceDubboService.isOtherLoanOrderLeft(userId);
////            isValidateUser  = otherLoanOrderLeftResDTO.isLeft();
////        }catch (Exception e){
////           BaseLogger.error("掉用信贷系统 － 查询（白领专享及锦囊）的未结清款项失败",e);
////        }
//
//        return isValidateUser;
//    }
//
//    private boolean validatorPFIAmountLimit(String amount){
//        String minAmount = financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_canborrow_min_amount","wage_advance");
//        String maxAmount = financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_canborrow_max_amount","wage_advance");
//        if(StringUtils.isBlank(minAmount) || StringUtils.isBlank(maxAmount) ){
//            throw new AppException("工资先享最小最大可借金额设置错误");
//        }
//        BigDecimal amountB = new BigDecimal(amount);
//        if(amountB.compareTo(new BigDecimal(minAmount))>=0 && amountB.compareTo(new BigDecimal(maxAmount)) <= 0){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 根据贷款产品构建房产征信方式
//     * @param loanPreoductsDTO
//     * @return
//     */
//    private HouseCreditWayDTO builderHouseCreditWayDTO(LoanProductsDTO loanPreoductsDTO,String borrowAmount) {
//        HouseCreditWayDTO houseCreditWayDTO = new HouseCreditWayDTO();
//        houseCreditWayDTO.setName("我有房产");
//        houseCreditWayDTO.setNoHouseIntroduceDesc("借款金额不符合要求。使用房产抵押进行的借款，需10万元起借。|此产品是一款由融桥宝专业金融团队打造的房产抵押短期借款服务。月综合费率低至1.25%，审批成功后2小时内放款到账，支持多种借款期限，随借随还，支持房产二次抵押");
//        houseCreditWayDTO.setHouseIntroduceDesc("请选择房产所在地");
//        houseCreditWayDTO.setCreditWayDesc(String.valueOf(loanPreoductsDTO.getProductDesc()));
//
//        houseCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_HOUSE.getValue());
//        houseCreditWayDTO.setProductNameDesc(loanPreoductsDTO.getProductName());
//        houseCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());
//
//        //借款金额是否超过起借额度,最大额度,判断产品是否可用
//       String minPrincipal = loanPreoductsDTO.getMinPrincipal();
//       String maxPrincipal = loanPreoductsDTO.getMaxPrincipal();
//       Boolean isAvailable = Boolean.TRUE;
//        //1、当用户输入金额小于10W，灰显；2、当用户收入金额大于等于10W，高亮显示
//        isAvailable =  compareBigDecimal(minPrincipal,borrowAmount) ;
//       houseCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(isAvailable));
//        //获取信贷产品城市列表
//        List<HouseCity> houseCityList =  builderHouseCitys();
//        houseCreditWayDTO.setHouseCityList(houseCityList);
//
//        BaseLogger.audit("构建房产征信方式 结束 ,houseCreditWayDTO ： " + houseCreditWayDTO.toString());
//
//        return houseCreditWayDTO;
//    }
//
//    /**
//     *  BigDecimal比较
//     * @param bigDecimal1
//     * @param bigDecimal2
//     * @return
//     */
//    private boolean compareBigDecimal(String bigDecimal1, String bigDecimal2) {
//        if(StringUtils.isBlank(bigDecimal1) || StringUtils.isBlank(bigDecimal2)){
//            BaseLogger.error("比较参数不能为空");
//            throw new AppException("参数为空");
//        }
//        BigDecimal t1 = new BigDecimal(bigDecimal1);
//        BigDecimal t2 = new BigDecimal(bigDecimal2);
//        return t1.compareTo(t2) <= 0 ? true: false;
//    }
//
//    /**
//     *  获取我有房产征信方式支持的城市列表
//     * @return
//     */
//    private List<HouseCity> builderHouseCitys() {
//        List<HouseCity> houseCityList = new ArrayList<HouseCity>();
//        //获取城市列表接口
//        try{
//            List<ProductCityDTO> productCityDTOs = productCityInfoDubboService.selectProductCitiesByProductId(CreditWayCodeEnum.CREDIT_HOUSE.getValue());
//            for(ProductCityDTO productCityDTO : productCityDTOs){
//                HouseCity houseCity = new HouseCity();
//                houseCity.setCanChoose(BooleanUtils.toStringTrueFalse("1".equals(productCityDTO.getIsValid())));
//                houseCity.setCityCode(productCityDTO.getCityCode());
//                houseCity.setCityName(productCityDTO.getCityName());
//                houseCityList.add(houseCity);
//            }
//        }catch (Exception e){
//            BaseLogger.error("调用信贷系统 工资易贷 城市列表接口失败",e);
//        }finally {
//            return houseCityList;
//        }
//
//    }
//
//    /**
//     * 根据贷款产品构建理财征信方式
//     * @param loanPreoductsDTO
//     * @return
//     */
//    private PledgeInvestCreditWayDTO builderPledgeInvestCreditWayDTO(String userId,String borrowAmount,LoanProductsDTO loanPreoductsDTO) {
//        PledgeInvestCreditWayDTO pledgeInvestCreditWayDTO = new PledgeInvestCreditWayDTO();
//        pledgeInvestCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_PLEDGE_INVEST.getValue());
//        pledgeInvestCreditWayDTO.setName("我有理财资产");
//        pledgeInvestCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse("1".equals(loanPreoductsDTO.getProductStatus())));
//        //pledgeInvestCreditWayDTO.setCreditWayDesc(String.valueOf(loanPreoductsDTO.getProductDesc()));
//        pledgeInvestCreditWayDTO.setProductNameDesc(loanPreoductsDTO.getProductName());
//        //pledgeInvestCreditWayDTO.setNoInverstOrderIntroduceDesc("暂无可担保理财资产。此产品属于先理财后贷款的产品，随借随还，按天计息。理财金额须大于600元，日利率0.035%，借款大于5万，T+1到账。");
//        pledgeInvestCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());
//        //查询用户的可借金额和投资总额
//        PledgeInvestInitiDTO pii = pledgeInvestMapper.queryPledgeInvestInitiV2(userId);
//        if(null != pii) {
//            pledgeInvestCreditWayDTO.setCanBorrowAmount(pii.getCanBorrowAmount());
//            pledgeInvestCreditWayDTO.setTotalCanBorrowAmount(pii.getTotalCanBorrowAmount());
//        }
//
//        //查询订单
//        List<InverstOrder> inverstOrders =  builderInverstOrder(userId,borrowAmount);
//        pledgeInvestCreditWayDTO.setInverstOrders(inverstOrders);
//
//        if( null == inverstOrders || inverstOrders.size() == 0){
//            pledgeInvestCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(false));
//        }
//
//        if(pledgeInvestCreditWayDTO.getIsAvailable().equals("true")){
//            pledgeInvestCreditWayDTO.setNoInverstOrderIntroduceDesc("请选择一个在投理财产品");
//            pledgeInvestCreditWayDTO.setCreditWayDesc("<font color=#B8CDD6>金额</font><font color=#FFBD30>"+loanPreoductsDTO.getMinPrincipal()+"</font><font color=#B8CDD6>元以上, 费率</font><font color=#FFBD30>"+loanPreoductsDTO.getRate()+"%</font><font color=#B8CDD6>(随借随还)</font>");
//        }else{
//            pledgeInvestCreditWayDTO.setNoInverstOrderIntroduceDesc("此产品属于先理财后贷款的产品，随借随还，按天计息。理财金额须大于600元，日利率0.035%，借款大于5万，T+1到账。");
//            pledgeInvestCreditWayDTO.setCreditWayDesc("<font color=#B8CDD6>金额"+loanPreoductsDTO.getMinPrincipal()+"元以上  费率"+loanPreoductsDTO.getRate()+"%  随借随还</font>");
//        }
//
//        BaseLogger.audit("构建理财征信方式 结束 ,pledgeInvestCreditWayDTO ： " + pledgeInvestCreditWayDTO.toString());
//        return pledgeInvestCreditWayDTO;
//    }
//
//    /**
//     * 查询满足条件的理财订单
//     * @param userId
//     * @return
//     */
//    private List<InverstOrder> builderInverstOrder(String userId,String borrowAmount) {
//
//        List<InverstOrder> list = new ArrayList<InverstOrder>();
//        if(pledgeInvestMapper.queryFinacialRewardSendInfo(userId)){
//            //查询用户可借金额对应的投资记录信息
//            list = pledgeInvestMapper.queryPledgeInvestInitiInnerOrderListLimitV2(userId,borrowAmount);
//        }else{
//            list = pledgeInvestMapper.queryPledgeInvestInitiInnerOrderListV2(userId,borrowAmount);
//        }
//        return list;
//    }
//
//
//    /**
//     * 根据贷款产品构建公积金征信方式
//     * @param loanPreoductsDTOForYingzt
//     * @param loanPreoductsDTOForCana
//     * @return
//     */
//    private FundCreditWayDTO builderFundCreditWayDTO(LoanProductsDTO loanPreoductsDTOForYingzt,LoanProductsDTO loanPreoductsDTOForCana) {
//        FundCreditWayDTO fundCreditWayDTO = new FundCreditWayDTO();
//        fundCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_FUND.getValue());
//        fundCreditWayDTO.setName("我有公积金信用贷款");
//        fundCreditWayDTO.setFundCreditLoanIntroduceDesc("请选择一款产品进行贷款");
//        fundCreditWayDTO.setCreditWayDesc("<font color=#B8CDD6>纯信用抵押，贷款日息最低可至</font><font color=#FFBD30>0.035%</font>");
//        //获取公积金方式列表,及判断产品是否可用
//        List<FundCreditLoan> fundCreditLoans = new ArrayList<>();
//        List<String> productNameDesc = new ArrayList<>();
//        // 优先白领专享排序
//        if(null != loanPreoductsDTOForYingzt ){
//            fundCreditWayDTO.setOrderingWeightValue(loanPreoductsDTOForYingzt.getOrderingWeightValue());
//            fundCreditLoans.add(builderFundCreditLoan(loanPreoductsDTOForYingzt));
//            productNameDesc.add(loanPreoductsDTOForYingzt.getProductName());
//        }
//        if(null != loanPreoductsDTOForCana ){
////            fundCreditWayDTO.setOrderingWeightValue(loanPreoductsDTOForCana.getOrderingWeightValue());
//            fundCreditLoans.add(builderFundCreditLoan(loanPreoductsDTOForCana));
//            productNameDesc.add(loanPreoductsDTOForCana.getProductName());
//        }
//        fundCreditWayDTO.setIsAvailable(Boolean.TRUE.toString());
//        if(0 == fundCreditLoans.size()){
//            BaseLogger.audit("公积金征信方式 产品不存在 ,fundCreditWayDTO  返回 ：null " );
//           return null;
//        }
//        fundCreditWayDTO.setFundCreditLoans(fundCreditLoans);
//
//
//        fundCreditWayDTO.setProductNameDesc(StringUtils.join(productNameDesc.toArray(),"|"));
//
//        BaseLogger.audit("构建公积金征信方式 结束 ,fundCreditWayDTO ： " + fundCreditWayDTO.toString());
//        return fundCreditWayDTO;
//    }
//
//    private FundCreditLoan builderFundCreditLoan(LoanProductsDTO loanPreoductsDTO) {
//        FundCreditLoan fundCreditLoan = new FundCreditLoan();
//        if(null != loanPreoductsDTO){
//            fundCreditLoan.setLoanProductId(loanPreoductsDTO.getProductId());
//            fundCreditLoan.setLoanProductName(loanPreoductsDTO.getProductName());
//            fundCreditLoan.setDesc(String.valueOf(loanPreoductsDTO.getProductDesc()));
//            fundCreditLoan.setBorrowAmount(StringUtils.join(new String[]{loanPreoductsDTO.getMinPrincipal(), loanPreoductsDTO.getMaxPrincipal()},"～"));
//            fundCreditLoan.setRate(loanPreoductsDTO.getRate());
//        }
//        return fundCreditLoan;
//    }
//
//    /**
//     * 根据贷款产品构建PFI征信方式
//     * @param loanPreoductsDTO
//     * @return
//     */
//    private PFICreditWayDTO builderPFICreditWayDTO(String borrowAmount,LoanProductsDTO loanPreoductsDTO ) {
//
//        PFICreditWayDTO pfiCreditWayDTO = new PFICreditWayDTO();
//        if(null != loanPreoductsDTO){
//            pfiCreditWayDTO.setName("我有PFI信用");
//            pfiCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(false));
//            if(validatorPFIAmountLimit(borrowAmount) && "1".equals(loanPreoductsDTO.getProductStatus())){
//                pfiCreditWayDTO.setIsAvailable(BooleanUtils.toStringTrueFalse(true));
//            }
//            pfiCreditWayDTO.setCreditWayDesc(String.valueOf(loanPreoductsDTO.getProductDesc()));
//            pfiCreditWayDTO.setProductNameDesc(loanPreoductsDTO.getProductName());
//            pfiCreditWayDTO.setCreditWayCode(CreditWayCodeEnum.CREDIT_PFI.getValue());
//            pfiCreditWayDTO.setOrderingWeightValue(loanPreoductsDTO.getOrderingWeightValue());
//        }
//        BaseLogger.audit("构建PFI征信方式 结束 ,pfiCreditWayDTO ： " + pfiCreditWayDTO.toString());
//        return pfiCreditWayDTO;
//    }


    @Override
    public WageAdvanceInitiDTO wageAdvanceIniti(String userId, String loanProductId,String borrowAmount) {

        if(StringUtils.isBlank(userId) || StringUtils.isBlank(borrowAmount)){
            throw new AppException("参数不能为空");
        }
        if(!StringUtils.isNumeric(borrowAmount)){
            throw new AppException("借款金额不合法");
        }

        WageAdvanceInitiDTO resultObj = new WageAdvanceInitiDTO();
        resultObj.setHelpURLTitle("帮助中心");
        resultObj.setHelpURL(financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_help_center","wage_advance"));
        resultObj.setHelpURLLabel("帮助中心");

        // 查询客户的相关贷款信息
        InitiReqDTO reqDto = new InitiReqDTO();
        reqDto.setClientId(userId);
        BaseLogger.info("调用信贷系统，查询客户的相关贷款信息入参userId:"+userId);
        InitiResDTO resDto = applyWageAdvanceDubboService.getInitiData(reqDto);
        BaseLogger.info("调用信贷系统，查询客户的相关贷款信息结果"+ JSONObject.toJSONString(resDto));
        if(resDto == null){
            throw new AppException("用户信息异常");
        }

        resultObj.setBorrowAmountLabel("借多少");
        resultObj.setBorrowAmountInputTip("请输入借款金额");
        resultObj.setCanBorrowMaxAmountLabel("总额度");
        resultObj.setCanBorrowMaxAmountDesc(resDto.getCanBorrowMaxAmount());//总额度
        resultObj.setCanBorrowAmountLabel("可借的钱");
        resultObj.setCanBorrowAmountDesc("¥"+resDto.getCanBorrowAmount());//可借额度（格式化的金额）
        resultObj.setCanBorrowAmount(MoneyCommonUtils.handlerMoneyStr(resDto.getCanBorrowAmount()));//可借额度
        resultObj.setCanBorrowDayLabel("借多久");
        resultObj.setCanBorrowDefaultPeriod(resDto.getDefaultPeriod());//默认借多久
        resultObj.setCanBorrowPeriods(resDto.getPeriodList());//贷款期限list<map>
        resultObj.setCanBorrowMinAmount(financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_canborrow_min_amount","wage_advance")); //最小额度
        resultObj.setCanBorrowAddAmount(financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_add_amount","wage_advance"));//递增金额
        resultObj.setBorrowModeLabel("怎么还");
        resultObj.setBorrowDefaultMode(resDto.getDefaultRepayType());//默认怎么还
        resultObj.setBorrowModes(resDto.getRepayTypeList());//还款方式list<map>
        resultObj.setCanBorrowAmountInterest(resDto.getRate());
        resultObj.setTotalInterestLabel("总利息");

        //获取默认还款方式的值
        String dt = resDto.getDefaultRepayType().get("key");
        //获取默认还款方式的值
        String dp = resDto.getDefaultPeriod().get("key");

        //计算利息
        CalculateInterestDTO interestDto = dynamicallyGeneratedInterest(userId,loanProductId,dt,borrowAmount,dp);

        resultObj.setTotalInterest(interestDto.getTotalInterest());//总利息
        resultObj.setRepaymentDateLabel("还款日期");
        resultObj.setRepaymentDate(interestDto.getRepaymentDate());//还款日期
        resultObj.setReceivableBankCardLabel("收款银行卡");
        resultObj.setReceivableBankCard(wageAdvanceMapper.queryUserSecurityCardDesc(userId));//收款银行卡

        resultObj.setBorrowTipDescription("可提前还款，利息按天计算，免手续费");
        resultObj.setContractLabel("贷款相关合同");
        resultObj.setContractTitle("贷款合同");

        resultObj.setContractURL(financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_contract_url","wage_advance"));//
        resultObj.setIsSendSMS(financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_is_sendMs","wage_advance"));//是否發送驗證碼
        resultObj.setIsShowRepaymentButton(resDto.getIsBorrowedBefore());//是否有借款中的订单

        return resultObj;
    }



    @Override
    public ScanRepaymentPlanDTO dynamicallyGeneratedRepaymentPlan(String userId, String borrowMode, String borrowAmount, String borrowPeriod) {
        ScanRepaymentPlanDTO srPlan = new ScanRepaymentPlanDTO();

        //调用生成还款计划接口，入参
        ShowRepayPlansReqDTO reqDto = new ShowRepayPlansReqDTO();
        reqDto.setApplyAmount(borrowAmount);
        reqDto.setPeriods(borrowPeriod);
        //调用还款计划接口
        BaseLogger.info("调用信贷系统，还款计划试算总利息入参:"+JSONObject.toJSONString(reqDto));
        ShowRepayPlansResDTO planDto = applyWageAdvanceDubboService.getShowRepayPlans(reqDto);
        BaseLogger.info("调用信贷系统，还款计划试算总利息返回结果"+ JSONObject.toJSONString(planDto));

        if(planDto == null){
            throw new AppException("生成还款计划异常");
        }

        //还款计划title
        List<RepaymentPlanTitleInfoDTO> planTitle = new ArrayList<RepaymentPlanTitleInfoDTO>();
        //获取还款计划列表
        List<RepayPlansResDTO> plans = planDto.getDefaultPlans();
        String rateStr = planDto.getDayRate();
        for(RepayPlansResDTO plan:plans){
            RepaymentPlanTitleInfoDTO rp = new RepaymentPlanTitleInfoDTO();
            rp.setKey(plan.getRepaymentMethod());
            String desc = LoanRepaymentTypeConstant.getValue(rp.getKey());

            rp.setValue(plan.getRepaymentMethodDesc());
            rp.setDesc(desc);
            if(plan.getRepaymentMethod().equals(borrowMode)){
                rp.setIsSelected("true");
            }
            planTitle.add(rp);

            RepaymentPlanInfoByTypeDTO paie = new RepaymentPlanInfoByTypeDTO();
            paie.setRepaymentAmount(plan.getPrincipalTotal());//应还总额
            paie.setRepaymentInterestDesc("<font color=#B8CDD6>日利率</font><font color=#FFBD30>"+rateStr+"%</font><font color=#B8CDD6> 利息</font><font color=#FFBD30>"+plan.getInterestTotal()+"</font><font color=#B8CDD6>元</font>");

            List<DetailPlanDTO> planInner = plan.getPlanList();//生成的还款计划
            List<RepaymentPlanDTO> resultPlan = new ArrayList<>();//返回给app的还款计划

            for(DetailPlanDTO pi:planInner){
                RepaymentPlanDTO rpd = new RepaymentPlanDTO();
                BigDecimal principal = new BigDecimal(MoneyCommonUtils.handlerMoneyStr(pi.getShouldPrincipal()));//本金
                BigDecimal interest = new BigDecimal(MoneyCommonUtils.handlerMoneyStr(pi.getShouldInterest()));//利息
                Date repayDate = pi.getRepaymentDate();//还款日期
                BigDecimal totalAmount = principal.add(interest);
                String des = "";
                if(principal.compareTo(new BigDecimal(0))<=0){
                    des = "利息¥"+interest.toPlainString();
                }else{
                    des = "本金¥"+principal.toPlainString()+"+"+"利息¥"+interest.toPlainString();
                }
                //获取当前"年"
                Calendar cal = Calendar.getInstance();
                cal.setTime(repayDate);
                cal.get(Calendar.YEAR);
                rpd.setYearGroup(String.valueOf(cal.get(Calendar.YEAR)));//年分组
                rpd.setDate(DateCommonUtils.handlerDate(repayDate,"MM月dd日"));//还款日期
                rpd.setTotalAmount("¥"+totalAmount.toPlainString());//当期应还总额
                rpd.setDetail(des);

                resultPlan.add(rpd);
            }
            paie.setRepaymentPlans(resultPlan);

            //设置返回结果-设置还款计划详情
            //等额本息
            if(plan.getRepaymentMethod().equals(LoanRepaymentTypeConstant.PRINCIPAL_AND_INTEREST_EQUAL)){
                srPlan.setPrincipalAndInterestEqualType(paie);
            }
            //到期还本付息
            else if(plan.getRepaymentMethod().equals(LoanRepaymentTypeConstant.REPAY_MATURITY)){
                srPlan.setMonthlyInterestType(paie);
            }

        }

        //根据还款计划的key排序
        Collections.sort(planTitle,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                RepaymentPlanTitleInfoDTO obj1 = (RepaymentPlanTitleInfoDTO)o1;
                RepaymentPlanTitleInfoDTO obj2 = (RepaymentPlanTitleInfoDTO)o2;
                return obj1.getKey().compareTo(obj2.getKey());
            }
        });
        //设置返回结果-设置还款计划头
        srPlan.setBorrowModes(planTitle);

        return srPlan;
    }




    @Override
    public CalculateInterestDTO dynamicallyGeneratedInterest(String userId, String loanProductId, String borrowMode, String borrowAmount, String borrowPeriod) {
        //返回结果
        CalculateInterestDTO result = new CalculateInterestDTO();

        //调用生成还款计划接口，入参
        ShowRepayPlansReqDTO reqDto = new ShowRepayPlansReqDTO();
        reqDto.setApplyAmount(borrowAmount);
        reqDto.setPeriods(borrowPeriod);
        //调用还款计划接口
        BaseLogger.info("调用信贷系统，还款计划试算总利息入参:"+JSONObject.toJSONString(reqDto));
        ShowRepayPlansResDTO planDto = applyWageAdvanceDubboService.getShowRepayPlans(reqDto);
        BaseLogger.info("调用信贷系统，还款计划试算总利息返回结果"+ JSONObject.toJSONString(planDto));

        if(planDto == null){
            throw new AppException("生成还款计划异常");
        }
        //当前还款计划
        RepayPlansResDTO currenPlan = new RepayPlansResDTO();
        //获取还款计划列表
        List<RepayPlansResDTO> plans = planDto.getDefaultPlans();
        for(RepayPlansResDTO plan:plans){
            if(plan.getRepaymentMethod().equals(borrowMode)){
                currenPlan = plan;
                break;
            }
        }
        //获取返回结果
        result.setTotalInterest(currenPlan.getInterestTotal());//利息总和
        result.setRepaymentDate(DateCommonUtils.handlerDate(currenPlan.getPlanList().get(0).getRepaymentDate(),"yyyy年MM月dd日"));//首期还款日期
        //返回
        return result;
    }



    @Override
    public String informationConfirmationSendSMSNotice(String userId) {
        //根据userId 查询用户手机号
        String phone = pledgeInvestMapper.queryUserPhoneByUserId(userId);
        if(StringUtils.isBlank(phone)){
            throw new AppException("数据异常：用户手机号不存在");
        }
        //获取验证码
        return sendSMSNotice(phone);
    }

    @Override
    public String informationConfirmationSendToneNotice(String userId) {
        //根据userId 查询用户手机号
        String phone = pledgeInvestMapper.queryUserPhoneByUserId(userId);
        if(StringUtils.isBlank(phone)){
            throw new AppException("数据异常：用户手机号不存在");
        }
        //获取验证码
        return sendToneNotice(phone);
    }

    //获取短信验证码
    private String sendSMSNotice(String phone){
        MessageDTO md = phoneMessageService.sendTextMessage(phone, "settlement_platform_default");
        if (!md.getSendFlag()) {
            throw new AppException(md.getSendFlagMessage());
        }
        return md.getVaildeTime();
    }

    //获取语音验证码
    private String sendToneNotice(String phone){
        MessageDTO md = phoneMessageService.sendToneMessage(phone, "settlement_platform_default");
        if (!md.getSendFlag()) {
            throw new AppException(md.getSendFlagMessage());
        }
        return md.getVaildeTime();
    }

    //校验验证码
    private void validatorCode(String phone,String code){
        MessageDTO message = phoneMessageService.checkValidationCode(phone, code, "settlement_platform_default");
        if (!message.getSendFlag()) {
            throw new AppException(message.getSendFlagMessage());
            //return Boolean.FALSE;
        }
        //return Boolean.TRUE;
    }


    @Override
    public Object applySMSVerificationConfirm(String userId, String code, String borrowMode, String loanProductId, String borrowAmount, String borrowPeriod) {

        LoanCheckStatusDTO checkStatusDTO = new LoanCheckStatusDTO();

        //1.参数基本校验
        if(StringUtils.isBlank(code) || StringUtils.isBlank(borrowMode) || StringUtils.isBlank(loanProductId) || StringUtils.isBlank(borrowAmount) || StringUtils.isBlank(borrowPeriod)){
            throw new AppException("参数不能为空");
        }

        //1.2 验证码校验
        String phone = pledgeInvestMapper.queryUserPhoneByUserId(userId);
        if(StringUtils.isBlank(phone)){
            throw new AppException("数据异常：用户手机号不存在");
        }
        validatorCode(phone,code);

        // 1.3 校验借款参数是否合法
        validatorAmount(userId,borrowAmount,borrowMode,borrowPeriod);

        //1.4校验用户是否绑定过银行卡
        validatorIsBingdingBankCard(userId);

        //1.5校验是否有未结清的款项（白领专享及锦囊）
        validatorIsLeftLoan(userId);

        //2.借款下单
        ApplyLoanResultDTO ad = mBUserOrderService.addWageAdvanceLoanOrder(userId, borrowMode, borrowAmount, borrowPeriod);
        //3.组装返回结果
        checkStatusDTO.setCode(ad.getCode());
        checkStatusDTO.setMessage(ad.getMessage());

        if(ad.getCode().equals("203900")){

            checkStatusDTO.setMessage("借款审核中");

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH,1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

            checkStatusDTO.setFeedbackInformation("将于一个工作日内完成审核 | 审核结果将于"+format.format(cal.getTime())+" 24点前公布");
            checkStatusDTO.setBorrowAmountLabel("借款额度");
            checkStatusDTO.setBorrowAmount("¥"+borrowAmount);
            checkStatusDTO.setButtonTextMessage("放款审核中");
            checkStatusDTO.setIconURL("");
        }


        return checkStatusDTO;
    }

    private void validatorIsLeftLoan(String userId){
        BaseLogger.info("调用信贷系统，查询是否有未结清的款项入参userId:"+userId);
        OtherLoanOrderLeftResDTO resDTO = applyWageAdvanceDubboService.isOtherLoanOrderLeft(userId);
        if(resDTO.isLeft()){
            throw new AppException("存在未结清的公积金贷款，请结清后再做借款");
        }
        BaseLogger.info("调用信贷系统，查询是否有未结清的款项返回结果"+ JSONObject.toJSONString(resDTO));
    }

    private void validatorIsBingdingBankCard(String userId){
        Map<String,String> cardNo = userQueryMapper.queryUserAllCardNum(userId);
        Object count = cardNo.get("cardNum");
        if(Integer.parseInt(String.valueOf(count)) <= 0){
            throw new AppException("请先绑卡，再做借款");
        }
    }

    private void validatorAmount(String userId,String borrowAmount,String repayType,String borrowPeriod){

        if(!StringUtils.isNumeric(borrowAmount)){
            throw new AppException("借款金额不合法");
        }
        if(!StringUtils.isNumeric(borrowPeriod)){
            throw new AppException("借款天数不合法");
        }

        // 查询客户的相关贷款信息
        InitiReqDTO reqDto = new InitiReqDTO();
        reqDto.setClientId(userId);
        BaseLogger.info("调用信贷系统，查询客户的相关贷款信息入参userId:"+userId);
        InitiResDTO resDto = applyWageAdvanceDubboService.getInitiData(reqDto);
        BaseLogger.info("调用信贷系统，查询客户的相关贷款信息结果"+ JSONObject.toJSONString(resDto));
        if(resDto == null){
            throw new AppException("用户信息异常");
        }
        //1.校验借款金额是否超过可借金额
        if(new BigDecimal(borrowAmount).compareTo(new BigDecimal(resDto.getCanBorrowAmount()))>0){
            throw new AppException("超过可借金额，请重新输入");
        }

        //2.校验还款计划
        List<Map<String,String>> repayTypes = resDto.getRepayTypeList();
        boolean repayTypeFlag = false;
        for(Map<String,String> map:repayTypes){
            if(repayType.equals(map.get("key"))){
                repayTypeFlag = true;
                break;
            }
        }
        if(!repayTypeFlag){
            throw  new AppException("还款方式不合法");
        }

        //3.校验借款期限是否合法
        List<Map<String,String>> repayPeriods = resDto.getPeriodList();
        boolean periodFlag = false;
        for(Map<String,String> map:repayPeriods){
            if(borrowPeriod.equals(map.get("key"))){
                periodFlag = true;
                break;
            }
        }
        if(!periodFlag){
            throw  new AppException("借款期限不合法");
        }
    }

    @Override
    public Object repaymentIniti(String userId) {
        if(StringUtils.isBlank(userId)){
            throw new AppException("userId不能为空");
        }

        //调用信贷系统，查询未还本金和借款笔数
        BaseLogger.info("工资先享调用信贷系统查询贷款还款初始化入参userId："+userId);
        NoRepayInfoReqDTO reqDTO = new NoRepayInfoReqDTO();
        reqDTO.setUserId(userId);
        NoRepayInfoResDTO resultObj = applyWageAdvanceDubboService.getNoRepayInfo(reqDTO);
        BaseLogger.info("工资先享调用信贷系统查询贷款还款初始化结果："+JSONObject.toJSONString(resultObj));

        if(("200").equals(resultObj.getCode())){
            RepaymentInitiDTO rid = new RepaymentInitiDTO();

            String count = resultObj.getNoSettleLoan();
            rid.setNoRepaymentCapitalTitle("未还本金");
            rid.setNoRepaymentCapital("¥"+resultObj.getNoRepayPrincipal());
            rid.setNoSettleLoanDescription("<font color=#B8CDD6>共</font><font color=#FFBD30>"+count+"</font><font color=#B8CDD6>笔借款未结清</font>");
            rid.setDueRepaymentLabel("到期还款");
            rid.setDueRepayment("请查看");
            rid.setDueRepaymentType("到期将从余额自动还款");
            rid.setEarlyRepaymentLabel("提前还款");
            rid.setEarlyRepayment("共"+count+"笔");
            rid.setEarlyRepaymentType("可提前结清所有的借款");

            if(resultObj.isOverdueExist()){
                rid.setLoanStatus("C_O");
            }else{
                rid.setLoanStatus("R");
            }


            return rid;
        }else{
            throw new AppException("系统异常");
        }
    }

    @Override
    public Object dueRepaymentIniti(String userId, String page) {
        if(StringUtils.isBlank(userId)|| StringUtils.isBlank(page)){
            throw new AppException("参数不能为空");
        }

        DueRepaymentInitiDTO did = new DueRepaymentInitiDTO();
        String amount = pledgeInvestMapper.queryUserMaAmountByUserId(userId);
        did.setAccountBalance(amount);
        did.setRepaymentTip("到期还款是到期还款时请到余额账户里充值后进行还款操作");
        did.setAccountBalanceLabel("账户余额:");

        //调用信贷系统，查询用户借款记录
        QueryNoRepayLoanOrdersReqDTO noRepayReq = new QueryNoRepayLoanOrdersReqDTO();
        noRepayReq.setUserId(userId);
        noRepayReq.setPage(Integer.parseInt(page));
        BaseLogger.info("工资先享调用信贷系统查询到期还款初始化入参userId："+userId);
        QueryNoRepayLoanOrdersResDTO noRepayRes = applyWageAdvanceDubboService.getQueryNoRepayLoanOrders(noRepayReq);
        BaseLogger.info("工资先享调用信贷系统查询到期还款初始化结果："+JSONObject.toJSONString(noRepayRes));

        if(("200").equals(noRepayRes.getCode())){
            // 获取未还账单列表
            List<NoRepayOrderDTO> list = noRepayRes.getNoRepayOrderDTOList();
            List<DueRepaymentInitiInnerRecordDTO> resultList = new ArrayList<>();
            for(NoRepayOrderDTO nod:list){
                DueRepaymentInitiInnerRecordDTO di = new DueRepaymentInitiInnerRecordDTO();
                di.setLoanCode(nod.getLoanCode());
                di.setLoanDateAndMoney(DateCommonUtils.handlerDate(nod.getStartTime(),"yyyy年MM月dd日")+" 借款"+nod.getLoanAmountTotal()+"元");
                di.setToRepaymentLabel("到期还款");
                String principal = nod.getOverplusPrincipal();
                String interest = nod.getOverplusInterest();
                di.setSurplusCapital("<font color=#4A4A4A>剩余本金:</font><font color=#4AC0F0>"+principal+"</font><font color=#4A4A4A>元</font>");
                di.setSurplusInterest("<font color=#4A4A4A>剩余利息:</font><font color=#4AC0F0>"+interest+"</font><font color=#4A4A4A>元</font>");
                di.setToRepaymentMoneyDate(DateCommonUtils.handlerDate(nod.getEndTime(),"yyyy年MM月dd日"));
                di.setPrincipal(principal);
                di.setInterest(interest);
                di.setLoanStatus(nod.getLoanStatus());
                resultList.add(di);
            }
            did.setRecords(resultList);
            if(list.size()<10){
                did.setIsMore("false");
            }else{
                did.setIsMore("true");
            }
            return did;
        }else{
            throw new AppException("系统异常");
        }
    }


    @Override
    public Object dueRepaymentDetail(String userId, String loanCode) {
        if(StringUtils.isBlank(userId)||StringUtils.isBlank(loanCode)){
            throw new AppException("参数不能为空");
        }
        WageAdvanceDueRepaymentDetailDTO result =  new WageAdvanceDueRepaymentDetailDTO();
        //调用信贷系统，查询借款详情
        NoRepayOrderDetailReqDTO noRepayDetailReq= new NoRepayOrderDetailReqDTO();
        noRepayDetailReq.setUserId(userId);
        noRepayDetailReq.setLoanId(loanCode);

        NoRepayOrderDetailResDTO noRepayDetailRes = applyWageAdvanceDubboService.getNoRepayLoanOrderDetail(noRepayDetailReq);
        List<RepaymentPlanDTO> repayList = new ArrayList<>();

        if(("200").equals(noRepayDetailRes.getCode())){
            List<DetailPlanDTO> planList = noRepayDetailRes.getNoRepayOrderDetail();

            for(DetailPlanDTO dpl:planList) {
                BigDecimal principal = new BigDecimal(MoneyCommonUtils.handlerMoneyStr(dpl.getShouldPrincipal()));//本金
                BigDecimal interest = new BigDecimal(MoneyCommonUtils.handlerMoneyStr(dpl.getShouldInterest()));//利息
                BigDecimal penaly = new BigDecimal(MoneyCommonUtils.handlerMoneyStr(dpl.getPenalty()==null?"0":dpl.getPenalty()));
                Date repayDate = dpl.getRepaymentDate();//还款日期


                String desc = "";
                if(principal.compareTo(new BigDecimal(0))>0){
                    desc = "本金"+principal.toPlainString()+desc+"+";
                }
                if(interest.compareTo(new BigDecimal(0))>0){
                    desc = desc+"利息"+interest.toPlainString()+"+";
                }
                if(penaly.compareTo(new BigDecimal(0))>0){
                    desc = desc+"罚息"+penaly.toPlainString()+"+";
                }
                if(StringUtils.isNotBlank(desc)){
                    desc = desc.substring(0,desc.length()-1);
                }

                RepaymentPlanDTO rpd = new RepaymentPlanDTO();


                if(repayDate.compareTo(new Date()) < 0){
                    rpd.setIsCurrentPeriod("no");//还款日过了今天，就是逾期，不是本期
                }else if(repayDate.compareTo(new Date()) == 0){
                    rpd.setIsCurrentPeriod("yes");//还款日等于今天，是本期
                }else{
                    //还款日大于今天，即还没有到期
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.MONTH,1);
                    if(cal.getTime().compareTo(repayDate)>0){
                        rpd.setIsCurrentPeriod("yes");//还款日等于今天，是本期
                    }else{
                        rpd.setIsCurrentPeriod("no");//还款日等于今天，是本期
                    }
                }

                String isOverDue = dpl.getIsOverdue();
                if(StringUtils.isNotBlank(isOverDue)&&isOverDue.equals("1")){
                    rpd.setIsOverDue("yes");
                }else{
                    rpd.setIsOverDue("no");
                }

                //获取当前"年"
                Calendar cal = Calendar.getInstance();
                cal.setTime(repayDate);
                cal.get(Calendar.YEAR);

                rpd.setYearGroup(String.valueOf(cal.get(Calendar.YEAR)));//年分组
                rpd.setDate(DateCommonUtils.handlerDate(repayDate,"MM月dd日"));//还款日期
                rpd.setTotalAmount(dpl.getMonthTotal());//当期应还总额
                rpd.setDetail(desc);

                repayList.add(rpd);


            }

            result.setRepayPlanInfo(repayList);
            result.setDueRepaymentDesc("每月到期自动还款");
            result.setKeepBalanceEnoughDesc("请确保余额资金充足");
            result.setRepaymentTypeDesc("还支持银行卡、存钱罐自动还款");
            result.setRepaymentTypeSubDesc("扣款顺序依次为余额/储蓄卡/存钱罐");
            return result;
        }else{
            throw new AppException("系统异常");
        }
    }

//    private Map<String, String> buildLabelAndValueMap(final String yearGroup, final String date,final String totalAmount,final String tip,final String detail) {
//        return new HashMap<String, String>() {
//            private static final long serialVersionUID = 1L;
//
//            {
//                put("yearGroup", yearGroup);
//                put("date", date);
//                put("totalAmount", totalAmount);
//                put("tip", tip);
//                put("detail", detail);
//            }
//        };
//    }


    @Override
    public Object earlyRepaymentIniti(String userId,String page) {

        if(StringUtils.isBlank(userId)|| StringUtils.isBlank(page)){
            throw new AppException("参数不能为空");
        }

        EarlyRepaymentInitiDTO did = new EarlyRepaymentInitiDTO();

        //调用信贷系统，查询用户借款记录
        QueryNoRepayLoanOrdersReqDTO noRepayReq = new QueryNoRepayLoanOrdersReqDTO();
        noRepayReq.setUserId(userId);
        noRepayReq.setPage(Integer.parseInt(page));
        BaseLogger.info("工资先享调用信贷系统查询到期还款初始化入参userId："+userId);
        QueryNoRepayLoanOrdersResDTO noRepayRes = applyWageAdvanceDubboService.getQueryNoRepayLoanOrders(noRepayReq);
        BaseLogger.info("工资先享调用信贷系统查询到期还款初始化结果："+JSONObject.toJSONString(noRepayRes));

        if(("200").equals(noRepayRes.getCode())){
            List<NoRepayOrderDTO> list = noRepayRes.getNoRepayOrderDTOList();
            List<DueRepaymentInitiInnerRecordDTO> resultList = new ArrayList<>();
            for(NoRepayOrderDTO nod:list) {
                DueRepaymentInitiInnerRecordDTO di = new DueRepaymentInitiInnerRecordDTO();
                di.setLoanCode(nod.getLoanCode());
                di.setLoanDateAndMoney(DateCommonUtils.handlerDate(nod.getStartTime(), "yyyy年MM月dd日") + " 借款" + nod.getLoanAmountTotal() + "元");
                di.setToRepaymentLabel("到期付款");
                String principal = nod.getOverplusPrincipal();
                String interest = nod.getOverplusInterest();
                di.setSurplusCapital("<font color=#4A4A4A>未还本金:</font><font color=#4AC0F0>" + principal + "</font><font color=#4A4A4A>元</font>");

                String desc = "<font color=#4A4A4A>未还利息:</font><font color=#4AC0F0>" + interest + "</font><font color=#4A4A4A>元</font>";
                String penalty = nod.getPenalty();
                if(new BigDecimal(penalty==null?"0":MoneyCommonUtils.handlerMoneyStr(penalty)).compareTo(new BigDecimal("0"))>0){
                    desc = desc+" | <font color=#4A4A4A>罚息:</font><font color=#4AC0F0>" + penalty + "</font><font color=#4A4A4A>元</font>";
                }

                di.setSurplusInterest(desc);
                di.setToRepaymentMoneyDate(DateCommonUtils.handlerDate(nod.getEndTime(), "yyyy年MM月dd日"));
                di.setPrincipal(MoneyCommonUtils.handlerMoneyStr(principal));
                di.setInterest(MoneyCommonUtils.handlerMoneyStr(interest));
                di.setNoRepaymentPeriod("剩余"+nod.getNoRapayTerm()+"期");//剩余还款计划期数
                di.setLoanStatus(nod.getLoanStatus());
                resultList.add(di);
            }
            did.setRecords(resultList);

            did.setRepaymentTip("<font color=#B8CDD6>共</font><font color=#FFBD30>"+noRepayRes.getTotalCount()+"</font><font color=#B8CDD6>笔借款未还，提前还款无需手续费</font>");
            if(list.size()<10){
                did.setIsMore("false");
            }else{
                did.setIsMore("true");
            }
            return did;
        }else{
            throw new AppException("系统异常");
        }
    }


    @Override
    public Object earlyRepaymentDetail(String userId, String loanCodes, String repaymentMoney) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes)|| StringUtils.isBlank(repaymentMoney)){
            throw new AppException("参数不能为空");
        }
        String[] loanIds = loanCodes.split(",");

        EarlyRepaymentDetailDTO detailDto = new EarlyRepaymentDetailDTO();
        detailDto.setRepaymentMoneyLabel("还款本金");
        detailDto.setNoRepaymentCapitalLabel("未还本金");
        detailDto.setCapitalLabel("本金");
        detailDto.setCounterFeeLabel("手续费");
        detailDto.setInterestLabel("利息");
        detailDto.setRepaymentTotalMoneyLabel("还款总额");
        detailDto.setAvailableBalanceMoneyLabel("账户余额");
        detailDto.setPenaltyLabel("罚息");

        String amount = pledgeInvestMapper.queryUserMaAmountByUserId(userId);
        detailDto.setAvailableBalanceMoney(amount);
        detailDto.setAvailableBalanceMoneyDesc(amount);


        BaseLogger.info(String.format("工资先享调用信贷系统查询借款记录详情入参userId：%s;loanCode:%s;repaymentMoney:%s",userId,loanCodes,repaymentMoney));
        QueryRepayAmountReqDTO rrd = new QueryRepayAmountReqDTO();
        rrd.setUserId(userId);
        rrd.setLoanIds(loanIds);
        rrd.setAmountTotal(repaymentMoney);
        QueryRepayAmountResDTO ra = applyWageAdvanceDubboService.getQueryRepayAmountByIds(rrd);

        BaseLogger.info("工资先享调用信贷系统查询借款记录详情结果："+JSONObject.toJSONString(ra));

        if(("200").equals(ra.getCode())){
            detailDto.setRepaymentMoney(repaymentMoney);
            detailDto.setRepaymentMoneyDesc(repaymentMoney);
            detailDto.setNoRepaymentCapital(ra.getNoRepayPrincipal());
            detailDto.setCapital(repaymentMoney);
            detailDto.setInterest(ra.getInterestTotal());
            detailDto.setCounterFee(ra.getPoundageTotal());
            detailDto.setRepaymentTotalMoney(MoneyCommonUtils.handlerMoneyStr(ra.getRepayAmountTotal()));
            detailDto.setRepaymentTotalMoneyDesc(ra.getRepayAmountTotal());
            detailDto.setPenalty(ra.getPenaltyTotal());
            return detailDto;

        }else{
            throw new AppException("订单还款异常");
        }
    }


    @Override
    public Object earlyRepaymentConfirmIniti(String userId, String loanCodes, String repaymentMoney) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes) ||StringUtils.isBlank(repaymentMoney)){
            throw new AppException("参数不能为空");
        }
        //返回结果
        WageAdvanceEarlyRepaymentConfirmInitiDTO ed = new WageAdvanceEarlyRepaymentConfirmInitiDTO();
       //用户银行卡列表
        List<UserBankCardListInfoDTO> repayWays = new ArrayList<>();

        //设置余额
        UserBankCardListInfoDTO balance = new UserBankCardListInfoDTO();
        balance.setBankCode("balance_code");
        balance.setBankName("账户余额");
        balance.setCardId("");
        balance.setCardNo("");

        balance.setCardType("account_balance");
        balance.setStatus("normal");
        balance.setIsSupportCard("Y");
        balance.setIsSendedSMS("N");
        balance.setCardAmountDesc("可用余额"+pledgeInvestMapper.queryUserMaAmountByUserId(userId));

        repayWays.add(balance);

        List<BindingCardDTO> cardList = withholdServiceFacade.queryWithholeCardList(userId, LoanBizTypeConstant.WAGE_BIND_CARD);
        if(CollectionUtils.isNotEmpty(cardList)){
            for(BindingCardDTO bcd:cardList){
                UserBankCardListInfoDTO bankCard = new UserBankCardListInfoDTO();
                bankCard.setBankCode(bcd.getBankCode());
                String cardNo =bcd.getCardNo();
                bankCard.setBankName(bcd.getBankName()+"(尾号"+cardNo.substring(cardNo.length()-4,cardNo.length())+")");
                bankCard.setCardId(bcd.getCardId());
                bankCard.setCardNo(cardNo);
                bankCard.setCardType("bank_card");
                bankCard.setStatus(bcd.getStatus());
                bankCard.setIsSendedSMS(bcd.getIsBindingThird());
                bankCard.setIsSupportCard(bcd.getIsSupportCard());
                String phone = bcd.getPhone();
                bankCard.setCardBindMobilePhoneNo("输入手机尾号"+phone.substring(phone.length()-4,phone.length())+"接收的验证码");
                bankCard.setCardAmountDesc(StringUtils.isBlank(bcd.getRemainAmount())?"":"可用额度"+bcd.getRemainAmount());
                repayWays.add(bankCard);
            }
        }


        //查询用户所有在易宝绑定的银行卡
        //repayWays.addAll(mBUserOrderService.queryUserYBBindingCardList(userId));

        ed.setRepaymentTitle("付款详情");
        ed.setRepaymentTypeLabel("付款类型");
        ed.setRepaymentTypeDesc("工资先享还款");
        ed.setRepaymentType("wageAdvance");
        ed.setRepaymentWayLabel("付款方式");
        ed.setRepaymentWays(repayWays);
        ed.setRepaymentWayDefault(repayWays.get(0));
        ed.setNeedRepaymentMoneyLabel("需付款");
        ed.setNeedRepaymentMoney(repaymentMoney);
        ed.setBizType(OrderTypeEnum.EARLY_REPAY_BANKCARD_WITHHOLD.getValue());//账单类型（银行卡提前还款）

        return ed;

    }

    private Map<String,String> buildRepaymentWay(String key,String value,String icon){
        Map<String,String> map = new HashMap<>();
        map.put("key",key);
        map.put("value",value);
        map.put("icon",icon);
        return map;
    }


    @Override
    public Object queryLoanRecordList(String userId, String page) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(page)){
            throw new AppException("参数不能为空");
        }
        Map<String,Object> resultMap = new HashMap<>();
        List<LoanRecordListDTO> resultList = new ArrayList<>();
        //调用信贷系统，获取借款记录列表
        BaseLogger.info(String.format("工资先享调用信贷系统查询借款记录入参userId：%s;page:%s",userId,page));
        QueryLoanOrdersReqDTO reqDTO = new QueryLoanOrdersReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setPage(Integer.parseInt(page));
        QueryLoanOrdersResDTO loanOrders = applyWageAdvanceDubboService.getQueryLoanOrders(reqDTO);
        BaseLogger.info("工资先享调用信贷系统查询借款记录结果："+JSONObject.toJSONString(loanOrders));

        if(("200").equals(loanOrders.getCode())){
            List<OrderDTO> list = loanOrders.getOrderList();
            for(OrderDTO order:list){
                LoanRecordListDTO ld = new LoanRecordListDTO();
                ld.setLoanCode(order.getLoanCode());
                ld.setLoanAmount("¥"+order.getLoanAmount());

                ld.setLoanTimeStr(DateCommonUtils.handlerDate(order.getLoanTime(),"yyyy年MM月dd日"));
                ld.setLoanStatus(order.getLoanStatus());
                ld.setLoanStatusStr(order.getLoanStatusStr());
                resultList.add(ld);
            }
            if(resultList.size()<10){
                resultMap.put("isMore", "false");
            }else{
                resultMap.put("isMore", "true");
            }
            resultMap.put("records", resultList);

            return resultMap;
        }else{
            throw new AppException("系统异常");
        }
    }


    @Override
    public Object queryLoanRecordDetail(String userId, String loanCode) {
        if(StringUtils.isBlank(userId)|| StringUtils.isBlank(loanCode)){
            throw new AppException("参数不能为空");
        }

        BaseLogger.info(String.format("工资先享调用信贷系统查询借款记录详情入参userId：%s;loanCode:%s",userId,loanCode));
        LoanOrderDetailReqDTO reqDTO = new LoanOrderDetailReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setLoanId(loanCode);
        LoanOrderDetailResDTO resDTO = applyWageAdvanceDubboService.getLoanOrderDetail(reqDTO);
        BaseLogger.info("工资先享调用信贷系统查询借款记录详情结果："+JSONObject.toJSONString(resDTO));


        if(("200").equals(resDTO.getCode())){

            LoanRecordDetailDTO lrd = new LoanRecordDetailDTO();
            lrd.setLoanCode(resDTO.getLoanCode());
            String loanStatus = resDTO.getLoanStatus();//账单状态（R：使用中，F：提前或到期结清，ST：强制结清）
            lrd.setLoanStatus(loanStatus);

            lrd.setLoanStatusTitle((loanStatus.equals("R")||loanStatus.equals("C_O"))?"使用中金额":"已结清本金");
            lrd.setTopLoanAmountTitle("¥ "+(loanStatus.equals("R")||loanStatus.equals("C_O")?resDTO.getNoRepayPricipal():resDTO.getLoanAmount()));
            lrd.setLeftPeriodTip((loanStatus.equals("R")||loanStatus.equals("C_O"))?"<font color=#B8CDD6>该笔额度将分</font><font color=#FFBD30>"+resDTO.getLeftPeriods()+"</font><font color=#B8CDD6>期还完</font>":"");

            lrd.setAdvanceRepayTitle("提前还款");
            lrd.setRepayFinishTitle("好借好还，再借不难！");
            lrd.setNoRepayPricipal(MoneyCommonUtils.handlerMoneyStr(resDTO.getNoRepayPricipal()));

            //借款信息
            LoanRecordDetailInnerloanInfoDTO lii = new LoanRecordDetailInnerloanInfoDTO();
            lii.setContract("点击查看");
            lii.setContractLabel("借款合同");
            lii.setContractTitle("借款合同");
            lii.setContractURL(financialLoanMapper.getParamsValueByKeyAndGroup("wage_advance_contract_url","wage_advance"));
            lii.setLoanTitle("借款明细");
            List<Map<String,String>> loanList = new ArrayList<>();
            loanList.add(buildLabelAndValueMap("借款金额", resDTO.getLoanAmount()+"元"));
            loanList.add(buildLabelAndValueMap("合同期限", DateCommonUtils.handlerDate(resDTO.getStartDate(),"yyyy/MM/dd")+"-"+DateCommonUtils.handlerDate(resDTO.getEndDate(),"yyyy/MM/dd")));
            loanList.add(buildLabelAndValueMap("还款方式", resDTO.getRepayTypeName()));
            lii.setLoanDetail(loanList);
            lrd.setLoanInfos(lii);


            //还款本金
            BigDecimal repayPrincipal = resDTO.getSumRepayPrincipal()==null?new BigDecimal("0"):new BigDecimal(MoneyCommonUtils.handlerMoneyStr(resDTO.getSumRepayPrincipal()));
            BigDecimal repayInterest = resDTO.getSumRepayInterest()==null?new BigDecimal("0"):new BigDecimal(MoneyCommonUtils.handlerMoneyStr(resDTO.getSumRepayInterest()));
            BigDecimal repayPoundage = resDTO.getPoundage()==null?new BigDecimal("0"):new BigDecimal(MoneyCommonUtils.handlerMoneyStr(resDTO.getPoundage()));
            BigDecimal repayPenalty = resDTO.getSumRepayPenalty()==null?new BigDecimal("0"):new BigDecimal(MoneyCommonUtils.handlerMoneyStr(resDTO.getSumRepayPenalty()));

            //如果有过还款记录，就显示还款明细
            if(repayPrincipal.compareTo(new BigDecimal(0)) > 0 ||
                    repayInterest.compareTo(new BigDecimal(0)) > 0 ||
                    repayPoundage.compareTo(new BigDecimal(0)) > 0 ||
                    repayPenalty.compareTo(new BigDecimal(0)) > 0 ){
                //已结清的订单，才有还款信息
                LoanRecordDetailInnerrepaymentInfoDTO lid = new LoanRecordDetailInnerrepaymentInfoDTO();
                lid.setRepayRecordLabel("还款记录");
                lid.setRepayTitle("还款明细");
                lid.setRepayRecord("点击查看");
                List<Map<String,String>> repayList = new ArrayList<>();
                repayList.add(buildLabelAndValueMap("已还本金", repayPrincipal.toPlainString()+"元"));
                repayList.add(buildLabelAndValueMap("已还利息", repayInterest.toPlainString()+"元"));
                repayList.add(buildLabelAndValueMap("手续费", repayPoundage.toPlainString()+"元"));
                repayList.add(buildLabelAndValueMap("罚息", repayPenalty.toPlainString()+"元"));
                lid.setRepaymentDetail(repayList);
                lrd.setRepaymentInfos(lid);
            }else{
                lrd.setRepaymentInfos(new LoanRecordDetailInnerrepaymentInfoDTO());
            }


            String penaltyStr = resDTO.getNoRepayPenalty();
            BigDecimal penalty = StringUtils.isBlank(penaltyStr)?new BigDecimal("0"):new BigDecimal(penaltyStr);
            if(penalty.compareTo(new BigDecimal(0)) > 0){
                LoanRecordDetailInnerpenalyInfoDTO penaltyInfo = new LoanRecordDetailInnerpenalyInfoDTO();
                penaltyInfo.setPenaltyTitle("逾期罚息");
                penaltyInfo.setPenaltyDayTitle("逾期时间");
                penaltyInfo.setPenaltyDay(resDTO.getPenaltyDays()+"天");
                penaltyInfo.setPenalty(penalty.toPlainString()+"元");
                penaltyInfo.setTip("");
                lrd.setPenaltyInfos(penaltyInfo);
            }else{
                lrd.setPenaltyInfos(new LoanRecordDetailInnerpenalyInfoDTO());
            }

            return lrd;

        }else{
            throw new AppException("系统异常");
        }
    }

    private Map<String, String> buildLabelAndValueMap(final String label, final String value) {
        return new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
                put("label", label);
                put("value", value);
            }
        };
    }


    @Override
    public Object queryrepayRecordList(String userId, String loanCode, String page) {
        if(StringUtils.isBlank(userId)|| StringUtils.isBlank(loanCode) || StringUtils.isBlank(page)){
            throw new AppException("参数不能为空");
        }

        BaseLogger.info(String.format("工资先享调用信贷系统查询借款的还款记录入参userId：%s;loanCode:%s;page:%s",userId,loanCode,page));
        QueryRepayOrdersReqDTO reqDTO = new QueryRepayOrdersReqDTO();
        reqDTO.setUserId(userId);
        reqDTO.setLoanId(loanCode);
        reqDTO.setPage(Integer.parseInt(page));
        QueryRepayOrdersResDTO resDTO = applyWageAdvanceDubboService.getQueryRepayOrders(reqDTO);
        BaseLogger.info("工资先享调用信贷系统查询借款的还款记录结果："+JSONObject.toJSONString(resDTO));

        Map<String,Object> resultMap = new HashMap<>();

        if(("200").equals(resDTO.getCode())){
            List<RepayOrderDTO> list =resDTO.getRepayOrderDTOList();//将map转成实体
            List<LoanRecordDetailReapyListDTO> resultList = new ArrayList<>();
            for(RepayOrderDTO dto:list){
                LoanRecordDetailReapyListDTO ld = new LoanRecordDetailReapyListDTO();
                ld.setCounterFeeTitle("手续费");
                ld.setRepayInterestTitle("利息");
                ld.setRepayPrincipalTitle("本金");
                ld.setPenaltyTitle("罚息");
                ld.setPenalty(dto.getPenalty());
                ld.setCounterFee(dto.getCounterFee()+"元");
                ld.setRepayInterest(dto.getRepayInterest()+"元");
                ld.setRepayPrincipal(dto.getRepayPrincipal()+"元");
                ld.setRepayDate(DateCommonUtils.handlerDate(dto.getRepayTime(), "yyyy年MM月dd日"));
                ld.setRepayTotal("¥"+dto.getRepayTotal());
                ld.setRepayTime(DateCommonUtils.handlerDate(dto.getRepayTime(), "HH:mm:ss"));
                resultList.add(ld);
            }
            if(resultList.size()<10){
                resultMap.put("isMore", "false");
            }else{
                resultMap.put("isMore", "true");
            }
            resultMap.put("records", resultList);
            return resultMap;
        }else{
            throw new AppException("系统异常");
        }
    }

    @Override
    public Object earlyRepaymentConfirm(String userId, String loanCodes, String principal, String repaymentMoney, String repaymentType, String repaymentWay) {

        WageAdvanceEarlyRepayDTO reqDto = new WageAdvanceEarlyRepayDTO(userId,loanCodes,principal,repaymentMoney,repaymentType,repaymentWay);

        LoanResultCommonDTO result = mBUserOrderService.wageAdvanceEarlyRepaymentConfirm(reqDto);

        EarlyRepayResultDTO erd = new EarlyRepayResultDTO();
        erd.setCode(result.getCode());
        erd.setMessage(result.getMessage());

        if(result.getCode().equals("204000")){
            erd.setFeedbackInformation("还款成功,"+repaymentMoney+"元");
            erd.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("early_repay_app_success","pledge_loan"));
        }
        if(result.getCode().equals("204002")){
            erd.setFeedbackInformation(erd.getMessage());
        }
        if(result.getCode().equals("204003")){
            erd.setFeedbackInformation("还款处理中,"+repaymentMoney+"元,稍后可通过账单查询处理结果");
            erd.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("early_repay_app_success","pledge_loan"));
        }

        return erd;
    }


    @Override
    public Object withhold(String userId, String cardNo, String bankCode, String amount) {
        return null;
    }

    @Override
    public Object queryWithholdResult(String userId,String amount, String orderNo) {
        return null;
    }


    @Override
    public Object queryUserLoanCheckStatus(String userId) {
        LoanCheckStatusDTO checkStatusDTO = new LoanCheckStatusDTO();

        BaseLogger.info(String.format("工资先享调用信贷系统查询queryUserLoanCheckStatus入参userId：%s",userId));
        RouteCheckResDTO resDTO = applyWageAdvanceDubboService.getRouteCheck(userId);
        BaseLogger.info("工资先享调用信贷系统查询queryUserLoanCheckStatus结果："+JSONObject.toJSONString(resDTO));

        String code = resDTO.getCode();
        checkStatusDTO.setCode(code);
        if("204100".equals(code)){
            checkStatusDTO.setMessage("借款审核成功");
        }else if("204101".equals(code)){
            checkStatusDTO.setMessage("借款审核中");
            checkStatusDTO.setFeedbackInformation("将于一个工作日内完成审核 | 审核结果将于两个工作日内公布，敬请留意");
            checkStatusDTO.setBorrowAmountLabel("借款额度");
            checkStatusDTO.setBorrowAmount(resDTO.getApplyAmount());
            checkStatusDTO.setButtonTextMessage("放款审核中");
            checkStatusDTO.setIconURL("");
        }else if("204102".equals(code)){
            checkStatusDTO.setMessage("借款审核失败");
            checkStatusDTO.setFeedbackInformation(resDTO.getMsg());
            checkStatusDTO.setBorrowAmountLabel("借款额度");
            checkStatusDTO.setBorrowAmount(resDTO.getApplyAmount());
            checkStatusDTO.setButtonTextMessage("完成");
            checkStatusDTO.setIconURL("");
        }

        return checkStatusDTO;
    }


    @Override
    public Map<String, String> queryWageAdvanceContractContent(String userId, String loanCode, String loanProductId) {
        return applyWageAdvanceDubboService.wageAdvanceContract(userId,loanCode,loanProductId);
    }

    public static void main(String[] args){
        BigDecimal a = new BigDecimal("222.11");
        BigDecimal b = new BigDecimal("222.11000");
        System.out.print(a.compareTo(b));
    }
}
