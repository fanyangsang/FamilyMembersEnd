package com.wlan.familymembers.model.goodsorder;

import android.util.Log;

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

public class GoodsOrderModel implements Contract.GoodsOrderContract.Model {
    @Override
    public Observable<ResponseBody> GoodsOrder(String userId, int orderStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", orderStatus);
        return HttpUtils.initRetrofit().GoodsOrder(map).compose(new ThreadTransformer());
    }
    @Override
    public Observable<ResponseBody> cancelOrder(String userId, String oderNum) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderNum", oderNum);
        return HttpUtils.initRetrofit().cancelOrder(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> warmGoods(String userId, String oderNum) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderNum", oderNum);
        return HttpUtils.initRetrofit().warmGoods(map).compose(new ThreadTransformer());
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
    public Observable<ResponseBody> wxpay(String userid, String orderid, String couponid, int payWay, int payType) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("orderid", orderid);
        map.put("payWay", payWay + "");
        map.put("payType", payType + "");
        //微信和支付宝支付向后台请求的接口是一样的
        return HttpUtils.initRetrofit().alipay(map).compose(new ThreadTransformer());
    }


    @Override
    public Observable<ResponseBody> confirmGoods(String orderid, int orderType, int payType) {
        Map<String, String> map = new HashMap<>();
        map.put("orderid", orderid);
        map.put("orderType", orderType + "");
        map.put("payType", payType + "");
        return HttpUtils.initRetrofit().confirmGoods(map).compose(new ThreadTransformer());
    }
}
