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
 * Created by Administrator on 2019/3/29.
 */

public class GoodsPayModel implements Contract.GoodsPayContract.Model {

    @Override
    public Observable<ResponseBody> alipay(String userid, String orderid,  int payWay, int payType) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("orderid", orderid);
        map.put("payWay", payWay + "");
        map.put("payType", payType + "");
        return HttpUtils.initRetrofit().alipay(map).compose(new ThreadTransformer());
    }
}
