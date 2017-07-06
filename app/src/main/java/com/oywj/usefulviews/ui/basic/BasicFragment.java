package com.oywj.usefulviews.ui.basic;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oywj.usefulviews.ui.basic.swipeback.SwipeBackFragment;

/**
 * {#BasicFragment}作为Fragment的基类，同样需要承担与BasicActivity类似的功能模块。
 */
public class BasicFragment extends SwipeBackFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
