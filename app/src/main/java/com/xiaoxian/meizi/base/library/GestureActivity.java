package com.xiaoxian.meizi.base.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/10/18.
 */

public abstract class GestureActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{
    private GestureDetector mGestureDetector;
    private int minDistance = 300;//最小距离
    private int minVelocity = 0;//最小移动速度(单位：像素/秒)

    //获取首个fragment
    protected abstract BaseFragment getFirstFragment();
    //获取Intent
    protected void handleIntent(Intent intent) {

    }

    protected abstract void doFinish();

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
        initGesture();
    }

    private void initGesture() {
        /**
         * new GestureDetector((GestureDetector.OnGestureListener) this))已经过时，不推荐使用
         * 以下写法须知：
         *              第一个this：代表一个Context；
         *              第二个this：代表一个OnGestureListener；
         *              该Activity已经实现OnGestureListener接口。
         */
        mGestureDetector = new GestureDetector(this,this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        /**
         * e1——第一个ACTION_DOWN；e2——最后一个ACTION_MOVE
         * getX()、getY()获得的是相对view触摸位置坐标
         */
        if (e1.getX() - e2.getX() > minDistance && Math.abs(velocityX) > minVelocity) {
            //向左手势(从左到右：e1.getX()>e2.getX())
        } else if (e2.getX() - e1.getX() > minDistance && Math.abs(velocityX) > minVelocity) {
            //向右手势(从右到左：e2.getX()>e1.getX())
            doFinish();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }
}
