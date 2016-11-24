package com.xiaoxian.meizi.base.library;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/10/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    //布局文件Id
    protected abstract int getContentViewId();
    //引用子布局Fragment的Id
    protected abstract int getFragmentViewId();
    //$：代表方法名
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    //添加Fragment
    protected void addFragment(BaseFragment fragment) {
            /**
            * replace(id,实例,tag)——tag设为fragment的底层类
            * getClass()——获得当前对象类型
            * getSimpleName()——返回源代码中底层类的简称
            * commitAllowingStateLoss()——允许状态值（Fragment跳转时系统保存的参数）丢失
            */
        if (fragment!=null){
            getSupportFragmentManager()//获取SupportFragmentManager对象
                    .beginTransaction()//开启事务
                    .replace(getFragmentViewId(),fragment,fragment.getClass().getSimpleName())//加入fragment
                    .addToBackStack(fragment.getClass().getSimpleName())//加入堆栈
                    .commitAllowingStateLoss();//提交事务
        }
    }

    //移除Fragment
    protected void removeFragment() {
        /**
        * 判断堆栈总数是否>1，是就出栈
        * 出栈原理：将事务操作插入到SupportFragmentManager操作队列，当轮询到该事务时才去执行
        * 注意：SupportFragmentManager处理的是FragmentTransaction而非Fragment
        *       一个Transaction可以包含一或多个Fragment相关操作
        */
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键的返回事件（实现再按一次退出程序）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //在return super...之前执行，确保能够finish
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
