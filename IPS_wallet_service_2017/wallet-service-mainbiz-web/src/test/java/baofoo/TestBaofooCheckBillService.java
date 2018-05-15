package baofoo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.WjCheckingAccountDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl.CheckingAccountService;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:core/application-context.xml" })*/
public class TestBaofooCheckBillService {

	public static void main(String[] args) {
		String date = "2016-08-12";
		String fileBusinessNo = "123456";

		BaseLogger.audit(String.format("宝付,日期[%s],解析,保存对账文件数据到数据库开始;", date));
		ZipInputStream zins = null;
		ByteArrayInputStream inStream = null;
		ByteArrayInputStream inStream2 = null;
		
		ByteArrayOutputStream outStream=null;
		
		ZipEntry ze = null;
		BufferedReader reader = null;
		InputStreamReader inputReader = null;
		String tempString = null;

		WjCheckingAccountDTO wjCheckingAccountDTO = new WjCheckingAccountDTO();
		List<WjCheckingAccountDetailDTO> list = new ArrayList<WjCheckingAccountDetailDTO>();

		byte[] file=getFileByte(new File("D:\\2016-08-12.zip"));

		try {
			inStream = new ByteArrayInputStream(file);
			zins = new ZipInputStream(inStream);
			// 解压
			if ((ze = zins.getNextEntry()) != null) {
				
				
				outStream=new ByteArrayOutputStream();
				byte[] ch=new byte[256];
				int i;
				 while ((i = zins.read(ch)) != -1){
					 outStream.write(ch, 0, i);  
				 }
				
			
				inStream2 = new ByteArrayInputStream(outStream.toByteArray());
				inputReader = new InputStreamReader(inStream2, "utf-8");
				reader = new BufferedReader(inputReader);
				int line = 1;
				while ((tempString = reader.readLine()) != null) {
					BaseLogger.info("解析数据,组装对账数据,tempString=" + tempString);
					// 解析数据,组装对账数据
					if (line == 2) {// 总数据
						String[] tmpStr = tempString.split("|");

						wjCheckingAccountDTO.setFileBusinessNo(fileBusinessNo);// 文件NO
						wjCheckingAccountDTO.setCheckingData(date);// 对账日期
						wjCheckingAccountDTO.setTradePlatformType(CheckingAccountService.PAY_TYPE_BAOFOO_PAY);// 宝付
						String checkNo = "123456";
						wjCheckingAccountDTO.setCheckNo(checkNo);// 对账NO
						wjCheckingAccountDTO.setTotalAmount(tmpStr[5]);// 对账总金额，以分为单位
						wjCheckingAccountDTO.setTotalNum(tmpStr[4]);// 对账总数
						wjCheckingAccountDTO.setTotalCounterFee(tmpStr[6]);// 总手续费

					} else if (line >= 4) {// 详细数据
						String[] tmpStr = tempString.split("|");
						WjCheckingAccountDetailDTO accountDetail = new WjCheckingAccountDetailDTO();
						accountDetail.setCheckNo(wjCheckingAccountDTO.getCheckNo());
						// accountDetail.setCheckDetailNo(checkDetailNo);
						accountDetail.setMerId(tmpStr[0]);// 商户号
						accountDetail.setCheckMerDate(date);// 对账日期
						accountDetail.setCheckTransTime(tmpStr[6]);
						accountDetail.setOrderId(tmpStr[5]);// 宝付订单号
						accountDetail.setTradeNo(tmpStr[10]);// 交易号
						accountDetail.setCheckTradeAmount(String.valueOf(tmpStr[8]));
						accountDetail.setCounterFee(tmpStr[9]);
						accountDetail.setCheckTransStatus("1");// 交易状态:成功
						// accountDetail.setGoodsId(str[12]);
						// accountDetail.setCheckProductId(str[13]);

						list.add(accountDetail);
					}
					line++;
				}
			}

			
			
			System.out.println(JSONObject.toJSONString(wjCheckingAccountDTO));
			System.out.println("===========================================");
			System.out.println(JSONObject.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (inStream2 != null) {
					inStream2.close();
				}

				if (inputReader != null) {
					inputReader.close();
				}

				if (reader != null) {
					reader.close();
				}

				if (zins != null) {
					zins.close();
				}
			} catch (IOException e) {
				BaseLogger.error(String.format("宝付,日期[%s],关闭流异常;", date), e);
			}

			BaseLogger.audit(String.format("宝付,日期[%s],保存对账文件数据到数据库结束;", date));
		}

	}

	public static byte[] getFileByte(File file) {

		byte[] by =null;
		try {
			InputStream is = new FileInputStream(file);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			byte[] bb = new byte[2048];
			int ch;
			ch = is.read(bb);
			while (ch != -1) {
				bytestream.write(bb, 0, ch);
				ch = is.read(bb);
			}
			by = bytestream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return by;
	}

}
