package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.SpringContextUtils;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.IWithholdExcuteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.IWithholdService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

/**
 * 调用具体业务实线层
 * 
 * @author jiangkaijun
 * 
 */

@Service("withholdExcuteService")
public class WithholdExcuteService implements IWithholdExcuteService{

	@Remote
	private ISendEmailService sendEmailService;

	public Object excutePreBizOperateParameter(String bizType,Object parameter) {
		for (String bean : getWithholdBeans()) {
			IWithholdService withholdBean = this.getWithholdBean(bean);
			if (withholdBean.isSupportOrderType(bizType)) {
				return withholdBean.preBizOperateParameter(parameter);
			}
		}
		return null;
	}

	public CallbackBizResultDTO excuteCallBackBizOperateSuccess(String userId,String bizNo, String orderType, BigDecimal amount) {
		try {
			BaseLogger.audit(String.format("充值/代扣-通知回调业务参数:[%s]", "userId="+userId+",bizNo="+bizNo+",orderType="+orderType+",amount="+amount));
			
			for (String bean : getWithholdBeans()) {
				BaseLogger.audit("excuteCallBackBizOperateSuccess-->bean调用"+bean);
				IWithholdService withholdBean = this.getWithholdBean(bean);
				if (withholdBean.isSupportOrderType(orderType)) {
					return withholdBean.callBackBizOperateSuccess(userId,bizNo, amount);
				}
			}
		} catch (Exception ex) {
			String err = ex.getMessage();
			BaseLogger.error("充值/代扣-excuteCallBackBizOperateSuccess通知回调业务异常:",ex);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("交易流水成功,代扣回调异常,订单号orderId[" + bizNo + "],错误信息[" + ex.getMessage() + "];"));
			return CallbackBizResultDTO.returnCallBackFail(String.format("调用支付回调函数异常[%s]", err.length() > 6000 ? err.substring(0, 6000) : err));
		}
		sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("交易流水成功场景,回调业务异常,订单号orderId[" + bizNo + "],错误信息[没找到匹配的业务回调实现!];"));
		return CallbackBizResultDTO.returnCallBackFail("业务类型不存在");
	}

	/**
	 * 业务操作失败
	 * 
	 * @param orderType
	 * @param bizNo
	 */
	public CallbackBizResultDTO excuteCallBackBizOperateFail(String userId,String bizNo, String orderType, BigDecimal amount) {
		try {
			BaseLogger.audit(String.format("充值/代扣-通知回调业务参数:[%s]", "userId="+userId+",bizNo="+bizNo+",orderType="+orderType+",amount="+amount));
			for (String bean : getWithholdBeans()) {
				BaseLogger.audit("excuteCallBackBizOperateFail-->bean调用"+bean);
				IWithholdService withholdBean = this.getWithholdBean(bean);
				if (withholdBean.isSupportOrderType(orderType)) {
					return withholdBean.callBackBizOperateFail(userId,bizNo, amount);
				}
			}
		} catch (Exception ex) {
			String err = ex.getStackTrace().toString();
			// 统一发送邮件信息
			BaseLogger.error("充值/代扣-excuteCallBackBizOperateFail通知回调业务异常:"+ ex.getStackTrace());
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("充值/代扣-通知回调业务异常,订单号orderId[" + bizNo + "],错误信息[" + ex.getMessage() + "];"));
			return CallbackBizResultDTO.returnCallBackFail(String.format("充值/代扣-通知回调业务函数异常[%s]", err.length() > 6000 ? err.substring(0, 6000) : err));
		}
		sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("交易流水失败场景,回调业务异常,订单号orderId[" + bizNo + "],错误信息[没找到匹配的业务回调实现!];"));
		return CallbackBizResultDTO.returnCallBackFail("业务类型不存在");
	}

	public void excuteCallBackProcess(String userId,String orderType, String bizNo) {
		for (String bean : getWithholdBeans()) {
			IWithholdService withholdBean = this.getWithholdBean(bean);
			if (withholdBean.isSupportOrderType(orderType)) {
				withholdBean.processOperator(userId,bizNo);
			}
		}
	}

	public TradeSearchResultDTO excuteQueryBizOperateResult(String userId,String orderType, String bizNo) {
		for (String bean : getWithholdBeans()) {
			IWithholdService withholdBean = this.getWithholdBean(bean);
			if (withholdBean.isSupportOrderType(orderType)) {
				return withholdBean.queryBizOperateResult(userId,bizNo);
			}
		}
		return null;
	}

	private IWithholdService getWithholdBean(String bean) {
		return (IWithholdService) SpringContextUtils.getContext().getBean(bean);
	}

	private String[] getWithholdBeans() {

		return SpringContextUtils.getContext().getBeanNamesForType(IWithholdService.class);
	}

	private Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}

}
