package com.xiaoxian.meizi.picture;

import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.app.Constants;
import com.xiaoxian.meizi.base.library.BaseFragment;
import com.xiaoxian.meizi.data.bean.PicturesBean;
import com.xiaoxian.meizi.util.BitmapUtil;
import com.xiaoxian.meizi.widget.PinchImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/21.
 */

public class PictureFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.rootView)
    LinearLayout mRootView;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private PictureAdapter mAdapter;

    private ArrayList<PicturesBean.ResultsEntity> datas;
    private int current;

    private Unbinder unbinder;
    private OnPictureChange mListener;

    /**
     * 为使Fragment与Activity交互，可在Fragment中定义一个接口并在Activity中实现
     * Fragment可在onAttach()中获取接口实例并调用接口方法与Activity交互
     *
     * 图片变换接口，用于切换图片时改变状态栏颜色
     */
    public interface OnPictureChange {
        void change(int color);
    }

    /**
     * 获取PictureFragment实例，同时传入参数
     * @param datas
     * @param current
     * @return
     */
    public static PictureFragment newInstance(ArrayList<Parcelable> datas, int current) {
        Bundle bundle = new Bundle();
        PictureFragment pictureFragment = new PictureFragment();
        //传入序列化图片数据以及当前项
        bundle.putParcelableArrayList("pictures", datas);
        bundle.putInt("current", current);
        pictureFragment.setArguments(bundle);
        return pictureFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picture;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnPictureChange) context;
        } catch (ClassCastException e) {
            //类型转换异常，当前者的域小于后者时出现（比如子类对象=父类对象）
            //OnPictureChange必须实现OnHeadlineSelectedListener以使用其实例mCallBack调用相应方法提供信息给Activity
            throw new ClassCastException(context.toString() + "must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            datas = bundle.getParcelableArrayList("pictures");
            current = bundle.getInt("current");
        }
        mAdapter = new PictureAdapter(mActivity, datas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(current);
        //注意setOnPageChangeListener()已过期，不推荐使用
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getColor();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 根据图片获得主题色
     */
    private void getColor() {
        PinchImageView imageView = getCurrentImageView();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());
        /**
         * Palette：从图像中提取图片颜色，融入UI之中，经常与Fragment、ViewPager搭配使用
         * 可提取颜色有6种：Vibrant(有活力的)/Vibrant dark/Vibrant light/Muted(柔和的)/Muted dark/Muted light
         */
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            //图片处理可能耗时，使用异步接口回调
            @Override
            public void onGenerated(Palette palette) {
                //提取亮色作为主题色
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant == null) {
                    return;
                }
                mListener.change(vibrant.getRgb());
            }
        });
    }

    private PinchImageView getCurrentImageView() {
        View currentItem = mAdapter.getPrimaryItem();
        if (currentItem == null) {
            return null;
        }
        PinchImageView imageView = (PinchImageView) currentItem.findViewById(R.id.pinchImg);
        if (imageView == null) {
            return null;
        }
        return imageView;
    }

    public void savePicture() {
        String imgUrl = datas.get(mViewPager.getCurrentItem()).getUrl();
        PinchImageView imageView = getCurrentImageView();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(imageView.getDrawable());

        //创建Observable对象，等价create()
        //substring()：截取字符串，可以指定开始与结束位置
        //lastIndexOf()：从字符串末尾开始检索(最后出现位置)
        Observable.just(BitmapUtil.saveBitmap(bitmap, Constants.dir, imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.length()), true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {//支持不完整定义回调，形如ActionX接口
                    @Override
                    public void call(Boolean isSuccess) {
                        if (isSuccess) {
                            //类似Toast，但比Toast更加灵活，显示在所有屏幕其它元素之上（屏幕最顶层）
                            Snackbar.make(mRootView, "下载成功喵~", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mRootView, "下载出错喵~", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void sharePicture() {
        PinchImageView imageView = getCurrentImageView();
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            Bitmap bitmap = BitmapUtil.drawableToBitmap(drawable);

            Observable.just(BitmapUtil.saveBitmap(bitmap, Constants.dir, "share.jpg", false))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean isSuccess) {
                            if (isSuccess) {
                                //由文件获得Uri
                                Uri imgUri = Uri.fromFile(new File(Constants.dir + "/share.jpg"));
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                                //指定分享类型为image
                                shareIntent.setType("image/*");
                                //自定义Chooser：匹配Intent（包括action,category,data）时，出现多个结果，系统会显示一个Dialog供用户选择
                                startActivity(Intent.createChooser(shareIntent, "分享图片至"));
                            } else {
                                Snackbar.make(mRootView, "分享出错喵~", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
