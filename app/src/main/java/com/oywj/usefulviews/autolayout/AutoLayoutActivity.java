package com.oywj.usefulviews.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.oywj.usefulviews.ui.basic.swipeback.SwipeBackActivity;


/**
 * Created by zhy on 15/11/19.
 * AutoLayoutActivity:用于UI适配可以将Activity对应的FrameLayout、LinearLayout、RelativeLayout自动转换成
 * AutoFrameLayout、AutoLinearLayout、AutoRelativeLayout。
 */
public class AutoLayoutActivity extends SwipeBackActivity {
    private static final String LINEAR_LAYOUT = "LinearLayout";
    private static final String FRAME_LAYOUT = "FrameLayout";
    private static final String RELATIVE_LAYOUT = "RelativeLayout";

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(FRAME_LAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LINEAR_LAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(RELATIVE_LAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;
        return super.onCreateView(name, context, attrs);
    }

}
