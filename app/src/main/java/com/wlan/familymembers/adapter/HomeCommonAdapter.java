package com.wlan.familymembers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.TopBean;
import com.wlan.familymembers.utils.RecyclerViewUtil;
import com.wlan.familymembers.view.activity.home.WebViewActivity;
import com.wlan.familymembers.view.activity.mall.MallDetailsActivity;
import com.wlan.familymembers.view.activity.service.ServiceDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/17.
 */

public class HomeCommonAdapter extends RecyclerView.Adapter {
    private final int px3 = ConvertUtils.dp2px(3);
    private final int px10 = ConvertUtils.dp2px(10);

    int singlewidthTail = 0;
    int width = 0;
    String type;
    Context context;
    TopBean topBean;
    List<MallBottomBean> mallBottomBeans;

    public HomeCommonAdapter(Context context,TopBean topBean,List<MallBottomBean> mallBottomBeans,String type) {
        this.context = context;
        this.topBean=topBean;
        this.mallBottomBeans=mallBottomBeans;
        width = ScreenUtils.getScreenWidth();
        this.type=type;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_common_head, parent, false);
            return new ViewHolderHead(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_common_tail, parent, false);
            return new ViewHolderTail(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ViewHolderHead viewHolderHead = (ViewHolderHead) holder;
            if (topBean!=null){
                viewHolderHead.tvTitle.setText(topBean.getSubTitle());
                if (topBean.getImg().contains("Http")){
                    Glide.with(context).load(topBean.getImg()).into(viewHolderHead.ivImg);
                }else {
                    Glide.with(context).load(HttpUtils.BASE_URL+topBean.getImg()).into(viewHolderHead.ivImg);
                }
                viewHolderHead.ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, WebViewActivity.class);
                        intent.putExtra("title",topBean.getTitle());
                        intent.putExtra("uri",topBean.getUrl());
                        context.startActivity(intent);
                    }
                });
            }

        } else {
            ViewHolderTail viewHolderTail = (ViewHolderTail) holder;
            if (mallBottomBeans.get(position - 1).getGoodsPic().contains("Http")) {
                Glide.with(context).load(mallBottomBeans.get(position - 1).getGoodsPic()).into(viewHolderTail.ivImgTwo);
            } else {
                Glide.with(context).load(HttpUtils.BASE_URL + mallBottomBeans.get(position - 1).getGoodsPic()).into(viewHolderTail.ivImgTwo);
            }
                viewHolderTail.tvName.setText(mallBottomBeans.get(position - 1).getGoodsName());
                viewHolderTail.tvMoney.setText("￥" + mallBottomBeans.get(position - 1).getGoodsPrice());

                if (!type.equals("1")) {
                    ViewGroup.LayoutParams layoutParams = viewHolderTail.cvView.getLayoutParams();
                    RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(layoutParams);

                    if ((position - 1) % 2 == 0) {
                        layoutParams1.setMargins(px3, px3, px10, px3);
                    } else {
                        layoutParams1.setMargins(px10, px3, px3, px3);
                    }
                    viewHolderTail.cvView.setLayoutParams(layoutParams1);


                    ViewGroup.LayoutParams ivParams = viewHolderTail.ivImgTwo.getLayoutParams();
                    ivParams.width = (width - 26) / 2;
                    ivParams.height = (int) (ivParams.width * 0.65);
                    viewHolderTail.ivImgTwo.setLayoutParams(ivParams);
                    viewHolderTail.btnRightRob.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ServiceDetailsActivity.class);
                            intent.putExtra("serviceId", mallBottomBeans.get(position - 1).getId());
                            context.startActivity(intent);
                        }
                    });
                } else {
                    viewHolderTail.cvView.setRadius(0);
                    ViewGroup.LayoutParams layoutParams = viewHolderTail.cvView.getLayoutParams();
                    RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(layoutParams);
                    layoutParams1.setMargins(0, 0, 0, px3 * 2);
                    viewHolderTail.cvView.setLayoutParams(layoutParams1);
                }
            viewHolderTail.cvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MallDetailsActivity.class);
                    intent.putExtra("goodsId", mallBottomBeans.get(position - 1).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mallBottomBeans.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            default:
                return 1;
        }
    }

    public void updataBean(TopBean topBean) {
        this.topBean=topBean;
        notifyDataSetChanged();
    }


    static class ViewHolderTail extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img_two)
        ImageView ivImgTwo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.btn_right_rob)
        Button btnRightRob;
        @BindView(R.id.cv_view)
        CardView cvView;

        ViewHolderTail(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class ViewHolderHead extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
