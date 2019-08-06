package com.wlan.familymembers.presenter.entrance;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/13.
 */

public class LoginPresenter extends Contract.LoginContract.Presenter{
    @Override
    public void login(final String phone, final String password) {
        addSubscribe(model.login(phone,password).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code" ) == 1){
                        JSONObject data=jsonObject.getJSONObject("body");
                        String userId=data.getString("userid");
                        view.loginSuccess();
                        saveUserInfo(phone,password,userId);
                    }else {
                        view.loginFail();
                    }
                } catch (JSONException e) {

                    view.showErrorWithStatus("解析错误");
                    e.printStackTrace();
                }
            }

            private void saveUserInfo(String phone, String password,String userId) {
                SPUtils.getInstance().put("phone",phone);
                SPUtils.getInstance().put("password",password);
                SPUtils.getInstance().put("userId",userId);
            }
        }));
    }
}
