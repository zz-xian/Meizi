package com.xiaoxian.meizi.base.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */

public abstract class SimpleBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> datas = new ArrayList<T>();

    public SimpleBaseAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 刷新适配器数据
     */
    public void refreshDatas(List<T> datas) {
        this.datas = datas;
        /**
         * notifyDataSetChange()在框架中最终由requestLayout()进行界面重绘
         * 使用时先设置完View相关属性再调用
         */
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas != null ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 注意设置为abstract
     */
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
