package com.wlan.familymembers.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.HomeBottomAdapter;
import com.wlan.familymembers.adapter.SearchAdapter;
import com.wlan.familymembers.bean.GoodsBean;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.SearchServiceModel;
import com.wlan.familymembers.presenter.home.SearchServicePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

import static com.wlan.familymembers.view.activity.home.SearchActivity.TYPE_GOODS;

/**
 * 类作用：
 * Created by Administrator on 2018/11/5.
 */

public class SearchFragment extends BaseFragment<SearchServiceModel, SearchServicePresenter> implements Contract.SearchServiceContract.View {
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private List<GoodsBean> goodsBeans;
    private List<ServiceListBean> serviceListBeans;
    private int type = TYPE_GOODS;
    private SearchAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common_content;
    }

    @Override
    protected void initView(Bundle bundle) {
        Bundle b = getArguments();
        if (null != b) {
            type = b.getInt("type");
        }
        goodsBeans = new ArrayList<>();
        serviceListBeans = new ArrayList<>();
        adapter = new SearchAdapter(getContext(), goodsBeans, serviceListBeans, type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setAdapter(adapter);
    }

    @Override
    protected void onEvent() {
        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

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

    public void addData(boolean isLastPage, Object list, int style) {
        if (style == TYPE_GOODS) {
            this.goodsBeans.clear();
            this.goodsBeans.addAll((List<GoodsBean>) list);

        } else {
            this.serviceListBeans.clear();
            this.serviceListBeans.addAll((List<ServiceListBean>) list);
        }
        adapter.notifyDataSetChanged();
    }

}
