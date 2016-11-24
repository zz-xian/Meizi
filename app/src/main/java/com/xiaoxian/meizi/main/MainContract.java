package com.xiaoxian.meizi.main;

import com.xiaoxian.meizi.base.BasePresenter;
import com.xiaoxian.meizi.base.BaseView;
import com.xiaoxian.meizi.data.bean.PicturesBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */

public interface MainContract {
    interface View extends BaseView {
        //对应PicturesDataSource中的onPicturesLoaded()
        void refresh(List<PicturesBean.ResultsEntity> datas);
        void load(List<PicturesBean.ResultsEntity> datas);
        //对应PicturesDataSource中的onDataNotAvailable()
        void showError();
        void showNormal();
    }

    interface Presenter extends BasePresenter {
        void getPictures(int page, int size, boolean isRefresh);
    }
}
