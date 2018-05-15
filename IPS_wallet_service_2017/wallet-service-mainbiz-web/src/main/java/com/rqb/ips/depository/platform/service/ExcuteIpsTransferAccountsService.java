package com.rqb.ips.depository.platform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsResponseDTO;
import com.rqb.ips.depository.platform.beans.IpsTransferAccountStranferAccDetail;
import com.rqb.ips.depository.platform.beans.IpsTransferAccounts;
import com.rqb.ips.depository.platform.beans.IpsTransferRespVo;
import com.rqb.ips.depository.platform.faced.IpsTransferAccountsService;
import com.rqb.ips.depository.platform.mapper.ExcuteIpsPlatLogMapper;
import com.rqb.ips.depository.platform.mapper.IpsTransferMapper;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.ICouponMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBProductProcess;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;

/**
 * @@author 朱涛 2017年12月1日下午4:52:47 TODO(这是这是转账)
 */
@Service
public class ExcuteIpsTransferAccountsService implements IpsTransferAccountsService {

	@Autowired
	private ExcuteIpsPlatLogMapper excuteIpsPlatLogMapper;

	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;

	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;

	@Autowired
	@Qualifier("MBRemoteProductProcess")
	private IMBProductProcess mBProductProcess;

	@Autowired
	@Qualifier("MBWjProductProcess")
	private IMBProductProcess mBWjProductProcess;

	@Autowired
	private IpsTransferAccountsService ipsTransferAccountsService;
	
	@Autowired
	private ICouponMapper couponMapper;
	
	@Autowired
	private IpsTransferMapper ipsTransferMapper;
	
	/**
	 * 调用ips转账接口
	 */
	@Override
	public IPSResponse transferAccounts(String userId, IpsTransferAccounts ipsTransferAccounts) {
		IPSResponse response = new IPSResponse();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("source", "APP");
		hashMap.put("operationType", "trade.transfer");
		//hashMap.put("createDate", new Date());

		try {
			//============ 转账请求数据掺入wj_order表，  考虑满标时 应将每个投资人的userId的插入表wj_order
			String orderNo=ipsTransferAccounts.getTransferAccDetail().get(0).getMerBillNo();
			String totalPrice=ipsTransferAccounts.getTransferAccDetail().get(0).getTrdAmt();
			String price=ipsTransferAccounts.getTransferAccDetail().get(0).getTrdAmt();
		    String productId=ipsTransferAccounts.getProjectNo();
			String batchNo=ipsTransferAccounts.getBatchNo();
			String freezeId=ipsTransferAccounts.getTransferAccDetail().get(0).getFreezeId();
			
			
			userAccountRfMapper.addOrderTransferRfToIps(userId,orderNo,totalPrice, price,productId,batchNo,freezeId);
			String sentJson = JSONUtils.obj2json(ipsTransferAccounts);
			
			System.out.println(sentJson);
			hashMap.put("sentJson", sentJson);
			//==============转账同步返回json字符串===================
			String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.TRANSFER, sentJson);
			//json转为map
			Map<String, Object> respResult = JSONUtils.json2map(ipsRes);
			String resultCode =(String)respResult.get("resultCode");
			String resultMsg =(String)respResult.get("resultMsg");
			String resulCode =(String)respResult.get("resultCode");
			String sign =(String)respResult.get("sign");
			String resp =(String) respResult.get("response");
			
			if(IpsVerifySign.checkSign(sign, resultCode, resultMsg, resp)){
				BaseLogger.audit("=====IPS转账同步验签成功=====");
				if("000000".equals(resulCode)){
					BaseLogger.audit("=====IPS转账同步响应成功=====");
					
					//==============解密IPS转账同步返回response,json字符串===================
					IpsResponseDTO responseDTO  = JSONUtils.json2pojo(DESUtil.decrypt3DES(resp, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe"),IpsResponseDTO.class);
					
					//处理IPS转账同步返回结果
					BaseLogger.audit("===============开始处理IPS转账同步返回数据============");
					
					ipsTransferAccountsService.transferS2sCallBack(responseDTO);
					
					
				}else{
					BaseLogger.audit("=====IPS转账同步响应失败=====");
				}
				
			}else {
				BaseLogger.audit("=====IPS转账同步验签失败=====");
				
			}
		    
			System.out.println(ipsRes);
			System.out.println(respResult);
			
			
			hashMap.put("returnJson", ipsRes);
			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				hashMap.put("status", "success");
			} else {
				response.setCode(IPSResponse.ErrCode.UNKNOW);

				hashMap.put("status", "error");
			}
			// TODO 转mainbiz时添加数据落地操作

		} catch (Exception e) {
			// 返回response的同时记录失败操作
			// TODO 转mainbiz时添加数据落地操作
			response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");
		}
		// 存入历史记录表
		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);

