package com.xiaoxian.meizi.util;

import android.util.Log;

/**
 * Log统一管理类
 * Created by Administrator on 2016/11/4.
 */

public class LogUtil {
    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印log，可在application的onCreate()中初始化
    public static boolean isDebug = true;
    private static final String TAG = "LogUtil";

    // 下面四个默认tag的函数
    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);//verbose
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);//debug
    }

    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);//info
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);//error
    }

    // 下面传入自定义tag的函数
    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }
}
