package com.wlan.familymembers.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.OneNBean;
import com.wlan.familymembers.view.activity.home.HomeCommonActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/17.
 */

public class OneTrailerNAdapter extends DelegateAdapter.Adapter {
    private Context context;
    List<OneNBean> oneNBeans;

    public OneTrailerNAdapter(Context context,List<OneNBean> oneNBeans) {
        this.context = context;
        this.oneNBeans=oneNBeans;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(3);
        onePlusNLayoutHelper.setBgColor(Color.parseColor("#ffffff"));
        onePlusNLayoutHelper.setColWeights(new float[]{50f});
        onePlusNLayoutHelper.setRowWeight(50f);
        return onePlusNLayoutHelper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_one_trailer_n, parent, false);
        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolderList viewHolderList= (ViewHolderList) holder;
        viewHolderList.tvTitle.setText(oneNBeans.get(position).getTitle());
        viewHolderList.tvFuTitle.setText(oneNBeans.get(position).getSubTitle());
            Glide.with(context).load(HttpUtils.BASE_URL+oneNBeans.get(position).getPic()).into(viewHolderList.ivImg);
        viewHolderList.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HomeCommonActivity.class);
                intent.putExtra("type",oneNBeans.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return oneNBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderList extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_fu_title)
        TextView tvFuTitle;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.v_one)
        View vOne;
        @BindView(R.id.v_two)
        View vTwo;
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;

        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
