package com.zhuanyi.vjwealth.wallet.mobile.product.server.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.LableValueDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductDetailQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductDetailQueryV6DTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductDetailQueryV7DTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductInvestmentListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductListQueryOldDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductListQueryV2DTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductListQueryV4DTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductListQueryV6DTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IProductFinaceMapper;

@Service
public class ProductQueryService implements IProductQueryService {

	@Autowired
	private IProductFinaceMapper productFinaceMapper;
    @Autowired
    private ICommConfigsQueryService commConfigsQueryService;


	@Override
	public Map<String,Object> queryProductListForOld(String page) {
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;

		List<ProductListQueryOldDTO> list = productFinaceMapper.queryProductListForOld(currentPage);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}

		return returnMap;
	}


	@Override
	public Map<String,Object> queryProductList(String page) {
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;

		List<ProductListQueryDTO> list = productFinaceMapper.queryProductList(currentPage);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}

		return returnMap;
	}


	@Override
	public Map<String,Object> queryProductListV4(String page) {
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;

		List<ProductListQueryV4DTO> list = productFinaceMapper.queryProductListV4(currentPage);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}

		return returnMap;
	}

	@Override
	public Map<String,Object> queryProductListV6(String userId,String uuid,String productType, String page,String orderBy) {
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;

		List<ProductListQueryV6DTO> list = productFinaceMapper.queryProductListV6(currentPage,productType,orderBy);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("isShowMyInvestment", "false");
		if(StringUtils.isNotEmpty(userId)) {
			if(productFinaceMapper.countMyInvestment(userId) > 0) {
				returnMap.put("isShowMyInvestment", "true");
			}
		}
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}

		return returnMap;
	}

	@Override
	public Map<String, Object> queryHomeNewPersonProductList(String userId, String uuid,  String orderBy) {


		List<ProductListQueryV6DTO> list = productFinaceMapper.queryHomeNewPersonProductList(orderBy);

		Map<String,Object> returnMap = new HashMap<String,Object>();
//		if(StringUtils.isNotEmpty(userId)) {
//			if(productFinaceMapper.countMyInvestment(userId) > 0) {
//				returnMap.put("isShowMyInvestment", "true");
//			}
//		}
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryCommonProductList(String userId, String uuid, String orderBy) {
		List<ProductListQueryV6DTO> list = productFinaceMapper.queryCommonProductList(orderBy);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(userId)) {
			if(productFinaceMapper.countMyInvestment(userId) > 0) {
				returnMap.put("isShowMyInvestment", "true");
			}
		}
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

	@Override
	public Map<String,Object> queryProductListV2(String page) {
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;

		List<ProductListQueryV2DTO> list = productFinaceMapper.queryProductListV2(currentPage);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}

		return returnMap;
	}



	@Override
	public ProductDetailQueryDTO queryProductDetail(String userId,String productId) {
		if(StringUtils.isBlank(productId)){
			throw new AppException("查询产品详情，产品编号不能为空");
		}
		if(StringUtils.isBlank(userId)){
			throw new AppException("查询产品详情，用户ID不能为空");
		}
		ProductDetailQueryDTO pdd = productFinaceMapper.queryProductDetail(userId,productId);
		if(pdd == null){
			throw new AppException("查询产品详情信息失败");
		}
		if(StringUtils.isNotBlank(pdd.getBorrowerStr())){
			Map<String,Object> borrower = JSONObject.parseObject(pdd.getBorrowerStr());
			if(borrower.get("realName")!=null){
				String realName = (String) borrower.get("realName");
				borrower.put("realName",handlerUserName(realName));//姓+*；例如李四，转成李*
			}
			pdd.setBorrower(borrower);
		}
		return pdd;
	}

	public ProductDetailQueryDTO queryProductDetailV2(String productId) {
		if(StringUtils.isBlank(productId)){
			throw new AppException("查询产品详情，产品编号不能为空");
		}
		ProductDetailQueryDTO pdd = productFinaceMapper.queryProductDetailV2(productId);
		if(pdd == null){
			throw new AppException("查询产品详情信息失败");
		}
		if(StringUtils.isNotBlank(pdd.getBorrowerStr())){
			Map<String,Object> borrower = JSONObject.parseObject(pdd.getBorrowerStr());
			if(borrower.get("realName")!=null){
				String realName = (String) borrower.get("realName");
				borrower.put("realName",handlerUserName(realName));//姓+*；例如李四，转成李*
			}
			pdd.setBorrower(borrower);
		}
		return pdd;
	}

	public Map<String, Object> queryProductDetailV6(String userId,String uuid,String productId) {
		 Map<String, Object> returnMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(productId)){
			throw new AppException("查询产品详情，产品编号不能为空");
		}
		ProductDetailQueryV6DTO pdd = productFinaceMapper.queryProductDetailV6(userId,productId);
		if(pdd == null){
			throw new AppException("查询产品详情信息失败");
		}
		boolean noVipPermission = productFinaceMapper.queryUserBuyVIPProductPermission(userId, productId);

		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		returnMap.put("annualYieldLabel", "年化收益");
		returnMap.put("annualYield", pdd.getAnnualYield());
		returnMap.put("principalPromiseIconUrl", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/principalPromise.png");
		returnMap.put("projectTermLabel", "项目期限");
		returnMap.put("projectTerm", pdd.getProjectTerm());
		returnMap.put("startInvestmentAmountLabel", "起投");
		returnMap.put("startInvestmentAmount", pdd.getStartInvestmentAmount());
		returnMap.put("guaranteeContentsLabel", "保险信用保证险");
		returnMap.put("guaranteeContentsIconUrl", ""/*commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/zhongan_baoxian_iv.png"*/);
//		returnMap.put("guaranteeContents","由蚂蚁金服、腾讯、中国平安等知名企业联合创立。|提供全额保单，保障本息到期兑付。");
		returnMap.put("guaranteeContents","由蚂蚁金服、腾讯、中国平安等知名企业联合创立。|到期兑付。");
		returnMap.put("dualInstructionLabel", "交易说明");
		@SuppressWarnings("serial")
		List<LableValueDTO> dualInstruction=new ArrayList<LableValueDTO>(){};
		dualInstruction.add(new LableValueDTO("到期时间",pdd.getExpirationDate()));
		dualInstruction.add(new LableValueDTO("起息方式","投资当日计息"));
		dualInstruction.add(new LableValueDTO("到账说明","到期后1个自然日还本付息"));
//		dualInstruction.add(new LableValueDTO("逾期还款约定","众安保险进行赔付,确保投资人应收本息如期兑付"));
		dualInstruction.add(new LableValueDTO("回款须知","该项目允许提前回款，按截至提前到期日的实际天数计算利息"));
		if("yes".equals(pdd.getIsPreExpire())){
			dualInstruction.add(new LableValueDTO("备注", MessageFormat.format("本理财产品{0}已提前到期",pdd.getWjAdvanceDueDate())));
		}
		returnMap.put("dualInstruction", dualInstruction);
		returnMap.put("projectIntroductionLabel", "项目介绍");
		returnMap.put("projectIntroductionIconUrl", ""/*commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/project_introduction.png"*/);
		returnMap.put("projectIntroduction", pdd.getProjectIntroduction());
		returnMap.put("newPersonMark", pdd.getNewPersonMark());
		returnMap.put("newPersonExclusiveIconUrl", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/newperson_exclusive.png");
		returnMap.put("productId", pdd.getProductId());
		returnMap.put("productCode", pdd.getProductCode());
		returnMap.put("productName", pdd.getProductName());
		returnMap.put("remainingInvestment", pdd.getRemainingInvestment());
		returnMap.put("investmentRecord", pdd.getInvestmentRecord());
		returnMap.put("whetherInvestment", pdd.getWhetherInvestment());
        returnMap.put("investmentInformation", pdd.getInvestmentInformation());
		returnMap.put("borrowerType", pdd.getBorrowerType());

		if (noVipPermission && !"卖完了".equals(pdd.getInvestmentInformation()) && !"产品未开售".equals(pdd.getInvestmentInformation())) {//VIP产品且用户没有购买权限且产品已开售未售完
			returnMap.put("investmentInformation", "特定投资");
            returnMap.put("whetherInvestment", "false");
        }

		if(StringUtils.isNotBlank(pdd.getBorrowerStr())){
			Map<String,Object> borrower = JSONObject.parseObject(pdd.getBorrowerStr());
			if(borrower.get("realName")!=null){
				String realName = (String) borrower.get("realName");
				borrower.put("realName",handlerUserName(realName));//姓+*；例如李四，转成李*
			}
			returnMap.put("borrower", borrower);
		}
		return returnMap;
	}
	
	public Map<String, Object> queryProductDetailV7(String userId,String uuid,String productId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(productId)){
			throw new AppException("查询产品详情，产品编号不能为空");
		}
		ProductDetailQueryV7DTO pdd = productFinaceMapper.queryProductDetailV7(userId,productId);
		if(pdd == null){
			throw new AppException("查询产品详情信息失败");
		}
		boolean noVipPermission = productFinaceMapper.queryUserBuyVIPProductPermission(userId, productId);
		
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		returnMap.put("annualYieldLabel", "年化收益");
		returnMap.put("annualYield", pdd.getAnnualYield());
		returnMap.put("couponMark", pdd.getCouponMark());
		returnMap.put("monthDiff", pdd.getMonthDiff());
		returnMap.put("packageMark", pdd.getPackageMark());
		returnMap.put("totalFinancing", pdd.getTotalFinancing());
		returnMap.put("principalPromiseIconUrl", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/principalPromise.png");
		returnMap.put("projectTermLabel", "项目期限");
		returnMap.put("projectTerm", pdd.getProjectTerm());
		returnMap.put("startInvestmentAmountLabel", "起投");
		returnMap.put("startInvestmentAmount", pdd.getStartInvestmentAmount());
		returnMap.put("guaranteeContentsLabel", "保险信用保证险");
		returnMap.put("guaranteeContentsIconUrl", ""/*commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/zhongan_baoxian_iv.png"*/);
//		returnMap.put("guaranteeContents","由蚂蚁金服、腾讯、中国平安等知名企业联合创立。|提供全额保单，保障本息到期兑付。");
		returnMap.put("guaranteeContents","由蚂蚁金服、腾讯、中国平安等知名企业联合创立。|到期兑付。");
		returnMap.put("dualInstructionLabel", "交易说明");
		@SuppressWarnings("serial")
		List<LableValueDTO> dualInstruction=new ArrayList<LableValueDTO>(){};
		dualInstruction.add(new LableValueDTO("到期时间",pdd.getExpirationDate()));
		dualInstruction.add(new LableValueDTO("起息方式","投资当日计息"));
		dualInstruction.add(new LableValueDTO("到账说明","到期后1个自然日还本付息"));
//		dualInstruction.add(new LableValueDTO("逾期还款约定","众安保险进行赔付,确保投资人应收本息如期兑付"));
		dualInstruction.add(new LableValueDTO("回款须知","该项目允许提前还款，按截至提前到期日的实际天数计算利息"));
		if("yes".equals(pdd.getIsPreExpire())){
			dualInstruction.add(new LableValueDTO("备注", MessageFormat.format("本理财产品{0}已提前到期",pdd.getWjAdvanceDueDate())));
		}
		returnMap.put("dualInstruction", dualInstruction);
		returnMap.put("projectIntroductionLabel", "项目介绍");
		returnMap.put("projectIntroductionIconUrl", ""/*commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/project_introduction.png"*/);
		returnMap.put("projectIntroduction", pdd.getProjectIntroduction());
		returnMap.put("newPersonMark", pdd.getNewPersonMark());
		returnMap.put("newPersonExclusiveIconUrl", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+ "/static/h5/img/newperson_exclusive.png");
		returnMap.put("productId", pdd.getProductId());
		returnMap.put("productCode", pdd.getProductCode());
		returnMap.put("productName", pdd.getProductName());
		returnMap.put("remainingInvestment", pdd.getRemainingInvestment());
		returnMap.put("investmentRecord", pdd.getInvestmentRecord());
		returnMap.put("whetherInvestment", pdd.getWhetherInvestment());
		returnMap.put("investmentInformation", pdd.getInvestmentInformation());
		returnMap.put("borrowerType", pdd.getBorrowerType());
		returnMap.put("bidAnnouncement", pdd.getBidAnnouncement());
		
		if (noVipPermission && !"卖完了".equals(pdd.getInvestmentInformation()) && !"产品未开售".equals(pdd.getInvestmentInformation())) {//VIP产品且用户没有购买权限且产品已开售未售完
			returnMap.put("investmentInformation", "特定投资");
			returnMap.put("whetherInvestment", "false");
		}
		
		if(StringUtils.isNotBlank(pdd.getBorrowerStr())){
			Map<String,Object> borrower = JSONObject.parseObject(pdd.getBorrowerStr());
			if(borrower.get("realName")!=null){
				String realName = (String) borrower.get("realName");
				borrower.put("realName",handlerUserName(realName));//姓+*；例如李四，转成李*
			}
			returnMap.put("borrower", borrower);
		}
		return returnMap;
	}
	
	public String queryProductBidAnnouncementDetail(String productId) {
		
		return productFinaceMapper.queryProductBidAnnouncementDetail(productId);
	}

	private String handlerUserName(String userName){
		if(!StringUtils.isBlank(userName)){
			String returnName = userName.substring(0, 1);
			String secondName = userName.substring(1,userName.length());
			if(secondName.length()>0){
				for(int i=0;i<secondName.length();i++){
					returnName = returnName+"*";
				}
			}
			return returnName;
		}
		return "";
	}


	@Override
	public Map<String, Object> queryProductInvestmentRecordList(String productId) {
		if(StringUtils.isBlank(productId)){
			throw new AppException("查询产品投资记录，产品编号不能为空");
		}
		List<ProductInvestmentListQueryDTO> list = productFinaceMapper.queryProductInvestmentRecordList(productId);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);

		return returnMap;
	}


	@Override
	public Map<String, Object> queryProductInvestmentRecordListV2(String productId,String page) {
		if(StringUtils.isBlank(productId)){
			throw new AppException("查询产品投资记录，产品编号不能为空");
		}
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;

		List<ProductInvestmentListQueryDTO> list = productFinaceMapper.queryProductInvestmentRecordListV2(productId,currentPage);

		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}

		return returnMap;
	}

}
