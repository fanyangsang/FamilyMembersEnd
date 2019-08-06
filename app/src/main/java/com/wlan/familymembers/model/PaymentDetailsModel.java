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
 * Created by Administrator on 2018/9/19.
 */

public class PaymentDetailsModel implements Contract.PaymentDetailsContract.Model{
    @Override
    public Observable<ResponseBody> getPaymentDetails(String userId, String goodsId, String attrPath, int num) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("goodsId",goodsId);
        map.put("attrPath",attrPath);
        map.put("num",num);
        return HttpUtils.initRetrofit().getPaymentDetails(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> submitOrder(String userId, String goodsid, String attrPath, String addressId, String remark, String discountId, String num) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("goodsid",goodsid);
        map.put("attrPath",attrPath);
        map.put("addressId",addressId);
        map.put("remark",remark);
        map.put("discountId",discountId);
        map.put("num",num);
        return HttpUtils.initRetrofit().submitOrder(map).compose(new ThreadTransformer());
    }
}
