package com.wlan.familymembers.view.activity.service;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.SearchVpAdapter;
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

public class ServiceListActivity extends BaseActivity<CommonListModel, CommonListPresenter> implements Contract.CommonListContract.View {

    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tl_menu)
    TabLayout tlMenu;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private SearchVpAdapter searchVpAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_list;
    }

    @Override
    public void initView() {
        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        tvContent.setText(name);
        showLoadingDialog();
        presenter.getserviceClassificationTwo(id);

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
    public void getserviceClassificationTwoSuccess(List<ServiceClassificationBean> serviceClassificationBeans) {
        fragments = new ArrayList<>();
        //循环添加fragment
        for (int i = 0; i < serviceClassificationBeans.size(); i++) {
            //new fragment
            ServiceListContentFragment serfragment = new ServiceListContentFragment();
            fragments.add(serfragment);
            Bundle bundle = new Bundle();
            //往fragment传catid
            bundle.putString("catId", serviceClassificationBeans.get(i).getId());
            serfragment.setArguments(bundle);
        }
        searchVpAdapter = new SearchVpAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(searchVpAdapter);
        //设置ViewPager
        tlMenu.setupWithViewPager(vpContent);

        //刪除所有标签
        tlMenu.removeAllTabs();
        //循环添加标签
        for (int i = 0; i < serviceClassificationBeans.size(); i++) {
            //新建标签并添加标签
            tlMenu.addTab(tlMenu.newTab().setText(serviceClassificationBeans.get(i).getName()));
        }
        //设置缓存
        vpContent.setOffscreenPageLimit(serviceClassificationBeans.size() + 1);
        hideLoadingDialog();
    }
}
