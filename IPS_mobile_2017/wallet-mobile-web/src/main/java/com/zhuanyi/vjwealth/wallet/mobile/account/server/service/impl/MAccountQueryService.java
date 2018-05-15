package com.zhuanyi.vjwealth.wallet.mobile.account.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.TAccountQueryMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.server.util.Format;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsPlatformLogin;
import com.rqb.ips.depository.platform.faced.IpsPlatformLoginService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.EAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.MAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IMAccountQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IOrderHelperService;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountDateUtils;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;

@Service
public class MAccountQueryService implements IMAccountQueryService {
	
	@Autowired
	private MAccountQueryMapper accountInfoMapper;
	@Autowired
	private EAccountQueryMapper eaccountInfoMapper;

	@Autowired
	private TAccountQueryMapper taccountInfoMapper;

	@Autowired
	private ISendExceptionEmailService accountEmailExceptionService;
	@Autowired
	private IOrderHelperService OrderUtilService;
	
	@Autowired
	private IUserQueryMapper userQueryMapper;
	
	@Autowired
	private IpsPlatformLoginService excuteIpsPlatformLoginService;
	
	@Value("${merchantId}")
	private String merchantId;
	
	
	//总账户信息
	public Map<String, String> queryMAccountInfo(String userId) {
		String yestoday=AccountDateUtils.getYestodayString();
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("userId", userId);
		paramMap.put("receiveDate", yestoday);
		
		//主账户信息
		Map<String,String> mAccountInfo=accountInfoMapper.queryMAccountInfo(userId);
		if(mAccountInfo==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "主账户信息不存在",this.getClass(),"queryMAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		//总资产
		Map<String,String> totalAmount=accountInfoMapper.queryAccountTotalAmount(userId);
		//总收益
		Map<String,String> totalReceive=accountInfoMapper.queryMAccountTotalReceive(userId);
		//昨日收益
		Map<String,String> yestodayReceive=accountInfoMapper.queryMAccountYestodayReceive(paramMap);
		//投资总额
		Map<String,String> investment=accountInfoMapper.queryMAccountInvestment(userId);
		if(investment==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "e账户或v1账户不存在",this.getClass(),"queryMAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		mAccountInfo.putAll(totalAmount);
		mAccountInfo.putAll(totalReceive);
		mAccountInfo.putAll(yestodayReceive);
		mAccountInfo.putAll(investment);
		
		return AccountMapUtils.changeMapDataToMapString(mAccountInfo);
	}

	
	
	public Map<String,Object> queryTotalAcountBill(String userId,String page) {
		//分页
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		//获取账户的账单明细列表
		
		resultList=accountInfoMapper.queryAccountBillDetail(paramMap);
		
		//封装查询结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(resultList==null){
			resultMap.put("isMore", "false");
			resultMap.put("records", resultList);
			return resultMap;
		}
		
		//补充字段
		resultList=supplementResult(resultList);
		
		if(resultList.size()<10){
			resultMap.put("isMore", "false");
		}else{
			resultMap.put("isMore", "true");
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



	public Map<String,Object> queryTotalAcountReceive(String userId,String page) {
		//分页
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		
		resultList=accountInfoMapper.queryMAccountReciveDetail(paramMap);
		
		//封装查询结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(resultList==null){
			resultMap.put("isMore", "false");
			resultMap.put("records", resultList);
			return resultMap;
		}
		if(resultList.size()<10){
			resultMap.put("isMore", "false");
		}else{
			resultMap.put("isMore", "true");
		}
		
		resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
		return resultMap;
	}

	
	public Map<String, String> queryTotalAccountBalance(String userId) {
		//可用余额
		Map<String,String> canUseAmount=accountInfoMapper.queryMAccountCanUseAmount(userId);
		if(canUseAmount==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "主账户信息不存在 ",this.getClass(),"queryTotalAccountBalance");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		//可体现次数
		Map<String,String> canWhitdraw=accountInfoMapper.queryMAccountCanWithdrawTimes(userId);
		if(canWhitdraw==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		//提现提示
		Map<String,String> whitdrawTip=accountInfoMapper.queryMAccountWithdrawTips();
		if(whitdrawTip==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		canUseAmount.putAll(canWhitdraw);
		canUseAmount.putAll(whitdrawTip);
		
		return AccountMapUtils.changeMapDataToMapString(canUseAmount);
	}

	
	
	@Override
	public Map<String, String> queryAccountFrozenBalance(String userId) {
		
		Map<String,String> resultMap=accountInfoMapper.queryAccountFrozenBalance(userId);
		if(resultMap==null){
			accountEmailExceptionService.sendAccountExceptionEmail("", "无法获取账户的冻结金额",this.getClass(),"queryVAccountFundWanInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		return AccountMapUtils.changeMapDataToMapString(resultMap);
	}
	
	
	
	@Override
	public Map<String, Object> queryAccountFrozenBalanceDetail(String userId, String page) {
		Integer currentPage=(Integer.parseInt(page)-1)*10;
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);
		
		List<Map<String,String>> resultList=null;
		resultList=accountInfoMapper.queryAccountFrozenBalanceDetail(paramMap);
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
	
	
	public Map<String,Object> queryWithdrawMaAccountAndEAccountInfo(String userId) {
		
		//	1. check param
		
		//	2. ma
		
		//	3. ea
		
		
		
		Map<String,Object> returnMap=new HashMap<String,Object>();
		
		
		//安全卡信息
		Map<String,String> withdrawCard=userQueryMapper.queryUserSecurityCardInfo(userId);
		if(withdrawCard==null){
			throw new AppException("没有可用的安全卡,请先去充值并绑卡");
		}
		returnMap.put("isBandCard", "yes");
		returnMap.put("card", AccountMapUtils.changeMapDataToMapString(withdrawCard));
	
		
		//e账户可用余额
		String amountAvailable_balance=eaccountInfoMapper.queryEAccountCanUseAmount(userId);
		
		if(StringUtils.isBlank(amountAvailable_balance)){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "e账户信息不存在 ",this.getClass(),"queryWithdrawMaAccountAndEAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		//e账户余额
		returnMap.put("ea_amount_available_balance",amountAvailable_balance);
		
	
		//e账户可体现次数
		Map<String,String> canWhitdraw=eaccountInfoMapper.queryEAccountCanWithdrawTimes(userId);
		if(canWhitdraw==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		//提现提示
		Map<String,String> whitdrawTip=eaccountInfoMapper.queryEAccountWithdrawTips();
		if(whitdrawTip==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		//tip语拼接,拼接剩余次数
		String tip=whitdrawTip.get("tip");
		tip=tip+",今日还可提现"+Format.getBigDecimalFormat("##").format(canWhitdraw.get("canUseCount"))+"次";
		
		
	
		//TODO 单笔提现5W放入到指定表中
		//可用余额和5万做比较
		BigDecimal amountAvailable= new BigDecimal(amountAvailable_balance);
		if(amountAvailable.compareTo(new BigDecimal(50000))>0){
			//转出到银行卡
			returnMap.put("ea_to_bank_tipInput", "本次最多提现50000元");
		}else{
			returnMap.put("ea_to_bank_tipInput", "本次最多提现"+amountAvailable.toPlainString()+"元");
		}
		//TODO ..此处应为 tip 
		returnMap.put("ea_to_bank_tipContent",tip);
		
		

		
		
		//查询可用余额
		Map<String,String> canUseAmount=accountInfoMapper.queryMAccountCanUseAmount(userId);
		if(canUseAmount==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "主账户信息不存在 ",this.getClass(),"queryWithdrawMaAccountAndEAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		//可用余额
		String amountAvailableString=String.valueOf(canUseAmount.get("amountAvailable"));
		//TODO ..应为 amountAvailableString ,还需添加ea账户的余额
		returnMap.put("ma_amountAvailable", amountAvailableString);
		
		//可体现次数
		Map<String,String> maWhitdraw=accountInfoMapper.queryMAccountCanWithdrawTimes(userId);
		if(maWhitdraw==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		//提现提示
		Map<String,String> mawhitdrawTip=accountInfoMapper.queryMAccountWithdrawTips();
		if(mawhitdrawTip==null){
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		
		//主账户充值语
		String ma_tip=mawhitdrawTip.get("tip")+",今日还可提现"+Format.getBigDecimalFormat("##").format(maWhitdraw.get("canUseCount"))+"次";;
		
		
//		//TODO 单笔提现5W放入到指定表中
//		//可用余额和5万做比较
		BigDecimal maamountAvailable= new BigDecimal(amountAvailableString);
		if(maamountAvailable.compareTo(new BigDecimal(50000))>0){
			//转出到银行卡
			returnMap.put("ma_to_bank_tipInput", "本次最多提现50000元");
		}else{
			returnMap.put("ma_to_bank_tipInput", "本次最多提现"+maamountAvailable.toPlainString()+"元");
		}
		
		returnMap.put("ma_to_bank_tipContent", ma_tip);
		return returnMap;
	}

	@Override
	public Map<String,Object> queryWithdrawMaAccountAndTAccountInfo(String userId) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//查询用户开户信息
		//Map<String,String> withdrawCard=userQueryMapper.queryUserSecurityCardInfo(userId);
		//Map<String,String> userAccountInfor=userQueryMapper.queryUserIsOpenAccount(userId);
		//用户没有绑卡调用IPS--平台下用户登录接口
		/*if(userAccountInfor==null||userAccountInfor.isEmpty()){
			// throw new AppException("没有可用的安全卡,请先去充值并绑卡");
			IpsPlatformLogin ipsPlatformLogin = new IpsPlatformLogin();
			ipsPlatformLogin.setMerchantId(merchantId);

			String userName = userQueryMapper.queryUserPhoneNo(userId);
			ipsPlatformLogin.setUserName(userName);

			IPSResponse platformLogin = excuteIpsPlatformLoginService.platformLogin(ipsPlatformLogin);
			
			returnMap.put("platformLogin", platformLogin);

			return returnMap;
		}*/
		//returnMap.put("isBandCard", "yes");
		//returnMap.put("card", AccountMapUtils.changeMapDataToMapString(withdrawCard));


		//ta账户可用余额
		/*String amountAvailable_balance=taccountInfoMapper.queryTAccountCanUseAmount(userId);
		if(StringUtils.isBlank(amountAvailable_balance)){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "ta账户信息不存在 ",this.getClass(),"queryWithdrawMaAccountAndTAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		
		returnMap.put("ta_amount_available", amountAvailable_balance);*/
		

		//ta账户可体现次数
		/*Map<String,String> canWhitdraw=taccountInfoMapper.queryTAccountCanWithdrawTimes(userId);
		if(canWhitdraw==null){
			throw new AppException("系统繁忙,请稍后再试");
		}*/

		//提现提示
		/*Map<String,String> whitdrawTip=taccountInfoMapper.queryTAccountWithdrawTips();
		if(whitdrawTip==null){
			throw new AppException("系统繁忙,请稍后再试");
		}*/
		//tip语拼接,拼接剩余次数
		/*String tip=whitdrawTip.get("tip");*/
//		tip=tip+",单笔提现10元起，手续费2元/次今日还可提现"+Format.getBigDecimalFormat("##").format(canWhitdraw.get("canUseCount"))+"次";
		/*tip="单笔提现不超过5万,10元起提,手续费2元/次";*/


		//TODO 单笔提现5W放入到指定表中
		//可用余额和5万做比较
		/*BigDecimal amountAvailable= new BigDecimal(amountAvailable_balance);
		if(amountAvailable.compareTo(new BigDecimal(50000))>0){
			//转出到银行卡
			returnMap.put("ea_to_bank_tipInput", "本次最多提现50000元");
		}else{
			returnMap.put("ea_to_bank_tipInput", "本次最多提现"+amountAvailable.toPlainString()+"元");
		}
		//TODO ..此处应为 tip
		returnMap.put("ea_to_bank_tipContent",tip);*/


		//查询可用余额
		Map<String,String> canUseAmount=accountInfoMapper.queryMAccountCanUseAmount(userId);
		if(canUseAmount==null){
			accountEmailExceptionService.sendAccountExceptionEmail(userId, "主账户信息不存在 ",this.getClass(),"queryWithdrawMaAccountAndEAccountInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}

		//可用余额
		String amountAvailableString=String.valueOf(canUseAmount.get("amountAvailable"));
		//TODO ..应为 amountAvailableString ,还需添加ea账户的余额
		returnMap.put("ma_amountAvailable", amountAvailableString);

		//可体现次数  ma账户提现20次每日
		Map<String,String> maWhitdraw=accountInfoMapper.queryMAccountCanWithdrawTimes(userId);
		if(maWhitdraw==null){
			throw new AppException("系统繁忙,请稍后再试");
		}

		//提现提示
	/*	Map<String,String> mawhitdrawTip=accountInfoMapper.queryMAccountWithdrawTips();
		if(mawhitdrawTip==null){
			throw new AppException("系统繁忙,请稍后再试");
		}*/


		//主账户充值语
//		String ma_tip=mawhitdrawTip.get("tip")+",今日还可提现"+Format.getBigDecimalFormat("##").format(maWhitdraw.get("canUseCount"))+"次";;
		String ma_tip="单笔提现不超过5万,10元起提,手续费2元/次";


//		//TODO 单笔提现5W放入到指定表中
//		//可用余额和5万做比较
		BigDecimal maamountAvailable= new BigDecimal(amountAvailableString);
		if(maamountAvailable.compareTo(new BigDecimal(50000))>0){
			//转出到银行卡
			returnMap.put("ma_to_bank_tipInput", "本次最多提现50000元");
		}else{
			returnMap.put("ma_to_bank_tipInput", "本次最多提现"+maamountAvailable.toPlainString()+"元");
		}

		returnMap.put("ma_to_bank_tipContent", ma_tip);
		return returnMap;
	}

}
