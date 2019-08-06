package com.wlan.familymembers.model.personal;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;
import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

public class EditAddressModel implements Contract.EditAddressContract.Model{
    @Override
    public Observable<ResponseBody> submitAddressInformation(String id, String userid, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("userid",userid);
        map.put("name",name);
        map.put("phone",phone);
        //服务地址
        map.put("address",addressService);
        //详细地址
        map.put("addressService",address);
        map.put("lon",lon);
        map.put("lat",lat);
        map.put("defaultAddress",defaultAddress);
        return HttpUtils.initRetrofit().submitAddressInformation(map).compose(new ThreadTransformer());
    }
}
