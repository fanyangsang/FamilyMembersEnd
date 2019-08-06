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
 * Created by Administrator on 2018/9/29.
 */

public class HelpCenterDetailsModel implements Contract.HelpCenterDetailsContract.Model{
    @Override
    public Observable<ResponseBody> getHelpCenterDetails(String id) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        return HttpUtils.initRetrofit().HelpCenterDetails(map).compose(new ThreadTransformer());
    }
}
