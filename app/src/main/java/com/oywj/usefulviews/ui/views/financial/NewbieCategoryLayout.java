package com.oywj.usefulviews.ui.views.financial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.oywj.usefulviews.R;

/**
 * Created by AndroidDev on 2017/8/10.
 */
public class NewbieCategoryLayout extends FrameLayout {

    public NewbieCategoryLayout(Context context) {
        this(context, null);
    }

    public NewbieCategoryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewbieCategoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView();
    }

    private void inflateView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_newbie_category, this, true);
    }

}
