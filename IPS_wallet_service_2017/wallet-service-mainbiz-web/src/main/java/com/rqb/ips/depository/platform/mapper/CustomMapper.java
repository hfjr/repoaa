package com.rqb.ips.depository.platform.mapper;

import java.util.List;

import com.fab.server.annotation.Mapper;
import com.rqb.ips.depository.platform.beans.IpsUserInfoDTO;

@Mapper
public interface CustomMapper {
	/**
	 * NAME_SPACE
	 */
	public static final String NAME_SPACE = CustomMapper.class.getName();
	/**
	 * ips用户信息查询 
	 */
	public static final String IPS_USER_INFO = NAME_SPACE + ".IPSUserInfoListByIPSNO";
	/**
	 * 分页查询用户信息
	 */
	public static final String QUERY_USER_INFO = NAME_SPACE + ".queryUserInfoListByPagination";

	/**
	 * 分页查询用户支付密码信息
	 */
	public static final String QUERY_USER_PASS_INFO = NAME_SPACE + ".queryUserPassInfoListByPagination";
	/**
	 * 分页查询批量开户批次号信息
	 */
	public static final String QUERY_BATCH_OPEN_CHECK_LIST = NAME_SPACE + ".queryBatchOpenCheckListByPagination";
	/**
	 * 分页查询批量开户批次号明细信息
	 */
	public static final String QUERY_BATCH_OPEN_DETAIL_LIST = NAME_SPACE + ".queryBatchOpenDetailPaginationList";
	/**
	 * 分页查询批量开户审批信息
	 */
	public static final String QUERY_BATCH_OPEN_AUDiT_LIST = NAME_SPACE + ".queryBatchOpenAccountAuditPagination";
	/**
	 * 分页查询用户账户信息
	 */
	public static final String QUERY_WJ_USER_TRADE_ACCOUNT = NAME_SPACE + ".querywjAccountinfopagination";
	/**
	 * 分页查询用户银行卡信息
	 */
	public static final String QUERY_WJ_USER_TRADE_ACCOUNT_CARD = NAME_SPACE + ".queryWjTradeAccountCardPagination";

	/**
	 * 分页查询用户身份
	 */
	public static final String QUERY_WJ_CUSTOM_CERTIFICATION = NAME_SPACE + ".queryCustomCertificationInfo";

	/**
	 * 分页查询企业用户信息
	 */
	public static final String QUERY_ENTERPRISECUSTOMINFO = NAME_SPACE + ".queryEnterpriseCustomInfoPagination";



	/**
	 * 通过手机号查询ips账号
	 */
	List<IpsUserInfoDTO> queryuseripsbyphone(IpsUserInfoDTO ipsUserInfoDTO);
	/**
	 * 通过ips查询ips表的信息
	 */
	List<IpsUserInfoDTO> queryuseripsbyips(IpsUserInfoDTO ipsUserInfoDTO);
	/**
	 * <!-- 更新ips账号信息 byips -->
	 */
	void updataipsinfobyips(IpsUserInfoDTO ipsUserInfoDTO);

	/**
	 * <!-- 新增ips账号信息 byips -->
	 */
	void insertipsinfobyips(IpsUserInfoDTO ipsUserInfoDTO);
	/**
	 * <!-- 更新ips交易查询 -->
	 */
	void updataipsmerbymerBillNo(IpsUserInfoDTO ipsUserInfoDTO);
	/**
	 * <!-- 更新ips用户信息查询 -->
	 */
	void updataipsuserinfobyips(IpsUserInfoDTO ipsUserInfoDTO);

	/**
	 * <!-- 更新ips余额查询 -->
	 */
	void updataipsbalancebyips(IpsUserInfoDTO ipsUserInfoDTO);


	
	
	
	


	
}
