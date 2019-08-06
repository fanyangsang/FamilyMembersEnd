package com.wlan.familymembers.presenter.mall;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.rwq.framworkapp.net.ResponseCallbackSubscriber;
import com.wlan.familymembers.bean.AttrVal;
import com.wlan.familymembers.bean.GoodsDetailBean;
import com.wlan.familymembers.bean.SelectAttrVal;
import com.wlan.familymembers.bean.SizeBean;
import com.wlan.familymembers.contract.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class MallDetailsPresenter extends Contract.MallDetailsContract.Presenter{
    @Override
    public void getMallDetails(String goodsId) {
        addSubscribe(model.getMallDetails(goodsId).subscribe(new ResponseCallbackSubscriber(view) {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    Log.d("getMallDetails:",""+jsonObject);
                    if (jsonObject.getBoolean("success")){
                        Gson gson=new Gson();
                        GoodsDetailBean goodsDetailBean;
                        JSONObject data = jsonObject.getJSONObject("body").getJSONObject("data");
                        //添加商品数据 名称  轮播图 价格等
                        goodsDetailBean = gson.fromJson(data.getJSONObject("goods").toString(), GoodsDetailBean.class);

                        JSONObject attr = data.getJSONObject("attr");
                        //数组
                        JSONArray attrsList = attr.getJSONArray("attrsList");
                        //对象
                        JSONObject skuMap = attr.getJSONObject("skuMap");

                        //new一个哈希集合
                        Map<String, List<AttrVal>> attrMap = new HashMap<>();
                        //遍历attrsList
                        for (int i = 0; i < attrsList.length(); i++) {
                            //attrsVal=attrsList.get(0)
                            JSONObject attrsVal = attrsList.getJSONObject(i);
                            //
                            Iterator<String> iterator = attrsVal.keys();
                            //iterator.hasNext() 判断当前对象是否还有下一个对象 有返回true
                            while (iterator.hasNext()) {
                                //iterator.next();返回当前元素， 并指向下一个元素
                                String key = iterator.next();
                                //把键名为key的数组存入attrVals
                                List<AttrVal> attrVals = new Gson().fromJson(attrsVal.getJSONArray(key).toString(), new TypeToken<List<AttrVal>>() {
                                }.getType());
                                //把键名键值添加进attrMap
                                attrMap.put(key, attrVals);
                            }
                        }

                        //SelectAttrVal--存储skuMap对象的实体类
                        Map<String, SelectAttrVal> selectAttrValMap = new HashMap<>();
                        Iterator<String> stringIterator = skuMap.keys();
                        while (stringIterator.hasNext()) {
                            String key = stringIterator.next();
                            SelectAttrVal selectAttrVal = new Gson().fromJson(skuMap.getJSONObject(key).toString(), SelectAttrVal.class);
                            selectAttrValMap.put(key, selectAttrVal);
                        }
                        view.getMallDetailsSuccess(goodsDetailBean,attrMap,selectAttrValMap);
                        Log.i("imgUrl",jsonObject.toString());
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
