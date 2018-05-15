package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.util.DateUtil;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper.ICheckingAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckBillImplService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service("baofooCheckBillService")
public class BaofooCheckBillServiceImpl extends AbstractCheckBillServiceImpl implements ICheckBillImplService{

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
	
	public static  final String FILE_NAME_ZIP = ".zip";//文件名后缀
	

	/**
	 * 下载对账文件
	 */
	
	@Transactional
	public void downloadBill(String date) throws Exception {
		BaseLogger.audit(String.format("宝付,日期[%s]下载解析对账文件开始;", date));
		// 判断对账文件是否已存在
		if (!isHasBillFile(date, Integer.parseInt(CheckingAccountService.PAY_TYPE_BAOFOO_PAY))) {
			try {
				downloadBill(date, Integer.valueOf(CheckingAccountService.PAY_TYPE_BAOFOO_PAY),CheckingAccountService.FILE_TYPE_BAOFOO_PAY,FILE_NAME_ZIP);
			} catch (AppException e) {
				BaseLogger.error(String.format("宝付,下载解析对账文件,业务异常,日期[%s];", date), e);
				sendAsyncEmail(String.format("宝付,下载解析对账文件,业务异常,日期[%s],原因:" + e.getMessage(), date));
				throw e;
			} catch (Exception e) {
				BaseLogger.error(String.format("宝付,下载解析对账文件,系统异常,日期[%s];", date), e);
				sendAsyncEmail(String.format("宝付,下载解析对账文件,系统异常,日期[%s],原因:" + e.getMessage(), date));
				throw e;
			}
		}
		BaseLogger.audit(String.format("宝付,日期[%s]下载解析对账文件结束;", date));
	}

	/**
	 * 解析规则不同 处理插入数据库(重写)
	 */
	@SuppressWarnings("unused")
	public void dealIntoDB(byte[] file, String fileBusinessNo, String date) throws Exception {
		BaseLogger.audit(String.format("宝付,日期[%s],解析,保存对账文件数据到数据库开始;", date));
		ZipInputStream zins = null;
		ByteArrayInputStream inStream = null;
		ByteArrayInputStream inStream2 = null;
		ZipEntry ze = null;
		BufferedReader reader = null;
		InputStreamReader inputReader=null;
		ByteArrayOutputStream outStream=null;
		String tempString = null;

		BigDecimal bigDecimal=new BigDecimal("100");
		
		//数据载体
		WjCheckingAccountDTO wjCheckingAccountDTO = new WjCheckingAccountDTO();
		List<WjCheckingAccountDetailDTO> list = new ArrayList<WjCheckingAccountDetailDTO>();
		
		try {
			inStream = new ByteArrayInputStream(file);
			zins = new ZipInputStream(inStream);
			// 解压
			if ((ze = zins.getNextEntry()) != null) {
				
				outStream=new ByteArrayOutputStream();
				byte[] ch=new byte[1024];
				int i;
				 while ((i = zins.read(ch)) != -1){
					 outStream.write(ch, 0, i);  
				 }

				inStream2 = new ByteArrayInputStream(outStream.toByteArray());
				inputReader = new InputStreamReader(inStream2, "utf-8");
				reader = new BufferedReader(inputReader);
				int line=1;
				int firstLength = 0;
				while ((tempString = reader.readLine()) != null) {
					BaseLogger.info("解析数据,组装对账数据,tempString="+tempString);
					//解析数据,组装对账数据
					if(line==1){
						firstLength = tempString.split("\\|").length;
					}
					else if(line==2){//总数据
						String[] tmpStr = tempString.split("\\|");
						
						wjCheckingAccountDTO.setFileBusinessNo(fileBusinessNo);//文件NO
						wjCheckingAccountDTO.setCheckingData(date);//对账日期
						wjCheckingAccountDTO.setTradePlatformType(CheckingAccountService.PAY_TYPE_BAOFOO_PAY);//宝付
						String checkNo = this.getCheckAccountNo(ISequenceService.SEQ_NAME_CHECK_ACCOUNT_SEQ, date);
						wjCheckingAccountDTO.setCheckNo(checkNo);//对账NO
						
						if(firstLength==tmpStr.length){
							try {
								wjCheckingAccountDTO.setTotalAmount(bigDecimal.multiply(new BigDecimal(tmpStr[5])).toPlainString());//对账总金额，以分为单位
								wjCheckingAccountDTO.setTotalNum(tmpStr[4]);//对账总数
								wjCheckingAccountDTO.setTotalCounterFee(bigDecimal.multiply(new BigDecimal(tmpStr[6])).toPlainString());//总手续费
							} catch (Exception e) {
								BaseLogger.error(String.format("宝付,文件NO[%s],账单文件解析数据异常！", fileBusinessNo), e);
							}
						}else{
							wjCheckingAccountDTO.setTotalAmount("0");//对账总金额，以分为单位
							wjCheckingAccountDTO.setTotalNum("0");//对账总数
							wjCheckingAccountDTO.setTotalCounterFee("0");//总手续费
						}
					}else if(line>=4){//详细数据
						String[] tmpStr = tempString.split("\\|");
						WjCheckingAccountDetailDTO accountDetail=new WjCheckingAccountDetailDTO();
						accountDetail.setCheckNo(wjCheckingAccountDTO.getCheckNo());
						accountDetail.setCheckDetailNo(this.getCheckAccountDetailNo(
								ISequenceService.SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ,date));
						accountDetail.setMerId(tmpStr[0]);//商户号
						accountDetail.setCheckMerDate(date);//对账日期
						
						//转日期格式
						accountDetail.setCheckTransTime(DateUtil.getDateFormatStr(DateUtil.getDate(tmpStr[6]), "yyyyMMdd"));//交易成功时间
						accountDetail.setOrderId(tmpStr[5]);//宝付订单号
						accountDetail.setTradeNo(tmpStr[10]);//交易号
						accountDetail.setCheckTradeAmount(bigDecimal.multiply(new BigDecimal(tmpStr[8])).toPlainString());//交易金额
						accountDetail.setCounterFee(bigDecimal.multiply(new BigDecimal(tmpStr[9])).toPlainString());
						accountDetail.setCheckTransStatus("1");//交易状态:成功
						//accountDetail.setGoodsId(str[12]);
						//accountDetail.setCheckProductId(str[13]);
						
						list.add(accountDetail);
					}
				line++;	
				}
			}

			//插入对账信息
			checkingAccountMapper.insertCheckingAccount(wjCheckingAccountDTO);
			// 插入对账明细
			if(list!=null&&list.size()>0){
				checkingAccountMapper.insertCheckingAccountDetailList(list);
			}
		} catch (AppException e) {
			BaseLogger.error(String.format("宝付,文件NO[%s],账单文件保存到数据库业务异常！", fileBusinessNo), e);
			throw e;
		} catch (Exception e) {
			BaseLogger.error(String.format("宝付,文件NO[%s],账单文件保存到数据库系统异常！", fileBusinessNo), e);
			throw e;
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if(inStream2 != null){
					inStream2.close();
				}
				
				if(inputReader!=null){
					inputReader.close();
				}
				
				if(reader!=null){
					reader.close();
				}
				
				if(zins!=null){
					zins.close();
				}
			} catch (IOException e) {
				BaseLogger.error(String.format("宝付,日期[%s],关闭流异常;", date), e);
			}

			BaseLogger.audit(String.format("宝付,日期[%s],保存对账文件数据到数据库结束;", date));
		}

	}

