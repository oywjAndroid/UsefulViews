package com.oywj.usefulviews.ui.adapter;

import android.content.Context;

import com.oywj.usefulviews.ui.basic.adapter.listAdapter.CommonAdapter;
import com.oywj.usefulviews.ui.basic.adapter.listAdapter.ViewHolder;

import java.util.List;

/**
 * Created by AndroidDev on 2017/8/17.
 */
public class WalletAdapter extends CommonAdapter<String> {

    public WalletAdapter(Context context, int layoutId, List<String> data) {
        super(context, layoutId, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {


    }
}
