package com.xiaoxian.meizi.util;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 双击退出
 * Created by Administrator on 2016/11/4.
 */

public class ExitActivityUtil extends Activity {
    private long exitTime = 0;

    //重写 onKeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            /**
             * 两秒之内按返回键就会退出（时间差是否在预期值内）
             * 首次按BACK键会弹Toast
             */
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
