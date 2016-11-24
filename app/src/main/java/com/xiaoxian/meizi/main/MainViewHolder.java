package com.xiaoxian.meizi.main;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.data.bean.PicturesBean;

/**
 * 解耦的ViewHolder
 * Created by Administrator on 2016/11/23.
 */

public class MainViewHolder extends BaseViewHolder<PicturesBean.ResultsEntity> {
    private ImageView imageView;

    public MainViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_picture);
        imageView = $(R.id.pictureImg);
    }

    @Override
    public void setData(PicturesBean.ResultsEntity data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
