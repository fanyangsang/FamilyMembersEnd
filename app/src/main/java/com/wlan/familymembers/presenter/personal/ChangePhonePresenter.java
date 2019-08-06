package com.wlan.familymembers.presenter.personal;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/28.
 */

public class ChangePhonePresenter extends Contract.ChangePhoneContract.Presenter{
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
    public void changePhone(String userId, String code, final String tel2) {
        addSubscribe(model.changePhone(userId,code,tel2).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.changePhoneSuccess();
                        SPUtils.getInstance().put("phone",tel2);
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
