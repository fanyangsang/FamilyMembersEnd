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
 * Created by Administrator on 2018/9/17.
 */

public class HomeCommonModel implements Contract.HomeCommonContract.Model{
    @Override
    public Observable<ResponseBody> getSpecialPageInfo(String type, int page, int pageSize) {
        Map<String,Object> map=new HashMap<>();
        map.put("type",type);
        map.put("page",page);
        map.put("pageSize",pageSize);
        return HttpUtils.initRetrofit().getSpecialPageInfo(map).compose(new ThreadTransformer());
    }
}