		System.out.println(response);
		return response;
	}

	/**
	 * 处理ips转账回调
	 */
	@Override
	public void transferS2sCallBack(IpsResponseDTO responseDTO) {
		// TODO Auto-generated method stub
		
		String productId = responseDTO.getProjectNo();
		String batchNo = responseDTO.getBatchNo();
		

		List<IpsTransferRespVo> list = responseDTO.getTransferAccDetail();
		//投标即息转账时list长度为1
		for (IpsTransferRespVo ipsTransferRespVo : list) {
			
		
			String trdStatus=ipsTransferRespVo.getTrdStatus();
			
			
			// 转出方 IPS 存管账户号
			String outIpsAcctNo = ipsTransferRespVo.getOutIpsAcctNo();
			// 投标人userId
			String userId = userAccountRfMapper.queryUserIdByIpsAcctNo(outIpsAcctNo);
			//IPS 订单号
			String ipsBillNo = ipsTransferRespVo.getIpsBillNo();
			//转入方 ips账号（公司代理人）
			String inIpsAcctNo = ipsTransferRespVo.getInIpsAcctNo();
			
			//商户订单号，查询ips冻结时,理财产品产生的订单号
			String merBillNo = ipsTransferRespVo.getMerBillNo();
			
			
			
			// ips转账失败
			if ("0".equals(trdStatus)) {
					BaseLogger.audit("========转账状态失败=============");
				// ips转账成功
			} else {
				BaseLogger.audit("==========转账状态成功===========");
				//==========校验同步转账金额=============
				// 投标金额
				BigDecimal amount = new BigDecimal(0);
				String amt = ipsTransferRespVo.getIpsTrdAmt();
				if (!StringUtils.isBlank(amt)) {
					amount = new BigDecimal(amt);
				}
				
				//根据返回merBillNo、商户转账批次号batchNo、项目 ID号projectNo查询金额
				
				Map<String,BigDecimal> amountMap=userAccountRfMapper.queryAmountByOrderBatchNoProjectNo(merBillNo, batchNo, productId);
				
				if(amountMap==null){
					throw new AppException("ISP转账回调之前金额不正确");
				}
				
				//总金额
				BigDecimal totalPrice=amountMap.get("totalPrice");
				
				//实际交易金额
				BigDecimal price = amountMap.get("price");
				
				if(totalPrice==null||price==null){
					
					throw new AppException("ISP转账回调之前金额不正确");
				}
				
				//无红包状态校验金额 下单金额与IPS返回金额校验
				if(amount.compareTo(totalPrice)!=0&&amount.compareTo(price)!=0){
					BaseLogger.audit("==========ISP冻结回调返回金额不正确=========");
					throw new AppException("SP转账回调返回金额不正确");
				}
				
				
				
				
			/*	String total_price = money.get("totalPrice");
				//总金额
				BigDecimal totalPrice = new BigDecimal(0);
				if(!StringUtils.isBlank(total_price)){
					totalPrice=new BigDecimal(total_price);
				}
				
				
				String price1 = money.get("price");
				//实际交易金额
				BigDecimal price = new BigDecimal(0);
				
				if(!StringUtils.isBlank(price1)){
					price=new BigDecimal(price1);
				}*/
				
				
				
				//=================校验ips返回金额 注意实际金额==========（可能扣除手续费）
				if(/*amount.compareTo(totalPrice)==0&&*/amount.compareTo(price)==0){
					BaseLogger.audit("=======转账回调金额校验正确=======");
					
					// 查询用户产品相关数据
					MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);
					
					// 生成理财产品唯一交易流水号
					String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);
					// 获取ips冻结时,理财产品订单号
					Map<String,String> orderNoAndCouponsId = userAccountRfMapper.queryOrderByFreezeId(merBillNo);
					
					
					if(orderNoAndCouponsId==null){
						BaseLogger.info("===========冻结下单数据错误===========");
						throw new AppException("冻结下单数据错误");
					}
					
					String orderNo = orderNoAndCouponsId.get("orderNo");
					
					//取出 加息券id
					String couponId = orderNoAndCouponsId.get("couponId");
					
					RfResponseDTO resdto = null;

					if (userAccountRfMapper.isRepayPlanMockMode()) {
						//扣除红包
						Map<String,Object>packetMap=ipsTransferMapper.queryPacket(orderNo);
						if(packetMap != null && packetMap.size()>0){
							String packetOrderNo=packetMap.get("orderNo")+"";
							ipsTransferMapper.updateRedPacket(packetOrderNo);
						}
						
						// 加入加息券处理 ,把利息加进去
						if(StringUtils.isNotBlank(couponId)){
							resdto = mBWjProductProcess.processOrder(totalPrice, this.getCoupons(userId, couponId, productId),userinfo, orderNo, productId, tradeId);
							//扣除加息券
							couponMapper.updateCouponsIsUse(couponId,orderNo);
						}else{
							resdto = mBWjProductProcess.processOrder(totalPrice, userinfo, orderNo, productId, tradeId);
							
						}
						
					} else {
						
						resdto = mBProductProcess.processOrder(totalPrice, userinfo, orderNo, productId, tradeId);
					}
					
					
					
					//处理订单
					//userAccountTransactionService.processRfIpsOrder(userinfo, resdto, null);
					userAccountTransactionService.processRfIpsOrder(userinfo, resdto, inIpsAcctNo, amt);
				}else {
					BaseLogger.audit("=======转账回调金额校验错误=======");
					
				}

				
			}
			// 更新转账订单状态
			userAccountRfMapper.updateOrderTransferRfToIps(userId, merBillNo, batchNo, ipsBillNo,trdStatus);

		}

	}

	public static void main(String[] args) {
		ExcuteIpsTransferAccountsService transferAccounts = new ExcuteIpsTransferAccountsService();
		IpsTransferAccountStranferAccDetail transferacc = new IpsTransferAccountStranferAccDetail();
		transferacc.setMerBillNo("245418643441");
		transferacc.setFreezeId("PDJ17120400000114523");
		transferacc.setOutIpsAcctNo("100000185031");// 100000185031,100000185023
													// 100000184497
		transferacc.setOutMerFee("0");
		transferacc.setInMerFee("0");
		transferacc.setTrdAmt("100");
		transferacc.setInIpsAcctNo("100000184497");
		List<IpsTransferAccountStranferAccDetail> list = new ArrayList<IpsTransferAccountStranferAccDetail>();
		list.add(transferacc);
		IpsTransferAccounts ipsTransferAccounts = new IpsTransferAccounts();
		ipsTransferAccounts.setBatchNo("12541108031");
		ipsTransferAccounts.setMerDate("2017-12-12");
		ipsTransferAccounts.setProjectNo("1000142222");
		// ipsTransferAccounts.setProjectNo("100014222");
		ipsTransferAccounts.setTransferType("1");
		ipsTransferAccounts.setIsAutoRepayment("3");
		ipsTransferAccounts.setTransferMode("2");
		ipsTransferAccounts.setS2SUrl("http://180.168.26.114:20010/p2p-deposit/test/send.do");
		ipsTransferAccounts.setTransferAccDetail(list);
		IPSResponse openAccount2 = transferAccounts.transferAccounts(null, ipsTransferAccounts);
		System.out.println("提交信息:");
		System.out.println("代码:" + openAccount2.getCode());
		System.out.println("错误信息:" + openAccount2.getMsg());
		System.out.println("数据:" + openAccount2.getData());
	}

	/**
	 * 
	 * 校验转账同步结果
	 */
	@Override
	public boolean queryTransferStatus(String batchNo, String projectNo, List<IpsTransferRespVo> transferAccDetail) {
		// TODO Auto-generated method stub
		//投标及时计息transferAccDetail 长度为1
		
		//map转对象
		IpsTransferRespVo ipsTransferRespVo = transferAccDetail.get(0);
          		
		String merBillNo = ipsTransferRespVo.getMerBillNo();
		
		boolean flag=userAccountRfMapper.queryTransferByOrderBatchNoProjectNo(merBillNo, batchNo, projectNo);
		
		return flag;
	}

	
	// 获取卡券
		public BigDecimal getCoupons(String userId,String couponsId,String productId){
			Map<String,Object> packageInfo=couponMapper.checkCouponsValid(userId, couponsId, productId);
			
			if(packageInfo==null){
				throw new AppException("加息券不存在");
			}
			String couponMark=(String)packageInfo.get("couponMark");
			if(couponMark.equals("no")){
				throw new AppException("该产品不支持卡券");
			}
			Double validateProfit=Double.valueOf(packageInfo.get("validateProfit").toString());
			if(validateProfit<0){
				throw new AppException("不符合加息券使用门槛");
			}
			Double validateTime=Double.valueOf(packageInfo.get("validateTime").toString());
			if(validateTime<0){
				throw new AppException("加息券已过期");
			}
			String isUse=(String)packageInfo.get("isUse");
			if(isUse.equals("Y")){
				throw new AppException("加息券已经使用");
			}
			
			return new BigDecimal(packageInfo.get("addProfit").toString());
		} 
	
	
	
	
	
	
	
}
