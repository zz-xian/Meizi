package com.xiaoxian.meizi.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SharedPreferences统一管理类
 * 保存key-value于xml文件
 * Created by Administrator on 2016/11/4.
 */

public class SPUtil {
    /**
     * 保存在手机里的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据方法，需要拿到保存数据具体类型，然后根据类型调用不同保存方法
     * @param context,key,object
     */
    public static void put(Context context, String key, Object object) {
        /**
         * getSharedPreferences()
         * 获取多个preferences文件在第一个参数指定名称
         * 第二个参数对文件权限描述：MODE_PRIVATE,MODE_APPEND...
         */
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor = sp.edit();
        //保存键值对
        if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据方法，根据默认值得到保存数据具体类型，然后调用相对方法获取值
     * @param context,key,object
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param context,key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context,key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有键值对
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method method = findApplyMethod();

        /**
         * 反射查找apply方法
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class cls = SharedPreferences.Editor.class;
                return cls.getMethod("apply");
            } catch (NoSuchMethodException e) { }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (method != null) {
                    //invoke:调用
                    method.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) { }
            catch (IllegalAccessException e) { }
            catch (InvocationTargetException e) { }
            editor.commit();
        }
    }
}
