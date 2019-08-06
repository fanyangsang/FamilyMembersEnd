package com.wlan.familymembers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlan.familymembers.R;
import com.wlan.familymembers.view.activity.mall.MallDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class HomeClssifyAdapter extends RecyclerView.Adapter {
    private Context context;

    public HomeClssifyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_classify_content, parent, false);
        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderList viewHolderList= (ViewHolderList) holder;
        viewHolderList.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MallDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolderList extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_title_two)
        TextView tvTitleTwo;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;
        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
