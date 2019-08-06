package com.wlan.familymembers.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.view.activity.goodsorder.ServicePaymentDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/26.
 */

public class TimeAdapter extends RecyclerView.Adapter {
    Context context;
    String[] time;

    public TimeAdapter(Context context, String[] time) {
        this.context = context;
        this.time = time;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_time, parent, false);
        ViewHolderList viewHolderList = new ViewHolderList(view);
        return viewHolderList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
       ViewHolderList viewHolderList = (ViewHolderList) holder;
       for(int i=0;i<time.length;i++){
           viewHolderList.tvTime.setText(time[position]);
       }

        String s = SPUtils.getInstance().getString("Itemtiemosition");
        String s2 = String.valueOf(position);
        if (s.equals(s2)) {
            viewHolderList.tvTime.setTextColor(Color.RED);
            viewHolderList.ivChecked.setVisibility(View.VISIBLE);
        } else {
            viewHolderList.tvTime.setTextColor(Color.BLACK);
            viewHolderList.ivChecked.setVisibility(View.GONE);
        }

        viewHolderList.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put("Itemtiemosition", String.valueOf(position));
                //触发点击时间后重新加载数据
                ServicePaymentDetailsActivity.instance.itemposition(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return time.length;
    }

    static class ViewHolderList extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_checked)
        ImageView ivChecked;

        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
