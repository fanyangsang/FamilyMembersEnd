package com.wlan.familymembers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlan.familymembers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class MyCouponAdapter extends RecyclerView.Adapter {
    private Context context;

    public MyCouponAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_my_coupon, parent, false);
        return new ViewHolderCoupon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolderCoupon extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_rules)
        TextView tvRules;
        @BindView(R.id.tv_coupon_name)
        TextView tvCouponName;
        @BindView(R.id.tv_range)
        TextView tvRange;
        @BindView(R.id.tv_grant_date)
        TextView tvGrantDate;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_use_state)
        TextView tvUseState;

        ViewHolderCoupon(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
