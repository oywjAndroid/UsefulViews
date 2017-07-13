package com.oywj.usefulviews.ui.fragments;

import android.os.Bundle;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.basic.BasicFragment;
import com.oywj.usefulviews.ui.basic.swipeback.SwipeBackFragment;
import com.oywj.usefulviews.ui.views.navigate.BottomBar;
import com.oywj.usefulviews.ui.views.navigate.BottomBarTab;

import butterknife.BindView;

public class MainFragment extends BasicFragment {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;

    @BindView(R.id.bottom_navigation_bar)
    BottomBar mBottomBar;

    SwipeBackFragment[] fragments = new SwipeBackFragment[4];

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGenerateLayoutResID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onBindDataToViews() {
        setSwipeBackEnable(false);
        activeFragment();
        activeViews();
    }

    private void activeFragment() {
        FinancialFragment firstFragment = findChildFragment(FinancialFragment.class);
        if (firstFragment == null) {
            fragments[FIRST] = FinancialFragment.newInstance();
            fragments[SECOND] = AccountFragment.newInstance();
            fragments[THIRD] = DiscoverFragment.newInstance();
            fragments[FOUR] = SafetyFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, FIRST, fragments);
        } else {
            fragments[FIRST] = firstFragment;
            fragments[SECOND] = findChildFragment(AccountFragment.class);
            fragments[THIRD] = findChildFragment(DiscoverFragment.class);
            fragments[FOUR] = findChildFragment(SafetyFragment.class);
        }
    }

    private void activeViews() {
        mBottomBar
                .addItem(new BottomBarTab(getActivity(), R.mipmap.icon_financial_normal, "").setIconSize(50))
                .addItem(new BottomBarTab(getActivity(), R.mipmap.icon_find_normal, "").setIconSize(50))
                .addItem(new BottomBarTab(getActivity(), R.mipmap.icon_account_normal, "").setIconSize(50))
                .addItem(new BottomBarTab(getActivity(), R.mipmap.icon_safety_normal, "").setIconSize(50));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(fragments[position], fragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
