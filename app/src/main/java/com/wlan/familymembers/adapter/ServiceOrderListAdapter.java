package com.wlan.familymembers.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.ServiceOrderBean;
import com.wlan.familymembers.view.fragment.ServiceOrderFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/18.
 */

public class ServiceOrderListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ServiceOrderBean> serviceOrderBeans;
    private int orderStatus;
    private ServiceOrderListAdapterListener serviceOrderListAdapterListener;
    private String orderNum;
    private String orderid;
    private int orderType = 2;//此处是服务订单，所以支付方式为2；
    private int payType;

    public ServiceOrderListAdapter(Context context, List<ServiceOrderBean> serviceOrderBeans) {
        this.context = context;
        this.serviceOrderBeans = serviceOrderBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_service_order, parent, false);
        return new ViewHolderService(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderService viewHolderService = (ViewHolderService) holder;
        orderNum = serviceOrderBeans.get(position).getOrderNum();
        viewHolderService.tvJob.setText(serviceOrderBeans.get(position).getOrderContent().get(0).getSellPoint());
        viewHolderService.tvAddress.setText(serviceOrderBeans.get(position).getOrderContent().get(0).getUserhmAddressVo().getAddress());
        viewHolderService.tvDoorTime.setText("下单时间" + serviceOrderBeans.get(position).getDownTime());
        viewHolderService.tvAppointmentTime.setText("预约时间" + serviceOrderBeans.get(position).getOrderContent().get(0).getSubTime());
        viewHolderService.tvMoney.setText("￥" + serviceOrderBeans.get(position).getPrice());
        Glide.with(context).load(HttpUtils.BASE_URL + serviceOrderBeans.get(position).getOrderContent().get(0).getPic()).into(viewHolderService.ivImg);
        if (orderStatus == 1) {
            viewHolderService.tvState.setText("未付款");
            viewHolderService.llName.setVisibility(View.GONE);
            viewHolderService.llPhone.setVisibility(View.GONE);
            viewHolderService.tvRealPayment.setVisibility(View.GONE);
            viewHolderService.btnCancel.setVisibility(View.VISIBLE);
            viewHolderService.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderService.btnCancel.setText("取消订单");
            viewHolderService.btnGoToPay.setText("去支付");
            viewHolderService.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceOrderListAdapterListener.cancelOrder(orderNum, position);
                }
            });
            viewHolderService.btnGoToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceOrderListAdapterListener.pay(orderNum);
                }
            });

        } else if (orderStatus == 2) {
            viewHolderService.tvState.setText("未派单");
            viewHolderService.llName.setVisibility(View.GONE);
            viewHolderService.llPhone.setVisibility(View.GONE);
            viewHolderService.tvRealPayment.setVisibility(View.GONE);
            viewHolderService.btnCancel.setVisibility(View.GONE);
            viewHolderService.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderService.btnGoToPay.setText("催单");
        } else if (orderStatus == 3) {
            orderid = serviceOrderBeans.get(position).getOrderid();
            payType = 1;
            viewHolderService.tvState.setText("已派单");
            viewHolderService.llName.setVisibility(View.VISIBLE);
            viewHolderService.llPhone.setVisibility(View.VISIBLE);
            viewHolderService.tvRealPayment.setVisibility(View.VISIBLE);
            viewHolderService.btnCancel.setVisibility(View.GONE);
            viewHolderService.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderService.btnGoToPay.setText("确认订单");
            viewHolderService.tvName.setText(serviceOrderBeans.get(position).getOrderContent().get(0).getWorkerName());
            viewHolderService.tvPhone.setText(serviceOrderBeans.get(position).getOrderContent().get(0).getWorkerTel());
            viewHolderService.tvRealPayment.setText("实付款:" + serviceOrderBeans.get(position).getPriceFinal());
            viewHolderService.btnGoToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceOrderListAdapterListener.confirm(orderid, orderType, payType);
                }
            });
        } else {
            viewHolderService.tvState.setText("已完成");
            viewHolderService.llName.setVisibility(View.VISIBLE);
            viewHolderService.llPhone.setVisibility(View.VISIBLE);
            viewHolderService.tvRealPayment.setVisibility(View.VISIBLE);
            viewHolderService.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderService.btnCancel.setVisibility(View.GONE);
            viewHolderService.btnGoToPay.setText("已完成");
            viewHolderService.tvName.setText(serviceOrderBeans.get(position).getOrderContent().get(0).getWorkerName());
            viewHolderService.tvPhone.setText(serviceOrderBeans.get(position).getOrderContent().get(0).getWorkerTel());
            viewHolderService.tvRealPayment.setText("实付款:" + serviceOrderBeans.get(position).getPriceFinal());
            viewHolderService.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceOrderListAdapterListener.finish(orderNum);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return serviceOrderBeans.size();
    }

    public void setButtonType(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public interface ServiceOrderListAdapterListener {
        //取消订单
        void cancelOrder(String orderNum, int position);

        //去支付
        void pay(String orderNum);

        //待确认
        void confirm(String orderid, int orderType, int payType);

        //已完成
        void finish(String orderNum);

        //每个item的点击
        void itemClick();
    }

    public void setListener(ServiceOrderListAdapterListener serviceOrderListAdapterListener) {
        this.serviceOrderListAdapterListener = serviceOrderListAdapterListener;
    }

    static class ViewHolderService extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_job)
        TextView tvJob;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_appointment_time)
        TextView tvAppointmentTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_door_time)
        TextView tvDoorTime;
        @BindView(R.id.iv_contacts)
        ImageView ivContacts;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_name)
        LinearLayout llName;
        @BindView(R.id.iv_tel)
        ImageView ivTel;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.ll_phone)
        LinearLayout llPhone;
        @BindView(R.id.tv_real_payment)
        TextView tvRealPayment;
        @BindView(R.id.v_line)
        View vLine;
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        @BindView(R.id.btn_go_to_pay)
        Button btnGoToPay;

        ViewHolderService(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
