package com.oywj.usefulviews.ui.fragments;

import android.os.Bundle;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicFragment;

/**
 * Created by Android on 2017/7/11.
 *
 */
public class DiscoverFragment extends BasicFragment {

    public static DiscoverFragment newInstance() {
        Bundle args = new Bundle();

        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);

    }
}
