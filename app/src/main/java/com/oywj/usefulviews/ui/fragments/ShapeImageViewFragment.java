package com.oywj.usefulviews.ui.fragments;

import android.os.Bundle;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicFragment;
import com.oywj.usefulviews.ui.views.ShapeImageView;
import com.oywj.usefulviews.utils.LogUtils;

import butterknife.BindView;


public class ShapeImageViewFragment extends BasicFragment {

    @BindView(R.id.circle_view)
    ShapeImageView shapeImageView;

    public static ShapeImageViewFragment newInstance() {
        Bundle args = new Bundle();
        ShapeImageViewFragment fragment = new ShapeImageViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.ui_shape_image;
    }

    @Override
    protected void onBindDataToViews() {
        LogUtils.d("Debug", "shapeImageView == {" + shapeImageView + "}");
    }
}
