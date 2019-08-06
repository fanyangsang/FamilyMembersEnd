package com.wlan.familymembers.model.service;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/10/18.
 */

public class ServiceDetailModel implements Contract.ServiceDetailContract.Model{
    @Override
    public Observable<ResponseBody> getServiceDetails(String serviceId) {
        Map<String,String> map=new HashMap<>();
        map.put("serviceId",serviceId);
        return HttpUtils.initRetrofit().getServiceDetails(map).compose(new ThreadTransformer());
    }
}
