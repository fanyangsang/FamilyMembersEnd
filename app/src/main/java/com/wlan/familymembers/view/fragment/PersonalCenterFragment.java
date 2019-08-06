package com.wlan.familymembers.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.app.FamilyMembersApp;
import com.wlan.familymembers.bean.BaseInformationBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.dingyiview.CircleImageView;
import com.wlan.familymembers.dingyiview.LevelView;
import com.wlan.familymembers.model.personal.PersonalCenterModel;
import com.wlan.familymembers.presenter.personal.PersonalCenterPresenter;
import com.wlan.familymembers.view.activity.entrance.LoginActivity;
import com.wlan.familymembers.view.activity.personal.AboutUsActivity;
import com.wlan.familymembers.view.activity.personal.BaseInformationActivity;
import com.wlan.familymembers.view.activity.personal.ChangePasswordActivity;
import com.wlan.familymembers.view.activity.personal.ContactCustomerActivity;
import com.wlan.familymembers.view.activity.personal.HelpCenterActivity;
import com.wlan.familymembers.view.activity.personal.MemberCenterActivity;
import com.wlan.familymembers.view.activity.personal.MyAddressActivity;
import com.wlan.familymembers.view.activity.personal.MyCouponActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class PersonalCenterFragment extends BaseFragment<PersonalCenterModel, PersonalCenterPresenter> implements Contract.PersonalCenterContract.View {


    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.civ_img)
    CircleImageView civImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.lv_grade)
    LevelView lvGrade;
    @BindView(R.id.tv_noLogin)
    TextView tvNoLogin;
    @BindView(R.id.iv_key)
    ImageView ivKey;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_my)
    LinearLayout llMy;
    @BindView(R.id.ll_my_address)
    LinearLayout llMyAddress;
    @BindView(R.id.ll_basis_information)
    LinearLayout llBasisInformation;
    @BindView(R.id.ll_change_password)
    LinearLayout llChangePassword;
    @BindView(R.id.ll_customer)
    LinearLayout llCustomer;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.ll_help_center)
    LinearLayout llHelpCenter;
    @BindView(R.id.btn_quit)
    Button btnQuit;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.srl_content)
    SwipeRefreshLayout srlContent;
    int a;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(Bundle bundle) {
    }

    @Override
    protected void onEvent() {
        llMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FamilyMembersApp.isLogin) {
                    Intent intent = new Intent(getContext(), MyAddressActivity.class);
                    startActivity(intent);
                } else {
                    showToast("请登录");
                }

            }
        });
        llBasisInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FamilyMembersApp.isLogin) {
                    Intent intent = new Intent(getContext(), BaseInformationActivity.class);
                    startActivity(intent);
                } else {
                    showToast("请登录");
                }

            }
        });
        llChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FamilyMembersApp.isLogin) {
                    Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                    startActivity(intent);
                } else {
                    showToast("请登录");
                }

            }
        });
        llCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FamilyMembersApp.isLogin) {
                    Intent intent = new Intent(getContext(), ContactCustomerActivity.class);
                    startActivity(intent);
                } else {
                    showToast("请登录");
                }

            }
        });
        llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        llHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpCenterActivity.class);
                startActivity(intent);
            }
        });
        llCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyCouponActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FLAG_ACTIVITY_CLEAR_TASK将清空所有activity
                //                    Intent intent = new Intent(getActivity(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        srlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getBaseInformation(SPUtils.getInstance().getString("userId"));
            }
        });

    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    /**
     * 进行懒加载
     */
    @Override
    protected void lazyFetchData() {
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString("userId"))) {
            showLoadingDialog();
            presenter.getBaseInformation(SPUtils.getInstance().getString("userId"));
        }
    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);
        srlContent.setRefreshing(false);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString("userId"))) {
            //已登录
            btnLogin.setVisibility(View.GONE);
            btnQuit.setVisibility(View.VISIBLE);
        } else {
            //未登录
            btnLogin.setVisibility(View.VISIBLE);
            btnQuit.setVisibility(View.GONE);
        }
    }

    //请求基本信息成功后调用
    @Override
    public void BaseInformation(BaseInformationBean baseInformationBean) {
        hideLoadingDialog();
        srlContent.setRefreshing(false);
        tvName.setText(baseInformationBean.getNickName());
        //这边应该是加载用户的头像
        if (baseInformationBean.getPic() != null && !baseInformationBean.getPic().isEmpty()) {
            if (baseInformationBean.getPic().contains("http")) {
                Glide.with(this).load(baseInformationBean.getPic()).into(civImg);
            } else {
                Glide.with(this).load(HttpUtils.BASE_URL + baseInformationBean.getPic()).into(civImg);
            }
        }
    }

    @Override
    public void setLevel(int a) {
        this.a = a;
        rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MemberCenterActivity.class);
                //会员中心在跳转的时候应该把a的值传过去
                startActivity(intent);
            }
        });
    }
}
