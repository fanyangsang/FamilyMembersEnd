package com.wlan.familymembers.presenter.personal;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.HelpCenterDetailsBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/29.
 */

public class HelpCenterDetailsPresenter extends Contract.HelpCenterDetailsContract.Presenter{
    @Override
    public void getHelpCenterDetails(String id) {
        addSubscribe(model.getHelpCenterDetails(id).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        HelpCenterDetailsBean helpCenterDetailsBean;
                        helpCenterDetailsBean=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("detail").toString(),HelpCenterDetailsBean.class);
                        view.getHelpCenterDetailsSuccess(helpCenterDetailsBean);

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
