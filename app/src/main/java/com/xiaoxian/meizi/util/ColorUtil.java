package com.xiaoxian.meizi.util;

import android.graphics.Color;

/**
 * 颜色处理工具类
 * Created by Administrator on 2016/11/4.
 */

public class ColorUtil {
    /**
     * 颜色加深处理
     * @param RGBValues
     *         alpha（透明度）、red（红）、green（绿）、blue（蓝）
     *         例如：#FFAABBCC,每种颜色占一个字节(8位)，值域0~255
     * 下面使用移位方法可以得到每种颜色的值，将每种颜色的值减小再合成RGB颜色，颜色就会变深
     * @return
     */
    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        //Math.floor()返回不大于的最大整数
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}
