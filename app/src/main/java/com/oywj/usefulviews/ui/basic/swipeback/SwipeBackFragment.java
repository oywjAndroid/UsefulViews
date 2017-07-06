package com.oywj.usefulviews.ui.basic.swipeback;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.oywj.usefulviews.ui.basic.swipeback.core.ISwipeBackFragment;
import com.oywj.usefulviews.ui.basic.swipeback.core.SwipeBackFragmentDelegate;
import com.oywj.usefulviews.ui.support.SupportFragment;

import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * {SwipeFragment}实现了从边缘侧滑隐藏掉当前Fragment。
 */
public class SwipeBackFragment extends SupportFragment implements ISwipeBackFragment {
    final SwipeBackFragmentDelegate mDelegate = new SwipeBackFragmentDelegate(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDelegate.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View attachToSwipeBack(View view) {
        return mDelegate.attachToSwipeBack(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mDelegate.onHiddenChanged(hidden);
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mDelegate.getSwipeBackLayout();
    }

    public void setSwipeBackEnable(boolean enable) {
        mDelegate.setSwipeBackEnable(enable);
    }

    /**
     * Set the offset of the parallax slip.
     */
    public void setParallaxOffset(@FloatRange(from = 0.0f, to = 1.0f) float offset) {
        mDelegate.setParallaxOffset(offset);
    }

    @Override
    public void onDestroyView() {
        mDelegate.onDestroyView();
        super.onDestroyView();
    }
}
