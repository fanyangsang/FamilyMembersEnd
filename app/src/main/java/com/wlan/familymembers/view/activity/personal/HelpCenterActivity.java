package com.wlan.familymembers.view.activity.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.PersonalCenterAdapter;
import com.wlan.familymembers.bean.HelpCenterBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.HelpCenterModel;
import com.wlan.familymembers.presenter.personal.HelpCenterPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class HelpCenterActivity extends BaseActivity<HelpCenterModel, HelpCenterPresenter> implements Contract.HelpCenterContract.View {


    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private PersonalCenterAdapter personalCenterAdapter;
    String userId;
    List<HelpCenterBean> helpCenterBeans;


    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_help_center;
    }

    @Override
    public void initView() {
        tvContent.setText("帮助中心");
        helpCenterBeans=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(linearLayoutManager);
        personalCenterAdapter=new PersonalCenterAdapter(this,helpCenterBeans);
        rvContent.setAdapter(personalCenterAdapter);
        showLoadingDialog();
        presenter.getHelpCenterData();
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);

    }

    @Override
    public void getHelpCenterData(List<HelpCenterBean> helpCenterBeans) {
        hideLoadingDialog();
        this.helpCenterBeans.clear();
        this.helpCenterBeans.addAll(helpCenterBeans);
        personalCenterAdapter.notifyDataSetChanged();
    }
}
