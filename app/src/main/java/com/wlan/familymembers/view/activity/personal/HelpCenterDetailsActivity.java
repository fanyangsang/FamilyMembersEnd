package com.wlan.familymembers.view.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.HelpCenterDetailsBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.HelpCenterDetailsModel;
import com.wlan.familymembers.presenter.personal.HelpCenterDetailsPresenter;
import com.wlan.familymembers.utils.HtmlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/29.
 */

public class HelpCenterDetailsActivity extends BaseActivity<HelpCenterDetailsModel, HelpCenterDetailsPresenter> implements Contract.HelpCenterDetailsContract.View {


    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @Override
    public int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initView() {
        tvContent.setText("帮助中心");
        String question = getIntent().getStringExtra("question");
        String id=getIntent().getStringExtra("id");
        tvQuestion.setText(question);
        showLoadingDialog();
        presenter.getHelpCenterDetails(id);
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
    public void getHelpCenterDetailsSuccess(HelpCenterDetailsBean helpCenterDetailsBean) {
        hideLoadingDialog();
        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        String html = HtmlUtil.fixEditorHtml(helpCenterDetailsBean.getContent());
        wvContent.loadData(html,"text/html;charset=UTF-8",null);
    }
}
