package com.wlan.familymembers.bean;

import retrofit2.http.Body;

/**
 * 类作用：
 * Created by Administrator on 2018/10/15.
 */

public class MallBottomBean {

    /**
     * id : 1539935123439
     * type : 2
     * goodsName : 洗电脑
     * goodsPic : /userfiles/1/files/hm/servicehm/2018/10/201810170351368480_x.jpg
     * goodsPrice : 20
     * originalPrice : 0
     * goodsTag : 专注洗电脑五十年
     //     */

    private String id;
    private String type;
    private String goodsName;
    private String goodsPic;
    private String goodsPrice;
    private String originalPrice;
    private String goodsTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
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

}
