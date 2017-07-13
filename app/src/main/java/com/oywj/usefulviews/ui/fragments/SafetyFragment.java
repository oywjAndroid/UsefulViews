package com.oywj.usefulviews.ui.fragments;

import android.os.Bundle;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicFragment;

/**
 * Created by Android on 2017/7/13.
 */
public class SafetyFragment extends BasicFragment {

    public static SafetyFragment newInstance() {
        Bundle args = new Bundle();

        SafetyFragment fragment = new SafetyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.fragment_safety;
    }

    @Override
    protected void onBindDataToViews() {

    }
}
