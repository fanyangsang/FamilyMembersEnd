package com.wlan.familymembers.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.view.activity.goodsorder.ServicePaymentDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/26.
 */

public class DateAdapter extends RecyclerView.Adapter {
    Context context;
    private List<String> date;
    private List<String> week;

    public DateAdapter(Context context, List<String> date, List<String> week) {
        this.context = context;
        this.date = date;
        this.week = week;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);

        ViewHolderList viewHolderList = new ViewHolderList(view);
        return viewHolderList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderList viewHolderList = (ViewHolderList) holder;
        for (int i = 0; i < date.size(); i++) {
            viewHolderList.tvWeekNumber.setText(week.get(position));
            viewHolderList.tvDate.setText(date.get(position));
        }
        //获取布局
        View view2 = viewHolderList.rlitemdate;
//        viewHolderList.rlitemdate.setContentView(view);
        //获取布局参数
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth()/3;
        layoutParams.height = ScreenUtils.getScreenWidth()/5;

        String s = SPUtils.getInstance().getString("ItemDatePosition");
        String s2 = String.valueOf(position);
        if (s.equals(s2)) {
            viewHolderList.tvDate.setTextColor(Color.RED);
            viewHolderList.tvWeekNumber.setTextColor(Color.RED);
            viewHolderList.vLine.setBackgroundColor(Color.RED);
        } else {
            viewHolderList.tvDate.setTextColor(Color.BLACK);
            viewHolderList.tvWeekNumber.setTextColor(Color.BLACK);
            viewHolderList.vLine.setBackgroundColor(Color.parseColor("#ada6a2a2"));
        }

        viewHolderList.rlitemdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put("ItemDatePosition", String.valueOf(position));
                //触发点击时间后重新加载数据
                ServicePaymentDetailsActivity.instance.dateposition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class ViewHolderList extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_week_number)
        TextView tvWeekNumber;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.v_line)
        View vLine;
        @BindView(R.id.rl_item_date)
        RelativeLayout rlitemdate;

        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
