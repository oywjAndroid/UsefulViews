package com.oywj.usefulviews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.oywj.usefulviews.autolayout.utils.AutoUtils;

/**
 * Created by Android on 2017/7/28.
 * Auto layout viewHolder
 */
public class AutoLayoutViewHolder extends RecyclerView.ViewHolder {
    public AutoLayoutViewHolder(View itemView) {
        super(itemView);
        AutoUtils.auto(itemView);
    }
}
