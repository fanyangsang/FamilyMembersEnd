package com.wlan.familymembers.presenter.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.ServiceOrderBean;
import com.wlan.familymembers.bean.WXPayBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class ServiceOrderPresenter extends Contract.ServiceOrderContract.Presenter{
    @Override
    public void ServiceOrderList(String userId, int orderStatus) {
        addSubscribe(model.ServiceOrderList(userId,orderStatus).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        List<ServiceOrderBean> serviceOrderBeans;
                        serviceOrderBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONArray("data").toString(),new TypeToken<List<ServiceOrderBean>>(){
                        }.getType());
                        view.ServiceOrderListSuccess(serviceOrderBeans);
                    }else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

    @Override
    public void cancelOrder(String userId, String orderNum) {
        addSubscribe(model.cancelOrder(userId,orderNum).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.cancelOrderSuccess();
                    }else {
                        view.showErrorWithStatus("取消订单失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

//    @Override
//    public void pay(String userId, String orderNum, String type) {
//        addSubscribe(model.pay(userId,orderNum,type).subscribe(new ResponseCallbackSubscriber(view) {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                try {
//                    if (jsonObject.getBoolean("success")){
//                        view.paySuccess();
//                    }else {
//                        view.showErrorWithStatus("支付失败");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    view.showErrorWithStatus("解析错误");
//                }
//
//            }
//        }));
//    }

    @Override
    public void alipay(String userid, String orderid, String couponid,int payWay, int payType) {
        addSubscribe(model.alipay(userid,orderid,couponid,payWay,payType).subscribe(new ResponseCallbackSubscriber(view) {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.alipay(jsonObject.getJSONObject("body").getString("map"));
                    }else {
                        String msg=jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

    @Override
    public void confirm(String orderid,int orderType,int payType) {
        addSubscribe(model.confirm(orderid,orderType,payType).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.confirmSuccess();
                    }else {
                        String msg=jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

    @Override
    public void wxpay(String userid, String orderid, String couponid, int payWay, int payType) {
        addSubscribe(model.wxpay(userid, orderid, couponid, payWay, payType).subscribe(new ResponseCallbackSubscriber(view) {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        WXPayBean wxPayBean;
                        wxPayBean = new Gson().fromJson(jsonObject.getJSONObject("body").getJSONObject("map").toString(),new TypeToken<WXPayBean>() {
                        }.getType());
                        view.wxpay(wxPayBean);
                    } else {
                        String msg = jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

}
