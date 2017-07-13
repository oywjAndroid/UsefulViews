package com.oywj.usefulviews.data.http.core;

import com.google.gson.JsonSyntaxException;
import com.oywj.usefulviews.utils.LogUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import rx.Subscriber;

/**
 * RxSubscriber:
 * 1.封装TAG，方便获取到Subscriptions。
 * 2.将错误信息进行进一步的封装。
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private static final String TAG = RxSubscriber.class.getSimpleName();
    private String tag;

    public RxSubscriber() {
        tag = UUID.randomUUID().toString();
    }

    public String getTag() {
        return tag;
    }

    public RxSubscriber setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public void onCompleted() {
        LogUtils.d(TAG, "onCompleted()...");
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof TimeoutException || e instanceof SocketTimeoutException
                || e instanceof ConnectException) {
            onFail(ErrorType.NET_WORT_ERROR, e);
        } else if (e instanceof JsonSyntaxException) {
            onFail(ErrorType.JSON_SYNTAX_ERROR, e);
        } else if (e instanceof ApiException) {
            ErrorType.STATUS_CODE_ERROR.setValue(((ApiException) e).getCode());
            onFail(ErrorType.STATUS_CODE_ERROR, e);
        } else {
            onFail(ErrorType.UNKNOWN_ERROR, e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 获取数据成功
     *
     * @param data 具体数据
     */
    public abstract void onSuccess(T data);

    /**
     * 获取数据失败
     *
     * @param errorType 获取数据失败信息
     */
    public abstract void onFail(ErrorType errorType, Throwable e);

    /**
     * 关于网络请求发生错误的分类
     */
    public enum ErrorType {

        // 网络发生错误
        NET_WORT_ERROR,

        // 服务器指定的错误
        STATUS_CODE_ERROR,

        // json数据解析错误
        JSON_SYNTAX_ERROR,

        // 其他未知错误
        UNKNOWN_ERROR;

        public void setValue(int nativeInt) {
            this.nativeInt = nativeInt;
        }

        public int getValue() {
            return nativeInt;
        }

        private int nativeInt;
    }


}
