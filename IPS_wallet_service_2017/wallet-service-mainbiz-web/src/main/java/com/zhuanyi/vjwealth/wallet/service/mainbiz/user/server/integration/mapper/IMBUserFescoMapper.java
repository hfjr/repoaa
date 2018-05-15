package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.FescoUserInfoDTO;

/**
 * fesco用户处理
 * @author caozhaoming
 * 2016年10月15日
 */
@Mapper
public interface IMBUserFescoMapper {

	/**
	 * 根据手机号查询是否是fesco用户
	 * @param phone
	 * @return
	 */
	int queryFescoUserCountByphone(@Param("phone")String phone);

	/**
	 * 根据openId查询记录是否存在
	 * @param phone
	 * @return
	 */
	int queryFescoUserCountByOpenId(@Param("openId")String openId);

	/**
	 * 新增fesco用户信息
	 * @param userInfo
	 */
	void insertFescoUserInfo(FescoUserInfoDTO userInfo);
	
	/**
	 * 修改fesco用户信息
	 * @param userInfo
	 */
	void updateFescoUserInfo(FescoUserInfoDTO userInfo);

	/**
	 * 查询fesco用户数据信息
	 * @return
	 */
	List<FescoUserInfoDTO> queryFescoUsers();

	/**
	 * 查询系统参数信息
	 * @param key
	 * @param string
	 * @return
	 */
	String getParamsValueByKeyAndGroup(@Param("key")String key, @Param("group")String group);

	/**
	 * fesco根据openid更新手机号
	 * @param openId
	 */
	void updateFescoPhoneByOpenId(FescoUserInfoDTO userInfo);
}
