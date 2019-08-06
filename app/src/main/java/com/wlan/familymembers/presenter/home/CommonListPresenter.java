package com.wlan.familymembers.presenter.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.ServiceClassificationBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class CommonListPresenter extends Contract.CommonListContract.Presenter {
    @Override
    public void getserviceClassificationTwo(String id) {
        addSubscribe(model.getserviceClassificationTwo(id).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        List<ServiceClassificationBean> serviceClassificationBeans;
                        serviceClassificationBeans = gson.fromJson(jsonObject.getJSONObject("body").getJSONArray("data").toString(), new TypeToken<List<ServiceClassificationBean>>() {
                        }.getType());
                        view.getserviceClassificationTwoSuccess(serviceClassificationBeans);
                    } else {
                        String msg = jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                    }
                } catch (JSONException e) {
                    view.showErrorWithStatus("解析错误");
                    e.printStackTrace();
                }
            }
        }));
    }
}
