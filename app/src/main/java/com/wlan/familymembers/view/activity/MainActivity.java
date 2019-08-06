package com.wlan.familymembers.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.app.FamilyMembersApp;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.MainModel;
import com.wlan.familymembers.presenter.MainPresenter;
import com.wlan.familymembers.view.fragment.GoodsOrderFragment;
import com.wlan.familymembers.view.fragment.HomePageFragment;
import com.wlan.familymembers.view.fragment.MallFragment;
import com.wlan.familymembers.view.fragment.PersonalCenterFragment;
import com.wlan.familymembers.view.fragment.ServiceOrderFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainModel, MainPresenter> implements Contract.MainContract.View,HomePageFragment.switchPageListener {

    FragmentManager fragmentManager;
    HomePageFragment homePageFragment;
    MallFragment mallFragment;
    ServiceOrderFragment serviceOrderFragment;
    GoodsOrderFragment goodsOrderFragment;
    PersonalCenterFragment personalCenterFragment;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    @BindView(R.id.rb_home_page)
    RadioButton rbHomePage;
    @BindView(R.id.rb_mall)
    RadioButton rbMall;
    @BindView(R.id.rb_service_order)
    RadioButton rbServiceOrder;
    @BindView(R.id.rb_goods_order)
    RadioButton rbGoodsOrder;
    @BindView(R.id.rb_personal_center)
    RadioButton rbPersonalCenter;
    @BindView(R.id.rg_rg)
    RadioGroup rgRg;
    String phone;
    String password;

    @Override
    public int getLayoutId() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT); //也可以设置成灰色透明的，比较符合Material Design的风格
        }
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        rgRg.check(R.id.rb_home_page);
        fragmentManager = getSupportFragmentManager();
        if (homePageFragment == null) {
            homePageFragment = new HomePageFragment();
            homePageFragment.setListener(this);
        }

        if (mallFragment == null) {
            mallFragment = new MallFragment();

        }
        if (serviceOrderFragment == null) {
            serviceOrderFragment = new ServiceOrderFragment();
        }
        if (goodsOrderFragment == null) {
            goodsOrderFragment = new GoodsOrderFragment();
        }
        if (personalCenterFragment == null) {
            personalCenterFragment = new PersonalCenterFragment();
        }
        //将fragment文件放入到FrameLayout布局文件中
        fragmentManager.beginTransaction()
                .add(R.id.fl_fragment, homePageFragment)
                .add(R.id.fl_fragment, mallFragment)
                .add(R.id.fl_fragment, serviceOrderFragment)
                .add(R.id.fl_fragment, goodsOrderFragment)
                .add(R.id.fl_fragment, personalCenterFragment)
                .hide(mallFragment)
                .hide(serviceOrderFragment)
                .hide(goodsOrderFragment)
                .hide(personalCenterFragment)
                .commit();
        if (FamilyMembersApp.isLogin){
        }else {
            //进入首页之后，从缓存中获取用户名和密码,并进行一次网络请求，进入登录状态
            phone= SPUtils.getInstance().getString("phone");
            password=SPUtils.getInstance().getString("password");
            if (!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(password)){
                showLoadingDialog();
                presenter.login(phone,password);
            }
        }

    }

    @Override
    public void onEvent() {
        rgRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_page:
                        fragmentManager.beginTransaction()
                                .show(homePageFragment)
                                .hide(mallFragment)
                                .hide(serviceOrderFragment)
                                .hide(goodsOrderFragment)
                                .hide(personalCenterFragment)
                                .commit();
                        break;
                    case R.id.rb_mall:
                        fragmentManager.beginTransaction()
                                .show(mallFragment)
                                .hide(homePageFragment)
                                .hide(serviceOrderFragment)
                                .hide(goodsOrderFragment)
                                .hide(personalCenterFragment)
                                .commit();
                        break;
                    case R.id.rb_service_order:
                        fragmentManager.beginTransaction()
                                .show(serviceOrderFragment)
                                .hide(homePageFragment)
                                .hide(mallFragment)
                                .hide(goodsOrderFragment)
                                .hide(personalCenterFragment)
                                .commit();
                        break;
                    case R.id.rb_goods_order:
                        fragmentManager.beginTransaction()
                                .show(goodsOrderFragment)
                                .hide(homePageFragment)
                                .hide(mallFragment)
                                .hide(serviceOrderFragment)
                                .hide(personalCenterFragment)
                                .commit();
                        break;
                    case R.id.rb_personal_center:
                        fragmentManager.beginTransaction()
                                .show(personalCenterFragment)
                                .hide(homePageFragment)
                                .hide(mallFragment)
                                .hide(serviceOrderFragment)
                                .hide(goodsOrderFragment)
                                .commit();
                        break;

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
    public void loginSuccess() {
        hideLoadingDialog();
        FamilyMembersApp.isLogin=true;

    }

    @Override
    public void loginFaile() {
        hideLoadingDialog();
        FamilyMembersApp.isLogin=false;
    }

    @Override
    public void switchPage() {
        fragmentManager.beginTransaction()
                .show(mallFragment)
                .hide(homePageFragment)
                .hide(serviceOrderFragment)
                .hide(goodsOrderFragment)
                .hide(personalCenterFragment)
                .commit();
    }
}
