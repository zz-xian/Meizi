package com.xiaoxian.meizi.data.source.remote;

import com.xiaoxian.meizi.data.bean.PicturesBean;
import com.xiaoxian.meizi.data.source.PicturesDataSource;
import com.xiaoxian.meizi.http.PicturesRetrofit;
import com.xiaoxian.meizi.http.PicturesService;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/19.
 */

public class RemotePicturesDataSource implements PicturesDataSource {
    @Override
    public void getPictures(int page, int size, final LoadPicturesCallback callback) {
        /**
         * 问题：为毛传入一个PicturesService接口的Class对象却又返回一个PicturesService对象？
         * 解答：深入源码分析，create()实际返回一个Proxy.newProxyInstance动态代理对象
         *       何为动态代理？比方：美团下单时需判断用户是否登录（跳转登录界面）
         *       即service是一个动态代理对象，当service调用getPictures()时会被动态代理拦截，
         *       然后调用Proxy.newProxyInstance()中的InvocationHandler对象，其invoke()传入3个参数：
         *           （1）Object proxy：代理对象
         *           （2）Method method：调用方法，即getPictures()
         *           （3）Object...args：方法参数
         *       Retrofit会用java反射获取getPictures()注解信息，配合args，创建一个ServiceMethod对象，
         *       传入Method对象，最终解析生成一个Request，最后返回一个Call对象（默认实现OkHttpCall）
         *
         * 这里不用默认Call，使用Observable
         */
        PicturesService service = PicturesRetrofit.getRetrofit().create(PicturesService.class);
        Observable<PicturesBean> observable = service.getPictures("福利", size, page);
        observable
                //切换线程，实现异步
                .subscribeOn(Schedulers.io())//指定Observable.OnSubscribe被激活时所处线程
                .observeOn(AndroidSchedulers.mainThread())//指定Subscriber所运行的线程
                .subscribe(new Observer<PicturesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(PicturesBean picturesBean) {
                        callback.onPicturesLoaded(picturesBean);
                    }
                });
    }

    /**
     * 返回首张图片，后面会用到
     */
    @Override
    public void getPicture(final LoadPicturesCallback callback) {
        getPictures(1,1,callback);
    }
}
