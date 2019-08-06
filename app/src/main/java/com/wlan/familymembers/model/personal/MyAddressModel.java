package com.wlan.familymembers.model.personal;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.HTTP;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

public class MyAddressModel implements Contract.MyAddressContract.Model{
    @Override
    public Observable<ResponseBody> getAdrressPageInfo(String userId, int currentPage, int i) {
        Map<String,Object> map=new HashMap<>();
        map.put("userid",userId);
        map.put("page",currentPage+"");
        map.put("size",i+"");
        return HttpUtils.initRetrofit().getAdrressPageInfo(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> deleteAddress(String userId, String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("userid",userId);
        map.put("id",id);
        return HttpUtils.initRetrofit().deleteAddress(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> submitAddressInformation(String id, String userId, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("userid",userId);
        map.put("name",name);
        map.put("phone",phone);
        map.put("address",address);
        map.put("addressService",addressService);
        map.put("lon",lon);
        map.put("lat",lat);
        map.put("defaultAddress",defaultAddress);
        return HttpUtils.initRetrofit().submitAddressInformation(map).compose(new ThreadTransformer());
    }

}
