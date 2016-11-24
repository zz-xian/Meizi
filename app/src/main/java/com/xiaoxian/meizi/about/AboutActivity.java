package com.xiaoxian.meizi.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.app.MyApplication;
import com.xiaoxian.meizi.base.library.BaseFragment;
import com.xiaoxian.meizi.base.library.GestureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2016/11/23.
 */

public class AboutActivity extends GestureActivity{
    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar_about)
    Toolbar mAboutToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getFragmentViewId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAboutToolbar.setTitle(R.string.action_about);
        setSupportActionBar(mAboutToolbar);
        mAboutToolbar.setNavigationIcon(R.mipmap.ic_back);
        mAboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        //毛玻璃效果
        Glide.with(this)
                .load(MyApplication.picture)
                .bitmapTransform(new BlurTransformation(this, 15))
                .into(mBackdrop);
    }

    @Override
    protected void doFinish() {
        finishActivity();
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View v) {

    }
}
