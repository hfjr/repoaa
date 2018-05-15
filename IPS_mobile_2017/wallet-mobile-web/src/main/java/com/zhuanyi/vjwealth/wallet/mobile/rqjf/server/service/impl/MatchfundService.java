package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.MapperUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.MatchfundApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IMatchfundMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IMatchfundService;
@Service
public class MatchfundService implements IMatchfundService {

	@Autowired
	private IMatchfundMapper matchfundMapper;
	@Override
	public Map<String, Object> queryProductList(String page,String auction,String location,String buyType,String auctionStatus,String priceArea) {
		//TODO .. 分页搜索问题遗留
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,Object>> auctionList =matchfundMapper.queryProductList((Integer.parseInt(page) - 1) * 10,
				MapperUtils.splitQueryCondition(auction),
				MapperUtils.splitQueryCondition(location),
				MapperUtils.splitQueryCondition(buyType),
				MapperUtils.splitQueryCondition(auctionStatus),
				MapperUtils.splitQueryCondition(priceArea)
				);
		//	getListRows(userId, (Integer.parseInt(page) - 1) * 10);
		//	2. 包装分页信息
		returnMap.put("productList", auctionList);
		returnMap.put("isMore", false);
		if (auctionList != null && auctionList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryProductDetail(String userId,String productId) {
		Map<String,Object> returnMap=matchfundMapper.queryProductDetail(userId, productId);
		returnMap.put("productPicList", MapperUtils.parseJSONArrayByStr(returnMap.get("productPicList")));
		return returnMap;
	}

	@Override
	public Map<String, Object> applyInit(String userId, String productId) {
		
		return matchfundMapper.applyInit(userId);
	}

	@Override
	public Map<String, Object> doApply(MatchfundApplyDTO matchfundApplyDTO) {
		
		if(matchfundMapper.checkCanApply(matchfundApplyDTO.getUserId(), matchfundApplyDTO.getProductId())>0){
			throw new AppException("尊敬的用户,您已提交申请,无须重复提交");
		}
		
		matchfundMapper.doApply(matchfundApplyDTO);
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put(  "message","尊敬的用户|您的申请已提交成功");
		return returnMap;
	}

	@Override
	public List<Map<String,Object>>  queryBannerInfo() {
		
		return matchfundMapper.queryBannerInfo();
	}

	
	@Override
	public String bidAnnouncement(String productId) {
	
		return matchfundMapper.queryBidAnnouncement(productId);
	}

	
	@Override
	public String introductionSubjectMatter(String productId) {
		
		return matchfundMapper.queryIntroductionSubjectMatter(productId);
	}

}
