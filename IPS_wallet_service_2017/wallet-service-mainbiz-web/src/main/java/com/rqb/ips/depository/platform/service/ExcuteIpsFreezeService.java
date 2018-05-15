package com.rqb.ips.depository.platform.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsFreeze;
import com.rqb.ips.depository.platform.beans.IpsTransferAccountStranferAccDetail;
import com.rqb.ips.depository.platform.beans.IpsTransferAccounts;
import com.rqb.ips.depository.platform.beans.IpsUnfreeze;
import com.rqb.ips.depository.platform.faced.IPsFreezeService;
import com.rqb.ips.depository.platform.faced.IpsTransferAccountsService;
import com.rqb.ips.depository.platform.faced.UnfreezeServices;
import com.rqb.ips.depository.platform.mapper.ExcuteIpsPlatLogMapper;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;

/**
 * 
 * <p>
 * Title: ipsFreeze.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 冻结
 * 
 * @author sunxiaolei
 * @date 2017年11月30日
 * @version 1.0
 */
@Service
public class ExcuteIpsFreezeService implements IPsFreezeService {
 
	@Autowired
	private ExcuteIpsPlatLogMapper excuteIpsPlatLogMapper;

	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;

	@Autowired
	private IMBUserAccountMapper userAccountMapper;

	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;

	@Autowired
	private IpsTransferAccountsService ipsTransferAccountsService;

	/*@Value(value = "${trade.transfer.s2SUrl}")
	private String transferS2sUrl;*/

	@Value(value = "trade.transfer.inIpsAcctNo")
	private String transferInIpsAcctNo;
	
	@Value(value = "${local_server_ip}")
	private String localServerIp;

	/*@Remote
	private IMBUserAccountService mbUserService;*/
	
	
	@Autowired
	private UnfreezeServices unfreezeServices;
	
	/**
	 * 调用环迅冻结接口
	 */
	@Override
	public IPSResponse freezeAccount(String orderId,IpsFreeze ipsFreeze,String source) {
		// TODO Auto-generated method stub
		IPSResponse response = new IPSResponse();

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("source", source);
		hashMap.put("operationType", "trade.freeze");
		hashMap.put("orderId", orderId);

		try {

			/*String sendJson = JSONUtils.obj2json(ipsFreeze);
			System.out.println(sendJson);
			String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.FREEZE, sendJson);
			hashMap.put("returnJson", ipsRes);

			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				response.setMsg("成功");
				System.out.println(ipsRes);
				hashMap.put("status", "success");
			} else {
				response.setCode(IPSResponse.ErrCode.UNKNOW);
				response.setMsg("失败");
				hashMap.put("status", "error");
			}

			System.out.println(ipsRes);*/
		} catch (Exception e) {
			// TODO: handle exception
			/*response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");*/
		}
		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);

