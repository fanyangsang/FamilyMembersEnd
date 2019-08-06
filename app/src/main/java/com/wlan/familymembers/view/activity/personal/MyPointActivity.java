package com.wlan.familymembers.view.activity.personal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.MyPointAdapter;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.MyPointModel;
import com.wlan.familymembers.presenter.personal.MyPointPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class MyPointActivity extends BaseActivity<MyPointModel, MyPointPresenter> implements Contract.MyPointContract.View {
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private MyPointAdapter myPointAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_point;
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(linearLayoutManager);
        myPointAdapter=new MyPointAdapter(this);
        rvContent.setAdapter(myPointAdapter);


    }

    @Override
    public void onEvent() {

    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
}
