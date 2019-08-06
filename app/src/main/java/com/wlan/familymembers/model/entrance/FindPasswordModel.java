package com.wlan.familymembers.model.entrance;

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

public class FindPasswordModel implements Contract.FindPasswordContract.Model{
    @Override
    public Observable<ResponseBody> getCode(String type, String phone) {
        Map<String,String> map=new HashMap<>();
        map.put("type",type);
        map.put("phone",phone);
        return HttpUtils.initRetrofit().getCode(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> findPassword(String codeTwo, String newPassword, String phoneTwo) {
        Map<String,String> map=new HashMap<>();
        map.put("code",codeTwo);
        map.put("pwd",newPassword);
        map.put("phone",phoneTwo);
        return HttpUtils.initRetrofit().findPassword(map).compose(new ThreadTransformer());
    }


}
