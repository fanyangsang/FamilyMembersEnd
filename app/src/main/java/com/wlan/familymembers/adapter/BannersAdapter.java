package com.wlan.familymembers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.rwq.framworkapp.net.HttpUtils;
import com.squareup.picasso.Picasso;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.LoopBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class BannersAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private int singlewidth=0;
    private List<LoopBean> loopBeans;
    private SowingMapAdapter sowingMapAdapter;
    public BannersAdapter(Context context,List<LoopBean> loopBeans){
        this.context=context;
        this.loopBeans=loopBeans;
    }



    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int width=ScreenUtils.getScreenWidth();
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_sowing_map_content, parent, false);
        ViewGroup.LayoutParams layoutParams=view.findViewById(R.id.rpv_content).getLayoutParams();
        layoutParams.width=width;
        singlewidth=width;
        layoutParams.height=singlewidth*9/16;
        view.findViewById(R.id.rpv_content).setLayoutParams(layoutParams);
        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderList viewHolderList= (ViewHolderList) holder;
        viewHolderList.rpvContent.setPlayDelay(3000);
        viewHolderList.rpvContent.setAnimationDurtion(1500);
        sowingMapAdapter=new SowingMapAdapter(viewHolderList.rpvContent,context,loopBeans);
        viewHolderList.rpvContent.setAdapter(sowingMapAdapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolderList extends RecyclerView.ViewHolder {
        @BindView(R.id.rpv_content)
        RollPagerView rpvContent;

        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
