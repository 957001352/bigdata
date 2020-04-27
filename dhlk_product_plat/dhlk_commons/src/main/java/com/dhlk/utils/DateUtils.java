package com.dhlk.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Content: Date工具类
 * Author:jpdong
 * Date:2020/3/9
 */
public class DateUtils {
    /**
     * 获取当前的时间戳
     *
     * @return long
     */
    public static long getLongCurrentTimeStamp() {
        Date date = new Date();
        long time = date.getTime();
        return time;
    }

    /**
     * 获取当前的时间戳
     *
     * @return timestamp
     */
    public static Timestamp getCurrentTimeStamp() {
        Date date = new Date();
        long time = date.getTime();
        return new Timestamp(time);
    }

    /**
     * 获得当前日期
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return String yyyy-MM-dd
     */
    public static String getToday() {

        return getToday("yyyy-MM-dd");
    }

    /**
     * 获取当前日期
     *
     * @param formatStr 默认 "yyyy-MM-dd"
     * @return String
     */
    public static String getToday(String formatStr) {
        if (CheckUtils.isNull(formatStr)) {
            return "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date d = new Date();
        String re = dateFormat.format(d);
        return re;
    }

   /**
   * 时间戳转换为日期
   * @author      gchen
   * @param millisecond
   * @return  date
   * @date        2020/4/1 12:08
   */
    public static Date MillisecondToDate(long millisecond) {
        Date date = null;
        if(CheckUtils.isNull(millisecond)){
            date = new Date(millisecond);
        }
        return date;
    }

}
