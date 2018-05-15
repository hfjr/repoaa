package com.rqb.ips.depository.platform.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.faced.IpsOpenUserReturnService;
import com.rqb.ips.depository.platform.mapper.ipsOpenUserResultMapper;
import com.rqb.ips.depository.platform.menu.MBOrderInfoDTO;

@Service
public class ExcuteOpenUserReturnService implements IpsOpenUserReturnService {

	@Autowired
	private ipsOpenUserResultMapper ipsOpenUserResultMapper;

	/**
	 * 查询开户状态
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void queryOpenStatus(Map<String, Object> respResult) {
		try {
			if (respResult != null) {
				// 存数据历史
				String js = JSONUtils.obj2json(respResult);
				HashMap<Object, Object> map = new HashMap<>();
				map.put("json", js);
				ipsOpenUserResultMapper.insertInfo(map);
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}

		// 商户订单
		String merBillNo = (String) respResult.get("merBillNo");
		// 商户返回订单
		String ipsBillNo = (String) respResult.get("ipsBillNo");
		// 返回状态
		String status = (String) respResult.get("status");
		// 数据--db 先得到userid 根据merbillno 去查对应的id
		Map<String, String> user = ipsOpenUserResultMapper.queryUserId(merBillNo);
		String id = user.get("userId");
		if (id != null) {
			// 查询开户状态
			Map<String, String> isStatus = ipsOpenUserResultMapper.queryUserOpenStuats(id, merBillNo);
			String userStatus = isStatus.get("openStatus");
			// 如果还是审核中，去查这次的状态成功，失败都会去更新
			// 成功失败不做任何操作
			if(userStatus==null){
				throw new AppException("状态没查询到");
			}
			if (MBOrderInfoDTO.ORDER_STATUS_OPEN_ACCOUNT_CHECK.equals(userStatus)) {
				// 还在审核中也是跟新订单
				ipsOpenUserResultMapper.updateUserOrder(ipsBillNo, status, id, merBillNo);
			} else {
				// 成功或者失败
				BaseLogger.audit("结果返回成功");
			}

		} else {
			throw new AppException("商户订单号没查到");
		}

	}

	/**
	 * 开户
	 * 
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void openUser(Map<String, Object> respResult) {
		// TODO Auto-generated method stub
		// 数据--历史
		try {
			if (respResult != null) {
				String js = JSONUtils.obj2json(respResult);
				HashMap<Object, Object> map = new HashMap<>();
				map.put("json", js);
				ipsOpenUserResultMapper.insertInfo(map);
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}

		// 商户订单
		String merBillNo = (String) respResult.get("merBillNo");
		// ips订单号
		String ipsBillNo = (String) respResult.get("ipsBillNo");
		// ipsAccTno账号
		String ipsAcctNo = (String) respResult.get("ipsAcctNo");
		// 返回状态
		String status = (String) respResult.get("status");
		// 返回时间
		String times = (String) respResult.get("ipsDoTime");
		// 数据--db 先得到userid 根据merbillno 去查对应的id
		Map<String, String> user = ipsOpenUserResultMapper.queryUserId(merBillNo);
		String id = user.get("userId");
		if (id != null) {
			if ("0".equals(status)) {
				String fail = MBOrderInfoDTO.ORDER_STATUS_OPEN_ACCOUNT_FAIl;
				status = fail;
				ipsOpenUserResultMapper.updateUserOrder(ipsBillNo, status, id, merBillNo);
			}
			// 根据id,merbillno 跟新用户订单状态 和反回订单号
			if ("1".equals(status)) {
				String success=MBOrderInfoDTO.ORDER_STATUS_OPEN_ACCOUNT_CONFIRM;
				status=success;
				ipsOpenUserResultMapper.updateUserOrder(ipsBillNo, status, id, merBillNo);
				// 根据userid 去存ipsAcctNo
				status="1";
				ipsOpenUserResultMapper.insertUserId(id, ipsAcctNo, status);
			}if("2".equals(status)){
				String check=MBOrderInfoDTO.ORDER_STATUS_OPEN_ACCOUNT_CHECK;
				status=check;
				ipsOpenUserResultMapper.updateUserOrder(ipsBillNo, status, id, merBillNo);
			}
		} else {
			throw new AppException("商户订单号没查到");
		}
	}
}
