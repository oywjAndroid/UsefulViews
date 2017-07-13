package com.oywj.usefulviews.presenter.subs;

import com.oywj.usefulviews.data.api.MessageApi;
import com.oywj.usefulviews.data.bean.GankItemBean;
import com.oywj.usefulviews.data.http.HttpManager;
import com.oywj.usefulviews.data.http.RxManager;
import com.oywj.usefulviews.data.http.core.RxSubscriber;
import com.oywj.usefulviews.presenter.Presenter;
import com.oywj.usefulviews.ui.fragments.FinancialFragment;
import com.oywj.usefulviews.utils.LogUtils;

import java.util.List;

/**
 * Created by Android on 2017/7/13.
 * FinancialPresenter 作为处理Message的presenter层。
 */
public class FinancialPresenter extends Presenter<FinancialFragment.MessageUiProxy> {

    public static final String TAG = "FinancialPresenter";

    public void getTechList() {
        MessageApi messageApi = HttpManager
                .with(getUiProxy().getProxyContext())
                .createApi(MessageApi.class);

        RxSubscriber<List<GankItemBean>> gankPresenter = new RxSubscriber<List<GankItemBean>>() {
            @Override
            public void onSuccess(List<GankItemBean> gankItemBeans) {
                for (GankItemBean bean : gankItemBeans) {
                    LogUtils.d("FinancialPresenter", bean.toString());
                }
            }

            @Override
            public void onFail(ErrorType errorType, Throwable e) {
                e.printStackTrace();
            }
        };
        gankPresenter.setTag("gank_presenter");

        RxManager.getInstance().doSubscribeTOfGsonConvert2(
                messageApi.getTechList("Android", 20, 1),
                gankPresenter
        );

//        MessageApi messageApi = HttpManager
//                .with(getUiProxy().getProxyContext())
//                .createApiForOriginal(MessageApi.class);
//
//        RxManager.getInstance().doSubscribe(
//                messageApi.getTechList("Android", 20, 1),
//                new RxSubscriber<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        //LogUtils.d("FinancialPresenter", "result:" + unicodeToUtf8(s));
//                    }
//
//                    @Override
//                    public void onFail(ErrorType errorType, Throwable e) {
//                        e.printStackTrace();
//                        switch (errorType) {
//                            case STATUS_CODE_ERROR:
//                                LogUtils.e(TAG, "STATUS_CODE_ERROR");
//                                break;
//                            case JSON_SYNTAX_ERROR:
//                                LogUtils.e(TAG, "JSON_SYNTAX_ERROR");
//                                break;
//                            case NET_WORT_ERROR:
//                                LogUtils.e(TAG, "NET_WORT_ERROR");
//                                break;
//                            case UNKNOWN_ERROR:
//                                LogUtils.e(TAG, "UNKNOWN_ERROR");
//                                break;
//                        }
//                    }
//                }
//        );
    }


    @Override
    public void destroyConsumables() {
        super.destroyConsumables();
        LogUtils.d("FinancialPresenter -- destroyConsumables()");
        RxManager.getInstance().removeSubscription("gank_presenter");
    }
}
