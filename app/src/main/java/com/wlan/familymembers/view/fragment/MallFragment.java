package com.wlan.familymembers.view.fragment;

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
import com.wlan.familymembers.adapter.MallGridAdapter;
import com.wlan.familymembers.bean.LoopBean;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.MallHomepageBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.mall.MallModel;
import com.wlan.familymembers.presenter.mall.MallPresenter;
import com.wlan.familymembers.utils.RecyclerViewUtil;
import com.wlan.familymembers.view.activity.home.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class MallFragment extends BaseFragment<MallModel, MallPresenter> implements Contract.MallContract.View {


    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SwipeRefreshLayout srlContent;
    Unbinder unbinder;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BannersAdapter bannersAdapter;
    private MallGridAdapter goodGoodsAdapter;
    private HeadAdapter headAdapter;
    private int page = 1;
    private int pageSize = 10;
    private boolean isLodingMore = true;
    List<MallHomepageBean> goodGoods;
    List<MallHomepageBean> hotGoodsList;
    List<LoopBean> loopBeans;
    private HeadAdapter head2Adapter;
    private MallGridAdapter hotGoodsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initView(Bundle bundle) {
        loopBeans = new ArrayList<>();
        goodGoods = new ArrayList<>();
        hotGoodsList = new ArrayList<>();
        virtualLayoutManager = new VirtualLayoutManager(getContext());
        rvContent.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //轮播图适配器
        bannersAdapter = new BannersAdapter(getContext(), loopBeans);
        delegateAdapter.addAdapter(bannersAdapter);
        //头适配器
        headAdapter = new HeadAdapter(getContext(), 1, "甄选好物");
        delegateAdapter.addAdapter(headAdapter);
        //网格布局适配器 甄选好物数据
        goodGoodsAdapter = new MallGridAdapter(getContext(), goodGoods);
        delegateAdapter.addAdapter(goodGoodsAdapter);
        //头2适配器
        head2Adapter = new HeadAdapter(getContext(), 2, "爆款推荐");
        delegateAdapter.addAdapter(head2Adapter);

        //网格布局适配器 爆款推荐数据
        hotGoodsAdapter = new MallGridAdapter(getContext(), hotGoodsList);
        delegateAdapter.addAdapter(hotGoodsAdapter);


        rvContent.setAdapter(delegateAdapter);
    }

    @Override
    protected void onEvent() {
        //为RecyclerView注册滑动监听
        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    //当滚动状态闲置时
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //当滚滚动到倒数第二个项目并且滚动停止时
                        if (RecyclerViewUtil.isVisBottom(rvContent)) {
                            //服务器返回不是最后一页时继续加载
                            if (isLodingMore) {
                                //显示加载对话框
                                showLoadingDialog();
                                //页码+1
                                page++;
                                showLoadingDialog();
                                //发送页码 请求对应页码数据商城数据
                                presenter.getMallHomePage(page, pageSize);
                            }
                        }
                }
            }
        });
        llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        srlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                presenter.getMallHomePage(page, pageSize);
            }
        });


    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {
        showLoadingDialog();
        page = 1;
        presenter.getMallHomePage(page, pageSize);

    }

    //显示错误状态
    @Override
    public void showErrorWithStatus(String msg) {
        //关闭动画
        hideLoadingDialog();
        showToast(msg);
    }

    @Override
    public void getMallHomePageSuccess(int page, List<MallHomepageBean> mallBottomBeans1, List<MallHomepageBean> mallBottomBeans2, List<LoopBean> loopBeans, boolean isLastPage) {
        //请求数据成功后 隐藏加载动画
        hideLoadingDialog();
        //关闭刷新动画
        srlContent.setRefreshing(false);
        //当isLastPage = true时   isLodingMore = false; 禁止上滑加载
        if (isLastPage) {
            isLodingMore = false;
        } else {
            isLodingMore = true;
        }
        //清除loopBeans实体类数据  设置新数据
        this.loopBeans.clear();
        this.loopBeans.addAll(loopBeans);
        //当请求回来的页码是1时
        //if (page == 1) {
        //清空fragment的实体类
        goodGoods.clear();
        //当传过来的集合长度=0时  隐藏标题
        if (mallBottomBeans1.size() == 0) {
            headAdapter.isDiaplay(false);
        } else {
            //显示标题 设置数据
            headAdapter.isDiaplay(true);
            goodGoods.addAll(mallBottomBeans1);
        }
        //清理热门商品实体类
        hotGoodsList.clear();
        if (mallBottomBeans2.size() == 0) {
            head2Adapter.isDiaplay(false);
        } else {
            //显示标题 设置数据
            headAdapter.isDiaplay(true);
            hotGoodsList.addAll(mallBottomBeans2);
        }
        //}
        //else{
        //页码不是1时 直接设置热门商品数据
        //hotGoodsList.addAll(mallBottomBeans2);
        //}
        //通知数据变化
        headAdapter.notifyDataSetChanged();
        head2Adapter.notifyDataSetChanged();
        goodGoodsAdapter.notifyDataSetChanged();
        hotGoodsAdapter.notifyDataSetChanged();
    }
}
