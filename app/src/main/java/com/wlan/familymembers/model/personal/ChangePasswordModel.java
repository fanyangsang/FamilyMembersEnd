package com.wlan.familymembers.model.personal;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/25.
 */

public class ChangePasswordModel implements Contract.ChangePasswordContract.Model{
    @Override
    public Observable<ResponseBody> changePassword(String userId, String oldPassword, String newPassword) {
        Map<String,String> map=new HashMap<>();
        map.put("userid",userId);
        map.put("oldPwd",oldPassword);
        map.put("newPwd",newPassword);
        return HttpUtils.initRetrofit().changePassword(map).compose(new ThreadTransformer());
    }
}
