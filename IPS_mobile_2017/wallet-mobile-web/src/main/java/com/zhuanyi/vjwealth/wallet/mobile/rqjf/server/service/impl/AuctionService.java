package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.MapperUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.AuctionApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IAuctionMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IAuctionService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserInviteInfoMapper;
@Service
public class AuctionService implements IAuctionService {

	@Autowired
	private IAuctionMapper acAuctionMapper;
	
	@Autowired
	private IUserInviteInfoMapper userInviteInfoMapper;
	

	@Override
	public Map<String, Object> queryProductList(String page,String sort,String productType,String auction,String location,String buyType,String auctionStatus) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//1.	返回列表
		List<Map<String,Object>> auctionList =acAuctionMapper.queryProductList((Integer.parseInt(page) - 1) * 10,
				sort,
				MapperUtils.splitQueryCondition(productType),
				MapperUtils.splitQueryCondition(auction),
				MapperUtils.splitQueryCondition(location),
				MapperUtils.splitQueryCondition(buyType),
				MapperUtils.splitQueryCondition(auctionStatus));
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
		Map<String,Object> returnMap=acAuctionMapper.queryProductDetail(userId, productId);
		returnMap.put("productPicList", MapperUtils.parseJSONArrayByStr(returnMap.get("productPicList")));
		return returnMap;
	}

	@Override
	public Map<String, Object> applyInit(String userId) {
		
		return acAuctionMapper.applyInit(userId);
	}

	@Override
	public Map<String, Object> doApply(AuctionApplyDTO auctionApplyDTO) {
		
		if(acAuctionMapper.checkCanApply(auctionApplyDTO.getUserId(), auctionApplyDTO.getProductId())>0){
			throw new AppException("尊敬的用户,您已提交申请,无须重复提交");
		}
		acAuctionMapper.doApply(auctionApplyDTO);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("message", "尊敬的用户|您的申请已提交成功");
		return returnMap;
	}
	
	@Override
	public Map<String, Object> doApplyV2(AuctionApplyDTO auctionApplyDTO) {
		// 推荐人校验
		String recommendPhone = auctionApplyDTO.getRecommendPhone();
		if (StringUtils.isNotBlank(recommendPhone)) {
			if (userInviteInfoMapper.queryRecommenderExitByPhone(recommendPhone) < 1) {
				throw new AppException("推荐人不存在,请确认填写是否正确");
			}

			if (userInviteInfoMapper.querytRecommenderIsSelfByPhone(recommendPhone, auctionApplyDTO.getUserId()) > 0) {
				throw new AppException("推荐人不能是自己");
			}
		}
		if (acAuctionMapper.checkCanApply(auctionApplyDTO.getUserId(), auctionApplyDTO.getProductId()) > 0) {
			throw new AppException("尊敬的用户,您已提交申请,无须重复提交");
		}
		acAuctionMapper.doApply(auctionApplyDTO);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("message", "尊敬的用户|您的申请已提交成功");
		return returnMap;
	}

	@Override
	public List<Map<String,Object>> queryBannerInfo() {
		
		return acAuctionMapper.queryBannerInfo();
	}
	
	@Override
	public String bidAnnouncement(String productId) {
	
		return acAuctionMapper.queryBidAnnouncement(productId);
	}

	
	@Override
	public String introductionSubjectMatter(String productId) {
		
		return acAuctionMapper.queryIntroductionSubjectMatter(productId);
	}
}
