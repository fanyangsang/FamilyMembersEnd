package com.wlan.familymembers.model.mall;

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

public class MallDetailsModel implements Contract.MallDetailsContract.Model{
    @Override
    public Observable<ResponseBody> getMallDetails(String goodsId) {
        Map<String,String> map=new HashMap<>();
        map.put("goodsId",goodsId);
        return HttpUtils.initRetrofit().getMallDetails(map).compose(new ThreadTransformer());
    }
}
