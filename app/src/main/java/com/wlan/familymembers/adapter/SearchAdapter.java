package com.wlan.familymembers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.GoodsBean;
import com.wlan.familymembers.bean.ServiceListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wlan.familymembers.view.activity.home.SearchActivity.TYPE_GOODS;

/**
 * 类作用：
 * Created by Administrator on 2018/11/7.
 */

public class SearchAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsBean> goodsBeans;
    private List<ServiceListBean> serviceListBeans;
    private int type;

    public SearchAdapter(Context context, List<GoodsBean> goodsBeans, List<ServiceListBean> serviceListBeans, int type) {
        this.context = context;
        this.goodsBeans = goodsBeans;
        this.serviceListBeans = serviceListBeans;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == TYPE_GOODS) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_mall_grid_two, parent, false);
            return new ViewHolderGoods(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_classify_content, parent, false);
            return new ViewHolderService(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (type==TYPE_GOODS){
            ViewHolderGoods viewHolderGoods= (ViewHolderGoods) holder;
            Glide.with(context).load(HttpUtils.BASE_URL+goodsBeans.get(position).getPic()).into(viewHolderGoods.ivImg);
            viewHolderGoods.tvName.setText(goodsBeans.get(position).getName());
            viewHolderGoods.tvMoney.setText(goodsBeans.get(position).getPrice());
            viewHolderGoods.tvState.setText(goodsBeans.get(position).getGoodsTagName());
        }else {
            ViewHolderService viewHolderService= (ViewHolderService) holder;
            Glide.with(context).load(HttpUtils.BASE_URL+serviceListBeans.get(position).getPic()).into(viewHolderService.ivImg);
            viewHolderService.tvTitle.setText(serviceListBeans.get(position).getName());
            viewHolderService.tvTitleTwo.setText(serviceListBeans.get(position).getSellPoint());
            viewHolderService.tvMoney.setText(serviceListBeans.get(position).getPrice());
        }

    }

    @Override
    public int getItemCount() {
        if (type == TYPE_GOODS) {
            return goodsBeans.size();
        } else {
            return serviceListBeans.size();
        }
    }

    static class ViewHolderGoods extends RecyclerView.ViewHolder {
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

        ViewHolderGoods(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderService extends RecyclerView.ViewHolder {
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

        ViewHolderService(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
