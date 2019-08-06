package com.wlan.familymembers.presenter.personal;

import android.util.Log;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.BaseInformationBean;
import com.wlan.familymembers.bean.LevelBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class PersonalCenterPresenter extends Contract.PersonalCenterContract.Presenter{
    @Override
    public void getBaseInformation(String userId) {
        addSubscribe(model.BaseInformation(userId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        BaseInformationBean baseInformationBean;
                        baseInformationBean=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("user").toString(),BaseInformationBean.class);
                        int a = jsonObject.getJSONObject("body").getInt("level");
                        //应该把a的数值直接传到个人中心的activity里面去
                        System.out.println("------------------------------------------"+a);
                        view.setLevel(a);
                        view.BaseInformation(baseInformationBean);
                    }else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "onSuccess: "+e.getMessage());
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }
}
