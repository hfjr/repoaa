package com.rqb.ips.depository.platform.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.hf.comm.utils.Define;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSRechargeResponseBean;
import com.rqb.ips.depository.platform.beans.IPSRequierParamsBean;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsRechargeBean;
import com.rqb.ips.depository.platform.beans.IpsRechargeRequestBean;
import com.rqb.ips.depository.platform.faced.IQueryDirectBank;
import com.rqb.ips.depository.platform.faced.IpsRechargeService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IPCPayService;

/**
 * IPS调用接口
 * @version 1.0 ,2017/12/8
 * @auther fan
 * @since 1.0
 */
@Controller
public class IPSController {
	
	@Autowired
	private IpsRechargeService ipsRechargeService;
	
	@Autowired
	private IQueryDirectBank iQueryDirectBank;
	
	@Autowired
	private IPCPayService payService;	

    @Value("${local_server_ip}")
    private String serverPath;
    
    
    /**
     * 充值界面选择银行
     */
	@RequestMapping("/app/ips/querBankCodeList")
	@AppController
	public Object querBankCodeList(String userId) {
		//判断是否已开户
		Map account=iQueryDirectBank.queryIPSAccount(userId, "ips","1");
		if(account.size() == 0 || account == null){
			throw new AppException("请先去激活存管账户");
		}
		IPSResponse response=iQueryDirectBank.queryBankList();
		if(!IPSResponse.ErrCode.SUCCESS.equals(response.getCode())){
			throw new AppException("IPS连接异常，请稍后再试");
		}		
		return response.getData();
	}
	
	/**
     * IPS充值
	 * @throws Exception 
     */
    @RequestMapping(value="/app/ips/IPSRecharge",method = RequestMethod.GET)
//    @AppController
    public String IPSRecharge(IpsRechargeRequestBean ipsRechargeRequestBean,Model model) throws Exception {
    	IpsRechargeBean bean=new IpsRechargeBean();
    	IPSRequierParamsBean res=new IPSRequierParamsBean();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		Map account=iQueryDirectBank.queryIPSAccount(ipsRechargeRequestBean.getUserId(), "ips","1");
		if(account.size() == 0 || account==null){
			throw new AppException("请先去激活存管账户");
		}
		List<Map<String,String>> mers=ipsRechargeService.getMerFee(Define.OperationType.WITHDRAW);
		for(Map<String,String> map:mers){
			if(map.get("key").toString().equals("ipsFeeType")){
				bean.setIpsFeeType(map.get("value").toString());
			}else if(map.get("key").toString().equals("merFee")){
				bean.setMerFee(map.get("value").toString());
			}else if(map.get("key").toString().equals("merFeeType")){
				bean.setMerFeeType(map.get("value").toString());
			}
		}
    	bean.setMerBillNo(GenerateMerBillNoUtil.getBillNoGenerate());
    	bean.setMerDate(sim.format(new Date()));
    	bean.setDepositType(ipsRechargeRequestBean.getDepositType());
    	bean.setChannelType(ipsRechargeRequestBean.getChannelType());
    	bean.setBankCode(ipsRechargeRequestBean.getBankCode());
    	bean.setUserType(ipsRechargeRequestBean.getUserType());
    	bean.setIpsAcctNo(account.get("id")+"");
    	bean.setTrdAmt(ipsRechargeRequestBean.getTrdAmt());
    	bean.setTaker(ipsRechargeRequestBean.getTaker());
    	bean.setWebUrl(serverPath+"/app/ips/rechargeSynchro");
    	bean.setS2SUrl(serverPath+"/app/ips/rechargeAsynchronous");
//    	bean.setWebUrl("http://192.168.0.105:20340/wallet-mobile/app/ips/rechargeSynchro?source=lujiahua");
//    	bean.setS2SUrl("http://180.168.26.114:20010/p2p-deposit/test/p2pweb.html");
    	
    	  //组建参数bean
    	res=ipsRechargeService.recharge(bean);
		
    	//记录历史流水
		ipsRechargeService.saveHistory("",bean.getMerBillNo(), "", ipsRechargeRequestBean.getTrdAmt(), ipsRechargeRequestBean.getBankCode(), "", "", "",JSONUtils.obj2json(bean)+"", "",ipsRechargeRequestBean.getUserId(),null,ipsRechargeRequestBean.getSource(), Define.OperationType.DEPOSIT);
    	
		// 请求的订单保存到数据库中
    	ipsRechargeService.saveRechargeOrder("", ipsRechargeRequestBean.getUserId(), ipsRechargeRequestBean.getTrdAmt(),bean.getMerBillNo(),ipsRechargeRequestBean.getBankCode(),account.get("id")+"");
   // 	return response.getData();
   // 	htpres.getOutputStream().write(response.getData().toString().getBytes("utf-8"));
    //	return res;
    	model.addAttribute("operationType",res.getOperationType());
    	model.addAttribute("merchantID",res.getMerchantID());
    	model.addAttribute("sign",res.getSign());
    	model.addAttribute("request",res.getRequest());
    	model.addAttribute("url",res.getUrl());
    	return "/app/model/recharge";
    }
    
    
    /**
     * IPS充值同步回掉
     * @throws Exception 
     */
    @RequestMapping("/app/ips/rechargeSynchro")
    public String rechargeSynchro(IPSRechargeResponseBean ipsRechargeResponseBean,HttpServletRequest req,Model model) throws Exception{
    	BaseLogger.info("IPS充值同步回掉参数:"+JSONUtils.obj2json(ipsRechargeResponseBean));
    	BaseLogger.info("开始校验签名");
    	String source =req.getParameter("source");
    	if(!IpsVerifySign.checkSign(ipsRechargeResponseBean.getSign(), ipsRechargeResponseBean.getResultCode(), ipsRechargeResponseBean.getResultMsg(), ipsRechargeResponseBean.getResponse())){
    		throw new AppException("错误数据");
    	}
    	BaseLogger.info("结束校验签名");
    	if(IPSResponse.ErrCode.SUCCESS.equals(ipsRechargeResponseBean.getResultCode())){
    		ipsRechargeService.updateRechaStatus(ipsRechargeResponseBean);
    	}else{
    		throw new AppException("IPS充值失败");
    	}
    	BaseLogger.info("IPS充值同步回掉结束");
    	model.addAttribute("source",source);
    	return "/app/model/rechargePC";
    }
    
