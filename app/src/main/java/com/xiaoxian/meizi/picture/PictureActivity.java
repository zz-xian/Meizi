package com.xiaoxian.meizi.picture;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.base.library.AppActivity;
import com.xiaoxian.meizi.base.library.BaseFragment;
import com.xiaoxian.meizi.util.ColorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/22.
 */

public class PictureActivity extends AppActivity implements PictureFragment.OnPictureChange {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    PictureFragment mPictureFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_picture;
    }

    @Override
    protected int getFragmentViewId() {
        return R.id.fragment_picture;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        mPictureFragment = PictureFragment.newInstance(getIntent().getParcelableArrayListExtra("pictures")
                , getIntent().getIntExtra("current", 0));
        return mPictureFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbar.setTitle(R.string.title_picture);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            mPictureFragment.savePicture();
            return true;
        } else if (id == R.id.action_share) {
            mPictureFragment.sharePicture();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void change(int color) {
        mToolbar.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //状态栏颜色加深
            window.setStatusBarColor(ColorUtil.colorBurn(color));
            window.setNavigationBarColor(ColorUtil.colorBurn(color));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
