package com.xiaoxian.meizi.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * 屏幕相关辅助类
 * Created by Administrator on 2016/11/4.
 */

public class ScreenUtil {
    private ScreenUtil() {
        /*cannot be instantiated*/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //DisplayMetrics用于显示大小，分辨率，字体
        DisplayMetrics metrics = new DisplayMetrics();
        //Display表示屏幕硬件对象
        Display display = wm.getDefaultDisplay();
        //获取真实屏幕尺寸
        display.getMetrics(metrics);
        //返回宽像素
        return metrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获得状态栏高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            /**
             * 原理：从R文件找到dimen内部类
             *      通过反射拿到dimen中status_bar_height的值(即资源id)
             *      通过getResource()获得id对应的值
             */
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Object obj = cls.newInstance();
            int height = Integer.parseInt(cls.getField("status_bar_height").get(obj).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        //getDecorView()获取window最顶层view
        View view = activity.getWindow().getDecorView();
        /**
         * 使用View.buildDrawingCache为view建立相应缓存
         * 这个cache就是一个bitmap对象
         * 可对整个屏幕视图进行截屏并生成Bitmap
         * 避免同时使用setDrawingCacheEnabled(true)，生成两个DrawingCache
         */
        view.buildDrawingCache();
        Bitmap bp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp1 = Bitmap.createBitmap(bp, 0, 0, width, height);
        //记得释放缓存
        view.destroyDrawingCache();
        return bp1;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        Bitmap bp = view.getDrawingCache();

        Rect frame = new Rect();
        //获取程序显示区域，包括标题栏，不包括状态栏
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //获取状态栏高度
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp1 = Bitmap.createBitmap(bp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp1;
    }
}
