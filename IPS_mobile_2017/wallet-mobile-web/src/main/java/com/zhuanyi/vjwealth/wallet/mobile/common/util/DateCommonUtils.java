package com.zhuanyi.vjwealth.wallet.mobile.common.util;

import com.fab.core.exception.service.AppException;
import org.apache.commons.lang.StringUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wzf on 2016/8/15.
 */
public class DateCommonUtils {

    /**
     * @param dateStr 时间戳
     * @param style yyyy-MM-dd，yyyy年MM月dd日
     * @return
     */
    public static String handlerTimeStamp(Object dateStr,String style){
        String str = String.valueOf(dateStr);
        if(StringUtils.isBlank(str)){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(style);
        Long datess = Long.parseLong(str);
        Date date = new Date(datess);
        return format.format(date);
    }

 	/**
     * @param dateStr 日期格式字符串
     * @param style yyyy-MM-dd，yyyy年MM月dd日
     * @return
     */
    public static String handlerDateStr(Object dateStr,String style){
        String str = String.valueOf(dateStr);
        if(StringUtils.isBlank(str)){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(style);

        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    /**
     * @param date 时间类型
     * @param style yyyy-MM-dd，yyyy年MM月dd日
     * @return
     */
    public static String handlerDate(Date date,String style){
        SimpleDateFormat format = new SimpleDateFormat(style);
        return format.format(date);
    }



    /**
     * 在一个日期基础上增加天数
     * @param dateStr 日期格式字符串
     * @param style dateStr的格式类型 （yyyy-MM-dd，yyyy年MM月dd日）
     * @return
     */
    public static Date addDay(String dateStr,String style,int days){
        //基础日期
        if(StringUtils.isBlank(dateStr)){
            return null;
        }
        if(days <= 0){
            days = 0;
        }

        //要解析的日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(style);

        //将str转成Date类型
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //增加天数
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,days);
        date = cal.getTime();

        //返回结果
        return date;
    }

    /**
     * 在一个日期基础上增加天数
     * @param date 日期格式字符串
     * @return
     */
    public static Date addDay(Date date,int days){

        //增加天数
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,days);
        date = cal.getTime();

        //返回结果
        return date;
    }


    public static void main(String[] args) {

    }



}
