package com.wlan.familymembers.bean;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/11/5.
 */

public class GoodsOrderBean {

    /**
     * orderNum : 215411563918759229
     * orderStatus : 1
     * payType : null
     * price : null
     * priceFinal : null
     * orderContent : [{"pic":"/userfiles/1/files/goods/goods/2018/11/201809180246406909_x%20(1).jpg","goodsName":"创维(SKYWORTH)43X6 43英寸 全高清智能液晶平板液晶电视","sellPoint":"10核处理器","price":"15999.0","num":"1"}]
     * downTime : 2018-11-02
     * expressNum : null
     * addressId : 1540795705818
     * address : 河南省郑州市金水区广播电视台
     */

    private String orderNum;
    private String orderid;
    private int orderStatus;
    private Object payType;
    private Object price;
    private Object priceFinal;
    private String downTime;
    private Object expressNum;
    private String addressId;
    private String address;
    private String orderContents;
    private OrderContentBean orderContent;

    public String getOrderNum() {
        return orderNum;
    }

    public String getOrderid(){
        return orderNum;
    }
    public void setOrderid(String orderid){
        orderid = orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(Object priceFinal) {
        this.priceFinal = priceFinal;
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime;
    }

    public Object getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(Object expressNum) {
        this.expressNum = expressNum;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderContents(){
        return orderContents;
    }

    public void setOrderContents(String orderContents){
        this.orderContent = orderContent;
    }

    public OrderContentBean getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(OrderContentBean orderContent) {
        this.orderContent = orderContent;
    }

    public static class OrderContentBean {
        /**
         * pic : /userfiles/1/files/goods/goods/2018/11/201809180246406909_x%20(1).jpg
         * goodsName : 创维(SKYWORTH)43X6 43英寸 全高清智能液晶平板液晶电视
         * sellPoint : 10核处理器
         * price : 15999.0
         * num : 1
         */

        private String pic;
        private String goodsName;
        private String sellPoint;
        private String price;
        private String num;
        private String sku;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getSellPoint() {
            return sellPoint;
        }

        public void setSellPoint(String sellPoint) {
            this.sellPoint = sellPoint;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getSku(){return  sku;}

        public void setSku(String sku){this.sku = sku;}
    }
}
