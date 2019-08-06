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
 * Created by Administrator on 2018/9/28.
 */

public class ChangePhoneModel implements Contract.ChangePhoneContract.Model{
    @Override
    public Observable<ResponseBody> getCode(String type, String phone) {
        Map<String,String> map=new HashMap<>();
        map.put("type",type);
        map.put("phone",phone);
        return HttpUtils.initRetrofit().getCode(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> changePhone(String userId, String code, String tel2) {
        Map<String,String> map=new HashMap<>();
        map.put("userid",userId);
        map.put("code",code);
        map.put("phone",tel2);
        return HttpUtils.initRetrofit().changePhone(map).compose(new ThreadTransformer());
    }
}
