package com.zhuanyi.vjwealth.wallet.mobile.packagehr.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.packagehr.dto.ResponseDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vj.Base16EncoderUtil;
import vj.EncrypterTool;
import vj.EncryptionTool;
import vj.SignatureUtils;

/**
 * Created by yi on 16/3/28.
 */
@RequestMapping("/packagehr")
@Controller
public class PackageHRController {

//    @Value("${packagehr.md5Key}")
    private String md5Key;

//    @Value("${packagehr.partner}")
    private String partner;

//    @Value("${packagehr.vjPrivateKey}")
    private String vjPrivateKey;

//    @Value("${packagehr.prPublicKey}")
    private String prPublicKey;


    @RequestMapping(value = "/decrypt", method = RequestMethod.GET)
    public String decrypt(@RequestParam("key") String key
            ,@RequestParam("sign") String sign
            ,@RequestParam("content") String content
            ,@RequestParam("partner") String partner,Model model) {
        BaseLogger.audit("解密 开始");
        BaseLogger.audit(String.format("解密 key:%s,sign:%s,content:%s,partner:%s",key,sign,content,partner));
        ResponseDTO responseDTO = new ResponseDTO();
        String responseContent;
        //参数验证
        if (StringUtils.isBlank(sign) || StringUtils.isBlank(content)  || StringUtils.isBlank(key) ) {
            BaseLogger.error("decrypt fail - invalid input");
            //TODO 返回错误代码
            responseContent = "{\"message\":\"decrypt fail - invalid input\",\"status\":\"N\"}";
            model.addAttribute("content", responseContent);
            return "/packagehr/error";
        }

        //partner参数验证
        if (!StringUtils.equals(this.partner, partner) ){
            BaseLogger.error("decrypt fail - invalid  partner ID ");
            //TODO 返回错误代码
            responseContent = "{\"message\":\"decrypt fail - invalid  partner ID\",\"status\":\"N\"}";
            model.addAttribute("content", responseContent);
            return "/packagehr/error";
        }

        try {
            //base16解码
            String binaryContent = new String(Base16EncoderUtil.decode(content));

            //1.验签 密文+md5Key 然后去md5的值进行比较
            System.out.println(String.format("binaryContent + md5Key ：[%s] ", binaryContent + md5Key));
            String expectedSign = EncrypterTool.string2MD5(binaryContent + md5Key);
//            String expectedSign = EncrypterTool.string2MD5("PACKAGEHR");
            if (!sign.equals(expectedSign)) {
                BaseLogger.error(String.format("verify sign failed|received:{%s}|expected:{%s}", expectedSign, sign));
                //TODO 返回错误代码
                throw new Exception("verify fail");
            }
            BaseLogger.info("验签通过");

            //2.base16解码及rsa解密
            String randomKey = SignatureUtils.rsaDecrypt(key, vjPrivateKey, null);

            //3.RC解密密文
            char[] chars = EncryptionTool.hloveyRC4(binaryContent, StringUtils.trim(randomKey));

            String  result = EncrypterTool.fromUnicode(new String(chars));
            BaseLogger.info("解密-获取的接口返回内容为: " + result);

            //TODO 业务逻辑
            JSONObject json = JSONObject.parseObject(result);
            String name = json.getString("name") + "test";
            responseContent = "{\"message\":\""+ name +"\",\"status\":\"Y\"}";
            //TODO 加密返回内容
            model.addAttribute("content", responseContent);

            BaseLogger.audit("解密成功");
        } catch (Exception e) {
            BaseLogger.error("解密异常 " + e.getMessage());
            responseContent = "{\"message\":\"解密失败\",\"status\":\"N\"}";
            model.addAttribute("content", responseContent);
            return "/packagehr/error";
        }
         BaseLogger.audit("解密结束");
          return "/packagehr/success";
    }

}
