package com.rwq.framworkapp.net;


import org.json.JSONObject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  作者：gaoyin
 *  电话：18810474975
 *  邮箱：18810474975@163.com
 *  版本号：1.0
 *  类描述：  预处理异常信息
 *  备注消息：
 *  修改时间：2016/11/25 下午7:22
 **/
public class DefaultTransformer<T>  implements Observable.Transformer<ResponseBody,JSONObject>{
    @Override
    public Observable<JSONObject> call(Observable<ResponseBody> httpResponseObservable) {
        return httpResponseObservable.subscribeOn(Schedulers.io())
                                                .observeOn(Schedulers.newThread())
                                                .compose(ErrorTransformer.<JSONObject>getInstance())
                                                .observeOn(AndroidSchedulers.mainThread());
    }
}
