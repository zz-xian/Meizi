package com.xiaoxian.meizi.main;

import com.xiaoxian.meizi.data.bean.PicturesBean;
import com.xiaoxian.meizi.data.source.PicturesDataSource;
import com.xiaoxian.meizi.data.source.PicturesRepository;
import com.xiaoxian.meizi.util.LogUtil;

/**
 * Created by Administrator on 2016/11/23.
 */

public class MainPresenter implements MainContract.Presenter {
    public static final String TAG = "MainPresenter";

    private MainContract.View mView;
    private PicturesRepository mRepository;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mRepository = new PicturesRepository();
    }

    @Override
    public void start() {
        getPictures(1, 20, true);
    }

    @Override
    public void getPictures(int page, int size, final boolean isRefresh) {
        LogUtil.d(TAG, "getPictures");
        mRepository.getPictures(page, size, new PicturesDataSource.LoadPicturesCallback() {
            @Override
            public void onPicturesLoaded(PicturesBean picturesBean) {
                if (isRefresh) {
                    mView.refresh(picturesBean.getResults());
                } else {
                    mView.load(picturesBean.getResults());
                }
                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                if (isRefresh) {
                    mView.showError();
                }
            }
        });
    }
}
