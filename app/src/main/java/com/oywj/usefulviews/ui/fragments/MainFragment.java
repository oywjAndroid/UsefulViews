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

    @BindView(R.id.bottom_navigation_bar)
    BottomBar mBottomBar;

    SwipeBackFragment[] fragments = new SwipeBackFragment[3];

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
        MessageFragment firstFragment = findChildFragment(MessageFragment.class);
        if (firstFragment == null) {
            fragments[FIRST] = MessageFragment.newInstance();
            fragments[SECOND] = ContactFragment.newInstance();
            fragments[THIRD] = DiscoverFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, FIRST, fragments);
        } else {
            fragments[FIRST] = firstFragment;
            fragments[SECOND] = findChildFragment(ContactFragment.class);
            fragments[THIRD] = findChildFragment(DiscoverFragment.class);
        }



    }

    private void activeViews() {
        mBottomBar
                .addItem(new BottomBarTab(getActivity(), R.mipmap.ic_message_white_24dp, "消息"))
                .addItem(new BottomBarTab(getActivity(), R.mipmap.ic_account_circle_white_24dp, "联系人"))
                .addItem(new BottomBarTab(getActivity(), R.mipmap.ic_discover_white_24dp, "朋友圈"));

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
