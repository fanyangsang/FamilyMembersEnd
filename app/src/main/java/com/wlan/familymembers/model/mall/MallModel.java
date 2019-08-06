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

public class MallModel implements Contract.MallContract.Model{
    @Override
    public Observable<ResponseBody> getMallHomePage(int page,int pageSize) {
        Map<String,Object> map=new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("page",page);
        return HttpUtils.initRetrofit().getMallHomePage(map).compose(new ThreadTransformer());
    }
}
