package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.WjEbatongTradeHistoryDTO;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountErrorDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.integration.mapper.ICheckingAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckingAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service("checkingAccountService")
public class CheckingAccountService implements ICheckingAccountService {
	public static  final String FILE_START_FLAG = "TRADEDETAIL-START";//对账文件开始标记
	public static  final String FILE_END_FLAG = "TRADEDETAIL-END";//对账文件结束标记
	
	public static  final String PAY_TYPE = "10";//充值类型:联动优势
	public static  final String PAY_TYPE_YEE_PAY = "20";//充值类型:易宝充值
	public static  final String PAY_TYPE_BAOFOO_PAY = "30";//充值类型:宝付充值
	public static  final String PAY_TYPE_JINGDONG_PAY = "40";//充值类型:京东充值
	public static  final String PAY_TYPE_JD_PAY_WITHHOLD = "60";//充值类型:京东支付代扣
	public static  final String FILE_TYPE = "union_mobile_checking_file";//文件类型:联动优势对账文件
	public static  final String FILE_TYPE_YEE_PAY = "yee_pay_checking_file";//文件类型:联动优势对账文件
	public static  final String FILE_TYPE_BAOFOO_PAY="baofoo_pay_checking_file";
	public static  final String FILE_TYPE_JINGDONG_PAY="jingdong_pay_checking_file";
	public static  final String FILE_TYPE_JD_PAY_WITHHOLD = "jd_pay_withhold_checking_file";
	public static  final String FILE_NAME_HEAD = "对账文件_";//文件名前缀
	public static  final String FILE_NAME_TAIL = ".txt";//文件名后缀
	public static int PAGE_SIZE = 1000;//分批数量
	
	private static final String RETURN_SUCESS="0000";
	
	@Autowired
	private ICheckingAccountMapper checkingAccountMapper;
	
//	@Remote
//	private IB2CGetawayImplService unionMobileB2CService;
	
	@Remote
	private ICommonAttachmentOperate commonAttachmentOperate;
	
	@Remote
	private ISendEmailService sendEmailService;
	
	@Autowired
	private ISequenceService sequenceService;
	
