package com.wlan.familymembers.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.bean.GoodsInfoBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/19.
 */

public class PaymentDetailsPresenter extends Contract.PaymentDetailsContract.Presenter {
    @Override
    public void getPaymentDetails(String userId, String goodsId, String attrPath, final int num) {
        addSubscribe(model.getPaymentDetails(userId, goodsId, attrPath, num).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        AddressBean addressBean;
                        GoodsInfoBean goodsInfoBean;
                        boolean isDefaultAddress = jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isDefaultAddress");
                        if (isDefaultAddress) {
                            addressBean = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONObject("defaultAddress").toString(), AddressBean.class);
                        } else {
                            addressBean = null;
                        }
                        goodsInfoBean = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONObject("goodsInfo").toString(), GoodsInfoBean.class);
                        view.getPaymentDetailsSuccess(addressBean, goodsInfoBean, isDefaultAddress);
                    } else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    view.showErrorWithStatus("解析错误");
                    e.printStackTrace();
                }

            }
        }));
    }

    //提交商品订单
    @Override
    public void submitOrder(String userId, String goodsid, String attrPath, String addressId, String remark, String discountId, String num) {
        addSubscribe(model.submitOrder(userId, goodsid, attrPath, addressId, remark, discountId, num).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    Log.d("submitOrder","+"+jsonObject);
                    if (jsonObject.getBoolean("success")) {
                        JSONObject data = jsonObject.getJSONObject("body");
                        String content = data.getString("data");
                        view.submitOrder(content);
                        view.intentPay(content);
                    } else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    view.showErrorWithStatus("解析错误");
                    e.printStackTrace();
                }

            }
        }));
    }
}
