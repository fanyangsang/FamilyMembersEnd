package com.wlan.familymembers.contract;


import com.rwq.framworkapp.base.BaseModel;
import com.rwq.framworkapp.base.BasePresenter;
import com.rwq.framworkapp.base.BaseView;
import com.wlan.familymembers.bean.AboutUsBean;
import com.wlan.familymembers.bean.AddressBean;
import com.wlan.familymembers.bean.AttrVal;
import com.wlan.familymembers.bean.BaseInformationBean;
import com.wlan.familymembers.bean.GoodsBean;
import com.wlan.familymembers.bean.GoodsDetailBean;
import com.wlan.familymembers.bean.GoodsInfoBean;
import com.wlan.familymembers.bean.GoodsOrderBean;
import com.wlan.familymembers.bean.HelpCenterBean;
import com.wlan.familymembers.bean.HelpCenterDetailsBean;
import com.wlan.familymembers.bean.LoopBean;
import com.wlan.familymembers.bean.MallBottomBean;
import com.wlan.familymembers.bean.MallHomepageBean;
import com.wlan.familymembers.bean.OneNBean;
import com.wlan.familymembers.bean.SelectAttrVal;
import com.wlan.familymembers.bean.ServiceClassificationBean;
import com.wlan.familymembers.bean.ServiceDetailsBean;
import com.wlan.familymembers.bean.ServiceInfoBean;
import com.wlan.familymembers.bean.ServiceListBean;
import com.wlan.familymembers.bean.ServiceOrderBean;
import com.wlan.familymembers.bean.TopBean;
import com.wlan.familymembers.bean.WXPayBean;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/13.
 */

public interface Contract {


    public interface MainContract {
        interface View extends BaseView {
            void loginSuccess();

            void loginFaile();

        }

        interface Model extends BaseModel {
            Observable<ResponseBody> login(String phone, String password);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void login(String phone, String password);
        }
    }

    public interface LoginContract {
        interface View extends BaseView {
            void loginSuccess();

            void loginFail();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> login(String phone, String password);

        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void login(String phone, String password);

        }
    }

    public interface RegisterContract {
        interface View extends BaseView {
            void registerSuccess();

            void getCodeSuccess();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> register(String phone, String newPassword, String code);

            Observable<ResponseBody> getCode(String type, String phoneTwo);
        }

        abstract class Presenter extends BasePresenter<View, Model> {

            public abstract void register(String phone, String newPassword, String code);

            public abstract void getCode(String type, String phoneTwo);
        }
    }

    public interface ServiceAgreementContract {
        interface View extends BaseView {
            void getServiceAgreementSuccess(String content);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> serviceAgreement();
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void serviceAgreement();
        }
    }

    public interface FindPasswordContract {
        interface View extends BaseView {
            void getCodeSuccess(String code);

            void findPasswordSuccess();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getCode(String type, String phone);

            Observable<ResponseBody> findPassword(String codeTwo, String newPassword, String phoneTwo);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getCode(String type, String phone);

            public abstract void findPassword(String codeTwo, String newPassword, String phoneTwo);
        }
    }

    public interface HomePageContract {
        interface View extends BaseView {

            void getDataSuccess(List<LoopBean> loopBeans, List<ServiceClassificationBean> serviceClassificationBeans, List<OneNBean> oneNBeans, List<MallBottomBean> mallBottomBeans, boolean isLastPage);

            void getHostPageInfoSuccess(List<MallBottomBean> mallBottomBeans);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getData(int pageSize);

            Observable<ResponseBody> getHostPageInfo(int page, int pageSize);
        }

        abstract class Presenter extends BasePresenter<View, Model> {

            public abstract void getData(int pageSize);

            public abstract void getHostPageInfo(int page, int pageSize);
        }
    }

    public interface CommonListContract {
        interface View extends BaseView {
            void getserviceClassificationTwoSuccess(List<ServiceClassificationBean> serviceClassificationBeans);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getserviceClassificationTwo(String id);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getserviceClassificationTwo(String id);
        }
    }

    public interface ServiceListContentContract {
        interface View extends BaseView {
            void getHostPageInfoSuccess(List<ServiceListBean> serviceListBeans, boolean isLastPage);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getHostPageInfo(String catId, int currentPage, int size);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getHostPageInfo(String catId, int currentPage, int size);

        }
    }

    public interface MallDetailsContract {
        interface View extends BaseView {
            void getMallDetailsSuccess(GoodsDetailBean goodsDetailBean, Map<String, List<AttrVal>> attrMap, Map<String, SelectAttrVal> selectAttrValMap);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getMallDetails(String goodsId);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getMallDetails(String goodsId);
        }
    }

