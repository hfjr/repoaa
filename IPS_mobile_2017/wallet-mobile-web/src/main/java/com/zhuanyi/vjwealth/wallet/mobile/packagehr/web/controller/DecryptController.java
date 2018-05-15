package com.zhuanyi.vjwealth.wallet.mobile.packagehr.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.packagehr.dto.ResponseDTO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vj.Base16EncoderUtil;
import vj.EncrypterTool;
import vj.EncryptionTool;
import vj.SignatureUtils;

/**
 * Created by yi on 16/3/28.
 */
@RequestMapping("/api")
@Controller
public class DecryptController {

//    @Value("${packagehr.md5Key}")
    private String md5Key;

//    @Value("${packagehr.partner}")
    private String partner;

//    @Value("${packagehr.vjPrivateKey}")
    private String vjPrivateKey;

//    @Value("${packagehr.prPublicKey}")
    private String prPublicKey;


    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> decrypt(@RequestParam("key") String key
            ,@RequestParam("sign") String sign
            ,@RequestParam("content") String content
            ,@RequestParam("partner") String partner) {
        BaseLogger.audit("解密 开始");

        BaseLogger.audit(String.format("解密 key:%s,sign:%s,content:%s,partner:%s",key,sign,content,partner));
        ResponseDTO responseDTO = new ResponseDTO();
        String responseContent;
        //参数验证
        if (StringUtils.isBlank(sign) || StringUtils.isBlank(content)  || StringUtils.isBlank(key) ) {
            BaseLogger.info("decrypt - invalid input");
            //TODO 返回错误代码
        }

        //partner参数验证
        if (!StringUtils.equals(this.partner, partner) ){
            BaseLogger.error("decrypt fail - invalid  partner ID ");
            //TODO 返回错误代码
        }

        try {

            //base16解码
            String binaryContent = new String(Base16EncoderUtil.decode(content));

            //1.验签 密文+md5Key 然后去md5的值进行比较
            System.out.println(String.format("binaryContent + md5Key ：[%s] ", binaryContent + md5Key));
            String expectedSign = EncrypterTool.string2MD5(binaryContent + md5Key);
//            String expectedSign = EncrypterTool.string2MD5("PACKAGEHR");
            if (!sign.equals(expectedSign)) {
                BaseLogger.info(String.format("verify sign failed|received:{%s}|expected:{%s}",expectedSign,sign));
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

            BaseLogger.audit("解密成功");
        } catch (Exception e) {
            e.printStackTrace();
            BaseLogger.error("解密异常 " + e.getMessage());
            responseContent = "{\"message\":\""+ e.getMessage() +"\",\"status\":\"N\"}";
        }
        responseDTO = encryptData(responseContent);

        BaseLogger.audit("解密结束");
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }



    /**
     * 加密数据
     *
     * @param content
     */
    private ResponseDTO  encryptData(String content) {
        BaseLogger.info(String.format("加密接口响应参数开始 content ：%s ", content));
//        Map<String, String> postData = new HashMap<String, String>();
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            //中文解决方案
            String unicodeContent = EncrypterTool.toUnicode(content,false);

            //获取一次性128位随机密码
            String randomKey = RandomStringUtils.randomAscii(128);
            //密文(加密算法用RC4，密钥为key)，
            char[] binaryContent = EncryptionTool.hloveyRC4(unicodeContent, StringUtils.trim(randomKey));

            //MD5签名信息(签名内容为content+MD5key)
            String sign = EncrypterTool.string2MD5(new String(binaryContent) + md5Key);
//            String sign = EncrypterTool.string2MD5(new String(binaryContent) + "PACKAGEHR");

            //密钥本身用RSA公钥加密并用base16编码
            String binaryKey = SignatureUtils.rsaEncryptWithBase16(randomKey, prPublicKey, "UTF-8");

            //base16编码格式
            String binaryContent2hex = Base16EncoderUtil.encode(binaryContent);

            responseDTO.setKey(binaryKey);
            responseDTO.setContent(binaryContent2hex);
            responseDTO.setSign(sign);
            responseDTO.setCode("00");
            responseDTO.setMessage("success");
        } catch (Exception e) {
            //todo 异常处理
            responseDTO.setCode("01");
            responseDTO.setMessage("fail");
            BaseLogger.error(String.format(" encryptData content ：[%s] 失败原因  ：[%s] " ,content, e.getMessage()));
            throw new RuntimeException("加密接口响应参数出现异常");
        }
        BaseLogger.info("加密接口响应参数结束 : " + responseDTO);
        return responseDTO;
    }
}
