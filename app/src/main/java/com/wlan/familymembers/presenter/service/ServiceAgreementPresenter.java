package com.wlan.familymembers.presenter.service;

import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class ServiceAgreementPresenter extends Contract.ServiceAgreementContract.Presenter{
    @Override
    public void serviceAgreement() {
        addSubscribe(model.serviceAgreement().subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        JSONObject data=jsonObject.getJSONObject("body");
                        JSONObject data2=data.getJSONObject("protocol");
                        String content=data2.getString("content");
                        view.getServiceAgreementSuccess(content);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }
}
