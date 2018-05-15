package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fab.core.logger.BaseLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfRepayPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBProductProcess;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTInvestService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

/**
 * 抽取小赢接口
 * @ClassName： MBRemoteProductProcess
 * @Description: TODO
 * @author 胡绍鑫
 * @date 2016年3月22日 下午4:10:39
 */
@Service("MBRemoteProductProcess")
public class MBRemoteProductProcess implements IMBProductProcess {

	@Autowired
	private IYingZTInvestService yingZTInvestService;
	
	@Remote
	ISendEmailService sendEmailService;
	
	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;
	
    @Value("${yingzt.partner}")
    private String partner;

	@Value("${yingzt.email}")
	private String email;


	@Value("${yingzt.isSendEmail}")
	private String isSendEmail;


	@Value("${yingzt.isSendSms}")
	private String isSendSms;
    
	@Override
	public RfResponseDTO processOrder(BigDecimal amount, MBUserInfoDTO userinfo, String orderNo, String productId, String tradeId) {
		BaseLogger.audit("MBRemoteProductProcess processOrder in.");
		//小赢接口要求tradeId为数值类型
		//isSendEmail、isSendSms后期需要调整，看是走公司的统一配置，还是走每个人的个性选项
		String strJson = getRfInvestParams(amount,tradeId,userinfo);

//        String strJson = "{\"amount\":\"50000\",\"email\":\"null\",\"identifyNo\":\"430422198802101067\",\"isSendEmail\":\"0\",\"isSendSms\":\"0\",\"loanId\":\"61060825386959552512\",\"mobile\":\"13701959491\",\"name\":\"恒意\",\"partnerId\":\"VJWEALTH\",\"tradeId\":\"01201603220000000668\"}";
        
		//调用小赢接口，如果失败，会抛出异常            
		String resultjson = yingZTInvestService.apiInvest(strJson);
		BaseLogger.audit("MBRemoteProductProcess processOrder resultjson:"+resultjson);
		JSONObject json = JSONObject.parseObject(resultjson);

		//如果小赢接口返回交易失败，抛出AppException异常
		if (!json.getString("tradeResult").equals("0")){
			throw new AppException(json.getString("tradeMessage"));	
		}
		RfResponseDTO dto = new RfResponseDTO();
		
		List<RfRepayPlanDTO> repayplans = JSON.parseArray(json.getString("repayPlan"), RfRepayPlanDTO.class);
		
		//根据还款期数对还款计划排序
		Collections.sort(repayplans, new Comparator<RfRepayPlanDTO>() {
			@Override
			public int compare(RfRepayPlanDTO o1, RfRepayPlanDTO o2) {
				if (o1.getPeriod() > o2.getPeriod())
					return 1;
				else
					return 0;
			}
	    });
		
		//插入还款计划
		String repayPlanNo = userAccountTransactionService.getId("RP", ISequenceService.SEQ_NAME_REPAYPLAN_SEQ);

		for(RfRepayPlanDTO item:  repayplans){
			item.setPlanRepayPrincipal(item.getPlanRepayPrincipal().divide(new BigDecimal(100)));
			item.setPlanRepayInterest(item.getPlanRepayInterest().divide(new BigDecimal(100)));
			item.setRepayPrincipal(item.getRepayPrincipal().divide(new BigDecimal(100)));
			item.setRepayInterest(item.getRepayInterest().divide(new BigDecimal(100)));
			item.setRepaymentPlanId(repayPlanNo);
			item.setOrderNo(orderNo);
			item.setUserId(userinfo.getUserId());
			item.setIsRepay("no");
			item.setTotalperiod(repayplans.size());
			switch(json.getString("repayType")){
			case "1":
				item.setRepaymentType("monthly_interest");
				break;
			case "2":
				item.setRepaymentType("repay_maturity");
				break;
			case "3":
				item.setRepaymentType("");
				break;
			case "4":
				item.setRepaymentType("");
				break;
			case "5":
				item.setRepaymentType("");
				break;
			default:
				throw new AppException("还款计划中还款类型错误");
			}
			item.setProductFlag(userinfo.getProductFlag());
			
			if (userinfo.getProductFlag().equals("greenhorn")){
				item.setGreenhornInterest(userinfo.getProductGreenhornReceiveRate().multiply(item.getPlanRepayInterest()).divide(userinfo.getProductReceiveRate(), 2, RoundingMode.HALF_DOWN));
			}else{
				item.setGreenhornInterest(BigDecimal.ZERO);
			}
			
			item.setPlanRepayDateTime(new Date(item.getPlanRepayDate()*1000));
		}		
		
		dto.setOrderNo(orderNo);
		dto.setRepayplans(repayplans);
		dto.setPolicyNo(json.getString("policyNo"));
		dto.setLoanId(json.getString("loanId"));
		dto.setTradeId(json.getString("tradeId"));
		dto.setAmount(amount);
		dto.setName(json.getString("name"));
		dto.setIdentifyNo(json.getString("identifyNo"));

		//如果返回小赢返回结果中保单凭证为空或者不存在，则发送邮件
		if (dto.getPolicyNo() == null || dto.getPolicyNo().equals("")){
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userinfo.getUserId() + "]，小赢返回保单凭证为空"));
		}
		BaseLogger.audit("MBRemoteProductProcess processOrder out.");
		return dto;
	}
	
	@Override
	public RfResponseDTO processOrder(BigDecimal amount, BigDecimal addProfit,
			MBUserInfoDTO userinfo, String orderNo, String productId,
			String tradeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}


	private String getRfInvestParams(BigDecimal amount,String tradeId, MBUserInfoDTO userinfo) {
		// email 改为统一邮件地址
		String strJson = "{\"amount\":\""+amount.multiply(new BigDecimal(100))+"\",\"email\":\""+email+"\",\"identifyNo\":\""+userinfo.getIndentityNo()
				+"\",\"isSendEmail\":\""+isSendEmail+"\",\"isSendSms\":\""+isSendSms+"\",\"loanId\":\""+userinfo.getProductLoanId()+"\",\"mobile\":\""+userinfo.getPhone()
				+"\",\"name\":\""+userinfo.getName()+"\",\"partnerId\":\""+partner+"\",\"tradeId\":\""+tradeId+"\"}";
		return strJson;
	}


	@Override
	public boolean queryOrder(String orderNo, String productId, String tradeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RfRepayPlanDTO generateAdvExpireOrder(BigDecimal amount, MBUserInfoDTO userinfo, String orderNo, String startTime, String endTime) {
		return null;
	}

	@Override
	public List<RfRepayPlanDTO> calcRepayPlanByMonthInteresting(BigDecimal amount, int firstmonthday, BigDecimal annualRating, String interestStartTime, String productEndTime, String productRepaymentType) {
		return null;
	}
}
