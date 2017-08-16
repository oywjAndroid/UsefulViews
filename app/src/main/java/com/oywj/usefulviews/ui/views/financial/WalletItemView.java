package com.oywj.usefulviews.ui.views.financial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.autolayout.AutoFrameLayout;

import butterknife.ButterKnife;

/**
 * Created by AndroidDev on 2017/8/16.
 */
public class WalletItemView extends AutoFrameLayout {
    public WalletItemView(Context context) {
        this(context, null);
    }

    public WalletItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WalletItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.wallet_view, this, true);
        ButterKnife.bind(this);
    }
}
