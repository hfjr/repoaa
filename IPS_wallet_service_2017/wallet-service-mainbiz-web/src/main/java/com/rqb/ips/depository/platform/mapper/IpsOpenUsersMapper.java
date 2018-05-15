package com.rqb.ips.depository.platform.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IpsOpenUsersMapper {

	/*
	 * //查询userId Map<Object, Object> queryUserId(@Param("ipsAcctNo")String
	 * ipsAcctNo); //跟新红包变成可用 void
	 * updateRqbPacketIsUse(@Param("rqbUserId")String rqbUserId); //查询锁 int
	 * queryProductInfor(@Param("productId")String productId); //跟新订单状态 void
	 * updateIpsOrderStatus(Map map); //跟新红包订单状态 void
	 * updateIpsOrderStatusIps(Map map); //数据落地 void
	 * updateIpsBillNo(@Param("bidBillNo")String
	 * bidBillNo, @Param("productId")String productId,@Param("rqbUserId") String
	 * rqbUserId,@Param("ipsBidBillNo")String ipsBidBillNo);
	 * 
	 * void updateIpsPacketBillNo(@Param("packetBillNo")String
	 * packetBillNo,@Param("productId") String
	 * productId, @Param("rqbUserId")String rqbUserId,@Param("ipsPacketBillNo")
	 * String ipsPacketBillNo);
	 */
	// json数据
	void insertJsons(HashMap<Object, Object> map);

	// 得到下单金额
	Map<Object, Object> queryFreezeMoney(@Param("rqbUserId") String rqbUserId, @Param("bidBillNo") String bidBillNo);

	// 得到红包金额
	Map<Object, Object> queryPacketValue(@Param("rqbUserId") String rqbUserId,
			@Param("packetBillNo") String packetBillNo);

	// 跟新红订单号
	void updateIpsPacketBillNo(@Param("packetBillNo") String packetBillNo, @Param("productId") String productId,
			@Param("rqbUserId") String rqbUserId, @Param("ipsPacketBillNo") String ipsPacketBillNo);

	void updateyibuIpsPacketBillNo(@Param("packetBillNo") String packetBillNo, @Param("productId") String productId,
			@Param("id") String rqbUserId, @Param("ipsPacketBillNo") String ipsPacketBillNo);

	// 数据落地
	void updateIpsBillNo(@Param("bidBillNo") String bidBillNo, @Param("productId") String productId,
			@Param("rqbUserId") String rqbUserId, @Param("ipsBidBillNo") String ipsBidBillNo);

	void updateyibuIpsBillNo(@Param("bidBillNo") String bidBillNo, @Param("productId") String productId,
			@Param("id") String rqbUserId, @Param("ipsBidBillNo") String ipsBidBillNo);

	// 跟新红包订单状态
	//void updateIpsOrderStatusIps(@Param("packetBillNo") String packetBillNo,@Param("trdStatus") String trdStatus);

	// 跟新订单状态
	void updateIpsOrderStatus(@Param("bidBillNo") String bidBillNo,@Param("trdStatus") String trdStatus);

	// 查询锁
	int queryProductInfor(@Param("productId") String productId);

	// 查询userId
	Map<Object, Object> queryUserId(@Param("ipsAcctNo") String ipsAcctNo);

	// 跟新红包变成可用
	void updateRqbPacketIsUse(@Param("rqbUserId") String rqbUserId);

	// 存历史记录
	void insertInfo(Map respResult);

	// 查询交易状态
	Map<String, String> queryThredStatus(@Param("id")String id, @Param("bidBillNo")String bidBillNo);

	// 查询红包交易状态
//	Map<String, String> queryPacketThredStatus(String id, String packetBillNo);
}
