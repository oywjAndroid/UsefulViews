package com.oywj.usefulviews.ui.basic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.oywj.usefulviews.presenter.Presenter;
import com.oywj.usefulviews.ui.basic.swipeback.SwipeBackActivity;
import com.oywj.usefulviews.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Functions：
 * 1.简化findViewById()操作。
 * 2.创建UI逻辑层与数据逻辑层的交互通道。
 * 3.提供Activity处于后台时出现的异常状况的处理机制。
 * 4.方便日间与夜间模式的切换。
 * 5.封装右滑finish当前Activity返回上级页面的功能。
 * 6.释放可能导致Activity出现泄漏的资源(Handler、AsyncTask等)。
 * <p/>
 * BasicActivity Template method:
 * <p/>
 * {模版方法回调的顺序、作用}
 * <p/>
 * 1.{@link #onGenerateLayoutResType()}:
 * 该方法被回调用来指定contentView被创建的类型（LayoutResID:通过布局文件指定、LayoutResView:通过View对象指定）。
 * <p/>
 * 2.{@link #onGenerateLayoutResID()}:
 * 通过设定LayoutResID来创建contentView；
 * {@link #onGenerateLayoutResView()}:
 * 通过返回View对象直接设置contentView。（这两个方法只会有一个被回调）
 * <p/>
 * 3.{@link #onObtainData()}:
 * 执行获取数据，如果savedInstanceState有保存数据就会回调{@link #isNeedDataOfSavedInstanceState(Bundle)}，
 * 可以选择在这个方法当中数据的恢复，恢复数据之后可根据结果返回相应的boolean值，决定{@link #onObtainData()}是否还会被调用。
 * <p/>
 * 4.{@link #onObtainPresenter()}:
 * 如需要使用MVP设计模式，可以复写此方法来创建对应的Presenter对象。
 * <p/>
 * 5.{@link #onBindDataToViews()}:
 * 执行数据绑定的操作
 */
public abstract class BasicActivity<T extends Presenter> extends SwipeBackActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndBindLayoutRes();
        checkAndBindData(savedInstanceState);
    }

    private void checkAndBindLayoutRes() {
        switch (onGenerateLayoutResType()) {
            case LayoutResID:
                setContentView(onGenerateLayoutResID());
                break;
            case LayoutResView:
                setContentView(onGenerateLayoutResView());
                break;
        }
        ButterKnife.bind(this);
    }

    private void checkAndBindData(Bundle savedInstanceState) {
        LogUtils.d("BasicActivity", "checkAndBindData");
        if (savedInstanceState == null) {
            // 到这里说明Activity是第一次created或者是之前被关闭时并没有保存实例状态数据
            // ...执行用户获取数据的逻辑
            LogUtils.d("BasicActivity", "savedInstanceState = {" + null + "}");
            onObtainData();
        } else {
            // 这说明先前Activity被关闭的时候有进行实例状态的数据保存，就说明需要恢复之前的状态
            // ...先从savedInstanceState中将保存的数据进行恢复
            LogUtils.d("BasicActivity", "savedInstanceState = {" + savedInstanceState + "}");
            if (!isNeedDataOfSavedInstanceState(savedInstanceState)) {
                onObtainData();
            }
        }
        onObtainPresenter();
        BasicApplication.getInstance().addActivity(this);
        onBindDataToViews();
    }


    /**
     * {#isNeedDataOfSavedInstanceState}只有在savedInstanceState实例不为空的情况下才会调用此方法，
     * 复写此方法可以选择恢复其中的数据。
     *
     * @param savedInstanceState onSaveInstanceState()方法中保存的数据。
     * @return true：恢复的数据就是想要的数据，不需要再重新获取数据(不会调用{@link #onObtainData})；
     * false:需要再调用{@link #onObtainData}重新获取数据。
     */
    protected boolean isNeedDataOfSavedInstanceState(Bundle savedInstanceState) {
        return false;
    }


    /**
     * 创建该Activity对应的Presenter对象
     * Demo:{
     * mPresenter = new PresenterImpl();
     * mPresenter.setUiProxy(new UiProxyImpl);//UiProxyImpl 使用内部类避免新建过多的接口文件
     * }
     */
    protected void onObtainPresenter() {
        // TODO 如果需要使用MVP模式就重写此方法
    }

    /**
     * 获取需要的数据，希望在初始获取数据操作能够在次方法中进行。
     * 在此访方法中获取数据能够降低与{@link #onBindDataToViews()}的耦合性。
     */
    protected void onObtainData() {

    }

    /**
     * 绑定数据到视图等操作。
     */
    protected abstract void onBindDataToViews();

    /**
     * {#onRestoreInstanceState}在onStart()之后被调用，但是一般在onCreate()方法中直接处理就行。
     *
     * @param savedInstanceState onSaveInstanceState()中保存的数据。
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * {#onSaveInstanceState}当Activity的所在进程处于后台进程的时候，会回调此方法方便用户保存实例状态信息。
     *
     * @param outState 用户保存数据的Bundle对象。
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * {#onPause}在当前Activity跳转到另外一个Activity的时候会先回调此方法
     * 因此在这个方法中可以停止但当前Activity的动画，结束消耗cpu性能的操作，持久化数据等操作。
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BasicApplication.getInstance().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.destroyConsumables();
        }
    }

    /**
     * 创建Activity对应的布局资源参数
     * 如果是需要调用 {@link #setContentView(int layoutResID)}方法，就请Override 此方法。
     *
     * @return 布局资源引用
     */
    protected int onGenerateLayoutResID() {
        return -1;
    }

    /**
     * 创建Activity对应的ContentView。
     * 如果是需要调用 {@link #setContentView(View view)}方法，就请Override 此方法。
     *
     * @return contentView
     */
    protected View onGenerateLayoutResView() {
        return new View(this);
    }

    /**
     * 指定想要setContentView的数据类型
     * {@link # LayoutResType.LayoutResID} 资源ID类型
     * {@link # LayoutResType.LayoutResView} View类型
     *
     * @return LayoutResType
     */
    protected LayoutResType onGenerateLayoutResType() {
        return LayoutResType.LayoutResID;
    }


}
