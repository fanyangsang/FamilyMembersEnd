package com.wlan.familymembers.presenter.entrance;

import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class RegisterPresenter extends Contract.RegisterContract.Presenter{

    @Override
    public void register(String phone, String newPassword, String code) {
        addSubscribe(model.register(phone,newPassword,code).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                       view.registerSuccess();
                    }else {
                        if (jsonObject.getInt("code")==2){
                            view.showErrorWithStatus("该手机号已注册");

                        }else {
                            view.showErrorWithStatus("验证码错误");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }

    @Override
    public void getCode(String type,String phoneTwo) {
        addSubscribe(model.getCode(type,phoneTwo).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.getCodeSuccess();
                    }else {
                        if (jsonObject.getInt("code")==2){
                            view.showErrorWithStatus("该手机已存在");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }
}
