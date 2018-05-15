package com.rqb.ips.depository.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rqb.ips.depository.platform.beans.InterfaceRecordJSONDTO;
import com.rqb.ips.depository.platform.faced.IPSInterfaceRecordService;
import com.rqb.ips.depository.platform.mapper.IPSInterfaceRecordMapper;
/**
 * ips接口调用记录
 * 
 *
 */
@Service
public class IPSInterfaceRecordServiceimpl implements IPSInterfaceRecordService {

	@Autowired
	private IPSInterfaceRecordMapper ipsInterfaceRecordMapper;
	/**
	 * 请求参数保存
	 * @param projectRegistration
	 */
	@Override
	public void SendJsonRecord(InterfaceRecordJSONDTO interfaceRecordJSONDTO) {

		ipsInterfaceRecordMapper.SendJsonRecord(interfaceRecordJSONDTO);		
	}
	
	/**
	 * 返回参数保存
	 * @param iPSResponse
	 */
	
	
	@Override
	public void ReturnJsonRecord(InterfaceRecordJSONDTO interfaceRecordJSONDTO) {

		ipsInterfaceRecordMapper.ReturnJsonRecord(interfaceRecordJSONDTO);		
	}

}
