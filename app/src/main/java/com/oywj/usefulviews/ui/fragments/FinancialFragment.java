package com.oywj.usefulviews.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.presenter.subs.FinancialPresenter;
import com.oywj.usefulviews.ui.basic.BasicFragment;
import com.oywj.usefulviews.ui.basic.UiProxy;
import com.oywj.usefulviews.ui.views.PullDownRefreshView;
import com.oywj.usefulviews.ui.views.ptrefresh.PtrDefaultHandler;
import com.oywj.usefulviews.ui.views.ptrefresh.PtrFrameLayout;

import butterknife.BindView;

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
        mPresenter.setUiProxy(new MessageUiProxy());
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
        initToolbar();
        mPullDownRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPullDownRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullDownRefreshView.refreshComplete();
                    }
                }, 2000);
            }
        });
        //mPresenter.getTechList();
    }

    private void initToolbar() {
        ImageView titleImg = new ImageView(getActivity());
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;
        titleImg.setImageResource(R.mipmap.title_logo);
        titleImg.setLayoutParams(params);
        mToolbar.addView(titleImg);
    }

    public class MessageUiProxy implements UiProxy {

        @Override
        public Context getProxyContext() {
            return getContext();
        }
    }
}
