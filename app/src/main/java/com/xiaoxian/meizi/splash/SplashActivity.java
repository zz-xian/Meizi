package com.xiaoxian.meizi.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.base.library.AppActivity;
import com.xiaoxian.meizi.base.library.BaseFragment;

/**
 * Created by Administrator on 2016/11/8.
 */

public class SplashActivity extends AppActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return SplashFragment.getInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getFragmentViewId() {
        return R.id.fragment_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
