package com.xiaoxian.meizi.picture;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.data.bean.PicturesBean;
import com.xiaoxian.meizi.widget.PinchImageView;

import java.util.ArrayList;

/**
 * PagerAdapter与ViewPager配合使用，是ViewPager的适配器
 * 功能：实现控件滑动效果（如广告栏）
 * 必须重写4个方法：
 * 1、instantiateItem(ViewGroup,int)
 * 2、destroyItem(ViewGroup,int,Object)
 * 3、getCount()
 * 4、isViewFromObject(View,Object)
 *
 * Created by Administrator on 2016/11/21.
 */

public class PictureAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<PicturesBean.ResultsEntity> mDatas;
    private LayoutInflater inflater;
    private View mCurrentView;

    public PictureAdapter(Context context, ArrayList<PicturesBean.ResultsEntity> datas) {
        mContext = context;
        mDatas = datas;
        inflater = LayoutInflater.from(this.mContext);
    }

    /**
     * 获得View总数
     * @return
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**
     * 通知PagerAdapter当前ViewPager显示的主要项，提供用户对主要项进行操作的方法
     * @param container ViewPager本身
     * @param position  给定位置
     * @param object  instantiateItem中提交给ViewPager进行保存的实例对象
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    /**
     * 为给定位置创建相应View，创建完成需将View自行添加到container中
     * @param container
     * @param position
     * @return  提交给ViewPager进行保存的实例对象
     */
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final String imgUrl = mDatas.get(position).getUrl();
        View view = inflater.inflate(R.layout.pinch_picture, container, false);
        PinchImageView pinchImageView = (PinchImageView) view.findViewById(R.id.pinchImg);
        Glide.with(mContext)
                .load(imgUrl)
                .thumbnail(0.2f)//产生所加载图片的thumbnail(缩略图)
                .into(pinchImageView);
        container.addView(view);
        return view;
    }

    /**
     * 为给定位置移除相应View
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 确认View与实例对象是否相互对应，ViewPager内部用于获取View对应的ItemInfo
     * @param view
     * @param object
     * @return  是否相互对应
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
