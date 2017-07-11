package com.oywj.usefulviews.data.http;

import android.text.TextUtils;

import com.oywj.usefulviews.data.http.core.ApiException;
import com.oywj.usefulviews.data.http.core.HttpResult;
import com.oywj.usefulviews.data.http.core.RxSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxManager 管理RxJava执行异步任务。
 */
public class RxManager {

    private static volatile RxManager singleton;
    private static Map<String, Subscription> subscriptionMap = new HashMap<>();

    private RxManager() {

    }

    public static RxManager getInstance() {
        if (singleton == null) {
            synchronized (HttpManager.class) {
                if (singleton == null) {
                    singleton = new RxManager();
                }
            }
        }
        return singleton;
    }

    /**
     * 执行任务并订阅，此方法适用于服务器返回了JSON数据。
     *
     * @param observable 被观察者
     * @param subscriber 订阅者
     * @param <T>        JSON数据中的实际数据对象（不包含状态码、消息）
     */
    public <T> void doSubscribeTOfGsonConvert(Observable<HttpResult<T>> observable, RxSubscriber<T> subscriber) {
        Subscription subscription = observable
                .flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> httpResult) {
                        if (httpResult.getCode() != 0) {
                            if (!TextUtils.isEmpty(httpResult.getMsg()))
                                throw new ApiException(httpResult.getMsg());
                            else
                                throw new ApiException(httpResult.getCode());
                        }
                        return Observable.just(httpResult.getData());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        subscriptionMap.put(subscriber.getTag(), subscription);
    }

    /**
     * 执行任务并订阅，此方法适用于服务器返回了JSON数据，并且数据中不包含数据实体。
     *
     * @param observable 被观察者
     * @param subscriber 订阅者
     */
    public void doSubscribeOfGsonConvert(Observable<HttpResult> observable, RxSubscriber<HttpResult> subscriber) {
        Subscription subscription = observable
                .map(new Func1<HttpResult, HttpResult>() {

                    @Override
                    public HttpResult call(HttpResult httpResult) {
                        if (httpResult.getCode() != 0) {
                            if (!TextUtils.isEmpty(httpResult.getMsg()))
                                throw new ApiException(httpResult.getMsg());
                            else
                                throw new ApiException(httpResult.getCode());
                        }
                        return httpResult;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        subscriptionMap.put(subscriber.getTag(), subscription);
    }

    /**
     * 执行任务(子线程：subscribeOn(Schedulers.io()))并订阅(订阅回调：主线程：observeOn(AndroidSchedulers.mainThread()))。
     *
     * @param observable 被观察者
     * @param subscriber 订阅者
     */
    public <T> void doSubscribe(Observable<T> observable, RxSubscriber<T> subscriber) {
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        subscriptionMap.put(subscriber.getTag(), subscription);
    }

    /**
     * 执行任务(子线程：subscribeOn(Schedulers.io()))并订阅(订阅回调：子线程：subscribeOn(Schedulers.io()))。
     *
     * @param observable 被观察者
     * @param subscriber 订阅者
     */
    public <T> void doIoSubscribe(Observable<T> observable, RxSubscriber<T> subscriber) {
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
        subscriptionMap.put(subscriber.getTag(), subscription);
    }

    /**
     * 通过{@link Observable.Transformer}进行一次变换的订阅过程。
     *
     * @param observable  被观察者
     * @param transformer 转换器
     * @param subscriber  订阅者
     * @param <T>         初始的数据类型
     * @param <R>         最终的数据类型
     * @return
     */
    public <T, R> void doComposeSubscriber(Observable<T> observable,
                                           Observable.Transformer<T, R> transformer,
                                           RxSubscriber<R> subscriber) {
        Observable<R> compose = observable.compose(transformer);
        Subscription subscribe = compose.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        subscriptionMap.put(subscriber.getTag(), subscribe);
    }

    /**
     * 执行变换操作
     *
     * @param observable  被观察者
     * @param transformer 转换器
     * @param <T>         初始的数据类型
     * @param <R>         最终的数据类型
     * @return Observable
     */
    public <T, R> Observable doComposeTransform(Observable<T> observable, Observable.Transformer<T, R> transformer) {
        return observable.compose(transformer);
    }


    /**
     * clear and unSubscribe of the Subscriptions.
     */
    public synchronized void clearSubscriptions() {
        Set<Map.Entry<String, Subscription>> entries = subscriptionMap.entrySet();
        for (Map.Entry<String, Subscription> entry : entries) {
            Subscription subscription = entry.getValue();
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }

        if (!subscriptionMap.isEmpty())
            subscriptionMap.clear();
    }

    /**
     * remove and unSubscribe of the subscription.
     *
     * @param tag set the tag of the RxSubscriber.
     */
    public synchronized void removeSubscription(String tag) {
        Subscription subscription = subscriptionMap.get(tag);
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


}
