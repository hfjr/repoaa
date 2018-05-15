package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.PaymentCallBackParamBO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.SingleStatusResult;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IBatchTransStatusService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper.IMBTradeRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;

/**
 * 批量更新交易状态和业务状态(定时查询交易流水表,查询特定时间内的,处理交易状态在进行中的交易)
 * 
 * @author jiangkaijun
 * 
 */
@Service("batchTransStatusService")
public class BatchTransStatusServiceImpl implements IBatchTransStatusService {

	@Autowired
	private IMBUserAccountService mBUserAccountService;

	@Autowired
	private IMBTradeRecordMapper tradeRecordMapper;

	@Override
	public List<SingleStatusResult> batchChangeTransStatus(int minute) throws Exception {
		List<PaymentCallBackParamBO> tradeList = tradeRecordMapper.queryForBatchChangeStatus(minute);
		List<SingleStatusResult> result = Lists.newArrayList();
		if (tradeList != null) {
			for (int i = 0; i < tradeList.size(); i++) {
				result.add(mBUserAccountService.modifyTransStatus(tradeList.get(i).getTradeNo(), tradeList.get(i).getOrderNo()));
			}
		}
		return result;
	}

}
