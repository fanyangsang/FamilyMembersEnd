package com.wlan.familymembers.model.entrance;

import android.nfc.NfcEvent;

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

public class RegisterModel implements Contract.RegisterContract.Model{
    @Override
    public Observable<ResponseBody> register(String phone, String newPassword, String code) {
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",newPassword);
        map.put("code",code);
        return HttpUtils.initRetrofit().register(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> getCode(String type,String phoneTwo) {
        Map<String,String> map=new HashMap<>();
        map.put("type",type);
        map.put("phone",phoneTwo);
        return HttpUtils.initRetrofit().getCode(map).compose(new ThreadTransformer());
    }
}
