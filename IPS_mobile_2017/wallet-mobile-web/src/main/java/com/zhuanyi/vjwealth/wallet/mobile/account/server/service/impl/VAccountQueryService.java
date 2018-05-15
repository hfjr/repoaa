package com.zhuanyi.vjwealth.wallet.mobile.account.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.MAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.VAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IOrderHelperService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IVAccountQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountDateUtils;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountDefaultMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;

@Service
public class VAccountQueryService implements IVAccountQueryService {
	@Autowired
	private VAccountQueryMapper accountInfoMapper;
	@Autowired
	private MAccountQueryMapper mAccountQueryMapper;
	@Autowired
	private ISendExceptionEmailService sendEmailExceptionService;	
	@Autowired
	private IOrderHelperService OrderUtilService;
	
	
	
	public Map<String, String> queryVAccountInfo(String userId) {
		Map<String,String> returnMap=new HashMap<String,String>();
		//查询v1账户可用余额和冻结余额
		Map<String,String> vAccountInfoMap=accountInfoMapper.queryVAccountInfo(userId);
		if(vAccountInfoMap==null){
			sendEmailExceptionService.sendAccountExceptionEmail(userId, "v1账户信息不存在",this.getClass(),"queryVAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		returnMap.putAll(vAccountInfoMap);
		//查询累积收益
		returnMap.putAll(accountInfoMapper.queryVAccountTotalReceive(userId));
		return AccountMapUtils.changeMapDataToMapString(returnMap);
	}
	
	
	public Map<String, String> queryVplusAccount(String userId) {
		String yestoday=AccountDateUtils.getYestodayString();
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("userId", userId);
		paramMap.put("receiveDate", yestoday);
		
		//v1账户信息
		Map<String,String> vAccountInfo=accountInfoMapper.queryVAccountOtherInfo(userId);
		if(vAccountInfo==null){
			sendEmailExceptionService.sendAccountExceptionEmail(userId, "v1账户信息不存在",this.getClass(),"queryVplusAccount");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
//		//v待确定份额
//		Map<String,String> waitForSureAmount=accountInfoMapper.queryVAccountWaitForSureAmount(userId);
//		//v已确定份额
//		Map<String,String> sureAmount=accountInfoMapper.queryVAccountAlreadySureAmount(userId);
		//v理财总收益
		Map<String,String> allReceive=accountInfoMapper.queryVAccountTotalReceive(userId);
		//v昨日收益
		Map<String,String> yestodayReceive=accountInfoMapper.queryVAccountYestodayReceive(paramMap);
		if(yestodayReceive==null){
			yestodayReceive=AccountDefaultMapUtils.getVAccountYestodayReceiveMap();
		}
		
//		vAccountInfo.putAll(waitForSureAmount);
//		vAccountInfo.putAll(sureAmount);
		vAccountInfo.putAll(allReceive);
		vAccountInfo.putAll(yestodayReceive);
	
		return AccountMapUtils.changeMapDataToMapString(vAccountInfo);
	}	
	

	public Map<String, String> queryVAccountToBalance(String userId) {
		Map<String,String> returnMap=new HashMap<String,String>();
		//v账户可用余额
		String amountAvailable=accountInfoMapper.queryVAccountCanUseAmount(userId);
		if(StringUtils.isBlank(amountAvailable)){
			sendEmailExceptionService.sendAccountExceptionEmail(userId, "v1账户信息不存在",this.getClass(),"queryVAccountToBalance");
			throw new AppException("系统繁忙,请稍后再试");
		}
		returnMap.put("amountAvailable",amountAvailable);
		returnMap.put("tipInput", "本次最多可转出"+amountAvailable+"元");
		returnMap.put("tipContent", "");
		return AccountMapUtils.changeMapDataToMapString(returnMap);
	}
	
	
	public Map<String, String> queryVAccountFundWanInfo() {
		Map<String,String> fundInfo=accountInfoMapper.queryVAccountFundWanInfo();
		if(fundInfo==null){
			sendEmailExceptionService.sendAccountExceptionEmail("", "无法获取V账户万份收益及七日年化利率",this.getClass(),"queryVAccountFundWanInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		//update by xuewentao V+ 理财 8%收益, 下一期将废弃掉 TODO
		fundInfo.put("weekReceiveRate", "8");
		
		Map<String,String> left=accountInfoMapper.queryVProductLeft();
		fundInfo.putAll(left);
		return AccountMapUtils.changeMapDataToMapString(fundInfo);
	}
	
	
	//v+进出账列表
	@Override
	public Map<String, Object> queryVAcountBill(String userId,String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		//获取v+账单信息列表
		resultList=accountInfoMapper.queryVAccountBillDetail(paramMap);
		
		//封装查询结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("isMore", "true");
		if(resultList==null){
			resultMap.put("isMore", "false");
			resultMap.put("records", resultList);
			return resultMap;
		}
		
		//补充字段
		resultList=supplementResult(resultList);
		
		if(resultList.size()<10){
			resultMap.put("isMore", "false");
		}
		resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
		return resultMap;
	}
	
	
	private List<Map<String, String>> supplementResult(List<Map<String, String>> resultList) {
		for(Map<String,String> map:resultList){
			
			map.putAll(OrderUtilService.getProcess(map.get("orderNo")));
		}
		return resultList;
	}

	
	public Map<String, Object> queryVAccountReceiveDetail(String userId, String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		//获取收益信息列表
		List<Map<String,String>> resultList=accountInfoMapper.queryVAccountReciveDetail(paramMap);
		
		//封装查询结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("isMore", "true");
		if(resultList==null){
			resultMap.put("isMore", "false");
			resultMap.put("records", resultList);
			return resultMap;
		}
		if(resultList.size()<10){
			resultMap.put("isMore", "false");
		}
		resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
		return resultMap;
	}



	@Override
	public Map<String, Object> queryVAccountFrozenDetail(String userId, String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		resultList=accountInfoMapper.queryVAccountFrozenDetail(paramMap);
		//封装查询结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("isMore", "true");
		if(resultList==null){
			resultMap.put("isMore", "false");
			resultMap.put("records", resultList);
			return resultMap;
		}
		
		//补充字段
		resultList=supplementResult(resultList);
		
		if(resultList.size()<10){
			resultMap.put("isMore", "false");
		}
		resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
		return resultMap;
	}



	//可申购份额
	@Override
	public Map<String,String> applyMaToV1Limit(String userId) {
		
		//主账户可用余额
		BigDecimal investmentAmount=mAccountQueryMapper.queryMAccountInvestmentAmount(userId);
		
		//实际可申购份额
		BigDecimal infaceCanApply=investmentAmount;
		
		//最小投资额
		BigDecimal minApplyB=accountInfoMapper.queryVAccountMinApplyAmount();
		
		
		// 1. 查询个人V理财剩余可购买份额
		BigDecimal canApplyRemainAmountBigDecimal=accountInfoMapper.queryVAccountCanApplyRemainAmount(userId);
		
		if(canApplyRemainAmountBigDecimal==null){
			sendEmailExceptionService.sendUserExceptionEmail(userId, "SQL语句有误,请检查VAccountInfoMapper.queryVAccountCanApplyRemainAmount", this.getClass(), "applyMaToV1Limit");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		
		// 2. 个人实际可购买份额  应当小于V理财剩余可购买额度
		if(investmentAmount.compareTo(canApplyRemainAmountBigDecimal)>0){
			infaceCanApply=canApplyRemainAmountBigDecimal;
		}
		
		// 3. 个人实际可购买份额 应当大于起投金额
		if(infaceCanApply.compareTo(minApplyB)<0){
			infaceCanApply=new BigDecimal(0);
		}
		
		Map<String,String> reMap=new HashMap<String,String>();
		reMap.put("tipInput", "当前最多可购买"+Format.getBigDecimalFormat("##0.00").format(infaceCanApply)+"元");
		reMap.put("tipContent", Format.getBigDecimalFormat("##0.00").format(minApplyB)+"元起投,V+理财剩余可购买"+Format.getBigDecimalFormat("##0.00").format(canApplyRemainAmountBigDecimal)+"元");
		reMap.put("maxBalance", Format.getBigDecimalFormat("##0.00").format(infaceCanApply));
		reMap.put("minBalance", Format.getBigDecimalFormat("##0.00").format(minApplyB));
		return AccountMapUtils.changeMapDataToMapString(reMap);
	}


	@Override
	public Map<String, String> queryVAccountInitPage(String userId) {
		Map<String,String> vaccount = queryVplusAccount(userId);
		vaccount.putAll(queryVAccountFundWanInfo());
		return vaccount;
	}


	@Override
	public Map<String, String> applyMaToV1InitPage(String userId) {
	    Map<String,String> initMap = applyMaToV1Limit(userId);
	    initMap.put("amountAvailable", mAccountQueryMapper.queryMAccountInvestmentAmount(userId).toPlainString());
		return initMap;
	}
	
}
