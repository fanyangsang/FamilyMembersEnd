package com.wlan.familymembers.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.ServiceListAdapter;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.ServiceListContentModel;
import com.wlan.familymembers.presenter.home.ServiceListContentPresenter;
import com.wlan.familymembers.utils.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class ServiceListContentFragment extends BaseFragment<ServiceListContentModel, ServiceListContentPresenter> implements Contract.ServiceListContentContract.View {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    //    @BindView(R.id.srl_content)  //这是坑啊！！！！！！！！！！！！！！！！
    //    SwipeRefreshLayout srlContent;
    //    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private ServiceListAdapter serviceListAdapter;
    private boolean isLodingMore = false;
    private int currentPage = 1;
    private int size = 10;
    private String catId;
    private List<ServiceListBean> serviceLists;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_content;
    }

    public static ServiceListContentFragment newInstance(int page) {
        Bundle args = new Bundle();
        //接收
        args.putInt("", page);
        ServiceListContentFragment fragment = new ServiceListContentFragment();
        //设置参数
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(Bundle bundle) {
        serviceLists = new ArrayList<>();
        catId = getArguments().getString("catId");
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(linearLayoutManager);
        serviceListAdapter = new ServiceListAdapter(getContext(), serviceLists);
        rvContent.setAdapter(serviceListAdapter);
        showLoadingDialog();
        presenter.getHostPageInfo(catId, currentPage, size);
    }

    @Override
    protected void onEvent() {
        //RecyclerView向上滑动监听
        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (RecyclerViewUtil.isVisBottom(rvContent)) {
                            if (isLodingMore) {
                                currentPage++;
                                showLoadingDialog();
                                presenter.getHostPageInfo(catId, currentPage, size);
                            }
                        }
                }
            }
        });

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    //懒加载
    @Override
    protected void lazyFetchData() {
        //        showLoadingDialog();
    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);

    }

    @Override
    public void getHostPageInfoSuccess(List<ServiceListBean> serviceListBeans, boolean isLastPage) {
        hideLoadingDialog();
        this.serviceLists.addAll(serviceListBeans);
        serviceListAdapter.notifyDataSetChanged();
        if (isLastPage) {
            isLodingMore = false;
        } else {
            isLodingMore = true;
        }


    }

}
