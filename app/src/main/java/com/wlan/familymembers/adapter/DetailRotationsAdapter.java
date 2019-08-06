package com.wlan.familymembers.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.rwq.framworkapp.net.HttpUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 类作用：服务详情、商品详情适配器
 * Created by Administrator on 2018/12/10.
 */

public class DetailRotationsAdapter extends StaticPagerAdapter {
    private Context context;
    private List<String> sowings;

    public DetailRotationsAdapter(Context context, List<String> sowings) {
        this.context = context;
        this.sowings = sowings;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        for (int i = 0; i < sowings.size(); i++) {
            //                if (sowings.get(i).contains("http")) {
            //                    Picasso.with(context).load(sowings.get(i)).into(view);
            //                } else {
            Picasso.with(context).load(HttpUtils.BASE_URL + sowings.get(position)).into(view);
            //                }
        }
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getCount() {
        return sowings.size();
    }
}
