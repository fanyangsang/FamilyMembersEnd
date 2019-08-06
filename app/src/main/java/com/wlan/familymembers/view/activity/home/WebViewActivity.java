package com.wlan.familymembers.view.activity.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.home.WebViewModel;
import com.wlan.familymembers.presenter.home.WebViewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/11/2.
 */

public class WebViewActivity extends BaseActivity<WebViewModel, WebViewPresenter> implements Contract.WebViewContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.wv_content)
    WebView wvContent;
    String title;
    String uri;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        title=getIntent().getStringExtra("title");
        uri=getIntent().getStringExtra("uri");
        tvContent.setText(title);
        wvContent.loadUrl(uri);

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

    }
}
