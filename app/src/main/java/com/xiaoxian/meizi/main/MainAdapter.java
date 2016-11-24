package com.xiaoxian.meizi.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xiaoxian.meizi.data.bean.PicturesBean;

/**
 * 使用解耦ViewHolder
 * 视图创建和改变全由ViewHolder管理，Adapter仅仅处理面向ViewHolder的逻辑
 * 必须实现OnCreateViewHolder()、OnBindViewHolder()
 *
 * Created by Administrator on 2016/11/23.
 */

public class MainAdapter extends RecyclerArrayAdapter<PicturesBean.ResultsEntity>{
    public static OnMyItemClickListener mOnMyItemClickListener;

    /**
     * 自定义ItemClick回调接口
     */
    public interface OnMyItemClickListener{
        void onItemClick(BaseViewHolder holder, int position);
    }

    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(parent);
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void OnBindViewHolder(final BaseViewHolder holder, final int position) {
        super.OnBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMyItemClickListener != null) {
                    mOnMyItemClickListener.onItemClick(holder, position);
                }
            }
        });
    }

    public void setOnMyItemClickListener(OnMyItemClickListener listener) {
        this.mOnMyItemClickListener = listener;
    }
}
