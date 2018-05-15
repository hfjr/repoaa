package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.PayResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountManagerService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service("mbUserAccountManagerService") 
public class MBUserAccountManagerServiceImpl implements IMBUserAccountManagerService {

	@Remote
	private IB2CPayGatewayService b2CPayGatewayService;

	@Autowired
	private IMBUserAccountMapper userAccountMapper;

	@Remote
	ISendEmailService sendEmailService;
	
	private static final String RECHARGE_INTERFACE_RESULT_SUCCESS = "0000";//充值接口返回调用成功

	@Override
	@Transactional
	public String withhold(MBRechargeDTO rechargeDTO) {
		
		int lockCount = userAccountMapper.findCountWithUserDrawLock(rechargeDTO.getUserId());
		// 大于0说明已经上锁
		if (lockCount > 0) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();

		try {
			BeanUtils.copyProperties(paramDTO, rechargeDTO);
			PayResultDTO payResultDTO = b2CPayGatewayService.noSendMessagePayment(paramDTO);
			if (!payResultDTO.getResult()) {
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}

			if (payResultDTO.getResult() && RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				// 充值成功后逻辑
				BigDecimal amount = new BigDecimal(rechargeDTO.getAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
				userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMaToDispose(rechargeDTO.getUserId(), rechargeDTO.getOrderNo(), amount, rechargeDTO.getCardId()));
			} else {
				BaseLogger.error("代扣出错：" + payResultDTO.getResultMessage());
				throw new AppException(payResultDTO.getResultMessage());
			}
			return rechargeDTO.getOrderNo();
		}catch (AppException e) {
			BaseLogger.error("代扣业务异常",e);
			throw new AppException("代扣业务异常,"+e.getMessage());
		} catch (Exception e) {
			BaseLogger.error("代扣系统异常",e);
			throw new AppException("代扣系统异常,"+e.getMessage());
		}

	}

}
