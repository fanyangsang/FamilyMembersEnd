package com.wlan.familymembers.presenter.personal;

import com.blankj.utilcode.util.SPUtils;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class ChangePasswordPresenter extends Contract.ChangePasswordContract.Presenter{
    @Override
    public void changePassword(final String userId, String oldPassword, final String newPassword) {
        addSubscribe(model.changePassword(userId,oldPassword,newPassword).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                       view.changePasswordSuccess();
                        saveUserInfo(newPassword,userId);
                    }else {

                    }
                } catch (JSONException e) {
                    view.showErrorWithStatus("解析出错");
                    e.printStackTrace();
                }
            }
            private void saveUserInfo(String password,String userId) {
                SPUtils.getInstance().put("password",password);
                SPUtils.getInstance().put("userId",userId);
            }
        }));
    }
}
