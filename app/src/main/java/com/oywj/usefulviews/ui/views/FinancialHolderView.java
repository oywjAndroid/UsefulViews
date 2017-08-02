package com.oywj.usefulviews.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.AutoFrameLayout;
import com.oywj.usefulviews.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android on 2017/8/2.
 */
public class FinancialHolderView extends AutoFrameLayout {
    public static final int UNREGISTER = 1;// 未注册
    public static final int REGISTERED = 1 << 2;// 已注册
    public static final int RECHARGE = 1 << 4;// 已充值
    public static final int INVESTMENT = 1 << 6;// 已投资

    private final int mSelectedColor;
    private final int mUnSelectedColor;

    @BindView(R.id.user_process_registered)
    View mRegister;
    @BindView(R.id.user_process_recharge)
    View mRecharge;
    @BindView(R.id.user_process_invest)
    View mInvest;

    private TextView mRegisterReward;
    private FinancialProgressView mRegisterProcess;
    private TextView mRegisterProcessDesc;

    private TextView mRechargeReward;
    private FinancialProgressView mRechargeProcess;
    private TextView mRechargeProcessDesc;

    private TextView mInvestReward;
    private FinancialProgressView mInvestProcess;
    private TextView mInvestProcessDesc;

    public FinancialHolderView(Context context) {
        this(context, null);
    }

    public FinancialHolderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FinancialHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSelectedColor = ContextCompat.getColor(getContext(), R.color.financial_process_selected_color);
        mUnSelectedColor = ContextCompat.getColor(getContext(), R.color.financial_process_unSelected_color);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        activeView();
    }

    private void activeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.financial_holder_view, this, true);
        ButterKnife.bind(this);
        mRegisterReward = (TextView) mRegister.findViewById(R.id.financial_reward);
        mRegisterProcess = (FinancialProgressView) mRegister.findViewById(R.id.financial_progress);
        mRegisterProcess.setLocationType(FinancialProgressView.LocationType.START);
        mRegisterProcessDesc = (TextView) mRegister.findViewById(R.id.financial_progress_desc);

        mRechargeReward = (TextView) mRecharge.findViewById(R.id.financial_reward);
        mRechargeProcess = (FinancialProgressView) mRecharge.findViewById(R.id.financial_progress);
        mRechargeProcess.setLocationType(FinancialProgressView.LocationType.MIDDLE);
        mRechargeProcessDesc = (TextView) mRecharge.findViewById(R.id.financial_progress_desc);

        mInvestReward = (TextView) mInvest.findViewById(R.id.financial_reward);
        mInvestProcess = (FinancialProgressView) mInvest.findViewById(R.id.financial_progress);
        mInvestProcess.setLocationType(FinancialProgressView.LocationType.END);
        mInvestProcessDesc = (TextView) mInvest.findViewById(R.id.financial_progress_desc);

        mRegisterReward.setText(R.string.register_reward);
        mRechargeReward.setText(R.string.recharge_reward);
        mInvestReward.setText(R.string.invest_reward);

        MarginLayoutParams params = (MarginLayoutParams) mRegisterProcess.getLayoutParams();
        params.leftMargin = AutoUtils.getPercentWidthSize(20);
        mRegisterProcess.setLayoutParams(params);

        MarginLayoutParams params2 = (MarginLayoutParams) mInvestProcess.getLayoutParams();
        params2.rightMargin = AutoUtils.getPercentWidthSize(25);
        mInvestProcess.setLayoutParams(params2);

    }

    public void setFinancialStatus(int status) {
        switch (status) {
            case UNREGISTER:
                unregisterStatus();
                break;
            case REGISTERED:
                registerStatus();
                break;
            case RECHARGE:
                rechargeStatus();
                break;
            case INVESTMENT:
                investmentStatus();
                break;
        }
    }

    // 已投资
    private void investmentStatus() {
        mRegisterReward.setTextColor(mSelectedColor);
        mRegisterProcess.setSelected(true);
        mRegisterProcessDesc.setTextColor(mSelectedColor);
        mRegisterProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg_normal);
        mRegisterProcessDesc.setText(R.string.register_complete);

        mRechargeReward.setTextColor(mSelectedColor);
        mRechargeProcess.setSelected(true);
        mRechargeProcessDesc.setTextColor(mSelectedColor);
        mRechargeProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg_normal);
        mRechargeProcessDesc.setText(R.string.recharge_complete);

        mInvestReward.setTextColor(mSelectedColor);
        mInvestProcess.setSelected(true);
        mInvestProcessDesc.setTextColor(mSelectedColor);
        mInvestProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg_normal);
        mInvestProcessDesc.setText(R.string.invest_complete);
    }

    // 已充值
    private void rechargeStatus() {
        mRegisterReward.setTextColor(mSelectedColor);
        mRegisterProcess.setSelected(true);
        mRegisterProcessDesc.setTextColor(mSelectedColor);
        mRegisterProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg_normal);
        mRegisterProcessDesc.setText(R.string.register_complete);

        mRechargeReward.setTextColor(mSelectedColor);
        mRechargeProcess.setSelected(true);
        mRechargeProcessDesc.setTextColor(mSelectedColor);
        mRechargeProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg_normal);
        mRechargeProcessDesc.setText(R.string.recharge_complete);

        mInvestReward.setTextColor(mSelectedColor);
        mInvestProcess.setSelected(true);
        mInvestProcessDesc.setTextColor(Color.WHITE);
        mInvestProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg);
        mInvestProcessDesc.setText(R.string.invest_process_desc);
    }

    // 已注册
    private void registerStatus() {
        mRegisterReward.setTextColor(mSelectedColor);
        mRegisterProcess.setSelected(true);
        mRegisterProcessDesc.setTextColor(mSelectedColor);
        mRegisterProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg_normal);
        mRegisterProcessDesc.setText(R.string.register_complete);

        mRechargeReward.setTextColor(mSelectedColor);
        mRechargeProcess.setSelected(true);
        mRechargeProcessDesc.setTextColor(Color.WHITE);
        mRechargeProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg);
        mRechargeProcessDesc.setText(R.string.recharge_process_desc);

        mInvestReward.setTextColor(mUnSelectedColor);
        mInvestProcess.setSelected(false);
        mInvestProcessDesc.setTextColor(mUnSelectedColor);
        mInvestProcessDesc.setText(R.string.invest);
    }

    // 未注册
    private void unregisterStatus() {
        mRegisterReward.setTextColor(mSelectedColor);
        mRegisterProcess.setSelected(true);
        mRegisterProcessDesc.setTextColor(Color.WHITE);
        mRegisterProcessDesc.setBackgroundResource(R.drawable.financial_desc_bg);
        mRegisterProcessDesc.setText(R.string.register_process_desc);

        mRechargeReward.setTextColor(mUnSelectedColor);
        mRechargeProcess.setSelected(false);
        mRechargeProcessDesc.setTextColor(mUnSelectedColor);
        mRechargeProcessDesc.setText(R.string.recharge);

        mInvestReward.setTextColor(mUnSelectedColor);
        mInvestProcess.setSelected(false);
        mInvestProcessDesc.setTextColor(mUnSelectedColor);
        mInvestProcessDesc.setText(R.string.invest);
    }


}
