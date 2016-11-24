package com.xiaoxian.meizi.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * app相关辅助类
 * Created by Administrator on 2016/11/4.
 */

public class AppUtil {
    private AppUtil() {
        /* cannot be instantiated(实例化)*/
        //不支持功能异常
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        /**
         * PackageManager、PackageInfo:应用包相关类
         */
        PackageManager packageManager = context.getPackageManager();
        try {
            //getPackageInfo(String packageName, int flags)
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            //labelRes对应android:label(获得label在R文件中值)
            int labelRes = packageInfo.applicationInfo.labelRes;
            //getString(int id)
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本Code信息
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
