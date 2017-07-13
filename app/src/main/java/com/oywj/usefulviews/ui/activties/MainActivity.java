package com.oywj.usefulviews.ui.activties;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicActivity;
import com.oywj.usefulviews.ui.fragments.MainFragment;
import com.oywj.usefulviews.utils.StatusHintUtils;

import butterknife.BindView;

public class MainActivity extends BasicActivity {

    @BindView(R.id.view_stub)
    ViewStub mViewStub;

    View mStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translucentStatus();
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
        loadRootFragment(R.id.main_root, MainFragment.newInstance());
    }

    private void translucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBar = mViewStub.inflate();
            StatusHintUtils.getInstance().translucentWindow(
                    getApplicationContext(),
                    mStatusBar
            );
            setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            mViewStub.setVisibility(View.VISIBLE);
        }
    }

    private void setStatusBarColor(int color) {
        if (mStatusBar != null && mStatusBar.getVisibility() == View.VISIBLE) {
            mStatusBar.setBackgroundColor(color);
        }
    }

}
