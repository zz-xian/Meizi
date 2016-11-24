package com.xiaoxian.meizi.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.base.library.BaseFragment;
import com.xiaoxian.meizi.data.bean.PicturesBean;
import com.xiaoxian.meizi.picture.PictureActivity;
import com.xiaoxian.meizi.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/11/23.
 */

public class MainFragment extends BaseFragment implements MainContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener {
    public static final String TAG = "MainFragment";

    /**
     * ViewStub直接继承View，当inflate布局时，ViewStub本身会被解析，且占据内存控件
     * 特点：
     * （1）相比其他控件，占据内存小
     * （2）有一layout属性，指向ViewStub本身会被替换掉的布局文件
     * （3）本身不可见，其setVisibility(View.VISIBLE)，若是首次使用，自动inflate其指向的布局文件，并替换ViewStub本身；再次使用才是设置可见性
     * （4）特定情况ViewStub指向布局才需被inflate（注意：替换后原来布局文件就没ViewStub控件，多次inflate会报错）
     */
    @BindView(R.id.recycler_view)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.network_error_layout)
    ViewStub mNetworkErrorLayout;

    private ArrayList<PicturesBean.ResultsEntity> data;
    private MainAdapter mAdapter;

    private MainPresenter mPresenter;
    private int page = 1;
    private int size = 20;

    private View networkErrorView;
    private Unbinder unbinder;

    public static MainFragment getInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new MainPresenter(this);
        initRecyclerView();
        //初始化数据
        mPresenter.start();
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        //交错网格实现瀑布流：StaggeredGridLayoutManager(int spanCount, int orientation)——设置列数、方向
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new MainAdapter(getContext());
        mRecyclerView.setAdapterWithProgress(mAdapter);

        mAdapter.setMore(R.layout.more_layout, this);
        mAdapter.setNoMore(R.layout.no_more_layout);
        mAdapter.setError(R.layout.network_error);

        mAdapter.setOnMyItemClickListener(new MainAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder holder, int position) {
                Intent intent = new Intent(mActivity, PictureActivity.class);
                intent.putParcelableArrayListExtra("pictures", data);
                intent.putExtra("current", position);
                //ActivityOptionsCompat类：启动Activity和添加动画（全版本稳定运行）
                //makeScaleUpAnimation：实现新的Activity从某个固定坐标以某个大小扩大至全屏
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(holder.itemView, holder.itemView.getWidth() / 2, holder.itemView.getHeight() / 2, 0, 0);
                startActivity(intent,options.toBundle());
            }
        });
        mRecyclerView.setRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.getPictures(1, size, true);
        page = 1;
    }

    @Override
    public void onMoreShow() {
        if (data.size() % 20 == 0) {
            LogUtil.d(TAG, "onLoadMore");
            page++;
            mPresenter.getPictures(page, size, false);
        }
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void refresh(List<PicturesBean.ResultsEntity> datas) {
        data.clear();
        data.addAll(datas);
        mAdapter.clear();
        mAdapter.addAll(datas);
    }

    @Override
    public void load(List<PicturesBean.ResultsEntity> datas) {
        data.addAll(datas);
        mAdapter.addAll(datas);
    }

    @Override
    public void showError() {
        mRecyclerView.showError();
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
        networkErrorView = mNetworkErrorLayout.inflate();
    }

    @Override
    public void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
