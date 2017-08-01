package com.oywj.usefulviews.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.utils.AutoUtils;


/**
 * 50元红包   100元红包  150元红包+3%加息券
 * O-----------O-------------O
 * 1. 注册拿奖励      充值          投资      （未注册）
 * 2.  已注册      充值领红包       投资      （已注册-未充值）
 * 3.  已注册       已充值        投资拿奖励   (已充值-未投资)
 * 4.  已注册       已充值        已投资      （已投资）
 */
public class FinancialProgressView extends View {

    private static final LocationType[] sLocationTypeArray = {
            LocationType.START,
            LocationType.MIDDLE,
            LocationType.END
    };
    private final Paint mPaint;

    private LocationType mLocationType;
    private boolean mSelected;
    private int mRadius = AutoUtils.getPercentWidthSizeBigger(3);

    public FinancialProgressView(Context context) {
        this(context, null);
    }

    public FinancialProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FinancialProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FinancialProgressView, defStyleAttr, 0);
        mSelected = a.getBoolean(R.styleable.FinancialProgressView_selected, false);
        final int index = a.getInt(R.styleable.FinancialProgressView_locationType, -1);
        if (index >= 0) {
            setLocationType(sLocationTypeArray[index]);
        }
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = getDefaultSize(mRadius * 2, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mSelected ? ContextCompat.getColor(getContext(),R.color.financial_process_selected_color)
                        : ContextCompat.getColor(getContext(),R.color.financial_process_unSelected_color)
        );


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setLocationType(LocationType type) {
        if (type == null) {
            throw new NullPointerException();
        }

        if (mLocationType != type) {
            mLocationType = type;
            requestLayout();
            invalidate();
        }
    }

    /**
     * START: start location.
     * MIDDLE: middle location.
     * END: end location.
     */
    public enum LocationType {

        START(0),

        MIDDLE(1),

        END(2);

        LocationType(int ni) {
            nativeInt = ni;
        }

        final int nativeInt;
    }
}
