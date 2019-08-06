package com.wlan.familymembers.presenter.mall;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.LoopBean;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.MallHomepageBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class MallPresenter extends Contract.MallContract.Presenter{
    @Override
    public void getMallHomePage(final int page,int pageSize) {
        addSubscribe(model.getMallHomePage(page,pageSize).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        List<MallHomepageBean> mallBottomBeans1=null;
                        List<MallHomepageBean> mallBottomBeans2;
                        boolean isLastPage;
                        List<LoopBean> loopBeans=null;
                        if (page == 1) {
                            mallBottomBeans1 = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("goodGoods").toString(), new TypeToken<List<MallHomepageBean>>() {
                            }.getType());
                            mallBottomBeans2 = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("hotGoods").toString(), new TypeToken<List<MallHomepageBean>>() {
                            }.getType());
                            loopBeans = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("banners").toString(), new TypeToken<List<LoopBean>>() {
                            }.getType());
                            isLastPage=true;
                            //isLastPage = jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isLastPage");
                            //view.getMallHomePageSuccess(page, mallBottomBeans1, mallBottomBeans2, loopBeans, isLastPage);
                        } else {
                            mallBottomBeans2 = gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("hotGoods").toString(), new TypeToken<List<MallHomepageBean>>() {
                            }.getType());
                            isLastPage=true;
                            //isLastPage = jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isLastPage");
                        }
                        Log.d("getMallHomePage", "===========商城信息解析成功");

                        view.getMallHomePageSuccess(page, mallBottomBeans1, mallBottomBeans2, loopBeans, isLastPage);

                    }else {
                            view.showErrorWithStatus("无数据");
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("商城信息解析错误",Log.getStackTraceString(e));
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }
}