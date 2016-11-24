package com.xiaoxian.meizi.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.about.AboutActivity;
import com.xiaoxian.meizi.base.library.AppActivity;
import com.xiaoxian.meizi.base.library.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/7.
 */

public class MainActivity extends AppActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private long exitTime = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentViewId() {
        return R.id.fragment_main;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return MainFragment.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);//此处Toolbar将取代原本Actionbar
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                // 必须明确使用mailto前缀来修饰邮件地址
                Uri uri = Uri.parse("mailto:a9xiaoxian@gmail.com");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(intent, "请选择邮件应用"));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            /**
             * 两秒之内按返回键就会退出（时间差是否在预期值内）
             * 首次按BACK键会弹Toast
             */
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(mFab, "秘技：按多一次退出", Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();//仅仅释放当前Activity，没有释放资源
                System.exit(0);//退出整个应用程序，释放内存
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
