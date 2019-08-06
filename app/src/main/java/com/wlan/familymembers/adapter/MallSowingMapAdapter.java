package com.wlan.familymembers.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;
import com.wlan.familymembers.R;
import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/18.
 */

public class MallSowingMapAdapter extends LoopPagerAdapter{
    private Context context;
    List<String> img;
    public MallSowingMapAdapter(RollPagerView rollPagerView, Context context,List<String> img){
        super(rollPagerView);
        this.context=context;
        this.img=img;
    }
    @Override
    public View getView(ViewGroup container, int position) {
        String path="";
        if (img!=null){
            path=img.get(position);
        }
        ImageView imageView= (ImageView) View.inflate(context, R.layout.item_mall_banners_content,null);
        Picasso.with(context).load(path).into(imageView);
        return imageView;
    }

    @Override
    public int getRealCount() {
        return img.size();
    }
}
