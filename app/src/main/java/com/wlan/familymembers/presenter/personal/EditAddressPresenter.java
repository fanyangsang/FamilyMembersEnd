package com.wlan.familymembers.presenter.personal;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.contract.Contract;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类作用：
 * Created by Administrator on 2018/9/20.
 */

//添加 修改地址
public class EditAddressPresenter extends Contract.EditAddressContract.Presenter{
    @Override
    public void submitAddressInformation(String id, String userid, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress) {
        addSubscribe(model.submitAddressInformation(id,userid,name,phone,address,addressService,lon,lat,defaultAddress).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {

                    System.out.println("--------------------------------------------------");
                    System.out.println(jsonObject.toString());
                    System.out.println("--------------");
                    Gson gson=new Gson();
                    System.out.println(gson.toJson(jsonObject));
                    System.out.println("--------------------------------------------------");
                    if (jsonObject.getBoolean("success")){
                        JSONObject data=jsonObject.getJSONObject("body").getJSONObject("newAddress");
                        String id  = data.getString("id");
                        String name  = data.getString("name");
                        String phone  = data.getString("phone");
                        String service  = data.getString("addressService");
                        //新添加的地址优先级最高  但是这个方法会重新请求一遍所有地址  所以 在这个方法之后 将先添加的地址存到本地
                        view.submitAddressInformationSuccess();
                        //将新提交的地址存到本地
                        SPUtils.getInstance().put("addressId",id);
                        SPUtils.getInstance().put("addressPhone",phone);
                        SPUtils.getInstance().put("addressName",name);
                        SPUtils.getInstance().put("addressService",service);
                        //存入新地址后  在订单页面显示
                        view.newaddress();
                    }else {
                        view.showErrorWithStatus("无数据");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("保存地址解析错误",Log.getStackTraceString(e));
                    view.showErrorWithStatus("解析错误");
                }
            }
        }));
    }
}
