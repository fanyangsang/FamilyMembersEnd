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

public class ServiceListContentModel implements Contract.ServiceListContentContract.Model{
    //请求首页服务二级分类列表下的内容
    @Override
    public Observable<ResponseBody> getHostPageInfo(String catId,int currentPage, int size) {
        String page = String.valueOf(currentPage);
        String pageSize = String.valueOf(size);
        Map<String,Object> map=new HashMap<>();
        map.put("classifyId",catId);
        map.put("page",currentPage);
        map.put("pageSize",size);
        return HttpUtils.initRetrofit().getHostPageInfo(map).compose(new ThreadTransformer());
    }
}
