package com.oywj.usefulviews.ui.views.financial;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.AutoFrameLayout;
import com.oywj.usefulviews.autolayout.utils.AutoUtils;
import com.oywj.usefulviews.ui.views.drawable.NewbieMarkDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AndroidDev on 2017/8/10.
 * 整个新手标对应的Layout。
 */
public class NewbieCategoryLayout extends AutoFrameLayout {

    private final NewbieMarkDrawable mBackgroundDrawable;
    @BindView(R.id.newbie_category_container)
    LinearLayout mContainer;
    @BindView(R.id.newbie_7day)
    TextView mNewbie7day;
    @BindView(R.id.newbie_percent_income)
    TextView mNewbiePercentIncome;
    @BindView(R.id.history_income)
    TextView mHistoryIncome;
    @BindView(R.id.newbie_buy)
    TextView mNewbieBuy;


    public NewbieCategoryLayout(Context context) {
        this(context, null);
    }

    public NewbieCategoryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewbieCategoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundDrawable = new NewbieMarkDrawable(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflateView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        configBackgroundDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        configBackgroundDrawable();
    }

    @Override
    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    private void configBackgroundDrawable() {
        if (getMeasuredHeight() > 0) {
            final int markBottomPadding = (int) (mNewbieBuy.getMeasuredHeight() * 0.49f + 0.5f);
            mBackgroundDrawable.setDrawBottomPadding(markBottomPadding);
            setBackground(mBackgroundDrawable);
        }
    }

    private void inflateView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_newbie_category, this, true);
        ButterKnife.bind(this);
    }

    /**
     * setting it of percent income.
     *
     * @param percentIncome
     */
    public void setNewbiePercentIncome(String percentIncome) {
        mNewbiePercentIncome.setText(percentIncome);
    }

    /**
     * setting it of newbie 7 day.
     *
     * @param visibility
     */
    public void setNewbie7DayVisibility(int visibility) {
        if (visibility == INVISIBLE) {
            visibility = GONE;
        }
        changeMarginLayoutParams(visibility);
        mNewbie7day.setVisibility(visibility);
    }

    private void changeMarginLayoutParams(int visibility) {
        if (visibility == GONE) {
            post(new Runnable() {
                @Override
                public void run() {
                    final int markBottomPadding = (int) (mNewbieBuy.getMeasuredHeight() * 0.7f + 0.5f);
                    MarginLayoutParams params = (MarginLayoutParams) mContainer.getLayoutParams();
                    params.topMargin = markBottomPadding + AutoUtils.getPercentHeightSize(getResources().getDimensionPixelOffset(R.dimen.newbie_category_container_margin_top));
                    mContainer.setLayoutParams(params);
                }
            });
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    final int markBottomPadding = (int) (mNewbieBuy.getMeasuredHeight() * 0.49f + 0.5f);
                    MarginLayoutParams params = (MarginLayoutParams) mContainer.getLayoutParams();
                    params.topMargin = markBottomPadding + AutoUtils.getPercentHeightSize(getResources().getDimensionPixelOffset(R.dimen.newbie_category_container_margin_top));
                    mContainer.setLayoutParams(params);
                }
            });
        }
    }

    @OnClick(R.id.newbie_buy)
    public void onClickNewbieBuy() {
        Toast.makeText(getContext(), "购买新手标", Toast.LENGTH_LONG).show();
    }

}
