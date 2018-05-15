package com.rqb.ips.depository.platform.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.QueryDirectBank;
import com.rqb.ips.depository.platform.faced.IQueryDirectBank;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper.IPSAccountMapper;

import org.springframework.stereotype.Service;

/**
 * 
* <p>Title: QueryDirectBank.java</p>
* <p>Description: </p>
* 查询直连银行
* @author sunxiaolei
* @date 2017年11月30日
* @version 1.0
 */
@Service
public class ExcuteQueryDirectBankService implements IQueryDirectBank{

	@Value("decryptKey")
	private final static String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
	
	@Value("iv")
	private final static String iv = "1eX24DCe";
	
	@Autowired
	private IPSAccountMapper ipsAccountMapper;

    @Value("${ips_bank_picture_url}")
    private String picture_url;
	
	@Override
	public IPSResponse queryBankList() {
		IPSResponse res=new IPSResponse();
		List<Map<String, String>> list=ipsAccountMapper.queryBanks();
		for(Map<String, String> map:list){
			map.put("pictureUrl", picture_url+"/"+map.get("bankCode")+".png");
		}
		res.setCode(IPSResponse.ErrCode.SUCCESS);
		res.setData(list);
		return res;
	}
	public static void main(String[] args) {
	QueryDirectBank qb = new QueryDirectBank();
	ExcuteQueryDirectBankService ex=new ExcuteQueryDirectBankService();
	ex.queryBankList();
	}
	
	@Override
	public Map<String, String> queryIPSAccount(String userId,String tradeType,String tradeAccountStatus) {
		return ipsAccountMapper.queryIPSAccountInfo(userId, tradeType, tradeAccountStatus);
	}
	
	@Override
	public Boolean isIPS(String userId) {
		Map<String, String> map= ipsAccountMapper.queryIPSAccountInfo(userId, "ips","1");
		if(map != null && map.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
