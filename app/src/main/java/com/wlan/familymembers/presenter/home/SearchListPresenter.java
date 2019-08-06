package com.wlan.familymembers.presenter.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.GoodsBean;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/10/15.
 */

public class SearchListPresenter extends Contract.SearchListContract.Presenter{
    @Override
    public void getSearchList(final String key, int page, int pageSize, final String type) {
        addSubscribe(model.getSearchList(key,page,pageSize,type).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        List<GoodsBean> goodsBeans=new ArrayList<>();
                        List<ServiceListBean> serviceListBeans=new ArrayList<>();
                        boolean isGoodsLastPage=false;
                        boolean isServiceLastPage=false;
                        if (type.equals("1")){
                            goodsBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("service").toString(),new TypeToken<List<ServiceListBean>>(){
                            }.getType());
                            goodsBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("goods").toString(),new TypeToken<List<GoodsBean>>(){
                            }.getType());
                            isGoodsLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isGoodsLastPage");
                            isServiceLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isServiceLastPage");
                        }else if (type.equals("2")){
                            isGoodsLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isGoodsLastPage");
                            isServiceLastPage=false;
                            goodsBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("goods").toString(),new TypeToken<List<GoodsBean>>(){
                            }.getType());
                            serviceListBeans=null;
                        }else if (type.equals("3")){
                            isServiceLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isServiceLastPage");
                            isGoodsLastPage=false;
                            serviceListBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("service").toString(),new TypeToken<List<ServiceListBean>>(){
                            }.getType());
                            goodsBeans=null;
                        }
                        view.getSearchListSuccess(isGoodsLastPage,isServiceLastPage,goodsBeans,serviceListBeans);

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
