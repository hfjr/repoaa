package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.impl;


import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.util.DateUtil;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.IMySalaryPlanService;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.SplitUtil;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.dto.MySalaryPlanQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.dto.PlanDetailInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server.mapper.MySalaryPlanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.util.MySalaryPlanDateUtils;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.constant.PlanConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.PlanSummaryInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanRecordDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.IWjSalaryPlanRecordService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.IWjSalaryPlanService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:我的工资计划
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
@Service
public class MySalaryPlanServiceImpl implements IMySalaryPlanService {
    @Autowired
    private ICommConfigsQueryService commConfigsQueryService;
    @Remote
    private IWjSalaryPlanService wjSalaryPlanService;
    @Remote
    private IWjSalaryPlanRecordService wjSalaryPlanRecordService;
    @Remote
    private IPhoneMessageService phoneMessageService;
//    @Autowired
//    private IUserInfoQueryService userInfoQueryService;
    @Autowired
    private MySalaryPlanMapper mySalaryPlanMapper;
    @Autowired
    private IUserAccountQueryMapper userAccountQueryMapper;
    /**
     * 0.页面路由接口(首页入口)
     *
     * @param userId
     * @return
     * @since 3.6
     */
    @Override
    public Map<String, Object> route(String userId) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        WjSalaryPlanDTO wjSalaryPlan=wjSalaryPlanService.queryWjSalaryPlan(userId,PlanConstant.PLAN_STATUS_COMPLETE.getCode());
        if(wjSalaryPlan==null){
            resultMap.put("code","204300");
            resultMap.put("message","没有工资计划，定制工资计划");
            return resultMap;
        }
        resultMap.put("code","204301");
        resultMap.put("message","已有工资计划，我的工资计划");
        return resultMap;
    }
    /**
     * 1.我的工资计划(已有工资计划)
     * @param userId
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> queryMySalaryPlan(String userId) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("title","我的工资计划");
        resultMap.put("managePlanButtonText","管理计划");
        resultMap.put("managePlanButton","删除计划|取消");
        resultMap.put("transferTimesText","成功转入");
        resultMap.put("transferSumAmountText","累计转入");
        resultMap.put("consecutiveDaysText","连续获得收益");
        PlanSummaryInfoDTO planSummaryInfo=wjSalaryPlanService.queryWjSalaryPlanSummaryInfo(userId);
        if(planSummaryInfo==null){
            throw new AppException("工资计划不存在!");
        }
        resultMap.put("planCode",planSummaryInfo.getPlanId());
        resultMap.put("depositDate",planSummaryInfo.getDepositDate());
        resultMap.put("depositAmount",planSummaryInfo.getDepositAmount());
        resultMap.put("bankName",planSummaryInfo.getBankName());
        resultMap.put("bankCard", MessageFormat.format("[{0}]", SplitUtil.splitLastStr(planSummaryInfo.getBankCard(), 4)));
        resultMap.put("incomeChannelName", "存钱罐");
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
        resultMap.put("incomeRate",MessageFormat.format("T金所{0}%",userAccountQueryMapper.queryTaReceiveRate()));
        resultMap.put("consecutiveDays",planSummaryInfo.getConsecutiveDays());
        resultMap.put("transferTimes",planSummaryInfo.getTransferTimes());
        resultMap.put("transferSumAmount",planSummaryInfo.getTransferSumAmount());
        
        resultMap.put("planInformationPictureUrl",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/mySalaryPlan/planInformationPicture.png");
        resultMap.put("planDetailInfos",convertPlanDetailInfo(wjSalaryPlanRecordService.queryWjSalaryPlanRecordByPlanId(planSummaryInfo.getPlanId())));
        resultMap.put("buttonTextMessage","去融桥宝存钱罐查看收益");
        return resultMap;
    }
    /**
     * 2.定制工资计划(初始化)
     * @param userId
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> addMySalaryPlanInit(String userId) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("title","定制工资计划");
        WjSalaryPlanDTO wjSalaryPlan = wjSalaryPlanService.addMySalaryPlanInit(userId);
        if(wjSalaryPlan == null) {
        	wjSalaryPlan = new WjSalaryPlanDTO();
        }
        resultMap.put("bankName","银行卡");
        resultMap.put("bankCard","");
        resultMap.put("incomeChannelName","存钱罐");
        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);

        resultMap.put("incomeRate",MessageFormat.format("T金所{0}%",userAccountQueryMapper.queryTaReceiveRate()));
        resultMap.put("depositDateText","几号存");
        resultMap.put("depositDate",wjSalaryPlan.getPlanDate());
        resultMap.put("depositDateTips","建议为发工资第二天");
        resultMap.put("depositDateDesc","");
        if(StringUtils.isNotEmpty(wjSalaryPlan.getPlanDate())) {
        	String panDate = wjSalaryPlan.getPlanDate();
        	String depositDate = MySalaryPlanDateUtils.getDepositDateString(panDate);
        	String depositDateDesc = "<font color='#B8CDD6'>每月</font><font color='#FFBD30'>{0}</font><font color='#B8CDD6'>日23:59前转入存钱罐,</font><font color='#FFBD30'>{1}</font></font><font color='#B8CDD6'>执行第一期</font>";
            resultMap.put("depositDateDesc",MessageFormat.format(depositDateDesc,panDate,depositDate));
        }

        resultMap.put("depositAmountText","存多少");
        resultMap.put("depositAmount","");
        if(!"0".equals(wjSalaryPlan.getAmount())) {
            resultMap.put("depositAmount", wjSalaryPlan.getAmount());
        }
        resultMap.put("depositAmountTips","建议转100元以上");
        resultMap.put("depositAmountDesc","");
        String amount = getDepositAmount(wjSalaryPlan.getAmount());
        if(StringUtils.isNotEmpty(amount)) {
        	resultMap.put("depositAmountDesc",MessageFormat.format("<font color='#B8CDD6'>预计首月每天可以赚</font><font color='#FFBD30'>{0}</font><font color='#B8CDD6'>元</font>",amount));
        }
        resultMap.put("salaryCardText","工资卡");
        resultMap.put("cardId",wjSalaryPlan.getCardId());
        resultMap.put("bankCardShowName","");
        if(StringUtils.isNotEmpty(wjSalaryPlan.getBankName())){
        	resultMap.put("bankCardShowName",wjSalaryPlan.getBankName() + MessageFormat.format("[{0}]", SplitUtil.splitLastStr(wjSalaryPlan.getBankCardNo(), 4)));
        }
        //String limitAmount = commConfigMap.get(BizCommonConstant.MY_SALARY_PLAN_LIMIT_AMOUNT);
        //resultMap.put("limitAmountTips",MessageFormat.format("<font color='#B8CDD6'>单笔限额</font><font color='#FFBD30'>{0}</font><font color='#B8CDD6'>万，单日无限额</font>",limitAmount));
        
        resultMap.put("limitAmountTips","");
        if(StringUtils.isNotEmpty(wjSalaryPlan.getLimitAmountTips())) {
        	resultMap.put("limitAmountTips",MessageFormat.format("<font color='#B8CDD6'>{0}</font>",wjSalaryPlan.getLimitAmountTips()));
        }
        resultMap.put("limitAmount",wjSalaryPlan.getLimitAmount());
        resultMap.put("buttonTextMessage","确定");
        resultMap.put("protocolText","同意并已仔细阅读");
        resultMap.put("productDetailsText","<<融桥宝平台服务协议>>");
        resultMap.put("financingDetailsText","<<T金所收益权产品认购协议>>");
        resultMap.put("productDetailsUrl",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/api/v3.6/app/mySalaryPlan/client-platform");
        resultMap.put("financingDetailsUrl",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/api/v3.6/app/mySalaryPlan/user-agreement");
        resultMap.put("planCode",wjSalaryPlan.getId());
        return resultMap;
    }
    /**
     * 3.定制工资计划(提交)
     * @param query
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> addMySalaryPlan(MySalaryPlanQueryDTO query) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Integer updcnt= wjSalaryPlanService.addMySalaryPlan(query.getUserId(), query.getPlanCode(),
        		query.getCardId(), query.getDepositDate(), query.getDepositAmount());
        if(updcnt==1){
            resultMap.put("code","204400");
            resultMap.put("message","提交成功");
            return resultMap;
        }
        resultMap.put("code","204401");
        resultMap.put("message","提交失败");
        return resultMap;
    }
    /**
     *  4.定制工资计划-SMS(初始化)
     * @param userId
     * @param planCode
     * @param bizType
     * @return
     */
    @Override
    public Map<String, Object> mySalaryPlanSendSmsInit(String userId, String planCode, String bizType) {
//        Map<String, Object> resultMap=new HashMap<String, Object>();
//        resultMap.put("validateButtonText","获取验证码");
//        resultMap.put("validateButtonTips","请输入验证码");
//        resultMap.put("buttonTextMessage","取消|确定");
//        WjSalaryPlanDTO wjSalaryPlanDTO=wjSalaryPlanService.queryWjSalaryPlanById(planCode);
//        if(wjSalaryPlanDTO==null){
//            throw new AppException("工资计划不存在!");
//        }
//        UserInfoDTO userInfoDTO = userInfoQueryService.queryUserInfoById(userId);
//        if("addPlan".equals(bizType)){
//            resultMap.put("title",MessageFormat.format("<font color=#4a4a4a>每月</font><font color=#FFBD30>{0}日</font><font color=#4a4a4a>转入</font><font color=#FFBD30>{1}</font><font color=#4a4a4a>元</font>"
//            ,wjSalaryPlanDTO.getPlanDate(),wjSalaryPlanDTO.getAmount()));
//            Map<String, Object> messageMap = addPlanSendSms(userId,planCode,userInfoDTO.getPhone(),true);
//            resultMap.put("remainTime", messageMap.get("remainTime"));
//            resultMap.put("bizOrderNo", messageMap.get("bizOrderNo"));
//        }else{
//        	Map<String, Object> messageMap = cancelPlanSendSms(userId,planCode,userInfoDTO.getPhone(),true);
//            resultMap.put("remainTime", messageMap.get("remainTime"));
//            resultMap.put("title","取消工资理财计划");
//        }
//        resultMap.put("displayText",MessageFormat.format("输入手机尾号{0}接收的验证码",SplitUtil.splitLastStr(wjSalaryPlanDTO.getPhone(),4)));
//        return resultMap;
    	return null;
    }
    /**
     * 5.发送短信验证码-SMS(获取文字验证码)
     * @param userId
     * @param planCode
     * @param bizType
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> mySalaryPlanSendSms(String userId, String planCode, String bizType) {
//        UserInfoDTO userInfoDTO = userInfoQueryService.queryUserInfoById(userId);
//        Map<String, Object> resultMap=new HashMap<String, Object>();
//        if("addPlan".equals(bizType)){
//        	resultMap = addPlanSendSms(userId,planCode,userInfoDTO.getPhone(),false);
//        } else {
//        	resultMap = cancelPlanSendSms(userId,planCode,userInfoDTO.getPhone(),false);
//        }
//        return resultMap;
        return null;
    }
    
    private Map<String, Object> addPlanSendSms(String userId, String planCode,String phone,Boolean flg) {
    	Map<String, Object> resultMap=new HashMap<String, Object>();
    	resultMap.put("bizOrderNo", "");
    	// 如果没有绑过卡,走易宝申请绑卡
        String orderNo = wjSalaryPlanService.bindCard(userId, planCode);
        if(StringUtils.isNotEmpty(orderNo)) {
            resultMap.put("remainTime", "60");
            resultMap.put("bizOrderNo", orderNo);
            resultMap.put("message", "验证码已发送至手机");
            return resultMap;
        }
        
        MessageDTO md = phoneMessageService.sendTextMessage(phone, "my_salary_plan_sms");
        if (!md.getSendFlag()) {
        	if(flg) {
	       		resultMap.put("remainTime", "0");
	       		return resultMap;
        	}
            throw new AppException(md.getSendFlagMessage());
        }
        
        resultMap.put("remainTime", md.getVaildeTime());
        resultMap.put("message", "验证码已发送至手机");
        return resultMap;
    }
    
    private Map<String, Object> cancelPlanSendSms(String userId, String planCode,String phone,Boolean flg) {
    	Map<String, Object> resultMap=new HashMap<String, Object>();
    	MessageDTO md = phoneMessageService.sendTextMessage(phone, "my_salary_plan_sms_cancel_plan");
        if (!md.getSendFlag()) {
        	if(flg) {
        		 resultMap.put("remainTime", "0");
        		return resultMap;
        	}
            throw new AppException(md.getSendFlagMessage());
        }
        
        resultMap.put("remainTime", md.getVaildeTime());
        resultMap.put("message", "验证码已发送至手机");
        return resultMap;
    }
    
    /**
     * 6.定制工资计划-SMS(提交)
     * @param userId
     * @param planCode
     * @param code
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> addMySalaryPlanSendSmsSave(String userId, String planCode,String orderNo, String code) {
    	wjSalaryPlanService.addMySalaryPlanSendSmsSave(userId, planCode, orderNo, code, "my_salary_plan_sms");
        Map<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("code","204500");
        resultMap.put("message","定制工资计划成功");
        return resultMap;
    }
    /**
     * 7.取消工资计划-SMS
     * @param userId
     * @param planCode
     * @param code
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> cancelMySalaryPlanSendSms(String userId, String planCode, String code) {
//    	 UserInfoDTO userInfoDTO = userInfoQueryService.queryUserInfoById(userId);
//         MessageDTO message = phoneMessageService.checkValidationCode(userInfoDTO.getPhone(), code, "my_salary_plan_sms_cancel_plan");
//         if (!message.getSendFlag()) {
//             throw new AppException(message.getSendFlagMessage());
//         }
//         Integer updCnt = wjSalaryPlanService.cancelMySalaryPlan(userId, planCode);
//         Map<String, Object> resultMap=new HashMap<String, Object>();
//         if(updCnt ==1) {
//        	 resultMap.put("code","204600");
//             resultMap.put("message","计划删除成功,以往交易记录请到账单查询|我知道了");
//             resultMap.put("buttonMessageText","计划删除成功,以往交易记录请到账单查询|我知道了");
//             return resultMap;
//         }
//         resultMap.put("code","204601");
//         resultMap.put("message","取消工资计划失败");
//         resultMap.put("buttonMessageText","");
//        return resultMap;
    	return null;
    }

    private List<PlanDetailInfoDTO> convertPlanDetailInfo(List<WjSalaryPlanRecordDTO> wjSalaryPlanRecords) {
        List<PlanDetailInfoDTO> planDetailInfos=new ArrayList<PlanDetailInfoDTO>();
        if(wjSalaryPlanRecords==null||wjSalaryPlanRecords.isEmpty()){
            return planDetailInfos;
        }
        PlanDetailInfoDTO planDetailInfoDTO=null;
        for(WjSalaryPlanRecordDTO dto:wjSalaryPlanRecords){
            planDetailInfoDTO=new PlanDetailInfoDTO();
            planDetailInfoDTO.setPlanAmount(dto.getAmount());
            planDetailInfoDTO.setPlanDate(DateUtil.getMonthAndDay(dto.getRecordDate()));
            planDetailInfoDTO.setIsShowIcon("true");
            if(PlanConstant.PLAN_STATUS_COMPLETE.getCode().equals(dto.getRecordStatus())){
                planDetailInfoDTO.setPlanAmount("");
                planDetailInfoDTO.setPlanStatusDesc(PlanConstant.PLAN_STATUS_COMPLETE.getValue());
            }else if(PlanConstant.PLAN_STATUS_EXECUTE_COMPLETE.getCode().equals(dto.getRecordStatus())){
                planDetailInfoDTO.setPlanStatusDesc(PlanConstant.PLAN_STATUS_EXECUTE_COMPLETE.getValue());
            }
            else if(PlanConstant.PLAN_STATUS_INTO.getCode().equals(dto.getRecordStatus())){
                planDetailInfoDTO.setIsShowIcon("false");
                planDetailInfoDTO.setPlanStatusDesc(PlanConstant.PLAN_STATUS_INTO.getValue());
            }
            else if(PlanConstant.PLAN_STATUS_EXECUTING.getCode().equals(dto.getRecordStatus())){
                planDetailInfoDTO.setIsShowIcon("false");
                planDetailInfoDTO.setPlanStatusDesc(PlanConstant.PLAN_STATUS_EXECUTING.getValue());
            }
            else if(PlanConstant.PLAN_STATUS_EXECUTE_FAIL.getCode().equals(dto.getRecordStatus())){
                planDetailInfoDTO.setIsShowIcon("false");
                planDetailInfoDTO.setPlanStatusDesc(PlanConstant.PLAN_STATUS_EXECUTE_FAIL.getValue());
            }
            planDetailInfos.add(planDetailInfoDTO);
        }
        return planDetailInfos;
    }
    /**
     * 12.定制工资计划-初始化-动态提示文案
     * @param depositAmount
     * @param depositDate
     * @since 3.6
     * @return
     */
    @Override
    public Map<String, Object> dynamicTipsMySalaryPlanInit(String depositAmount, String depositDate) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        resultMap.put("depositAmountDesc","");
        resultMap.put("depositDateDesc","");
        String amount = getDepositAmount(depositAmount);
        if(!StringUtils.isEmpty(amount)){
            resultMap.put("depositAmountDesc",MessageFormat.format("<font color='#B8CDD6'>预计首月每天可以赚</font><font color='#FFBD30'>{0}</font><font color='#B8CDD6'>元</font>",amount));
        }
        if(!StringUtils.isEmpty(depositDate)) {
        	String depositDateDesc = "<font color='#B8CDD6'>每月</font><font color='#FFBD30'>{0}</font><font color='#B8CDD6'>日23:59前转入存钱罐,</font><font color='#FFBD30'>{1}</font></font><font color='#B8CDD6'>执行第一期</font>";
            resultMap.put("depositDateDesc",MessageFormat.format(depositDateDesc,depositDate,MySalaryPlanDateUtils.getDepositDateString(depositDate)));
        }
        return resultMap;
    }
    
    private String getDepositAmount(String depositAmount) {
    	if(StringUtils.isEmpty(depositAmount) || Integer.parseInt(depositAmount) == 0){
    		return "";
    	}
    	Map<String,Object> map = mySalaryPlanMapper.queryTAccountFundWanInfo();
    	if(map != null && map.get("receiveRate") != null) {
    		String receiveRate = map.get("receiveRate").toString();
        	BigDecimal amount = new BigDecimal(receiveRate).multiply(new BigDecimal(0.0001).multiply(new BigDecimal(depositAmount)));
        	java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
        	return myformat.format(amount);
    	}
    	return "";
    }
    
}
