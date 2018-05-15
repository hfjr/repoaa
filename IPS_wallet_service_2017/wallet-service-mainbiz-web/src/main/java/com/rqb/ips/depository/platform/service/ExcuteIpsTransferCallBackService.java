package com.rqb.ips.depository.platform.service;

import java.math.BigDecimal;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.rqb.ips.depository.platform.beans.IpsTransferRespVo;
import com.rqb.ips.depository.platform.faced.IpsCallbackService;
import com.rqb.ips.depository.platform.mapper.IpsTransferCallBackMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.ICouponMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBProductProcess;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;


@Service
public class ExcuteIpsTransferCallBackService  implements IpsCallbackService {

	@Autowired
	private IpsTransferCallBackMapper ipsTransferCkMapper;
	

	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;
	
	@Autowired
	@Qualifier("MBRemoteProductProcess")
	private IMBProductProcess mBProductProcess;
	
	@Autowired
	@Qualifier("MBWjProductProcess")
	private IMBProductProcess mBWjProductProcess;

	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;
	
	@Autowired
	private ICouponMapper couponMapper;
	
	@Override
	public void IpsCallBackServices(Map<String,Object> datamap) {
		

		String productNo = (String) datamap.get("projectNo");
		String batchNo = (String) datamap.get("batchNo");

		List<IpsTransferRespVo> list = (List<IpsTransferRespVo>) datamap.get("transferAccDetail");
		//投标即息转账时list长度为1
		for (IpsTransferRespVo ipsTransferRespVo : list) {
			
			String trdStatus=ipsTransferRespVo.getTrdStatus();
			
			// 转出方 IPS 存管账户号
			String outIpsAcctNo = ipsTransferRespVo.getOutIpsAcctNo();
			
			//IPS 订单号
			String ipsBillNo = ipsTransferRespVo.getIpsBillNo();
			
			//商户订单号，查询ips冻结时,理财产品产生的订单号
			String merBillNo = ipsTransferRespVo.getMerBillNo();
			
			// 根据outIpsAcctNo获取userId
			String userId = ipsTransferCkMapper.queryUserIdByIpsAcctNo(outIpsAcctNo);
			
			String orderNo = ipsTransferCkMapper.queryOrderNo(userId, ipsBillNo, productNo);
			
			String couponId = ipsTransferCkMapper.queryCouponId(userId);
			
		
			String token = null;
			// ips转账失败
			/*if ("0".equals(trdStatus)) {
					BaseLogger.audit("========转账状态失败=============");
				// ips转账成功
			} else {
				BaseLogger.audit("==========转账状态成功===========");
				//==========校验同步转账金额=============
				 * 

*/				// 投标金额
				BigDecimal amount = new BigDecimal(0);
				//从回复的list中获取 转账金额
				String amt = ipsTransferRespVo.getIpsTrdAmt();
				
				if (!StringUtils.isBlank(amt)) {
					amount = new BigDecimal(amt);
				}
				
				//根据返回merBillNo、商户转账批次号batchNo、项目 ID号projectNo查询金额
				
				Map<String,String> money=ipsTransferCkMapper.queryAmountByOrderBatchNoProjectNo(merBillNo, batchNo, productNo);
				
				String total_price = money.get("totalPrice");
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
				}
				//此接口为有红包接口    amount 为转账的实际金额  而total 为 amount+ 红包的金额
				
				if(amount.compareTo(totalPrice)==0&&amount.compareTo(price)==0){
					BaseLogger.audit("=======转账回调金额校验正确=======");
					
					//生成用户详细信息
					MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productNo);
					//生成流水号 tradeId
					String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);// 生成小赢唯一交易流水号
					//生成还款计划  有加息券
					
					
					RfResponseDTO resdto = null;
					//返回还款计划、保险凭证等数据
					if (userAccountRfMapper.isRepayPlanMockMode()) {
						// 加入加息券处理 ,把利息加进去
						
						if(StringUtils.isNotBlank(couponId)){
							//resdto 还款计划单子有加息券
							resdto =  mBWjProductProcess.processOrder(amount, this.getCoupons(userId, couponId, productNo),userinfo,merBillNo, productNo, tradeId);
							//扣除加息券
							couponMapper.updateCouponsIsUse(couponId,merBillNo);
						}
					
						else{
							//resdto 还款计划单子【无】加息券
							resdto =  mBWjProductProcess.processOrder(amount, userinfo, merBillNo, productNo, tradeId);
							
						}
						
					} else {
						//开关false
						resdto = mBProductProcess.processOrder(amount, userinfo, merBillNo, productNo, tradeId);
				
					}
					
					// 处理小赢理财订单
					userAccountTransactionService.processRfOrderWithRp(userinfo, resdto, token);
					}else 
					{
					BaseLogger.audit("=======转账回调金额校验错误=======");
					
				}
				// 更新转账订单状态
				
				ipsTransferCkMapper.updateOrderTransferRfToIps(userId, merBillNo, batchNo, ipsBillNo,trdStatus);
				
		}
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
