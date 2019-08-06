package com.wlan.familymembers.presenter.personal;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class ContactCustomerPresenter extends Contract.ContactCustomerContract.Presenter{
    @Override
    public void ContactCustomer(String userId) {
        addSubscribe(model.ContactCustomer(userId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                     JSONObject data=jsonObject.getJSONObject("body").getJSONObject("customerService");
                     String content=data.getString("introduce");
                        view.ContactCustomerSuccess(content);
                    }else {
                        view.showErrorWithStatus("无数据");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析出错");
                }
            }
        }));
    }
}