//	/**
//	 * 下载对账
//	 * 
//	 * @param rechargeDTO
//	 * @param platformID
//	 * @return
//	 * @throws Exception 
//	 */
//	public void downloadBill(String date, int platformType) throws Exception {
//		MBRechargeDTO rechargeDTO = new MBRechargeDTO();
//		rechargeDTO.setStartDate(date);
//		rechargeDTO.setEndDate(date);
//		//1.下载对账文件
//		BaseLogger.info(String.format("1.结账日期:[%s]下载账单文件;", date));
//		
//		
//		byte[] file=null;
//		try{
//			file= b2CPayGatewayService.downloadCheckFile(rechargeDTO, platformType);
//		}catch(Exception e){
//			BaseLogger.error("下载对账文件异常,",e);
//		}
//	
//		if (file == null) {
//			throw new AppException("下载对账文件失败");
//		}
//
//		//2.文件名
//		String fileName = CheckingAccountService.FILE_NAME_HEAD + date + FILE_NAME_ZIP;
//		BaseLogger.info(String.format("2.结账日期:[%s]保存对账文件到文件服务器;", date));
//		
//		String fileBusinessNo=null;
//		try{
//			
//			fileBusinessNo = commonAttachmentOperate.saveAttachementAndReturnFileId(fileName,file,
//					CheckingAccountService.FILE_TYPE_BAOFOO_PAY);
//		}catch(Exception e){
//			BaseLogger.error("上传对账文件异常,",e);
//		}
//		
//		if(fileBusinessNo==null){
//			throw new AppException("上传对账文件失败");
//		}
//		
//		//3.把对账单文件数据保存到数据库
//		BaseLogger.info(String.format("3.结账日期:[%s]解析对账文件入库;", date));
//		dealIntoDB(file,fileBusinessNo,date);
//	}
//
//
//	
//	
//	/**
//	 * 是否已有对账单文件
//	 * 
//	 * @param date
//	 * @param platformID
//	 * @return
//	 */
//	public boolean isHasBillFile(String date, int platformType) {
//		boolean hasBill = false;
//
//		WjCheckingAccountDTO checkingAccountDTO = new WjCheckingAccountDTO();
//		checkingAccountDTO.setCheckingData(date);
//		checkingAccountDTO.setTradePlatformType(Integer.toString(platformType));
//		List<WjCheckingAccountDTO> dtoList = checkingAccountMapper.queryCheckingAccount(checkingAccountDTO);
//		if (dtoList != null && dtoList.size() > 0) {
//			hasBill = true;
//		}
//		return hasBill;
//	}
//
//	/**
//	 * 获取对账编号
//	 * @param sequenceName
//	 * @param date
//	 * @return
//	 */
//	public String getCheckAccountNo(String sequenceName, String date) {
//		return "CM" + date + sequenceService.getNextStringValue(sequenceName, 5);
//	}
//
//	/**
//	 * 获取对账明细编号
//	 * @param sequenceName
//	 * @param date
//	 * @return
//	 */
//	public String getCheckAccountDetailNo(String sequenceName, String date) {
//		return "CD" + date + sequenceService.getNextStringValue(sequenceName, 10);
//	}
//	
//	
//	
//	/**
//	 * 发送email
//	 * @param msg
//	 */
//	public void sendAsyncEmail(String msg){
//		sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(msg));
//	}
//	
//	
//	/**
//	 * 下载对账文件
//	 */
//
//	public Map<String, Object> pageEmailMap(String content) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("content", content);
//		return map;
//	}
}
