package com.wlan.familymembers.presenter;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class MainPresenter extends Contract.MainContract.Presenter{
    @Override
    public void login(final String phone, final String password) {
        addSubscribe(model.login(phone,password).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        view.loginSuccess();
                    }else {
                        String msg=jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                        view.loginFaile();
                        SPUtils.getInstance().clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }
}
