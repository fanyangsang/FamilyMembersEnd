package com.wlan.familymembers.view.activity.goodsorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.DateAdapter;
import com.wlan.familymembers.adapter.TimeAdapter;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.bean.ServiceInfoBean;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.goodsorder.ServicePaymentDetailsModel;
import com.wlan.familymembers.presenter.goodsorder.ServicePaymentDetailsPresenter;
import com.wlan.familymembers.view.activity.personal.EditAddressActivity;
import com.wlan.familymembers.view.activity.personal.MyCouponActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicePaymentDetailsActivity extends BaseActivity<ServicePaymentDetailsModel, ServicePaymentDetailsPresenter> implements Contract.ServicePaymentDetailsContract.View {
    AlertDialog.Builder builder;
    Dialog alertDialog;
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
    @BindView(R.id.tv_order)
    TextView tvOrder;
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
    @BindView(R.id.tv_service_time)
    TextView tvservicetime;
    @BindView(R.id.tv_service_date)
    TextView tvservicedate;
    private DateAdapter dateAdapter;
    private TimeAdapter timeAdapter;
    private AddressBean addressBean;
    private ServiceInfoBean serviceInfoBean;
    //地址id
    private String addressId;
    public static ServicePaymentDetailsActivity instance;
    //月日
    private ArrayList<String> date;
    //星期
    private ArrayList<String> week;
    //时间
    private static String[] time = {"08:00", "08:30", "09:00", "09:30",
            "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30"};
    //年
    private String year;

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment_datails;
    }

    @Override
    public void initView() {
        instance = this;
        addressBean = new AddressBean();
        serviceInfoBean = new ServiceInfoBean();
        llServiceTime.setVisibility(View.VISIBLE);
        //        createDialog();
        String userId = SPUtils.getInstance().getString("userId");
        String serviceId = getIntent().getStringExtra("serviceId");
        //设置控件颜色
        SPUtils.getInstance().put("ItemDatePosition", "0");
        SPUtils.getInstance().put("Itemtiemosition", "0");

        //动态数组
        date = new ArrayList<>();
        week = new ArrayList<>();
        //        time = new ArrayList<>();
        date.clear();
        week.clear();
        //        time.clear();

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");//获取年
        Date date2 = new Date(System.currentTimeMillis());
        year = simpleDateFormat2.format(date2);
        //存入日期   月日
        for (int i = 1; i < 4; i++) {
            //获取当前时间戳
            Long data = Long.valueOf(com.wlan.familymembers.utils.TimeUtils.getTime());
            data = data + Long.valueOf(i * 86400);
            //存入月日
            String s = com.wlan.familymembers.utils.TimeUtils.MonthDay(String.valueOf(data));
            date.add(s);

        }

        long data = Long.valueOf(com.wlan.familymembers.utils.TimeUtils.getTime13());
        long tian = Long.valueOf(86400000);
        //存入周几
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");//周几
        data += tian;
        week.add("明天");
        data += tian;
        week.add(simpleDateFormat.format(data));
        data += tian;
        week.add(simpleDateFormat.format(data));

        //设置服务日期
        tvservicedate.setText(year + "-" + date.get(0));
        tvservicetime.setText(time[0]);
        showLoadingDialog();
        presenter.ServicePaymentDetails(userId, serviceId);
    }

    private void createDialog() {
        showLoadingDialog();

        alertDialog = new Dialog(this, R.style.BottomDialog);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.dialog_service_time, null);
        ViewHolderList viewHolderList = new ViewHolderList(view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolderList.rvDate.setLayoutManager(linearLayoutManager);
        dateAdapter = new DateAdapter(this, date, week);
        viewHolderList.rvDate.setAdapter(dateAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        viewHolderList.rvTime.setLayoutManager(gridLayoutManager);
        timeAdapter = new TimeAdapter(this, time);
        viewHolderList.rvTime.setAdapter(timeAdapter);
        viewHolderList.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //设置view
        alertDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //设置控件宽度为屏幕宽度
        layoutParams.width = ScreenUtils.getScreenWidth();
        //设置布局参数
        view.setLayoutParams(layoutParams);
        //设置可取消
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        hideLoadingDialog();
    }

    //设置日期
    public void dateposition(int position) {
        dateAdapter.notifyDataSetChanged();
        tvservicedate.setText(year + "-" + date.get(position));
    }

    //设置时间
    public void itemposition(int position) {
        timeAdapter.notifyDataSetChanged();
        tvservicetime.setText(time[position]);
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //服务时间
        llServiceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                createDialog();
                //显示对话框
                alertDialog.show();
            }
        });
        //添加地址
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicePaymentDetailsActivity.this, EditAddressActivity.class);
                intent.putExtra("key", 0);
                intent.putExtra("order", 1);
                startActivity(intent);
            }
        });
        //优惠券
        llCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicePaymentDetailsActivity.this, MyCouponActivity.class);
                startActivity(intent);
            }
        });
        //立即支付
        btnImmediatePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvaddress = tvAddress.getText().toString();
                if (TextUtils.isEmpty(tvaddress)) {
                    showToast("请输入地址！");
                    return;
                } else {
                    String serviceId = getIntent().getStringExtra("serviceId");
                    String userId = SPUtils.getInstance().getString("userId");
                    String appointmentTime = tvservicedate.getText().toString() + " " + tvservicetime.getText().toString();
                    presenter.submitServiceOrder(userId, addressId, serviceId, "", appointmentTime, "");
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
    public void ServicePaymentDetailsSuccess(ServiceInfoBean serviceInfoBean, AddressBean addressBean, boolean isDefaultAddress) {
        this.serviceInfoBean = serviceInfoBean;
        this.addressBean = addressBean;
        if (isDefaultAddress) {
            tvAddressHead.setVisibility(View.GONE);
            llAddressHead.setVisibility(View.VISIBLE);
            tvName.setText(addressBean.getName() + "  " + addressBean.getPhone());
            tvAddress.setText(addressBean.getAddressServer());
            addressId = addressBean.getId();
        } else {
            //            tvAddressHead.setVisibility(View.VISIBLE);
            //            llAddressHead.setVisibility(View.GONE);
        }
        tvOrder.setText("服务订单");
        Glide.with(this).load(HttpUtils.BASE_URL + serviceInfoBean.getPic()).into(ivImg);
        tvGoodsName.setText(serviceInfoBean.getName());
        tvGoodsSummary.setText(serviceInfoBean.getSellingPoint());
        tvMoney.setText("￥" + serviceInfoBean.getPrice());
        tvGoodsNumber.setText("x" + serviceInfoBean.getNum());
        tvRealPaymentNumber.setText("￥" + serviceInfoBean.getPrePay());
        tvTime.setText(serviceInfoBean.getDate());
        tvAdvancePrice.setText("预付款： ￥" + serviceInfoBean.getPrePay());
        hideLoadingDialog();
    }

    //提交订单成功后
    @Override
    public void submitServiceOrder(String hint) {
        finish();
        showToast("提交成功！订单id：" + hint);
    }

    static class ViewHolderList extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_close)
        ImageView ivClose;
        @BindView(R.id.rv_date)
        RecyclerView rvDate;
        @BindView(R.id.rv_time)
        RecyclerView rvTime;

        ViewHolderList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //添加新地址成功后调用这个方法设置到订单上
    public void address() {
        tvName.setText(SPUtils.getInstance().getString("addressName") + "     " + SPUtils.getInstance().getString("addressPhone"));
        tvAddress.setText(SPUtils.getInstance().getString("addressService"));
        //更改新的商品id
        addressId = SPUtils.getInstance().getString("addressId");
    }
}
