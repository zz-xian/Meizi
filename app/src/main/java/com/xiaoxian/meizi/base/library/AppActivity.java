package com.xiaoxian.meizi.base.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/10/18.
 */

public abstract class AppActivity extends BaseActivity {
    //获取首个fragment
    protected abstract BaseFragment getFirstFragment();
    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected int getFragmentViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        if (getIntent() != null) {
            handleIntent(getIntent());
        }

        //避免重复添加Fragment
        //getFragments()——获取所有add进Activity的Fragment
        if (getSupportFragmentManager().getFragments() == null) {
            BaseFragment firstFragment=getFirstFragment();
            if (firstFragment != null) {
                addFragment(firstFragment);
            }
        }
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }
}
