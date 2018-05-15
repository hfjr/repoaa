package com.rqb.ips.depository.platform.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.IpsRequestUtils;
import com.hf.comm.utils.JSONUtils;
import com.hf.comm.utils.MD5Util;
import com.rqb.ips.depository.platform.beans.IPSRechargeResponseBean;
import com.rqb.ips.depository.platform.beans.IPSRequierParamsBean;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsRechargeBean;
import com.rqb.ips.depository.platform.faced.IpsRechargeService;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.IPSAccountMapper;
import com.zhuanyi.vjwealth.wallet.mobile.order.dto.WjOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.order.server.mapper.IOrderInfoQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IPCPayMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IPayMapper;

/**
 * 
* <p>Title: IpsRechargeBean.java</p>
* <p>Description: 充值</p>
* @author sunxiaolei
* @date 2017年12月5日
* @version 1.0
 */
@Service
public class ExecuteRechargeService  implements IpsRechargeService{

	@Autowired
	private IPSAccountMapper ipsAccountMapper;
	
	@Autowired
	private IPayMapper iPayMapper;
	
	@Autowired
	private IPCPayMapper iPCPayMapper;
	
	@Autowired
	private IOrderInfoQueryMapper iOrderInfoQueryMapper;
	
    @Value("${ips_merchantID}")
    private String ips_merchantID;
    
    @Value("${ips_md5}")
    private String ips_md5;
    
    @Value("${ips_url}")
    private String ips_url;
	
	
	public IPSRequierParamsBean recharge(IpsRechargeBean ipsRechargeBean) {
		IPSRequierParamsBean res=new IPSRequierParamsBean();
		try {
			 Map map=IpsRequestUtils.getRequestMap(Define.OperationType.DEPOSIT, JSONUtils.obj2json(ipsRechargeBean));
//			String reqparam=DESUtil.encrypt3DES(JSONUtils.obj2json(ipsRechargeBean),"r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe").replaceAll("\n","");
			res.setOperationType(Define.OperationType.DEPOSIT);
			res.setMerchantID(map.get("merchantID")+"");
			res.setRequest(map.get("request")+"");
			res.setSign(map.get("sign")+"");
			res.setUrl(HttpClientUtils.ips_url);
		} catch (Exception e) {
			BaseLogger.error("充值组参数失败："+e.getMessage());
			e.printStackTrace();
		}

		return res;
		
	}

	public static void main(String[] args)  {
		IpsRechargeBean re=new IpsRechargeBean();
		re.setMerBillNo("1198133131");
		re.setMerDate("2015-01-01");
		re.setDepositType("1");
		re.setChannelType("1");
		re.setBankCode("1103");
		re.setUserType("1");
		re.setIpsAcctNo("100000184497");
		re.setTrdAmt("21000");
		re.setIpsFeeType("2");
		re.setMerFee("10");
		re.setMerFeeType("2");
		re.setTaker("2");
		re.setWebUrl("http://180.168.26.114:20010/p2p-deposit/test/p2pweb.html");
		re.setS2SUrl("http://180.168.26.114:20010/p2p-deposit/test/send.do");
		ExecuteRechargeService a=new ExecuteRechargeService();
		a.recharge(re);
	
	}

	@Override
	public List<Map<String, String>> getMerFee(String paramGroup) {
		return ipsAccountMapper.queryMerType(paramGroup);
	}

	@Override
	public void saveHistory(String cardId,String cardNo,String tradeNo,String amount,String bankCode,String asidePhone,String result,String message,String requestJson,String responseJson,String userId,String status,String source,String operationType) {
		ipsAccountMapper.saveRechargeHistoryIPS(cardId,cardNo,tradeNo, amount, bankCode, asidePhone, result, message, requestJson, responseJson, userId, status, source, operationType);
	}

	@Override
	public void saveRechargeOrder(String tradeNo, String userId, String amt,String orderNo,String bankCode,String ipsAcctNo) {
		iPayMapper.saveTradeRecord(tradeNo,amt,orderNo,bankCode);
		//产生未支付成功的订单
		ipsAccountMapper.saveRechargeOrder(userId,orderNo, amt, ipsAcctNo,bankCode);
	}

	@Override
	@Transactional
	public void updateRechaStatus(IPSRechargeResponseBean ipsRechargeResponseBean) {
		String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
		String iv = "1eX24DCe";
		try {
			Map<String,Object> map=JSONUtils.json2map(DESUtil.decrypt3DES(ipsRechargeResponseBean.getResponse(), decryptKey, iv));
			String orderNo=map.get("merBillNo")+"";
			WjOrderInfoDTO order=iOrderInfoQueryMapper.getOrderInfoByOrderNo(orderNo);
			if("recharge_ma_confirm_dispose".equals(order.getOrderStatus())){
				String status=map.get("trdStatus")+"";
				ipsAccountMapper.updateHistoryIPS(DESUtil.decrypt3DES(ipsRechargeResponseBean.getResponse(), decryptKey, iv),status,map.get("ipsBillNo")+"",orderNo,ipsRechargeResponseBean.getResultMsg());
				if("1".equals(status)){
					ipsAccountMapper.updatePaymentTradeStatusSuccess(map.get("ipsBillNo")+"",orderNo);
					ipsAccountMapper.updatePCRechargeOrderSuccess(map.get("ipsBillNo")+"",orderNo, order.getUserId());
					iPCPayMapper.addUserMaAvailableAmount(map.get("ipsTrdAmt")+"", order.getUserId());
				}else if("0".equals(status)){
					ipsAccountMapper.updatePaymentTradeStatusFail(map.get("ipsBillNo")+"", orderNo);
					ipsAccountMapper.updateRrechargeOrderFail(map.get("ipsBillNo")+"",orderNo,order.getUserId());
					}
				}
			} catch (Exception e) {
			throw new AppException("数据异常");
		}
	}	
}