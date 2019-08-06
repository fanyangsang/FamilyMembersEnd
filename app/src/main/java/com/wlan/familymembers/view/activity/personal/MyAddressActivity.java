package com.wlan.familymembers.view.activity.personal;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.MyAddressAdapter;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.MyAddressModel;
import com.wlan.familymembers.presenter.personal.MyAddressPresenter;
import com.wlan.familymembers.utils.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

public class MyAddressActivity extends BaseActivity<MyAddressModel, MyAddressPresenter> implements Contract.MyAddressContract.View, MyAddressAdapter.MyAddressListener {


    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_my_address)
    TextView tvMyAddress;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.tv_new_address)
    TextView tvNewAddress;
    @BindView(R.id.ll_new_address)
    LinearLayout llNewAddress;
    private MyAddressAdapter myAddressAdapter;
    int currentPage = 1;
    boolean isloadingMore = true;
    String userId;
    List<AddressBean> addressBeans;
    public static MyAddressActivity instance;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_my_address;
    }

    @Override
    public void initView() {
        instance = this;
        //加下划线
        tvNewAddress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(linearLayoutManager);
        addressBeans = new ArrayList<>();
        myAddressAdapter = new MyAddressAdapter(this, addressBeans);
        myAddressAdapter.setListener(this);
        rvContent.setAdapter(myAddressAdapter);
        userId = SPUtils.getInstance().getString("userId");
        //uesrid  当前页 分页10
        presenter.getAdrressPageInfo(userId, currentPage, 10);

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
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (RecyclerViewUtil.isVisBottom(rvContent)) {
                            if (isloadingMore) {
                                currentPage++;
                                showLoadingDialog();
                                presenter.getAdrressPageInfo(userId, currentPage, 10);
                            }
                        }
                        break;
                }
            }
        });
        llNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressActivity.this, EditAddressActivity.class);
                //0代表新增地址，1代表编辑地址
                intent.putExtra("key", 0);
                startActivity(intent);
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
    public void getAdrressPageInfoSuccess(List<AddressBean> addressBeans) {
        hideLoadingDialog();
        this.addressBeans.addAll(addressBeans);
        if (addressBeans.size() < 10) {
            isloadingMore = false;
        }
        myAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteAddressSuccess() {
        showToast("删除成功");
    }

    //默认地址修改成功
    @Override
    public void submitAddressInformation() {
        hideLoadingDialog();
        //uesrid  当前页 分页10
        addressBeans.clear();
        try {
            presenter.getAdrressPageInfo(userId, currentPage, 10);
        }catch (Exception e){
            e.printStackTrace();
        }
        myAddressAdapter.notifyDataSetChanged();
//        showToast("修改成功");
    }


    @Override
    public void deleteAddress(int position, String id) {
        this.addressBeans.remove(position);
        myAddressAdapter.notifyDataSetChanged();
        presenter.deleteAddress(userId, id);
    }

    @Override
    public void checkbox(int position, String id, boolean b) {
        //获取checkbox点击时的状态
        if (!b) {
            String defaultAddress = "1";
            presenter.submitAddressInformation(addressBeans.get(position).getId(),
                    addressBeans.get(position).getUserId(),
                    addressBeans.get(position).getName(),
                    addressBeans.get(position).getPhone(),
                    addressBeans.get(position).getAddress(),
                    addressBeans.get(position).getAddressServer(),
                    addressBeans.get(position).getLon(),
                    addressBeans.get(position).getLat(),
                    defaultAddress);
        } else {
            String defaultAddress = "0";
            presenter.submitAddressInformation(addressBeans.get(position).getId(),
                    addressBeans.get(position).getUserId(),
                    addressBeans.get(position).getName(),
                    addressBeans.get(position).getPhone(),
                    addressBeans.get(position).getAddress(),
                    addressBeans.get(position).getAddressServer(),
                    addressBeans.get(position).getLon(),
                    addressBeans.get(position).getLat(),
                    defaultAddress);
        }
    }
}
