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
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.MallHomepageBean;
import com.wlan.familymembers.view.activity.mall.MallDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/18.
 */

public class MallGridAdapter extends DelegateAdapter.Adapter<MallGridAdapter.ViewHolder> {
    Context context;
    int width;
    int singlewidth;
    List<MallHomepageBean> contentList;

    public MallGridAdapter(Context context,List<MallHomepageBean> mallBottomBeans1) {
        this.context = context;
        this.contentList =mallBottomBeans1;
        width = ScreenUtils.getScreenWidth();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setSpanCount(2);
        gridLayoutHelper.setHGap(ConvertUtils.dp2px(5));
        gridLayoutHelper.setGap(ConvertUtils.dp2px(10));
        gridLayoutHelper.setPaddingBottom(ConvertUtils.dp2px(10));
        gridLayoutHelper.setPaddingLeft(ConvertUtils.dp2px(5));
        gridLayoutHelper.setAutoExpand(false);
        return gridLayoutHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.item_mall_grid_two, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.ivImg.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth() / 2-ConvertUtils.dp2px(10);
        layoutParams.height = layoutParams.width;
        holder.ivImg.setLayoutParams(layoutParams);
        Glide.with(context).load(HttpUtils.BASE_URL + contentList.get(position).getGoods().getGoodsPic()).into(holder.ivImg);
        holder.tvName.setText(contentList.get(position).getGoods().getGoodsName());
        holder.tvMoney.setText("￥"+contentList.get(position).getGoods().getGoodsPrice());
        holder.tvState.setText(contentList.get(position).getGoods().getGoodsTag().getTagName());

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MallDetailsActivity.class);
                intent.putExtra("goodsId",contentList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return contentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
