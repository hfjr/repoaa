package com.rqb.ips.depository.platform.mapper;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
/**
 *  提现更新订单表
 * @author whb
 *
 */
@Mapper
public interface IpsWithDrawMapper {

	void updateWithDraw(@Param(value = "merBillNo") String merBillNo,@Param(value = "ipsBillNo") String ipsBillNo,@Param(value = "trdStatus") String trdStatus);
	void updateOrderWithDraw(Map map);
	void saveOrderRecord(Map<String, Object> map);
	void saveOrderWithDrawRecord(Map<String, Object> map);
	Boolean queryWithDrawStatus(@Param(value = "merBillNo") String merBillNo);

	String queryUserIdByIpsAcctNo(@Param(value = "ipsAcctNo") String ipsAcctNo);

	//解锁冻结资金
	void deductionFreezeMaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	
	void cancelFreezeMaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	boolean queryUserIsOpenAccount(@Param(value = "userId") String userId);
	
	
}
