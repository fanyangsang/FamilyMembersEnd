package com.wlan.familymembers.presenter.home;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.TopBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/9/17.
 */

public class HomeComonPresenter extends Contract.HomeCommonContract.Presenter{
    @Override
    public void getSpecialPageInfo(String type, int page, int pageSize) {
        addSubscribe(model.getSpecialPageInfo(type,page,pageSize).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("success")){
                        TopBean topBean;
                        List<MallBottomBean> mallBottomBeans;
                        Gson gson=new Gson();
                        boolean isLastPage =true;
                        //boolean isLastPage=jsonObject.getJSONObject("body").getJSONObject("data").getBoolean("isLastPage");
                        topBean=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONObject("top").toString(),TopBean.class);
                        mallBottomBeans=gson.fromJson(jsonObject.getJSONObject("body").getJSONObject("data").getJSONArray("content").toString(),new TypeToken<List<MallBottomBean>>(){
                        }.getType());
                        view.getSpecialPageInfoSuccess(topBean,mallBottomBeans,isLastPage);
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
