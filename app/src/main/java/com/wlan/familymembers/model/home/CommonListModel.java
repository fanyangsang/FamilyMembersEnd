package com.wlan.familymembers.model.home;

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

public class CommonListModel implements Contract.CommonListContract.Model{
    @Override
    public Observable<ResponseBody> getserviceClassificationTwo(String id) {
        Map<String,String> map=new HashMap<>();
        map.put("parentId",id);
        return HttpUtils.initRetrofit().getserviceClassification(map).compose(new ThreadTransformer());
    }
}
