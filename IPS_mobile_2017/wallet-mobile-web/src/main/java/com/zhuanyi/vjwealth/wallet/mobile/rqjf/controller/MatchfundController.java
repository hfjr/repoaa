package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.MatchfundApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IMatchfundService;

@Controller
public class MatchfundController {

	@Autowired
	private IMatchfundService matchfundService;

	@RequestMapping("/api/matchfund/product/queryProductList/v1.0")
	@AppController
	public Object queryProductList(String page, String auction, String location, String buyType, String auctionStatus,String priceArea) {
		
		validateQueryProductList(page, auction, location, buyType, auctionStatus, priceArea);
		
		return matchfundService.queryProductList(page, auction, location, buyType, auctionStatus,priceArea);
	}

	@RequestMapping("/api/matchfund/product/queryProductDetail/v1.0.security")
	@AppController
	public Object queryProductDetail(String userId, String productId) {
		
		validatequeryProductDetail(productId);
		
		return matchfundService.queryProductDetail(userId, productId);
	}

	@RequestMapping("/api/matchfund/product/applyInit/v1.0.security")
	@AppController
	public Object applyInit(String userId, String productId) {

		return matchfundService.applyInit(userId, productId);
	}

	@RequestMapping("/api/matchfund/product/doApply/v1.0.security")
	@AppController
	public Object doApply(MatchfundApplyDTO matchfundApplyDTO) {
		validateDoApply(matchfundApplyDTO);
		return matchfundService.doApply(matchfundApplyDTO);
	}

	@RequestMapping("/api/matchfund/banner/queryBannerInfo/v1.0")
	@AppController
	public Object queryBannerInfo() {

		return matchfundService.queryBannerInfo();
	}

	
	@RequestMapping("/api/matchfund/product/bidAnnouncement/v1.0")
	public String bidAnnouncement(String productId,Model model) {
		ValidatorUtils.validateNull(productId, "productId");
		model.addAttribute("bidAnnouncementContent", matchfundService.bidAnnouncement(productId));
		return "/rqjf/bidAnnouncement";
	}
	
	@RequestMapping("/api/matchfund/product/introductionSubjectMatter/v1.0")
	public String introductionSubjectMatter(String productId,Model model) {
		ValidatorUtils.validateNull(productId, "productId");
		model.addAttribute("introductionSubjectMatterContent", matchfundService.introductionSubjectMatter(productId));
		return "/rqjf/introductionSubjectMatterContent";
	}
	
	
	
	private void validateQueryProductList(String page, String auction, String location, String buyType, String auctionStatus,String priceArea){
		ValidatorUtils.validatePage(page);
	}
	
	
	private void validateDoApply(MatchfundApplyDTO matchfundApplyDTO){
		String realName=matchfundApplyDTO.getRealName();
		String phone=matchfundApplyDTO.getPhone();
		String idNo=matchfundApplyDTO.getIdNo();
		ValidatorUtils.validateNull(realName, "realName");
		ValidatorUtils.validateNull(phone, "phone");
		ValidatorUtils.validateIDCard(idNo);
	}
	
	
	private void validatequeryProductDetail(String productId){
		ValidatorUtils.validateNull(productId, "productId");
	}   
	
}
