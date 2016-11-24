package com.xiaoxian.meizi.data.source;

import com.xiaoxian.meizi.data.bean.PicturesBean;

/**
 * Created by Administrator on 2016/10/19.
 */

public interface PicturesDataSource {
    /**
     * 集成图片加载回调接口
     * 回调成功——加载图片、回调失败——数据出错
     */
    interface LoadPicturesCallback {
        void onPicturesLoaded(PicturesBean picturesBean);
        void onDataNotAvailable();
    }

    /**
     * 根据回调结果返回图片数组/首图
     */
    void getPictures(int page, int size, LoadPicturesCallback callback);

    void getPicture(LoadPicturesCallback callback);
}
