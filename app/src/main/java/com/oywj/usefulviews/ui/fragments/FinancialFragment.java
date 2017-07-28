package com.oywj.usefulviews.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.data.bean.FinancialData;
import com.oywj.usefulviews.presenter.subs.FinancialPresenter;
import com.oywj.usefulviews.ui.adapter.FinancialAdapter;
import com.oywj.usefulviews.ui.basic.BasicFragment;
import com.oywj.usefulviews.ui.basic.UiProxy;
import com.oywj.usefulviews.ui.views.PullDownRefreshView;
import com.oywj.usefulviews.ui.views.ptrefresh.PtrDefaultHandler;
import com.oywj.usefulviews.ui.views.ptrefresh.PtrFrameLayout;

import java.util.List;

import butterknife.BindView;

/**
 * 理财页面
 */
public class FinancialFragment extends BasicFragment<FinancialPresenter> {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.pull_down_refresh_view)
    PullDownRefreshView mPullDownRefreshView;

    public static FinancialFragment newInstance() {
        Bundle args = new Bundle();

        FinancialFragment fragment = new FinancialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onObtainPresenter() {
        mPresenter = new FinancialPresenter();
        mPresenter.setUiProxy(new FinancialUiProxy());
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
        activeToolbar();
        activeRefreshView();
        activeRecyclerView();
    }

    private void activeRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO 获取模拟数据
        List<FinancialData> dataList = mPresenter.simulateData();
        FinancialAdapter adapter = new FinancialAdapter(dataList);
        mRecyclerView.setAdapter(adapter);
    }

    private void activeRefreshView() {
        mPullDownRefreshView.disableWhenHorizontalMove(true);
        mPullDownRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPullDownRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullDownRefreshView.refreshComplete();
                    }
                }, 2500);
            }
        });
    }

    private void activeToolbar() {
        ImageView titleImg = new ImageView(getActivity());
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                getResources().getDimensionPixelOffset(R.dimen.bottom_bar_title_size),
                getResources().getDimensionPixelOffset(R.dimen.bottom_bar_title_size)
        );
        params.gravity = Gravity.CENTER;
        titleImg.setImageResource(R.mipmap.title_logo);
        titleImg.setLayoutParams(params);
        mToolbar.addView(titleImg);
    }

    public class FinancialUiProxy implements UiProxy {

        @Override
        public Context getProxyContext() {
            return getContext();
        }
    }
}
