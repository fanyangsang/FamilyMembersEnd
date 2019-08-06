package com.wlan.familymembers.presenter.entrance;

import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class FindPasswordPresenter extends Contract.FindPasswordContract.Presenter{
    @Override
    public void getCode(String type, String phone) {
        addSubscribe(model.getCode(type,phone).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        JSONObject data=jsonObject.getJSONObject("body");
                        String code=data.getString("code");
                        view.getCodeSuccess(code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

    @Override
    public void findPassword(String codeTwo, String newPassword, String phoneTwo) {
        addSubscribe(model.findPassword(codeTwo,newPassword,phoneTwo).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.findPasswordSuccess();
                    }else {
                        view.showErrorWithStatus("找寻密码失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }
        }));
    }

}
