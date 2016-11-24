package com.xiaoxian.meizi.base.library;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Administrator on 2016/10/18.
 */

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;
    static {
        instance = new ActivityManager();
    }

    //获取ActivityManager实例
    public static ActivityManager getInstance() {
        return instance;
    }

    /**
     * 添加指定Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        //lastElement：栈顶活动
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束全部Activity
     */
    public void finishAllActivity() {
        for (int i=0;i<activityStack.size();i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        //清空堆栈
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void Exit() {
        /**
         * 官方不推荐用android.app.ActivityManager的restartPackage()
         * 直接调用System.exit(0)退出
         */
        finishAllActivity();
        System.exit(0);
    }
}
