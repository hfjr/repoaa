package com.rqb.ips.depository.platform.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
@Mapper
public interface IpsTransferCallBackMapper {

	//查询是否存在订单
	Integer queryTransferByOrderBatchNoProjectNo(@Param(value = "merBillNo") String merBillNo,@Param(value = "batchNo")  String batchNo,@Param(value = "projectNo") String projectNo);

	//查询userId
	String  queryUserIdByIpsAcctNo(@Param(value = "outIpsAcctNo")  String outIpsAcctNo);
	
	//返回余额
	Map<String,String> queryAmountByOrderBatchNoProjectNo(@Param(value = "merBillNo") String merBillNo,@Param(value = "batchNo")  String batchNo,@Param(value = "projectNo") String projectNo);
	
	
	
	//跟新订单状态
	void updateOrderTransferRfToIps(@Param(value = "userId")  String userId,@Param(value = "merBillNo") String merBillNo, @Param(value = "batchNo") String batchNo,  @Param(value = "ipsBillNo") String ipsBillNo, @Param(value = "trdStatus") String trdStatus);

	
	//根据  userId   project_no   ipsbillno   
	String  queryOrderNo(@Param(value = "userId")  String userId, @Param(value = "ipsBillNo") String ipsBillNo, @Param(value = "productNo") String productNo);

	//coupons_info  表
	String  queryCouponId(@Param(value = "userId")  String userId);
	
	Integer FromMatoRf(@Param(value = "amount")  String amount,@Param(value = "userId")  String userId);
	
	
}
