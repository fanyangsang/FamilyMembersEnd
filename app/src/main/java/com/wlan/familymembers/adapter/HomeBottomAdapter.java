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

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.GoodsBean;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.view.activity.mall.MallDetailsActivity;
import com.wlan.familymembers.view.activity.service.ServiceDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 类作用：
 * Created by Administrator on 2018/9/17.
 */

public class HomeBottomAdapter extends DelegateAdapter.Adapter<HomeBottomAdapter.ViewHolderTail>{
    private Context context;
    private int width;
    private int singlewidth;
    private int upCount;
    private List<MallBottomBean> mallBottomBeans;


    public HomeBottomAdapter(Context context,List<MallBottomBean> mallBottomBeans) {
        this.context = context;
        width = ScreenUtils.getScreenWidth();
        this.mallBottomBeans=mallBottomBeans;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setHGap(ConvertUtils.dp2px(10));
        gridLayoutHelper.setGap(ConvertUtils.dp2px(10));
        gridLayoutHelper.setPaddingLeft(ConvertUtils.dp2px(10));
        gridLayoutHelper.setPaddingRight(ConvertUtils.dp2px(10));
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 4+upCount) {
                    return 2;
                }
                return 1;
            }
        });
        return gridLayoutHelper;
    }

    @NonNull
    @Override
    public ViewHolderTail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(context).inflate(R.layout.item_home_grid_bottom, parent, false);
            singlewidth = (width - 30) / 2;
            ViewHolderTail viewHolderTail = new ViewHolderTail(view);
            ViewGroup.LayoutParams layoutParams = viewHolderTail.ivImg.getLayoutParams();
            layoutParams.width = singlewidth;
            layoutParams.height = singlewidth;
            viewHolderTail.ivImg.setLayoutParams(layoutParams);
            return viewHolderTail;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTail holder, final int position) {
        //1是商品  2是服务
        Glide.with(context).load(HttpUtils.BASE_URL+mallBottomBeans.get(position).getGoodsPic()).into(holder.ivImg);
        holder.tvName.setText(mallBottomBeans.get(position).getGoodsName());
        holder.tvMoney.setText(mallBottomBeans.get(position).getGoodsPrice());
        if (mallBottomBeans.get(position).getType().equals("1")){
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText(mallBottomBeans.get(position).getGoodsTag());
            holder.rlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MallDetailsActivity.class);
                    intent.putExtra("goodsId",mallBottomBeans.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }else {
            holder.tvState.setVisibility(View.GONE);
            holder.rlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ServiceDetailsActivity.class);
                    intent.putExtra("serviceId",mallBottomBeans.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mallBottomBeans.size();
    }

    static class ViewHolderTail extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;

        ViewHolderTail(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
