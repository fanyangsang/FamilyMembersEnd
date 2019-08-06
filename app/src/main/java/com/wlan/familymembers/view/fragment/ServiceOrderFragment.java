package com.wlan.familymembers.view.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.base.BaseFragment;
import com.rwq.framworkapp.base.BaseView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.ServiceOrderListAdapter;
import com.wlan.familymembers.bean.ServiceOrderBean;
import com.wlan.familymembers.bean.WXPayBean;
import com.wlan.familymembers.constant.Constant;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.service.ServiceOrderModel;
import com.wlan.familymembers.presenter.service.ServiceOrderPresenter;
import com.wlan.familymembers.utils.WeakHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class ServiceOrderFragment extends BaseFragment<ServiceOrderModel, ServiceOrderPresenter> implements Contract.ServiceOrderContract.View, ServiceOrderListAdapter.ServiceOrderListAdapterListener {


    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.tv_no_payment)
    TextView tvNoPayment;
    @BindView(R.id.v_line_one)
    View vLineOne;
    @BindView(R.id.ll_no_payment)
    LinearLayout llNoPayment;
    @BindView(R.id.tv_no_finish)
    TextView tvNoFinish;
    @BindView(R.id.v_line_two)
    View vLineTwo;
    @BindView(R.id.ll_no_finish)
    LinearLayout llNoFinish;
    @BindView(R.id.tv_waitGoods)
    TextView tvWaitGoods;
    @BindView(R.id.v_line_three)
    View vLineThree;
    @BindView(R.id.ll_wait_goods)
    LinearLayout llWaitGoods;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.v_line_four)
    View vLineFour;
    @BindView(R.id.ll_finish)
    LinearLayout llFinish;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SwipeRefreshLayout srlContent;
    private ServiceOrderListAdapter serviceOrderListAdapter;
    //1未付款 2已付款 3已派单  4已完成
    private int orderStatus = 1;
    private String userId;
    private List<ServiceOrderBean> serviceOrderBeans;
    private int position;
    private String type;
    private String orderNum;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI iwxapi;

    private String userid;
    private String orderid;
    private String couponid;
    private int payWay;
    private int payType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_order;
    }

    @Override
    protected void initView(Bundle bundle) {
        //从本地获取userId
        userId = SPUtils.getInstance().getString("userId");

        userid = userId;
        //服务订单实体类
        serviceOrderBeans = new ArrayList<>();
        //线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //垂直分布
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        rvContent.setLayoutManager(linearLayoutManager);
        //适配器
        serviceOrderListAdapter = new ServiceOrderListAdapter(getContext(), serviceOrderBeans);
        //设置按钮的类型 未付款 待付款 待发货 已完成
        serviceOrderListAdapter.setButtonType(orderStatus);
        serviceOrderListAdapter.setListener(this);
        //设置适配器
        rvContent.setAdapter(serviceOrderListAdapter);

    }

    @Override
    protected void onEvent() {
        //点击未付款时
        llNoPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置订单状态为1
                orderStatus = 1;
                tvNoPayment.setTextColor(Color.parseColor("#e10e05"));
                tvNoFinish.setTextColor(Color.parseColor("#000000"));
                tvWaitGoods.setTextColor(Color.parseColor("#000000"));
                tvFinish.setTextColor(Color.parseColor("#000000"));
                vLineOne.setVisibility(View.VISIBLE);
                vLineTwo.setVisibility(View.INVISIBLE);
                vLineThree.setVisibility(View.INVISIBLE);
                vLineFour.setVisibility(View.INVISIBLE);
                //传值1
                serviceOrderListAdapter.setButtonType(orderStatus);
                showLoadingDialog();
                //请求 订单1状态
                presenter.ServiceOrderList(userId, orderStatus);


            }
        });
        llNoFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = 2;
                tvNoPayment.setTextColor(Color.parseColor("#000000"));
                tvNoFinish.setTextColor(Color.parseColor("#e10e05"));
                tvWaitGoods.setTextColor(Color.parseColor("#000000"));
                tvFinish.setTextColor(Color.parseColor("#000000"));
                vLineOne.setVisibility(View.INVISIBLE);
                vLineTwo.setVisibility(View.VISIBLE);
                vLineThree.setVisibility(View.INVISIBLE);
                vLineFour.setVisibility(View.INVISIBLE);
                serviceOrderListAdapter.setButtonType(orderStatus);
                showLoadingDialog();
                presenter.ServiceOrderList(userId, orderStatus);

            }
        });
        llWaitGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = 3;
                tvNoPayment.setTextColor(Color.parseColor("#000000"));
                tvNoFinish.setTextColor(Color.parseColor("#000000"));
                tvWaitGoods.setTextColor(Color.parseColor("#e10e05"));
                tvFinish.setTextColor(Color.parseColor("#000000"));
                vLineOne.setVisibility(View.INVISIBLE);
                vLineTwo.setVisibility(View.INVISIBLE);
                vLineThree.setVisibility(View.VISIBLE);
                vLineFour.setVisibility(View.INVISIBLE);
                serviceOrderListAdapter.setButtonType(orderStatus);
                showLoadingDialog();
                presenter.ServiceOrderList(userId, orderStatus);

            }
        });
        llFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = 4;
                tvWaitGoods.setTextColor(Color.parseColor("#000000"));
                tvFinish.setTextColor(Color.parseColor("#e10e05"));
                tvNoPayment.setTextColor(Color.parseColor("#000000"));
                tvNoFinish.setTextColor(Color.parseColor("#000000"));
                vLineOne.setVisibility(View.INVISIBLE);
                vLineTwo.setVisibility(View.INVISIBLE);
                vLineThree.setVisibility(View.INVISIBLE);
                vLineFour.setVisibility(View.VISIBLE);
                serviceOrderListAdapter.setButtonType(orderStatus);
                showLoadingDialog();
                presenter.ServiceOrderList(userId, orderStatus);
            }
        });
        srlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.ServiceOrderList(userId, orderStatus);
            }
        });
    }

    @Override
    protected BaseView getViewImp() {
        return this;
    }

    @Override
    protected void lazyFetchData() {
        showLoadingDialog();
        presenter.ServiceOrderList(userId, orderStatus);

    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);

    }

    @Override
    public void ServiceOrderListSuccess(List<ServiceOrderBean> serviceOrderBeans) {
        hideLoadingDialog();
        srlContent.setRefreshing(false);
        this.serviceOrderBeans.clear();
        this.serviceOrderBeans.addAll(serviceOrderBeans);
        serviceOrderListAdapter.notifyDataSetChanged();

    }

    @Override
    public void cancelOrderSuccess() {
        hideLoadingDialog();
        showToast("取消订单成功");
        serviceOrderBeans.remove(position);
        serviceOrderListAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void paySuccess() {
//        hideLoadingDialog();
//        showToast("支付成功");
//    }

    @Override
    public void cancelOrder(String orderNum, int position) {
        this.position = position;
        showLoadingDialog();
        presenter.cancelOrder(userId, orderNum);
    }

    @Override
    public void pay(String orderNum) {
        this.orderNum = orderNum;
        orderid = orderNum;
        createDialog();
        alertDialog.show();
    }

    @Override
    public void confirm(String orderid, int orderType, int payType) {
        orderType = 2;
        payType = 2;
        showLoadingDialog();
        presenter.confirm(orderid, orderType, payType);
    }

    public void confirmSuccess() {
        hideLoadingDialog();
        showToast("确认订单成功");
    }

    @Override
    public void finish(String orderNum) {

    }

    @Override
    public void itemClick() {
    }

    //创建支付弹出框
    private void createDialog() {
        //初始化build
        builder = new AlertDialog.Builder(getContext());
        //加载view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pay_type, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1 + "";
                payWay = 1;
                payType = 2;
                alertDialog.dismiss();
                showLoadingDialog();
                //presenter.pay(userId, orderNum, type);
                presenter.alipay(userid, orderid, couponid, payWay, payType);
            }
        });
        /**
         * 点击微信支付按钮进行支付
         */
        viewHolder.tvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payWay = 2;//微信支付，支付方式为2
                payType = 2;//服务订单，支付类型为2
                alertDialog.dismiss();
                showLoadingDialog();
                presenter.wxpay(userid, orderid, couponid, payWay, payType);
            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        alertDialog = builder.create();
    }

    //请求接口成功，开始支付
    @Override
    public void alipay(final String orderid) {
        hideLoadingDialog();
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
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

    @Override
    public void wxpay(WXPayBean wxPayBean) {
        hideLoadingDialog();
        toWXPay(wxPayBean);
    }

    private WeakHandler mWeakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    ServiceOrderFragment.PayResult payResult = new ServiceOrderFragment.PayResult((Map<String, String>) msg.obj);
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

    private void toWXPay(final WXPayBean wxPayBean) {
        iwxapi = WXAPIFactory.createWXAPI(getContext(), null); //初始化微信api
        iwxapi.registerApp(Constant.APP_ID); //注册appid  appid可以在开发平台获取

        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = Constant.APP_ID;//APPID
                Log.d("toWXPay","APPID为："+request.appId);
                request.partnerId = "1531823821"; //商户名
                Log.d("toWXPay","商户名为："+request.partnerId);
                request.prepayId = wxPayBean.getPrepayid(); //预付款ID
                Log.d("toWXPay","预付款ID为："+request.prepayId);
                request.packageValue = "Sign=WXPay";//固定值
                Log.d("toWXPay","固定值为："+request.packageValue);
                request.nonceStr = wxPayBean.getNoncestr();//随机数
                Log.d("toWXPay","随机数为："+request.nonceStr);
                request.timeStamp = String.valueOf(wxPayBean.getTimestamp());//时间戳
                Log.d("toWXPay","时间戳为："+request.timeStamp);
                request.sign = wxPayBean.getSign();//签名
                Log.d("toWXPay","签名为："+request.sign);
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_alipay)
        ImageView tvAlipay;
        @BindView(R.id.tv_we_chat)
        ImageView tvWeChat;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
