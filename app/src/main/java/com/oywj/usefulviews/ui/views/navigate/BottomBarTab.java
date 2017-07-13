package com.oywj.usefulviews.ui.views.navigate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oywj.usefulviews.R;


/**
 * Created by YoKeyword on 16/6/3.
 */
@SuppressLint("ViewConstructor")
public class BottomBarTab extends FrameLayout {
    private ImageView mIcon;
    private TextView mTvTitle;
    private int mTabPosition = -1;

    private TextView mTvUnreadCount;
    private int mSelectedColor;
    private int mUnSelectedColor;

    private int mIconSize = 27;
    private int mTextSize = 10;

    public BottomBarTab(Context context, @DrawableRes int icon, CharSequence title) {
        this(context, null, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon, CharSequence title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, CharSequence title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, CharSequence title) {
        mSelectedColor = ContextCompat.getColor(context, R.color.colorPrimary);
        mUnSelectedColor = ContextCompat.getColor(context, R.color.tab_unSelect);

        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        LinearLayout lLContainer = new LinearLayout(context);
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);

        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIconSize, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);
        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unSelect));
        lLContainer.addView(mIcon);

        if (!TextUtils.isEmpty(title)) {
            mTvTitle = new TextView(context);
            mTvTitle.setText(title);
            LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsTv.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            mTvTitle.setTextSize(mTextSize);
            mTvTitle.setTextColor(ContextCompat.getColor(context, R.color.tab_unSelect));
            mTvTitle.setLayoutParams(paramsTv);
            lLContainer.addView(mTvTitle);
        }

        addView(lLContainer);

        int min = dip2px(20);
        int padding = dip2px(5);
        mTvUnreadCount = new TextView(context);
        mTvUnreadCount.setBackgroundResource(R.drawable.bg_msg_bubble);
        mTvUnreadCount.setMinWidth(min);
        mTvUnreadCount.setTextColor(Color.WHITE);
        mTvUnreadCount.setPadding(padding, 0, padding, 0);
        mTvUnreadCount.setGravity(Gravity.CENTER);
        LayoutParams tvUnReadParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, min);
        tvUnReadParams.gravity = Gravity.CENTER;
        tvUnReadParams.leftMargin = dip2px(17);
        tvUnReadParams.bottomMargin = dip2px(14);
        mTvUnreadCount.setLayoutParams(tvUnReadParams);
        mTvUnreadCount.setVisibility(GONE);

        addView(mTvUnreadCount);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(mSelectedColor);
            if (mTvTitle != null) mTvTitle.setTextColor(mSelectedColor);
        } else {
            mIcon.setColorFilter(mUnSelectedColor);
            if (mTvTitle != null) mTvTitle.setTextColor(mUnSelectedColor);
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    /**
     * 设置图标的大小
     *
     * @param iconSize 图标大小
     * @return this
     */
    public BottomBarTab setIconSize(int iconSize) {
        this.mIconSize = iconSize;
        int size = dip2px(iconSize);
        ViewGroup.LayoutParams params = mIcon.getLayoutParams();
        params.width = size;
        params.height = size;
        mIcon.setLayoutParams(params);
        return this;
    }

    /**
     * 设置文本大小
     *
     * @param textSize 文本大小
     * @return this
     */
    public BottomBarTab setTextSize(int textSize) {
        this.mTextSize = textSize;
        mTvTitle.setTextSize(mTextSize);
        return this;
    }

    /**
     * 设置选中的颜色
     *
     * @param selectedColor 选中颜色
     */
    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
        postInvalidate();
    }

    /**
     * 设置未选中时的颜色
     *
     * @param unSelectedColor 未选中颜色
     */
    public void setUnselectedColor(int unSelectedColor) {
        mUnSelectedColor = unSelectedColor;
        postInvalidate();
    }


    /**
     * 设置未读数量
     */
    @SuppressLint("SetTextI18n")
    public void setUnreadCount(int num) {
        if (num <= 0) {
            mTvUnreadCount.setText(String.valueOf(0));
            mTvUnreadCount.setVisibility(GONE);
        } else {
            mTvUnreadCount.setVisibility(VISIBLE);
            if (num > 99) {
                mTvUnreadCount.setText("99+");
            } else {
                mTvUnreadCount.setText(String.valueOf(num));
            }
        }
    }

    /**
     * 获取当前未读数量
     */
    public int getUnreadCount() {
        int count = 0;
        if (TextUtils.isEmpty(mTvUnreadCount.getText())) {
            return count;
        }
        if (mTvUnreadCount.getText().toString().equals("99+")) {
            return 99;
        }
        try {
            count = Integer.valueOf(mTvUnreadCount.getText().toString());
        } catch (Exception ignored) {
        }
        return count;
    }

    private int dip2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
