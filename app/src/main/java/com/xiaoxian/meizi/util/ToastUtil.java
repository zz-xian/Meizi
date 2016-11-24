package com.xiaoxian.meizi.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast管理类
 * Created by Administrator on 2016/11/4.
 */

public class ToastUtil {
    public static boolean isShow = true;//是否显示Toast

    /*cannot be instantiated*/
    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     * @param context,message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     * @param context,message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     * @param context,message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     * @param context,message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     * @param context,message,duration
     */
    public static void showCustom(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     * @param context,message,duration
     */
    public static void showCustom(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }
}
