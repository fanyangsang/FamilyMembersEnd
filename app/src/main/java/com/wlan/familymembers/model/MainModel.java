package com.wlan.familymembers.model;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class MainModel implements Contract.MainContract.Model{
    @Override
    public Observable<ResponseBody> login(String phone, String password) {
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",password);
        return HttpUtils.initRetrofit().login(map).compose(new ThreadTransformer());
    }
}
