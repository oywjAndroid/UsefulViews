package com.oywj.usefulviews.ui.activties;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicActivity;
import com.oywj.usefulviews.ui.fragments.MainFragment;

public class MainActivity extends BasicActivity {

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
        loadRootFragment(R.id.main_root, MainFragment.newInstance());
    }

}
