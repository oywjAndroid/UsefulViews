package com.oywj.usefulviews.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.presenter.subs.FinancialPresenter;
import com.oywj.usefulviews.ui.basic.BasicFragment;
import com.oywj.usefulviews.ui.basic.UiProxy;

import butterknife.BindView;

public class FinancialFragment extends BasicFragment<FinancialPresenter> {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

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

        mPresenter.getTechList();
    }

    private void initToolbar() {


    }

    public class MessageUiProxy implements UiProxy {

        @Override
        public Context getProxyContext() {
            return getContext();
        }
    }
}
