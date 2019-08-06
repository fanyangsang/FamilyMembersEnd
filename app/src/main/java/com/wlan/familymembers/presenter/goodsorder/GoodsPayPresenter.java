package com.wlan.familymembers.presenter.goodsorder;


import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2019/3/29.
 */

public class GoodsPayPresenter extends Contract.GoodsPayContract.Presenter {

    @Override
    public void alipay(String userid, String orderid, int payWay, int payType) {
        addSubscribe(model.alipay(userid, orderid, payWay, payType).subscribe(new ResponseCallbackSubscriber(view) {

            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        view.alipay(jsonObject.getJSONObject("body").getString("map"));
                    } else {
                        String msg = jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }

}