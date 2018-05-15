package com.rqb.ips.depository.platform.mapper;

import com.fab.server.annotation.Mapper;
import com.rqb.ips.depository.platform.beans.InterfaceRecordJSONDTO;
/**
 * ips接口调用记录
 * @author Administrator
 *
 */
@Mapper
public interface IPSInterfaceRecordMapper {
	/**
	 * 请求参数保存
	 * @param Sendjson sendjson
	 */
	public void SendJsonRecord(InterfaceRecordJSONDTO interfaceRecordJSONDTO);
	/**
	 * 返回参数保存
	 * @param iPSResponse
	 */
	public void ReturnJsonRecord(InterfaceRecordJSONDTO interfaceRecordJSONDTO);
	
	
	
	
}
