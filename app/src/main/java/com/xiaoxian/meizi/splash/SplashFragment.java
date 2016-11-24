package com.xiaoxian.meizi.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiaoxian.meizi.R;
import com.xiaoxian.meizi.base.library.ActivityManager;
import com.xiaoxian.meizi.base.library.BaseFragment;
import com.xiaoxian.meizi.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/11/7.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View{
    @BindView(R.id.splash)
    ImageView splashIv;

    private Unbinder unbinder;
    private Animation animation;
    private SplashPresenter mPresenter;

    public static SplashFragment getInstance() {
        SplashFragment splashFragment = new SplashFragment();
        return splashFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //传入根布局，使用注解绑定到类成员
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new SplashPresenter(SplashFragment.this);
        initAnim();
    }

    private void initAnim() {
        /**
         * ScaleAnimation(缩放动画)：从屏幕中间向两端从无到有横向延伸
         * 具体参数：
         * fromX——起始view横向所占长度与自身宽度比值
         * toX——结束view横向所占长度与自身宽度比值
         * fromY——起始view纵向所占长度与自身宽度比值
         * toY——结束view纵向所占长度与自身宽度比值
         * 取值（0.0收缩至无、1.0正常无收缩、<1.0收缩、>1.0放大）
         *
         * pivotXType——X轴伸缩模式（ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT）
         * pivotXValue——动画相对于物件X坐标开始位置
         * pivotYType——Y轴伸缩模式（ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT）
         * pivotYValue——动画相对于物件Y坐标开始位置
         * 取值（0.5表示X/Y方向坐标上中点位置，两边同时伸长/收缩）
         */
        /*scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);//动画结束时，停留在最后一帧
        scaleAnimation.setDuration(2500);
        splashIv.startAnimation(scaleAnimation);*/

        //换用资源动画，因为Glide.animate(Animation animation)已经弃用
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        splashIv.startAnimation(animation);

        //缩放动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //getActivity()获取Fragment相关联的Activity实例
                startActivity(new Intent(getActivity(), MainActivity.class));
                //结束启动界面
                ActivityManager.getInstance().finishActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void showPicture(String url) {
        /**
         * Glide构造器至少传入3个参数：
         * with(Context context/Activity activity/Fragment fragment)：自动提取出上下文
         * load(String imageUrl)：链接到网络图片/从Res资源加载/从文件加载
         * into(ImageView targetImageView)：解析图片传给所要显示ImageView
         * 其他参数：
         * animate(resId)：资源动画——可自定义
         * diskCacheStrategy(DiskCacheStrategy.None/SOURCE/RESULT/ALL)：磁盘缓存策略
         * 分别表示：不缓存/只缓存全尺寸图片/只缓存降低分辨率后图片/缓存所有类型图片
         */
        Glide.with(getActivity())
                .load(url)
                .animate(R.anim.scale)
                .into(splashIv);
    }

    @Override
    public void showPicture() {
        //没有网洛连接时启动页
        Glide.with(getActivity())
                .load(R.mipmap.welcome)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .animate(R.anim.scale)
                .into(splashIv);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //对于Fragment，需在onDestroyView中将view设为null，调用unbind()
        unbinder.unbind();
    }
}
