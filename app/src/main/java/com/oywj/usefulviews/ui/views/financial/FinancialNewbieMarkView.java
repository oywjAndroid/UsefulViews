package com.oywj.usefulviews.ui.views.financial;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.oywj.usefulviews.ui.views.drawable.NewbieMarkDrawable;

/**
 * Created by AndroidDev on 2017/8/9.
 */
public class FinancialNewbieMarkView extends View {

    private final NewbieMarkDrawable drawable;

    public FinancialNewbieMarkView(Context context) {
        this(context, null);
    }

    public FinancialNewbieMarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FinancialNewbieMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawable = new NewbieMarkDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            drawable.setBounds(
                    getPaddingLeft(),
                    getPaddingTop(),
                    w - getPaddingLeft(),
                    h - getPaddingBottom()
            );
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        drawable.setBounds(
                getPaddingLeft(),
                getPaddingTop(),
                getMeasuredWidth() - getPaddingRight(),
                getMeasuredHeight() - getPaddingBottom()
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawable.draw(canvas);
    }
}
