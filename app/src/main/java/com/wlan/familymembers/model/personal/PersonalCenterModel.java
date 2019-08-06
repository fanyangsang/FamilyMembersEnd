package com.wlan.familymembers.model.personal;

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

public class PersonalCenterModel implements Contract.PersonalCenterContract.Model{
    @Override
    public Observable<ResponseBody> BaseInformation(String userId) {
        Map<String,String> map=new HashMap<>();
        map.put("userid",userId);
        return HttpUtils.initRetrofit().BaseInformation(map).compose(new ThreadTransformer());
    }
}
