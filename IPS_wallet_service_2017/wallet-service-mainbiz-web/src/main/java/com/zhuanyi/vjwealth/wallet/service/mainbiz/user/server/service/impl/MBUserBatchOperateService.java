package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.Enum.EmailTypeEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserOpenAccountValidateDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserOpenAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserBatchOperateMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserBatchOperateService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.util.CheckIdCardUtils;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service
public class MBUserBatchOperateService implements IMBUserBatchOperateService {

	@Autowired
	private IMBUserBatchOperateMapper userMapper;

	@Autowired
	private IMBUserAccountMapper userAccountMapper;

	@Autowired
	private ISequenceService sequenceService;

	@Remote
	ISendEmailService sendEmailService;

	// 银行信息
	Map<String, String> bankMap;

	//根据文件批次号开户
	public void batchOpenAccount(String fileBatchNo) {
		// 校验批字号
		this.checkFileBatchNoValid(fileBatchNo);
		// 1. 查询开户用户
		List<UserOpenAccountDTO> userOpenAccountDTOList = userMapper
				.queryBatchOpenAccountByFileBatchNo(fileBatchNo);
//		try {
//			// 2. 循环开户
//			for (UserOpenAccountDTO userOpenAccountDTO : userOpenAccountDTOList) {
//				checkOpenUserValidate(userOpenAccountDTO);
//				registeredUserForEnterprise(userOpenAccountDTO);
//			}
//			// 3. 更新成功批量开户后文件成功处理状态
//			userMapper
//					.updateBatchOpenAccountFileHandledStatusSuccess(fileBatchNo);
//			// 4. 删除临时数据
//			userMapper.deleteBatchOpenAccountRunTemp(fileBatchNo);
//		} catch (Exception e) {
//			BaseLogger.error("企业开户创建用户失败:" + e);
//			// 发送异常邮件
//			// sendEmailService.sendSystemErrorWarn("批量开户失败,批字号["+fileBatchNo+"],错误原因:<br/>"+e);
//			// 3. 更新为失败状态
//			userMapper.updateBatchOpenAccountFileHandledStatusFail(fileBatchNo,
//					e.toString().length() < 6000 ? e.toString() : e.toString()
//							.substring(0, 6000));
//			// 4. 回滚数据
//			this.batchOpenAccountRollback(fileBatchNo);
//			throw new AppException(e.toString());
//		}		
		this.batchOpenAccount(userOpenAccountDTOList);
	}

	//根据开户明细数据开户
	public void batchOpenAccount(List<UserOpenAccountDTO> userOpenAccountDTOList) {		
		try {
		//1. 批量校验开户信息，将返回开户校验失败的信息
		checkOpenUserValidate(userOpenAccountDTOList);
		}
		catch (Exception e) {
			BaseLogger.error("企业开户数据校验失败:" + e);
			throw new AppException(e.toString());
		}				
		try {
			//2.循环开户
			for (UserOpenAccountDTO userOpenAccountDTO : userOpenAccountDTOList) {
				registeredUserForEnterprise(userOpenAccountDTO);
			}
			//3. 更新成功开户明细状态
			for (UserOpenAccountDTO userOpenAccountDTO : userOpenAccountDTOList) {
				userMapper.updateUserOpenAccountCheckStatus(UserOpenAccountDTO.AUDIT_STATUS_SUCCESS,
						"开户成功",userOpenAccountDTO.getId());			
			}
			// 4. 删除临时数据
			userMapper.deleteBatchOpenAccountRunTemp(userOpenAccountDTOList.get(0).getFileBatchNo());
		} catch (Exception e) {
			BaseLogger.error("企业开户创建用户失败:" + e);
			// 发送异常邮件
//			sendEmailService.sendSystemErrorWarn("批量开户失败,批字号["+fileBatchNo+"],错误原因:<br/>"+e);
			// 3. 更新为失败状态
			userMapper.updateBatchOpenAccountFileHandledStatusFail(userOpenAccountDTOList.get(0).getFileBatchNo(),
					e.toString().length() < 6000 ? e.toString() : e.toString()
							.substring(0, 6000));
			for (UserOpenAccountDTO userOpenAccountDTO : userOpenAccountDTOList) {
				userMapper.updateUserOpenAccountCheckStatus(UserOpenAccountDTO.AUDIT_STATUS_FAIL,
						e.toString().length() < 6000 ? e.toString() : e.toString()
								.substring(0, 6000),userOpenAccountDTO.getId());			
			}
			// 4. 回滚数据
			this.batchOpenAccountRollback(userOpenAccountDTOList.get(0).getFileBatchNo());
			throw new AppException(e.toString());
		}
	}
	
	
	private void checkFileBatchNoValid(String fileBatchNo) {

		if (StringUtils.isBlank(fileBatchNo))
			throw new AppException("文件批字号不能为空");
		if (userMapper.countBatchOpenAccountByFileBatchNo(fileBatchNo) == 0) {
			// 更新状态为失败
			// userMapper.updateBatchOpenAccountFileHandledStatusFail(fileBatchNo,
			// "批量开户文件为空,原因如下:1.批字号不存在2.该批次文件不是正确的状态,已被处理过");
			throw new AppException("批量开户文件为空,原因如下:1.批字号不存在2.该批次文件状态不正确,已被处理过");
		}
	}

