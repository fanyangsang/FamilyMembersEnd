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
import com.jude.rollviewpager.RollPagerView;
import com.wlan.familymembers.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/18.
 */

public class MallBannersAdapter extends DelegateAdapter.Adapter {
    private Context context;
    int width;
    private MallSowingMapAdapter mallSowingMapAdapter;

    public MallBannersAdapter(Context context) {
        this.context = context;
        width= ScreenUtils.getScreenWidth();
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_mall_banners, parent, false);
        ViewGroup.LayoutParams layoutParams=view.findViewById(R.id.rvp_content).getLayoutParams();
        layoutParams.width=width;
        layoutParams.height=width*9/16;
        view.findViewById(R.id.rvp_content).setLayoutParams(layoutParams);
        return new ViewHolderBanners(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderBanners viewHolderBanners= (ViewHolderBanners) holder;
        viewHolderBanners.rvpContent.setPlayDelay(3000);
        viewHolderBanners.rvpContent.setAnimationDurtion(1500);
        String imgone = "https://yuanyangjudian.oss-cn-beijing.aliyuncs.com/data/08c58fff-6db5-4752-a254-223b9af6374a";
        String imgtwo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537018142789&di=3e5f91a8702ec61ed429ca39b4c2973d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F038497757a940710000012e7ea0c6ce.jpg";
        String imgthree = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537018142789&di=26ee584e6a8169a229adf7c0b09e6f9d&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F219%2F29%2F3PA45RGN659N.jpg";
        List<String> img=new ArrayList<>();
        img.add(imgone);
        img.add(imgtwo);
        img.add(imgthree);
        mallSowingMapAdapter=new MallSowingMapAdapter(viewHolderBanners.rvpContent,context,img);
        viewHolderBanners.rvpContent.setAdapter(mallSowingMapAdapter);

    }

    @Override
    public int getItemCount() {
        return 1;
    }
    static class ViewHolderBanners extends RecyclerView.ViewHolder {
        @BindView(R.id.rvp_content)
        RollPagerView rvpContent;

        ViewHolderBanners(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
