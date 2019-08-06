package com.wlan.familymembers.presenter.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class ServiceListContentPresenter extends Contract.ServiceListContentContract.Presenter{
    @Override
    public void getHostPageInfo(String catId,int currentPage, int size) {
        addSubscribe(model.getHostPageInfo(catId,currentPage,size).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        //判断是否是最后一页
                        boolean isLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isLastPage");
                        Gson gson=new Gson();
                        List<ServiceListBean> serviceListBeans;
                        serviceListBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("content").toString(),new TypeToken<List<ServiceListBean>>(){
                        }.getType());
                        view.getHostPageInfoSuccess(serviceListBeans,isLastPage);
                    }else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));

    }
}
