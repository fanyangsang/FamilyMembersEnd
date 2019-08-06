package com.wlan.familymembers.view.fragment;

import android.os.Bundle;

import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;

/**
 * 类作用：
 * Created by Administrator on 2018/12/8.
 */

public class TestFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(Bundle bundle) {

    }

    @Override
    protected void onEvent() {

    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {

    }
}
