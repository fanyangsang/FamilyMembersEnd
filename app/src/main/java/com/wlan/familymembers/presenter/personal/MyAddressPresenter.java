package com.wlan.familymembers.presenter.personal;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

public class MyAddressPresenter extends Contract.MyAddressContract.Presenter {
    @Override
    //请求地址
    public void getAdrressPageInfo(String userId, int currentPage, int i) {
        addSubscribe(model.getAdrressPageInfo(userId, currentPage, i).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        List<AddressBean> addressBeans;
                        addressBeans = gson.fromJson(jsonObject.getJSONObject("body").getJSONArray("addresses").toString(), new TypeToken<List<AddressBean>>() {
                        }.getType());
                        for (int i = 0; i < addressBeans.size(); i++) {
                            //将默认地址存到本地
                            if (addressBeans.get(i).getDefaultAddress() == 0) {
                                SPUtils.getInstance().put("addressId", addressBeans.get(i).getId());
                                SPUtils.getInstance().put("addressPhone", addressBeans.get(i).getPhone());
                                SPUtils.getInstance().put("addressName", addressBeans.get(i).getName());
                                SPUtils.getInstance().put("addressService", addressBeans.get(i).getAddressServer());
                            }
                        }
                        view.getAdrressPageInfoSuccess(addressBeans);
                    } else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                    Log.e("保存地址时出现解析错误",Log.getStackTraceString(e));
                }
            }
        }));
    }

    @Override
    public void deleteAddress(String userId, String id) {
        addSubscribe(model.deleteAddress(userId, id).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.deleteAddressSuccess();
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
    public void submitAddressInformation(String id, String userId, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress) {
        addSubscribe(model.submitAddressInformation(id, userId, name, phone, address, addressService, lon, lat, defaultAddress).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.submitAddressInformation();
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