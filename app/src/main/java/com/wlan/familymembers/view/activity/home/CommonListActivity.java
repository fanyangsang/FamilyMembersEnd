package com.wlan.familymembers.view.activity.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.ServiceClassificationBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.CommonListModel;
import com.wlan.familymembers.presenter.home.CommonListPresenter;
import com.wlan.familymembers.view.fragment.ServiceListContentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class CommonListActivity extends BaseActivity<CommonListModel, CommonListPresenter> implements Contract.CommonListContract.View {

    List<ServiceListContentFragment> serviceListContentFragments;
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tl_menu)
    TabLayout tlMenu;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_list;
    }

    @Override
    public void initView() {
        String id = getIntent().getStringExtra("id");
        String page = String.valueOf(1);
        String name = getIntent().getStringExtra("name");
        tvContent.setText(name);
        String size = String.valueOf(200);
        presenter.getserviceClassificationTwo(id);
        serviceListContentFragments = new ArrayList<>();
        Bundle bundle = new Bundle();


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

    }

    @Override
    public void getserviceClassificationTwoSuccess(List<ServiceClassificationBean> serviceClassificationBeans) {
        tlMenu.removeAllTabs();
        for (int i=0;i<serviceClassificationBeans.size();i++){
            tlMenu.addTab(tlMenu.newTab().setText(serviceClassificationBeans.get(i).getName()));
        }
        tlMenu.setupWithViewPager(vpContent);
    }
}
