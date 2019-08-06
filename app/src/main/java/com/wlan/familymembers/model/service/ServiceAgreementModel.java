package com.wlan.familymembers.model.service;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class ServiceAgreementModel implements Contract.ServiceAgreementContract.Model{
    @Override
    public Observable<ResponseBody> serviceAgreement() {
        return HttpUtils.initRetrofit().serviceAgreement().compose(new ThreadTransformer());
    }
}
