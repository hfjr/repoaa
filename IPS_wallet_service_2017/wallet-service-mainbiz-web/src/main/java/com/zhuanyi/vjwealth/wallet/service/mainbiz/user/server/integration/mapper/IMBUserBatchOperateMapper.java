package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserOpenAccountDTO;

/**
 * @author xuewentao
 *
 */
@Mapper
public interface IMBUserBatchOperateMapper {

	/**
	 * 查询开户用户记录(成功状态)
     * @param @param fileBatchNo    
	 */
	List<UserOpenAccountDTO> queryBatchOpenAccountByFileBatchNo(@Param("fileBatchNo") String fileBatchNo);
	
	/**
	 * 查询开户申请记录
     * @param @param fileBatchNo    
	 */
	List<UserOpenAccountDTO> queryBatchOpenAccountRequestByFileBatchNo(@Param("fileBatchNo") String fileBatchNo);
	
	/**
	 * 查询审核通过用户Id (营销活动-事件采集用)
     * @param @param fileBatchNo
	 */
	List<UserOpenAccountDTO> queryBatchOpenAccountSuccessUserByFileBatchNo(@Param("fileBatchNo") String fileBatchNo);

	int countBatchOpenAccountByFileBatchNo(@Param("fileBatchNo") String fileBatchNo);
	
	void createUnActiveUser(@Param("userId") String userId, @Param("phone") String phone, @Param("realName") String realName, @Param("indentityType") String indentityType, @Param("indentityNo") String indentityNo, @Param("sex") String sex, @Param("enterpriseNo") String enterpriseNo);
	
	void createSecurityAccountCard(@Param("userId") String userId, @Param("cardId") String cardId, @Param("cardOwer") String cardOwer, @Param("bankCardNo") String bankCardNo, @Param("asideBankPhone") String asideBankPhone, @Param("bankName") String bankName, @Param("bankCode") String bankCode);
	
	void addUserTransLockInfo(@Param("userId") String userId);
	
	void saveOpenAccountToTemp(@Param("fileBatchNo") String fileBatchNo,@Param("userId") String userId);
	
	void createUserAccountInfo(@Param("userId") String userId, @Param("accountId") String accountId, @Param("tradeType") String tradeType);
	
	/**
	 * 根据文件校验结果更新文件批次表
     * @param  fileBatchNo    
	 */
	int updateWjChannelBatchFileByFileBatchNo(@Param("fileCheckStatus") String fileCheckStatus,@Param("fileCheckMessage") String fileCheckMessage,@Param("fileBatchNo") String fileBatchNo,@Param("updateBy") String updateBy);
	
	/**
	 * 添加开户明细信息wj_channel_batch_open_account表 
	 */
	int insertWjChannelBatchOpenAccount(UserOpenAccountDTO userOpenAccountDTO);
	
	/**
	 * 更新开户明细信息wj_channel_batch_open_account表 
	 */
	int updateWjChannelBatchOpenAccount(UserOpenAccountDTO userOpenAccountDTO);
	
	/**
	 * 
	* @Title: deleteTradeAccountCardForBatchOpenAccountRollback 
	* @Description: 回滚账户银行卡数据 
	* @param @param fileBatchNo    
	* @return void    返回类型 
	* @throws
	 */
	void deleteTradeAccountCardForBatchOpenAccountRollback(String fileBatchNo);
	
	/**
	 * 
	* @Title: deleteUserTradeAccountForBatchOpenAccountRollback 
	* @Description:  回滚用户帐户数据
	* @param @param fileBatchNo    
	* @return void    返回类型 
	* @throws
	 */
	void deleteUserTradeAccountForBatchOpenAccountRollback(String fileBatchNo);
	
	/**
	 * 
	* @Title: deleteUserInfoForBatchOpenAccountRollback 
	* @Description:  回滚用户信息数据 
	* @param @param fileBatchNo    
	* @return void    返回类型 
	* @throws
	 */
	void deleteUserInfoForBatchOpenAccountRollback(String fileBatchNo);
	
	/**
	 * 
	* @Title: deleteBatchOpenAccountRunTemp 
	* @Description:  删除临时数据
	* @param @param fileBatchNo    
	* @return void    返回类型 
	* @throws
	 */
	void deleteBatchOpenAccountRunTemp(String fileBatchNo);
	
	/**
	 * 
	* @Title: updateBatchOpenAccountFileHandledStatusFail 
	* @Description:  开户批插数据失败更新批处理文件状态
	* @param @param map 
	* 
	* 	fileBatchNo		文件批次号
	* 	handledMessage	处理失败信息
	*   
	* @return void    返回类型 
	* @throws
	 */
	void updateBatchOpenAccountFileHandledStatusFail(@Param("fileBatchNo")String fileBatchNo,@Param("handledMessage")String handledMessage);
		
	/**
	 * 
	* @Title: updateBatchOpenAccountFileHandledStatusSuccess 
	* @Description:  批量开户成功后更新文件成功处理状态
	* @param @param fileBatchNo    
	* @return void    返回类型 
	* @throws
	 */
	void updateBatchOpenAccountFileHandledStatusSuccess(String fileBatchNo);
	

	/**
	 * 更新开户明细的审核结果
	 * @param checkStatus
	 * @param returnMessage
	 * @param id
	 */
	void updateUserOpenAccountCheckStatus(@Param("checkStatus")String checkStatus,@Param("returnMessage")String returnMessage,@Param("id")String id);
	
	
	void deleteSingleUserByPhone(@Param("phone")String phone);
	
	//手机号是否存在
	Integer countPhoneExits(@Param("phone")String phone);
	
	//证件号是否存在
	Integer countIdnoExits(@Param("indentityNo")String indentityNo);
	
	//银行信息获取
	List<BankInfoDTO> getBankInfo();
	
	//挂在蔚捷渠道下
	void saveChannelForWallet();
	
	//挂在各自渠道下
	void saveChannelForCompany();
	
	/**
	 * 冻结金额,将账户的可用余额冻结至冻结金额字段
	 * @param amount 金额
	 * @param tradeAccountId 校验账户ID
	 */
	int updateAvailableAmountToFrozenAmount(@Param("amount") String amount,@Param("userId") String userId);
	
	/**
	 * 解冻扣款，从账户的冻结金额字段直接扣减金额，划款至员工账户
	 * @param amount 金额
	 * @param tradeAccountId 校验账户ID
	 */
	int updateFrozenAmountToEmployee(@Param("amount") String amount,@Param("userId") String userId);
	
	/**
	 * 解冻返款，从账户的冻结金额返款回可用余额字段
	 * @param amount 金额
	 * @param tradeAccountId 校验账户ID
	 */
	int updateFrozenAmountToAvailableAmount(@Param("amount") String amount,@Param("userId") String userId);
}
