package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.util.DateUtil;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper.ICheckingAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckBillImplService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

public abstract class AbstractCheckBillServiceImpl implements ICheckBillImplService {
	
	public static final String DATE_FORMAT_yyyy_MM_dd = "yyyyMMdd";

	@Remote
	private ICommonAttachmentOperate commonAttachmentOperate;
	
	@Autowired
	private ICheckingAccountMapper checkingAccountMapper;

	@Autowired
	private ISequenceService sequenceService;

	@Remote
	private IB2CPayGatewayService b2CPayGatewayService;

	@Remote
	private ISendEmailService sendEmailService;
	
	
	/**
	 * 下载对账文件
	 */
	public abstract void downloadBill(String date) throws Exception ;

	/**
	 * 解析规则不同 处理插入数据库(重写)
	 */
	public abstract void dealIntoDB(byte[] file, String fileBusinessNo, String date) throws Exception;

	/**
	 * 下载对账
	 * 
	 * @param rechargeDTO
	 * @param platformID
	 * @return
	 * @throws Exception 
	 */
	protected void downloadBill(String date, int platformType,String bizType,String fileType) throws Exception {
		//1.下载对账文件
		BaseLogger.info(String.format("1.结账日期:[%s]下载账单文件;", date));
		MBRechargeDTO rechargeDTO = new MBRechargeDTO();
		rechargeDTO.setStartDate(date);
		rechargeDTO.setEndDate(date);
		
		byte[] file=null;
		try{
			file= b2CPayGatewayService.downloadCheckFile(rechargeDTO, platformType);
		}catch(Exception e){
			BaseLogger.error("下载对账文件异常,",e);
		}
	
		if (file == null) {
			throw new AppException("下载对账文件失败");
		}

		//2.文件名
		String fileName = CheckingAccountService.FILE_NAME_HEAD + date + fileType;
		BaseLogger.info(String.format("2.结账日期:[%s]保存对账文件到文件服务器;", date));
		
		String fileBusinessNo=null;
		try{
			
			fileBusinessNo = commonAttachmentOperate.saveAttachementAndReturnFileId(fileName,file,bizType);
		}catch(Exception e){
			BaseLogger.error("上传对账文件异常,",e);
		}
		
		if(fileBusinessNo==null){
			throw new AppException("上传对账文件失败");
		}
		
		//3.把对账单文件数据保存到数据库
		BaseLogger.info(String.format("3.结账日期:[%s]解析对账文件入库;", date));
		this.dealIntoDB(file,fileBusinessNo,date);
	}


	
	
	/**
	 * 是否已有对账单文件
	 * 
	 * @param date
	 * @param platformID
	 * @return
	 */
	protected boolean isHasBillFile(String date, int platformType) {
		boolean hasBill = false;

		WjCheckingAccountDTO checkingAccountDTO = new WjCheckingAccountDTO();
		checkingAccountDTO.setCheckingData(date);
		checkingAccountDTO.setTradePlatformType(Integer.toString(platformType));
		List<WjCheckingAccountDTO> dtoList = checkingAccountMapper.queryCheckingAccount(checkingAccountDTO);
		if (dtoList != null && dtoList.size() > 0) {
			hasBill = true;
		}
		return hasBill;
	}

	/**
	 * 获取对账编号
	 * @param sequenceName
	 * @param date
	 * @return
	 */
	protected String getCheckAccountNo(String sequenceName, String date) {
		return "CM" + date + sequenceService.getNextStringValue(sequenceName, 5);
	}

	/**
	 * 获取对账明细编号
	 * @param sequenceName
	 * @param date
	 * @return
	 */
	protected String getCheckAccountDetailNo(String sequenceName, String date) {
		return "CD" + date + sequenceService.getNextStringValue(sequenceName, 10);
	}
	
	
	
	/**
	 * 发送email
	 * @param msg
	 */
	protected void sendAsyncEmail(String msg){
		sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(msg));
	}
	
	
	/**
	 * 下载对账文件
	 */

	protected Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}
	
	//校验日期格式
	protected boolean verifyDateFormat(String format,String stringDate){
    	try {
	    	SimpleDateFormat f = new SimpleDateFormat(format);
	    	Date date = (Date) f.parseObject(stringDate);
	    	String tmp = f.format(date);
	    	return tmp.equals(stringDate);
    	} catch (Exception e) {
            return false;
        }
    }
}
