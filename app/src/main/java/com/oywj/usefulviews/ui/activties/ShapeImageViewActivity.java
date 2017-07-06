package com.oywj.usefulviews.ui.activties;

import android.os.Bundle;
import android.util.Log;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.presenter.subs.ShapeImagePresenter;
import com.oywj.usefulviews.ui.basic.BasicActivity;
import com.oywj.usefulviews.ui.basic.UiProxy;


public class ShapeImageViewActivity extends BasicActivity {

    @Override
    protected int generateLayoutResID() {
        return R.layout.ui_shape_image;
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void bindDataToViews() {
        Log.d("Debug", "bindDataToViews()");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void obtainPresenter() {
        mPresenter = new ShapeImagePresenter();
        mPresenter.setUiProxy(new ShapeUIProxy());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("BasicActivity", "--onSaveInstanceState()--");
        outState.putString("test1", "HongKong");
        outState.putString("test2", "回归");
        outState.putInt("test3", 20);
        super.onSaveInstanceState(outState);
    }

    public class ShapeUIProxy implements UiProxy {

    }

}
