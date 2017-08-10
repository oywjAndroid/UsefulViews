package com.oywj.usefulviews.ui.views.financial;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Android on 2017/8/3.
 * 理财分类项目
 */
public class FinancialCategoryLayout extends AutoLinearLayout {

    @BindView(R.id.financial_category_icon)
    ImageView mCategoryIcon;
    @BindView(R.id.financial_category_question)
    ImageView mCategoryQuestion;
    @BindView(R.id.financial_category_desc)
    TextView mCategoryDesc;
    @BindView(R.id.financial_category_more)
    TextView mCategoryMore;

    private Drawable mCategoryDrawable;
    private String mCategoryText;
    private boolean mShowQuestion;
    private boolean mShowMore;

    private OnClickQuestionAndMoreListener mListener;

    public FinancialCategoryLayout(Context context) {
        this(context, null);
    }

    public FinancialCategoryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FinancialCategoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FinancialCategoryLayout, defStyleAttr, 0);
        mCategoryDrawable = a.getDrawable(R.styleable.FinancialCategoryLayout_categoryDrawable);
        mCategoryText = a.getString(R.styleable.FinancialCategoryLayout_categoryText);
        mShowQuestion = a.getBoolean(R.styleable.FinancialCategoryLayout_showQuestion, false);
        mShowMore = a.getBoolean(R.styleable.FinancialCategoryLayout_showMore, false);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.financial_category_item, this, true);
        ButterKnife.bind(this);

        if (mCategoryDrawable != null) {
            mCategoryIcon.setImageDrawable(mCategoryDrawable);
        }
        mCategoryDesc.setText(mCategoryText);
        mCategoryQuestion.setVisibility(mShowQuestion ? VISIBLE : GONE);
        mCategoryMore.setVisibility(mShowMore ? VISIBLE : GONE);
    }

    @OnClick(R.id.financial_category_more)
    public void onClickQueryMore(View v) {
        Toast.makeText(getContext(), "更多被点击了", Toast.LENGTH_SHORT).show();
        if (mListener != null) {
            mListener.clickQueryMore(v);
        }
    }

    @OnClick(R.id.financial_category_question)
    public void onClickQuestion(View v) {
        Toast.makeText(getContext(), "疑问被点击了", Toast.LENGTH_SHORT).show();
        if (mListener != null) {
            mListener.clickQuestion(v);
        }
    }

    public interface OnClickQuestionAndMoreListener {
        void clickQueryMore(View v);

        void clickQuestion(View v);
    }

    public void setOnClickQuestionAndMoreListener(OnClickQuestionAndMoreListener listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
        mCategoryDrawable = null;
    }
}
