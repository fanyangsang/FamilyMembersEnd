package com.wlan.familymembers.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.BannersAdapter;
import com.wlan.familymembers.adapter.HeadAdapter;
import com.wlan.familymembers.adapter.HomeBottomAdapter;
import com.wlan.familymembers.adapter.HomeGridViewAdapter;
import com.wlan.familymembers.adapter.OneTrailerNAdapter;
import com.wlan.familymembers.bean.LoopBean;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.OneNBean;
import com.wlan.familymembers.bean.ServiceClassificationBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.HomePageModel;
import com.wlan.familymembers.presenter.home.HomePagePresenter;
import com.wlan.familymembers.utils.RecyclerViewUtil;
import com.wlan.familymembers.utils.RequestCodeInfo;
import com.wlan.familymembers.view.activity.home.CityPickerActivity;
import com.wlan.familymembers.view.activity.home.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class HomePageFragment extends BaseFragment<HomePageModel, HomePagePresenter> implements Contract.HomePageContract.View, HomeGridViewAdapter.switchPageListener {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SwipeRefreshLayout srlContent;
    Unbinder unbinder;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BannersAdapter bannersAdapter;
    private HomeGridViewAdapter homeGridViewAdapter;
    private OneTrailerNAdapter oneTrailerNAdapter;
    private HomeBottomAdapter homeBottomAdapter;
    private HeadAdapter headAdapter;
    private switchPageListener switchPageListener;
    List<LoopBean> loopBeans;
    List<ServiceClassificationBean> serviceClassificationBeans;
    List<OneNBean> oneNBeans;
    List<MallBottomBean> mallBottomBeans;
    //新品状态
    private int goodsState = 1;
    int pageSize = 10;
    int page = 1;
    boolean isloadingMore = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(Bundle bundle) {
        serviceClassificationBeans = new ArrayList<>();
        loopBeans = new ArrayList<>();
        oneNBeans = new ArrayList<>();
        mallBottomBeans = new ArrayList<>();
        virtualLayoutManager = new VirtualLayoutManager(getContext());
        rvContent.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //轮播图适配器
        bannersAdapter = new BannersAdapter(getContext(), loopBeans);
        delegateAdapter.addAdapter(bannersAdapter);
        //菜单适配器
        homeGridViewAdapter = new HomeGridViewAdapter(getContext(), serviceClassificationBeans);
        homeGridViewAdapter.setListener(this);
        delegateAdapter.addAdapter(homeGridViewAdapter);


        //一拖n适配器
        oneTrailerNAdapter = new OneTrailerNAdapter(getContext(), oneNBeans);
        delegateAdapter.addAdapter(oneTrailerNAdapter);
        //底部适配器
        homeBottomAdapter = new HomeBottomAdapter(getContext(), mallBottomBeans);
        headAdapter = new HeadAdapter(getContext(), 1, "甄选好物");
        delegateAdapter.addAdapter(headAdapter);
        delegateAdapter.addAdapter(homeBottomAdapter);
        rvContent.setAdapter(delegateAdapter);

    }

    @Override
    protected void onEvent() {
        llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), RequestCodeInfo.GETCITY);
            }
        });
        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (RecyclerViewUtil.isVisBottom(rvContent)) {
                            if (isloadingMore) {
                                page++;
                                presenter.getHostPageInfo(page, pageSize);
                            }
                        }
                }
            }
        });
        srlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getData(pageSize);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeInfo.GETCITY:
                    String city = data.getExtras().getString("city");
                    if (city != null) {
                        tvCity.setText(city);
                    }
                    break;
            }

        }
    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {
        showLoadingDialog();
        page = 1;
        presenter.getData(pageSize);

    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);

    }

    @Override
    public void switchPage() {
        switchPageListener.switchPage();
    }

    @Override
    public void getDataSuccess(List<LoopBean> loopBeans, List<ServiceClassificationBean> serviceClassificationBeans, List<OneNBean> oneNBeans, List<MallBottomBean> mallBottomBeans, boolean isLastPage) {
        hideLoadingDialog();
        srlContent.setRefreshing(false);
        //轮播
        this.loopBeans.clear();
        this.loopBeans.addAll(loopBeans);
        bannersAdapter.notifyDataSetChanged();
        //菜单
        this.serviceClassificationBeans.clear();
        this.serviceClassificationBeans.addAll(serviceClassificationBeans);
        homeGridViewAdapter.notifyDataSetChanged();
        //板块
        this.oneNBeans.clear();
        this.oneNBeans.addAll(oneNBeans);
        oneTrailerNAdapter.notifyDataSetChanged();
        //好物品
        if (mallBottomBeans != null) {
            headAdapter.notifyDataSetChanged();
            this.mallBottomBeans.clear();
            this.mallBottomBeans.addAll(mallBottomBeans);
            homeBottomAdapter.notifyDataSetChanged();
        }

        if (isLastPage) {
            isloadingMore = false;
        } else {
            isloadingMore = true;
        }
    }

    @Override
    public void getHostPageInfoSuccess(List<MallBottomBean> mallBottomBeans) {
        this.mallBottomBeans.addAll(mallBottomBeans);
        homeBottomAdapter.notifyDataSetChanged();
    }


    public interface switchPageListener {
        void switchPage();
    }

    public void setListener(switchPageListener switchPageListener) {
        this.switchPageListener = switchPageListener;
    }


}
