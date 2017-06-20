package com.oywj.usefulviews.shapeviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.oywj.usefulviews.R;


/**
 * Created by Android on 2017/6/16.
 * ShapeImageView 实现了项目中常用的圆形、圆角矩形、椭圆等头像控件。
 */
public class ShapeImageView extends ImageView {
    private static final String ROUND_RADIUS = "roundRadius";
    private static final String STROKE_COLOR = "strokeColor";
    private static final String PARCELABLE_INSTANCE = "parcelable_instance";
    private static final String IS_STROKE = "is_stroke";
    private static final String CIRCLE_SIZE = "circle_size";
    private static final ShapeType[] arrayShapeTypes = {
            ShapeType.NORMAL,
            ShapeType.CIRCLE,
            ShapeType.ROUND,
            ShapeType.OVAL
    };
    private static final int DEFAULT_ROUND_RADIUS = 10;//dp
    private ShapeType mShapeType;
    private boolean mIsStroke;
    private int mStrokeWidth;
    private int mCircleSize;
    private Paint mShapePaint;
    private Paint mStrokePaint;
    private Matrix mShaderMatrix;
    private Canvas mBitmapCanvas;
    private BitmapShader mBitmapShader;
    private Rect mBitmapRect = new Rect();
    private RectF mRoundRectF = new RectF();
    private int mRoundRadius;
    private int mStrokeColor;
    private Drawable mDrawable;

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initTools();
    }


    private void initAttrs(AttributeSet attrs) {
        TypedArray ty = getResources().obtainAttributes(attrs, R.styleable.ShapeImageView);
        int index = ty.getInt(R.styleable.ShapeImageView_shapeType, -1);
        if (index >= 0) {
            setShapeType(arrayShapeTypes[index]);
        }
        mIsStroke = ty.getBoolean(R.styleable.ShapeImageView_stroke, false);
        mStrokeWidth = ty.getInt(R.styleable.ShapeImageView_strokeWidth, 0);
        if (mStrokeWidth < 0) {
            mStrokeWidth = Math.abs(mStrokeWidth);
        }
        mStrokeColor = ty.getColor(R.styleable.ShapeImageView_strokeColor, Color.WHITE);
        if (mShapeType == ShapeType.ROUND) {
            mRoundRadius = dp2px(ty.getInt(R.styleable.ShapeImageView_roundRadius, DEFAULT_ROUND_RADIUS) * 1f);
        }
        ty.recycle();
    }

    private void initTools() {
        mShapePaint = new Paint();
        mStrokePaint = new Paint();
        mShaderMatrix = new Matrix();
        mBitmapCanvas = new Canvas();
        mRoundRectF = new RectF();
    }

    /**
     * setting Shape type.
     *
     * @param shapeType ShapeType
     */
    public void setShapeType(ShapeType shapeType) {
        if (shapeType == null) {
            throw new NullPointerException();
        }

        if (mShapeType != shapeType) {
            mShapeType = shapeType;
        }
    }

    public void isStroke(boolean isStroke) {
        if (mIsStroke != isStroke) {
            mIsStroke = isStroke;
        }
    }

    public void setStrokeWidth(int strokeWidth) {
        if (mStrokeWidth != strokeWidth) {
            mStrokeWidth = strokeWidth;
        }
    }

    public void setStrokeColor(int strokeColor) {
        if (mStrokeColor != strokeColor) {
            mStrokeColor = strokeColor;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mShapeType == ShapeType.ROUND || mShapeType == ShapeType.OVAL) {
            mRoundRectF.set(0, 0, getWidth(), getHeight());
            if (mIsStroke) {
                mRoundRectF.set(
                        mStrokeWidth,
                        mStrokeWidth,
                        getWidth() - mStrokeWidth,
                        getHeight() - mStrokeWidth
                );
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(ROUND_RADIUS, mRoundRadius);
        bundle.putInt(STROKE_COLOR, mStrokeColor);
        bundle.putBoolean(IS_STROKE, mIsStroke);
        bundle.putInt(CIRCLE_SIZE, mCircleSize);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(PARCELABLE_INSTANCE));
            mRoundRadius = bundle.getInt(ROUND_RADIUS);
            mStrokeColor = bundle.getInt(STROKE_COLOR);
            mIsStroke = bundle.getBoolean(IS_STROKE);
            mCircleSize = bundle.getInt(CIRCLE_SIZE);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mShapeType == ShapeType.CIRCLE) {
            mCircleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(mCircleSize, mCircleSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShapeType == ShapeType.NORMAL) {
            super.onDraw(canvas);
            return;
        }
        if (getDrawable() == null) {
            return;
        }
        if (mBitmapShader == null
                || mDrawable == null
                || mDrawable != getDrawable()) {
            mDrawable = getDrawable();
            mBitmapShader = drawableToBitmapShader(getDrawable(), mBitmapCanvas, mBitmapRect);
        }
        switch (mShapeType) {
            case CIRCLE:
                onDrawCircle(canvas);
                break;
            case ROUND:
            case OVAL:
                onDrawOvalOrRound(canvas);
                break;
        }
    }

    private void onDrawOvalOrRound(Canvas canvas) {
        configRoundOrOval();

        if (mShapeType == ShapeType.OVAL) {
            canvas.drawOval(mRoundRectF, mShapePaint);
            if (mIsStroke) {
                canvas.drawOval(mRoundRectF, mStrokePaint);
            }
        } else {
            canvas.drawRoundRect(mRoundRectF, mRoundRadius, mRoundRadius, mShapePaint);
            if (mIsStroke)
                canvas.drawRoundRect(mRoundRectF, mRoundRadius, mRoundRadius, mStrokePaint);
        }
    }

    private void configRoundOrOval() {
        mShaderMatrix.reset();
        mShapePaint.reset();
        mShapePaint.reset();

        if (mIsStroke) {
            mStrokePaint.setAntiAlias(true);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setStrokeWidth(mStrokeWidth);
            mStrokePaint.setColor(mStrokeColor);
        }

        float scale = Math.max(
                getWidth() * 1f / mBitmapRect.right,
                getHeight() * 1f / mBitmapRect.bottom
        );
        float dx = (getWidth() - mBitmapRect.right * scale) * 0.5f;
        float dy = (getHeight() - mBitmapRect.bottom * scale) * 0.5f;
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(dx, dy);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setShader(mBitmapShader);
    }


    private void onDrawCircle(Canvas canvas) {
        mShaderMatrix.reset();
        mShapePaint.reset();
        mStrokePaint.reset();

        if (mIsStroke) {
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setStrokeWidth(mStrokeWidth);
            mStrokePaint.setColor(mStrokeColor);
        }

        float scale = mCircleSize * 1f / Math.min(mBitmapRect.right, mBitmapRect.bottom);
        mShaderMatrix.setScale(scale, scale);
        float dx = 0;
        float dy = 0;
        if (mCircleSize < mBitmapRect.right * scale)
            dx = (mCircleSize - mBitmapRect.right * scale) * 0.5f;
        else if (mCircleSize < mBitmapRect.bottom * scale)
            dy = (mCircleSize - mBitmapRect.bottom * scale) * 0.5f;
        mShaderMatrix.postTranslate(dx, dy);
        mBitmapShader.setLocalMatrix(mShaderMatrix);

        mShapePaint.setStyle(Paint.Style.FILL);
        mShapePaint.setAntiAlias(true);
        mShapePaint.setShader(mBitmapShader);
        canvas.drawCircle(mCircleSize * 0.5f, mCircleSize * 0.5f, mCircleSize * 0.5f, mShapePaint);
        if (mIsStroke) {
            float radius = (mCircleSize - mStrokeWidth) * 0.5f;
            canvas.drawCircle(mCircleSize * 0.5f, mCircleSize * 0.5f, radius, mStrokePaint);
        }
    }

    private BitmapShader drawableToBitmapShader(Drawable drawable, Canvas canvas, Rect rect) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );
            canvas.setBitmap(bitmap);
            drawable.draw(canvas);
        }

        rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        return new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    protected int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public enum ShapeType {

        NORMAL(0),

        CIRCLE(1),

        ROUND(2),

        OVAL(3);

        ShapeType(int type) {
            nativeType = type;
        }

        final int nativeType;
    }
}
