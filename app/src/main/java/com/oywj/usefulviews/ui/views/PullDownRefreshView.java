package com.oywj.usefulviews.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.oywj.usefulviews.ui.views.ptrefresh.PtrFrameLayout;

/**
 * Created by Android on 2017/7/14.
 * <p>
 * PullDownRefreshView:基于PtrFrameLayout的下拉刷新视图。
 */
public class PullDownRefreshView extends PtrFrameLayout {

    private PullDownRefreshHeader mPullDownHeader;

    public PullDownRefreshView(Context context) {
        this(context, null);
    }

    public PullDownRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullDownRefreshView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        activeViews();

    }

    private void activeViews() {
        mPullDownHeader = new PullDownRefreshHeader(getContext());
        setHeaderView(mPullDownHeader);
        addPtrUIHandler(mPullDownHeader);
    }

    /**
     * get the pullDownRefreshHeader.
     *
     * @return PullDownRefreshHeader
     */
    public PullDownRefreshHeader getPullDownHeader() {
        return mPullDownHeader;
    }
}
