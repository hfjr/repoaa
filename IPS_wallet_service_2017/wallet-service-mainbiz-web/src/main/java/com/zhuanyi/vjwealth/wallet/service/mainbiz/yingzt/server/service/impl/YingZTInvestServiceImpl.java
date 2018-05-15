package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTInvestService;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.utils.yingzt.VJSercurityUtil;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yi on 16/2/22.
 */
@Service
public class YingZTInvestServiceImpl implements IYingZTInvestService {

    @Value("${yingzt.md5Key}")
    private String md5Key;

    @Value("${yingzt.partner}")
    private String partner;

    @Value("${yingzt.yztPublicKey}")
    private String yztPublicKey;

    @Value("${yingzt.vjPrivateKey}")
    private String vjPrivateKey;

    @Value("${yingzt.investUrl}")
    private String investUrl;

    @Remote
    ISendEmailService sendEmailService;

    @Autowired
    private IMBUserAccountRfMapper userAccountRfMapper;

    @Override
    public String apiInvest(String strJson) throws RuntimeException {

        Map<String, String> postData = new HashMap<String, String>();
        String result = null;
        String tradeId = null; //交易流水号
        try {
             //获取交易标示
             JSONObject jsonArgs = JSONObject.parseObject(strJson);
             tradeId = jsonArgs.getString("tradeId"); //
            //构建加密参数
            postData = VJSercurityUtil.encryptParams(strJson,md5Key,yztPublicKey,partner);
        } catch (Exception e) {
            String log = String.format("定期理财-小赢保单-交易流水号:[%s]-加密请求参数失败! ", tradeId);
            BaseLogger.error( log, e);
            // 异常发送邮件 转移到业务
            //sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(log));
            throw new RuntimeException( log);
        }
        // 2.调用接口,获取返回数据
        String response = vj.HttpClientUtil.post(investUrl, postData, "UTF-8");
        BaseLogger.audit(String.format("获取保单编号-响应内容：[%s]  ", response));
        // 3.接口返回验证签名及解密 是否通过,通过：业务逻辑，不通过：记录非法日志
        try{
            Map<String,String>  responseMap = JSONObject.parseObject(response,Map.class);
            result = VJSercurityUtil.decryptResponse(responseMap,md5Key,vjPrivateKey,"");
        } catch (Exception e) {
            String log = String.format("定期理财-小赢保单-交易流水号:[%s]-解密响应参数失败! ", tradeId);
            BaseLogger.error(log, e);
            // 异常发送邮件 转移到业务
           // sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(log));
            throw new RuntimeException(log);
        }

        // 4.调用业务接口(邮件服务异常处理)
        try{
            if( null != result){
        result = dealResponseData(result,tradeId);
            }
        } catch (Exception e) {
            String log = String.format("定期理财-小赢保单-交易流水号:[%s]-解析赢众通的返回数据失败! ", tradeId);
            BaseLogger.error(log, e);
            // 异常发送邮件 转移到业务
            throw new RuntimeException(log);
        }finally {
        return result;
        }

    }


    /**
     * 解析赢众通的返回数据
     * @param result
     * @return
     */
    private String dealResponseData(String result,String tradeId) {
        BaseLogger.audit(String.format("dealResponseData 入参数：[%s]  ", result));

        String dealResult  = null ;
        JSONObject json = JSONObject.parseObject(result);
        // 非正常响应逻辑邮件预警
        if (!"0".equals(json.getString("ret"))) {
            String log = String.format("定期理财-小赢保单-交易流水号:[%s]-赢众通接口返回值 result : [%s] 异常! ", tradeId, result);
            BaseLogger.warn( log );
            //调用邮件服务失败处理
            sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(log));
            dealResult = result;
        } else {
            dealResult =  json.getString("data");
            //重复下单
            //      if ("0".equals(json.getString("ret"))  && "repeat".equals(json.getString("msg")) ) {
            //          BaseLogger.audit(String.format("小赢理财-下单接口-重复下单-赢众通 ret: 0, msg:'repeat' ， [%s]",result));
            //          sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("[%s],小赢理财-下单接口-重复下单-赢众通返回值result : [%s] ! ", this.getClass() + "verifyAndDecrypt",result)));
            //      }
        }
        BaseLogger.audit(String.format("dealResponseData 出参数：[%s]  ", dealResult));
        return dealResult;
    }


    private Map<String, Object> pageEmailMap(String content) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", content);
        return map;
    }


}
