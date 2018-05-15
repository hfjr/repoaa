/**
 * 
 */
package com.rqb.ips.depository.platform.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.Define;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.hf.comm.utils.GenerateOrderNoUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsTransferAccountStranferAccDetail;
import com.rqb.ips.depository.platform.beans.IpsTransferAccounts;
import com.rqb.ips.depository.platform.beans.IpsTransferRespVo;
import com.rqb.ips.depository.platform.faced.IpsTransferAccountsService;
import com.rqb.ips.depository.platform.faced.IpsTransferService;
import com.rqb.ips.depository.platform.mapper.ExcuteIpsPlatLogMapper;
import com.rqb.ips.depository.platform.mapper.IpsTransferMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;

@Service
public class ExcuteIpsTransferService implements IpsTransferService {


	@Autowired
	private IpsTransferMapper ipsTransferMapper;
	
	@Autowired
	private IpsTransferAccountsService ipsTransferAccountsService;
	
	@Autowired
	private ExcuteIpsPlatLogMapper excuteIpsPlatLogMapper;

	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;

    @Value("${local_server_ip}")
    private String serverPath;
    
	@Override
	public IPSResponse IpsTransferMoney(String userId,String orderNo,String redOrderNo) {
		IPSResponse response = new IPSResponse();
		Map<String,String> orderDetai=ipsTransferMapper.transdetail(orderNo);
		List<Map<String,String>> mers=ipsTransferMapper.queryMerType(Define.OperationType.TRANSFER);
		IpsTransferAccountStranferAccDetail detail=new IpsTransferAccountStranferAccDetail();
		IpsTransferAccounts ipstras=new IpsTransferAccounts();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("operationType", "trade.transfer");
		hashMap.put("source", "AUTO");
	
	
		for(Map<String,String> mer:mers){
			if(mer.get("key").toString().equals("outMerFee")){
				detail.setOutMerFee(mer.get("value").toString());
			}else if(mer.get("key").toString().equals("inMerFee")){
				detail.setInMerFee(mer.get("value").toString());
			}
		}
		ipstras.setBatchNo("bactno" + GenerateOrderNoUtil.generate());
		ipstras.setMerDate(sim.format(new Date()));
		ipstras.setProjectNo(orderDetai.get("productId")+"");
		ipstras.setTransferType("1");
		ipstras.setIsAutoRepayment("2");
		ipstras.setTransferMode("2");
		ipstras.setS2SUrl(serverPath+"/api/ips/transfer/s2sCallBackCallBack");
		//ipstras.setS2SUrl("http://180.168.26.114:20010/p2p-deposit/test/p2pweb.html");
		detail.setMerBillNo(GenerateMerBillNoUtil.getBillNoGenerate());
		detail.setFreezeId(orderDetai.get("ipsNO")+"");
		detail.setOutIpsAcctNo(orderDetai.get("outips")+"");
		detail.setInIpsAcctNo(orderDetai.get("inips")+"");
		Object pr=orderDetai.get("price");
		BigDecimal price=new BigDecimal(pr.toString());
		detail.setTrdAmt(price.setScale(2, BigDecimal.ROUND_DOWN)+"");
		List<IpsTransferAccountStranferAccDetail> transferAccDetail=new ArrayList<IpsTransferAccountStranferAccDetail>();
		transferAccDetail.add(detail);
		ipstras.setTransferAccDetail(transferAccDetail);
		try {
			userAccountRfMapper.addOrderTransferRfToIps(userId,detail.getMerBillNo(),price,price,ipstras.getProjectNo(),ipstras.getBatchNo(),detail.getFreezeId());
			hashMap.put("sentJson", JSONUtils.obj2json(ipstras));
			String ips=HttpClientUtils.ipsPostMethod(Define.OperationType.TRANSFER,JSONUtils.obj2json(ipstras));
			Map<String,Object> map=JSONUtils.json2map(ips);
			String resp =(String)map.get("response");
			Map<String, Object> respResultResponse = JSONUtils.json2map(DESUtil.decrypt3DES(resp, "r0uScmDuH5FLO37AJV2FN72J", "1eX24DCe"));
			List<IpsTransferRespVo> list = (List<IpsTransferRespVo>) respResultResponse.get("transferAccDetail");
			if(IPSResponse.ErrCode.SUCCESS.equals(map.get("resultCode")+"")){
//				if(redOrderNo != null){
//					ipsTransferMapper.updateRedPacket(redOrderNo);
//				}
//				if(!StringUtil.isNullString(orderDetai.get("couponsId")+"")){
//					ipsTransferMapper.updateCoupons(orderDetai.get("couponsId")+"");
//				}
				//ipsTransferAccountsService.transferS2sCallBack(respResultResponse);
				hashMap.put("returnJson", ips);
				hashMap.put("status", "success");
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ips);
	
			}else{
				response.setCode(IPSResponse.ErrCode.UNKNOW);
				response.setMsg(map.get("resultMsg")+"");
				hashMap.put("status", "error");
			}	
			excuteIpsPlatLogMapper.saveRechargeHistoryIPS(orderNo, "", list.get(0).getIpsBillNo(), list.get(0).getIpsTrdAmt(),map.get("resultCode")+"", map.get("resultMsg")+"", JSONUtils.obj2json(ipstras), ips, userId, list.get(0).getTrdStatus(), "AUTO", Define.OperationType.TRANSFER);
		}catch (Exception e) {
			BaseLogger.error("IPS转账错误："+e.getMessage());
			response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");
		}
//		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);
		return response;

	}

}