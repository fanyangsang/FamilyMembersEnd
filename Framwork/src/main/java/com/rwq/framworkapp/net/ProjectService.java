package com.rwq.framworkapp.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/4/24.
 */

public interface ProjectService {
    //登录>>注册
    @POST("admin/hm/api/userhm/authen/register")
    @FormUrlEncoded
    Observable<ResponseBody> register(@FieldMap Map<String, String> map);

    //系统内部错误
    @POST("admin/hm/api/userhm/authen/getcode")
    @FormUrlEncoded
    Observable<ResponseBody> getCode(@FieldMap Map<String, String> map);

    @POST("admin/hm/api/userhm/authen/login")
    @FormUrlEncoded
    Observable<ResponseBody> login(@FieldMap Map<String, String> map);

    @GET("admin/hm/api/userhm/protocol/info")
    Observable<ResponseBody> serviceAgreement();

    @POST("admin/hm/api/userhm/userhm/changepwd")
    @FormUrlEncoded
    Observable<ResponseBody> changePassword(@FieldMap Map<String, String> map);//FieldMap 以map的形式提交多个Field

    //个人中心>>关于我们
    @GET("admin/hm/api/userhm/aboutus/info")
    Observable<ResponseBody> getAboutUsInformation();

    //个人中心>>联系客服
    @GET("admin/hm/api/userhm/customerservice/info")
    Observable<ResponseBody> ContactCustomer(@QueryMap Map<String, String> map);

    @GET("admin/hm/api/userhm/userhm/info")
    Observable<ResponseBody> BaseInformation(@QueryMap Map<String, String> map);

    //带文件提交 有错误
    @POST("admin/hm/api/userhm/userhm/changepic")
    @Multipart
    Observable<ResponseBody> uploadPicture(@Part MultipartBody.Part part, @Part MultipartBody.Part userId);

    //个人中心>>基本信息>>修改手机号
    @POST("admin/hm/api/userhm/userhm/changephone")
    @FormUrlEncoded
    Observable<ResponseBody> changePhone(@FieldMap Map<String, String> map);

    //系统内部错误
    @POST("admin/hm/api/userhm/userhm/info/update")
    @FormUrlEncoded
    Observable<ResponseBody> keep(@FieldMap Map<String, String> map);

    //个人中心>>我的位置>>我的地址列表
    @GET("admin/hm/api/userhm/useraddress/list")
    Observable<ResponseBody> getAdrressPageInfo(@QueryMap Map<String, Object> map);

    //个人中心>>我的位置>>我的地址>>删除地址
    @POST("admin/hm/api/userhm/useraddress/delete")
    @FormUrlEncoded
    Observable<ResponseBody> deleteAddress(@FieldMap Map<String, Object> map);

    //个人中心>>帮助中心
    @GET("admin/hm/api/userhm/helpcenter/detail")
    Observable<ResponseBody> HelpCenterDetails(@QueryMap Map<String, String> map);

    @POST("admin/hm/api/userhm/authen/foundpwd")
    @FormUrlEncoded
    Observable<ResponseBody> findPassword(@FieldMap Map<String, String> map);

    //个人中心>>帮助中心>>帮助中心列表
    @GET("admin/hm/api/userhm/helpcenter/titlelist")
    Observable<ResponseBody> getHelpCenterData();

    //个人中心>>我的位置>>我的地址>>保存地址
    @POST("admin/hm/api/userhm/useraddress/save")
    @FormUrlEncoded
    Observable<ResponseBody> submitAddressInformation(@FieldMap Map<String, String> map);

    //首页>>家电清洗、家具维修、家具安装、收取快递列表
    @POST("admin/hm/api/public/servicehmCategory/list")
    @FormUrlEncoded
    Observable<ResponseBody> getserviceClassification(@FieldMap Map<String, String> map);

