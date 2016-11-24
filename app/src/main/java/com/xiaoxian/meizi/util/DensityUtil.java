package com.xiaoxian.meizi.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 单位转换类
 * Created by Administrator on 2016/11/4.
 */

public class DensityUtil {
    /**
     * cannot be instantiated
     */
    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     * TypedValue工具类，作为动态容器，存放resource数值
     * applyDimension(int unit, float value, DisplayMetrics metrics)
     * 3个参数分别表示：单位，数值，显示区域的各种属性值
     * @param context,dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,//dpVal*density
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     * @param context,spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,//spVal*scaledDensity
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     * @param context,pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().density);
    }

    /**
     * px转sp
     * @param context,pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
