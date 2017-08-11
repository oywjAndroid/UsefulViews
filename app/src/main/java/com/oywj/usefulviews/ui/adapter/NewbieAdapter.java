package com.oywj.usefulviews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.views.financial.NewbieCategoryLayout;

import java.util.List;

import butterknife.BindView;
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
        holder.newbieLayout.setNewbiePercentIncome(mNewbieData.get(position));

        if (position == 0) {
            holder.newbieLayout.setNewbie7DayVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mNewbieData == null ? 0 : mNewbieData.size();
    }

    public class ViewHolder extends AutoLayoutViewHolder {

        @BindView(R.id.newbie_category_layout)
        NewbieCategoryLayout newbieLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
