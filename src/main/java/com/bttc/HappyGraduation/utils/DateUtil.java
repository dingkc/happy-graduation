package com.bttc.HappyGraduation.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //用于转换时间，提高精度
    static final long UNITS = 1000*60;

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
    * <p>Title: getDateBeforeMonths</p>
    * <p>Description: 获取几个月之前的时间</p>
    * @author liuxf6
    * @date 2018年10月25日
    */
    public static Date getDateBeforeMonths(Date date, Integer months) {
    	Calendar calendar = Calendar.getInstance();    
    	calendar.setTime(date);    
    	calendar.add(Calendar.MONTH, - months);
    	return calendar.getTime();
    }
    
    /**
     * <p>Title: getDateAfterMonths</p>
     * <p>Description: 获取几个月之后的时间</p>
     * @author liuxf6
     * @date 2018年10月31日
     */
    public static Date getDateAfterMonths(Date date, Integer months) {
    	Calendar calendar = Calendar.getInstance();    
    	calendar.setTime(date);    
    	calendar.add(Calendar.MONTH, + months);
    	return calendar.getTime();
    }
    
    /**
     * <p>Title: getDateBeforeDays</p>
     * <p>Description: 获取几天月之前的时间</p>
     * @author liuxf6
     * @date 2018年11月14日
     */
    public static Date getDateBeforeDays(Date date, Integer days) {
    	Calendar calendar = Calendar.getInstance();    
    	calendar.setTime(date);    
    	calendar.add(Calendar.DATE, - days);
    	return calendar.getTime();
    }
    
    /**
     * <p>Title: getDateAfterMonths</p>
     * <p>Description: 获取几天月之后的时间</p>
     * @author liuxf6
     * @date 2018年10月31日
     */
    public static Date getDateAfterDays(Date date, Integer days) {
    	Calendar calendar = Calendar.getInstance();    
    	calendar.setTime(date);    
    	calendar.add(Calendar.DATE, + days);
    	return calendar.getTime();
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
        return formatter.parse(strDate, pos);
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
        return formatter.format(dateDate);
    }
    
    /**
     * <p>Title: getStringNowDate</p>
     * <p>Description: 将短时间格式时间转换为字符串yyyymmddhhmmss</p>
     * @author liuxf6
     * @date 2018年10月24日
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static int intervalTimeYear(Date date) {
        return (int) ((System.currentTimeMillis() - date.getTime()) / (UNITS * 60 * 24 * 30 * 12));
    }

    public static int intervalTimeMonth(Date date) {
        return (int) ((System.currentTimeMillis() - date.getTime()) / (UNITS * 60 * 24 * 30));
    }

    public static int intervalTimeDay(Date date) {
        return (int) ((System.currentTimeMillis() - date.getTime()) / (UNITS * 60 * 24));

    }

    public static int intervalTimeHour(Date date) {
        return (int) ((System.currentTimeMillis() - date.getTime()) / (UNITS * 60));
    }

    public static int intervalTimeMinute(Date date) {
        return (int) ((System.currentTimeMillis() - date.getTime()) / UNITS);
    }

    public static String getIntervalTime(Date date) {
        if (null == date) {
            return null;
        }
        int intervalTime;
        StringBuilder stringBuilder = new StringBuilder();
        intervalTime = intervalTimeYear(date);
        if (intervalTime == 0) {
            intervalTime = intervalTimeMonth(date);
            if (intervalTime == 0) {
                intervalTime = intervalTimeDay(date);
                if (intervalTime == 0) {
                    intervalTime = intervalTimeHour(date);
                    if (intervalTime == 0) {
                        intervalTime = intervalTimeMinute(date);
                        if (intervalTime == 0) {
                            stringBuilder.append("less than a minute");
                        } else {
                            stringBuilder.append(intervalTime).append(" minutes");
                        }
                    } else {
                        stringBuilder.append(intervalTime).append(" hours");
                    }
                } else {
                    stringBuilder.append(intervalTime).append(" days");
                }
            } else {
                stringBuilder.append(intervalTime).append(" months");
            }
        } else {
            stringBuilder.append(intervalTime).append(" years");
        }
        return stringBuilder.toString();
    }
}

