package com.zhuanyi.vjwealth.wallet.mobile.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wzf on 2016/8/15.
 */
public class MoneyCommonUtils {

    /**
     * 处理金额的字符串
     * @param money 1,000
     * @return 1000
     */
    public static String handlerMoneyStr(String money){
        return money.replace(",", "");
    }


    /**
     * 金额除以10000
     * @param money 1,000
     * @return 1000
     */
    public static String divideW(String money){
        return new BigDecimal(handlerMoneyStr(money)).divide(new BigDecimal("10000")).setScale(0).toPlainString();
    }

}
