package com.rqb.ips.depository.platform.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

/**
 * 
 * title:开户返回
 * @author sunxiaolei
 *
 */
@Mapper
public interface ipsOpenUserResultMapper {

	void insertInfo(HashMap<Object, Object> map);

	Map<String, String> queryUserId(@Param("merBillNo")String merBillNo);

	void updateUserOrder(@Param("ipsBillNo")String ipsBillNo,@Param("status")String status, @Param("id")String id,@Param("merBillNo")String merBillNo);

	void insertUserId(@Param("id")String id, @Param("ipsAcctNo")String ipsAcctNo,@Param("status")String status);

	Map<String, String> queryUserOpenStuats(@Param("id")String id,@Param("merBillNo") String merBillNo);



}
