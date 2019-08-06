package com.wlan.familymembers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class MyPointAdapter extends RecyclerView.Adapter {
    private Context context;
    static final int HEAD=1;
    static final int center=2;
    static final int tatil=3;
    public MyPointAdapter(Context context){
        this.context=context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