    public interface MallContract {
        interface View extends BaseView {
            void getMallHomePageSuccess(int page, List<MallHomepageBean> mallBottomBeans1, List<MallHomepageBean> mallBottomBeans2, List<LoopBean> loopBeans, boolean isLastPage);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getMallHomePage(int page, int pageSize);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getMallHomePage(int page, int pageSize);
        }
    }

    public interface ServiceOrderContract {
        interface View extends BaseView {
            void ServiceOrderListSuccess(List<ServiceOrderBean> serviceOrderBeans);

            void cancelOrderSuccess();

            void alipay(String orderid);

            //void paySuccess();
            void wxpay(WXPayBean wxPayBean);

            void confirmSuccess();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> ServiceOrderList(String userId, int orderStatus);

            Observable<ResponseBody> cancelOrder(String userId, String orderNum);

            //Observable<ResponseBody> pay(String userId, String orderNum, String type);
            Observable<ResponseBody> wxpay(String userid, String orderid, String couponid, int payWay, int payType);

            Observable<ResponseBody> alipay(String userid, String orderid, String couponid, int payWay, int payType);

            Observable<ResponseBody> confirm(String orderid, int orderType, int payType);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void ServiceOrderList(String userId, int orderStatus);

            public abstract void cancelOrder(String userId, String orderNum);

            //public abstract void pay(String userId, String orderNum, String type);

            public abstract void alipay(String userid, String orderid, String couponid, int payWay, int payType);

            public abstract void wxpay(String userid, String orderid, String couponid, int payWay, int payType);

            public abstract void confirm(String orderid, int orderType, int payType);
        }
    }

    public interface GoodsOrderContract {
        interface View extends BaseView {
            void GoodsOrderSuccess(List<GoodsOrderBean> goodsOrderBeans);

            void cancelOrderSuccess();

            void warmGoodsSuccess();

            void alipay(String orderid);

            void wxpay(WXPayBean wxPayBean);

            void confirmSuccess();

            void delayedGoods();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> GoodsOrder(String userId, int orderStatus);

            Observable<ResponseBody> cancelOrder(String userId, String oderNum);

            Observable<ResponseBody> warmGoods(String userId, String oderNum);

            Observable<ResponseBody> alipay(String userid, String orderid, String couponid, int payWay, int payType);

            Observable<ResponseBody> wxpay(String userid, String orderid, String couponid, int payWay, int payType);

            Observable<ResponseBody> confirmGoods(String orderid, int orderType, int payType);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void GoodsOrder(String userId, int orderStatus);

            public abstract void cancelOrder(String userId, String oderNum);

            public abstract void warmGoods(String userId, String oderNum);

            public abstract void alipay(String userid, String orderid, String couponid, int payWay, int payType);

            public abstract void wxpay(String userid, String orderid, String couponid, int payWay, int payType);

            public abstract void confirmGoods(String orderid, int orderType, int payType);
        }
    }

    public interface PersonalCenterContract {
        interface View extends BaseView {
            void BaseInformation(BaseInformationBean baseInformationBean);

            void setLevel(int a);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> BaseInformation(String userId);

        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getBaseInformation(String userId);

        }
    }

    public interface HomeCommonContract {
        interface View extends BaseView {
            void getSpecialPageInfoSuccess(TopBean topBean, List<MallBottomBean> mallBottomBeans, boolean isLastPage);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getSpecialPageInfo(String type, int page, int pageSize);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getSpecialPageInfo(String type, int page, int pageSize);
        }
    }

    public interface PaymentDetailsContract {
        interface View extends BaseView {
            void getPaymentDetailsSuccess(AddressBean addressBean, GoodsInfoBean goodsInfoBean, boolean isDefaultAddress);

            void submitOrder(String content);

            void intentPay(String orderid);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getPaymentDetails(String userId, String goodsId, String attrPath, int num);

            Observable<ResponseBody> submitOrder(String userId, String goodsid, String attrPath, String addressId, String remark, String discountId, String num);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getPaymentDetails(String userId, String goodsId, String attrPath, int num);

            public abstract void submitOrder(String userId, String goodsid, String attrPath, String addressId, String remark, String discountId, String num);
        }
    }

    public interface GoodsPayContract {
        interface View extends BaseView {
            void alipay(String orderid);

            void paySuccess();
        }

