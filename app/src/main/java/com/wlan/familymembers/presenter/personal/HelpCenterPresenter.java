package com.wlan.familymembers.presenter.personal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.HelpCenterBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class HelpCenterPresenter extends Contract.HelpCenterContract.Presenter{
    @Override
    public void getHelpCenterData() {
        addSubscribe(model.getHelpCenterData().subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        List<HelpCenterBean> helpCenterBeans;
                        helpCenterBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONArray("list").toString(),new TypeToken<List<HelpCenterBean>>(){
                        }.getType());
                        view.getHelpCenterData(helpCenterBeans);
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
}
