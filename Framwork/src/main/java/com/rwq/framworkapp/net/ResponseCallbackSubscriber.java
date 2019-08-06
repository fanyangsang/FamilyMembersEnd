package com.rwq.framworkapp.net;

import com.orhanobut.logger.Logger;
import com.rwq.framworkapp.base.BaseView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/5/19.
 */

public abstract class ResponseCallbackSubscriber extends Subscriber<ResponseBody> {
    private BaseView view;

    protected ResponseCallbackSubscriber(BaseView view) {
        this.view = view;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Logger.w(e.getMessage());
        onError(" 访问服务器失败！");
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            if (responseBody != null) {
                String result = responseBody.string();
                Logger.i("responseBody----" + result);
                JSONObject jsonObject = new JSONObject(result);
                onSuccess(jsonObject);
            } else {
                onError("响应体为空！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            onError("访问数据异常！");
        } catch (JSONException e) {
            e.printStackTrace();
            onError("数据解析异常！");
        }
    }

    public abstract void onSuccess(JSONObject jsonObject);

    private void onError(String errorMsg) {
        view.showErrorWithStatus(errorMsg);
        view = null;
    }
}
