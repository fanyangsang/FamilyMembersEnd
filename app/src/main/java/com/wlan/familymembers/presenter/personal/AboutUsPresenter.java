package com.wlan.familymembers.presenter.personal;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.AboutUsBean;
import com.wlan.familymembers.bean.BaseInformationBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class AboutUsPresenter extends Contract.AboutUsContract.Presenter{
    @Override
    public void getAboutUsInformation() {
        addSubscribe(model.getAboutUsInformation().subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){

                        Gson gson = new Gson();
                        AboutUsBean aboutUsBean;
                        aboutUsBean = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("aboutus").toString(),AboutUsBean.class);

//                        JSONObject data=jsonObject.getJSONObject("body").getJSONObject("aboutus");
//                        String content=data.getString("introduce");
                        view.getAboutUsInformationSuccess(aboutUsBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }
}
