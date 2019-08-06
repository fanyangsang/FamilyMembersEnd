package com.rwq.framworkapp.net;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/5/19.
 */

public class ThreadTransformer implements Observable.Transformer<ResponseBody, ResponseBody> {
    @Override
    public Observable<ResponseBody> call(Observable<ResponseBody> observableObservable) {
        return observableObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

}
