package com.wlan.familymembers.view.activity.personal;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.AboutUsBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.personal.AboutUsModel;
import com.wlan.familymembers.presenter.personal.AboutUsPresenter;
import com.wlan.familymembers.utils.ClearCacheUtil;
import com.wlan.familymembers.utils.HtmlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class AboutUsActivity extends BaseActivity<AboutUsModel, AboutUsPresenter> implements Contract.AboutUsContract.View {
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_new_version)

    TextView tvNewVersion;
    @BindView(R.id.tv_Clean)
    TextView tvclean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        tvContent.setText("关于我们");
        //        tvCache.setText("已缓存" + CacheUtils.getInstance().getCacheSize());
        tvNewVersion.setText("最新版本：" + AppUtils.getAppVersionName());
        showLoadingDialog();
        presenter.getAboutUsInformation();
        try {
            tvCache.setText("已缓存" + ClearCacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(tvCache, "清除缓存？", Snackbar.LENGTH_SHORT).setAction("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CleanUtils.cleanExternalCache();
                        CleanUtils.cleanInternalCache();
                        CleanUtils.cleanInternalSP();
                        Snackbar.make(tvclean, "已清理", Snackbar.LENGTH_SHORT).show();
                        tvCache.setText("已缓存 0.0M");
                    }
                }).show();
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
    public void getAboutUsInformationSuccess(AboutUsBean aboutUsBean) {
        hideLoadingDialog();
//        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
//        String html = HtmlUtil.fixEditorHtml(content);
//        wvContent.loadData(html, "text/html;charset=UTF-8", null);

        //获取html数据
        String htmlInfo = aboutUsBean.getIntroduce();
        String data = Html.fromHtml(htmlInfo).toString();
        String replace_data = data.replace("src=\"", "style=\"width:100%\" src=\"" + HttpUtils.BASE_URL);
        String html = "<html><head><meta charset=\"utf-8\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/><style>p,div,table,span,img{width:100%;}</style></head><body>" + replace_data + "</body>" +
                "</html>";
        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        wvContent.loadData(html, "text/html;charset=UTF-8", null);
    }
}
