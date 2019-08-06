package com.wlan.familymembers.model.goodsorder;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/11/5.
 */

public class ServicePaymentDetailsModel implements Contract.ServicePaymentDetailsContract.Model{
    @Override
    public Observable<ResponseBody> ServicePaymentDetails(String userId, String serviceId) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("serviceId",serviceId);
        return HttpUtils.initRetrofit().ServicePaymentDetails(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> submitServiceOrder(String userId, String addressId, String serviceId, String discountId, String appointmentTime, String remark) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("addressId",addressId);
        map.put("serviceId",serviceId);
        map.put("discountId",discountId);
        map.put("appointmentTime",appointmentTime);
        map.put("remark",remark);
        return HttpUtils.initRetrofit().submitServiceOrder(map).compose(new ThreadTransformer());
    }
}
