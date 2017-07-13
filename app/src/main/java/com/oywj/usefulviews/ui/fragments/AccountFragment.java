package com.oywj.usefulviews.ui.fragments;

import android.os.Bundle;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicFragment;

/**
 * Created by Android on 2017/7/11.
 */
public class AccountFragment extends BasicFragment {

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
    }
}
