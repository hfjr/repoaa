package com.rqb.ips.depository.platform.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.IpsRequestUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IpsPacketFrozen;
import com.rqb.ips.depository.platform.faced.IpsPacketFrozenService;
import com.rqb.ips.depository.platform.faced.IpsTransferService;
import com.rqb.ips.depository.platform.mapper.IpsOpenUsersMapper;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;

@Service
public class ExcuteIpsPacketFrozenService implements IpsPacketFrozenService {

	@Autowired
	private IpsOpenUsersMapper ipsOpenMapper;

	/*
	 * @Autowired private IMBUserAccountMapper userAccountMapper;
	 */

	@Autowired
	private IMBUserAccountMapper userAccountMapper;
	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;

	@Autowired
	private IpsTransferService ipsTransferService;

	@Override
	@Transactional
	public Map<String, String> packetFrozen(IpsPacketFrozen ipsPacketFrozen) {
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("source", "APP");
		hashMap.put("operationType", "trade.combFreeze");
		Map<String, String> ipsRess = new HashMap<String, String>();
		try {
			String js = JSONUtils.obj2json(ipsPacketFrozen);
			hashMap.put("sendJson", js);
			ipsRess = IpsRequestUtils.getRequestMap(Define.OperationType.COMBFREEZE, js);
			if (ipsRess != null) {
				hashMap.put("status", "success");
			} else {
				hashMap.put("status", "error");
				throw new AppException("参数异常");
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
		ipsOpenMapper.insertJsons(hashMap);
		return ipsRess;

	}

	/**
	 * ips 回调处理订单 、账户、还款计划
	 */

	@Override
	@Transactional
	public void packetCallBack(Map map) {
		// 数据--历史
				try {
					if (map != null) {
						String js = JSONUtils.obj2json(map);
						HashMap<Object, Object> maps = new HashMap<>();
						maps.put("json", js);
						ipsOpenMapper.insertInfo(map);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		// TODO Auto-generated method stub
		String productId = (String) map.get("projectNo");

		String trdStatus = (String) map.get("trdStatus");

		Map<Object, Object> bid = (Map<Object, Object>) map.get("bid");
		String ipsAcctNo = (String) bid.get("ipsAcctNo");
		// 冻结金额
		String bd = (String) bid.get("ipsTrdAmt");
		BigDecimal frozenAmoun = new BigDecimal(bd);

		Map<Object, Object> packet = (Map<Object, Object>) map.get("redPacket");
		// 红包金额
		String rpalue = (String) packet.get("ipsTrdAmt");
		BigDecimal rpValue = new BigDecimal(rpalue);
		// 其他订单编号
		String bidBillNo = (String) bid.get("merBillNo");
		// 返回 其他订单号
		String ipsBidBillNo = (String) bid.get("ipsBillNo");
		// 红包订单编号
		String packetBillNo = (String) packet.get("merBillNo");
		// 返回 红包订单号
		String ipsPacketBillNo = (String) packet.get("ipsBillNo");

		// 失败
		if ("0".equals(trdStatus)) {
			trdStatus=MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM;
			ipsOpenMapper.updateIpsOrderStatus(bidBillNo,trdStatus);
		//	ipsOpenMapper.updateIpsOrderStatusIps(packetBillNo,trdStatus);
			//throw new AppException("response 失敗");
		}
		// 成功
		if ("1".equals(trdStatus)) {
			try {
				// 1根据ipsACCTno 得到用户id,根据用户id
				Map<Object, Object> userMap = ipsOpenMapper.queryUserId(ipsAcctNo);
				String rqbUserId = (String) userMap.get("userId");
				// 判断金额是否相等
				// 根据userid 和订单号得到红包金额
				Map<Object, Object> redMap = ipsOpenMapper.queryPacketValue(rqbUserId, packetBillNo);
				Integer it = (Integer) redMap.get("packMoney");
				String redMoney = it.toString();
				// 得到下单金额
				// 根据userid he orderno
				Map<Object, Object> freeMap = ipsOpenMapper.queryFreezeMoney(rqbUserId, bidBillNo);
				BigDecimal fmoney = (BigDecimal) freeMap.get("freMoney");
				// 冻结金额直接暴力删除小数位
				fmoney = fmoney.setScale(0, BigDecimal.ROUND_DOWN);
				if (redMoney.equals(rpalue) && fmoney.equals(frozenAmoun)) {
					trdStatus=MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_CONFIRM;
					// 跟新订单状态
					ipsOpenMapper.updateIpsOrderStatus(bidBillNo,trdStatus);
					// 跟新红包订单状态
			//		ipsOpenMapper.updateIpsOrderStatusIps(packetBillNo,trdStatus);

					// 查询锁
					int lockVersion = ipsOpenMapper.queryProductInfor(productId);

					// 2.定期理财产品剩余总份额减去当前申购份额
					int updatecount = userAccountRfMapper.updateProductFinace(productId, frozenAmoun, lockVersion);
					if (updatecount < 1) {
						throw new AppException("购买失败");// 产品更新剩余份额遭遇乐观锁
					}
					// 3. 从ma 账户扣除金额到冻结
					BigDecimal frozenAmount = frozenAmoun;
					if (rpValue.compareTo(new BigDecimal(0)) > 0) {// 冻结金额减去红包金额
						frozenAmount = (frozenAmoun).subtract(rpValue);

					}
					
					userAccountMapper.updateFreezeMaAmountIps(rqbUserId, frozenAmount);
					// 数据落地 根据userId merbillNo productid去跟新
					ipsOpenMapper.updateIpsBillNo(bidBillNo, productId, rqbUserId, ipsBidBillNo);
					ipsOpenMapper.updateIpsPacketBillNo(packetBillNo, productId, rqbUserId, ipsPacketBillNo);

					// 调用ips转账
					ipsTransferService.IpsTransferMoney(rqbUserId, bidBillNo, packetBillNo);
				} else {
					throw new AppException("返回金额不一致");
				}

			} catch (Exception e) {

				throw new AppException("未知异常");

			}

		} else {
			BaseLogger.audit("下单成功");
		}

	}

	/**
	 * 查询状态
	 */
	@Override
	@Transactional
	public void queryStatus(Map map) {
		// 数据--历史
		try {
			if (map != null) {
				String js = JSONUtils.obj2json(map);
				HashMap<Object, Object> maps = new HashMap<>();
				maps.put("json", js);
				ipsOpenMapper.insertInfo(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String productId = (String) map.get("projectNo");

		String trdStatus = (String) map.get("trdStatus");

		Map<Object, Object> bid = (Map<Object, Object>) map.get("bid");
		String ipsAcctNo = (String) bid.get("ipsAcctNo");
		// 冻结金额
		String bd = (String) bid.get("ipsTrdAmt");
		BigDecimal frozenAmoun = new BigDecimal(bd);

		Map<Object, Object> packet = (Map<Object, Object>) map.get("redPacket");
		// 红包金额
		String rpalue = (String) packet.get("ipsTrdAmt");
		BigDecimal rpValue = new BigDecimal(rpalue);
		// 其他订单编号
		String bidBillNo = (String) bid.get("merBillNo");
		// 返回 其他订单号
		String ipsBidBillNo = (String) bid.get("ipsBillNo");
		// 红包订单编号
		String packetBillNo = (String) packet.get("merBillNo");
		// 返回 红包订单号
		String ipsPacketBillNo = (String) packet.get("ipsBillNo");
		// 根据账号去查userid
		Map<Object, Object> userMap = ipsOpenMapper.queryUserId(ipsAcctNo);
		String id = (String) userMap.get("userId");
		if (id != null) {
			// 如果还是审核中，去查这次的状态成功，失败都会去更新
			Map<String, String> stt = ipsOpenMapper.queryThredStatus(id, bidBillNo);
			String sttStatus = stt.get("sttStatus");
			if(sttStatus==null){
				throw new AppException("状态没查询到");
			}
			// 如果不确定继续更新订单状态
			if (MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM.equals(sttStatus)) {
				ipsOpenMapper.updateIpsOrderStatus(bidBillNo,trdStatus);
			}
			// 成功失败不做任何操作
			else {
				// 成功或者失败
				BaseLogger.audit("结果返回成功");
			}

		} else {
			throw new AppException("商户订单号没查到");
		}
	}
}