		//System.out.println(response);
		return response;
	}
	
	
	
	/**
	 * 后管调用环迅冻结接口发红包
	 */
	@Override
	public IPSResponse managerFreezeAccount(String orderId,IpsFreeze ipsFreeze,String source) {
		// TODO Auto-generated method stub
		IPSResponse response = new IPSResponse();
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("source", source);
		hashMap.put("operationType", "trade.freeze");
		hashMap.put("orderId", orderId);
		
		try {
			
			String sendJson = JSONUtils.obj2json(ipsFreeze);
			String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.FREEZE, sendJson);
			//System.out.println("ipsRes:"+ipsRes);
			hashMap.put("returnJson", ipsRes);
			
			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				response.setMsg("成功");
				System.out.println(ipsRes);
				hashMap.put("status", "success");
			} else {
				response.setCode(IPSResponse.ErrCode.UNKNOW);
				response.setMsg("失败");
				hashMap.put("status", "error");
			}

			System.out.println(ipsRes);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");
		}
		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);
		
		return response;
	}

	public static void main(String[] args) throws Exception {
		IpsFreeze ipf = new IpsFreeze();
		String merBillNo = GenerateMerBillNoUtil.getBillNoGenerate();

		ipf.setMerBillNo(merBillNo);
		ipf.setMerDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		
		ipf.setProjectNo("1000142222");
		ipf.setBizType("9");
		ipf.setRegType("1");
		// ipf.setContractNo(null);
		// ipf.setAuthNo(null);
		ipf.setTrdAmt("1000");
		ipf.setMerFee("1");
		ipf.setFreezeMerType("1");
		ipf.setIpsAcctNo("100000184497");
		// ipf.setOtherIpsAcctNo(null);
		// ipf.setWebUrl(null);
		ipf.setS2SUrl("http://180.168.26.114:20010/p2p-deposit/test/send.do");

		String sendJson = JSONUtils.obj2json(ipf);
		
		String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.FREEZE, sendJson);

		
		System.out.println(ipsRes);
		
		
		
	}

	/**
	 * ips 冻结回调处理订单 、账户、还款计划
	 */
	@Transactional
	@Override
	public void freezeCallBack(Map map) {
		// TODO Auto-generated method stub
		//投资理财产品的订单号
		String orderNo = (String) map.get("merBillNo");
		String productId = (String) map.get("projectNo");
		String money = (String) map.get("ipsTrdAmt");
		// 表wj_user_trade_account的主键
		String ipsAcctNo = (String) map.get("ipsAcctNo");
		String ipsBillNo = (String) map.get("ipsBillNo");
		String trdStatus = (String) map.get("trdStatus");
		
		map.put("trdStatus", "1".equals(trdStatus)?"apply_ma_to_rf_confirm":"apply_ma_to_rf_failed");
		
		// 更新订单状态
		userAccountMapper.updateIpsOrderStatus(map);
		//下单金额
		BigDecimal amount = new BigDecimal(0);
		
		if(!StringUtils.isBlank(money)){
			amount=new BigDecimal(money);
		}
		// 失败
		if ("0".equals(trdStatus)) {

			BaseLogger.audit("=======IPS冻结状态失败=========");
		} else {
			
			BaseLogger.audit("=======IPS冻结状态成功=========");
			// 冻结下单成功
			
			//根据ipsAcctNo查询用户id 注=====当考虑满标时应该根据productId获取所有投标人的userId
			String userId= userAccountRfMapper.queryUserIdByIpsAcctNo(ipsAcctNo);
			
			//根据订单号查询下单冻结时的金额,校验
			Map<String,BigDecimal> amountMap=userAccountRfMapper.queryAmountByOrder(userId,orderNo,productId);
			
			if(amountMap==null){
				throw new AppException("ISP冻结回调之前金额不正确");
			}
			
			//总金额
			BigDecimal totalPrice=amountMap.get("totalPrice");
			
			//实际交易金额
			BigDecimal price = amountMap.get("price");
			
			if(totalPrice==null||price==null){
				
				throw new AppException("ISP冻结回调之前金额不正确");
			}
			
			//无红包状态校验金额 下单金额与IPS返回金额校验
			if(amount.compareTo(totalPrice)!=0&&amount.compareTo(price)!=0){
				BaseLogger.audit("==========ISP冻结回调返回金额不正确=========");
				throw new AppException("ISP冻结回调返回金额不正确");
			}
			
			
			
			
			int version = userAccountMapper.queryProductInfor(productId);
			//  ma账户资金冻结，产品份额减少
			userAccountTransactionService.ipsApplyMaToRf(userId, productId, amount, version);
			
			// 以下调用IPS转账接口===投标即起息
			IpsTransferAccounts transferAccounts = new IpsTransferAccounts();
			String batchNo = "bactno" + GenerateMerBillNoUtil.getBillNoGenerate().substring(0, 30);
			transferAccounts.setBatchNo(batchNo);
			transferAccounts.setMerDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			transferAccounts.setProjectNo(productId);
			transferAccounts.setTransferType("1");
			transferAccounts.setIsAutoRepayment("3");
			transferAccounts.setTransferMode("2");
			transferAccounts.setS2SUrl(localServerIp+"/api/ips/transfer/s2sCallBack");
			//转账明细
			IpsTransferAccountStranferAccDetail accountStranferAccDetail = new IpsTransferAccountStranferAccDetail();
			String merBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
			//每个投标人的商户订单号
			accountStranferAccDetail.setMerBillNo(merBillNo);
			//每个投标人的IPS原冻结订单号
			accountStranferAccDetail.setFreezeId(ipsBillNo);
			//每个投标人的IPS存管账户号
			accountStranferAccDetail.setOutIpsAcctNo(ipsAcctNo);//转出方ips存管账号
			accountStranferAccDetail.setOutMerFee("0");
			
			// =============转入方ips存管账号==================
			//根据理财产品id查询转入即发表人的存管账号
			String  agentIpsAcctNo=userAccountMapper.queryAgentInIpsAcctNo(productId);
			 
			accountStranferAccDetail.setInIpsAcctNo(agentIpsAcctNo);
			accountStranferAccDetail.setInMerFee("0");
			accountStranferAccDetail.setTrdAmt(money);
			//转账明细集合
			ArrayList<IpsTransferAccountStranferAccDetail> list = new ArrayList<IpsTransferAccountStranferAccDetail>();
			list.add(accountStranferAccDetail);
			
			transferAccounts.setTransferAccDetail(list);
			//开始调用ips转账接口 
			ipsTransferAccountsService.transferAccounts(userId,transferAccounts);
			BaseLogger.audit("==============开始调用IPS转账接口==================");

		}

		//
		userAccountMapper.saveFreezeInfor(map);

	}

	@Override
	public boolean queryFreezeStatus(String merBillNo, String projectNo) {
		// TODO Auto-generated method stub
		boolean flage= userAccountRfMapper.queryFreezeStatus(merBillNo,projectNo);
		 
		
	    return flage; 
		
	}


	/**
	 * 校验后管同步还款状态
	 */
	@Override
	public boolean queryFreezeRepaymentStatus(String merBillNo) {
		// TODO Auto-generated method stub
		boolean flage= userAccountRfMapper.queryFreezeRepaymentOrderStatus(merBillNo);
		
		return flage;
	}



	/**
	 * ============处理后管冻结============
	 */
	
	@Override
	public void freezeRepayment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String orderNo = (String) map.get("merBillNo");
		//环迅返回的金额(本息)
		String money = (String) map.get("ipsTrdAmt");
		// 表wj_user_trade_account的主键
		String ipsAcctNo = (String) map.get("ipsAcctNo");
		String ipsBillNo = (String) map.get("ipsBillNo");
		String trdStatus = (String) map.get("trdStatus");
		//后管调用转账接口时，传入的
		String productId = (String) map.get("projectNo");
		map.put("title", "后管红包冻结");
		
		map.put("trdStatus", "1".equals(trdStatus)?"apply_ma_to_rf_confirm":"apply_ma_to_rf_failed");
		
		userAccountMapper.updateFreezeRepaymentOrderStatus(map);
		
		BigDecimal amount = new BigDecimal(0);
		
		if(!StringUtils.isBlank(money)){
			amount=new BigDecimal(money);
		}
		
		
		// 失败
		if ("0".equals(trdStatus)) {
			map.put("trdStatus", "manager_ips_freeze_failed");

			BaseLogger.audit("=======后管IPS冻结状态失败=========");
		} else {
			BaseLogger.audit("=======后管IPS冻结状态成功=========");
			map.put("trdStatus", "manager_ips_freeze_confirm");
			
			String userId= userAccountRfMapper.queryUserIdByIpsAcctNo(ipsAcctNo);
			

			//查询买理财时的订单号
			HashMap<String, String> queryProductOrderNo = userAccountMapper.queryProductOrderNo(orderNo);
			
			String ProductOrderNo = queryProductOrderNo.get("ProductOrderNo");
			String planId = queryProductOrderNo.get("planId");
			

			
			
		//根据订单号查询后管调用冻结时的金额,校验,理财产品id，后管调用冻结接口时，也传入productId

          Map<String,BigDecimal> amountMap=userAccountRfMapper.queryAmountByOrder(userId,orderNo,productId);

      	
			if(amountMap==null){
				throw new AppException("ISP冻结回调之前金额不正确");
			}
          
  		//本息
          BigDecimal totalPrice = amountMap.get("totalPrice");
          
          
        //本金
          BigDecimal price= amountMap.get("price");
          

          
      	if(totalPrice==null||price==null){
			
			throw new AppException("ISP后管冻结之前金额不正确");
		}
			
			//后管调用冻结，发红包的形式没有手续费
			if(amount.compareTo(totalPrice)!=0&&amount.compareTo(price)!=0){
				BaseLogger.audit("==========后管ISP冻结回调返回金额不正确=========");
				throw new AppException("后管ISP冻结回调返回金额不正确");
			}
			
			
			//后管冻结回调成功
			//1更新用户可用余额ma，叠加用户收益和回款本金 ----- 冻结回调成功处理（本息）
			userAccountMapper.updateUserRepaymentPlanReceive(userId,totalPrice);
			
			//2 更新用户rf帐户，扣减还款计划到期本金----冻结回调成功后处理（本金）
			 userAccountMapper.updateUserRfAccount(userId,planId);
			
			//3.更新用户rf明细帐户，扣减还款计划到期本金----

			userAccountMapper.updateUserRfAccountDetail(userId,ProductOrderNo, productId, planId);


			
			BaseLogger.audit("==============开始调用解冻接口=====================");
			
			IpsUnfreeze ipsUnfreeze = new IpsUnfreeze();
			
			ipsUnfreeze.setFreezeId(ipsBillNo);
			ipsUnfreeze.setIpsAcctNo(ipsAcctNo);
			ipsUnfreeze.setTrdAmt(money);
			
			//========解冻==========
			unfreezeServices.Unfreeze(ipsUnfreeze);
			
		}
		
		
		userAccountMapper.saveFreezeInfor(map);
	}
	
	
	
}
