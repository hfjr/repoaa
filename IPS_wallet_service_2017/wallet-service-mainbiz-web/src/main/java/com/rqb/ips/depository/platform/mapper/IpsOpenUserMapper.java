package com.rqb.ips.depository.platform.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IpsOpenUserMapper {

		//json
		void insertJsons(HashMap<String, Object> hashMap);
		//查询用户开没开过户（ips）
		HashMap<Object, Object> queryUserIsOpen(@Param("userId")String userId);
		//查询用户的类型
		Map<String, Object> queryUserType();
		//数据落地
		void insertUserInfo(@Param("userId")String userId, @Param("userName")String userName, @Param("identNo")String identNo,@Param("realName") String realName);
		//订单落地
		void insertUserBillNo(@Param("userId")String userId,@Param("merBillNo") String merBillNo,@Param("openAccountConfirm")String openAccountConfirm);
		//查询用户手机号 
		HashMap<Object, Object> queryUserPhone(@Param("userId")String userId);
		//查询用户次数
		int queryUserFreQuency(@Param("userId")String userId);

}
