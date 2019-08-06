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
 * Created by Administrator on 2018/9/15.
 */

public class ServiceOrderModel implements Contract.ServiceOrderContract.Model{
    @Override
    public Observable<ResponseBody> ServiceOrderList(String userId, int orderStatus) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("orderStatus",orderStatus);
        return HttpUtils.initRetrofit().ServiceOrderList(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> cancelOrder(String userId, String orderNum) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("orderNum",orderNum);
        return HttpUtils.initRetrofit().cancelOrder(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> wxpay(String userid, String orderid, String couponid, int payWay, int payType) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("orderid", orderid);
        //map.put("couponid",couponid);
        map.put("payWay", payWay + "");
        map.put("payType", payType + "");
        return HttpUtils.initRetrofit().alipay(map).compose(new ThreadTransformer());
    }


    @Override
    public Observable<ResponseBody> alipay(String userid, String orderid, String couponid, int payWay, int payType) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("orderid", orderid);
        //map.put("couponid",couponid);
        map.put("payWay", payWay + "");
        map.put("payType", payType + "");
        return HttpUtils.initRetrofit().alipay(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> confirm(String orderid, int orderType, int payType) {
        Map<String, String> map = new HashMap<>();
        map.put("orderid", orderid);
        map.put("orderType", orderType + "");
        map.put("payType", payType + "");
        return HttpUtils.initRetrofit().confirm(map).compose(new ThreadTransformer());
    }
}
