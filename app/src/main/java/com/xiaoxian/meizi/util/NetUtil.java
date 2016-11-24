package com.xiaoxian.meizi.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关辅助类
 * Created by Administrator on 2016/11/4.
 */

public class NetUtil {
    private NetUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //NetworkInfo.State嵌套类
                if (networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是WIFI连接
     * @param context
     * @return
     */
    public static boolean isWIFI(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        //ComponentName(String pkg, String cls)
        ComponentName cn = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
        //set the component to handle the intent
        intent.setComponent(cn);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);//默认requestCode=0
    }
}
