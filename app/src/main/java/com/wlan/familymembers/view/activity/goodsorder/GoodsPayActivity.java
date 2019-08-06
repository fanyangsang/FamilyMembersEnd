package com.wlan.familymembers.view.activity.goodsorder;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.R;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.goodsorder.GoodsPayModel;
import com.wlan.familymembers.presenter.goodsorder.GoodsPayPresenter;
import com.wlan.familymembers.utils.WeakHandler;
import com.wlan.familymembers.view.fragment.GoodsOrderFragment;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2019/3/29.
 */

public class GoodsPayActivity extends BaseActivity<GoodsPayModel, GoodsPayPresenter> implements Contract.GoodsPayContract.View {

    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvcontent;
    @BindView(R.id.alipay_button)
    TextView alipayButton;
    private static final int SDK_PAY_FLAG = 1;
    private String userid;
    private String orderid;
    private String couponid;
    private int payWay;
    private int payType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goodspay;
    }

    @Override
    public void initView() {
        tvcontent.setText("支付订单");
        //这里还需把多少钱传过来
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //这里进行支付事件
        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payWay = 1;
                payType = 1;
                userid = getIntent().getStringExtra("userId");
                orderid = getIntent().getStringExtra("orderid");
                presenter.alipay(userid, orderid, payWay, payType);
            }
        });

    }

    @Override
    public BaseView getBaseView() {
        return null;
    }


    @Override
    public void showErrorWithStatus(String msg) {

    }

    //请求接口成功，开始支付
    @Override
    public void alipay(final String orderid) {
        hideLoadingDialog();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(GoodsPayActivity.this);
                Map<String, String> result = alipay.payV2(orderid, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mWeakHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private WeakHandler mWeakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        /*支付成功*/
                        showToast("支付成功");
                        //settext();
                    } else {/*支付失败*/
                        showToast("支付失败");
                    }
                    break;
            }
            return false;
        }
    });

    public class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(Map<String, String> rawResult) {
            if (rawResult == null) {
                return;
            }

            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult.get(key);
                }
            }
        }

        public String getResultStatus() {
            return resultStatus;
        }

    }

    @Override
    public void paySuccess() {

    }

}
