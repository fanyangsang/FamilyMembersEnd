package com.wlan.familymembers.bean;

import java.util.List;

/**
 * 类作用：
 * Created by Administrator on 2018/12/5.
 */

public class MallHomepageBean {

    private String id;
    private Object remarks;
    private Object createBy;
    private Object createDate;
    private Object updateBy;
    private Object updateDate;
    private Object homeHot;
    private GoodsBeanX goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public Object getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Object createBy) {
        this.createBy = createBy;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getHomeHot() {
        return homeHot;
    }

    public void setHomeHot(Object homeHot) {
        this.homeHot = homeHot;
    }

    public GoodsBeanX getGoods() {
        return goods;
    }

    public void setGoods(GoodsBeanX goods) {
        this.goods = goods;
    }

    public static class GoodsBeanX {
        /**
         * id : null
         * remarks : null
         * createBy : null
         * createDate : null
         * updateBy : null
         * updateDate : null
         * goodsName : 小米mix3
         * goodsSellPoint : null
         * goodsPrice : 33
         * goodsTag : {"id":null,"remarks":null,"createBy":null,"createDate":null,"updateBy":null,"updateDate":null,"tagName":"热销"}
         * goodsBanner : null
         * goodsDetail : null
         * goodsClassify : null
         * goodsAttr : null
         * goodsIntegral : null
         * goodsPic : /userfiles/1/files/goods/goods/2018/12/O1CN01dqycgR1r2w97Cz3XJ_!!0-item_pic_jpg_130x130.jpg
         * onSale : null
         * salesVolume : null
         * originalPrice :
         */

        private Object id;
        private Object remarks;
        private Object createBy;
        private Object createDate;
        private Object updateBy;
        private Object updateDate;
        private String goodsName;
        private Object goodsSellPoint;
        private String goodsPrice;
        private GoodsTagBeanX goodsTag;
        private Object goodsBanner;
        private Object goodsDetail;
        private Object goodsClassify;
        private Object goodsAttr;
        private Object goodsIntegral;
        private String goodsPic;
        private Object onSale;
        private Object salesVolume;
        private String originalPrice;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public Object getGoodsSellPoint() {
            return goodsSellPoint;
        }

        public void setGoodsSellPoint(Object goodsSellPoint) {
            this.goodsSellPoint = goodsSellPoint;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public GoodsTagBeanX getGoodsTag() {
            return goodsTag;
        }

        public void setGoodsTag(GoodsTagBeanX goodsTag) {
            this.goodsTag = goodsTag;
        }

        public Object getGoodsBanner() {
            return goodsBanner;
        }

        public void setGoodsBanner(Object goodsBanner) {
            this.goodsBanner = goodsBanner;
        }

        public Object getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(Object goodsDetail) {
            this.goodsDetail = goodsDetail;
        }

        public Object getGoodsClassify() {
            return goodsClassify;
        }

        public void setGoodsClassify(Object goodsClassify) {
            this.goodsClassify = goodsClassify;
        }

        public Object getGoodsAttr() {
            return goodsAttr;
        }

        public void setGoodsAttr(Object goodsAttr) {
            this.goodsAttr = goodsAttr;
        }

        public Object getGoodsIntegral() {
            return goodsIntegral;
        }

        public void setGoodsIntegral(Object goodsIntegral) {
            this.goodsIntegral = goodsIntegral;
        }

        public String getGoodsPic() {
            return goodsPic;
        }

        public void setGoodsPic(String goodsPic) {
            this.goodsPic = goodsPic;
        }

        public Object getOnSale() {
            return onSale;
        }

        public void setOnSale(Object onSale) {
            this.onSale = onSale;
        }

        public Object getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(Object salesVolume) {
            this.salesVolume = salesVolume;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public static class GoodsTagBeanX {
            /**
             * id : null
             * remarks : null
             * createBy : null
             * createDate : null
             * updateBy : null
             * updateDate : null
             * tagName : 热销
             */

            private Object id;
            private Object remarks;
            private Object createBy;
            private Object createDate;
            private Object updateBy;
            private Object updateDate;
            private String tagName;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getRemarks() {
                return remarks;
            }

            public void setRemarks(Object remarks) {
                this.remarks = remarks;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public Object getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Object createDate) {
                this.createDate = createDate;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(Object updateDate) {
                this.updateDate = updateDate;
            }

            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }
        }
    }
}



