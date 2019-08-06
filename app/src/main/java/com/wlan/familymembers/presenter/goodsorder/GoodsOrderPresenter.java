package com.wlan.familymembers.presenter.goodsorder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.GoodsOrderBean;
import com.wlan.familymembers.bean.WXPayBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class GoodsOrderPresenter extends Contract.GoodsOrderContract.Presenter {
    //1未付款   2待发货  3待收货  4已完成
    @Override
    public void GoodsOrder(String userId, int orderStatus) {
        addSubscribe(model.GoodsOrder(userId, orderStatus).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        List<GoodsOrderBean> goodsOrderBeans;
                        goodsOrderBeans = gson.fromJson(jsonObject.getJSONObject("body").getJSONArray("data").toString(), new TypeToken<List<GoodsOrderBean>>() {
                        }.getType());
                        view.GoodsOrderSuccess(goodsOrderBeans);
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

    @Override
    public void cancelOrder(String userId, String oderNum) {
        addSubscribe(model.cancelOrder(userId, oderNum).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.cancelOrderSuccess();
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

    @Override
    public void warmGoods(String userId, String oderNum) {
        addSubscribe(model.warmGoods(userId, oderNum).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.warmGoodsSuccess();
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

    @Override
    public void alipay(String userid, String orderid, String couponid, int payWay, int payType) {
        addSubscribe(model.alipay(userid, orderid, couponid, payWay, payType).subscribe(new ResponseCallbackSubscriber(view) {
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.alipay(jsonObject.getJSONObject("body").getString("map"));
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


    @Override
    public void confirmGoods(String orderid, int orderType, int payType) {
        addSubscribe(model.confirmGoods(orderid, orderType, payType).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.confirmSuccess();
                    } else if (jsonObject.getInt("errorCode") == 3) {
                        view.delayedGoods();
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
