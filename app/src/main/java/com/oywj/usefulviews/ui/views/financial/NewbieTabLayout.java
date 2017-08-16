
package com.oywj.usefulviews.ui.views.financial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewbieTabLayout extends AutoLinearLayout {

    @BindView(R.id.newbie_tab_left)
    TextView mLeftTab;
    @BindView(R.id.newbie_tab_middle)
    TextView mMiddleTab;
    @BindView(R.id.newbie_tab_right)
    TextView mRightTab;

    private OnClickTabListener mOnClickTabListener;
    private List<String> mTabData;
    private int oldPosition = -1;

    public NewbieTabLayout(Context context) {
        this(context, null);
    }

    public NewbieTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewbieTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.layout_newbie_tab, this, true);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.newbie_tab_left)
    void clickLeftTab() {
        if (mOnClickTabListener != null) {
            mOnClickTabListener.onClickLeftTab();
        }
    }

    @OnClick(R.id.newbie_tab_right)
    void clickRightTab() {
        if (mOnClickTabListener != null) {
            mOnClickTabListener.onClickRightTab();
        }
    }

    public void setTabData(List<String> tabData) {
        if (tabData == null || tabData.isEmpty()) {
            throw new IllegalArgumentException("tabData is empty.");
        }
        mTabData = tabData;
        setCurrentItem(0);
    }

    public void setCurrentItem(final int position) {
        if (position < 0 || position >= mTabData.size()) {
            return;
        }
        if (oldPosition != position) {
            oldPosition = position;
            final int leftPos;
            final int rightPos;
            if (position == 0) {
                leftPos = mTabData.size() - 1;
                rightPos = 1;
            } else if (position == mTabData.size() - 1) {
                leftPos = mTabData.size() - 2;
                rightPos = 0;
            } else {
                leftPos = position - 1;
                rightPos = position + 1;
            }
            post(new Runnable() {
                @Override
                public void run() {
                    mLeftTab.setText(mTabData.get(leftPos));
                    mMiddleTab.setText(mTabData.get(position));
                    mRightTab.setText(mTabData.get(rightPos));
                }
            });
        }
    }

    public interface OnClickTabListener {

        void onClickLeftTab();

        void onClickRightTab();
    }

    public void setOnClickTabListener(OnClickTabListener clickTabListener) {
        mOnClickTabListener = clickTabListener;
    }


}
