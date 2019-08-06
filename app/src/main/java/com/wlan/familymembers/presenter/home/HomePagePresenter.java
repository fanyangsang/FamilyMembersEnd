package com.wlan.familymembers.presenter.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.LoopBean;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.OneNBean;
import com.wlan.familymembers.bean.ServiceClassificationBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/14.
 */

public class HomePagePresenter extends Contract.HomePageContract.Presenter{
    @Override
    public void getData(int pageSize) {
        addSubscribe(model.getData(pageSize).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        boolean isLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isLastPage");
                        List<LoopBean> loopBeans;
                        loopBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("banners").toString(),new TypeToken<List<LoopBean>>(){
                        }.getType());
                        List<ServiceClassificationBean> serviceClassificationBeans;
                        serviceClassificationBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("menu").toString(),new TypeToken<List<ServiceClassificationBean>>(){
                        }.getType());
                        List<OneNBean> oneNBeans;
                        oneNBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("hotPlate").toString(),new TypeToken<List<OneNBean>>(){
                        }.getType());
                        List<MallBottomBean> mallBottomBeans;
                        mallBottomBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("goodGoods").toString(),new TypeToken<List<MallBottomBean>>(){
                        }.getType());
                        view.getDataSuccess(loopBeans,serviceClassificationBeans,oneNBeans,mallBottomBeans,isLastPage);
                     }else {
                        String msg=jsonObject.getString("msg");
                        view.showErrorWithStatus(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }

            }

        }));
    }

    @Override
    public void getHostPageInfo(int page, int pageSize) {
        addSubscribe(model.getHostPageInfo(page,pageSize).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        List<MallBottomBean> mallBottomBeans;
                        Gson gson=new Gson();
                        mallBottomBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("goodGoods").toString(),new TypeToken<List<MallBottomBean>>(){
                        }.getType());
                        view.getHostPageInfoSuccess(mallBottomBeans);
                    }else {
                        String msg=jsonObject.getString("msg");
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