    /**
     * IPS充值异步回掉
     * @throws Exception 
     */
    @RequestMapping("/app/ips/rechargeAsynchronous")
    @AppController
    public Object rechargeAsynchronous(IPSRechargeResponseBean ipsRechargeResponseBean, HttpServletResponse resp) throws Exception{
    	BaseLogger.info("IPS充值异步回掉参数:"+JSONUtils.obj2json(ipsRechargeResponseBean));
    	BaseLogger.info("开始校验签名");
    	if(!IpsVerifySign.checkSign(ipsRechargeResponseBean.getSign(), ipsRechargeResponseBean.getResultCode(), ipsRechargeResponseBean.getResultMsg(), ipsRechargeResponseBean.getResponse())){
    		throw new AppException("错误数据");
    	}
    	BaseLogger.info("结束校验签名");
    	if(IPSResponse.ErrCode.SUCCESS.equals(ipsRechargeResponseBean.getResultCode())){
    		ipsRechargeService.updateRechaStatus(ipsRechargeResponseBean);
    	}else{
    		throw new AppException("IPS充值失败");
    	}
    	BaseLogger.info("IPS充值异步回掉结束");
    	return "ipsCheckOk";
    }
    
    /**
     * 判断是否开户
     * @throws IOException 
     */
    @RequestMapping("/app/ips/isOpenAccount")
    @AppController
    public Object isOpenAccount(String userId) throws IOException{
    	Map map=new HashMap<String, String>();
    	map.put("ips", iQueryDirectBank.isIPS(userId)?"00":"10");
    	return map;
    }
    
}
