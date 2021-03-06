package com.oywj.usefulviews.ui.views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.AutoLayoutInfo;
import com.oywj.usefulviews.autolayout.utils.AutoLayoutHelper;
import com.oywj.usefulviews.ui.views.ptrefresh.PtrFrameLayout;
import com.oywj.usefulviews.ui.views.ptrefresh.PtrUIHandler;
import com.oywj.usefulviews.ui.views.ptrefresh.indicator.PtrIndicator;
import com.oywj.usefulviews.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android on 2017/7/14.
 * PullDownRefreshHeader为下拉刷新头部视图。
 */
public class PullDownRefreshHeader extends FrameLayout implements PtrUIHandler {

    private AutoLayoutHelper mAutoHelper = new AutoLayoutHelper(this);

    @BindView(R.id.pull_down_refresh_anim_img)
    ImageView mAnimImg;
    @BindView(R.id.pull_down_desc_tv)
    TextView mDescTv;

    private AnimationDrawable mAnim;

    public PullDownRefreshHeader(Context context) {
        this(context, null);
    }

    public PullDownRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullDownRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        activeViews(context);
    }

    private void activeViews(Context context) {
        View headerView = LayoutInflater.from(context).inflate(R.layout.pull_down_refresh_header, this, true);
        ButterKnife.bind(headerView);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        LogUtils.d("onUIReset()...");
        reset();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        reset();
        LogUtils.d("onUIRefreshPrepare()...");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        LogUtils.d("onUIRefreshBegin()");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        LogUtils.d("onUIRefreshComplete()...");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        LogUtils.d("onUIPositionChange(): isUnderTouch = " + isUnderTouch + ",status = " + status + "----" + frame.isPullToRefresh());
        int offsetToRefresh = ptrIndicator.getOffsetToRefresh();
        int currentPosY = ptrIndicator.getCurrentPosY();
        int lastPosY = ptrIndicator.getLastPosY();

        if (currentPosY < offsetToRefresh && lastPosY >= offsetToRefresh) {
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mDescTv.setText(R.string.pull_down);
            }
        } else if (currentPosY > offsetToRefresh && lastPosY <= offsetToRefresh) {
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mDescTv.setText(R.string.release_refresh_txt);
            }
        }

        if (status == PtrFrameLayout.PTR_STATUS_LOADING) {
            if (mAnim == null) {
                mAnim = (AnimationDrawable) ContextCompat.getDrawable(getContext(), R.drawable.pull_down_refresh_animation);
                mAnimImg.setImageDrawable(mAnim);
            }
            mAnim.start();
            mDescTv.setText(R.string.loading_text);
        }
    }

    private void reset() {
        if (mAnim != null) {
            mAnim.stop();
        }
        mDescTv.setText(R.string.pull_down);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mAutoHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(FrameLayout.LayoutParams source) {
            super((MarginLayoutParams) source);
            gravity = source.gravity;
        }

        public LayoutParams(LayoutParams source) {
            this((FrameLayout.LayoutParams) source);
            mAutoLayoutInfo = source.mAutoLayoutInfo;
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo() {
            return mAutoLayoutInfo;
        }

    }

}
