package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.AuctionApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IAuctionService;

@Controller
public class AuctionController {

	@Autowired
	private IAuctionService auctionService;

	
	
	
	@RequestMapping("/api/auction/product/queryProductList/v1.0")
	@AppController
	public Object queryProductList(String page, String sort,String productType, String auction, String location, String buyType, String auctionStatus) {
		validateQueryProductList(page, sort, productType, auction, location, buyType, auctionStatus);
		return auctionService.queryProductList(page, sort,productType, auction, location, buyType, auctionStatus);
	}

	

	@RequestMapping("/api/auction/product/queryProductDetail/v1.0.security")
	@AppController
	public Object queryProductDetail(String userId, String productId) {
		validatequeryProductDetail(productId);
		return auctionService.queryProductDetail(userId, productId);
	}
	
	
	

	@RequestMapping("/api/auction/product/applyInit/v1.0.security")
	@AppController
	public Object applyInit(String userId) {

		return auctionService.applyInit(userId);
	}
	
	
	


	@RequestMapping("/api/auction/product/doApply/v1.0.security")
	@AppController
	public Object doApply(AuctionApplyDTO auctionApplyDTO) {
		validateDoApply(auctionApplyDTO);
		return auctionService.doApply(auctionApplyDTO);
	}
	@RequestMapping("/api/auction/product/doApply/v2.0.security")
	@AppController
	public Object doApplyV2(AuctionApplyDTO auctionApplyDTO) {
		validateDoApply(auctionApplyDTO);
		return auctionService.doApplyV2(auctionApplyDTO);
	}
	
	

	@RequestMapping("/api/auction/banner/queryBannerInfo/v1.0")
	@AppController
	public Object queryBannerInfo() {

		return auctionService.queryBannerInfo();
	}
	
	@RequestMapping("/api/auction/product/bidAnnouncement/v1.0")
	public String bidAnnouncement(String productId,Model model) {
		
		ValidatorUtils.validateNull(productId, "productId");
		
		model.addAttribute("bidAnnouncementContent", auctionService.bidAnnouncement(productId));
		return "/rqjf/bidAnnouncement";
	}
	
	@RequestMapping("/api/auction/product/introductionSubjectMatter/v1.0")
	public String introductionSubjectMatter(String productId,Model model) {
		ValidatorUtils.validateNull(productId, "productId");
		model.addAttribute("introductionSubjectMatterContent", auctionService.introductionSubjectMatter(productId));
		return "/rqjf/introductionSubjectMatterContent";
	}
	
	private void validateQueryProductList(String page, String sort,String productType, String auction, String location, String buyType, String auctionStatus){
		ValidatorUtils.validatePage(page);
	}
	
	
	private void validateDoApply(AuctionApplyDTO auctionApplyDTO){
		String realName=auctionApplyDTO.getRealName();
		String phone=auctionApplyDTO.getPhone();
		String idNo=auctionApplyDTO.getIdNo();
		ValidatorUtils.validateNull(realName, "realName");
		ValidatorUtils.validateNull(phone, "phone");
		ValidatorUtils.validateIDCard(idNo);
	}
	
	
	private void validatequeryProductDetail(String productId){
		ValidatorUtils.validateNull(productId, "productId");
	}   
	

}
