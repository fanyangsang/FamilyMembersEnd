package com.wlan.familymembers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.MallBottomBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/11/3.
 */

public class HeadAdapter extends DelegateAdapter.Adapter<HeadAdapter.ViewHolderHead> {
    private Context context;
    private String content;
    private int type;
    private boolean isDisplay=true;

    public HeadAdapter(Context context, int type, String content) {
        this.type = type;
        this.content = content;
        this.context = context;

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @NonNull
    @Override
    public ViewHolderHead onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_mall_grid_one, parent, false);
        return new ViewHolderHead(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHead holder, int position) {
        if (isDisplay) {
            holder.llContent.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(content);
        }else {
            holder.llContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void isDiaplay(boolean b) {
        isDisplay = b;
    }

    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
