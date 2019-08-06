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
 * Created by Administrator on 2018/9/14.
 */

public class HomePageModel implements Contract.HomePageContract.Model{
    @Override
    public Observable<ResponseBody> getData(int pageSize) {
        Map<String,Object> map=new HashMap<>();
        map.put("pageSize",pageSize);
        return HttpUtils.initRetrofit().getData(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> getHostPageInfo(int page, int pageSize) {
        Map<String,Object> map=new HashMap<>();
        map.put("page",page);
        map.put("pageSize",pageSize);
        return HttpUtils.initRetrofit().getPageInfo(map).compose(new ThreadTransformer());
    }
}
