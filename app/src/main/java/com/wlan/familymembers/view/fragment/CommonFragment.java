package com.wlan.familymembers.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.HomeClssifyAdapter;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.CommonModel;
import com.wlan.familymembers.presenter.home.CommonPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class CommonFragment extends BaseFragment<CommonModel, CommonPresenter> implements Contract.CommonContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SwipeRefreshLayout srlContent;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private HomeClssifyAdapter homeClssifyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_content;
    }

    @Override
    protected void initView(Bundle bundle) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(linearLayoutManager);
        homeClssifyAdapter = new HomeClssifyAdapter(getContext());
        rvContent.setAdapter(homeClssifyAdapter);


    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void showErrorWithStatus(String msg) {

    }
}
