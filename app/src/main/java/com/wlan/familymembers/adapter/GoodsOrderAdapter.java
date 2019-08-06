package com.wlan.familymembers.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.bean.GoodsOrderBean;
import com.wlan.familymembers.view.activity.goodsorder.PaymentDetailsActivity;
import com.wlan.familymembers.view.fragment.GoodsOrderFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 类作用：
 * Created by Administrator on 2018/9/19.
 */

public class GoodsOrderAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsOrderBean> goodsOrderBeans;
    private int orderStatus;
    private GoodsOrderAdapterListener goodsOrderAdapterListener;
    private String orderNum;
    private String orderid;
    private int orderType = 1;//此处是商品订单，所以支付方式为1；
    private int payType;

    public GoodsOrderAdapter(Context context, List<GoodsOrderBean> goodsOrderBeans) {
        this.context = context;
        this.goodsOrderBeans = goodsOrderBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_goods_order, parent, false);
        return new ViewHolderGoodsList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderGoodsList viewHolderGoodsList = (ViewHolderGoodsList) holder;
        if (orderStatus == 1) {
            viewHolderGoodsList.btnCancel.setVisibility(View.VISIBLE);
            viewHolderGoodsList.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderGoodsList.tvState.setText("未付款");
            viewHolderGoodsList.btnCancel.setText("取消订单");
            viewHolderGoodsList.btnGoToPay.setText("去支付");

            orderNum = goodsOrderBeans.get(position).getOrderNum();
            Log.d("orderNum", "-----" + orderNum);
            Glide.with(context).load(HttpUtils.BASE_URL + (goodsOrderBeans.get(position).getOrderContent().getPic()).substring(1)).into(viewHolderGoodsList.ivImg);
            viewHolderGoodsList.tvGoodsName.setText(goodsOrderBeans.get(position).getOrderContent().getGoodsName());
            Log.d("测试", "" + goodsOrderBeans.get(position).getOrderContent().getGoodsName());
            viewHolderGoodsList.tvGoodsSummary.setText(goodsOrderBeans.get(position).getOrderContent().getSellPoint());
            viewHolderGoodsList.tvMoney.setText("￥" + goodsOrderBeans.get(position).getOrderContent().getPrice());
            viewHolderGoodsList.tvTime.setText(goodsOrderBeans.get(position).getDownTime());
            viewHolderGoodsList.tvRealPaymentNumber.setText("会员价： ￥" + goodsOrderBeans.get(position).getPriceFinal());
            viewHolderGoodsList.tvGoodsNumber.setText("x" + goodsOrderBeans.get(position).getOrderContent().getNum());
            //取消订单的点击事件
            viewHolderGoodsList.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsOrderAdapterListener.cancelOrder(orderNum, position);
                }
            });
            //支付的点击事件
            viewHolderGoodsList.btnGoToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderNum = goodsOrderBeans.get(position).getOrderNum();
                    goodsOrderAdapterListener.pay(orderNum);
                    Log.d("orderNum", "+++++" + orderNum);
                }
            });
        } else if (orderStatus == 2) {
            viewHolderGoodsList.btnCancel.setVisibility(View.GONE);
            viewHolderGoodsList.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderGoodsList.tvState.setText("待发货");
            viewHolderGoodsList.btnGoToPay.setText("提醒发货");
            orderNum = goodsOrderBeans.get(position).getOrderNum();
            Glide.with(context).load(HttpUtils.BASE_URL + (goodsOrderBeans.get(position).getOrderContent().getPic()).substring(1)).into(viewHolderGoodsList.ivImg);
            viewHolderGoodsList.tvGoodsName.setText(goodsOrderBeans.get(position).getOrderContent().getGoodsName());
            viewHolderGoodsList.tvGoodsSummary.setText(goodsOrderBeans.get(position).getOrderContent().getSellPoint());
            viewHolderGoodsList.tvMoney.setText("￥" + goodsOrderBeans.get(position).getOrderContent().getPrice());
            viewHolderGoodsList.tvTime.setText(goodsOrderBeans.get(position).getDownTime());
            viewHolderGoodsList.tvGoodsNumber.setText("x" + goodsOrderBeans.get(position).getOrderContent().getNum());
            viewHolderGoodsList.tvRealPaymentNumber.setText("会员价： ￥" + goodsOrderBeans.get(position).getPriceFinal());
            //提醒发货的点击事件
            viewHolderGoodsList.btnGoToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsOrderAdapterListener.warmGoods(orderNum);
                }
            });
        } else if (orderStatus == 3) {
            viewHolderGoodsList.btnCancel.setVisibility(View.INVISIBLE);
            viewHolderGoodsList.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderGoodsList.tvState.setText("待收货");
            viewHolderGoodsList.btnGoToPay.setText("确认收货");
            orderNum = goodsOrderBeans.get(position).getOrderNum();
            orderid = goodsOrderBeans.get(position).getOrderid();
            payType = 1; //暂时只有支付宝支付，所以支付方式为1
            Glide.with(context).load(HttpUtils.BASE_URL + (goodsOrderBeans.get(position).getOrderContent().getPic()).substring(1)).into(viewHolderGoodsList.ivImg);
            viewHolderGoodsList.tvGoodsName.setText(goodsOrderBeans.get(position).getOrderContent().getGoodsName());
            viewHolderGoodsList.tvGoodsSummary.setText(goodsOrderBeans.get(position).getOrderContent().getSellPoint());
            viewHolderGoodsList.tvMoney.setText("￥" + goodsOrderBeans.get(position).getOrderContent().getPrice());
            viewHolderGoodsList.tvTime.setText(goodsOrderBeans.get(position).getDownTime());
            viewHolderGoodsList.tvGoodsNumber.setText("x" + goodsOrderBeans.get(position).getOrderContent().getNum());
            viewHolderGoodsList.tvRealPaymentNumber.setText("会员价： ￥" + goodsOrderBeans.get(position).getPriceFinal());
            //确认收货的点击事件
            viewHolderGoodsList.btnGoToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsOrderAdapterListener.confirmGoods(orderid, orderType, payType);
                }
            });
        } else if (orderStatus == 4) {
            viewHolderGoodsList.btnCancel.setVisibility(View.INVISIBLE);
            viewHolderGoodsList.btnGoToPay.setVisibility(View.VISIBLE);
            viewHolderGoodsList.tvState.setText("已完成");
            viewHolderGoodsList.btnGoToPay.setText("已完成");
            orderNum = goodsOrderBeans.get(position).getOrderNum();
            Glide.with(context).load(HttpUtils.BASE_URL + (goodsOrderBeans.get(position).getOrderContent().getPic()).substring(1)).into(viewHolderGoodsList.ivImg);
            viewHolderGoodsList.tvGoodsName.setText(goodsOrderBeans.get(position).getOrderContent().getGoodsName());
            viewHolderGoodsList.tvGoodsSummary.setText(goodsOrderBeans.get(position).getOrderContent().getSellPoint());
            viewHolderGoodsList.tvMoney.setText("￥" + goodsOrderBeans.get(position).getOrderContent().getPrice());
            viewHolderGoodsList.tvTime.setText(goodsOrderBeans.get(position).getDownTime());
            viewHolderGoodsList.tvGoodsNumber.setText("x" + goodsOrderBeans.get(position).getOrderContent().getNum());
            viewHolderGoodsList.tvRealPaymentNumber.setText("会员价： ￥" + goodsOrderBeans.get(position).getPriceFinal());
        }

    }

    @Override
    public int getItemCount() {
        return goodsOrderBeans.size();
    }

    public interface GoodsOrderAdapterListener {
        //取消订单
        void cancelOrder(String oderNum, int position);

        //去支付
        void pay(String orderNum);

        //提醒发货
        void warmGoods(String oderNum);

        //确认收货
        void confirmGoods(String orderid, int orderType, int payType);
    }


    public void setButton(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setListener(GoodsOrderAdapterListener goodsOrderAdapterListener) {
        this.goodsOrderAdapterListener = goodsOrderAdapterListener;
    }

    static class ViewHolderGoodsList extends RecyclerView.ViewHolder {
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
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        @BindView(R.id.btn_go_to_pay)
        Button btnGoToPay;

        ViewHolderGoodsList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
