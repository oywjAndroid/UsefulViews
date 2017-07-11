package com.oywj.usefulviews.ui.fragments;

import android.os.Bundle;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicFragment;

/**
 * Created by Android on 2017/7/11.
 */
public class MessageFragment extends BasicFragment {

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
    }
}
