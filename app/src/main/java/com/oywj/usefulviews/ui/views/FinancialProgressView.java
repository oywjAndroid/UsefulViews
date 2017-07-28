package com.oywj.usefulviews.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.utils.DensityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Android on 2017/7/18.
 * 1.如何处理分段？
 * 2.
 */
public class FinancialProgressView extends View {

    private List<FinancialProgressData> mProgressData;
    private float mMinValue;
    private float mMaxValue;
    private float mProgress;
    private int mTextSize;
    private Paint mPaint;

    public FinancialProgressView(Context context) {
        super(context);
    }

    public FinancialProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FinancialProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttrs(context, attrs, defStyleAttr);
        configParams();
    }

    private void configParams() {
        mProgressData = new ArrayList<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private void resolveAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FinancialProgressView, defStyleAttr, 0);
        mMinValue = a.getFloat(R.styleable.FinancialProgressView_minValue, 0f);
        mMaxValue = a.getFloat(R.styleable.FinancialProgressView_maxValue, 100f);
        mProgress = a.getFloat(R.styleable.FinancialProgressView_progress, mMinValue);
        mTextSize = a.getDimensionPixelSize(R.styleable.FinancialProgressView_textSize, DensityUtils.sp2px(15));
        a.recycle();
    }

    public void addFinancialProgressData(FinancialProgressData... progressData) {
        Collections.addAll(mProgressData, progressData);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //int width = resolveSize(getSuggestedMinimumWidth())
        //calculateTextSize();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int calculateTextSize() {
        mPaint.setTextSize(mTextSize);
        int rewardWidth = 0;
        int manipulateWidth = 0;
        int manipulateDescWidth = 0;
        for (FinancialProgressData financialData : mProgressData) {
            rewardWidth += mPaint.measureText(financialData.getFinancialReward());
            manipulateWidth += mPaint.measureText(financialData.getManipulate());
            manipulateDescWidth += mPaint.measureText(financialData.getManipulateDesc());
        }
        return Math.max(Math.max(rewardWidth, manipulateWidth), manipulateDescWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    /**
     * 理财阶段数据信息
     */
    public static class FinancialProgressData {
        private String financialReward;
        private String manipulate;
        private String manipulateDesc;

        public FinancialProgressData(String financialReward, String manipulate, String manipulateDesc) {
            this.financialReward = financialReward;
            this.manipulate = manipulate;
            this.manipulateDesc = manipulateDesc;
        }

        public FinancialProgressData() {

        }

        public String getFinancialReward() {
            return financialReward;
        }

        public void setFinancialReward(String financialReward) {
            this.financialReward = financialReward;
        }

        public String getManipulate() {
            return manipulate;
        }

        public void setManipulate(String manipulate) {
            this.manipulate = manipulate;
        }

        public String getManipulateDesc() {
            return manipulateDesc;
        }

        public void setManipulateDesc(String manipulateDesc) {
            this.manipulateDesc = manipulateDesc;
        }
    }

}
