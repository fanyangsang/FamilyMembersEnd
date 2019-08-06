package com.wlan.familymembers.presenter.service;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.ServiceDetailsBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/10/18.
 */

public class ServiceDetailPresenter extends Contract.ServiceDetailContract.Presenter {
    @Override
    public void getServiceDetails(String serviceId) {
        addSubscribe(model.getServiceDetails(serviceId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        ServiceDetailsBean serviceDetailsBean;
                        Gson gson=new Gson();
                        serviceDetailsBean=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").toString(),ServiceDetailsBean.class);
                        view.getServiceDetailsSuccess(serviceDetailsBean);
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
}
