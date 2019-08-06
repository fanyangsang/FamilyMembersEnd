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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.ServiceClassificationBean;
import com.wlan.familymembers.view.activity.service.ServiceListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class HomeGridViewAdapter extends DelegateAdapter.Adapter {
    private Context context;
    List<ServiceClassificationBean> serviceClassificationBeans;
    private switchPageListener switchPageListener;

    public HomeGridViewAdapter(Context context,List<ServiceClassificationBean> serviceClassificationBeans) {
        this.context = context;
        this.serviceClassificationBeans=serviceClassificationBeans;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setHGap(ConvertUtils.dp2px(5));
        gridLayoutHelper.setGap(ConvertUtils.dp2px(5));
        gridLayoutHelper.setBgColor(Color.parseColor("#ffffff"));
        gridLayoutHelper.setMarginBottom(ConvertUtils.dp2px(8));
        gridLayoutHelper.setPaddingTop(ConvertUtils.dp2px(16));
        gridLayoutHelper.setPaddingBottom(ConvertUtils.dp2px(16));
        return gridLayoutHelper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_view, parent, false);
        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolderList viewHolderList= (ViewHolderList) holder;
                Glide.with(context).load(HttpUtils.BASE_URL+serviceClassificationBeans.get(position).getPic()).into(viewHolderList.ivImg);

        viewHolderList.tvName.setText(serviceClassificationBeans.get(position).getName());
        if (serviceClassificationBeans.get(position).getName().equals("我的商城")){
            switchPageListener.switchPage();


        }else {
            viewHolderList.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ServiceListActivity.class);
                    intent.putExtra("id",serviceClassificationBeans.get(position).getId());
                    intent.putExtra("name",serviceClassificationBeans.get(position).getName());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return serviceClassificationBeans.size();
    }

    static class ViewHolderList extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface switchPageListener{
        void switchPage();
    }
    public void setListener(switchPageListener switchPageListener){
        this.switchPageListener=switchPageListener;

    }


}
