package com.oywj.usefulviews.ui.basic;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oywj.usefulviews.presenter.Presenter;
import com.oywj.usefulviews.ui.basic.swipeback.SwipeBackFragment;
import com.oywj.usefulviews.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * {#BasicFragment}作为Fragment的基类，同样需要承担与BasicActivity类似的功能模块。
 * BasicFragment Template method:
 * <p/>
 * 模版方法回调的顺序、作用:
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
public abstract class BasicFragment<P extends Presenter> extends SwipeBackFragment {

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = checkAndBindLayoutRes(inflater, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkAndBindData(savedInstanceState);
    }

    private View checkAndBindLayoutRes(LayoutInflater inflater, ViewGroup container) {
        View rootView = null;
        switch (onGenerateLayoutResType()) {
            case LayoutResID:
                rootView = inflater.inflate(onGenerateLayoutResID(), container, false);
                break;
            case LayoutResView:
                rootView = onGenerateLayoutResView();
                break;
        }
        return rootView;
    }

    private void checkAndBindData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            LogUtils.d("BasicActivity", "savedInstanceState = {" + null + "}");
            onObtainData();
        } else {
            LogUtils.d("BasicActivity", "savedInstanceState = {" + savedInstanceState + "}");
            if (!isNeedDataOfSavedInstanceState(savedInstanceState)) {
                onObtainData();
            }
        }
        onObtainPresenter();
        onBindDataToViews();
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
     * 创建该Fragment对应的Presenter对象
     * Demo:{
     * mPresenter = new PresenterImpl();
     * mPresenter.setUiProxy(new UiProxyImpl);//UiProxyImpl 使用内部类避免新建过多的接口文件
     * }
     */
    protected void onObtainPresenter() {
        // TODO 如果需要使用MVP模式就重写此方法
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

    /**
     * 创建Fragment对应的布局资源参数
     *
     * @return 布局资源引用
     */
    protected int onGenerateLayoutResID() {
        return -1;
    }

    /**
     * 创建AFragment对应的RootView。
     *
     * @return rootView
     */
    protected View onGenerateLayoutResView() {
        return new View(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroyConsumables();
        }
    }
}
