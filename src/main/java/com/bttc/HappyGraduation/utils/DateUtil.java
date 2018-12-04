package com.bttc.HappyGraduation.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  
 * <p>Title: DateUtil</p>  
 * <p>Description: 时间转换工具类</p>  
 *
 * @author liuxf6  
 * @date 2018年4月17日  
 */
public class DateUtil {

    private DateUtil() {

    }

    /**
     *  
     *  * <p>Title: getNowDate</p>  
     *  * <p>Description: 获取现在时间 </p>  
     *  * @return  返回时间类型Date
     *  
     */
    public static Date getNowDate() {
        return new Date();
    }


    /**
     *  
     *  * <p>Title: getStringToday</p>  
     *  * <p>Description: 获取现在时间类型为yyyymmdd</p>  
     *  * @return  String
     *  
     */
    public static String getStringToday() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);

    }

    /**
     *  
     *  * <p>Title: getDateInHarfAnHour</p>  
     *  * <p>Description: 获取半个小时后的时间</p>  
     *  * @return  String
     *  
     */
    public static Date getDateInHarfAnHour() {
        long curren = System.currentTimeMillis();
        curren += 30 * 60 * 1000;
        return new Date(curren);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDateShort(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrShort(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
}

