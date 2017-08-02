package com.oywj.usefulviews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oywj.usefulviews.R;
import com.oywj.usefulviews.data.bean.FinancialData;
import com.oywj.usefulviews.ui.basic.BasicApplication;
import com.oywj.usefulviews.ui.views.BannerLayout;
import com.oywj.usefulviews.ui.views.FinancialHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android on 2017/7/17.
 * 理财页面内容的适配器。
 */
public class FinancialAdapter extends RecyclerView.Adapter<FinancialAdapter.ViewHolder> {

    private List<FinancialData> mData;

    public FinancialAdapter(List<FinancialData> data) {
        mData = data;
    }

    @Override
    public FinancialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(FinancialAdapter.ViewHolder holder, int position) {
        switch (mData.get(position).type) {
            case ViewHolder.VIEW_HOLDER_BILLBOARD:
                handleBillboard((ViewHolderBillboard) holder, position);
                break;
            case ViewHolder.VIEW_HOLDER_USER_PROCESS:
                handleUserProcess((ViewHolderProcess) holder, position);
                break;
            case ViewHolder.VIEW_HOLDER_COIN_PURSE:
                handleCoinPurse((ViewHolderCoinPurse) holder, position);
                break;
            case ViewHolder.VIEW_HOLDER_NOVICE_EXCLUSIVE:
                handleNoviceExclusive((ViewHolderNoviceExclusive) holder, position);
                break;
            case ViewHolder.VIEW_HOLDER_REGULAR_FINANCIAL:
                handleRegularFinancial((ViewHolderRegularFinancial) holder, position);
                break;
            case ViewHolder.VIEW_HOLDER_TRANSFER_PROJECT:
                handleTransferProject((ViewHolderTransferProject) holder, position);
                break;
            default:
                break;
        }
    }

    private void handleTransferProject(ViewHolderTransferProject holder, int position) {
        //TODO
    }

    private void handleRegularFinancial(ViewHolderRegularFinancial holder, int position) {
        //TODO
    }

    private void handleNoviceExclusive(ViewHolderNoviceExclusive holder, int position) {
        //TODO
    }

    private void handleCoinPurse(ViewHolderCoinPurse holder, int position) {
        //TODO
    }

    // 处理用户投资进展
    private void handleUserProcess(ViewHolderProcess holder, int position) {
        holder.financialHolderView.setFinancialStatus(FinancialHolderView.RECHARGE);
    }

    // 处理Banner视图
    private void handleBillboard(ViewHolderBillboard holder, int position) {
        holder.mBannerLayout.setViewUrls(mData.get(position).banners);
        holder.mBannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(BasicApplication.getInstance(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }


    public static abstract class ViewHolder extends AutoLayoutViewHolder {
        // 广告牌
        public static final int VIEW_HOLDER_BILLBOARD = 0;
        // 用户理财进展
        public static final int VIEW_HOLDER_USER_PROCESS = VIEW_HOLDER_BILLBOARD + 1;
        // 新手专享
        public static final int VIEW_HOLDER_NOVICE_EXCLUSIVE = VIEW_HOLDER_USER_PROCESS + 1;
        // 零钱包（灵活理财，按日计息）
        public static final int VIEW_HOLDER_COIN_PURSE = VIEW_HOLDER_NOVICE_EXCLUSIVE + 1;
        // 定期理财
        public static final int VIEW_HOLDER_REGULAR_FINANCIAL = VIEW_HOLDER_COIN_PURSE + 1;
        // 转让项目
        public static final int VIEW_HOLDER_TRANSFER_PROJECT = VIEW_HOLDER_REGULAR_FINANCIAL + 1;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public static ViewHolder create(ViewGroup viewHolder, int type) {
            switch (type) {
                case VIEW_HOLDER_BILLBOARD:
                    return ViewHolderBillboard.create(viewHolder);
                case VIEW_HOLDER_USER_PROCESS:
                    return ViewHolderProcess.create(viewHolder);
                case VIEW_HOLDER_NOVICE_EXCLUSIVE:
                    return ViewHolderNoviceExclusive.create(viewHolder);
                case VIEW_HOLDER_COIN_PURSE:
                    return ViewHolderCoinPurse.create(viewHolder);
                case VIEW_HOLDER_REGULAR_FINANCIAL:
                    return ViewHolderRegularFinancial.create(viewHolder);
                case VIEW_HOLDER_TRANSFER_PROJECT:
                    return ViewHolderTransferProject.create(viewHolder);
                default:
                    return null;
            }
        }
    }

    // 广告牌
    public static class ViewHolderBillboard extends ViewHolder {

        @BindView(R.id.banner_layout)
        BannerLayout mBannerLayout;

        public ViewHolderBillboard(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static ViewHolderBillboard create(ViewGroup parent) {
            return new ViewHolderBillboard(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.view_holder_billboard,
                            parent,
                            false)
            );
        }
    }

    // 用户理财进展
    public static class ViewHolderProcess extends ViewHolder {
        @BindView(R.id.financial_holder_view)
        FinancialHolderView financialHolderView;

        public ViewHolderProcess(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public static ViewHolderProcess create(ViewGroup parent) {
            return new ViewHolderProcess(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.view_holder_user_process,
                            parent,
                            false)
            );
        }
    }

    // 新手专享
    public static class ViewHolderNoviceExclusive extends ViewHolder {

        public ViewHolderNoviceExclusive(View itemView) {
            super(itemView);
        }

        public static ViewHolderNoviceExclusive create(ViewGroup parent) {
            return new ViewHolderNoviceExclusive(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.view_holder_novice_exclusive,
                            parent,
                            false)
            );
        }
    }

    // 零钱包（灵活理财，按日计息）
    public static class ViewHolderCoinPurse extends ViewHolder {

        public ViewHolderCoinPurse(View itemView) {
            super(itemView);
        }

        public static ViewHolderCoinPurse create(ViewGroup parent) {
            return new ViewHolderCoinPurse(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.view_holder_coin_purse,
                            parent,
                            false)
            );
        }
    }

    // 定期理财
    public static class ViewHolderRegularFinancial extends ViewHolder {

        public ViewHolderRegularFinancial(View itemView) {
            super(itemView);
        }

        public static ViewHolderRegularFinancial create(ViewGroup parent) {
            return new ViewHolderRegularFinancial(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.view_holder_regular_financial,
                            parent,
                            false)
            );
        }
    }

    // 转让项目
    public static class ViewHolderTransferProject extends ViewHolder {

        public ViewHolderTransferProject(View itemView) {
            super(itemView);
        }

        public static ViewHolderTransferProject create(ViewGroup parent) {
            return new ViewHolderTransferProject(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.view_holder_transfer_project,
                            parent,
                            false)
            );
        }
    }


}
