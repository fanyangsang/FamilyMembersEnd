package com.wlan.familymembers.presenter.goodsorder;

import android.content.pm.ServiceInfo;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.bean.ServiceInfoBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/11/5.
 */

public class ServicePaymentDetailsPresenter extends Contract.ServicePaymentDetailsContract.Presenter {
    @Override
    public void ServicePaymentDetails(String userId, String serviceId) {
        addSubscribe(model.ServicePaymentDetails(userId, serviceId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        ServiceInfoBean serviceInfoBean;
                        AddressBean addressBean = null;
                        serviceInfoBean = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONObject("service").toString(), ServiceInfoBean.class);
                        try {
                            //用户没有地址时不会传defaultAddress集合
                            addressBean = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONObject("defaultAddress").toString(), AddressBean.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            view.showErrorWithStatus("请输入地址");
                        }
                        boolean isDefaultAddress = jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isDefaultAddress");
                        view.ServicePaymentDetailsSuccess(serviceInfoBean, addressBean, isDefaultAddress);
                    } else {
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
    public void submitServiceOrder(String userId, String addressId, String serviceId, String discountId, String appointmentTime, String remark) {
        addSubscribe(model.submitServiceOrder(userId, addressId, serviceId, discountId, appointmentTime, remark).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        JSONObject data = jsonObject.getJSONObject("body");
                        String content = data.getString("orderNum");
                        view.submitServiceOrder(content);
                    } else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }
}
