package com.wlan.familymembers.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/11/7.
 */

public class GoodsDetailBean {

    /**
     * goodsName : 创维(SKYWORTH)43X6 43英寸 全高清智能液晶平板液晶电视
     * goodsSellPoint : 10核处理器
     * goodsPrice : 4999.00
     * originalPrice :
     * goodsTag : 新品上架
     * goodsBanner : ["/userfiles/1/files/goods/goods/2018/10/945023944-3999.jpg","/userfiles/1/files/goods/goods/2018/10/201810311210347384_x.jpg"]
     * goodsDetail : <p><img src=/userfiles/upload/image/20181101/1541052085647061202.jpg title=1541052085647061202.jpg alt=945023944-3999.jpg/></p>
     * goodsClassify : 电视
     * goodsIntegral : 400
     * goodsPic : /userfiles/1/files/goods/goods/2018/10/201810311210347384_x.jpg
     * salesVolume :
     */

    private String goodsName;
    private String goodsSellPoint;
    private String goodsPrice;
    private String originalPrice;
    private String goodsTag;
    private String goodsDetail;
    private String goodsClassify;
    private int goodsIntegral;
    private String goodsPic;
    private String salesVolume;
    private ArrayList<String> goodsBanner;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSellPoint() {
        return goodsSellPoint;
    }

    public void setGoodsSellPoint(String goodsSellPoint) {
        this.goodsSellPoint = goodsSellPoint;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(String goodsClassify) {
        this.goodsClassify = goodsClassify;
    }

    public int getGoodsIntegral() {
        return goodsIntegral;
    }

    public void setGoodsIntegral(int goodsIntegral) {
        this.goodsIntegral = goodsIntegral;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public ArrayList<String> getGoodsBanner() {
        return goodsBanner;
    }

    public void setGoodsBanner(ArrayList<String> goodsBanner) {
        this.goodsBanner = goodsBanner;
    }
}
