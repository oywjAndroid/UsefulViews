package com.oywj.usefulviews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oywj.usefulviews.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Android on 2017/8/4.
 */
public class NewbieAdapter extends RecyclerView.Adapter<NewbieAdapter.ViewHolder> {
    private List<String> mNewbieData;

    public NewbieAdapter(List<String> newbieData) {
        mNewbieData = newbieData;
    }

    @Override
    public NewbieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_newbie, parent, false));
    }

    @Override
    public void onBindViewHolder(NewbieAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mNewbieData == null ? 0 : mNewbieData.size();
    }

    public class ViewHolder extends AutoLayoutViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
