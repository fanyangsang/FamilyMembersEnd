package com.wlan.familymembers.view.activity.goodsorder;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.bean.GoodsInfoBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.PaymentDetailsModel;
import com.wlan.familymembers.presenter.PaymentDetailsPresenter;
import com.wlan.familymembers.view.activity.personal.EditAddressActivity;
import com.wlan.familymembers.view.activity.personal.MyCouponActivity;

import butterknife.BindView;

/**
 * 类作用：
 * Created by Administrator on 2018/9/19.
 */

public class PaymentDetailsActivity extends BaseActivity<PaymentDetailsModel, PaymentDetailsPresenter> implements Contract.PaymentDetailsContract.View {

    @BindView(R.id.tv_content)
    TextView tvcontent;
    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_address_head)
    TextView tvAddressHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address_head)
    LinearLayout llAddressHead;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_goods_summary)
    TextView tvGoodsSummary;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_goods_number)
    TextView tvGoodsNumber;
    @BindView(R.id.tv_real_payment_number)
    TextView tvRealPaymentNumber;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_service_time)
    LinearLayout llServiceTime;
    @BindView(R.id.tv_advance_price)
    TextView tvAdvancePrice;
    @BindView(R.id.btn_immediate_payment)
    Button btnImmediatePayment;
    private AddressBean addressBean;
    private GoodsInfoBean goodsInfoBean;
    public static PaymentDetailsActivity instance;
    private String addressId;
    //商品数量
    private String num;
    private String orderid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_datails;
    }

    @Override
    public void initView() {
        instance = this;
        tvcontent.setText("填写订单");
        //隐藏服务时间
        llServiceTime.setVisibility(View.GONE);
        String goodsId = getIntent().getStringExtra("goodsId");
        String userId = SPUtils.getInstance().getString("userId");
        String attrPath = getIntent().getStringExtra("attrPath");
        int num = getIntent().getIntExtra("num", 0);
        addressBean = new AddressBean();
        goodsInfoBean = new GoodsInfoBean();
        //开启加载动画
        showLoadingDialog();
        //请求数据
        presenter.getPaymentDetails(userId, goodsId, attrPath, num);
        //将默认地址的数据设置到上方地址栏
        address();
    }

    @Override
    public void onEvent() {
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentDetailsActivity.this, EditAddressActivity.class);
                intent.putExtra("key", 0);
                intent.putExtra("order", 0);
                startActivity(intent);
            }
        });
        llCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentDetailsActivity.this, MyCouponActivity.class);
                startActivity(intent);
            }
        });
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //立即支付
        btnImmediatePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goodsId = getIntent().getStringExtra("goodsId");
                String userId = SPUtils.getInstance().getString("userId");
                String attrPath = getIntent().getStringExtra("attrPath");
                String tvaddress = tvAddress.getText().toString();
                //提交订单
                if (TextUtils.isEmpty(tvaddress)) {
                    showToast("请输入地址！");
                    return;
                } else {
                    showLoadingDialog();
                    presenter.submitOrder(userId, goodsId, attrPath, addressId, "", "", num);
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
    public void getPaymentDetailsSuccess(AddressBean addressBean, GoodsInfoBean goodsInfoBean, boolean isDefaultAddress) {
        hideLoadingDialog();
        this.addressBean = addressBean;
        this.goodsInfoBean = goodsInfoBean;
        if (isDefaultAddress) {
            //            llAddressHead.setVisibility(View.VISIBLE);
            //            tvAddressHead.setVisibility(View.GONE);
            tvName.setText(addressBean.getName() + "     " + addressBean.getPhone());
            tvAddress.setText(addressBean.getAddressServer());
            addressId = addressBean.getId();
        } else {
            //            tvAddressHead.setVisibility(View.VISIBLE);
            //            llAddressHead.setVisibility(View.GONE);
        }

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(10);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(this).load(HttpUtils.BASE_URL + (goodsInfoBean.getPic().substring(1))).apply(options).into(ivImg);
        tvGoodsName.setText(goodsInfoBean.getName());
        tvGoodsSummary.setText(goodsInfoBean.getSellingPoint());
        tvMoney.setText("￥" + goodsInfoBean.getPrice());
        tvTime.setText(goodsInfoBean.getDate());
        tvGoodsNumber.setText("x" + goodsInfoBean.getNum());
        tvRealPaymentNumber.setText("实付款：" + goodsInfoBean.getRealPayPrice());
        tvAdvancePrice.setText("预付款： ￥" + goodsInfoBean.getRealPayPrice());
        num = goodsInfoBean.getNum();
    }

    //提交订单成功后调用 返回订单id
    @Override
    public void submitOrder(String content) {
        hideLoadingDialog();
        finish();
        showToast("提交成功！ 订单id：" + content);
    }

    @Override
    public void intentPay(String orderid) {
                Intent intent = new Intent(PaymentDetailsActivity.this,GoodsPayActivity.class);
                this.orderid = orderid;
                String userId = SPUtils.getInstance().getString("userId");
                Log.d("intentPay","=========="+orderid);
                Log.d("intentPay","============"+userId);
                intent.putExtra("orderid",orderid);
                intent.putExtra("userId",userId);
                startActivity(intent);

        //需要把这笔订单的相关参数传到另一个activity里面去
    }


    //MyAddressPresenter添加地址成功后调用这个方法  设置新地址
    public void address() {
        tvName.setText(SPUtils.getInstance().getString("addressName") + "     " + SPUtils.getInstance().getString("addressPhone"));
        tvAddress.setText(SPUtils.getInstance().getString("addressService"));
        //更改新的商品id
        addressId = SPUtils.getInstance().getString("addressId");
    }
}
