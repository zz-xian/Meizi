package com.xiaoxian.meizi.splash;

import com.xiaoxian.meizi.base.BasePresenter;
import com.xiaoxian.meizi.base.BaseView;

/**
 * Created by Administrator on 2016/11/7.
 */

public interface SplashContract {
        interface View extends BaseView<Presenter> {
            void showPicture(String url);//根据图片Url加载图片

            void showPicture();//无效图片加载
        }

        interface Presenter extends BasePresenter {

        }
}
