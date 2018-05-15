package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;

@Mapper
public interface IUserBindingCardMapper {

	/**
	 * 查询绑定的银行卡
	 * @param userId 用户Id
	 * @param bizType 订单类型
	 *
	 * @return
	 */
	public List<BindingCardDTO> queryUserbindingCardList(Map<String, String> dataMap);

	public List<BindingCardDTO> querySecurityWithholdCard(@Param("userId") String userId,@Param("bizType") String bizType);
	
	public List<BindingCardDTO> querySecurityWithholdCardV2(@Param("userId") String userId,@Param("bizType") String bizType);

	/**
	 * 根据业务类型,查询代扣的银行卡列表
	 * @param bizType
	 * @return
	 */
	public List<BindingCardDTO> queryWithholdBankList(@Param("bizType") String bizType);
	
	/**
	 * 查询绑定的代扣银行卡
	 * @param userId
	 * @param bizType
	 * @param bankCardNo
	 * @return
	 */
	public List<BindingCardDTO> queryWithholdCard(@Param("userId") String userId,@Param("bizType") String bizType,@Param("bankCardNo") String bankCardNo);

}
