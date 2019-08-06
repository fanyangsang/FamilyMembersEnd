package com.wlan.familymembers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.HelpCenterBean;
import com.wlan.familymembers.dingyiview.CircleImageView;
import com.wlan.familymembers.view.activity.personal.HelpCenterDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/29.
 */

public class PersonalCenterAdapter extends RecyclerView.Adapter {
    public static final int HEAD = 0;
    public static final int BOTTOM = 1;
    Context context;
    List<HelpCenterBean> helpCenterBeans;

    public PersonalCenterAdapter(Context context,List<HelpCenterBean> helpCenterBeans) {
        this.context = context;
        this.helpCenterBeans=helpCenterBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == HEAD) {
            view = LayoutInflater.from(context).inflate(R.layout.item_personal_help_center_head, parent, false);
            return new ViewHolderHead(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_personal_help_center_bottom, parent, false);
            return new ViewHolderBottom(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (position!=0){
            ViewHolderBottom viewHolderBottom= (ViewHolderBottom) holder;
            viewHolderBottom.tvQuestion.setText(helpCenterBeans.get(position-1).getTitle());
            viewHolderBottom.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, HelpCenterDetailsActivity.class);
                    intent.putExtra("question",helpCenterBeans.get(position-1).getTitle());
                    intent.putExtra("id",helpCenterBeans.get(position-1).getId());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return helpCenterBeans.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else {
            return BOTTOM;
        }
    }

    static class ViewHolderHead extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_img)
        CircleImageView civImg;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolderHead(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderBottom extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_question)
        TextView tvQuestion;
        @BindView(R.id.iv_right_key)
        ImageView ivRightKey;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        ViewHolderBottom(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
