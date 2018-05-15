package com.zhuanyi.vjwealth.wallet.service.mainbiz.unfreeze.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.Define;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsUnfreeze;
import com.rqb.ips.depository.platform.beans.Unfreeze;
import com.rqb.ips.depository.platform.beans.wj_orderIPS;
import com.rqb.ips.depository.platform.faced.UnfreezeServices;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.unfreeze.mapper.UnfreezeMapper;

@Service
@Transactional
public class IPSUnfreezeServices implements UnfreezeServices {
	// 返回对象

	private IPSResponse ipsResponse = new IPSResponse();

	private wj_orderIPS wj_orderIPS = new wj_orderIPS();

	private IpsUnfreeze ipsUnfreeze2 = new IpsUnfreeze();
	
	@Autowired
	private UnfreezeMapper nfreezeMapper;

	String IPSSendjson = null;
	String ipsRes = null;

	@Override
	public IPSResponse Unfreeze(IpsUnfreeze ipsUnfreeze) {
		
		/** 健壮判断 */
		if (ipsUnfreeze.getTrdAmt() == null) {
			ipsResponse.setMsg("解冻金额不能为空");
		
			return ipsResponse;
		}
		if (ipsUnfreeze.getIpsAcctNo() == null) {
			ipsResponse.setMsg("解冻的IPS的存款账户号不能为空");
			ipsResponse.setCode("000001");
			return ipsResponse;
		}
		
		String ipsAcctNo = ipsUnfreeze.getIpsAcctNo();
		Map<Object, Object> ipsMap = nfreezeMapper.selectIPSMa(ipsAcctNo);
		
		if (ipsMap == null) {
			ipsResponse.setMsg("本次操作出现错误,请稍后再试!");
			return ipsResponse;
		}
		String id = (String)ipsMap.get("id");
		
		System.out.println(id);
		
		if (ipsUnfreeze.getFreezeId() == null) {
			ipsResponse.setMsg("原IPS的冻结订单号不能为空");
	
			return ipsResponse;
		}

		/** 商户的订单号 */
		String MerBillNo = GenerateMerBillNoUtil.getBillNoGenerate();

		ipsUnfreeze2.setMerBillNo(MerBillNo);
		ipsUnfreeze2.setMerDate(ipsUnfreeze.getMerDate());

		/** 项目的id号 */
		ipsUnfreeze2.setProjectNo(ipsUnfreeze.getProjectNo());

		/** 原IPS的冻结订单号 */
		ipsUnfreeze2.setFreezeId(ipsUnfreeze.getFreezeId());
		
		/** 业务的类型 */
		ipsUnfreeze2.setBizType(ipsUnfreeze.getBizType());
		/** 健壮判断 */
		if (ipsUnfreeze.getMerFee() == null) {
			ipsUnfreeze.setMerFee("0");
			ipsUnfreeze2.setMerFee(ipsUnfreeze.getMerFee());
		}
		/** 平台手续费 */
		ipsUnfreeze2.setMerFee(ipsUnfreeze.getMerFee());
		/** 解冻的IPS存管账户号 */
		ipsUnfreeze2.setIpsAcctNo(ipsUnfreeze.getIpsAcctNo());

		/** 解冻的金额 */
		ipsUnfreeze2.setTrdAmt(ipsUnfreeze.getTrdAmt());

		/** 通知,商户平台回调时通知地址 */
		ipsUnfreeze2.setS2SUrl("http://192.168.1.196:20340/wallet-mobile-web/unfreezeController/unfreeze");
		try {
			/** 调用环迅接口,如果失败,记录失败操作 */
			IPSSendjson = JSONUtils.obj2json(ipsUnfreeze2);
			String port = "trade.unFreeze";
			try {
				if (IPSSendjson == null && IPSSendjson.equals("")) {
					return null;
				}
				ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.UNFREEZE, IPSSendjson);
					
				this.nfreezeMapper.saveTradehistory(IPSSendjson, ipsRes, port);

				if (ipsRes == null && ipsRes.equals("")) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.nfreezeMapper.saveTradehistory(IPSSendjson, ipsRes, port);

			}
			/** 如果不失败订单表insert语句一次 */
			wj_orderIPS.setMerBillNo(ipsUnfreeze2.getMerBillNo());
			String ipsAcctNo2 = ipsUnfreeze2.getIpsAcctNo();
			Map<Object, Object> Account_result = this.nfreezeMapper.IPSselectUser_id(ipsAcctNo2);
			if (Account_result == null) {
				ipsResponse.setMsg("本次根据IPS存管帐号并没有找到拥有此IPS的用户");
				return ipsResponse;
			}
			String user_id = (String) Account_result.get("user_id");
			wj_orderIPS.setUser_id(user_id);
			String bizType = ipsUnfreeze2.getBizType();
			if (bizType.equals("4")) {
				wj_orderIPS.setTitle("红包解冻");
			}
			String freezeId = ipsUnfreeze2.getFreezeId();
			/*
			 * Map<Object, Object> ResyltOfreezeIdRFreezeNo =
			 * this.nfreezeMapper.selectFreezeId(freezeId);
			 * if(ResyltOfreezeIdRFreezeNo==null){
			 * ipsResponse.setMsg("本次根据原IPS冻结订单号并没有找到原冻结的IPS订单号"); return
			 * unfreeze; } String OfreezeIdRFreezeNo = (String)
			 * ResyltOfreezeIdRFreezeNo.get("order_no");
			 * wj_orderIPS.setOfreezeIdRFreezeNo(OfreezeIdRFreezeNo);
			 */

			wj_orderIPS.setOfreezeIdRFreezeNo(freezeId);
			BigDecimal fee = new BigDecimal(ipsUnfreeze2.getMerFee());
			wj_orderIPS.setFee(fee);
			wj_orderIPS.setTrade_account_id(ipsUnfreeze2.getIpsAcctNo());
			BigDecimal total_price = new BigDecimal(ipsUnfreeze2.getTrdAmt());
			wj_orderIPS.setTotal_price(total_price);
			/*
			 * if(ipsUnfreeze2.getMerFee()!=null&&ipsUnfreeze2.getMerFee().
			 * equals("0")){ BigDecimal practical_price= new BigDecimal(
			 * ipsUnfreeze2.getTrdAmt()); BigDecimal fee2 =
			 * wj_orderIPS.getFee();
			 * 
			 * }
			 */
			BigDecimal practical_price = new BigDecimal(ipsUnfreeze2.getTrdAmt());
			wj_orderIPS.setPractical_price(practical_price);
			/**
			 * 订单表第一次insert,不判定IPS返回数据的状态,先行orderInsert,IPS返回信息解析获取到为1的status
			 * 再Update
			 */
			wj_orderIPS.setOrder_status("manager_ips_unfreeze_noconfirm");

			wj_orderIPS.setOrder_type("trade.unFreeze");
			this.nfreezeMapper.insertOrder(wj_orderIPS);

			Map<String, Object> json2map = JSONUtils.json2map(ipsRes.toString());
			String response = (String) json2map.get("response");
			
			String resultCode = (String) json2map.get("resultCode");
			ipsResponse.setCode(resultCode);
			String sign = (String) json2map.get("sign");

			String resultMsg = (String) json2map.get("resultMsg");
			ipsResponse.setMsg(resultMsg);

			wj_orderIPS.setCode(IPSResponse.ErrCode.TIME_OUT);
			wj_orderIPS.setData(response);
			wj_orderIPS.setCode(resultCode);
			wj_orderIPS.setReturnmsg(resultMsg);
			wj_orderIPS.setSign(sign);

			success(port, wj_orderIPS);
		} catch (Exception e) {
			ipsResponse.setMsg("参数有误");
			e.printStackTrace();
		}
		return ipsResponse;
	}

	@Override
	@Transactional
	public IPSResponse success(String port, wj_orderIPS wj_orderIPS) {
		// Map<String, Object> json2map;
		try {
			/** 验签 */
			String response = (String) wj_orderIPS.getData();
			
			boolean checkSign = IpsVerifySign.checkSign(wj_orderIPS.getSign(), wj_orderIPS.getCode(),
					wj_orderIPS.getReturnmsg(), response);
			if (!checkSign) {
				ipsResponse.setMsg(wj_orderIPS.getReturnmsg());
				return ipsResponse;
			}
			/** 如果本次操作返回code不是00000就return不执行order为0记录修改,orderMA账户增减 */
			if (!wj_orderIPS.getCode().equals("000000")) {
				ipsResponse.setMsg(wj_orderIPS.getReturnmsg());
				return ipsResponse;
			}
			/** 解密 */
			String iv = "1eX24DCe";
			String decryptKey = "r0uScmDuH5FLO37AJV2FN72J";
			String data = DESUtil.decrypt3DES(response, decryptKey, iv);
			/**--*/
			ipsResponse.setData(data);
			
			//ipsResponse.setData(data);
			Map<String, Object> json3map = JSONUtils.json2map(data.toString());

			Unfreeze unfreeze = new Unfreeze();

			unfreeze.setMerBillNo(json3map.get("merBillNo").toString());
			// unfreeze.setProjectNo(json3map.get("projectNo").toString());
			/**freezeid*/
			unfreeze.setFreezeId(json3map.get("freezeId").toString());
			unfreeze.setMerFee(json3map.get("merFee").toString());
			unfreeze.setIpsAcctNo(json3map.get("ipsAcctNo").toString());
			unfreeze.setIpsBillNo(json3map.get("ipsBillNo").toString());
			unfreeze.setIpsDoTime(json3map.get("ipsDoTime").toString());

			ipsResponse.setMsg(wj_orderIPS.getReturnmsg());

			String stuts = unfreeze.setTrdStatus(json3map.get("trdStatus").toString());
			/** 返回解冻状态,1为成功,成功修改数据库用户余额,将数据记录到wj付款订单记录表中,修改订单表为0的订单状态 */
			/** 0为失败,结束本次操作,不去修改order表中为0的状态不过会记录失败的操作进order表中 */
			if (stuts.equals("0")) {
				ipsResponse.setMsg("本次解冻失败,因:" + wj_orderIPS.getReturnmsg());
				return ipsResponse;
			} else {
				// **如果返回给我的商户单号和我传入过去的商户订单号不一致不去修改数据库为0的状态 *//
				ipsResponse.setMsg(wj_orderIPS.getReturnmsg());
				/** 拿到这个ips的存管账号,去查询数据库,ma的账户,然后修改主账户用户可用余额 */
				String ipsAcctNo = unfreeze.getIpsAcctNo();
				Map<Object, Object> ipsMap = nfreezeMapper.selectIPSMa(ipsAcctNo);
				if (ipsMap == null) {
					ipsResponse.setMsg("本次查询并没有找到ma的用户");
					return ipsResponse;
				}
				
				String IPSIDdepositNO = (String) ipsMap.get("id");
				
				String ipsBillNo = unfreeze.getMerBillNo();
				Map<Object, Object> selectIPSMa = this.nfreezeMapper.selectTrdAmt(ipsBillNo);
				if (selectIPSMa == null) {
					ipsResponse.setMsg("本次操作超时链接,出现失败请稍后再试,谢谢");
					return ipsResponse;
				}
				BigDecimal Order_trd = (BigDecimal) selectIPSMa.get("price");
				nfreezeMapper.unfreezeMoney(Order_trd, IPSIDdepositNO);
				wj_orderIPS.setPractical_price(Order_trd);
				wj_orderIPS.setMerBillNo(unfreeze.getMerBillNo());
				
				wj_orderIPS.setOrder_status("withdraw_ma_confirm");
				wj_orderIPS.setProductNo(unfreeze.getProjectNo());
				nfreezeMapper.updateOrder(wj_orderIPS);
				wj_orderIPS.setOfreezeIdRFreezeNo(unfreeze.getFreezeId());

				wj_orderIPS.setMsg("解冻成功");
				ipsResponse.setMsg(wj_orderIPS.getMsg());
				nfreezeMapper.insertWj_payment_trade_record(wj_orderIPS);
				String freezeId2 = unfreeze.getFreezeId();
					
				
				try {
					nfreezeMapper.investmentManageMoney(freezeId2);
					nfreezeMapper.investmentEarningsManageMoney(freezeId2);
					nfreezeMapper.updateRepayment(freezeId2);
					nfreezeMapper.returnMoney(freezeId2);
				
					// 插入收益表，还款计划到期收益、新手补贴收益、小赢平台补贴收益
					//nfreezeMapper.insertRepaymentPlanReceive();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ipsResponse.setMsg("理财功能挂调了金额的增减OK了");					
				}
				
			}
		} catch (Exception e) {

			e.printStackTrace();
			ipsResponse.setMsg("本次操作,出现超时连接.");
			return ipsResponse;
		}
		return ipsResponse;
	}
}
