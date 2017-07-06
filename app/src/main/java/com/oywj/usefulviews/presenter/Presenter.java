package com.oywj.usefulviews.presenter;

import com.oywj.usefulviews.ui.basic.UiProxy;

import java.lang.ref.WeakReference;

/**
 * Presenter是UI逻辑层与Data逻辑层的交互的通道(规定UI和Data不直接进行数据的交互)。
 * 1.Presenter在UI和Data之间是双向的。
 * 2.获取数据的方式(Http、Database、Sp、File等)。
 * 3.获取数据需要的参数配置。
 */
public abstract class Presenter<T extends UiProxy> {

    protected WeakReference<T> mUiProxy;

    /**
     * 设置UI代理类
     *
     * @param uiProxy UI层的代理类
     */
    public void setUiProxy(T uiProxy) {
        mUiProxy = new WeakReference<>(uiProxy);
    }

    /**
     * 获取UI代理类
     *
     * @return UI层的代理类
     */
    public T getUiProxy() {
        return mUiProxy.get();
    }

    /**
     * 在Activity被销毁的同时，将Presenter中消耗资源的操作释放掉。
     */
    public void destroyConsumables() {
        mUiProxy.clear();
    }


}
