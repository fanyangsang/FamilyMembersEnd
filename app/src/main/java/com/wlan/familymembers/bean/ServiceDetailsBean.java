package com.wlan.familymembers.bean;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/10/31.
 */

public class ServiceDetailsBean {

    /**
     * id : 1539935123439
     * name : 洗电脑
     * sellingPoint : 专注洗电脑五十年
     * banners : ["/userfiles/1/files/hm/servicehm/2018/10/2233.jpg","/userfiles/1/files/hm/servicehm/2018/10/%E7%94%B5%E8%A7%86%E6%9C%BA.jpg"]
     * price : 80.52
     * prePay : 20
     * salesVolume : 0
     * detail :
     */

    private String id;
    private String name;
    private String sellingPoint;
    private String price;
    private String prePay;
    private int salesVolume;
    private String detail;
    private List<String> banners;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellingPoint() {
        return sellingPoint;
    }

    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrePay() {
        return prePay;
    }

    public void setPrePay(String prePay) {
        this.prePay = prePay;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }
}
