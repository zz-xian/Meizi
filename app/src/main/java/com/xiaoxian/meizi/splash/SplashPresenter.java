package com.xiaoxian.meizi.splash;

import com.xiaoxian.meizi.app.MyApplication;
import com.xiaoxian.meizi.data.bean.PicturesBean;
import com.xiaoxian.meizi.data.source.PicturesDataSource;
import com.xiaoxian.meizi.data.source.PicturesRepository;

/**
 * Created by Administrator on 2016/11/7.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private PicturesRepository mRepository;

    public SplashPresenter(SplashContract.View view) {
        mView = view;
        mRepository = new PicturesRepository();
    }

    @Override
    public void start() {
        mRepository.getPicture(new PicturesDataSource.LoadPicturesCallback() {
            @Override
            public void onPicturesLoaded(PicturesBean picturesBean) {
                //获取首张图片作为启动页
                mView.showPicture(picturesBean.getResults().get(0).getUrl());
                MyApplication.picture = picturesBean.getResults().get(0).getUrl();
            }

            @Override
            public void onDataNotAvailable() {
                mView.showPicture();
            }
        });
    }
}
