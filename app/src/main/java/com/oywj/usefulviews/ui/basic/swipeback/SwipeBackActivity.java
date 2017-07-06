package com.oywj.usefulviews.ui.basic.swipeback;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.swipeback.core.ISwipeBackActivity;
import com.oywj.usefulviews.ui.basic.swipeback.core.SwipeBackActivityDelegate;
import com.oywj.usefulviews.ui.support.SupportActivity;

import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * {SwipeActivity}主要实现了侧滑finished当前的Activity界面。
 */
public class SwipeBackActivity extends SupportActivity implements ISwipeBackActivity {
    private final SwipeBackActivityDelegate mDelegate = new SwipeBackActivityDelegate(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mDelegate.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        mDelegate.setSwipeBackEnable(enable);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先;  false: Fragment优先滑动退出
     */
    @Override
    public boolean swipeBackPriority() {
        return mDelegate.swipeBackPriority();
    }

}
