package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.utils.yingzt;

import com.fab.core.logger.BaseLogger;
import org.apache.commons.lang.RandomStringUtils;
import vj.Base16EncoderUtil;
import vj.EncrypterTool;
import vj.EncryptionTool;
import vj.SignatureUtils;
import vj.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 小赢加密及解密封装
 * Created by hexy on 16/8/11.
 */
public class VJSercurityUtil {

    /**
     * 加密请求的内容
     * @param resp 加密内容
     * @param md5Key  加密md5秘钥
     * @param publicKey 对方RSA公钥
     * @param partner 商户编号
     * @return
     * @throws Exception
     */
    public static Map<String, String> encryptParams(String resp, String md5Key, String publicKey, String partner) throws Exception {
        BaseLogger.audit(String.format("encryptParams 入参数 resp ：%s, md5Key ：%s , publicKey ：%s , partner ：%s  ", resp,md5Key,publicKey,partner));
        HashMap map = new HashMap();
        // 内容unicode编码
        String unicodeContent = EncrypterTool.toUnicode(resp, false);
        //获取一次性128位随机密码
        String randomKey128 = RandomStringUtils.randomAscii(128);
        //密文(加密算法用RC4，密钥为key)，base16编码格式
        char[] rc4 = EncryptionTool.hloveyRC4(unicodeContent, randomKey128);
        String binaryContent = Base16EncoderUtil.encode(rc4);
        //MD5签名信息(签名内容为binaryContent+MD5key)
        String sign = EncrypterTool.string2MD5(binaryContent + md5Key);
        //一次性密钥本身用RSA公钥加密并用base16编码
        String binaryKey = vj.SignatureUtils.rsaEncryptWithBase16(randomKey128, publicKey, "UTF-8");
        map.put("key", binaryKey);
        map.put("content", binaryContent);
        map.put("sign", sign);
        map.put("partner", partner);
        BaseLogger.audit(String.format("encryptParams 出参数 key ：%s, content ：%s , sign ：%s , partner ：%s  ", binaryKey,binaryContent,sign,partner));
        return map;
    }

    /**
     * 解密响应的内容
     * @param respMap 解密内容map参数期中key为["sign","key","content"]
     * @param md5Key
     * @param privateKey
     * @param partner
     * @return
     * @throws Exception
     */
    public static String decryptResponse(Map respMap, String md5Key, String privateKey, String partner) throws Exception {
        BaseLogger.audit(String.format("decryptResponse 入参数 respMap ：%s, md5Key ：%s , privateKey ：%s , partner ：%s  ", respMap,md5Key,privateKey,partner));
        String content = null;
        if(!StringUtils.equals(partner, getStringValueFromMap(respMap, "partner"))) {
            //System.out.println("商户号无效 invalid  partner ID ");
            BaseLogger.audit("decryptResponse 商户号无效 invalid  partner ID ");
        } else {
            String expectedSign = EncrypterTool.string2MD5(getStringValueFromMap(respMap, "content") + md5Key);
            if(expectedSign.equals(getStringValueFromMap(respMap, "sign"))) {
//                System.out.println("decryptResponse 验证签名成功");
                //base16解码并用RSA解密key
                String randomKey = SignatureUtils.rsaDecrypt(getStringValueFromMap(respMap, "key"), privateKey, null);
                //base16解码并用RC解密密文
                String binaryContent = new String(Base16EncoderUtil.decode(getStringValueFromMap(respMap, "content")));
                char[] chars = EncryptionTool.hloveyRC4(binaryContent, randomKey);
                // 内容unicode解码
                content = EncrypterTool.fromUnicode(new String(chars));
            } else {
                //System.out.println("decryptResponse 验证签名失败");
                BaseLogger.error("decryptResponse 验证签名失败");
            }
        }
        BaseLogger.audit(String.format("decryptResponse 出参数 content ：%s  ", content));
        return content;
    }

    /**
     * 获取指定长度的一次性密码
     * @param length
     * @return
     */
    private static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("`1234567890-=qwertyuiop[]\\asdfghjkl;\'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?1234567890-=qwertyuiop[]\\asdfghjkl;\'zxcvbnm");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();

        for(int i = 0; i < length; ++i) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }

        return sb.toString();
    }

    /**
     * 根据key获取Map中的值
     * @param map
     * @param key
     * @return
     */
    private static String getStringValueFromMap(Map map, Object key) {
        if(map == null) {
            return "";
        } else {
            Object o = map.get(key);
            return o == null?"":o.toString();
        }
    }
}
