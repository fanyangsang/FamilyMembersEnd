package com.wlan.familymembers.view.activity.service;

import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.service.ServiceAgreementModel;
import com.wlan.familymembers.presenter.service.ServiceAgreementPresenter;
import com.wlan.familymembers.utils.HtmlUtil;

import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class ServiceAgreementActivity extends BaseActivity<ServiceAgreementModel, ServiceAgreementPresenter> implements Contract.ServiceAgreementContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.wv_content)
    WebView wvContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_agreement;
    }

    @Override
    public void initView() {
        showLoadingDialog();
        presenter.serviceAgreement();

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
    public void getServiceAgreementSuccess(String content) {
        hideLoadingDialog();
        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        wvContent.loadData(HtmlUtil.fixEditorHtml(content), "text/html;charset=UTF-8", null);

    }
}
