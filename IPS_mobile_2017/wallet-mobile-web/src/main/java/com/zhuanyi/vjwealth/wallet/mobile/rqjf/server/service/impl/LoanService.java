package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.LoanApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.ILoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ILoanService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserInviteInfoMapper;
@Service
public class LoanService implements ILoanService {
	@Autowired
	private ILoanMapper loanMapper;
	
	@Autowired
	private IUserInviteInfoMapper userInviteInfoMapper;
	
	@Override
	public Map<String, Object> applyInit(String userId, String productType) {
		
		return loanMapper.applyInit(userId, productType);
	}

	@Override
	public Map<String, Object> doApply(LoanApplyDTO loanApplyDTO) {
		
		if(loanMapper.checkCanApply(loanApplyDTO.getUserId(), loanApplyDTO.getProductType())>0){
			throw new AppException("尊敬的用户,您已提交申请,无须重复提交");
		}
		
		//借款万为单位
		loanApplyDTO.setLoanAmt(new BigDecimal(loanApplyDTO.getLoanAmt()).multiply(new BigDecimal("10000")).toString());
		loanMapper.doApply(loanApplyDTO);
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put(  "message","尊敬的用户|您的申请已提交成功");
		return returnMap;
	}
	
	@Override
	public Map<String, Object> doApplyV2(LoanApplyDTO loanApplyDTO) {
		
		if(loanMapper.checkCanApply(loanApplyDTO.getUserId(), loanApplyDTO.getProductType())>0){
			throw new AppException("尊敬的用户,您已提交申请,无须重复提交");
		}
		
		//推荐人校验
		String recommendPhone=loanApplyDTO.getRecommendPhone();
		if(StringUtils.isNotBlank(recommendPhone)){
			if(userInviteInfoMapper.queryRecommenderExitByPhone(recommendPhone)<1){
				throw new AppException("推荐人不存在,请确认填写是否正确");
			}
			
			if(userInviteInfoMapper.querytRecommenderIsSelfByPhone(recommendPhone, loanApplyDTO.getUserId())>0){
				throw new AppException("推荐人不能是自己");
			}
		}
		
		//借款万为单位
		loanApplyDTO.setLoanAmt(new BigDecimal(loanApplyDTO.getLoanAmt()).multiply(new BigDecimal("10000")).toString());
		loanMapper.doApplyV2(loanApplyDTO);
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put(  "message","尊敬的用户|您的申请已提交成功");
		return returnMap;
	}

	@Override
	public Map<String, Object> queryBorrowList(String page,String userId) {
		
		//TODO .. 分页搜索问题遗留
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,Object>> borrowList =loanMapper.queryBorrowList((Integer.parseInt(page) - 1) * 10,userId);
		//	2. 包装分页信息
		returnMap.put("borrowList", borrowList);
		returnMap.put("isMore", false);
		if (borrowList != null && borrowList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryRepayPlan(String userId, String loanId) {
		
		Map<String,Object> returnMap=new HashMap<String, Object>();
		returnMap.put("repayPlanList", loanMapper.queryRepayPlan(loanId));
		return returnMap;
	}

	@Override
	public List<Map<String,Object>> queryBannerInfo() {
		
			
		return loanMapper.queryBannerInfo();
	}


}
