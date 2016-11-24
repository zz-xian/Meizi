package com.xiaoxian.meizi.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.xiaoxian.meizi.app.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * bitmap处理工具类
 * Created by Administrator on 2016/11/4.
 */

public class BitmapUtil {
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                // 取得drawable固有宽度、高度(单位dp)
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                // 取得drawable颜色格式(不透明度)
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        /**
         * 以bitmap对象创建一个画布，将内容绘制在bitmap上面
         */
        Canvas canvas = new Canvas(bitmap);
        //left,top,right,bottom
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //将drawable内容绘制在canvas内部矩形区域(上面设置)
        drawable.draw(canvas);
        return bitmap;
    }

    public static boolean saveBitmap(Bitmap bitmap, String dir, String name, boolean isShowPhotos) {
        File path = new File(dir);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path + "/" + name);
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            /**
             * compress(Bitmap.CompressFormat format, int quality, OutputStream stream)
             * quality(0-100)：最小/最大压缩size
             */
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 其次把文件插入到系统图库
        if (isShowPhotos) {
            try {
                //insertImage(ContentResolver cr, String imagePath, String name, String description) 
                MediaStore.Images.Media.insertImage(MyApplication.getInstance().getContentResolver(), file.getAbsolutePath(), name, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            MyApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        }
        return true;
    }
}
