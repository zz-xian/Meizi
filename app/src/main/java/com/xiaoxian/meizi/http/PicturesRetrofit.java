package com.xiaoxian.meizi.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/10/19.
 */

public class PicturesRetrofit {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            /**
             * 添加一个同步锁，阻塞其它访问线程
             * 如synchronized(object)：给object对象上锁
             */
            synchronized (PicturesRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl("http://gank.io/")//添加请求头
                            .client(PicturesOkHttp.okHttpClient())//封装OkHttp对象
                            .addConverterFactory(GsonConverterFactory.create())//添加解析
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加回调适配器
                            .build();
                }
            }
        }
        return retrofit;
    }
}
