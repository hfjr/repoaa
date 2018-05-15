package com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.util.DateUtil;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.SearchCardDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.SmsResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.constant.PlanConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.PlanSummaryInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanRecordDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.mapper.IWjSalaryPlanMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.mapper.IWjSalaryPlanRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.service.IWjSalaryPlanService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WjSalaryPlanService implements IWjSalaryPlanService {

	@Remote
	private IB2CPayGatewayService b2CPayGatewayService;
	@Remote
    private IPhoneMessageService phoneMessageService;

    @Autowired
    private IWjSalaryPlanMapper wjSalaryPlanMapper;
    @Autowired
    private IWjSalaryPlanRecordMapper wjSalaryPlanRecordMapper;

    @Override
    public WjSalaryPlanDTO queryWjSalaryPlan(String userId,String planStatus) {
        return wjSalaryPlanMapper.queryWjSalaryPlan(userId,planStatus);
    }

    @Override
    public PlanSummaryInfoDTO queryWjSalaryPlanSummaryInfo(String userId) {
        return wjSalaryPlanMapper.queryWjSalaryPlanSummaryInfo(userId);
    }

    @Override
    public WjSalaryPlanDTO queryWjSalaryPlanById(String id) {
        return wjSalaryPlanMapper.queryWjSalaryPlanById(id);
    }

    @Override
    @Transactional
    public void updateMySalaryPlan(String planCode, String userId) {
		WjSalaryPlanDTO wjSalaryPlanDTO=wjSalaryPlanMapper.queryWjSalaryPlanById(planCode);
        int lockUpdateCount =wjSalaryPlanMapper.updateMySalaryPlan(planCode,userId,getPlanRecord(Integer.valueOf(wjSalaryPlanDTO.getPlanDate())));
        if(lockUpdateCount!=1){
            throw new AppException("定制工资计划失败");
        }
		WjSalaryPlanRecordDTO param=new WjSalaryPlanRecordDTO();
		param.setRecordDate(new Date());
		param.setPlanId(planCode);
		param.setUserId(userId);
		param.setRecordStatus(PlanConstant.PLAN_STATUS_COMPLETE.getCode());
        wjSalaryPlanRecordMapper.insertWjSalaryPlanRecordByPlanId(param);
		param.setRecordStatus(PlanConstant.PLAN_STATUS_INTO.getCode());
		param.setRecordDate(getPlanRecord(Integer.valueOf(wjSalaryPlanDTO.getPlanDate())));
		wjSalaryPlanRecordMapper.insertWjSalaryPlanRecordByPlanId(param);
    }
	private Date getPlanRecord(int planDay){
		Date date=new Date();
		int month=DateUtil.getDayByNow(date)<=planDay?0:1;//当月或者下月
		return DateUtil.getDateAddMonthAndSetDay(date,month,planDay);
	}
	@Override
	@Transactional
	public WjSalaryPlanDTO addMySalaryPlanInit(String userId) {
		// 1.查询工资计划是否存在
		WjSalaryPlanDTO planDTO = wjSalaryPlanMapper.queryWjSalaryPlanByUserId(userId);
		// 1.1查询工资计划不存在
		if(planDTO == null) {
			planDTO = new WjSalaryPlanDTO();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			wjSalaryPlanMapper.createWjSalaryPlan(map);
			planDTO.setId(map.get("id").toString());
		}
		// 如果没有选定工资卡，则绑定安全卡
		if(StringUtils.isEmpty(planDTO.getCardId())) {
			WjSalaryPlanDTO securityCard = wjSalaryPlanMapper.queryRechargeCardByUserId(userId);
			if(securityCard != null) {
				planDTO.setBankCode(securityCard.getBankCode());
				planDTO.setCardId(securityCard.getCardId());
				planDTO.setBankCardNo(securityCard.getBankCardNo());
				planDTO.setBankName(securityCard.getBankName());
			}
		} else {
			WjSalaryPlanDTO securityCard = wjSalaryPlanMapper.queryRechargeCardByCardId(planDTO.getCardId());
			if(securityCard == null) {
				planDTO.setBankCode("");
				planDTO.setCardId("");
				planDTO.setBankCardNo("");
				planDTO.setBankName("");
			}
		}
		// 取得银行限额
		if(StringUtils.isNotEmpty(planDTO.getBankCode())) {
			WjSalaryPlanDTO limit = wjSalaryPlanMapper.queryLimitAmountByBankCode(planDTO.getBankCode());
			if(limit != null) {
				planDTO.setLimitAmount(limit.getLimitAmount());
				planDTO.setLimitAmountTips(limit.getLimitAmountTips());
			}
		}
		return planDTO;
	}

	@Override
	@Transactional
	public Integer addMySalaryPlan(String userId, String planCode,
			String cardId, String depositDate, String depositAmount) {
		WjSalaryPlanDTO wjSalaryPlanDTO = wjSalaryPlanMapper.queryUserCardInfo(userId, cardId);
		String bankCardNo = wjSalaryPlanDTO != null ? wjSalaryPlanDTO.getBankCardNo() : null;
		return wjSalaryPlanMapper.updateMySalaryPlanInit(userId,planCode, cardId, bankCardNo, depositDate, depositAmount);
	}

	@Override
	@Transactional
	public Integer cancelMySalaryPlan(String userId, String planCode) {
		return wjSalaryPlanMapper.cancelMySalaryPlan(userId, planCode);
	}

	@Override
	public String bindCard(String userId, String planCode) {
		WjSalaryPlanDTO wjSalaryPlanDTO = wjSalaryPlanMapper.queryUserCardInfoByPlanCode(userId, planCode);
		if(StringUtils.isEmpty(wjSalaryPlanDTO.getIndentityNo())){
            throw new AppException("请实名制认证！");
        }
		String orderNo = "";
		if(bindCardExist(userId,wjSalaryPlanDTO.getBankCardNo())) {
			return orderNo;
		}
		try {
			BaseLogger.audit("申请绑卡-->start");
			MBRechargeDTO rechargeDTO=new MBRechargeDTO();
			rechargeDTO.setUserId(userId);
			rechargeDTO.setCardNo(wjSalaryPlanDTO.getBankCardNo());
			rechargeDTO.setCertNo(wjSalaryPlanDTO.getIndentityNo());
			rechargeDTO.setRealName(wjSalaryPlanDTO.getCardOwer());
			rechargeDTO.setCardBindMobilePhoneNo(wjSalaryPlanDTO.getAsideBankPhone());
			rechargeDTO.setOrderType(MBOrderInfoDTO.ORDER_TYPE_SALARY_PLAN_BANKCARD_WITHHOLD);
			SmsResultDTO smsResultDTO =  b2CPayGatewayService.bindCard(rechargeDTO);
			orderNo = smsResultDTO.getOrderNo();
			BaseLogger.audit("申请绑卡-->end");
		}catch(Exception e) {
			BaseLogger.error("申请绑卡 失败！");
			if( e instanceof AppException) {
				throw e;
			}
		}
		return orderNo;
	}
	
	@Override
	@Transactional
	public void addMySalaryPlanSendSmsSave(String userId, String planCode,String orderNo,String code, String bizType) {
		
		WjSalaryPlanDTO wjSalaryPlanDTO = wjSalaryPlanMapper.queryUserCardInfoByPlanCode(userId, planCode);
		if(StringUtils.isEmpty(wjSalaryPlanDTO.getIndentityNo())){
			throw new AppException("请实名制认证！");
		}
		try {
			// 已经绑过卡
			if(bindCardExist(userId,wjSalaryPlanDTO.getBankCardNo())) {
				MessageDTO message = phoneMessageService.checkValidationCode(wjSalaryPlanDTO.getPhone(), code, bizType);
					if (!message.getSendFlag()) {
						throw new AppException(message.getSendFlagMessage());
					}
			} else {
				BaseLogger.audit("确认绑卡-->start");
				MBRechargeDTO rechargeDTO=new MBRechargeDTO();
				rechargeDTO.setUserId(userId);
				rechargeDTO.setCardNo(wjSalaryPlanDTO.getBankCardNo());
				rechargeDTO.setCertNo(wjSalaryPlanDTO.getIndentityNo());
				rechargeDTO.setOrderNo(orderNo);
				rechargeDTO.setDynamicCode(code);
				rechargeDTO.setOrderType(MBOrderInfoDTO.ORDER_TYPE_SALARY_PLAN_BANKCARD_WITHHOLD);
				b2CPayGatewayService.confirmBindCard(rechargeDTO);
				BaseLogger.audit("确认绑卡-->end");
			}
			this.updateMySalaryPlan(planCode,userId);
		}catch(Exception e) {
			BaseLogger.error("确认绑卡 失败！");
			if( e instanceof AppException) {
				throw e;
			}
		}
	}

	// 判断是否已经绑过卡
	private Boolean bindCardExist(String userId,String cardNo) {
		MBRechargeDTO rechargeDTO=new MBRechargeDTO();
		rechargeDTO.setUserId(userId);
		rechargeDTO.setOrderType(MBOrderInfoDTO.ORDER_TYPE_SALARY_PLAN_BANKCARD_WITHHOLD);
		List<SearchCardDTO> searchCardDTO = b2CPayGatewayService.queryBindCardList(rechargeDTO);
		if(searchCardDTO != null && searchCardDTO.size() > 0) {
			for(SearchCardDTO dto : searchCardDTO) {
				if(cardNo.equals(dto.getCardNo())) {
					return true;
				}
			}
		}
		return false;
	}
}
