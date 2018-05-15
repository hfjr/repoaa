package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IRecruitMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IRecruitService;

@Service
public class RecruitService implements IRecruitService {

	@Autowired
	private IRecruitMapper recruitMapper;
	
	
	@Override
	public Map<String, Object> queryRecruitList(String page) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,Object>> auctionList =recruitMapper.queryRecruitList((Integer.parseInt(page) - 1) * 10);
		//	2. 包装分页信息
		returnMap.put("recruitList", auctionList);
		returnMap.put("isMore", false);
		if (auctionList != null && auctionList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryRecruitDetail(String recruitId) {
		return recruitMapper.queryRecruitDetail( recruitId);
	}

	@Override
	public Map<String, Object> applyInit(String userId) {
		return recruitMapper.applyInit(userId);
	}
	
	@Override
	public Map<String, Object> doApply(String recruitId, String userId, String realName, String applyPhone,String workExperience,String salaryExpectation) {
		if(recruitMapper.checkCanApply(userId, recruitId)>0){
			throw new AppException("尊敬的用户,您已提交申请,无须重复提交");
		}
		recruitMapper.doApply(recruitId, userId, realName, applyPhone, workExperience, salaryExpectation);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("message", "尊敬的用户|您的申请已提交成功");
		return returnMap;
	}


}
