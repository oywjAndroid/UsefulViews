package com.oywj.usefulviews.ui.views.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.utils.AutoUtils;
import com.oywj.usefulviews.ui.basic.BasicApplication;

/**
 * Created by AndroidDev on 2017/8/9.
 * 新手专享理财标的物的背景Drawable。
 */
public class NewbieMarkDrawable extends Drawable {

    private final int mStrokeSize;
    private final RectF mRoundRect = new RectF();
    private Paint mPaint;
    private int mRoundRectRadius = AutoUtils.getPercentWidthSize(10);
    private int drawBottomPadding = 0;

    public NewbieMarkDrawable(Context context) {
        mStrokeSize = context.getResources().getDimensionPixelSize(R.dimen.newbie_markView_stroke_width);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(BasicApplication.getInstance(), R.color.financial_category_more));
    }

    @Override
    public void draw(Canvas canvas) {
        final float dLeft = getBounds().left;
        final float dTop = getBounds().top;
        final float dRight = getBounds().right;
        final float dBottom = getBounds().bottom - drawBottomPadding;
        final float dWidth = dRight - dLeft;
        final float dHeight = dBottom - dTop;

        // 1.draw the stroke.
        mPaint.setStrokeWidth(mStrokeSize);
        mRoundRect.set(
                dLeft + mStrokeSize,
                dTop + mStrokeSize,
                dRight - mStrokeSize,
                dBottom - mStrokeSize
        );
        canvas.drawRoundRect(mRoundRect, mRoundRectRadius, mRoundRectRadius, mPaint);

        // 2.draw the content.
        mPaint.setStrokeWidth(mStrokeSize * 0.55f);
        final float lineWidth = dWidth * 0.1f;
        final float leftDistance = lineWidth * 0.5f;
        final float topDistance = dHeight * 0.3f;
        final float bottomDistance = dHeight * 0.9f;

        // draw the line of upper left corner.
        float startX1 = dLeft + leftDistance;
        float startY1 = dTop + topDistance;
        float stopX1 = startX1 + lineWidth;
        float stopY1 = startY1 * 0.5f;
        canvas.drawLine(startX1, startY1, stopX1, stopY1, mPaint);

        // draw the line of upper right corner.
        float startX2 = dLeft + dWidth - lineWidth;
        float stopX2 = dLeft + dWidth;
        canvas.drawLine(startX2, startY1, stopX2, stopY1, mPaint);

        // draw the line of bottom left corner.
        float startY3 = dTop + bottomDistance;
        float stopY3 = dTop + 1.92f * bottomDistance - dHeight;//(getBounds().height() - (getBounds().height() - bottomDistance) * 2f) ==> 2f*bottomDistance - getBounds().height()
        canvas.drawLine(startX1, startY3, stopX1, stopY3, mPaint);

        // draw the line of bottom right corner.
        float startX4 = startX2 * 0.98f;
        float stopX4 = startX2 + lineWidth * 0.8f;
        canvas.drawLine(startX4, startY3, stopX4, stopY3, mPaint);

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        getBounds().set(bounds);
    }

    @Override
    public int getIntrinsicHeight() {
        return getBounds().width();
    }

    @Override
    public int getIntrinsicWidth() {
        return getBounds().height();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public void setDrawBottomPadding(int drawBottomPadding) {
        this.drawBottomPadding = drawBottomPadding;
    }
}
