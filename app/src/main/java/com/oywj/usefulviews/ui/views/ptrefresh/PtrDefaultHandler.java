package com.oywj.usefulviews.ui.views.ptrefresh;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

/**
 * PtrDefaultHandler主要用于PtrFrameLayout判断是否可以执行刷新操作、执行刷新时回调（这种做法耦合性更低）；
 * 默认检测ScrollView、ListView是否处于可刷新状态。
 */
public abstract class PtrDefaultHandler implements PtrHandler {

    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    private static boolean canRecyclerScrollUp(View view) {
        RecyclerView mRecycler = (RecyclerView) view;
        RecyclerView.LayoutManager layoutManager = mRecycler.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {

        }
        return false;
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        if (content instanceof RecyclerView) {
            return !canRecyclerScrollUp(content);
        }
        return !canChildScrollUp(content);
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}