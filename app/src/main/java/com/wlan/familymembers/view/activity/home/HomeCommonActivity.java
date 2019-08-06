package com.wlan.familymembers.view.activity.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.HomeCommonAdapter;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.TopBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.HomeCommonModel;
import com.wlan.familymembers.presenter.home.HomeComonPresenter;
import com.wlan.familymembers.utils.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/17.
 */

public class HomeCommonActivity extends BaseActivity<HomeCommonModel, HomeComonPresenter> implements Contract.HomeCommonContract.View {


    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private HomeCommonAdapter homeCommonAdapter;
    String type;
    GridLayoutManager gridLayoutManager;
    int page = 1;
    int pageSize = 10;
    TopBean topBean;
    List<MallBottomBean> mallBottomBeans;
    boolean isLodingMore=true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_common;
    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
        mallBottomBeans = new ArrayList<>();
        switch (type) {
            case 1 + "":
                gridLayoutManager = new GridLayoutManager(this, 1);
                break;
            default:
                gridLayoutManager = new GridLayoutManager(this, 2);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                    @Override
                    public int getSpanSize(int position) {
                        if (position == 0) {
                            return 2;
                        } else {
                            return 1;
                        }
                    }
                });
                break;
        }
        rvContent.setLayoutManager(gridLayoutManager);
        homeCommonAdapter = new HomeCommonAdapter(this, topBean, mallBottomBeans,type);
        rvContent.setAdapter(homeCommonAdapter);
        showLoadingDialog();
        presenter.getSpecialPageInfo(type,page,pageSize);
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (RecyclerViewUtil.isVisBottom(rvContent)){
                            if (isLodingMore){
                                presenter.getSpecialPageInfo(type,page,pageSize);
                            }
                        }
                }
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
    public void getSpecialPageInfoSuccess(TopBean topBean, List<MallBottomBean> mallBottomBeans,boolean isLastPage) {
        hideLoadingDialog();
        if (isLastPage){
            isLodingMore=true;
        }else {
            isLodingMore=false;
        }
        homeCommonAdapter.updataBean(topBean);
        this.topBean=topBean;
        this.mallBottomBeans.clear();
        this.mallBottomBeans.addAll(mallBottomBeans);
        homeCommonAdapter.notifyDataSetChanged();
        tvContent.setText(topBean.getTitle());
    }
}
