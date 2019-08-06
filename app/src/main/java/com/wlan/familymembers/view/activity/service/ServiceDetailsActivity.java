package com.wlan.familymembers.view.activity.service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.DetailRotationsAdapter;
import com.wlan.familymembers.bean.ServiceDetailsBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.dingyiview.MyWebView;
import com.wlan.familymembers.model.service.ServiceDetailModel;
import com.wlan.familymembers.presenter.service.ServiceDetailPresenter;
import com.wlan.familymembers.view.activity.goodsorder.ServicePaymentDetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/10/18.
 */

public class ServiceDetailsActivity extends BaseActivity<ServiceDetailModel, ServiceDetailPresenter> implements Contract.ServiceDetailContract.View {

    ServiceDetailsBean serviceDetailsBean;
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rpv_content)
    RollPagerView rpvContent;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.mwv_content)
    MyWebView mwvContent;
    @BindView(R.id.tv_advance_payment)
    TextView tvAdvancePayment;
    @BindView(R.id.btn_immediately_order)
    Button btnImmediatelyOrder;
    private ArrayList<String> sowings;
    private MyWebView.ScrollChangedListener scrollChangedListener;
    private String serviceId;
    private DetailRotationsAdapter detailRotationsAdapter;

    @Override
    public int getLayoutId() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        return R.layout.activity_service_classisy_details;
    }

    @Override
    public void initView() {
        tvContent.setText("服务详情");
        serviceDetailsBean = new ServiceDetailsBean();
        serviceId = getIntent().getStringExtra("serviceId");
        sowings = new ArrayList<>();

        //设置轮播图播放时间间隔
        rpvContent.setPlayDelay(3000);
        //设置透明度
        rpvContent.setAnimationDurtion(1500);
        //初始化适配器
        detailRotationsAdapter = new DetailRotationsAdapter(this, sowings);
        rpvContent.setAdapter(detailRotationsAdapter);

        showLoadingDialog();
        presenter.getServiceDetails(serviceId);

    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnImmediatelyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ServiceDetailsActivity.this,ServicePaymentDetailsActivity.class);
               intent.putExtra("serviceId",serviceId);
               startActivity(intent);
            }
        });
        //webview设置触摸监听器  返回true不可触摸
        mwvContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
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
    public void getServiceDetailsSuccess(ServiceDetailsBean serviceDetailsBean) {
        hideLoadingDialog();
        this.serviceDetailsBean = serviceDetailsBean;
        tvName.setText(serviceDetailsBean.getName());
        tvPrice.setText("￥" + serviceDetailsBean.getPrice());
        //        mwvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        //        mwvContent.loadData(HtmlUtil.fixEditorHtml(serviceDetailsBean.getDetail()),"text/html;charset=UTF-8",null);
        //获取html数据
        String htmlInfo = serviceDetailsBean.getDetail();
        //二次解析富文本
        String data = Html.fromHtml(htmlInfo).toString();
        //截取字符串 添加url
        String replace_data = data.replace("src=\"", "style=\"width:100%\" src=\"" + HttpUtils.BASE_URL);
        //设置标签
        String html = "<html><head><meta charset=\"utf-8\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/><style>p,div,table,span,img{width:100%;}</style></head><body>" + replace_data + "</body>" +
                "</html>";
        mwvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        mwvContent.loadData(html, "text/html;charset=UTF-8", null);
        tvAdvancePayment.setText("上门服务费：" + serviceDetailsBean.getPrePay());
        sowings.clear();
        sowings.addAll(serviceDetailsBean.getBanners());
        detailRotationsAdapter.notifyDataSetChanged();
    }
}
