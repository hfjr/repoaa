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

@Service("jingdongCheckBillService")
public class JingdongCheckBillServiceImpl extends AbstractCheckBillServiceImpl implements ICheckBillImplService{

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
		BaseLogger.audit(String.format("京东,日期[%s]下载解析对账文件开始;", date));
		//判断日期格式
		if(!this.verifyDateFormat(AbstractCheckBillServiceImpl.DATE_FORMAT_yyyy_MM_dd,date)){
			BaseLogger.error(String.format("京东充值日期[%s]格式不正确", date,"yyyyMMdd"));
			throw new AppException(String.format("日期[%s]格式不正确", date,"yyyyMMdd"));
		}
		// 判断对账文件是否已存在
		if (!super.isHasBillFile(date, Integer.parseInt(CheckingAccountService.PAY_TYPE_JINGDONG_PAY))) {
			try {
				String dateAddOne = DateUtil.getDateStr(DateUtil.addDay(DateUtil.getDate(date,"yyyyMMdd"),1));
				super.downloadBill(dateAddOne, Integer.valueOf(CheckingAccountService.PAY_TYPE_JINGDONG_PAY),CheckingAccountService.FILE_TYPE_JINGDONG_PAY,FILE_NAME_ZIP);
			} catch (AppException e) {
				BaseLogger.error(String.format("京东,下载解析对账文件,业务异常,日期[%s];", date), e);
				super.sendAsyncEmail(String.format("京东,下载解析对账文件,业务异常,日期[%s],原因:" + e.getMessage(), date));
				throw e;
			} catch (Exception e) {
				BaseLogger.error(String.format("京东,下载解析对账文件,系统异常,日期[%s];", date), e);
				super.sendAsyncEmail(String.format("京东,下载解析对账文件,系统异常,日期[%s],原因:" + e.getMessage(), date));
				throw e;
			}
		}
		BaseLogger.audit(String.format("京东,日期[%s]下载解析对账文件结束;", date));
	}


	@Override
	public void dealIntoDB(byte[] file, String fileBusinessNo, String date)
			throws Exception {
		BaseLogger.audit(String.format("京东解析,保存对账文件数据到数据库开始;"));
		date = DateUtil.getDateStr(DateUtil.addDay(DateUtil.getDate(date,"yyyyMMdd"),-1));
		BaseLogger.audit(String.format("京东,日期[%s],解析,保存对账文件数据到数据库开始;", date));
		ZipInputStream zins = null;
		ByteArrayInputStream inStream = null;
		ByteArrayInputStream inStream2 = null;
		ZipEntry ze = null;
		BufferedReader reader = null;
		InputStreamReader inputReader=null;
		ByteArrayOutputStream outStream=null;
		String tempString = null;

		BigDecimal bigDecimal=new BigDecimal("100");
		BigDecimal amountAll = new BigDecimal(0);
		
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
				inputReader = new InputStreamReader(inStream2, "GBK");
				reader = new BufferedReader(inputReader);
				int line=1;
				
				wjCheckingAccountDTO.setFileBusinessNo(fileBusinessNo);//文件NO
				wjCheckingAccountDTO.setCheckingData(date);//对账日期
				wjCheckingAccountDTO.setTradePlatformType(CheckingAccountService.PAY_TYPE_JINGDONG_PAY);//京东
				String checkNo = super.getCheckAccountNo(ISequenceService.SEQ_NAME_CHECK_ACCOUNT_SEQ, date);
				wjCheckingAccountDTO.setCheckNo(checkNo);//对账NO
				while ((tempString = reader.readLine()) != null) {
					//解析数据,组装对账数据
					if(line>1){//详细数据
						String[] tmpStr = tempString.split(",");
						WjCheckingAccountDetailDTO accountDetail=new WjCheckingAccountDetailDTO();
						accountDetail.setCheckNo(wjCheckingAccountDTO.getCheckNo());
						accountDetail.setCheckDetailNo(this.getCheckAccountDetailNo(
								ISequenceService.SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ,date));
						
						accountDetail.setCheckMerDate(tmpStr[8]);//交易时间
						accountDetail.setCheckTransTime(tmpStr[9]);//处理时间
						BigDecimal amount = bigDecimal.multiply(new BigDecimal(tmpStr[2]));
						accountDetail.setCheckTradeAmount(amount.toPlainString());//交易金额
						
						String tradeNo = tmpStr[0];
						String fee = tmpStr[11];
						if(tradeNo.indexOf("=\"")!=-1){
							tradeNo = tradeNo.substring(2, tradeNo.length()-1);
						}
						if(fee.length()>1&&fee.charAt(0)=='\"'){
							fee = fee.substring(1, fee.length()-1);
						}
						accountDetail.setCounterFee(bigDecimal.multiply(new BigDecimal(fee)).toPlainString());//手续费
						accountDetail.setOrderId(tradeNo);
						accountDetail.setCheckTransStatus("1");//交易状态:成功
						//业务类型tmpStr[3],订单类型tmpStr[4]
						accountDetail.setRemark1(tmpStr[3]);
						accountDetail.setRemark2(tmpStr[4]);
						
						amountAll = amountAll.add(amount);
						list.add(accountDetail);
					}
					line++;	
				}
				wjCheckingAccountDTO.setTotalAmount(amountAll.toPlainString());//对账总金额，以分为单位
				wjCheckingAccountDTO.setTotalNum(String.valueOf(line!=1?line-2:0));//对账总数
				
				//插入对账信息
				checkingAccountMapper.insertCheckingAccount(wjCheckingAccountDTO);
				// 插入对账明细
				if(list!=null&&list.size()>0){
					checkingAccountMapper.insertCheckingAccountDetailList(list);
				}
			}
		} catch (AppException e) {
			BaseLogger.error(String.format("京东,文件NO[%s],账单文件保存到数据库业务异常！", fileBusinessNo), e);
			throw e;
		} catch (Exception e) {
			BaseLogger.error(String.format("京东,文件NO[%s],账单文件保存到数据库系统异常！", fileBusinessNo), e);
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
				BaseLogger.error(String.format("京东,日期[%s],关闭流异常;", date), e);
			}

			BaseLogger.audit(String.format("京东,日期[%s],保存对账文件数据到数据库结束;", date));
		}

	}

	
}
