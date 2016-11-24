package com.xiaoxian.meizi.base.library;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/18.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    /**
    * 定义操作，为下面的onCreateView()服务
    */
    //获取Fragment布局文件Id
    protected abstract int getLayoutId();
    //初始化视图
    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity=(BaseActivity)context;
    }

    //添加Fragment，调用BaseActivity中的addFragment()
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除Fragment，调用BaseActivity中的removeFragment()
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //承接上面
        View view=inflater.inflate(getLayoutId(),container,false);
        initView(view,savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
