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
 * Created by Administrator on 2018/10/15.
 */

public class SearchListModel implements Contract.SearchListContract.Model{

    @Override
    public Observable<ResponseBody> getSearchList(String key, int page, int pageSize, String type) {
        Map<String,Object> map=new HashMap<>();
        map.put("key",key);
        map.put("page",page);
        map.put("pageSize",pageSize);
        map.put("type",type);
        return HttpUtils.initRetrofit().getSearchList(map).compose(new ThreadTransformer());
    }
}
