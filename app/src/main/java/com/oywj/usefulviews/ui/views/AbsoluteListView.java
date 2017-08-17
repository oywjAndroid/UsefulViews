package com.oywj.usefulviews.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by AndroidDev on 2017/8/17.
 * AbsoluteListView:拥有绝对高度的ListView。
 */
public class AbsoluteListView extends ListView {

    public AbsoluteListView(Context context) {
        super(context);
    }

    public AbsoluteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsoluteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsoluteListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
