package com.rqb.ips.depository.platform.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.InterfaceRecordJSONDTO;
import com.rqb.ips.depository.platform.beans.IpsTradeQuery;
import com.rqb.ips.depository.platform.beans.IpsUserInfoDTO;
import com.rqb.ips.depository.platform.faced.IPSInterfaceRecordService;
import com.rqb.ips.depository.platform.faced.IPSQueryUserInfoService;
import com.rqb.ips.depository.platform.mapper.CustomMapper;
import com.rqb.ips.depository.platform.menu.Define;

import javassist.runtime.Desc;

@Service
public class IPSQueryUserInfoServiceimpl implements IPSQueryUserInfoService {

	@Autowired
	private IPSInterfaceRecordService iPSInterfaceRecordService;


	@Autowired
	private CustomMapper customMapper;
	
	
	
	@Override
	public IpsUserInfoDTO IpsQueryUserInfo(IpsUserInfoDTO ipsUserInfoDTO) {
		if(ipsUserInfoDTO==null){
			try {
				throw new Exception("系统错误");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}

		ExcuteIpsTradeQueryService openAccount=new ExcuteIpsTradeQueryService();
		IpsTradeQuery    ipsTradeQuery=new IpsTradeQuery();
		IPSResponse tradeQuery=new IPSResponse();
		//01:账户查询
		if("01".equals(ipsUserInfoDTO.getQuerytype())){
			ipsTradeQuery.setIpsAcctNo(ipsUserInfoDTO.getUseripsid());
			ipsTradeQuery.setQueryType(ipsUserInfoDTO.getQuerytype());
			//请求记录保存
			InterfaceRecordJSONDTO toSendJson = ToSendJson(ipsTradeQuery);
			tradeQuery = openAccount.tradeQuery(ipsTradeQuery);
			if(!"000000".equals(tradeQuery.getCode())){
				System.out.println("错误");

				toSendJson.setJson(tradeQuery.toString());
				ToReturnJson(toSendJson);
			}else{
				System.out.println("输出接口返回的参数"+tradeQuery);
				//保存返回记录
				try {
					String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
					String iv = "1eX24DCe";
                    String data1=(String)tradeQuery.getData();
                    Map<String, Object> jsondata = JSONUtils.json2map(data1);
                    String  response= (String)jsondata.get("response");
					String dataString = DESUtil.decrypt3DES(response, decryptKey, iv);
					Map<String, Object> data = JSONUtils.json2map(dataString);
					String obj2json = JSONUtils.obj2json(data.get("userInfo"));
					toSendJson.setJson(obj2json);
					ToReturnJson(toSendJson);
					Map<String, Object> userInfo = JSONUtils.json2map(obj2json);
					ipsUserInfoDTO.setAcctStatus(userInfo.get("acctStatus").toString());
					ipsUserInfoDTO.setuCardStatus(userInfo.get("uCardStatus").toString());
					ipsUserInfoDTO.setBankName(userInfo.get("bankName").toString());
					ipsUserInfoDTO.setBankCard(userInfo.get("bankCard").toString());
					ipsUserInfoDTO.setSignStatus(userInfo.get("signStatus").toString());
					customMapper.updataipsuserinfobyips(ipsUserInfoDTO);
					
				} catch (Exception e) {
				 System.out.println(" 交易查询锁雾");
					e.printStackTrace();
				}
				
			}
			
		}


		//02：交易查询
		if("02".equals(ipsUserInfoDTO.getQuerytype())){
			ipsTradeQuery.setQueryType(ipsUserInfoDTO.getQuerytype());
			ipsTradeQuery.setMerBillNo(ipsUserInfoDTO.getMerBillNo());
			//请求记录保存
			InterfaceRecordJSONDTO toSendJson = ToSendJson(ipsTradeQuery);
			tradeQuery = openAccount.tradeQuery(ipsTradeQuery);
			//保存返回记录
			if(!"000000".equals(tradeQuery.getCode())){
				System.out.println("错误");

				toSendJson.setJson(tradeQuery.toString());
				ToReturnJson(toSendJson);
			}else{
			try {
				String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
				String iv = "1eX24DCe";
				String data1=(String)tradeQuery.getData();
				//System.out.println(data1);

				Map<String, Object> jsondata = JSONUtils.json2map(data1);
				String  response= (String)jsondata.get("response");
				String dataString = DESUtil.decrypt3DES(response, decryptKey, iv);
				Map<String, Object> data = JSONUtils.json2map(dataString);
				String obj2json = JSONUtils.obj2json(data.get("trade"));
				toSendJson.setJson(obj2json);
				ToReturnJson(toSendJson);
				Map<String, Object> trade = JSONUtils.json2map(obj2json);
				
				ipsUserInfoDTO.setMerDate(trade.get("merDate").toString());
				ipsUserInfoDTO.setTrdStatus(trade.get("trdStatus").toString());
				ipsUserInfoDTO.setIpsBillNo(trade.get("ipsBillNo").toString());
				ipsUserInfoDTO.setIpsDoTime(trade.get("ipsDoTime").toString());
				customMapper.updataipsmerbymerBillNo(ipsUserInfoDTO);
				
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			}
		}
		//03:余额查询
		if("03".equals(ipsUserInfoDTO.getQuerytype())){
			ipsTradeQuery.setIpsAcctNo(ipsUserInfoDTO.getUseripsid());
			ipsTradeQuery.setQueryType(ipsUserInfoDTO.getQuerytype());
			//请求记录保存
			InterfaceRecordJSONDTO toSendJson = ToSendJson(ipsTradeQuery);
			tradeQuery = openAccount.tradeQuery(ipsTradeQuery);
			//保存返回记录
			if(!"000000".equals(tradeQuery.getCode())){
				System.out.println("错误");

				toSendJson.setJson(tradeQuery.toString());
				ToReturnJson(toSendJson);
			}else{
			try {
				String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
				String iv = "1eX24DCe";
				String data1=(String)tradeQuery.getData();
				//System.out.println(data1);

				Map<String, Object> jsondata = JSONUtils.json2map(data1);
				String  response= (String)jsondata.get("response");
			    String dataString = DESUtil.decrypt3DES(response, decryptKey, iv);
				Map<String, Object> data = JSONUtils.json2map(dataString);

				String obj2json = JSONUtils.obj2json(data.get("balance"));
				Map<String, Object> balance = JSONUtils.json2map(obj2json);
				toSendJson.setJson(obj2json);
				ToReturnJson(toSendJson);
				ipsUserInfoDTO.setCurBal(balance.get("curBal").toString());
				ipsUserInfoDTO.setAvailBal(balance.get("availBal").toString());
				ipsUserInfoDTO.setFreezeBal(balance.get("freezeBal").toString());
				ipsUserInfoDTO.setRepaymentBal(balance.get("repaymentBal").toString());
				customMapper.updataipsbalancebyips(ipsUserInfoDTO);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}

		System.out.println("代码:"+tradeQuery.getCode());
		System.out.println("信息:"+tradeQuery.getMsg());
		System.out.println("Data:"+tradeQuery.getData());

		return ipsUserInfoDTO;
	}

	
	@Override
	public InterfaceRecordJSONDTO ToSendJson(IpsTradeQuery ipsTradeQuery) {
		InterfaceRecordJSONDTO interfaceRecordJSONDTO = new InterfaceRecordJSONDTO();
		try {
			interfaceRecordJSONDTO.setJson(JSONUtils.obj2json(ipsTradeQuery));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(ipsTradeQuery.getMerBillNo()+"----"+ipsTradeQuery.getIpsAcctNo());
		interfaceRecordJSONDTO.setMerBillNo(ipsTradeQuery.getMerBillNo()==null ? "":ipsTradeQuery.getMerBillNo());
		interfaceRecordJSONDTO.setIpsAcctNo(ipsTradeQuery.getIpsAcctNo()==null ? "":ipsTradeQuery.getIpsAcctNo());
		interfaceRecordJSONDTO.setRegproject(Define.OperationType.COMMONQUERY);
		iPSInterfaceRecordService.SendJsonRecord(interfaceRecordJSONDTO);
		return interfaceRecordJSONDTO;
	}

	@Override
	public void ToReturnJson(InterfaceRecordJSONDTO interfaceRecordJSONDTO)  {
		try {
			iPSInterfaceRecordService.ReturnJsonRecord(interfaceRecordJSONDTO);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}