    //首页>>家电清洗、家具维修、家具安装、收取快递列表>>二级页面分类内容
    @POST("admin/hm/api/public/servicehmCategory/getContentByTwoId")
    @FormUrlEncoded
    Observable<ResponseBody> getHostPageInfo(@FieldMap Map<String, Object> map);

    //首页>>家电清洗、家具维修、家具安装、收取快递列表>>二级页面分类内容>>服务详情页
    @POST("admin/hm/api/public/servicehm/serviceDetail")
    @FormUrlEncoded
    Observable<ResponseBody> getServiceDetails(@FieldMap Map<String, String> map);

    //首页
    @POST("admin/hm/api/public/home/home")
    @FormUrlEncoded
    Observable<ResponseBody> getData(@FieldMap Map<String, Object> map);

    //商城 有错误
    @POST("admin/hm/api/shop/shop_home")
    @FormUrlEncoded
    Observable<ResponseBody> getMallHomePage(@FieldMap Map<String, Object> map);

    //商城详情页和支付订单时候的接口
    @POST("admin/hm/api/public/goods/goodsDetail")
    @FormUrlEncoded
    Observable<ResponseBody> getMallDetails(@FieldMap Map<String, String> map);

    @POST("admin/hm/api/public/home/goodGoodsList")
    @FormUrlEncoded
    Observable<ResponseBody> getPageInfo(@FieldMap Map<String, Object> map);

    @POST("admin/hm/api/public/home/hot_plate")
    @FormUrlEncoded
    Observable<ResponseBody> getSpecialPageInfo(@FieldMap Map<String, Object> map);

    @POST("admin/hm/api/public/search/searchAll")
    @FormUrlEncoded
    Observable<ResponseBody> getSearchList(@FieldMap Map<String, Object> map);

    @POST("admin/hm/api/public/goodsorder/downGoodsOrder")
    @FormUrlEncoded
    Observable<ResponseBody> getPaymentDetails(@FieldMap Map<String, Object> map);

    @POST("admin/hm/api/public/serviceOder/serviceDownOrder")
    @FormUrlEncoded
    Observable<ResponseBody> ServicePaymentDetails(@FieldMap Map<String, String> map);

    @POST("admin/hm/api/public/goodsOrder/orderList")
    @FormUrlEncoded
    Observable<ResponseBody> GoodsOrder(@FieldMap Map<String, Object> map);

    @POST("admin/hm/api/public/baserOrder/cancelOrder")
    @FormUrlEncoded
    Observable<ResponseBody> cancelOrder(@FieldMap Map<String, String> map);

    @POST("admin/hm/api/public/baserOrder/remindSendOrder")
    @FormUrlEncoded
    Observable<ResponseBody> warmGoods(@FieldMap Map<String, String> map);

    @POST("admin/hm/api/public/serviceOrder/orderList")
    @FormUrlEncoded
    Observable<ResponseBody> ServiceOrderList(@FieldMap Map<String, Object> map);

    @POST("public/pay/pay")
    @FormUrlEncoded
    Observable<ResponseBody> pay(@FieldMap Map<String, String> map);

    //订单开始支付
    @POST("hm/order/payStart")
    @FormUrlEncoded
    Observable<ResponseBody> alipay(@FieldMap Map<String, String> map);

    //商品订单确认收货
    @POST("hm/order/payOver")
    @FormUrlEncoded
    Observable<ResponseBody> confirmGoods(@FieldMap Map<String, String> map);

    //提交订单
    @POST("admin/hm/api/public/goodsorder/submitOrder")
    @FormUrlEncoded
    Observable<ResponseBody> submitOrder(@FieldMap Map<String, Object> map);

    //提交服务订单
    @POST("admin/hm/api/public/serviceOder/submitServiceOrder")
    @FormUrlEncoded
    Observable<ResponseBody> submitServiceOrder(@FieldMap Map<String, Object> map);

    //支付订单确认收货
    @POST("hm/order/payOver")
    @FormUrlEncoded
    Observable<ResponseBody> confirm(@FieldMap Map<String, String> map);

}
