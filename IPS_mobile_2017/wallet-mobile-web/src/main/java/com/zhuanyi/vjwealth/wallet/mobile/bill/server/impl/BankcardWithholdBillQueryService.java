package com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl;

import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.AbstractBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper.IBillListQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:工资定存代扣
 * @author:cuidezhong
 * @date: 2016-09-24 11:08
 */
@Service("bankcardWithholdBillQueryService")
public class BankcardWithholdBillQueryService extends AbstractBillTemplateService implements IBillTemplateService{

	@Autowired
	private IBillListQueryMapper billListQueryMapper;

	@Override
	public Map<String, Object> getBillList(String userId, String page) {
		return super.getBillPageList(userId, page);
	}

	public List<BillListQueryDTO> getListRows(String userId, int pageIndex) {
		return billListQueryMapper.getBankcardWithholdBillListByUserIdAndType(userId, pageIndex);
	}

	public Object getBillDetail(String orderId, String orderType) {
		return null;
	}

}