	@Remote
	private IB2CPayGatewayService b2CPayGatewayService;
	/**
	 * 比对充值账单数据
	 */
	@Override
	public void checkingAccount(String checkingDate) {
		//判断日期格式
		if(!this.verifyDateFormat("yyyyMMdd",checkingDate)){
			BaseLogger.error(String.format("日期[%s]格式不正确[%s]", checkingDate,"yyyyMMdd"));
			throw new AppException(String.format("日期[%s]格式不正确[%s]", checkingDate,"yyyyMMdd"));
		}
		Date startDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(startDate);
		
		//比对出错记录,需发送异常邮件的内容信息
		List<String> errorMessageList = new ArrayList<String>();
		
		//比对账单数据(如果需要未对账的文件数据都需要比对,需要作修改)
		try {
			//多个充值渠道,一天会有多条主数据
			WjCheckingAccountDTO checkingAccountDTO = new WjCheckingAccountDTO();
			checkingAccountDTO.setCheckingData(checkingDate);
			List<WjCheckingAccountDTO> dtoList = checkingAccountMapper.queryCheckingAccount(checkingAccountDTO);
			
			if(dtoList==null || dtoList.size()<1){
				BaseLogger.error(String.format("日期:[%s],账单数据不存在!",checkingDate));
				return;
			}
			for(WjCheckingAccountDTO wjCheckingAccountDTO:dtoList){
				boolean result = true;//最后结果
				//存在此对账主记录已对账过,且结果为失败
				if("20".equals(wjCheckingAccountDTO.getResult())){
					result = false;
				}
				//查询昨天未对账的账单明细总数,分批处理用
				int sum = checkingAccountMapper.queryCheckingAccountDetailSum(wjCheckingAccountDTO.getCheckNo());
				
				for(int i=0;sum/PAGE_SIZE>i-1&&sum>0;i++){
					//1.查询昨天未对账的账单明细
					WjCheckingAccountDetailDTO wjCheckingAccountDetailDTO = new WjCheckingAccountDetailDTO();
					wjCheckingAccountDetailDTO.setCheckNo(wjCheckingAccountDTO.getCheckNo());
					wjCheckingAccountDetailDTO.setContrastFlag("N");//未对账数据
					
					wjCheckingAccountDetailDTO.setPageSize(PAGE_SIZE);//设置分批数量
					List<WjCheckingAccountDetailDTO> checkList = checkingAccountMapper.queryCheckingAccountDetailList(wjCheckingAccountDetailDTO);
					//与充值数据作对比
					if(checkList!=null)
						result = this.checkFileDataList(checkList,result,wjCheckingAccountDTO,errorMessageList);
				}
				
				//更新主账单信息
				updateCheckingAccount(startDate,wjCheckingAccountDTO.getId(),result);
			}
			
			//1个对账文件内可能包含1-2个自然交易日期的交易记录
			//查询不是当天和前一天,没对账的数据
			//只过滤当天的数据,如果有昨天数据,未正常在对账文件里,也增加异常,人工再核实
			//判断是提前一天还是提前两天对账,过滤昨天的数据要不要对账
			String yesterday = null;
			Long todayTime = sdf.parse(today).getTime();
			Long checkTime = sdf.parse(checkingDate).getTime();
			if(todayTime == (checkTime + 1000*60*60*24*2)){
				//对账时间比当前时间提前两天
				yesterday = sdf.format(new Date(todayTime - 1000*60*60*24));
			}
			compCheckFileEmptyData(today,yesterday,checkingDate,errorMessageList);
			
		} catch (Exception e) {
			BaseLogger.error(String.format("日期:[%s],账单数据比对出错!",checkingDate),e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("日期:[%s],账单数据比对出错!",checkingDate)));
			throw new AppException(String.format("日期:[%s],账单数据比对出错!",checkingDate));
		} finally{
			if(errorMessageList!=null&&errorMessageList.size()>0){
				StringBuffer emailMessage = new StringBuffer();
				String xxx = "<br>";
				for(String message:errorMessageList){
					emailMessage.append(xxx);
					emailMessage.append(message);
				}
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_FIANCE_HIGH", pageEmailMap(emailMessage.toString()));
			}
		}
	}
	
	//对账文件数据与充值数据作对比
	private boolean checkFileDataList(List<WjCheckingAccountDetailDTO> checkList,
						boolean res,WjCheckingAccountDTO wjCheckingAccountDTO,List<String> errorMessageList){
		boolean flag = true ;
		boolean result = res;
		//与充值数据作对比
		for(WjCheckingAccountDetailDTO dto:checkList){
			try {
				flag = this.checkFileData(dto,wjCheckingAccountDTO,errorMessageList);
			} catch (Exception e) {
				// 其中一条记录出错,不影响其他记录执行
				BaseLogger.error(String.format("orderNo:[%s],账单数据比对出错!",dto.getOrderId()),e);
				result = false;
//				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("orderNo:[%s],账单数据比对出错!",dto.getOrderId())));
				
				errorMessageList.add(String.format("orderNo:[%s],账单数据比对出错!",dto.getOrderId()));
			}
			if(!flag)//只要有一次失败,就是异常
				result = false;
		}
		return result;
	}
	
	//单条比对数据
	private boolean checkFileData(WjCheckingAccountDetailDTO dto,WjCheckingAccountDTO wjCheckingAccountDTO,List<String> errorMessageList){
			if(StringUtils.isBlank(dto.getOrderId())){
				//对账文件有,我方无数据,插异常表
				WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = this.setWeNoDate(dto,wjCheckingAccountDTO,errorMessageList);
				checkingAccountMapper.insertCheckingAccountError(wjCheckingAccountErrorDTO);
				//修改对账明细表
				checkingAccountMapper.updateCheckingAccountDetailWithContrast(dto.getId());
				
				return false;
			}
			
			return toCheckOrderData(dto,wjCheckingAccountDTO,errorMessageList);
	}
	
	//核实最终订单是否正常,金额是否相同
	private boolean toCheckOrderData(WjCheckingAccountDetailDTO dto,WjCheckingAccountDTO wjCheckingAccountDTO,List<String> errorMessageList){
		//比对订单是否正常,金额是否相同
		String price = checkingAccountMapper.queryPriceInOrderByOrderNo(dto.getOrderId());
		if(StringUtils.isBlank(price)){
			//查历史记录
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("outTradeNo", dto.getOrderId());
			List<WjEbatongTradeHistoryDTO> tradeHis = checkingAccountMapper.queryTradeHistoryListToCheck(map);
			if(tradeHis==null||tradeHis.size()<=0){//有多条数据情况,逻辑上不可能
				//对账文件有,我方无数据,插异常表
				WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = this.setWeNoDate(dto,wjCheckingAccountDTO,errorMessageList);
				checkingAccountMapper.insertCheckingAccountError(wjCheckingAccountErrorDTO);
				//修改对账明细表
				checkingAccountMapper.updateCheckingAccountDetailWithContrast(dto.getId());
				
				return false;
			}
			WjEbatongTradeHistoryDTO tradeHistoryDTO = tradeHis.get(0);
			
			
			//订单不存在,插异常表
			WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = this.setCheckingException(dto, tradeHistoryDTO,"订单不存在,或订单中price不存在",errorMessageList);
			checkingAccountMapper.insertCheckingAccountError(wjCheckingAccountErrorDTO);
			//修改对账明细表
			checkingAccountMapper.updateCheckingAccountDetailWithContrast(dto.getId());
			
			return false;
		}
		
		
		BigDecimal amountOrder = new BigDecimal(0);
		BigDecimal amount = new BigDecimal(0);
		try {
			amountOrder = new BigDecimal( price).multiply(new BigDecimal("100"));
			amount = new BigDecimal(dto.getCheckTradeAmount());
		} catch (Exception e) {
			BaseLogger.error(String.format("orderNo:[%s],取订单金额出错,或不存在该订单!",dto.getOrderId()),e);
//			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("orderNo:[%s],取订单金额出错,或不存在该订单!",dto.getOrderId())));
			
			errorMessageList.add(String.format("orderNo:[%s],取订单金额出错,或不存在该订单!",dto.getOrderId()));
			return false;
		}
		
		
		if(!(amount.compareTo(amountOrder)==0)){//订单表中金额不对
			//查历史记录
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("outTradeNo", dto.getOrderId());
			List<WjEbatongTradeHistoryDTO> tradeHis = checkingAccountMapper.queryTradeHistoryListToCheck(map);
			WjEbatongTradeHistoryDTO tradeHistoryDTO;
			if(tradeHis==null||tradeHis.size()<=0){//有多条数据情况,逻辑上不可能
				tradeHistoryDTO = new WjEbatongTradeHistoryDTO();
			}else{
				tradeHistoryDTO = tradeHis.get(0);
			}
			
			//金额不同,插异常表
			WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = this.setCheckingException(dto, tradeHistoryDTO,"与订单表中金额不同",errorMessageList);
			checkingAccountMapper.insertCheckingAccountError(wjCheckingAccountErrorDTO);
			//修改对账明细表
			checkingAccountMapper.updateCheckingAccountDetailWithContrast(dto.getId());
			//修改订单表比对标记
			
			checkingAccountMapper.updateOrderWithContrast(dto.getOrderId());
			
			return false;
		}else{
			//修改对账明细表
			checkingAccountMapper.updateCheckingAccountDetailWithContrast(dto.getId());
			//修改订单表比对标记
			
			checkingAccountMapper.updateOrderWithContrast(dto.getOrderId());
		}
		return true;
	}

	//更新主账单信息
	private void updateCheckingAccount(Date startDate,Long accountId,boolean result){
		//更新主账单信息
		Date endDate = new Date();
		Timestamp endTime = new Timestamp(endDate.getTime());
		Timestamp startTime = new Timestamp(startDate.getTime());
		
		WjCheckingAccountDTO updateCheckingAccount = new WjCheckingAccountDTO();
		updateCheckingAccount.setStartData(startTime);
		updateCheckingAccount.setFinishData(endTime);
		updateCheckingAccount.setId(accountId);
		if(result){
			updateCheckingAccount.setResult("10");//成功
		}else{
			updateCheckingAccount.setResult("20");//失败
		}
		
		checkingAccountMapper.updateCheckingAccount(updateCheckingAccount);
	}
	
	//查询不是当天和前一天,没对账的数据,并对数据库做处理
	private void compCheckFileEmptyData(String today,String yesterday,String checkingDate,List<String> errorMessageList) throws ParseException{
		//查询未对账过的订单表数据
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderType", "recharge_ma");
		map.put("contrastFlag", "N");
		map.put("orderStatus", "recharge_ma_confirm");
		map.put("today", today);
		if(StringUtils.isNotBlank(yesterday)){
			map.put("yesterday", yesterday);
		}
		
		List<MBOrderInfoDTO> orderList = checkingAccountMapper.queryOrderInfoToCheck(map);
		for(MBOrderInfoDTO order:orderList){
			try {
				//我方有成功订单,对账文件无,插异常表
				WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = new WjCheckingAccountErrorDTO();
				
				Map<String,Object> mapHis = new HashMap<String,Object>();
				map.put("outTradeNo", order.getOrderNo());
				List<WjEbatongTradeHistoryDTO> tradeHis = checkingAccountMapper.queryTradeHistoryListToCheck(map);
				if(tradeHis!=null||tradeHis.size()>0){
					wjCheckingAccountErrorDTO.setTradeNo(tradeHis.get(0).getToken());
					wjCheckingAccountErrorDTO.setTradePlatformType(tradeHis.get(0).getPayType());
				}
				
				wjCheckingAccountErrorDTO.setUserId(order.getUserId());
				wjCheckingAccountErrorDTO.setErrorMessage("对账文件无数据");
				wjCheckingAccountErrorDTO.setOrderNo(order.getOrderNo());
				wjCheckingAccountErrorDTO.setOrderAmount(order.getPrice().toString());
				checkingAccountMapper.insertCheckingAccountError(wjCheckingAccountErrorDTO);
				
				//修改订单表对账标记
				checkingAccountMapper.updateOrderWithContrast(order.getOrderNo());
				
				//-输出信息
				BaseLogger.error(String.format("对账文件日期:[%s]orderNo:[%s]账单数据比对出错,对账文件无数据!", checkingDate,order.getOrderNo()));
				
//				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("对账文件日期:[%s]orderNo:[%s]账单数据比对出错,对账文件无数据!", checkingDate,order.getOrderNo())));
				
				errorMessageList.add(String.format("对账文件日期:[%s]orderNo:[%s]账单数据比对出错,对账文件无数据!", checkingDate,order.getOrderNo()));
			} catch (Exception e) {
				//其中有一条数据出错,不影响其他数据
				BaseLogger.error(String.format("对账文件日期:[%s]orderNo:[%s]账单数据比对出错!", checkingDate,order.getOrderNo()),e);
//				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("对账文件日期:[%s]orderNo:[%s]账单数据比对出错!", checkingDate,order.getOrderNo())));
				
				errorMessageList.add(String.format("对账文件日期:[%s]orderNo:[%s]账单数据比对出错!", checkingDate,order.getOrderNo()));
			}
		}
	}
	
	//对账文件有,我方无数据,设置参数
	private WjCheckingAccountErrorDTO setWeNoDate(WjCheckingAccountDetailDTO dto,WjCheckingAccountDTO wjCheckingAccountDTO,List<String> errorMessageList){
		WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = new WjCheckingAccountErrorDTO();
		wjCheckingAccountErrorDTO.setCheckNo(dto.getCheckNo());
		wjCheckingAccountErrorDTO.setCheckDetailNo(dto.getCheckDetailNo());
		wjCheckingAccountErrorDTO.setErrorMessage("对账文件有,我方历史表中无数据");
		wjCheckingAccountErrorDTO.setOrderNo(dto.getOrderId());
		wjCheckingAccountErrorDTO.setCheckTradeAmount(dto.getCheckTradeAmount());
		wjCheckingAccountErrorDTO.setTradePlatformType(wjCheckingAccountDTO.getTradePlatformType());
		
		try {
			//-输出信息
			BaseLogger.error(String.format("orderNo:[%s]账单数据比对出错,我方无数据!", dto.getOrderId()));
//			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("orderNo:[%s]账单数据比对出错,我方无数据!", dto.getOrderId())));
			
			errorMessageList.add(String.format("orderNo:[%s]账单数据比对出错,我方无数据!", dto.getOrderId()));
		} catch (Exception e) {
		}
		return wjCheckingAccountErrorDTO;
	}
	
	//对账文件与我方无数据不同,设置参数
	private WjCheckingAccountErrorDTO setCheckingException(WjCheckingAccountDetailDTO dto,
						WjEbatongTradeHistoryDTO tradeHistoryDTO,String errorMessage,List<String> errorMessageList){
		WjCheckingAccountErrorDTO wjCheckingAccountErrorDTO = new WjCheckingAccountErrorDTO();
		wjCheckingAccountErrorDTO.setCheckNo(dto.getCheckNo());
		wjCheckingAccountErrorDTO.setCheckDetailNo(dto.getCheckDetailNo());
		wjCheckingAccountErrorDTO.setUserId(tradeHistoryDTO.getUserId());
		wjCheckingAccountErrorDTO.setTradeNo(tradeHistoryDTO.getToken());
		wjCheckingAccountErrorDTO.setErrorMessage(errorMessage);
		wjCheckingAccountErrorDTO.setOrderNo(dto.getOrderId());
		wjCheckingAccountErrorDTO.setOrderAmount(tradeHistoryDTO.getAmount());
		wjCheckingAccountErrorDTO.setCheckTradeAmount(dto.getCheckTradeAmount());
		wjCheckingAccountErrorDTO.setTradePlatformType(tradeHistoryDTO.getPayType());
		
		try {
			//-输出信息
			BaseLogger.error(String.format("orderNo:[%s]账单数据比对出错,[%s]!", dto.getOrderId(),errorMessage));
//			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("orderNo:[%s]账单数据比对出错,[%s]!", dto.getOrderId(),errorMessage)));
			
			errorMessageList.add(String.format("orderNo:[%s]账单数据比对出错,[%s]!", dto.getOrderId(),errorMessage));
		} catch (Exception e) {
		}
		return wjCheckingAccountErrorDTO;
	}

	/**
	 * 保存充值账单文件,解析数据并存库
	 * 联动优势对账数据是每天早上7：00开始生成，于9:00左右生成结束。
	 * 
	 */
	@Override
	public void savaCheckingAccountFile(String checkingDate) {
		BaseLogger.audit(String.format("联动优势,日期[%s]下载对账文件开始;", checkingDate));
		//判断日期格式
		if(!this.verifyDateFormat("yyyyMMdd",checkingDate)){
			BaseLogger.error(String.format("联动优势,日期[%s]格式不正确[%s]", checkingDate,"yyyyMMdd"));
			throw new AppException(String.format("联动优势,日期[%s]格式不正确[%s]", checkingDate,"yyyyMMdd"));
		}
		//判断是否已存对账文件
		WjCheckingAccountDTO checkingAccountDTO = new WjCheckingAccountDTO();
		checkingAccountDTO.setCheckingData(checkingDate);
		checkingAccountDTO.setTradePlatformType(PAY_TYPE);
		List<WjCheckingAccountDTO> dtoList = checkingAccountMapper.queryCheckingAccount(checkingAccountDTO);
		if(dtoList!=null&&dtoList.size()>0){
			BaseLogger.audit(String.format("联动优势,日期[%s]对账文件已存在;", checkingDate));
			return;
		}
		try {
			BaseLogger.audit(String.format("联动优势,日期[%s]调用联动优势下载对账文件接口;", checkingDate));
			//根据对账日期,调用联动接口,下载对账文件
//			byte[] file = unionMobileB2CService.downloadCheckFile(checkingDate);
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO 
				= new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			rechargeDTO.setStartDate(checkingDate);
			byte[] file = b2CPayGatewayService.downloadCheckFile(rechargeDTO, 10);
			//文件名
			String fileName = FILE_NAME_HEAD + checkingDate + FILE_NAME_TAIL;
			BaseLogger.audit(String.format("联动优势,日期[%s]保存对账文件到文件服务器;", checkingDate));
			String fileBusinessNo = commonAttachmentOperate.saveAttachementAndReturnFileId(fileName, file, FILE_TYPE);
			//把对账单文件数据保存到数据库
			this.saveCheckingFileToData(file,fileBusinessNo,checkingDate);
			
			BaseLogger.audit(String.format("联动优势,日期[%s]下载对账文件结束;", checkingDate));
		} catch (Exception e) {
			if(e instanceof AppException){
				String msg=((AppException)e).getMessage();
				if(StringUtils.isBlank(msg)){
					BaseLogger.error(String.format("联动优势,结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate),e);
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("联动优势,结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate)));
					throw new AppException(String.format("联动优势,结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate));
				}
				BaseLogger.error(msg,e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(msg));
				throw (AppException)e; 
			}else {
				BaseLogger.error(String.format("联动优势,结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate),e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("联动优势,结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate)));
			}
			
			throw new AppException(String.format("联动优势,结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate));
		}
	}
	
	//把对账单文件数据保存到数据库
	private void saveCheckingFileToData(byte[] file,String fileBusinessNo,String checkingDate) {
		BaseLogger.audit(String.format("联动优势,日期[%s]保存对账文件数据到数据库开始;", checkingDate));
		BufferedReader reader = null;
		List<WjCheckingAccountDetailDTO> list = new ArrayList<WjCheckingAccountDetailDTO>();
		WjCheckingAccountDTO wjCheckingAccountDTO = new WjCheckingAccountDTO();
		wjCheckingAccountDTO.setFileBusinessNo(fileBusinessNo);
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(file);
			InputStreamReader inputReader =  new InputStreamReader(inStream,"GBK");
			reader = new BufferedReader(inputReader);
			String tempString = null;
			int line = 0;
			boolean startFlag = true;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (startFlag && tempString.startsWith(FILE_START_FLAG)) {
					//解析文件首行信息,并保存
					this.resolveHead(tempString,checkingDate,wjCheckingAccountDTO);
					
					startFlag = false;
					continue;
				}
				if (tempString.startsWith(FILE_END_FLAG)) {
					//解析文件尾行信息,并保存
					this.resolveTail(tempString,wjCheckingAccountDTO);
					
					break;
				}
				//解析账单信息
				String[] str = tempString.split(",");
				if (str == null || str.length < 16) {
					//对账文件中有异常数据
					BaseLogger.error(String.format("联动优势,文件NO[%s],账单文件有条异常数据dataString[%s]", fileBusinessNo,tempString));
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("联动优势,文件NO[%s],账单文件有条异常数据dataString[%s]", fileBusinessNo,tempString)));
					continue;
				}
				//设置账单内容数据
				WjCheckingAccountDetailDTO dto = this.resolveContent(str);
				list.add(dto);
				line++;
				if (line > PAGE_SIZE) {
					for(WjCheckingAccountDetailDTO detailDTO:list){
						detailDTO.setCheckNo(wjCheckingAccountDTO.getCheckNo());
						detailDTO.setCheckDetailNo(this.getCheckAccountDetailNo(
								ISequenceService.SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ,checkingDate));
					}
					checkingAccountMapper.insertCheckingAccountDetailList(list);
					list = new ArrayList<WjCheckingAccountDetailDTO>();
					line = 0;
				}
			}
			reader.close();
			
			if(list!=null&&list.size()>0){
				for(WjCheckingAccountDetailDTO dto:list){
					dto.setCheckNo(wjCheckingAccountDTO.getCheckNo());
					dto.setCheckDetailNo(this.getCheckAccountDetailNo(
							ISequenceService.SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ,checkingDate));
				}
				checkingAccountMapper.insertCheckingAccountDetailList(list);
			}
		} catch (AppException e) {
			throw e; 
		} catch (Exception e) {
			BaseLogger.error(String.format("联动优势,文件NO[%s],账单文件数据保存到数据库失败！", fileBusinessNo),e);
			throw new AppException(String.format("联动优势,文件NO[%s],账单文件数据保存到数据库失败！", fileBusinessNo));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e1) {
					BaseLogger.error(e1);
				}
			}
		}
		BaseLogger.audit(String.format("联动优势,日期[%s]保存对账文件数据到数据库结束;", checkingDate));
	}
	
	//解析文件首行信息,并保存
	private void resolveHead(String tempString,String checkingDate,WjCheckingAccountDTO wjCheckingAccountDTO){
		// 商户号（mer_id）,对账日期（settle_date）,版本号（version）,返回码（ret_code）,返回信息（ret_msg）
		String[] bill_head = tempString.split(",");
		//	1. 检测对账文件是否生成
		if(!RETURN_SUCESS.equals(bill_head[4])){
			BaseLogger.error(String.format("联动优势,[%s]对账文件还没成功生成！",checkingDate));
			throw new AppException(String.format("联动优势,[%s]对账文件还没成功生成！",checkingDate));
		}
		//	2. 保存对账主信息
		wjCheckingAccountDTO.setCheckingData(bill_head[2]);
		wjCheckingAccountDTO.setTradePlatformType(PAY_TYPE);
		String checkNo = this.getCheckAccountNo(ISequenceService.SEQ_NAME_CHECK_ACCOUNT_SEQ, checkingDate);
		wjCheckingAccountDTO.setCheckNo(checkNo);
		checkingAccountMapper.insertCheckingAccount(wjCheckingAccountDTO);
		BaseLogger.audit(String.format("联动优势,[%s]对账文件数据库保存！",checkingDate));
	}
	
	//解析文件尾行信息,并保存
	private void resolveTail(String tempString,WjCheckingAccountDTO wjCheckingAccountDTO){
		// TRADEDETAIL-END,商户号（mer_id）,交易日期（settle_date）,总笔数,总金额
		String[] bill_end = tempString.split(",");
		wjCheckingAccountDTO.setTotalAmount(bill_end[4]);
		wjCheckingAccountDTO.setTotalNum(bill_end[3]);
		//更新对账主信息(更新总数量,总金额)
		checkingAccountMapper.updateCheckingAccount(wjCheckingAccountDTO);
	}
	
	//设置账单内容数据
	private WjCheckingAccountDetailDTO resolveContent(String[] str){
		WjCheckingAccountDetailDTO dto = new WjCheckingAccountDetailDTO();
		dto.setMerId(str[0]);
		dto.setGoodsId(str[1]);
		dto.setCheckMobileId(str[2]);
		dto.setOrderId(str[3]);
		dto.setCheckMerDate(str[4]);
		dto.setCheckPayDate(str[5]);
		dto.setCheckTradeAmount(str[6]);
		dto.setAmtType(str[7]);
		dto.setCheckGateId(str[8]);
		dto.setCheckSettleDate(str[9]);
		dto.setCheckTransType(str[10]);
		dto.setCheckTransStatus(str[11]);
		dto.setCheckBankCheck(str[12]);
		dto.setCheckProductId(str[13]);
		dto.setCheckRefundNo(str[14]);
		dto.setCheckTransTime(str[15]);
		return dto;
	}
	
	private Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}
	
	//校验日期格式
    private boolean verifyDateFormat(String format,String stringDate){
    	try {
	    	SimpleDateFormat f = new SimpleDateFormat(format);
	    	Date date = (Date) f.parseObject(stringDate);
	    	String tmp = f.format(date);
	    	return tmp.equals(stringDate);
    	} catch (Exception e) {
            return false;
        }
    }
    
    private String getCheckAccountNo(String sequenceName,String date) {
		return "CM" + date + sequenceService.getNextStringValue(sequenceName, 5);
	}
    
    private String getCheckAccountDetailNo(String sequenceName,String date) {
		return "CD" + date + sequenceService.getNextStringValue(sequenceName, 10);
	}

    /**
     * 订单查询
     */
	@Override
	public MBRechargeDTO queryOrder(String orderNo)throws Exception {
		com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO 
			= new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
		rechargeDTO.setOrderNo(orderNo);
		MBRechargeDTO result = new MBRechargeDTO();
		BeanUtils.copyProperties(result, b2CPayGatewayService.queryOrder(rechargeDTO).getRechargeDTO());
		
		return result;
	}

	/**
	 * 易宝充值对账文件保存
	 * 对账数据是每天早上6：00生成昨天的数据
	 */
	@Override
	public void savaYeePayCheckingAccountFile(String checkingDate) throws Exception {
		BaseLogger.audit(String.format("易宝充值,日期[%s]下载对账文件开始;", checkingDate));
		//判断日期格式
		if(!this.verifyDateFormat("yyyyMMdd",checkingDate)){
			BaseLogger.error(String.format("易宝充值日期[%s]格式不正确", checkingDate,"yyyyMMdd"));
			throw new AppException(String.format("日期[%s]格式不正确", checkingDate,"yyyyMMdd"));
		}
    	
		//判断是否已存对账文件
		WjCheckingAccountDTO checkingAccountDTO = new WjCheckingAccountDTO();
		checkingAccountDTO.setCheckingData(checkingDate);
		checkingAccountDTO.setTradePlatformType(PAY_TYPE_YEE_PAY);
		List<WjCheckingAccountDTO> dtoList = checkingAccountMapper.queryCheckingAccount(checkingAccountDTO);
		if(dtoList!=null && dtoList.size()>0){
			BaseLogger.audit(String.format("易宝充值日期[%s]对账文件已存在;", checkingDate));
			return;
		}
		try {
			BaseLogger.audit(String.format("易宝充值日期[%s]调用易宝充值下载对账文件接口;", checkingDate));
			//根据对账日期,调用联动接口,下载对账文件
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO 
				= new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			String startDate = checkingDate.substring(0, 4)+"-"+checkingDate.substring(4, 6)+"-"+checkingDate.substring(6, 8);
			rechargeDTO.setStartDate(startDate);
			rechargeDTO.setEndDate(startDate);
			byte[] file = b2CPayGatewayService.downloadCheckFile(rechargeDTO, 20);
			
			if(file==null){
				BaseLogger.error(String.format("易宝充值,结账日期:[%s],充值账单文件不存在！", checkingDate));
				BaseLogger.audit(String.format("易宝充值,日期[%s]下载对账文件结束;", checkingDate));
//				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("易宝充值结账日期:[%s],充值账单文件不存在！", checkingDate)));
				return;
			}
			
			//文件名
			String fileName = FILE_NAME_HEAD + checkingDate + FILE_NAME_TAIL;
			BaseLogger.audit(String.format("易宝充值日期[%s]保存对账文件到文件服务器;", checkingDate));
			String fileBusinessNo = commonAttachmentOperate.saveAttachementAndReturnFileId(fileName, file, FILE_TYPE_YEE_PAY);
			//把对账单文件数据保存到数据库
			this.saveYeePayCheckingFileToData(file,fileBusinessNo,checkingDate);
			
			BaseLogger.audit(String.format("易宝充值,日期[%s]下载对账文件结束;", checkingDate));
		} catch (AppException e) {
			String msg=((AppException)e).getMessage();
			if(StringUtils.isBlank(msg)){
				BaseLogger.error(String.format("易宝充值结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate),e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("易宝充值结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate)));
				throw new AppException(String.format("易宝充值结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate));
			}
			BaseLogger.error(msg,e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(msg));
			throw e; 
		}catch (Exception e) {	
			BaseLogger.error(String.format("易宝充值结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate),e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("易宝充值结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate)));
			throw new AppException(String.format("易宝充值结账日期:[%s],保存充值账单文件,解析数据并存库失败！", checkingDate));
		}
		
	}
	
	
	//把对账单文件数据保存到数据库
	private void saveYeePayCheckingFileToData(byte[] file,String fileBusinessNo,String checkingDate) {
		BaseLogger.audit(String.format("易宝日期[%s]保存对账文件数据到数据库开始;", checkingDate));
		BufferedReader reader = null;
		List<WjCheckingAccountDetailDTO> list = new ArrayList<WjCheckingAccountDetailDTO>();
		WjCheckingAccountDTO wjCheckingAccountDTO = new WjCheckingAccountDTO();
		wjCheckingAccountDTO.setFileBusinessNo(fileBusinessNo);
		try {
			ByteArrayInputStream inStream = new ByteArrayInputStream(file);
			InputStreamReader inputReader =  new InputStreamReader(inStream,"utf-8");
			reader = new BufferedReader(inputReader);
			String tempString = null;
			int line = 0;
			
			//保存对账数据
			wjCheckingAccountDTO.setCheckingData(checkingDate);
			wjCheckingAccountDTO.setTradePlatformType(PAY_TYPE_YEE_PAY);
			String checkNo = this.getCheckAccountNo(ISequenceService.SEQ_NAME_CHECK_ACCOUNT_SEQ, checkingDate);
			wjCheckingAccountDTO.setCheckNo(checkNo);
			
			
			
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				
				if (tempString.startsWith("商户账户编号")) {
					continue;
				}
				if(tempString.startsWith("消费金额：")){
					//解析文件尾行信息,并保存
					try {
						String totalAmountStr = tempString.substring(5);
						BigDecimal totalAmount = new BigDecimal(totalAmountStr).multiply(new BigDecimal(100));
						wjCheckingAccountDTO.setTotalAmount(totalAmount.toPlainString());
					} catch (Exception e) {
						BaseLogger.error("易宝对账文件,消费金额获取出错:totalAmount["+tempString.substring(6)+"]");
					}
					continue;
				}
				if (tempString.startsWith("总交易笔数：")) {
					//解析文件尾行信息,并保存
					wjCheckingAccountDTO.setTotalNum(tempString.substring(6));;
					continue;
				}
				if (tempString.startsWith("总手续费：")) {
					//解析文件尾行信息,并保存
					wjCheckingAccountDTO.setTotalCounterFee(tempString.substring(5));

					checkingAccountMapper.insertCheckingAccount(wjCheckingAccountDTO);
					BaseLogger.audit(String.format("易宝[%s]对账文件数据库保存成功！",checkingDate));
//						checkingAccountMapper.updateCheckingAccount(wjCheckingAccountDTO);
					break;
				}
				//解析账单信息
				String[] str = tempString.split(",");
				if(str != null && str.length >= 14){
					//设置账单内容数据
					WjCheckingAccountDetailDTO dto = this.yeePayResolveContent(str);
					list.add(dto);
					line++;
					if (line > PAGE_SIZE) {
						for(WjCheckingAccountDetailDTO detailDTO:list){
							detailDTO.setCheckNo(wjCheckingAccountDTO.getCheckNo());
							detailDTO.setCheckDetailNo(this.getCheckAccountDetailNo(
									ISequenceService.SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ,checkingDate));
						}
						checkingAccountMapper.insertCheckingAccountDetailList(list);
						list = new ArrayList<WjCheckingAccountDetailDTO>();
						line = 0;
					}
				}
				
			}
			reader.close();
			
			if(list!=null&&list.size()>0){
				for(WjCheckingAccountDetailDTO dto:list){
					dto.setCheckNo(wjCheckingAccountDTO.getCheckNo());
					dto.setCheckDetailNo(this.getCheckAccountDetailNo(
							ISequenceService.SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ,checkingDate));
				}
				checkingAccountMapper.insertCheckingAccountDetailList(list);
			}
		} catch (AppException e) {
			throw e; 
		} catch (Exception e) {
			BaseLogger.error(String.format("易宝文件NO[%s],账单文件数据保存到数据库失败！", fileBusinessNo),e);
			throw new AppException(String.format("易宝文件NO[%s],账单文件数据保存到数据库失败！", fileBusinessNo));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e1) {
					BaseLogger.error(e1);
				}
			}
		}
		BaseLogger.audit(String.format("易宝日期[%s]保存对账文件数据到数据库结束;", checkingDate));
	}
	
	//设置账单内容数据
	private WjCheckingAccountDetailDTO yeePayResolveContent(String[] str){
		WjCheckingAccountDetailDTO dto = new WjCheckingAccountDetailDTO();
		dto.setMerId(str[0]);
		dto.setCheckMerDate(str[2]);
		dto.setCheckTransTime(str[3]);
		dto.setOrderId(str[4]);
		dto.setTradeNo(str[5]);
		
		try {
			int amount = new BigDecimal(str[6]).multiply(new BigDecimal(100)).intValue();
			dto.setCheckTradeAmount(String.valueOf(amount));
		} catch (Exception e) {
			BaseLogger.error("易宝对账文件金额异常;");
		}
		
		dto.setCounterFee(str[9]);
		dto.setCheckTransStatus("1");//交易状态:成功
		dto.setGoodsId(str[12]);
		dto.setCheckProductId(str[13]);
		
		return dto;
		
	}
		
}
