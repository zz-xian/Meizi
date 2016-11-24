package com.xiaoxian.meizi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类
 * Created by Administrator on 2016/11/4.
 */

public class DateUtil {
    /**
     * 英文简写如：2016
     */
    public static String FORMAT_Y = "yyyy";

    /**
     * 英文简写 如：00:00
     */
    public static String FORMAT_HM = "HH:mm";

    /**
     * 英文简写 如：12-1 00:00
     */
    public static String FORMAT_MDHM = "MM-dd HH:mm";

    /**
     * 英文简写 如：2016-12-01
     */
    public static String FORMAT_YMD = "yyyy-MM-dd";

    /**
     * 英文全称 如：2016-12-01 00:00
     */
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 英文全称 如：2016-12-01 00:00:00
     */
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 中文简写 如：2016年12月01日
     */
    public static String FORMAT_YMD_CN = "yyyy年MM月dd日";

    /**
     * 中文简写 如：2016年12月01日 00时
     */
    public static String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

    /**
     * 中文简写 如：2016年12月01日 00时00分
     */
    public static String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分";

    /**
     * 中文全称 如：2016年12月01日 00时00分00秒
     */
    public static String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static Calendar c = null;
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        //为null则用默认FORMAT
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date d = null;
        try {
            // SimpleDateFormat完成日期的显示格式化(日期格式转换)
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            // 将给定字符串中日期提取出来
            d = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);
    }

    public static Calendar str2Calendar(String str, String format) {
        Date d = str2Date(str, format);
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    public static String date2Str(Calendar c) {
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" +c.get(Calendar.DAY_OF_MONTH) + "-" +
                c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
    }

    /**
     * 获得当前日期字符串格式
     * @param format
     * @return
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }

    /**
     * @param time
     * @return 当前的天
     */
    public static String getDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * @param time
     * @return 格式到秒
     */
    public static String getMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);
    }

    /**
     * @param time
     * @return 格式到毫秒
     */
    public static String getSMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);
    }

    /**
     * 在日期上增加数个整月
     * @param d,n
     * @return
     */
    public static Date addMonth(Date d, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    /**
     * 在日期上增加天数
     * @param d,n
     * @return
     */
    public static Date addDay(Date d, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    /**
     * 获取距今某一小时时刻
     * @param format
     * @param h 例如：-1上一小时，1下一小时
     * @return 日期字符串格式
     */
    public static String getNextHour(String format, int h) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = new Date();
        d.setTime(d.getTime() + h * 60 * 60 * 1000);
        return sdf.format(d);
    }

    /**
     * 获取时间戳
     * @return
     */
    public static String getTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_FULL);
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    /**
     * 返回月
     * @param d
     * @return
     */
    public static int getMonth(Date d) {
        c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日
     * @param d
     * @return
     */
    public static int getDay(Date d) {
        c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     * @param d
     * @return
     */
    public static int getHour(Date d) {
        c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     * @param d
     * @return
     */
    public static int getMinute(Date d) {
        c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     * @param d
     * @return
     */
    public static int getSecond(Date d) {
        c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒
     * @param d
     * @return
     */
    public static long getMillis(Date d) {
        c = Calendar.getInstance();
        c.setTime(d);
        return c.getTimeInMillis();
    }

    /**
     * 获得默认格式
     * @return 默认格式
     */
    public static String getDatePattern() {
        return FORMAT_YMDHMS;
    }

    /**
     * 使用预设格式提取字符串日期
     * @param strDate
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     * @param strDate
     * @param format
     * @return 字符串日期
     */
    public static Date parse(String strDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 默认格式字符串距今天数
     * @param strDate
     * @return
     */
    public static int countDays(String strDate) {
        //今天日期
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(strDate));
        //指定日期
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 用户格式字符串距今天数
     * @param strDate,format
     * @return
     */
    public static int countDays(String strDate, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(strDate, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }
}
