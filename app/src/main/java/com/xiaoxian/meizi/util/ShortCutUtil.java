package com.xiaoxian.meizi.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.xiaoxian.meizi.R;

/**
 * 创建、删除快捷图标
 * final修饰的类是最终类，不被继承
 * 需要权限:
 * com.android.launcher.permission.INSTALL_SHORTCUT
 * com.android.launcher.permission.UNINSTALL_SHORTCUT
 * Created by Administrator on 2016/11/4.
 */

public final class ShortCutUtil {
    /**
     * Don't let anyone instantiate this class
     */
    private ShortCutUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 检测是否存在快捷方式
     * @param activity
     * @return
     */
    public static boolean hasShortcut(Activity activity) {
        boolean isInstallShortcut = false;
        //获取内容解析器实例
        final ContentResolver cr = activity.getContentResolver();
        final String AUTHORITY = "com.android.launcher.settings";
        //从com.android.launcher的launcher.db中favorites表读取快捷方式
        final Uri uri = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(uri,
                new String[]{"title", "iconResource"},//列名列表(重点：找出iconResource)
                "title=?",
                new String[]{activity.getString(R.string.app_name).trim()},
                null);//sortOrder
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
        }
        c.close();
        return isInstallShortcut;
    }

    /**
     * 创建桌面快捷方式
     * @param activity,res
     */
    public static void addShortcut(Activity activity, int res) {
        Intent i = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式名称
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));
        // 不允许重复创建(duplicate:重复)
        i.putExtra("duplicate", false);

        Intent i2 = new Intent(Intent.ACTION_MAIN);
        //setClassName():通过字符串运行时定位activity
        i2.setClassName(activity, activity.getClass().getName());
        // 快捷方式动作
        i.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i2);

        // 快捷方式图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity, res);
        i.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(i);
    }

    /**
     * 删除程序快捷方式
     * @param activity
     */
    public static void delShortcut(Activity activity) {
        Intent i = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 快捷方式名称
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));

        Intent i2 = new Intent(Intent.ACTION_MAIN);
        i2.setClassName(activity, activity.getClass().getName());
        i.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i2);

        activity.sendBroadcast(i);
    }
}
