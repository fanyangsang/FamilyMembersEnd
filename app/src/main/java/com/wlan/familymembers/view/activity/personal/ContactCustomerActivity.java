package com.wlan.familymembers.view.activity.personal;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.ContactCustomerModel;
import com.wlan.familymembers.presenter.personal.ContactCustomerPresenter;
import com.wlan.familymembers.utils.HtmlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class ContactCustomerActivity extends BaseActivity<ContactCustomerModel, ContactCustomerPresenter> implements Contract.ContactCustomerContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.wv_content)
    WebView wvContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_customer;
    }

    @Override
    public void initView() {
        showLoadingDialog();
        String userId= SPUtils.getInstance().getString("userId");
        presenter.ContactCustomer(userId);
        tvContent.setText("联系客服");

    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        public void ContactCustomerSuccess(String content) {
        hideLoadingDialog();
//        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
//        wvContent.loadData(content,"text/html;charset=UTF-8",null);
        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        wvContent.loadData(HtmlUtil.fixEditorHtml(content),"text/html;charset=UTF-8",null);

    }
}
