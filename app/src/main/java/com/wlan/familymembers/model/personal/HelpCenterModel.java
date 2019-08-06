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
 * Created by Administrator on 2018/9/25.
 */

public class HelpCenterModel implements Contract.HelpCenterContract.Model{
    @Override
    public Observable<ResponseBody> getHelpCenterData() {
        return HttpUtils.initRetrofit().getHelpCenterData().compose(new ThreadTransformer());
    }
}