	private void registeredUserForEnterprise(
			UserOpenAccountDTO userOpenAccountDTO) {
		// 创建用户信息
		String userId = getUserNoId(ISequenceService.SEQ_NAME_USER_ID_SEQ);
		if (StringUtils.isNotBlank(userOpenAccountDTO.getIndentityType())
				&& userOpenAccountDTO.getIndentityType().equals("01"))// 身份证
			userOpenAccountDTO.setSex(CheckIdCardUtils
					.getGenderByIdCard(userOpenAccountDTO.getIndentityNo()));
		// 1.创建未激活用户信息
		userMapper.createUnActiveUser(userId, userOpenAccountDTO.getPhone(),
				userOpenAccountDTO.getRealName(),
				userOpenAccountDTO.getIndentityType(),
				userOpenAccountDTO.getIndentityNo(),
				userOpenAccountDTO.getSex(),
				userOpenAccountDTO.getEnterpriseNo());
		// 创建主账户
		String accountMaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountMaId, "ma");
		// 创建e账户
		String accountEaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountEaId, "ea");
		// 创建v+账户
		String accountV1Id = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountV1Id, "v1");

		// 创建rf账户
		String accountRfId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountRfId, "rf");
		
		// 创建ln账户
		String accountLnId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountLnId, "ln");
		
		// 创建la账户
		String accountLaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountLaId, "la");
		
		// 创建lf账户
		String accountLfId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountLfId, "lf");

		// 创建ta账户， add zhangyingxuan on 20160721 for T金所使用账户
		String accountTaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountTaId, "ta");

		// 创建锁
		userMapper.addUserTransLockInfo(userId);
		// 插入安全卡
		String cardId = getCardId(ISequenceService.SEQ_NAME_CARD_SEQ);
		userMapper.createSecurityAccountCard(userId, cardId,
				userOpenAccountDTO.getRealName(),
				userOpenAccountDTO.getBankCardNo(),
				userOpenAccountDTO.getAsideBankPhone(),
				userOpenAccountDTO.getBankName(),
				userOpenAccountDTO.getBankCode());
		// 2. 插入临时表
		userMapper.saveOpenAccountToTemp(userOpenAccountDTO.getFileBatchNo(),
				userId);
		// 加载渠道..
		this.saveChannelForWXRef();
	}

	/**
	 * 1.单个开户开启事物
	 */
	@Transactional
	public void openSignleUserAccount(UserOpenAccountDTO userOpenAccountDTO) {
		// 校验开户数据
		this.checkOpenUserValidate(userOpenAccountDTO);
		// 创建用户信息
		String userId = getUserNoId(ISequenceService.SEQ_NAME_USER_ID_SEQ);
		// 1.创建未激活用户信息
		userMapper.createUnActiveUser(userId, userOpenAccountDTO.getPhone(),
				userOpenAccountDTO.getRealName(),
				userOpenAccountDTO.getIndentityType(),
				userOpenAccountDTO.getIndentityNo(),
				userOpenAccountDTO.getSex(),
				userOpenAccountDTO.getEnterpriseNo());
		// 创建主账户
		String accountMaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountMaId, "ma");
		// 创建e账户
		String accountEaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountEaId, "ea");

		// 创建v+账户
		String accountV1Id = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountV1Id, "v1");

		// 创建rf账户
		String accountRfId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountRfId, "rf");
		
		// 创建ln账户
		String accountLnId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountLnId, "ln");
		
		// 创建la账户
		String accountLaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountLaId, "la");
		
		// 创建lf账户
		String accountLfId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountLfId, "lf");

		// 创建ta账户， add zhangyingxuan on 20160721 for T金所使用账户
		String accountTaId = getAccountId(ISequenceService.SEQ_NAME_USER_ACCOUNT_SEQ);
		userMapper.createUserAccountInfo(userId, accountTaId, "ta");

		// 创建锁
		userMapper.addUserTransLockInfo(userId);
		// 插入安全卡
		String cardId = getCardId(ISequenceService.SEQ_NAME_CARD_SEQ);
		userMapper.createSecurityAccountCard(userId, cardId,
				userOpenAccountDTO.getRealName(),
				userOpenAccountDTO.getBankCardNo(),
				userOpenAccountDTO.getAsideBankPhone(),
				userOpenAccountDTO.getBankName(),
				userOpenAccountDTO.getBankCode());
		this.saveChannelForWXRef();

	}

	/**
	 * 微信端渠道管理 1.所有的用户都是蔚捷的用户(wallet) 2.用户根据渠道编号挂在各自的渠道下
	 */
	private void saveChannelForWXRef() {
		userMapper.saveChannelForWallet();
		userMapper.saveChannelForCompany();
	}

	/**
	 * 回滚数据
	 */
	private void batchOpenAccountRollback(String fileBatchNo) {
		// 1. 回滚银行卡数据
		userMapper
				.deleteTradeAccountCardForBatchOpenAccountRollback(fileBatchNo);
		// 2. 回滚账户数据
		userMapper
				.deleteUserTradeAccountForBatchOpenAccountRollback(fileBatchNo);
		// 3. 回滚用户数据
		userMapper.deleteUserInfoForBatchOpenAccountRollback(fileBatchNo);
	}

	/**
	 * 校验单个开户数据
	 */	 
	private void checkOpenUserValidate(UserOpenAccountDTO userOpenAccountDTO) {
		if (userOpenAccountDTO == null)
			throw new AppException("开户用户数据为空");
		// 参数校验 TODO ..
		userOpenAccountDTO.checkParamValidate();
		// 校验业务逻辑
		// 1.手机号
		if (userMapper.countPhoneExits(userOpenAccountDTO.getPhone()) > 0) {
			throw new AppException("手机号已存在" + userOpenAccountDTO.getPhone()
					+ ",请检查 该批次/用户表 里是否有重复手机号");
		}

		// 2.身份证
		if (userMapper.countIdnoExits(userOpenAccountDTO.getIndentityNo()) > 0) {
			throw new AppException("开户证件号码重复"
					+ userOpenAccountDTO.getIndentityNo()
					+ ",请检查 该批次/用户表 里是否有重复身份证号");
		}
	}
	
	/**
	 * 校验批量开户数据
	 */	 
	private void checkOpenUserValidate(List<UserOpenAccountDTO> userOpenAccountDTOList) {
        Boolean resultErr=false;
		if (userOpenAccountDTOList == null | userOpenAccountDTOList.size()<1){
			throw new AppException("开户用户数据为空");
		}
		for (UserOpenAccountDTO userOpenAccountDTO : userOpenAccountDTOList) {
			// 校验业务逻辑
			// 1.手机号
			if (userMapper.countPhoneExits(userOpenAccountDTO.getPhone()) > 0) {
				userMapper.updateUserOpenAccountCheckStatus(UserOpenAccountDTO.AUDIT_STATUS_FAIL,
						"开户失败;开户证件号码已存在",userOpenAccountDTO.getId());
				resultErr=true;
			}
			// 2.身份证
			if (userMapper.countIdnoExits(userOpenAccountDTO.getIndentityNo()) > 0) {
				userMapper.updateUserOpenAccountCheckStatus(UserOpenAccountDTO.AUDIT_STATUS_FAIL,
						"开户失败;开户证件号码重复:"+ userOpenAccountDTO.getIndentityNo(),userOpenAccountDTO.getId());
				resultErr=true;
			}
		}
		if(resultErr){
			throw new AppException("开户失败,请查看开户明细!");
		}
	}
	
	
	private String getUserNoId(String sequenceName) {
		return "US" + new SimpleDateFormat("yyyyMMdd").format(new Date())
				+ sequenceService.getNextStringValue(sequenceName, 10);
	}

	private String getAccountId(String sequenceName) {
		return "AC" + new SimpleDateFormat("yyyyMMdd").format(new Date())
				+ sequenceService.getNextStringValue(sequenceName, 10);
	}

	private String getCardId(String sequenceName) {
		return "CA" + new SimpleDateFormat("yyyyMMdd").format(new Date())
				+ sequenceService.getNextStringValue(sequenceName, 10);
	}

	/**
	 * 批量开户数据校验
	 * 
	 * @param userAccountList
	 *            传入开户用户数据
	 * @param enterpriseNo
	 *            企业编号
	 * @return
	 */
	public UserOpenAccountValidateDTO checkBatchUserOpenAccountDataByfileBatchNo(
			String fileBatchNo) {
		// 根据批次号查询开户用户数据
		List<UserOpenAccountDTO> userOpenAccountDTOList = userMapper
				.queryBatchOpenAccountRequestByFileBatchNo(fileBatchNo);
		if(userOpenAccountDTOList==null |userOpenAccountDTOList.size()<1){
			throw new AppException("批次号错误,无对应开户数据!");
		}
		return checkBatchUserOpenAccountData(userOpenAccountDTOList, "");
	}

	/**
	 * 批量开户数据校验
	 * 
	 * @param userAccountList
	 *            传入开户用户数据
	 * @param enterpriseNo
	 *            企业编号
	 * @return
	 */
	public UserOpenAccountValidateDTO checkBatchUserOpenAccountData(
			List<UserOpenAccountDTO> userAccountList, String enterpriseNo) {
		UserOpenAccountValidateDTO batchOpenAccountValidateDTO = new UserOpenAccountValidateDTO();
		boolean allDataCheckFlag = true;
		int indexNo = 0;
		String fileBatchNo = "";
		bankMap = new HashMap<String, String>();
		getBankInfo(bankMap);
		for (UserOpenAccountDTO wjChannelBatchOpenAccountDTO : userAccountList) {
			fileBatchNo = wjChannelBatchOpenAccountDTO.getFileBatchNo();
			wjChannelBatchOpenAccountDTO.setEnterpriseNo(enterpriseNo);
			// wjChannelBatchOpenAccountDTO.setBankCardNo(wjChannelBatchOpenAccountDTO.getBankCardNo().replaceAll("\\s*",
			// ""));
			// 校验同一批次的批量开户数据中是否有重复的数据
			if (duplicateDataInThisBatch(wjChannelBatchOpenAccountDTO,
					userAccountList, indexNo)) {
				allDataCheckFlag = false;
			}
			// 开户数据分类校验
			else if (!checkUserAcccountData(wjChannelBatchOpenAccountDTO)) {
				allDataCheckFlag = false;
			}
			indexNo++;
		}
		batchOpenAccountValidateDTO.setSuccessful(allDataCheckFlag);
		batchOpenAccountValidateDTO.setFileBatchNo(fileBatchNo);
		if (allDataCheckFlag) {
			batchOpenAccountValidateDTO.setErrorMessage("校验成功,待审核");
			batchOpenAccountValidateDTO
					.setFileCheckStatus(UserOpenAccountValidateDTO.FILE_CHECK_SUCCESSFUL);
		} else {
			batchOpenAccountValidateDTO.setErrorMessage("数据有误,请修改数据");
			batchOpenAccountValidateDTO
					.setFileCheckStatus(UserOpenAccountValidateDTO.FILE_CHECK_FAIL);
		}
		// 保存文件内容到返回结果中
		batchOpenAccountValidateDTO.setBatchOpenAccountDTOList(userAccountList);

		// 将校验结果保存到wj_channel_batch_file表中,并且将文件数据保存到wj_channel_batch_open_account表中
		saveFileDataToBatchAccount(batchOpenAccountValidateDTO);
		return batchOpenAccountValidateDTO;
	}

	/**
	 * 校验同一批次的批量开户数据中是否有重复的数据
	 * 
	 * @param BatchOpenAccountDTO
	 *            单个开户数据
	 * @param userAccountList
	 *            批量开户数据
	 * @param indexNo
	 * @return
	 */
	private boolean duplicateDataInThisBatch(
			UserOpenAccountDTO BatchOpenAccountDTO,
			List<UserOpenAccountDTO> userAccountList, int indexNo) {
		boolean checkFlag = false;
		for (int i = 0; i < userAccountList.size(); i++) {
			UserOpenAccountDTO channelBatchOpenAccountDTO = new UserOpenAccountDTO();
			channelBatchOpenAccountDTO = userAccountList.get(i);
			if (i != indexNo
					&& (BatchOpenAccountDTO.getPhone()
							.equals(channelBatchOpenAccountDTO.getPhone()))) {
				returnDataFail(BatchOpenAccountDTO, "本批次中:"+channelBatchOpenAccountDTO.getRealName()+"的手机号"
						+ BatchOpenAccountDTO.getPhone() + "已被占用，请核实后重新提交;");
				checkFlag = true;
			}
			if (i != indexNo
					&& (BatchOpenAccountDTO.getIndentityNo()
							.equals(channelBatchOpenAccountDTO.getIndentityNo()))) {
				returnDataFail(BatchOpenAccountDTO, "本批次中:"+channelBatchOpenAccountDTO.getRealName()+"的证件号码"
						+ BatchOpenAccountDTO.getIndentityNo() + "已被占用，请核实后重新提交;");
				checkFlag = true;
			}
		}

		return checkFlag;
	}

	/**
	 * 批量开户数据分类校验
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @return
	 */
	private boolean checkUserAcccountData(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO) {
		boolean allDataCheckFlag = true;
		// 默认为正确,下面校验错误情形
		wjChannelBatchOpenAccountDTO
				.setCheckStatus(UserOpenAccountDTO.CHECK_STATUS_SUCCESS);
		// 初始化返回信息为空
		wjChannelBatchOpenAccountDTO.setReturnMessage("");

		// 校验银行名称、银行编码是否正确
		if (!checkBankCodeAndBankName(wjChannelBatchOpenAccountDTO)) {
			allDataCheckFlag = false;
		}

		// 校验银行卡号、银行卡户主姓名不能为空
		if (!checkCardNoAndCardOwner(wjChannelBatchOpenAccountDTO)) {
			allDataCheckFlag = false;
		}

		// 校验证件号码是否正确
		if (!checkIdentityNoAndIdentityType(wjChannelBatchOpenAccountDTO)) {
			allDataCheckFlag = false;
		}
		// 校验手机号是否正确
		if (!checkPhone(wjChannelBatchOpenAccountDTO)) {
			allDataCheckFlag = false;
		}
		return allDataCheckFlag;
	}

	/**
	 * 批量开户数据 校验银行名称/银行编码
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @return
	 */
	private boolean checkBankCodeAndBankName(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO) {
		String bankName = wjChannelBatchOpenAccountDTO.getBankName();
		if (StringUtils.isBlank(bankName))
			return returnDataFail(wjChannelBatchOpenAccountDTO, "银行名称不能为空;");
		// 根据银行卡号获取银行名称
		if (StringUtils.isBlank(bankMap.get(bankName))) {
			return returnDataFail(wjChannelBatchOpenAccountDTO, "银行名称不正确;");
		}
		return true;
	}

	/**
	 * 批量开户数据 银行卡号 银行卡户主姓名
	 */
	private boolean checkCardNoAndCardOwner(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO) {
		String cardName = wjChannelBatchOpenAccountDTO.getBankName();
		String cardNo = wjChannelBatchOpenAccountDTO.getBankCardNo();
		if (StringUtils.isBlank(cardName))
			return returnDataFail(wjChannelBatchOpenAccountDTO, "姓名不能为空;");
		if (StringUtils.isBlank(cardNo)) {
			return returnDataFail(wjChannelBatchOpenAccountDTO, "银行卡号不正确;");
		}
		if (cardNo.length() <10) {
			return returnDataFail(wjChannelBatchOpenAccountDTO, "银行卡号不正确;");
		}
		return true;
	}

	/**
	 * 批量开户数据：校验 证件类型和证件号码是否正确
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @return
	 */
	private boolean checkIdentityNoAndIdentityType(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO) {

		String idNo = wjChannelBatchOpenAccountDTO.getIndentityNo()
				.toUpperCase();
		String idType = wjChannelBatchOpenAccountDTO.getIndentityType();

		if (StringUtils.isBlank(idType))
			return returnDataFail(wjChannelBatchOpenAccountDTO, "证件类型不能为空;");

		if (StringUtils.isBlank(idNo))
			return returnDataFail(wjChannelBatchOpenAccountDTO, "证件号码不能为空;");

		if ((!CheckIdCardUtils.validateCard(idNo)) && "01".equals(idType))
			return returnDataFail(wjChannelBatchOpenAccountDTO, "证件号码错误;");

		// 校验该证件号码是否已存在
		if (userMapper.countIdnoExits(idNo) > 0) {
			String existentInfoValidate = isEqualForUserInfoExistent(
					wjChannelBatchOpenAccountDTO.getUserId(), "证件号码");
			if (existentInfoValidate.length() > 0) {
				return returnDataExistent(wjChannelBatchOpenAccountDTO,
						existentInfoValidate);
			}
		}
		return true;
	}

	/**
	 * 批量开户数据：校验手机号格式是否正确,校验手机号+交易类型是否存在.
	 */
	private boolean checkPhone(UserOpenAccountDTO wjChannelBatchOpenAccountDTO) {
		// 手机号
		String phone = wjChannelBatchOpenAccountDTO.getPhone();
		// 账号类型类型(ma)
		String tradeType = "ma";
		if (StringUtils.isBlank(phone)) {
			return returnDataFail(wjChannelBatchOpenAccountDTO, "手机号不能为空;");
		}
		if (phone.length() != 11) {
			return returnDataFail(wjChannelBatchOpenAccountDTO, "手机号格式不正确;");
		}
		if (userMapper.countPhoneExits(phone) > 0) {
			// 如果已开户手机号存在对应的'ma'电子交易账号,不合法
			if (userAccountMapper.queryAccountAvailableAmount(
					wjChannelBatchOpenAccountDTO.getUserId(), tradeType) != null) {
				String existentInfoValidate = isEqualForUserInfoExistent(
						wjChannelBatchOpenAccountDTO.getUserId(), "手机号码");
				if (existentInfoValidate.length() > 0) {
					return returnDataExistent(wjChannelBatchOpenAccountDTO,
							existentInfoValidate);
				}
			}
			return returnDataExistent(wjChannelBatchOpenAccountDTO,
					wjChannelBatchOpenAccountDTO.getRealName()+
					"的手机号" + phone
					+ "已被占用，请核实后重新提交");
		}

		return true;
	}

	/**
	 * 查询已开户用户的详情
	 * 
	 * @param BatchOpenAccountDTO
	 * @param userInfoDTO
	 */
	private String isEqualForUserInfoExistent(String userId, String checkType) {

		StringBuffer returnSb = new StringBuffer();
		// 默认错误信息：已开户
		returnSb.append(checkType).append("已开户; ");
		// 查询账户安全卡信息,反馈错误信息: 判断是否已开户但未绑定安全卡
		if (userAccountMapper.queryCardCountByUserId(userId) == 0) {
			returnSb.append(checkType).append("已开户但未绑定银行卡; ");
		}
		return returnSb.toString();
	}

	/**
	 * 将错误类型DTO,添加标记为,并且返回信息已存在
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @param message
	 * @return
	 */
	private boolean returnDataExistent(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO, String message) {
		dataExistentMessage(wjChannelBatchOpenAccountDTO, message);
		return false;
	}

	/**
	 * 将错误类型DTO,添加标记为,并且返回错误
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @param message
	 * @return
	 */
	private boolean returnDataFail(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO, String message) {
		dataErrorMessage(wjChannelBatchOpenAccountDTO, message);
		return false;
	}

	/**
	 * 返回校验的错误消息
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @param message
	 */
	private void dataErrorMessage(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO, String message) {
		wjChannelBatchOpenAccountDTO
				.setCheckStatus(UserOpenAccountDTO.CHECK_STATUS_FAIL);
		String lastMsg = wjChannelBatchOpenAccountDTO.getReturnMessage() == null ? ""
				: wjChannelBatchOpenAccountDTO.getReturnMessage();
		// 累积错误消息
		wjChannelBatchOpenAccountDTO.setReturnMessage(lastMsg + message);
	}

	/**
	 * 校验数据,返回信息存在消息
	 * 
	 * @param wjChannelBatchOpenAccountDTO
	 * @param message
	 */
	private void dataExistentMessage(
			UserOpenAccountDTO wjChannelBatchOpenAccountDTO, String message) {
		wjChannelBatchOpenAccountDTO
				.setCheckStatus(UserOpenAccountDTO.CHECK_STATUS_EXISTENT);
		String lastMsg = wjChannelBatchOpenAccountDTO.getReturnMessage() == null ? ""
				: wjChannelBatchOpenAccountDTO.getReturnMessage();
		// 累积错误消息
		wjChannelBatchOpenAccountDTO.setReturnMessage(lastMsg + message);
	}

	/**
	 * 将校验结果保存到wj_channel_batch_file表中,并且将文件数据保存到wj_channel_batch_open_account表中
	 * 
	 * @param result
	 *            true:校验成功；false:校验失败
	 * @param list
	 */
	private void saveFileDataToBatchAccount(
			UserOpenAccountValidateDTO batchOpenAccountValidateDTO) {
		// 1.保存开户数据 (wj_channel_batch_open_account)
		saveToBatchAccount(batchOpenAccountValidateDTO
				.getBatchOpenAccountDTOList());
		
		// 2. 更新wj_channel_batch_file表
		userMapper.updateWjChannelBatchFileByFileBatchNo(
				batchOpenAccountValidateDTO.getFileCheckStatus(),
				batchOpenAccountValidateDTO.getErrorMessage(),
				batchOpenAccountValidateDTO.getFileBatchNo(),
				batchOpenAccountValidateDTO.getBatchOpenAccountDTOList().get(0)
						.getUpdateBy());
		



		// 3. 发送审核通知邮件
		if (batchOpenAccountValidateDTO.isSuccessful()) {
			Map<String, Object> sendMailParameter = new HashMap<String, Object>();
			sendMailParameter.put("enterpriseName", batchOpenAccountValidateDTO
					.getBatchOpenAccountDTOList().get(0) == null ? ""
					: batchOpenAccountValidateDTO.getBatchOpenAccountDTOList()
							.get(0).getEnterpriseName());
			sendMailParameter.put("registrantNum", batchOpenAccountValidateDTO
					.getBatchOpenAccountDTOList().size());
			sendMailParameter.put("content", "开户申请已完成，请审核！");
			sendEmailService.sendAsyncEmail(
					EmailTypeEnum.OPEN_ACCOUNT_AUDIT.getValue(),
					sendMailParameter);
		}
	}

	/**
	 * 文件数据保存到wj_channel_batch_open_account表中
	 */
	private void saveToBatchAccount(List<UserOpenAccountDTO> list) {
		// 根据批次号查询开户用户数据
		List<UserOpenAccountDTO> userOpenAccountDTOList = userMapper
				.queryBatchOpenAccountRequestByFileBatchNo(list.get(0)
						.getFileBatchNo());
		if (list != null && list.size() > 0) {
			// EnterpriseInfoDTO
			// enterpriseInfo=enterpriseInfoMapper.queryEnterpriseInfoDTOById(enterpriseNo);
			// //保存文件内容到wj_channel_batch_open_account
			String errMsg="";
			for (UserOpenAccountDTO userOpenAccountDTO : list) {
				if ((userOpenAccountDTOList == null | userOpenAccountDTOList
						.size() == 0)) {
					userOpenAccountDTO.setTradeType("gzqb");// 默认交易类型为"gzqb"
					// if(enterpriseInfo!=null){
					// userOpenAccountDTO.setEnterpriseName(enterpriseInfo.getEnterpriseName());
					// }
					userOpenAccountDTO.setBankCode(bankMap
							.get(userOpenAccountDTO.getBankName()));
					if(userMapper.insertWjChannelBatchOpenAccount(userOpenAccountDTO)<1)
					{
						errMsg=errMsg+"客户("+userOpenAccountDTO.getRealName()+")的开户数据新增失败;\n";
					}
				} else {
					if(StringUtils.isBlank(userOpenAccountDTO.getId()))
					{	
						errMsg=errMsg+"客户("+userOpenAccountDTO.getRealName()+")的开户数据更新失败,数据缺少id主键!\n";
					}
					userOpenAccountDTO.setBankCode(bankMap
							.get(userOpenAccountDTO.getBankName()));
					if(userMapper.updateWjChannelBatchOpenAccount(userOpenAccountDTO)<1)
					{
						errMsg=errMsg+"客户("+userOpenAccountDTO.getRealName()+")的开户数据的校验状态修改失败;\n";
					}
				}

			}
			if(!StringUtils.isBlank(errMsg))
			{
				throw new AppException(errMsg);
			}
		}
	}

	/**
	 * 获取银行信息
	 * 
	 * @return
	 */
	private Map<String, String> getBankInfo(Map<String, String> bankMap) {

		List<BankInfoDTO> banklist = userMapper.getBankInfo();
		if (banklist != null && banklist.size() > 0) {
			for (BankInfoDTO dto : banklist) {
				bankMap.put(dto.getBankName(), dto.getBankCode());
				bankMap.put(dto.getBankNameShow(), dto.getBankCode());
				bankMap.put(dto.getBankCode(), dto.getBankNameShow());
			}
		}
		return bankMap;
	}

	@Override
	public List<UserOpenAccountDTO> queryBatchOpenAccountSuccessUserByFileBatchNo(
			String fileBatchNo) {
		return userMapper.queryBatchOpenAccountSuccessUserByFileBatchNo(fileBatchNo);
	}

}
