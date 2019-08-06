package com.wlan.familymembers.presenter.personal;

import android.util.Log;

import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.BaseInformationBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * 类作用：
 * Created by Administrator on 2018/9/21.
 */

public class BaseInformationPresenter extends Contract.BaseInformationContract.Presenter{
    @Override
    public void getBaseInformation(String userId) {
        addSubscribe(model.BaseInformation(userId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    Log.i(TAG, "onSuccess: " + jsonObject.toString());
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        BaseInformationBean baseInformationBean;
                        baseInformationBean=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("user").toString(),BaseInformationBean.class);
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

    @Override
    public void uploadPicture(File imgFile, String userId) {
        addSubscribe(model.uploadPicture(imgFile,userId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.uploadPicture();
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

    @Override
    public void keep(String userId, final String i, String name) {
        addSubscribe(model.keep(userId,i,name).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.keepSuccess();
                    }else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析异常");
                }
            }
        }));
    }
}
