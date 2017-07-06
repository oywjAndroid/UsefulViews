package com.oywj.usefulviews.ui.activties;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.presenter.subs.ShapeImagePresenter;
import com.oywj.usefulviews.ui.basic.BasicActivity;
import com.oywj.usefulviews.ui.basic.UiProxy;
import com.oywj.usefulviews.ui.fragments.ShapeImageViewFragment;
import com.oywj.usefulviews.utils.LogUtils;

import butterknife.BindView;


public class ShapeImageViewActivity extends BasicActivity {

    @BindView(R.id.root_view)
    FrameLayout mRootView;

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.activity_shape;
    }

    @Override
    protected void onBindDataToViews() {
        LogUtils.d("Debug", "mRootView == {" + mRootView + "}");

        loadRootFragment(R.id.root_view, ShapeImageViewFragment.newInstance());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onObtainPresenter() {
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