        interface Model extends BaseModel {
            rx.Observable<ResponseBody> alipay(String userid, String orderid, int payWay, int payType);
        }


        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void alipay(String userid, String orderid, int payWay, int payType);
        }
    }

    public interface MemberCenterContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface MyAddressContract {
        interface View extends BaseView {
            void getAdrressPageInfoSuccess(List<AddressBean> addressBeans);

            void deleteAddressSuccess();

            void submitAddressInformation();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getAdrressPageInfo(String userId, int currentPage, int i);

            Observable<ResponseBody> deleteAddress(String userId, String id);

            Observable<ResponseBody> submitAddressInformation(String id, String userId, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getAdrressPageInfo(String userId, int currentPage, int i);

            public abstract void deleteAddress(String userId, String id);

            public abstract void submitAddressInformation(String id, String userId, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress);
        }
    }

    public interface EditAddressContract {
        interface View extends BaseView {
            void submitAddressInformationSuccess();

            void newaddress();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> submitAddressInformation(String id, String userid, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void submitAddressInformation(String id, String userid, String name, String phone, String address, String addressService, String lon, String lat, String defaultAddress);
        }
    }

    public interface BaseInformationContract {
        interface View extends BaseView {
            void BaseInformation(BaseInformationBean baseInformationBean);

            void uploadPicture();

            void keepSuccess();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> BaseInformation(String userId);

            Observable<ResponseBody> uploadPicture(File imgFile, String userId);

            Observable<ResponseBody> keep(String userId, String i, String name);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getBaseInformation(String userId);

            public abstract void uploadPicture(File imgFile, String userId);

            public abstract void keep(String userId, String i, String name);
        }
    }

    public interface ChangePasswordContract {
        interface View extends BaseView {
            void changePasswordSuccess();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> changePassword(String userId, String oldPassword, String newPassword);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void changePassword(String userId, String oldPassword, String newPassword);
        }
    }

    public interface ContactCustomerContract {
        interface View extends BaseView {
            void ContactCustomerSuccess(String content);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> ContactCustomer(String userId);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void ContactCustomer(String userId);
        }
    }

    public interface AboutUsContract {
        interface View extends BaseView {
            void getAboutUsInformationSuccess(AboutUsBean aboutUsBean);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getAboutUsInformation();
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getAboutUsInformation();
        }
    }

    public interface HelpCenterContract {
        interface View extends BaseView {
            void getHelpCenterData(List<HelpCenterBean> helpCenterBeans);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getHelpCenterData();
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getHelpCenterData();
        }
    }

    public interface MyCouponContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface MyPointContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface CouponNotesContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface ChangePhoneContract {
        interface View extends BaseView {
            void getCodeSuccess(String code);

            void changePhoneSuccess();
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getCode(String type, String phone);

            Observable<ResponseBody> changePhone(String userId, String code, String tel2);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getCode(String type, String phone);

            public abstract void changePhone(String userId, String code, String tel2);
        }
    }

    public interface HelpCenterDetailsContract {
        interface View extends BaseView {
            void getHelpCenterDetailsSuccess(HelpCenterDetailsBean helpCenterDetailsBean);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getHelpCenterDetails(String id);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getHelpCenterDetails(String id);
        }
    }

    public interface MapContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface SearchContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {

        }
    }

    public interface SearchListContract {
        interface View extends BaseView {
            void getSearchListSuccess(boolean isGoodsLastPage, boolean isServiceLastPage, List<GoodsBean> goodsBeans, List<ServiceListBean> serviceListBeans);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getSearchList(String key, int page, int pageSize, String type);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getSearchList(String key, int page, int pageSize, String type);
        }
    }

    public interface ServiceDetailContract {
        interface View extends BaseView {
            void getServiceDetailsSuccess(ServiceDetailsBean serviceDetailsBean);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> getServiceDetails(String serviceId);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void getServiceDetails(String serviceId);
        }
    }

    public interface WebViewContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface CommonContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface SearchServiceContract {
        interface View extends BaseView {
        }

        interface Model extends BaseModel {
        }

        abstract class Presenter extends BasePresenter<View, Model> {
        }
    }

    public interface ServicePaymentDetailsContract {
        interface View extends BaseView {
            void ServicePaymentDetailsSuccess(ServiceInfoBean serviceInfoBean, AddressBean addressBean, boolean isDefaultAddress);

            void submitServiceOrder(String hint);
        }

        interface Model extends BaseModel {
            Observable<ResponseBody> ServicePaymentDetails(String userId, String serviceId);

            Observable<ResponseBody> submitServiceOrder(String userId, String addressId, String serviceId, String discountId, String appointmentTime, String remark);
        }

        abstract class Presenter extends BasePresenter<View, Model> {
            public abstract void ServicePaymentDetails(String userId, String serviceId);

            public abstract void submitServiceOrder(String userId, String addressId, String serviceId, String discountId, String appointmentTime, String remark);
        }
    }
}
