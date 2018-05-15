package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqb.server.service.impl;

import java.util.Map;


import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.order.dto.OrderLoanRepayDTO;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
@Service
public class MyBorrowDubboService implements IMyBorrowDubboService {

	@Override
	public Map<String, Object> borrowRecordDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> borrowRecordList(String arg0, String arg1,
			String arg2) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> borrowRecordList(String arg0, String arg1,
			String arg2, String arg3) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> canaBorrowDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> canaRepayDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> ltbBorrowDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> ltbRepayDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderLoanRepayDTO queryLatelyRepaymentInfo(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryYingztContractUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> writeBorrowDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> writeRepayDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
