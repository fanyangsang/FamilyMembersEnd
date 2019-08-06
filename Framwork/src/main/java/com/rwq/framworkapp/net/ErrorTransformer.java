package com.rwq.framworkapp.net;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 *  作者：gaoyin
 *  电话：18810474975
 *  邮箱：18810474975@163.com
 *  版本号：1.0
 *  类描述：
 *  备注消息：
 *  修改时间：2016/11/25 下午8:11
 *  Rxjava
 **/

public class ErrorTransformer<T> implements Observable.Transformer<ResponseBody,JSONObject> {

    public static <T> ErrorTransformer<T> create() {
        return new ErrorTransformer<>();
    }

    private static ErrorTransformer instance = null;

    private ErrorTransformer(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T>ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer();
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<JSONObject> call(Observable<ResponseBody> stringObservable) {
        return stringObservable.map(new Func1<ResponseBody, JSONObject>() {
            @Override
            public JSONObject call(ResponseBody s) {
                try {
                    String info = s.string();
                    return new JSONObject(info);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
