package com.wlan.familymembers.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.rwq.framworkapp.net.HttpUtils;
import com.squareup.picasso.Picasso;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.LoopBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.view.activity.home.WebViewActivity;
import com.wlan.familymembers.view.activity.mall.MallDetailsActivity;
import com.wlan.familymembers.view.activity.service.ServiceDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 类作用：
 * Created by Administrator on 2018/10/15.
 */

public class SowingMapAdapter extends LoopPagerAdapter {
    private Context context;
    private List<LoopBean> loopBeans;
    private ArrayList<String> sowings;

    public SowingMapAdapter(RollPagerView rollPagerView, Context context, List<LoopBean> loopBeans) {
        super(rollPagerView);
        this.context = context;
        this.loopBeans = loopBeans;
    }

    public SowingMapAdapter(RollPagerView rollPagerView, Context context, ArrayList<String> sowings) {
        super(rollPagerView);
        this.context = context;
        this.sowings = sowings;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        ImageView view = (ImageView) View.inflate(context, R.layout.loop_img, null);
        if (loopBeans != null) {
            //遍历
            for (int i = 0; i < loopBeans.size(); i++) {
                //判断getPic（）是否含有http的字段
                if (loopBeans.get(i).getPic().contains("Http")) {

                    Picasso.with(context).load(loopBeans.get(i).getPic()).into(view);
                } else {
                    Picasso.with(context).load(HttpUtils.BASE_URL + loopBeans.get(position).getPic()).into(view);
                }
                //1商品  2链接   3服务
                if (loopBeans.get(position).getType().equals("1")) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MallDetailsActivity.class);
                            intent.putExtra(" goodsId", loopBeans.get(position).getContent());
                            context.startActivity(intent);
                        }
                    });

                } else if (loopBeans.get(position).getType().equals("2")) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, WebViewActivity.class);
                            intent.putExtra("uri", loopBeans.get(position).getContent());
                            intent.putExtra("title", "图片");
                            context.startActivity(intent);
                        }
                    });

                } else {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ServiceDetailsActivity.class);
                            intent.putExtra("serviceId", loopBeans.get(position).getContent());
                            context.startActivity(intent);
                        }
                    });

                }

            }
        } else if (sowings != null) {
            for (int i = 0; i < sowings.size(); i++) {
                //判断getPic（）是否含有http的字段
                Log.i("imgUrl", sowings.get(position));
                if (sowings.get(i).contains("Http")) {
                    Picasso.with(context).load(sowings.get(i)).into(view);
                }else{
                    Picasso.with(context).load(HttpUtils.BASE_URL + sowings.get(position)).into(view);
                }
            }
        }
        return view;
    }

    @Override
    public int getRealCount() {
        if (loopBeans != null) {
            return loopBeans.size();
        } else {
            return sowings.size();
        }

    }
}
