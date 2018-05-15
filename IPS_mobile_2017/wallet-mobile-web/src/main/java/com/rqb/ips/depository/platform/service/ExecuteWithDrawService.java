package com.rqb.ips.depository.platform.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsPlatformLogin;
import com.rqb.ips.depository.platform.faced.IpsPlatformLoginService;
import com.rqb.ips.depository.platform.faced.IpsWithDrawService;
import com.rqb.ips.depository.platform.mapper.IpsWithDrawMapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.MAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;

@Service
public class ExecuteWithDrawService implements IpsWithDrawService{

	@Autowired
	private IpsWithDrawMapper  ipsWithDrawMapper;
	
	@Autowired
	private IUserQueryMapper userQueryMapper;
	
	@Autowired
	private MAccountQueryMapper accountInfoMapper;
	
	
	@Autowired
	private ISendExceptionEmailService accountEmailExceptionService;
	
	
	@Value("${merchantId}")
	private String merchantId;
	
	
	/**
	 * 处理提现回调
	 */
	@Override
	public void updateWithDraw(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String merBillNo = (String)map.get("merBillNo");
		String ipsBillNo = (String)map.get("ipsBillNo");
		String trdStatus = (String)map.get("trdStatus");
		String ipsAcctNo = (String)map.get("ipsAcctNo");
		String ipsTrdAmt = (String)map.get("ipsTrdAmt");
		// 查询提现人userId
		String userId = ipsWithDrawMapper.queryUserIdByIpsAcctNo(ipsAcctNo);
		
		BigDecimal amount = new BigDecimal(0);
		
		if(!StringUtils.isBlank(ipsTrdAmt)){
			amount=new BigDecimal(ipsTrdAmt);
		}
		
		
		if("0".equals(trdStatus)){
			//ma 解冻锁定冻结资金
			map.put("trdStatus", "withdraw_ma_failed");
			ipsWithDrawMapper.cancelFreezeMaAmount(userId, amount);
		}else if("1".equals(trdStatus)){
			//从冻结金额中扣除资金
			map.put("trdStatus", "withdraw_ma_confirm");
			ipsWithDrawMapper.deductionFreezeMaAmount(userId, amount);
			
		}else if("2".equals(trdStatus)){
			
			map.put("trdStatus", "withdraw_ma_dispose");
		}else{
			
			map.put("trdStatus", "withdraw_ma_refund");
		}
		//更新表wj_order
		ipsWithDrawMapper.updateOrderWithDraw(map);
		//插入表wj_payment_trade_record
		ipsWithDrawMapper.saveOrderWithDrawRecord(map);
		
	}

	/**
	 * 用户提现
	 */
	@Override
	public Map<String, Object> userWithDraw(String userId) {
		// TODO Auto-generated method stub
		Map<String,Object> returnMap=new HashMap<String,Object>();
		//查询用户开户信息
		boolean userInfor = ipsWithDrawMapper.queryUserIsOpenAccount(userId);
		//用户没有绑卡调用IPS--平台下用户登录接口
		if(!userInfor){
			/*IpsPlatformLogin ipsPlatformLogin = new IpsPlatformLogin();
			ipsPlatformLogin.setMerchantId(merchantId);

			String userName = userQueryMapper.queryUserPhoneNo(userId);
			ipsPlatformLogin.setUserName(userName);

			IPSResponse platformLogin = excuteIpsPlatformLoginService.platformLogin(ipsPlatformLogin);
			
			returnMap.put("platformLogin", platformLogin);
			return returnMap;*/
			
			throw new AppException("该用户未开户:" + userId);
			
		}
		
	

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

	@Override
	public boolean queryWithDrawStatus(String merBillNo) {
		// TODO Auto-generated method stub
		return ipsWithDrawMapper.queryWithDrawStatus(merBillNo);
	}

}
