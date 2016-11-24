package com.xiaoxian.meizi.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/23.
 */

public class CircleImageView extends ImageView {
    private int defaultWidth=0;
    private int defaultHeight=0;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0,0);//传入测量模式
        //不允许传入点9图片
        if (drawable.getClass() == NinePatchDrawable.class) {
            return;
        }

        Bitmap bitmap1=((BitmapDrawable)drawable).getBitmap();
        Bitmap bitmap2=bitmap1.copy(Bitmap.Config.ARGB_8888,true);
        if (defaultWidth == 0) {
            defaultWidth=getWidth();
        }
        if (defaultHeight == 0) {
            defaultHeight=getHeight();
        }
        int radius=(defaultWidth<defaultHeight?defaultWidth:defaultHeight)/2;
        Bitmap roundBitmap=getCroppedRoundBitmap(bitmap2,radius);
        canvas.drawBitmap(roundBitmap,defaultWidth/2-radius,defaultHeight/2-radius,null);//或left=0,top=0
    }

    public Bitmap getCroppedRoundBitmap(Bitmap bitmap,int radius) {
        int diameter=radius*2;//直径
        int bitmapWidth=bitmap.getWidth();
        int bitmapHeight=bitmap.getHeight();

        Bitmap squareBitmap;//方形位图
        int squareWidth,squareHeight;
        int x,y;
        //以下判断位图宽、高确定裁剪区域
        if (bitmapHeight > bitmapWidth) {
            squareWidth=squareHeight=bitmapWidth;
            x=0;
            y=(bitmapHeight-bitmapWidth)/2;
            squareBitmap=Bitmap.createBitmap(bitmap,x,y,squareWidth,squareHeight);
        } else if (bitmapHeight < bitmapWidth) {
            squareWidth=squareHeight=bitmapHeight;
            x=(bitmapWidth-bitmapHeight)/2;
            y=0;
            squareBitmap=Bitmap.createBitmap(bitmap,x,y,squareWidth,squareHeight);
        } else {
            squareBitmap=bitmap;
        }

        Bitmap scaledSrcBmp;//缩放位图
        if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp=Bitmap.createScaledBitmap(squareBitmap,diameter,diameter,true);
        }else {
            scaledSrcBmp=squareBitmap;
        }

        Bitmap finalBitmap=Bitmap.createBitmap(scaledSrcBmp.getWidth(),scaledSrcBmp.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(finalBitmap);

        Paint paint=new Paint();
        Rect rect=new Rect(0,0,scaledSrcBmp.getWidth(),scaledSrcBmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0,0,0,0);//初始填充finalBitmap
        canvas.drawCircle(scaledSrcBmp.getWidth()/2,scaledSrcBmp.getHeight()/2,scaledSrcBmp.getWidth()/2,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//图像混合模式，SRC_IN显示重叠部分
        //两个rect分别表示：所剪辑图片包围框、在Canvas中显示区域（即位置）
        canvas.drawBitmap(scaledSrcBmp,rect,rect,paint);

        bitmap.recycle();
        squareBitmap.recycle();
        scaledSrcBmp.recycle();
        return finalBitmap;
    }
}
