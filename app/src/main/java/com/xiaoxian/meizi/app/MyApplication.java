package com.xiaoxian.meizi.app;

import android.app.Application;

import com.xiaoxian.meizi.util.LogUtil;
import com.xiaoxian.meizi.util.ToastUtil;

/**
 * Created by Administrator on 2016/11/4.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    public static String picture = "http://t.cn/Rf9INor";

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //配置是否显示log
        LogUtil.isDebug = true;
        //配置是否显示toast
        ToastUtil.isShow = true;
    }

    public static MyApplication getInstance() {
        return myApplication;
    }
}
