package com.zhuanyi.vjwealth.wallet.mobile.account.server.service.impl;

import com.fab.core.exception.service.AppException;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.EAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.MAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.TAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IEAccountQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IOrderHelperService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.ITAccountQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountDateUtils;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountDefaultMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyingxuan
 * @date 20160714
 */
@Service
public class TAccountQueryService implements ITAccountQueryService {
	@Autowired
	private TAccountQueryMapper accountInfoMapper;
	
	
	@Autowired
	private MAccountQueryMapper maccountInfoMapper;
	
	
	@Autowired
	private ISendExceptionEmailService accountEmailExceptionService;
	
	@Autowired
	private IOrderHelperService OrderUtilService;
	
	public Map<String, String> queryTAccountInfo(String userId) {
		Map<String,String> returnMap=new HashMap<String,String>();
		
		//e账户冻结金额和总金额
		Map<String,String> tAccountInfo=accountInfoMapper.queryTAccountInfo(userId);
		if(tAccountInfo==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "ta账户信息不存在 ",this.getClass(),"queryTAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		returnMap.putAll(tAccountInfo);
		
		//e账户总收益
		Map<String,String> totalReceive=accountInfoMapper.queryTAccountTotalReceive(userId);
		returnMap.putAll(totalReceive);
		
		//e账户昨日收益
		Map<String,String> yestodayReceive=accountInfoMapper.queryTAccountYestodayReceive(userId, AccountDateUtils.getYestodayString());
		if(yestodayReceive==null){
			yestodayReceive = AccountDefaultMapUtils.getEAccountYestodayReceiveMap();
		}
		returnMap.putAll(yestodayReceive);
		return AccountMapUtils.changeMapDataToMapString(returnMap);
	}
	
	
	public Map<String, Object> queryTAccountDetail(String userId,String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		
		//获取转入信息列表
		resultList= accountInfoMapper.queryTAccountBillDetail(paramMap);
		
		//封装查询结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("isMore", "true");
		if(resultList==null){
			resultMap.put("isMore", "false");
			resultMap.put("records", new ArrayList<Map<String,String>>());
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
	
	
	public Map<String, Object> queryTAccountReciveDetail(String userId, String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		//获取收益信息列表
		List<Map<String,String>> resultList=accountInfoMapper.queryTAccountReciveDetail(paramMap);
		
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
	
	
	public Map<String, String> queryTAccountFundWanInfo() {
		Map<String,String> returnMap=accountInfoMapper.queryTAccountFundWanInfo();
		if(returnMap==null){
			accountEmailExceptionService.sendAccountExceptionEmail("", "无法获取TA账户万份收益及七日年化利率 ",this.getClass(),"queryTAccountFundWanInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		return AccountMapUtils.changeMapDataToMapString(returnMap);
	}
	
	public Map<String, String> queryTAccountOutComeBalance(String userId) {
		
		Map<String,String> returnMap=new HashMap<String,String>();
		//可用余额
		String amountAvailable_balance=accountInfoMapper.queryTAccountCanUseAmount(userId);
		if(StringUtils.isBlank(amountAvailable_balance)){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "ta账户信息不存在 ",this.getClass(),"queryTAccountBalance");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		//可体现次数
		Map<String,String> canWhitdraw=accountInfoMapper.queryTAccountCanWithdrawTimes(userId);
		if(canWhitdraw==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		returnMap.putAll(canWhitdraw);
		
		//提现提示
		Map<String,String> whitdrawTip=accountInfoMapper.queryTAccountWithdrawTips();
		if(whitdrawTip==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		//tip语拼接,拼接剩余次数
		String tip=whitdrawTip.get("tip");
		tip=tip+",今日还可转出"+Format.getBigDecimalFormat("##").format(canWhitdraw.get("canUseCount"))+"次";
		
		
		
		//TODO 单笔提现5W放入到param表里
		//可用余额和5万做比较
		BigDecimal amountAvailable= new BigDecimal(amountAvailable_balance);
		if(amountAvailable.compareTo(new BigDecimal(50000))>0){
			//转出到银行卡
			returnMap.put("toBank_tipInput", "本次最多转出50000元");
			returnMap.put("amountAvailable_bank", "50000");
		}else{
			returnMap.put("amountAvailable_bank", amountAvailable.toPlainString());
			returnMap.put("toBank_tipInput", "本次最多转出"+amountAvailable.toPlainString()+"元");
		}
		returnMap.put("toBank_tipContent", tip);
		
		//补充转到余额提示 add by Eric $.2015-10-14 02:18:22
		returnMap.put("amountAvailable_balance", amountAvailable.toPlainString());
		returnMap.put("toBalance_tipInput", "本次最多转出"+amountAvailable.toPlainString()+"元");
		returnMap.put("toBalance_tipContent", "");
		return AccountMapUtils.changeMapDataToMapString(returnMap);
	}
	
	@Override
	public Map<String, String> queryTAccountInComeBalance(String userId) {
		Map<String,String> returnMap=new HashMap<String,String>();
		//可用余额
		BigDecimal amountAvailable_balance=maccountInfoMapper.queryMAccountInvestmentAmount(userId);
		if(amountAvailable_balance==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "主账户信息不存在 ",this.getClass(),"queryTAccountBalance");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		returnMap.put("amountAvailable_balance", Format.getBigDecimalFormat("##0.00").format(amountAvailable_balance));
		// e账户转入 tip
		returnMap.put("tipInput", "建议转入100元以上金额");
		returnMap.put("tipContent", "转入金额必须为100元以上的整数，如18888。");
		returnMap.put("min", "100");
		returnMap.put("step", "1");
		
		return AccountMapUtils.changeMapDataToMapString(returnMap);
	}

	@Override
	public Map<String, Object> queryTAccountFrozenDetail(String userId, String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		resultList=accountInfoMapper.queryTAccountFrozenDetail(paramMap);
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


	/**
	 * ta账户初始化页面
	 */
	@Override
	public Map<String, String> queryTAccountInitPage(String userId) {
		Map<String,String> eaccount=queryTAccountInfo(userId);
		eaccount.putAll(queryTAccountFundWanInfo());
		return eaccount;
	}
}

