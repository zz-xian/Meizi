package com.xiaoxian.meizi.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/10/19.
 */

public class PicturesOkHttp {
    public static OkHttpClient okHttpClient() {
        /**
         * 设置连接、读、写时间
         */
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
