package com.wlan.familymembers.view.activity.personal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.MyCouponAdapter;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.MyCouponModel;
import com.wlan.familymembers.presenter.personal.MyCouponPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class MyCouponActivity extends BaseActivity<MyCouponModel, MyCouponPresenter> implements Contract.MyCouponContract.View {

    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_used)
    TextView tvUsed;
    @BindView(R.id.v_one)
    View vOne;
    @BindView(R.id.ll_used)
    LinearLayout llUsed;
    @BindView(R.id.tv_expired)
    TextView tvExpired;
    @BindView(R.id.v_two)
    View vTwo;
    @BindView(R.id.ll_expired)
    LinearLayout llExpired;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private MyCouponAdapter myCouponAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    @Override
    public void initView() {
        tvContent.setText("我的优惠券");
        tvPoints.setText("说明");
        tvPoints.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(linearLayoutManager);
        myCouponAdapter = new MyCouponAdapter(this);
        rvContent.setAdapter(myCouponAdapter);


    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vOne.setVisibility(View.VISIBLE);
                vTwo.setVisibility(View.INVISIBLE);
                tvUsed.setTextColor(Color.parseColor("#dd0d01"));
                tvExpired.setTextColor(Color.parseColor("#000000"));
            }
        });
        llExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vOne.setVisibility(View.INVISIBLE);
                vTwo.setVisibility(View.VISIBLE);
                tvUsed.setTextColor(Color.parseColor("#000000"));
                tvExpired.setTextColor(Color.parseColor("#dd0d01"));
            }
        });
        tvPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyCouponActivity.this,CouponNotesActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

}
